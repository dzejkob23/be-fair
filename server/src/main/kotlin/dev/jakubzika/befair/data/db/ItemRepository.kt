package dev.jakubzika.befair.data.db

import dev.jakubzika.befair.domain.model.ItemStats
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.greater
import org.jetbrains.exposed.v1.core.isNull
import org.jetbrains.exposed.v1.core.less
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

/** Result of an idempotent create: [created] is false when the id already existed. */
data class CreateResult<T>(val row: T, val created: Boolean)

/**
 * Data-access layer for [Items] and [ItemEvents]. Each call runs a blocking Exposed
 * transaction on the IO dispatcher so route handlers can stay `suspend`. Ownership checks
 * (matching `userId`) live here so route handlers never assemble raw SQL conditions.
 */
class ItemRepository {

    // ---- Items -------------------------------------------------------------------------

    /** Finds an item by id, scoped to its owner, regardless of archived/deleted state. */
    suspend fun findOwned(id: String, userId: Int): ItemRow? = dbQuery {
        Items.selectAll()
            .where { (Items.id eq id) and (Items.userId eq userId) }
            .map(::toItemRow)
            .singleOrNull()
    }

    suspend fun listForUser(
        userId: Int,
        kind: String?,
        includeArchived: Boolean,
        updatedSince: Long?,
    ): List<ItemRow> = dbQuery {
        val conditions = mutableListOf<Op<Boolean>>(Items.userId eq userId)
        if (updatedSince != null) {
            conditions += Items.updatedAt greater updatedSince
        } else {
            conditions += Items.deletedAt.isNull()
            if (!includeArchived) {
                conditions += Items.archivedAt.isNull()
            }
        }
        if (kind != null) {
            conditions += Items.kind eq kind
        }
        Items.selectAll()
            .where { conditions.reduce { acc, condition -> acc and condition } }
            .orderBy(Items.createdAt to SortOrder.ASC)
            .map(::toItemRow)
    }

    /** Inserts [row], or returns the already-stored row for the same id unchanged. */
    suspend fun create(row: ItemRow): CreateResult<ItemRow> = dbQuery {
        val existing = Items.selectAll().where { Items.id eq row.id }.map(::toItemRow).singleOrNull()
        if (existing != null) {
            CreateResult(existing, created = false)
        } else {
            Items.insert {
                it[id] = row.id
                it[userId] = row.userId
                it[kind] = row.kind
                it[name] = row.name
                it[category] = row.category
                it[priceCents] = row.priceCents
                it[currency] = row.currency
                it[purchasedOn] = row.purchasedOn
                it[archivedAt] = row.archivedAt
                it[deletedAt] = row.deletedAt
                it[createdAt] = row.createdAt
                it[updatedAt] = row.updatedAt
            }
            CreateResult(row, created = true)
        }
    }

    /** Applies only the non-null fields; always bumps `updatedAt`. */
    suspend fun update(
        id: String,
        name: String?,
        category: String?,
        priceCents: Long?,
        currency: String?,
        purchasedOn: Long?,
        now: Long,
    ): ItemRow = dbQuery {
        Items.update({ Items.id eq id }) { statement ->
            name?.let { statement[Items.name] = it }
            category?.let { statement[Items.category] = it }
            priceCents?.let { statement[Items.priceCents] = it }
            currency?.let { statement[Items.currency] = it }
            purchasedOn?.let { statement[Items.purchasedOn] = it }
            statement[Items.updatedAt] = now
        }
        Items.selectAll().where { Items.id eq id }.map(::toItemRow).single()
    }

    suspend fun setArchived(id: String, archived: Boolean, now: Long): ItemRow = dbQuery {
        Items.update({ Items.id eq id }) {
            it[archivedAt] = if (archived) now else null
            it[updatedAt] = now
        }
        Items.selectAll().where { Items.id eq id }.map(::toItemRow).single()
    }

    suspend fun softDelete(id: String, now: Long) {
        dbQuery {
            Items.update({ Items.id eq id }) {
                it[deletedAt] = now
                it[updatedAt] = now
            }
        }
    }

    /** Bumps `updatedAt` without changing any other field, e.g. after a stats-affecting event. */
    suspend fun touchUpdatedAt(id: String, now: Long) {
        dbQuery {
            Items.update({ Items.id eq id }) {
                it[updatedAt] = now
            }
        }
    }

    // ---- Item events ---------------------------------------------------------------------

    suspend fun createEvent(row: ItemEventRow): CreateResult<ItemEventRow> = dbQuery {
        val existing = ItemEvents.selectAll().where { ItemEvents.id eq row.id }.map(::toItemEventRow).singleOrNull()
        if (existing != null) {
            CreateResult(existing, created = false)
        } else {
            ItemEvents.insert {
                it[id] = row.id
                it[itemId] = row.itemId
                it[type] = row.type
                it[occurredAt] = row.occurredAt
                it[costCents] = row.costCents
                it[note] = row.note
                it[createdAt] = row.createdAt
                it[deletedAt] = row.deletedAt
            }
            CreateResult(row, created = true)
        }
    }

    suspend fun listEvents(itemId: String, limit: Int, before: Long?): List<ItemEventRow> = dbQuery {
        val conditions = mutableListOf<Op<Boolean>>(ItemEvents.itemId eq itemId, ItemEvents.deletedAt.isNull())
        if (before != null) {
            conditions += ItemEvents.occurredAt less before
        }
        ItemEvents.selectAll()
            .where { conditions.reduce { acc, condition -> acc and condition } }
            .orderBy(ItemEvents.occurredAt to SortOrder.DESC)
            .limit(limit)
            .map(::toItemEventRow)
    }

    /** All non-deleted events for [itemId], used to compute [dev.jakubzika.befair.domain.model.ItemStats]. */
    suspend fun activeEventsFor(itemId: String): List<ItemEventRow> = dbQuery {
        ItemEvents.selectAll()
            .where { (ItemEvents.itemId eq itemId) and ItemEvents.deletedAt.isNull() }
            .map(::toItemEventRow)
    }

    suspend fun findEventForItem(eventId: String, itemId: String): ItemEventRow? = dbQuery {
        ItemEvents.selectAll()
            .where { (ItemEvents.id eq eventId) and (ItemEvents.itemId eq itemId) }
            .map(::toItemEventRow)
            .singleOrNull()
    }

    suspend fun softDeleteEvent(id: String, now: Long) {
        dbQuery {
            ItemEvents.update({ ItemEvents.id eq id }) {
                it[deletedAt] = now
            }
        }
    }

    // ---- Derived stats ---------------------------------------------------------------------

    /** Aggregates active events into the counters/derived costs shown on [ItemRow]. */
    suspend fun statsFor(item: ItemRow) = computeStats(item, activeEventsFor(item.id))

    private fun computeStats(
        item: ItemRow,
        events: List<ItemEventRow>,
    ): ItemStats {
        val wearCount = events.count { it.type == "WEAR" }
        val washCount = events.count { it.type == "WASH" }
        val useCount = events.count { it.type == "USE" }
        val maintenanceCents = events.sumOf { it.costCents ?: 0L }
        val lastEventAt = events.maxOfOrNull { it.occurredAt }

        val usageCount = if (item.kind == "TOOL") useCount else wearCount
        val costPerUseCents = if (usageCount > 0) {
            (item.priceCents + maintenanceCents) / usageCount
        } else {
            null
        }

        val costPerMonthCents = if (item.kind == "TOOL") {
            val months = ChronoUnit.MONTHS.between(LocalDate.ofEpochDay(item.purchasedOn), LocalDate.now())
            if (months > 0) item.priceCents / months else null
        } else {
            null
        }

        return ItemStats(
            wearCount = wearCount,
            washCount = washCount,
            useCount = useCount,
            maintenanceCents = maintenanceCents,
            lastEventAt = lastEventAt,
            costPerUseCents = costPerUseCents,
            costPerMonthCents = costPerMonthCents,
        )
    }

    // ---- Mapping ---------------------------------------------------------------------------

    private fun toItemRow(row: ResultRow) = ItemRow(
        id = row[Items.id],
        userId = row[Items.userId],
        kind = row[Items.kind],
        name = row[Items.name],
        category = row[Items.category],
        priceCents = row[Items.priceCents],
        currency = row[Items.currency],
        purchasedOn = row[Items.purchasedOn],
        archivedAt = row[Items.archivedAt],
        deletedAt = row[Items.deletedAt],
        createdAt = row[Items.createdAt],
        updatedAt = row[Items.updatedAt],
    )

    private fun toItemEventRow(row: ResultRow) = ItemEventRow(
        id = row[ItemEvents.id],
        itemId = row[ItemEvents.itemId],
        type = row[ItemEvents.type],
        occurredAt = row[ItemEvents.occurredAt],
        costCents = row[ItemEvents.costCents],
        note = row[ItemEvents.note],
        createdAt = row[ItemEvents.createdAt],
        deletedAt = row[ItemEvents.deletedAt],
    )

    private suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) { transaction { block() } }
}
