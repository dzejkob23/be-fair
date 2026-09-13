package dev.jakubzika.befair.routes

import dev.jakubzika.befair.auth.JwtConfig
import dev.jakubzika.befair.data.db.ItemEventRow
import dev.jakubzika.befair.data.db.ItemRepository
import dev.jakubzika.befair.data.db.ItemRow
import dev.jakubzika.befair.domain.model.CreateItemRequest
import dev.jakubzika.befair.domain.model.EventLoggedResponse
import dev.jakubzika.befair.domain.model.GenericResponse
import dev.jakubzika.befair.domain.model.ItemEventListResponse
import dev.jakubzika.befair.domain.model.ItemEventResponse
import dev.jakubzika.befair.domain.model.ItemEventType
import dev.jakubzika.befair.domain.model.ItemKind
import dev.jakubzika.befair.domain.model.ItemListResponse
import dev.jakubzika.befair.domain.model.ItemResponse
import dev.jakubzika.befair.domain.model.ItemStats
import dev.jakubzika.befair.domain.model.LogEventRequest
import dev.jakubzika.befair.domain.model.UpdateItemRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import java.time.LocalDate
import kotlin.math.min

private const val DEFAULT_EVENT_LIMIT = 50
private const val MAX_EVENT_LIMIT = 200
private val currencyPattern = Regex("^[A-Za-z]{3}$")

/**
 * Mounts the item-tracking endpoints under /api/items. Every route requires a valid access
 * token; ownership is always derived from the JWT `userId` claim, never from the request body.
 */
fun Route.itemRoutes(itemRepository: ItemRepository) {
    authenticate("auth-jwt") {
        route("/api/items") {
            createItem(itemRepository)
            listItems(itemRepository)
            getItem(itemRepository)
            updateItem(itemRepository)
            archiveItem(itemRepository)
            deleteItem(itemRepository)
            logEvent(itemRepository)
            listEvents(itemRepository)
            deleteEvent(itemRepository)
        }
    }
}

private fun Route.createItem(repo: ItemRepository) = post {
    val userId = call.currentUserId() ?: return@post call.respondUnauthorized()
    val request = call.receive<CreateItemRequest>()

    val today = LocalDate.now().toEpochDay()
    val errors = mutableMapOf<String, String>()
    if (request.name.isBlank() || request.name.length > 120) {
        errors["name"] = "Name must be between 1 and 120 characters."
    }
    if (request.priceCents <= 0) {
        errors["priceCents"] = "Price must be greater than zero."
    }
    if (request.purchasedOn > today) {
        errors["purchasedOn"] = "Purchase date cannot be in the future."
    }
    if (!currencyPattern.matches(request.currency)) {
        errors["currency"] = "Currency must be a 3-letter ISO 4217 code."
    }
    if (errors.isNotEmpty()) {
        return@post call.respond(HttpStatusCode.BadRequest, GenericResponse(false, "Validation failed.", errors))
    }

    val now = System.currentTimeMillis()
    val category = request.category?.trim()?.takeIf { it.isNotBlank() } ?: defaultCategory(request.kind)
    val (item, created) = repo.create(
        ItemRow(
            id = request.id,
            userId = userId,
            kind = request.kind.name,
            name = request.name.trim(),
            category = category,
            priceCents = request.priceCents,
            currency = request.currency.uppercase(),
            purchasedOn = request.purchasedOn,
            archivedAt = null,
            deletedAt = null,
            createdAt = now,
            updatedAt = now,
        ),
    )

    if (item.userId != userId) {
        // id collision with another user's item -- never leak that it exists.
        return@post call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    }

    val stats = if (created) null else repo.statsFor(item)
    call.respond(if (created) HttpStatusCode.Created else HttpStatusCode.OK, item.toResponse(stats))
}

private fun Route.listItems(repo: ItemRepository) = get {
    val userId = call.currentUserId() ?: return@get call.respondUnauthorized()

    val kind = call.request.queryParameters["kind"]?.let { raw ->
        runCatching { ItemKind.valueOf(raw) }.getOrNull()
            ?: return@get call.respond(HttpStatusCode.BadRequest, GenericResponse(false, "Invalid kind filter."))
    }
    val includeArchived = call.request.queryParameters["includeArchived"]?.toBooleanStrictOrNull() ?: false
    val updatedSince = call.request.queryParameters["updatedSince"]?.toLongOrNull()

    val items = repo.listForUser(userId, kind?.name, includeArchived, updatedSince)
    val responses = items.map { row ->
        if (row.deletedAt != null) row.toResponse(stats = null) else row.toResponse(repo.statsFor(row))
    }
    call.respond(ItemListResponse(items = responses, serverTime = System.currentTimeMillis()))
}

private fun Route.getItem(repo: ItemRepository) = get("/{id}") {
    val userId = call.currentUserId() ?: return@get call.respondUnauthorized()
    val id = call.parameters["id"]!!

    val item = repo.findOwned(id, userId)?.takeIf { it.deletedAt == null }
        ?: return@get call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    call.respond(item.toResponse(repo.statsFor(item)))
}

private fun Route.updateItem(repo: ItemRepository) = patch("/{id}") {
    val userId = call.currentUserId() ?: return@patch call.respondUnauthorized()
    val id = call.parameters["id"]!!

    val existing = repo.findOwned(id, userId)
        ?: return@patch call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    if (existing.deletedAt != null) {
        return@patch call.respond(HttpStatusCode.Conflict, GenericResponse(false, "Item has been deleted."))
    }

    val request = call.receive<UpdateItemRequest>()
    val today = LocalDate.now().toEpochDay()
    val errors = mutableMapOf<String, String>()
    request.name?.let { if (it.isBlank() || it.length > 120) errors["name"] = "Name must be between 1 and 120 characters." }
    request.priceCents?.let { if (it <= 0) errors["priceCents"] = "Price must be greater than zero." }
    request.purchasedOn?.let { if (it > today) errors["purchasedOn"] = "Purchase date cannot be in the future." }
    request.currency?.let { if (!currencyPattern.matches(it)) errors["currency"] = "Currency must be a 3-letter ISO 4217 code." }
    if (errors.isNotEmpty()) {
        return@patch call.respond(HttpStatusCode.BadRequest, GenericResponse(false, "Validation failed.", errors))
    }

    val updated = repo.update(
        id = id,
        name = request.name?.trim(),
        category = request.category?.trim()?.takeIf { it.isNotBlank() },
        priceCents = request.priceCents,
        currency = request.currency?.uppercase(),
        purchasedOn = request.purchasedOn,
        now = System.currentTimeMillis(),
    )
    call.respond(updated.toResponse(repo.statsFor(updated)))
}

private fun Route.archiveItem(repo: ItemRepository) = post("/{id}/archive") {
    val userId = call.currentUserId() ?: return@post call.respondUnauthorized()
    val id = call.parameters["id"]!!

    val existing = repo.findOwned(id, userId)
        ?: return@post call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    if (existing.deletedAt != null) {
        return@post call.respond(HttpStatusCode.Conflict, GenericResponse(false, "Item has been deleted."))
    }

    val updated = repo.setArchived(id, archived = existing.archivedAt == null, now = System.currentTimeMillis())
    call.respond(updated.toResponse(repo.statsFor(updated)))
}

private fun Route.deleteItem(repo: ItemRepository) = delete("/{id}") {
    val userId = call.currentUserId() ?: return@delete call.respondUnauthorized()
    val id = call.parameters["id"]!!

    val existing = repo.findOwned(id, userId)
        ?: return@delete call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    if (existing.deletedAt == null) {
        repo.softDelete(id, now = System.currentTimeMillis())
    }
    call.respond(HttpStatusCode.NoContent)
}

private fun Route.logEvent(repo: ItemRepository) = post("/{id}/events") {
    val userId = call.currentUserId() ?: return@post call.respondUnauthorized()
    val id = call.parameters["id"]!!

    val item = repo.findOwned(id, userId)
        ?: return@post call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    if (item.deletedAt != null) {
        return@post call.respond(HttpStatusCode.Conflict, GenericResponse(false, "Item has been deleted."))
    }

    val request = call.receive<LogEventRequest>()
    val now = System.currentTimeMillis()
    val occurredAt = request.occurredAt ?: now

    val errors = mutableMapOf<String, String>()
    val typeCompatible = when (request.type) {
        ItemEventType.WEAR, ItemEventType.WASH -> item.kind == ItemKind.CLOTHING.name
        ItemEventType.USE -> item.kind == ItemKind.TOOL.name
        ItemEventType.REPAIR -> true
    }
    if (!typeCompatible) {
        errors["type"] = "Event type ${request.type} is not valid for kind ${item.kind}."
    }
    if (occurredAt > now) {
        errors["occurredAt"] = "Event time cannot be in the future."
    }
    request.note?.let { if (it.length > 255) errors["note"] = "Note must be at most 255 characters." }
    if (errors.isNotEmpty()) {
        return@post call.respond(HttpStatusCode.BadRequest, GenericResponse(false, "Validation failed.", errors))
    }

    val (event, created) = repo.createEvent(
        ItemEventRow(
            id = request.id,
            itemId = id,
            type = request.type.name,
            occurredAt = occurredAt,
            costCents = request.costCents,
            note = request.note,
            createdAt = now,
            deletedAt = null,
        ),
    )

    if (event.itemId != id) {
        // id collision with an event on another item -- never leak that it exists.
        return@post call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    }
    if (created) {
        repo.touchUpdatedAt(id, now)
    }

    val refreshedItem = repo.findOwned(id, userId) ?: item
    call.respond(
        if (created) HttpStatusCode.Created else HttpStatusCode.OK,
        EventLoggedResponse(
            event = event.toResponse(),
            item = refreshedItem.toResponse(repo.statsFor(refreshedItem)),
        ),
    )
}

private fun Route.listEvents(repo: ItemRepository) = get("/{id}/events") {
    val userId = call.currentUserId() ?: return@get call.respondUnauthorized()
    val id = call.parameters["id"]!!

    repo.findOwned(id, userId)?.takeIf { it.deletedAt == null }
        ?: return@get call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))

    val limit = call.request.queryParameters["limit"]?.toIntOrNull()
        ?.coerceAtLeast(1)
        ?.let { min(it, MAX_EVENT_LIMIT) }
        ?: DEFAULT_EVENT_LIMIT
    val before = call.request.queryParameters["before"]?.toLongOrNull()

    val events = repo.listEvents(id, limit, before)
    call.respond(ItemEventListResponse(events = events.map { it.toResponse() }, serverTime = System.currentTimeMillis()))
}

private fun Route.deleteEvent(repo: ItemRepository) = delete("/{id}/events/{eventId}") {
    val userId = call.currentUserId() ?: return@delete call.respondUnauthorized()
    val id = call.parameters["id"]!!
    val eventId = call.parameters["eventId"]!!

    val item = repo.findOwned(id, userId)
        ?: return@delete call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Item not found."))
    val event = repo.findEventForItem(eventId, id)
        ?: return@delete call.respond(HttpStatusCode.NotFound, GenericResponse(false, "Event not found."))

    if (event.deletedAt == null) {
        val now = System.currentTimeMillis()
        repo.softDeleteEvent(eventId, now)
        repo.touchUpdatedAt(item.id, now)
    }
    call.respond(HttpStatusCode.NoContent)
}

private fun defaultCategory(kind: ItemKind): String = when (kind) {
    ItemKind.CLOTHING -> "Clothing"
    ItemKind.TOOL -> "Tool"
}

private fun ItemRow.toResponse(stats: ItemStats?) = ItemResponse(
    id = id,
    kind = ItemKind.valueOf(kind),
    name = name,
    category = category,
    priceCents = priceCents,
    currency = currency,
    purchasedOn = purchasedOn,
    archivedAt = archivedAt,
    deletedAt = deletedAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    stats = stats,
)

private fun ItemEventRow.toResponse() = ItemEventResponse(
    id = id,
    itemId = itemId,
    type = ItemEventType.valueOf(type),
    occurredAt = occurredAt,
    costCents = costCents,
    note = note,
    createdAt = createdAt,
)

private fun ApplicationCall.currentUserId(): Int? =
    principal<JWTPrincipal>()?.let { JwtConfig.userIdClaim(it.payload) }?.toIntOrNull()

private suspend fun ApplicationCall.respondUnauthorized() =
    respond(HttpStatusCode.Unauthorized, GenericResponse(false, "Unknown user."))
