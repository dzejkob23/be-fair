---
version: alpha
name: BeFair — Digital Functionalism
description: >-
  Achromatic, hairline-structured design system for the BeFair wear & tool
  cost tracker. A Dieter Rams "less, but better" adaptation: matte neutrals,
  strict 8dp rhythm, neo-grotesque type, and semantic color used only to mean
  something. Mobile-first (390px canvas).
colors:
  # Neutrals — the achromatic base (warm-tinted, never pure)
  canvas: "#DBDBD7"
  background: "#F5F5F2"
  surface: "#FFFFFF"
  ink: "#161614"
  ink-2: "#6F6F69"
  ink-3: "#9C9C95"
  hairline: "#E4E4DE"
  hairline-strong: "#CFCFC8"
  # Semantic accents — used only to carry meaning
  primary: "#3E7C33"        # leaf green — confirm / start / success
  on-primary: "#FFFFFF"
  error: "#C9431A"          # blood orange — destructive / error
  notice: "#E2A416"         # honey yellow — notice / bookmark
  notice-tint: "#FBF3DE"
colors-dark:
  # Neutrals — warm charcoals; tonal layers invert (surfaces lift LIGHTER)
  canvas: "#0C0C0B"
  background: "#161614"
  surface: "#1F1F1C"
  surface-2: "#272723"      # raised/pressed surface, bottom sheets
  ink: "#EDEDE8"
  ink-2: "#A3A39C"
  ink-3: "#6E6E68"
  hairline: "#2E2E2A"
  hairline-strong: "#3D3D38"
  # Semantic accents — lightened for contrast on dark; meanings unchanged
  primary: "#5FA653"        # leaf green — confirm / start / success
  on-primary: "#0C0C0B"     # dark text on luminous green
  error: "#E0673E"          # blood orange — destructive / error
  notice: "#E9B53D"         # honey yellow — notice / bookmark
  notice-tint: "#2A2415"    # dark amber wash for notice surfaces
typography:
  display:
    fontFamily: Helvetica Neue
    fontSize: 28px
    fontWeight: 700
    lineHeight: 1
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Helvetica Neue
    fontSize: 19px
    fontWeight: 700
    lineHeight: 1.1
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Helvetica Neue
    fontSize: 17px
    fontWeight: 700
    lineHeight: 1.2
    letterSpacing: -0.01em
  body-lg:
    fontFamily: Helvetica Neue
    fontSize: 15px
    fontWeight: 400
    lineHeight: 1.45
  body-md:
    fontFamily: Helvetica Neue
    fontSize: 14px
    fontWeight: 400
    lineHeight: 1.45
  body-sm:
    fontFamily: Helvetica Neue
    fontSize: 13px
    fontWeight: 400
    lineHeight: 1.45
  label-row:
    fontFamily: Helvetica Neue
    fontSize: 15px
    fontWeight: 500
    lineHeight: 1.2
  label-value:
    fontFamily: Helvetica Neue
    fontSize: 15px
    fontWeight: 700
    lineHeight: 1.2
    fontFeature: "'tnum' 1"
  label-action:
    fontFamily: Helvetica Neue
    fontSize: 15px
    fontWeight: 700
    lineHeight: 1
    letterSpacing: 0.01em
  label-caps:
    fontFamily: Helvetica Neue
    fontSize: 12px
    fontWeight: 700
    lineHeight: 1
    letterSpacing: 0.08em
  caption:
    fontFamily: Helvetica Neue
    fontSize: 12px
    fontWeight: 400
    lineHeight: 1.4
  tab:
    fontFamily: Helvetica Neue
    fontSize: 11px
    fontWeight: 500
    lineHeight: 1
rounded:
  none: 0px
  sm: 3px
  md: 4px
  lg: 8px
  full: 9999px
spacing:
  base: 8px
  half: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  pad: 16px            # comfortable container padding
  pad-compact: 12px    # compact density
  row-pad: 14px        # comfortable list-row vertical padding
  row-pad-compact: 9px # compact density
  hit-target: 44px     # minimum interactive size
  canvas-width: 390px  # mobile design canvas
components:
  button-primary:
    backgroundColor: "{colors.primary}"
    textColor: "{colors.on-primary}"
    typography: "{typography.label-action}"
    rounded: "{rounded.md}"
    height: 48px
    padding: 24px
  button-primary-hover:
    backgroundColor: "#37692D"   # primary mixed 88% with black
  button-secondary:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
    rounded: "{rounded.md}"
    height: 48px
    padding: 24px
  button-secondary-hover:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
  button-destructive:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.error}"
    rounded: "{rounded.md}"
    height: 48px
    padding: 24px
  button-destructive-hover:
    backgroundColor: "{colors.error}"
    textColor: "#FFFFFF"
  quick-add:
    backgroundColor: "{colors.primary}"
    textColor: "{colors.on-primary}"
    rounded: "{rounded.full}"
    height: 44px
    width: 44px
  list-item:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
    padding: 16px
    height: 56px
  list-item-hover:
    backgroundColor: "#FAFAF7"
  card:
    backgroundColor: "{colors.surface}"
    rounded: "{rounded.md}"
    padding: 16px
  input:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
    typography: "{typography.body-lg}"
    rounded: "{rounded.md}"
    height: 48px
    padding: 14px
  input-focus:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
  input-error:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
  notice:
    backgroundColor: "{colors.notice-tint}"
    textColor: "{colors.ink}"
    typography: "{typography.body-sm}"
    rounded: "{rounded.md}"
    padding: 12px
  segmented-control:
    backgroundColor: "#E9E9E4"
    textColor: "{colors.ink-2}"
    rounded: "{rounded.md}"
    height: 36px
    padding: 2px
  segmented-control-active:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
  empty-state:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
    rounded: "{rounded.md}"
    padding: 32px
  empty-state-mark:
    textColor: "{colors.hairline-strong}"
    height: 44px
    width: 44px
  empty-state-title:
    typography: "{typography.headline-lg}"
    textColor: "{colors.ink}"
  empty-state-body:
    typography: "{typography.body-md}"
    textColor: "{colors.ink-2}"
  step-item:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink}"
    padding: 14px
  step-number:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.ink-2}"
    rounded: "{rounded.full}"
    height: 22px
    width: 22px
---

# BeFair Design System

A self-contained design system for **BeFair**, a mobile app that reveals the
true cost of what you own — *price ÷ wears* for clothing, *price ÷ months owned*
for tools. The system is named for its discipline: **Digital Functionalism**, a
Dieter Rams "less, but better" adaptation in which every element earns its place
and color is never decorative.

## Overview

BeFair feels **honest, calm, and engineered**. It is the antithesis of the
gamified spending app: no confetti, no dopamine gradients, no manipulative
nudges. The interface recedes so the numbers can speak.

- **Personality.** Quiet, precise, trustworthy. A well-made measuring tool
  rather than a toy. The UI should feel like matte aluminium and good paper.
- **Audience.** Considered consumers, minimalists, and cost-per-wear thinkers
  who want to buy less and use things longer.
- **Emotional response.** Reassurance through clarity. The user should feel
  *in control of the facts*, never judged or sold to.
- **Density.** Spacious by default (comfortable), with a compact density
  option for power users. Generous whitespace; one idea per region.
- **Restraint.** Achromatic by default. Color appears only where it changes
  meaning — confirm, destroy, or notice. If color is removed, the layout must
  still be fully legible through structure and weight alone.

## Colors

The palette is rooted in warm achromatic neutrals — never pure white or pure
black — structured entirely by hairlines. Three semantic accents are the only
chromatic notes, and each one *means* something.

- **Ink (#161614):** A warm matte near-black for all primary text and the brand
  mark. Provides permanence and maximum readability without the harshness of
  pure black.
- **Ink-2 (#6F6F69) / Ink-3 (#9C9C95):** Stone greys for secondary metadata and
  tertiary hints — the descending voice of the type hierarchy.
- **Background (#F5F5F2):** A soft warm off-white; the foundation of every
  screen. **Surface (#FFFFFF)** is reserved for content that lifts above it —
  cards, lists, inputs. **Canvas (#DBDBD7)** is the darker matte behind the
  device frame.
- **Hairline (#E4E4DE) / Hairline-strong (#CFCFC8):** The structural language.
  Separation is drawn with 1px hairlines, not shadows or fills.
- **Primary — Leaf Green (#3E7C33):** The single affirmative accent. Used only
  for the primary action of a screen, confirmation, success, and the +1 quick
  control. Never decorative.
- **Error — Blood Orange (#C9431A):** Destructive and error states only —
  delete actions, validation failures, remove buttons.
- **Notice — Honey Yellow (#E2A416):** Advisory only — notices, tips, bookmarks.
  Paired with its tint (#FBF3DE) for notice surfaces.

> When the system's accent is set to *reduced*, the leaf green retreats to
> neutral surfaces with ink text, proving the hierarchy holds without color.

### Design Tokens

```
colors:
  canvas: "#DBDBD7"
  background: "#F5F5F2"
  surface: "#FFFFFF"
  ink: "#161614"
  ink-2: "#6F6F69"
  ink-3: "#9C9C95"
  hairline: "#E4E4DE"
  hairline-strong: "#CFCFC8"
  primary: "#3E7C33"
  on-primary: "#FFFFFF"
  error: "#C9431A"
  notice: "#E2A416"
  notice-tint: "#FBF3DE"
```

### Dark Mode

Dark mode is **not an inversion** — it is the same Digital Functionalism logic
re-grounded on warm charcoal. Three principles govern it:

- **Tonal layering inverts.** In light mode surfaces lift toward white; in dark
  mode the background recedes to a warm charcoal (#161614) and surfaces step
  *lighter* (#1F1F1C → #272723). Depth still reads through tone and hairlines —
  no new shadows are introduced.
- **Neutrals stay warm.** Nothing is pure #000 or #FFF. Charcoals carry the same
  warm tint as the light neutrals, so the system feels matte, not inky.
- **Accents lift, meanings hold.** Leaf green, blood orange, and honey yellow are
  raised ~15–20% in lightness to clear 4.5:1 contrast on dark surfaces without
  becoming neon. Their roles never change. Because the lightened leaf green is
  luminous, **`on-primary` flips to near-black** for cleaner button text, and
  `notice-tint` becomes a dark amber wash rather than pale cream.

The *reduced-accent* mode applies identically: leaf green retreats to neutral
surfaces with ink text, and the hierarchy must remain legible with color removed.

### Design Tokens

```
colors-dark:
  canvas: "#0C0C0B"
  background: "#161614"
  surface: "#1F1F1C"
  surface-2: "#272723"
  ink: "#EDEDE8"
  ink-2: "#A3A39C"
  ink-3: "#6E6E68"
  hairline: "#2E2E2A"
  hairline-strong: "#3D3D38"
  primary: "#5FA653"
  on-primary: "#0C0C0B"
  error: "#E0673E"
  notice: "#E9B53D"
  notice-tint: "#2A2415"
```

## Typography

A single neo-grotesque — **Helvetica Neue** (falling back through Liberation
Sans / Arial) — carries the entire system. Hierarchy is built from size and
weight, not from competing typefaces. Only two weights do real work: Regular
(400) for prose, Bold (700) for emphasis, with Medium (500) for list-row names.

- **Display & headlines.** Bold with tight negative tracking (-0.02em → -0.01em)
  for statistics, screen titles, and the cost numbers that are the product's
  reason to exist.
- **Body.** Regular 13–15px with relaxed line-height (~1.45) for legibility on
  small screens.
- **Labels (caps).** Field labels and section headers are 12px Bold, uppercase,
  with +0.08em tracking — the quiet structural signposting of the interface.
- **Values.** Numeric values use tabular figures (`font-feature: 'tnum'`) so
  columns of money and counts align cleanly.
- **Tabs.** 11px, Medium, switching to Bold when active.

### Design Tokens

```
typography:
  display:        { fontFamily: Helvetica Neue, fontSize: 28px, fontWeight: 700, lineHeight: 1,    letterSpacing: -0.02em }
  headline-lg:    { fontFamily: Helvetica Neue, fontSize: 19px, fontWeight: 700, lineHeight: 1.1,  letterSpacing: -0.01em }
  headline-md:    { fontFamily: Helvetica Neue, fontSize: 17px, fontWeight: 700, lineHeight: 1.2,  letterSpacing: -0.01em }
  body-lg:        { fontFamily: Helvetica Neue, fontSize: 15px, fontWeight: 400, lineHeight: 1.45 }
  body-md:        { fontFamily: Helvetica Neue, fontSize: 14px, fontWeight: 400, lineHeight: 1.45 }
  body-sm:        { fontFamily: Helvetica Neue, fontSize: 13px, fontWeight: 400, lineHeight: 1.45 }
  label-row:      { fontFamily: Helvetica Neue, fontSize: 15px, fontWeight: 500, lineHeight: 1.2 }
  label-value:    { fontFamily: Helvetica Neue, fontSize: 15px, fontWeight: 700, lineHeight: 1.2, fontFeature: "'tnum' 1" }
  label-action:   { fontFamily: Helvetica Neue, fontSize: 15px, fontWeight: 700, lineHeight: 1,   letterSpacing: 0.01em }
  label-caps:     { fontFamily: Helvetica Neue, fontSize: 12px, fontWeight: 700, lineHeight: 1,   letterSpacing: 0.08em }
  caption:        { fontFamily: Helvetica Neue, fontSize: 12px, fontWeight: 400, lineHeight: 1.4 }
  tab:            { fontFamily: Helvetica Neue, fontSize: 11px, fontWeight: 500, lineHeight: 1 }
```

## Layout

A **fluid single-column mobile layout** on a fixed **390px design canvas**,
governed by a strict **8dp grid** with a 4px half-step for micro-adjustments.
Vertical rhythm between regions is 16px; content scrolls within a fixed header
and bottom tab bar.

- **Structure.** Screen = fixed header (56px) · scrolling content · optional
  fixed tab bar. Content padding is 16px (comfortable) or 12px (compact).
- **Containment.** Related items are grouped into surface cards and hairline
  lists; never let unrelated content share a container.
- **Hit targets.** Every interactive element is **≥44px** in its smallest
  dimension. List rows are ≥56px tall.
- **Density.** A system-level *comfortable / compact* switch scales container
  and row padding only — type sizes and the 8dp grid never change.

### Design Tokens

```
spacing:
  base: 8px
  half: 4px
  md: 16px
  lg: 24px
  xl: 32px
  pad: 16px
  pad-compact: 12px
  row-pad: 14px
  row-pad-compact: 9px
  hit-target: 44px
  canvas-width: 390px
```

## Elevation & Depth

The system is **functionally flat**. Depth is conveyed through **tonal layers
and hairlines**, not shadows: the warm off-white background recedes, pure-white
surfaces advance, and 1px hairlines draw every boundary.

Shadows are permitted in exactly two places, both kept whisper-soft:
- the **device frame** lifting off the canvas — `0 16px 40px rgba(22,22,20,0.10)`;
- the **active segment** of a segmented control — `0 1px 2px rgba(22,22,20,0.08)`.

Modal **bottom sheets** rise from the bottom edge over a 32%-opacity ink scrim
with a brief 180ms ease-out — the only motion in the system beyond active-state
feedback.

## Shapes

The shape language is **architectural sharpness softened by a single hair of
radius**. Rectangular elements — cards, inputs, buttons, lists, list rows —
use a minimal **4px** corner. Containers that meet the device edge (the phone
frame, bottom sheets) step up to **8px**. Circular geometry (`full`) is reserved
for genuinely circular controls: the +1 quick-add button, remove buttons, the
profile avatar, and status dots.

### Design Tokens

```
rounded:
  none: 0px
  sm: 3px
  md: 4px
  lg: 8px
  full: 9999px
```

## Components

**Buttons.** 48px tall, 4px radius, Bold 15px label.
- *Primary* — leaf-green fill, white text; one per screen, for the affirmative
  action. Darkens on hover.
- *Secondary* — white surface, strong-hairline border, ink text; border darkens
  to ink on hover.
- *Destructive* — white surface with a half-strength orange border and orange
  text; inverts to a solid orange fill on hover.
- *Small* variant drops to 40px / 13px.

**Quick-add (+1).** A 44px circular leaf-green control for logging a wear/use in
one tap; scales to 0.94 on press. Under *reduced* accent it becomes a neutral
outlined circle.

**Lists & rows.** Surface cards with hairline dividers between items. Rows are
≥56px, lead with a name (Medium 15px) and meta (12px ink-2), and trail with a
tabular value + unit, status tick, or quick control. Rows tint #FAFAF7 on hover.

**Cards & stats.** White surface, 4px radius, 16px padding. Statistics use the
display/headline scale with the cost number as the hero; under *full* accent the
key stat value picks up leaf green.

**Inputs.** 48px, white surface, strong-hairline border, 4px radius. Border goes
ink on focus and orange in the error state, with an orange helper message below.

**Segmented control.** A #E9E9E4 track at 36px with a 2px inset; the active
segment lifts onto a white surface with Bold text and a faint shadow.

**Notice.** An advisory block on the honey-yellow tint with a yellow leading dot
and a yellow-mixed border — informational only, never an error.

**Tab bar.** Three-up, hairline top border, white surface. Inactive tabs are
ink-3 Medium 11px; the active tab is ink Bold.

**Empty state (no data).** The screen a freshly registered user lands on, before
anything exists to measure. A white surface card, 4px radius, 32px top padding,
centre-aligned: a 44px hairline-strong outline mark, a headline-lg welcome
(personalised with the user's first name), one body-md line of ink-2 explaining
what happens next, and a single full-width primary button. Followed by a
numbered "How it works" list — `step-item` rows with a 22px circular
hairline-outlined `step-number` — and a closing caption reassurance.

> **Never fake a statistic.** With no items, the stats card, attention notice,
> and activity list are *removed*, not zero-filled. "€0.00" or an empty chart
> would imply a measurement that hasn't happened. The empty state's job is to
> explain and offer the one action worth taking — nothing else.

### Design Tokens

```
components:
  button-primary:      { backgroundColor: "{colors.primary}", textColor: "{colors.on-primary}", rounded: "{rounded.md}", height: 48px, padding: 24px }
  button-primary-hover:{ backgroundColor: "#37692D" }
  button-secondary:    { backgroundColor: "{colors.surface}", textColor: "{colors.ink}", rounded: "{rounded.md}", height: 48px, padding: 24px }
  button-destructive:  { backgroundColor: "{colors.surface}", textColor: "{colors.error}", rounded: "{rounded.md}", height: 48px, padding: 24px }
  button-destructive-hover: { backgroundColor: "{colors.error}", textColor: "#FFFFFF" }
  quick-add:           { backgroundColor: "{colors.primary}", textColor: "{colors.on-primary}", rounded: "{rounded.full}", height: 44px, width: 44px }
  list-item:           { backgroundColor: "{colors.surface}", textColor: "{colors.ink}", padding: 16px, height: 56px }
  list-item-hover:     { backgroundColor: "#FAFAF7" }
  card:                { backgroundColor: "{colors.surface}", rounded: "{rounded.md}", padding: 16px }
  input:               { backgroundColor: "{colors.surface}", textColor: "{colors.ink}", rounded: "{rounded.md}", height: 48px, padding: 14px }
  notice:              { backgroundColor: "{colors.notice-tint}", textColor: "{colors.ink}", rounded: "{rounded.md}", padding: 12px }
  segmented-control:   { backgroundColor: "#E9E9E4", textColor: "{colors.ink-2}", rounded: "{rounded.md}", height: 36px, padding: 2px }
  segmented-control-active: { backgroundColor: "{colors.surface}", textColor: "{colors.ink}" }
  empty-state:         { backgroundColor: "{colors.surface}", textColor: "{colors.ink}", rounded: "{rounded.md}", padding: 32px }
  empty-state-mark:    { textColor: "{colors.hairline-strong}", height: 44px, width: 44px }
  empty-state-title:   { typography: "{typography.headline-lg}", textColor: "{colors.ink}" }
  empty-state-body:    { typography: "{typography.body-md}", textColor: "{colors.ink-2}" }
  step-item:           { backgroundColor: "{colors.surface}", textColor: "{colors.ink}", padding: 14px }
  step-number:         { backgroundColor: "{colors.surface}", textColor: "{colors.ink-2}", rounded: "{rounded.full}", height: 22px, width: 22px }
```

## Do's and Don'ts

- **Do** use leaf green for exactly one primary action per screen — confirm,
  start, or +1.
- **Do** reserve blood orange for destruction and errors, and honey yellow for
  advisory notices — never swap their meanings.
- **Do** draw structure with hairlines and tonal layers before reaching for a
  shadow.
- **Do** keep every interactive target ≥44px and align all numbers with tabular
  figures.
- **Do** keep the layout fully legible with color removed (test against the
  *reduced* accent mode).
- **Don't** use pure #000 or #FFF for type or backgrounds — the neutrals are
  warm-tinted on purpose.
- **Don't** introduce a second typeface, a gradient, or a decorative accent
  color. Color must always carry meaning.
- **Don't** add a third font weight to a single screen; size and the existing
  weights are enough.
- **Don't** mix the 4px content radius with sharp 0px corners in the same view.
- **Don't** gamify — no confetti, badges, streak fireworks, or manipulative
  nudges. The numbers are the reward.
- **Don't** fill an empty state with zeroed stats, placeholder charts, or sample
  data. Remove the measurement and explain the next step instead.
