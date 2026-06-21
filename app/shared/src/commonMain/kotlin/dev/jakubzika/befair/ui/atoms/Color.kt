package dev.jakubzika.befair.ui.atoms

import androidx.compose.ui.graphics.Color

// Colors generated from app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/DESIGN.md
// "BeFair — Digital Functionalism": warm achromatic neutrals + 3 meaning-bearing accents
// (primary = leaf green, error = blood orange, notice = honey yellow).
//
// M3 role mapping:
//   secondary  -> neutral stone-grey (ink-2 family) — kept achromatic, no decorative hue
//   tertiary   -> muted/desaturated green derived from primary — stays monochromatic
//   notice     -> not an M3 role; declared below as a standalone custom color

/************************/
/***** LIGHT COLORS *****/
/************************/

val primaryLight = Color(0xFF3E7C33)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFDFE6DB)
val onPrimaryContainerLight = Color(0xFF37692D)
val secondaryLight = Color(0xFF6F6F69)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFCFCFC8)
val onSecondaryContainerLight = Color(0xFF3E3E38)
val tertiaryLight = Color(0xFF57764E)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFDDE2D9)
val onTertiaryContainerLight = Color(0xFF3D5337)
val errorLight = Color(0xFFC9431A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFF0E0D8)
val onErrorContainerLight = Color(0xFF8D2F12)
val backgroundLight = Color(0xFFF5F5F2)
val onBackgroundLight = Color(0xFF161614)
val surfaceLight = Color(0xFFFFFFFF)
val onSurfaceLight = Color(0xFF161614)
val surfaceVariantLight = Color(0xFFE4E4DE)
val onSurfaceVariantLight = Color(0xFF6F6F69)
val outlineLight = Color(0xFFCFCFC8)
val outlineVariantLight = Color(0xFFE4E4DE)
val scrimLight = Color(0xFF161614)
val inverseSurfaceLight = Color(0xFF161614)
val inverseOnSurfaceLight = Color(0xFFF5F5F2)
val inversePrimaryLight = Color(0xFF5FA653)
val surfaceDimLight = Color(0xFFDBDBD7)
val surfaceBrightLight = Color(0xFFFFFFFF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFFAFAF7)
val surfaceContainerLight = Color(0xFFF5F5F2)
val surfaceContainerHighLight = Color(0xFFE4E4DE)
val surfaceContainerHighestLight = Color(0xFFCFCFC8)

// Custom semantic color — advisory/notice only, never an error. Not part of the M3 ColorScheme.
val noticeLight = Color(0xFFE2A416)
val onNoticeLight = Color(0xFF161614)
val noticeTintLight = Color(0xFFFBF3DE)

/***********************/
/***** DARK COLORS *****/
/***********************/

val primaryDark = Color(0xFF5FA653)
val onPrimaryDark = Color(0xFF0C0C0B)
val primaryContainerDark = Color(0xFF2C3A27)
val onPrimaryContainerDark = Color(0xFF98C28F)
val secondaryDark = Color(0xFFA3A39C)
val onSecondaryDark = Color(0xFF0C0C0B)
val secondaryContainerDark = Color(0xFF3D3D38)
val onSecondaryContainerDark = Color(0xFFC8C8C2)
val tertiaryDark = Color(0xFF81A578)
val onTertiaryDark = Color(0xFF0C0C0B)
val tertiaryContainerDark = Color(0xFF333A2E)
val onTertiaryContainerDark = Color(0xFFACC2A5)
val errorDark = Color(0xFFE0673E)
val onErrorDark = Color(0xFF0C0C0B)
val errorContainerDark = Color(0xFF462D23)
val onErrorContainerDark = Color(0xFFE59D82)
val backgroundDark = Color(0xFF161614)
val onBackgroundDark = Color(0xFFEDEDE8)
val surfaceDark = Color(0xFF1F1F1C)
val onSurfaceDark = Color(0xFFEDEDE8)
val surfaceVariantDark = Color(0xFF2E2E2A)
val onSurfaceVariantDark = Color(0xFFA3A39C)
val outlineDark = Color(0xFF3D3D38)
val outlineVariantDark = Color(0xFF2E2E2A)
val scrimDark = Color(0xFF0C0C0B)
val inverseSurfaceDark = Color(0xFFFFFFFF)
val inverseOnSurfaceDark = Color(0xFF161614)
val inversePrimaryDark = Color(0xFF3E7C33)
val surfaceDimDark = Color(0xFF0C0C0B)
val surfaceBrightDark = Color(0xFF272723)
val surfaceContainerLowestDark = Color(0xFF0C0C0B)
val surfaceContainerLowDark = Color(0xFF161614)
val surfaceContainerDark = Color(0xFF1F1F1C)
val surfaceContainerHighDark = Color(0xFF272723)
val surfaceContainerHighestDark = Color(0xFF3D3D38)

// Custom semantic color — advisory/notice only, never an error. Not part of the M3 ColorScheme.
val noticeDark = Color(0xFFE9B53D)
val onNoticeDark = Color(0xFF0C0C0B)
val noticeTintDark = Color(0xFF2A2415)
