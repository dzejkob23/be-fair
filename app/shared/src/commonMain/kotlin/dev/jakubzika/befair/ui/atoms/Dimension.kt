package dev.jakubzika.befair.ui.atoms

import androidx.compose.ui.unit.dp

/**
 * Usage:
 *   BeFairDimension.Spacing.md     → 16.dp  (mobile gutter)
 *   BeFairDimension.Spacing.lg     → 24.dp  (desktop margin)
 *   BeFairDimension.Radius.medium  → 8.dp
 *   BeFairDimension.Radius.full    → 9999.dp (pill shape)
 */
object BeFairDimension {

    object Spacing {
        val xs = 4.dp
        val sm = 8.dp
        val md = 16.dp
        val lg = 24.dp
        val xl = 32.dp
        val unit = 4.dp
    }

    object Radius {
        val small = 4.dp
        val medium = 8.dp
        val large = 12.dp
        val full = 9999.dp
    }
}
