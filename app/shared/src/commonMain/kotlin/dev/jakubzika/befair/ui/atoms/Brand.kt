package dev.jakubzika.befair.ui.atoms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.brand_mark
import be_fair.app.shared.generated.resources.brand_wordmark
import org.jetbrains.compose.resources.stringResource

// Brand mark per DESIGN.md: "B=" mark + "BeFair" wordmark, both headline-lg
// (700 weight) in ink. No color — the mark carries weight, not hue.
@Composable
fun BrandMark(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(Res.string.brand_mark),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(BeFairDimension.Spacing.xs))
        Text(
            text = stringResource(Res.string.brand_wordmark),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun BrandMarkPreview() {
    BeFairTheme {
        BrandMark()
    }
}
