<p align="center">
  <img src="dist/icon-512.png" width="120" alt="Recipe Lab icon: a 35mm film strip and a green CRT">
</p>

<h1 align="center">Recipe Lab · Cassette Futurism edition</h1>

<p align="center">
  A <b>Cassette Futurism</b> green-screen theme for the Sony PlayMemories camera app<br>
  <sub>81 recipes · live preview · write a look straight into the camera · English / 简体 / 繁體 / 日本語 / 한국어 / Tiếng Việt</sub><br>
  <sub>Based on the latest code of <a href="https://github.com/voxivoid/recipe-lab-sony-pmca">voxivoid/recipe-lab-sony-pmca</a> (GPL-3.0) · unofficial branch</sub>
</p>

> **Companion desktop tool: [Recipe Lab Studio](https://github.com/jjingith/recipe-lab-studio-sony-pmca)**
> Build recipes on a PC instead of dialling them in by hand on the camera. It fits a **custom recipe** from a
> `.cube` LUT, a set of photographs, or a same-position "before + after" pair; previews the result and its
> residual; keeps a PC master library; and pushes recipes to the camera card's `RECIPES\` folder (backing up
> before the write, then reading the file back to verify it). Same three languages, **permanently free**.
> Note: it writes **custom recipes**, so the camera must run a build that has the custom-recipe feature
> (this branch or the upstream original) — older builds cannot see them.

---

**Contents**

- [Notice](#notice)
- [What it is](#what-it-is)
- [What this branch adds](#what-this-branch-adds)
- [Screenshots](#screenshots)
- [The added filters](#the-added-filters)
- [Compatibility](#compatibility)
- [Download and install](#download-and-install)
- [How to use](#how-to-use)
- [Building from source](#building-from-source)
- [Differences from upstream](#differences-from-upstream)
- [Known limits](#known-limits)
- [Credits](#credits)
- [Licence](#licence)
- [Tip](#tip)

---

## Notice

- **[jjingith/recipe-lab-sony-pmca-cn](https://github.com/jjingith/recipe-lab-sony-pmca-cn) is the only original and maintained repository for this branch.** The Cassette Futurism theme, the six-language UI, the 81 recipes and the build scripts all live here, and every future update is published here only.
- **This branch is a Chinese localisation and derivative of [voxivoid/recipe-lab-sony-pmca](https://github.com/voxivoid/recipe-lab-sony-pmca) (GPL-3.0).** The upstream author's and contributors' copyright notices and credits are kept in full — see [Credits](#credits) and [Licence](#licence). Upstream was MIT up to and including v1.7.0 and moved to GPL-3.0 with a 7(b) attribution term from v1.8.0 on.
- **Forking, modifying and redistributing are welcome** (GPL-3.0 allows it), but please keep a link to this repository, the upstream attribution and the copyright notice. Do not strip the GitHub `forked from` label, and do not present your copy as the original.
- **An unmodified copy under another account is only a mirror.** Official builds and Releases are published here only; same-named repositories under other accounts that drop the attribution are not mine and do not represent my updates or support.
- **The project is permanently free and open source under GPL-3.0.** Anyone may use, modify and redistribute it under GPL-3.0 (which also permits distribution for a fee). I do not offer a paid edition, an unlock or a licensing channel, and I charge nothing for this project apart from the [Tip](#tip) link at the end of this README.

---

## What it is

An app that runs **on the Sony cameras that support PlayMemories Camera Apps (PMCA)**. Turn the control wheel to browse **81 film recipes** (Fuji, Kodak, Leica, Hasselblad, Ricoh GR, cine stocks, Ilford and more) with a live preview, then press the centre button to **write the look into the camera for good** — it stays in effect in P/A/S/M, stills and video, across a power cycle.

- **No firmware, no jailbreak**: it only writes settings you could change by hand in the camera menus
- **Fully reversible**: TRASH in the app restores the factory colour
- **Live preview**: turn the wheel and watch the frame change before you commit
- **Six languages**: English / Simplified / Traditional Chinese / Japanese / Korean / Vietnamese, switchable in the app menu; `Auto` follows the camera's locale

## What this branch adds

| | |
| --- | --- |
| **Theme** | The whole UI is redrawn as **Cassette Futurism**: a green phosphor monitor (`#74FF9A`) on black, amber label plates (`#FFB23E`), CRT scanlines, square hardware panels, a status LED beside each title, and inverse-video selection (green on black flipped to black on green). Rounded corners, frosted glass and gradient blooms are gone; red appears only on warning badges |
| **Fonts** | English is drawn in the **monospace terminal face** `Droid Sans Mono` and Chinese in `Droid Sans Fallback` (both cut down to what the screens use, Apache-2.0). Both are metrically matched to the camera's own font, so mixed Chinese/Latin lines no longer jump in size |
| **Icon** | A **35mm film strip** (sprocket holes, three frames) holding a **green CRT**, on a charcoal chassis with an amber bezel, corner screws and a status LED |
| **Extra filters** | **4 new recipes** on top of the original 77 (see [below](#the-added-filters)), taking the total to **81** |
| **Localisation** | Menus, prompts, the brand browser, the parameter row and the key hints are all in Chinese, using Sony's official Chinese menu wording for Creative Style and Picture Effect |

Only the runtime keys handed to the camera HAL (such as `standard`, `toy-camera`) stay English — they are protocol values and must not be translated.

## Screenshots

Shot on an **A7R II (ILCE-7RM2)** running this build: the green terminal theme, the monospace face and Chinese text all render correctly.

| Brand browser (Kodak) | Recipe list (with a favourite) | Panel · live preview |
| --- | --- | --- |
| [<img src="docs/screenshots/01-brand-browser-kodak.jpg" width="330">](docs/screenshots/01-brand-browser-kodak.jpg) | [<img src="docs/screenshots/02-recipe-list.jpg" width="330">](docs/screenshots/02-recipe-list.jpg) | [<img src="docs/screenshots/03-panel-live-preview.jpg" width="330">](docs/screenshots/03-panel-live-preview.jpg) |

- **Left**: the brand browser — Chinese groups and counts on the left (Custom / Sony / Fuji Sim / Fuji Film / Kodak 15 / Cine / Ricoh GR / Leica / Hasselblad…), the recipe list on the right, and the key hints along the bottom
- **Middle**: the recipe list starting at `Kodak Portra 160`, with a star on the favourite
- **Right**: the panel previewing `Kodak Kodachrome 64` (38 / 81, badge "已启用" / active) with the parameter row — quality RAW+JPEG, style, saturation +3, contrast +2, sharpness +1, effect off, WB auto

## The added filters

Four recipes added on top of the official 77, calibrated against the spectral response of the stock they copy:

| Recipe | Group | What it is |
| --- | --- | --- |
| **Kodak 2383 print** | Cine | Vision3 50D negative through a 2383 print, restrained teal-and-orange, cool shadows |
| **Kodak Aerocolor IV 2460** | Kodak | Aerial C-41: very clean, fine grain, a faint cyan cast, soft highlights |
| **Foma Pan 100** | Other | Classic black and white: fine grain, deep blacks, clean tonality |
| **Tatsuo Suzuki B&W** | Other | Gritty Tatsuo Suzuki street black and white: max contrast +3, sharpness +1, −0.3 EV, DRO off |

> If you prefer the camera's own hard-contrast monochrome, switch the effect to Sony's **High Contrast Mono (HC)**.

## Compatibility

**Verified** on an **A7R II (ILCE-7RM2, firmware 4.00 / 4.01)**. Every model the original project supports works too — A7R, A7 II, A7, A6000, A6500, A6300, A5100, NEX-5T, RX100 V, HX60 and more (see the [upstream README](docs/UPSTREAM-README.md)).

**Not supported**: cameras released after late 2016 (A7 III and later, A9/A1, the later A6x00, ZV, FX, RX100 VA+, RX10 IV, RX0, HX99…). Their firmware is signed, there is no `MENU → Application` entry, and no camera app can be installed on them.

## Download and install

1. Download `RecipeLab.apk` from this repository's **[Releases](../../releases)**
2. Download the installer **[Sony-PMCA-RE](https://github.com/ma1co/Sony-PMCA-RE/releases)** (`pmca-gui-v0.18-win.exe` on Windows)
3. On the camera: `MENU → Setup → USB Connection → Mass Storage`, with a card in and the camera on
4. Connect over USB, run pmca-gui, click **Install app from file** and pick `RecipeLab.apk`
5. The camera screen going black or flickering during the install is normal — go by the computer's **"Task completed successfully"** and do not unplug
6. Unplug, **power-cycle the camera**, then `MENU → Application → Application List → Recipe Lab`

## How to use

| Control | Action |
| --- | --- |
| Control wheel / dial | Browse recipes (live preview) |
| Fn | Open the brand list (the first entry is Favourites) |
| Centre button (short) | **Select** the recipe and write it to the camera |
| Centre button (hold) | Favourite / unfavourite |
| AEL / DISP | Cycle the panel: full → no keys → hidden |
| TRASH | Restore the factory colour |
| MENU | Quit the app |
| C1 | Developer menu (settings snapshot diff, read-only checks, sample run) |

> **Important**: after selecting a recipe, power-cycle the camera so the look becomes the default in every mode.

## Building from source

You need a JDK (`javac --release 8`), Android SDK build-tools **30.0.3** and the **android-28** `android.jar`.

On Windows this branch ships a one-shot script (edit the paths inside it if yours differ):

```cmd
build-local.cmd
```

It runs the seven steps of `build.cmd`: `ndk-build → aapt → javac → d8 → aapt package → zipalign → apksigner`, and writes `RecipeLab.apk`.

**Build notes**: keep the build path ASCII (`aapt` cannot load an `android.jar` from a non-ASCII path); `-A assets` is required or the bundled fonts are left out; any self-signed key will do (the camera only checks the v1 / JAR signature).

After editing a text table, rebuild the font subsets:

```
python3 tools/subset-font.py
```

## Differences from upstream

| | Upstream | This branch |
| --- | --- | --- |
| Package | `com.voxivoid.recipelab` | `com.lutlab.pmca` |
| Version | 1.9.0 | **1.7.0** |
| Recipes | 77 | **81** (+4 filters) |
| Theme | native dark | **Cassette Futurism green screen** |
| Fonts | system | **bundled Droid Sans Mono / Droid Sans Fallback** |
| Languages | English / Simplified / Traditional Chinese / Japanese / Korean / Vietnamese | **the same, re-localised with Sony's official Chinese menu terms** |

> The package name differs, so this build installs **side by side** with the original without replacing it.

## Known limits

- A recipe is an **approximation** of another brand's colour from the parameters a camera can store, not a copy of its colour science
- The camera stores no tone curve, so log curves (S-Log, V-Log, CineLike D) and toned monochromes (selenium, cyanotype) cannot be done
- The settings slot ids were reverse-engineered on an A6000 and may sit elsewhere on untested bodies
- Picture Effect recipes exclude RAW (the camera ignores the effect), so the app switches to JPEG by itself

## Credits

- **[ma1co](https://github.com/ma1co)** — the PlayMemories platform reverse-engineering (Sony-PMCA-RE, OpenMemories-Platform, OpenMemories-Tweak); none of this exists without that work
- **[voxivoid](https://github.com/voxivoid)** — the author of Recipe Lab
- **AOSP Droid fonts** — the bundled `Droid Sans Mono` and `Droid Sans Fallback` subsets (Apache-2.0), from the same family as the camera's own font

## Licence

- **GNU GPL-3.0**, following upstream — see [LICENSE](LICENSE). Upstream was MIT up to and including v1.7.0 and moved to GPL-3.0 from v1.8.0 on.
- Under upstream's additional GPL-3.0 term **7(b)**, any copy or modified version must keep the attribution **“Based on Recipe Lab by André Domingues”** in its source notices and, where there is an interactive interface, show it in the Appropriate Legal Notices. This branch complies.
- The bundled fonts are subsets of Droid Sans Mono / Droid Sans Fallback, **Apache License 2.0** — provenance in [assets/fonts/README.txt](assets/fonts/README.txt)
- The original English README is kept at [docs/UPSTREAM-README.md](docs/UPSTREAM-README.md)

---

## Tip

If this made your camera a little nicer to use, you can buy me a coffee on [Afdian](https://afdian.com/a/jiajun2160) ☕.

Tips are entirely voluntary and **do not buy extra features or priority support** — the software is the same for everyone, and permanently free.

Thank you!
