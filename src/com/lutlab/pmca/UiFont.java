package com.lutlab.pmca;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The typeface each display language ({@link Lang}) is drawn in, and how large its text sits. The camera's firmware
 * font carries only Latin, and the interface is a green-screen terminal: English and Vietnamese (Latin with diacritics)
 * are drawn in a bundled Droid Sans Mono, Chinese, Japanese and Korean in the bundled Droid Sans Fallback — the AOSP font
 * the camera's own Droid Sans pairs with — both cut down to what the tables use by tools/subset-font.py, one file per
 * script so each gets its own glyph forms. A font that will not load falls back to the camera's: the text is then boxes,
 * but the app still opens.
 *
 * {@link #setScale} makes every language's text a little smaller or larger than its layout asks for. A CJK glyph fills
 * its em box where a Latin one leaves room above and below, so the same point size looks bigger in Chinese; the views
 * ask {@link #scale} and shrink to match.
 */
final class UiFont {
    private UiFont() {}

    private static final String[] ASSET = {
        "fonts/RecipeLabMono-Regular.ttf",     // English
        "fonts/RecipeLabCJKsc-Regular.ttf",    // 简体中文
        "fonts/RecipeLabCJKtc-Regular.ttf",    // 繁體中文
        "fonts/RecipeLabCJKjp-Regular.ttf",    // 日本語
        "fonts/RecipeLabCJKkr-Regular.ttf",    // 한국어
        "fonts/RecipeLabMono-Regular.ttf",     // Tiếng Việt: Latin with diacritics, which Droid Sans Mono draws
    };
    private static final Typeface[] LOADED = new Typeface[Lang.COUNT];
    private static float scale = 1f;

    /** the typeface of a language, loaded the first time it is asked for */
    static Typeface of(Context c, int lang) {
        if (ASSET[lang] == null) return Typeface.DEFAULT;
        if (LOADED[lang] == null) {
            try { LOADED[lang] = Typeface.createFromAsset(c.getAssets(), ASSET[lang]); }
            catch (Throwable t) { LOADED[lang] = Typeface.DEFAULT; }
        }
        return LOADED[lang];
    }

    /** how large the current language's text is drawn, relative to what a layout asked for (1 in English) */
    static float scale() { return scale; }
    static void setScale(float s) { scale = s > 0 ? s : 1f; }

    /**
     * Every TextView under {@code v} in {@code tf}, each keeping the style and the size its layout gave it. Both are
     * kept in the view's tag the first time — because a typeface without a bold face reports plain once it is set, and
     * because {@link #setScale} is applied to the size the layout asked for, not to the one already scaled.
     */
    static void apply(View v, Typeface tf) {
        if (v instanceof TextView) {
            TextView t = (TextView) v;
            if (!(t.getTag() instanceof int[]))
                t.setTag(new int[] { t.getTypeface() == null ? Typeface.NORMAL : t.getTypeface().getStyle(), (int) t.getTextSize() });
            int[] kept = (int[]) t.getTag();
            t.setTypeface(tf, kept[0]);
            t.setTextSize(TypedValue.COMPLEX_UNIT_PX, kept[1] * scale);
        }
        if (v instanceof ViewGroup) {
            ViewGroup g = (ViewGroup) v;
            for (int i = 0; i < g.getChildCount(); i++) apply(g.getChildAt(i), tf);
        }
    }
}
