package dev.jakubzika.befair.ui.atoms

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.font_helvetica_neue_bold
import be_fair.app.shared.generated.resources.font_helvetica_neue_medium
import be_fair.app.shared.generated.resources.font_helvetica_neue_roman
import org.jetbrains.compose.resources.Font

// DESIGN.md mandates a single neo-grotesque, Helvetica Neue, carrying the
// whole system. Only Regular/Medium/Bold do real work (see "Typography").
@Composable
fun BeFairTypography(): Typography {
    val helveticaNeue = FontFamily(
        Font(Res.font.font_helvetica_neue_roman, FontWeight.Normal),
        Font(Res.font.font_helvetica_neue_medium, FontWeight.Medium),
        Font(Res.font.font_helvetica_neue_bold, FontWeight.Bold)
    )
    return Typography(
        // display — stat heroes & cost numbers
        displayLarge = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.56).sp
        ),
        // headline-lg — screen titles
        headlineLarge = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            lineHeight = 20.9.sp,
            letterSpacing = (-0.19).sp
        ),
        // headline-md — section headers
        headlineMedium = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            lineHeight = 20.4.sp,
            letterSpacing = (-0.17).sp
        ),
        // label-action — button labels
        titleLarge = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            lineHeight = 15.sp,
            letterSpacing = 0.15.sp
        ),
        // label-value — tabular numeric values
        titleMedium = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp,
            fontFeatureSettings = "tnum 1"
        ),
        // label-row — list row names
        titleSmall = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp
        ),
        // body-lg
        bodyLarge = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 21.75.sp,
            letterSpacing = 0.sp
        ),
        // body-md
        bodyMedium = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.3.sp,
            letterSpacing = 0.sp
        ),
        // body-sm
        bodySmall = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 18.85.sp,
            letterSpacing = 0.sp
        ),
        // label-caps — uppercase structural signposting (apply textTransform at call site)
        labelLarge = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            letterSpacing = 0.96.sp
        ),
        // caption
        labelMedium = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.8.sp,
            letterSpacing = 0.sp
        ),
        // tab — bold when active (apply fontWeight override at call site)
        labelSmall = TextStyle(
            fontFamily = helveticaNeue,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 11.sp,
            letterSpacing = 0.sp
        )
        // displayMedium, displaySmall, headlineSmall retain Material 3 defaults.
    )
}
