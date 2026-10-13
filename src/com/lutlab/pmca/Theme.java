package com.lutlab.pmca;

/**
 * The one palette the whole interface draws from: a black CRT ground, the phosphor green its text is written in, an
 * amber the panel labels and read-outs are pressed in, and the few greys between them. Views used to carry their own
 * colours; a theme change then meant editing every one of them, and they drifted apart. Everything here is a plain int,
 * so a view can name a colour without reaching for android.*.
 *
 * The look it describes is cassette futurism: the overlays are the black screen of a green-phosphor tube, a selected
 * row is lit in inverse video (green behind, black in front), panel labels and values are amber like the lettering on a
 * hardware plate, and the only red is the warning — a rust orange, never a neon.
 */
final class Theme {
    private Theme() {}

    // ---- the grounds
    /** the near-opaque ground an overlay covers the screen with: a green-black tube */
    static final int GROUND = 0xF0080E09;
    /** the opaque ground of a full-screen overlay (the browser, the menus, the editor) */
    static final int MODAL = 0xFF050906;
    /** plastic in the gap between the modules: a dark olive chassis */
    static final int CHASSIS = 0xFF20261C;
    /** the outline of the modal box and the minimised pill */
    static final int EDGE = 0x99FFB23E;

    // ---- the phosphor
    static final int ACCENT = 0xFF74FF9A;        // the green: a highlight, a selection, a mark
    static final int ACCENT_DIM = 0x9974FF9A;    // its outline, and a rhythm that should not shout
    static final int ACCENT_SOFT = 0x2E74FF9A;   // a filled but unselected key
    static final int INK = 0xFF06120A;           // text laid on the green (inverse video)
    static final int INK_SOFT = 0xCC06120A;      // the same, a little quieter

    // ---- text on the tube
    static final int TEXT = 0xFFD0FFDA;          // the soft phosphor the body text glows in
    static final int TEXT_DIM = 0xCC8FE6A6;      // a detail line under a name
    static final int TEXT_FAINT = 0x9987B69A;    // a hint, a count, an icon
    static final int RULE = 0x4074FF9A;          // the hairline that divides

    // ---- the amber of a pressed label plate
    static final int AMBER = 0xFFFFB23E;         // a panel label, a value, a count
    static final int AMBER_DIM = 0x80FFB23E;     // the same, a little quieter
    static final int BEIGE = 0xFFCFC5A8;         // the beige plastic of a label plate

    // ---- the two badges the panel carries, and the warning
    static final int BADGE_OK = 0xFF74FF9A;      // ACTIVE: the green lamp
    static final int BADGE_EDITED = 0xFFFFB23E;  // EDITED: stored, but not the recipe itself
    static final int BADGE_WARN = 0xFFE0603A;    // PREVIEW: not stored yet
    static final int WARN_TEXT = 0xFFFF6B4A;     // a rejected name, an error line — the one red
    static final int TAG_BG = 0x55FFB23E;        // the JPEG-only tag on a recipe row: amber-tinted
    static final int TAG_BG_ON = 0x33061208;     // the same tag with the row lit underneath it

    // ---- scrollbars, the scanline veil, and the third-party marks
    static final int TRACK = 0x1F74FF9A;
    static final int THUMB = 0xCC74FF9A;
    static final int SCAN = 0x26000000;          // a scanline over the phosphor
    static final int STAR = 0xFFFFB23E;
    static final int HEART = 0xFFFFB23E;
}