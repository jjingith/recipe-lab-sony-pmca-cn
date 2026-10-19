package com.lutlab.pmca;

/**
 * The one palette the whole interface draws from, in three sets: the cassette-futurism green tube the app shipped with
 * ({@link #CRT}), and two Windows XP looks — Luna light and a dark variant. A view names a colour slot; a theme switch
 * rewrites every slot and bumps {@link #version()}, which every view checks in its draw before it restyles the paints
 * it captured at construction. Everything here is a plain int, so a view can name a colour without reaching for
 * android.*, and the palette can be exercised off the camera by tools/test.sh.
 *
 * The default look it describes is cassette futurism: the overlays are the black screen of a green-phosphor tube, a
 * selected row is lit in inverse video (green behind, black in front), panel labels and values are amber like the
 * lettering on a hardware plate, and the only red is the warning — a rust orange, never a neon. The XP looks invert
 * that: a light (or dark) window ground, dark (or light) text, and a selected row lit in one accent colour with the
 * lettering it needs in front — the Luna selection, which is why {@link #TAG_BG_ON} and {@link #EDGE} cannot simply
 * scale with the rest. The pale window takes the amber the plate lettering already uses, with dark lettering on its lit
 * row; the dark one keeps the Luna blue.
 */
final class Theme {
    private Theme() {}

    // ------------------------------------------------------------ the themes
    /** the themes, in menu order */
    static final int CRT = 0, XP_LIGHT = 1, XP_DARK = 2, COUNT = 3, DEFAULT = CRT;
    /** how a choice is kept in the app's preferences: by code, so a new theme never shifts a stored one */
    static final String[] CODES = { "crt", "xp-light", "xp-dark" };
    /** the lettering on an XP overlay's title band: white on the Luna gradient, the same in both XP variants */
    static final int TITLE_INK = 0xFFFFFFFF;
    /** the Luna gradient of that band, dark blue to light blue */
    static final int TITLE_TOP = 0xFF0054E3, TITLE_BOTTOM = 0xFF3A93FF, TITLE_EDGE = 0xFF003C74;

    // the slots, in the order a PALETTES row is written
    private static final int S_GROUND = 0, S_MODAL = 1, S_CHASSIS = 2, S_EDGE = 3,
            S_ACCENT = 4, S_ACCENT_DIM = 5, S_ACCENT_SOFT = 6, S_INK = 7, S_INK_SOFT = 8,
            S_TEXT = 9, S_TEXT_DIM = 10, S_TEXT_FAINT = 11, S_RULE = 12,
            S_AMBER = 13, S_AMBER_DIM = 14, S_BEIGE = 15,
            S_BADGE_OK = 16, S_BADGE_EDITED = 17, S_BADGE_WARN = 18, S_WARN_TEXT = 19,
            S_TAG_BG = 20, S_TAG_BG_ON = 21, S_TRACK = 22, S_THUMB = 23,
            S_SCAN = 24, S_STAR = 25, S_HEART = 26;

    // ---- the grounds
    /** the near-opaque ground an overlay covers the screen with: a green-black tube */
    static int GROUND = 0xF0080E09;
    /** the opaque ground of a full-screen overlay (the browser, the menus, the editor) */
    static int MODAL = 0xFF050906;
    /** plastic in the gap between the modules: a dark olive chassis */
    static int CHASSIS = 0xFF20261C;
    /** the outline of the modal box and the minimised pill */
    static int EDGE = 0x99FFB23E;

    // ---- the phosphor
    static int ACCENT = 0xFF74FF9A;        // the green: a highlight, a selection, a mark
    static int ACCENT_DIM = 0x9974FF9A;    // its outline, and a rhythm that should not shout
    static int ACCENT_SOFT = 0x2E74FF9A;   // a filled but unselected key
    static int INK = 0xFF06120A;           // text laid on the green (inverse video)
    static int INK_SOFT = 0xCC06120A;      // the same, a little quieter

    // ---- text on the tube
    static int TEXT = 0xFFD0FFDA;          // the soft phosphor the body text glows in
    static int TEXT_DIM = 0xCC8FE6A6;      // a detail line under a name
    static int TEXT_FAINT = 0x9987B69A;    // a hint, a count, an icon
    static int RULE = 0x4074FF9A;          // the hairline that divides

    // ---- the amber of a pressed label plate
    static int AMBER = 0xFFFFB23E;         // a panel label, a value, a count
    static int AMBER_DIM = 0x80FFB23E;     // the same, a little quieter
    static int BEIGE = 0xFFCFC5A8;         // the beige plastic of a label plate

    // ---- the two badges the panel carries, and the warning
    static int BADGE_OK = 0xFF74FF9A;      // ACTIVE: the green lamp
    static int BADGE_EDITED = 0xFFFFB23E;  // EDITED: stored, but not the recipe itself
    static int BADGE_WARN = 0xFFE0603A;    // PREVIEW: not stored yet
    static int WARN_TEXT = 0xFFFF6B4A;     // a rejected name, an error line — the one red
    static int TAG_BG = 0x55FFB23E;        // the JPEG-only tag on a recipe row: amber-tinted
    static int TAG_BG_ON = 0x33061208;     // the same tag with the row lit underneath it

    // ---- scrollbars, the scanline veil, and the third-party marks
    static int TRACK = 0x1F74FF9A;
    static int THUMB = 0xCC74FF9A;
    static int SCAN = 0x26000000;          // a scanline over the phosphor
    static int STAR = 0xFFFFB23E;
    static int HEART = 0xFFFFB23E;

    /** a slot value per theme, in the S_* order above: CRT, then XP light, then XP dark */
    private static final int[][] PALETTES = {
        {   // CRT: the cassette-futurism tube the app shipped with — the regression guard ThemeTest pins value by value
            0xF0080E09, 0xFF050906, 0xFF20261C, 0x99FFB23E,
            0xFF74FF9A, 0x9974FF9A, 0x2E74FF9A, 0xFF06120A, 0xCC06120A,
            0xFFD0FFDA, 0xCC8FE6A6, 0x9987B69A, 0x4074FF9A,
            0xFFFFB23E, 0x80FFB23E, 0xFFCFC5A8,
            0xFF74FF9A, 0xFFFFB23E, 0xFFE0603A, 0xFFFF6B4A,
            0x55FFB23E, 0x33061208, 0x1F74FF9A, 0xCC74FF9A,
            0x26000000, 0xFFFFB23E, 0xFFFFB23E,
        },
        {   // XP light: a Luna window — pale ground, dark text, the accent amber with dark lettering in front
            0xF0ECE9D8, 0xFFECE9D8, 0xFFD4D0C8, 0xFF0054E3,
            0xFFFFB23E, 0x99FFB23E, 0xFFFFFFFF, 0xFF1A1A1A, 0xCC1A1A1A,
            0xFF1A1A1A, 0xFF4A4A4A, 0xFF6E6E6E, 0x40808080,
            0xFF003C74, 0x80003C74, 0xFFCFC5A8,
            0xFF1E7B1E, 0xFF8A5A00, 0xFFC00000, 0xFFC00000,
            0xFFFFF0C0, 0x66FFFFFF, 0x1F000000, 0xCCFFB23E,
            0x00000000, 0xFFE8B23C, 0xFFE06AA0,
        },
        {   // XP dark: the same Luna geometry on a dark ground — light text, the selection blue a shade brighter
            0xF0212329, 0xFF21242A, 0xFF2E3238, 0xFF3A6EA5,
            0xFF2F6FC4, 0x992F6FC4, 0xFF343943, 0xFFFFFFFF, 0xCCFFFFFF,
            0xFFEDEDED, 0xFFC0C4CC, 0xFF9AA0A8, 0x405A7FB5,
            0xFF9CC4FF, 0x809CC4FF, 0xFF3A3F46,
            0xFF6CD06C, 0xFFE0A63C, 0xFFE0603A, 0xFFFF8A6A,
            0x55E8B23C, 0x33000000, 0x1F9CC4FF, 0xCC9CC4FF,
            0x00000000, 0xFFE8B23C, 0xFFE06AA0,
        },
    };

    private static int cur = DEFAULT;
    private static int ver = 0;

    /** the theme the interface is drawn in */
    static int current() { return cur; }
    /** whether the cassette-futurism look is on: the only one that draws the scanline veil */
    static boolean crt() { return cur == CRT; }
    /** the theme generation: a view captures this with its paints and restyles when it changes */
    static int version() { return ver; }

    /** switches the palette the whole interface draws from, and counts it as a new version so every view restyles */
    static void apply(int id) {
        cur = id >= 0 && id < COUNT ? id : DEFAULT;
        int[] p = PALETTES[cur];
        GROUND = p[S_GROUND]; MODAL = p[S_MODAL]; CHASSIS = p[S_CHASSIS]; EDGE = p[S_EDGE];
        ACCENT = p[S_ACCENT]; ACCENT_DIM = p[S_ACCENT_DIM]; ACCENT_SOFT = p[S_ACCENT_SOFT]; INK = p[S_INK]; INK_SOFT = p[S_INK_SOFT];
        TEXT = p[S_TEXT]; TEXT_DIM = p[S_TEXT_DIM]; TEXT_FAINT = p[S_TEXT_FAINT]; RULE = p[S_RULE];
        AMBER = p[S_AMBER]; AMBER_DIM = p[S_AMBER_DIM]; BEIGE = p[S_BEIGE];
        BADGE_OK = p[S_BADGE_OK]; BADGE_EDITED = p[S_BADGE_EDITED]; BADGE_WARN = p[S_BADGE_WARN]; WARN_TEXT = p[S_WARN_TEXT];
        TAG_BG = p[S_TAG_BG]; TAG_BG_ON = p[S_TAG_BG_ON]; TRACK = p[S_TRACK]; THUMB = p[S_THUMB];
        SCAN = p[S_SCAN]; STAR = p[S_STAR]; HEART = p[S_HEART];
        ver++;
    }

    // ------------------------------------------------------------ the menu choice
    /** the choices the Theme row steps through */
    static final int[] CHOICES = { CRT, XP_LIGHT, XP_DARK };
    /** the text key of each theme's name, in CHOICES order */
    private static final String[] LABEL_KEYS = { "theme_crt", "theme_xp_light", "theme_xp_dark" };

    /** the choice left / right lands on, wrapping */
    static int nextChoice(int choice, int dir) {
        int pos = 0;
        for (int i = 0; i < CHOICES.length; i++) if (CHOICES[i] == choice) pos = i;
        return CHOICES[(pos + CHOICES.length + dir) % CHOICES.length];
    }

    /** a stored preference back to a choice; anything unknown — nothing stored yet, a dropped theme — is CRT */
    static int parseChoice(String code) {
        for (int c = 0; c < CODES.length; c++) if (CODES[c].equals(code)) return c;
        return DEFAULT;
    }

    static String choiceCode(int choice) { return CODES[choice >= 0 && choice < COUNT ? choice : DEFAULT]; }

    /** the Theme row's value: the theme's name in the current display language */
    static String choiceLabel(int choice) { return Lang.t(LABEL_KEYS[choice >= 0 && choice < COUNT ? choice : DEFAULT]); }
}
