package com.lutlab.pmca;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;

/**
 * The chrome the XP themes add to the interface. The res/drawable shapes are the CRT look written in XML and cannot
 * follow a theme switch, so the panel, pills, keys and badges are built here in Java from the live palette, and the
 * overlays get a Luna title band. Everything is API 1 — GradientDrawable, LinearGradient, Path.addRoundRect — and the
 * callers attach it with setBackgroundDrawable: setBackground or a tint would crash the Android 2.3.7 camera this runs
 * on. Chrome reads {@link Theme}, so a caller rebuilds these whenever {@link Theme#version()} changes.
 */
final class Chrome {
    private Chrome() {}

    /** the same colour at another alpha: the pane colours are one slot each, read at different strengths */
    private static int tint(int color, int alpha) { return (color & 0x00FFFFFF) | (alpha << 24); }

    private static int px(float d, float dp) { return (int) (dp * d + 0.5f); }

    private static GradientDrawable rounded(float d, int fill, int strokeColor, float strokeDp, float radiusDp) {
        GradientDrawable g = new GradientDrawable();
        g.setShape(GradientDrawable.RECTANGLE);
        g.setColor(fill);
        if (strokeColor != 0) g.setStroke(px(d, strokeDp), strokeColor);
        if (radiusDp > 0) g.setCornerRadius(radiusDp * d);
        return g;
    }

    /** a value key at rest: a plain key cap. XP draws its edge so a white key is still a key on a pale panel */
    static GradientDrawable chip(float d) {
        return rounded(d, Theme.ACCENT_SOFT, Theme.crt() ? 0 : Theme.CHASSIS, 1, 2);
    }

    /** a value key under the highlight: lit in inverse video */
    static GradientDrawable chipSel(float d) { return rounded(d, Theme.ACCENT, 0, 0, 2); }

    /** a value key holding the recipe's own value, not the camera's: an amber key */
    static GradientDrawable chipHi(float d) { return rounded(d, tint(Theme.AMBER, 0x33), Theme.AMBER, 1.5f, 2); }

    /** a badge lamp: a tinted face ringed in the colour it names */
    static GradientDrawable badge(float d, int color) { return rounded(d, tint(color, 0x33), color, 1, 2); }

    /** the minimised recipe pill: a plate with an amber border, like a label on the body */
    static GradientDrawable pill(float d) { return rounded(d, tint(Theme.GROUND, 0xE6), tint(Theme.AMBER, 0xB3), 1, 3); }

    /** a transient status: a plate at the top of the screen */
    static GradientDrawable toast(float d) { return rounded(d, tint(Theme.GROUND, 0xF0), tint(Theme.AMBER, 0xB3), 1, 3); }

    /** the panel's ground: the live image shows through the top, the readouts sit on the ground below */
    static GradientDrawable panel(float d) {
        GradientDrawable g = new GradientDrawable();
        g.setShape(GradientDrawable.RECTANGLE);
        g.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        g.setColors(new int[] { tint(Theme.GROUND, 0x00), tint(Theme.GROUND, 0xC4), tint(Theme.GROUND, 0xF0) });
        g.setStroke(px(d, 1), tint(Theme.EDGE, 0x80));
        return g;
    }

    private static final Paint BAR = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Path BAR_PATH = new Path();
    private static final RectF BAR_RECT = new RectF();
    private static final float[] BAR_RADII = new float[8];
    private static final Paint SUNKEN = new Paint(Paint.ANTI_ALIAS_FLAG);

    /** an XP title band across the top of an overlay: a Luna gradient, a dark edge, rounded top corners */
    static void titleBar(Canvas c, float w, float h, float d) {
        float r = Math.min(6 * d, h / 2);
        for (int i = 0; i < 8; i++) BAR_RADII[i] = i < 4 ? r : 0;   // the top two corners round, the bottom square
        BAR_RECT.set(0.5f, 0.5f, w - 0.5f, h);
        BAR_PATH.reset();
        BAR_PATH.addRoundRect(BAR_RECT, BAR_RADII, Path.Direction.CW);
        BAR.setStyle(Paint.Style.FILL);
        BAR.setShader(new LinearGradient(0, 0, 0, h, Theme.TITLE_TOP, Theme.TITLE_BOTTOM, Shader.TileMode.CLAMP));
        c.drawPath(BAR_PATH, BAR);
        BAR.setShader(null);
        BAR.setColor(Theme.TITLE_EDGE);
        BAR.setStyle(Paint.Style.STROKE);
        BAR.setStrokeWidth(d);
        c.drawPath(BAR_PATH, BAR);
        BAR.setStyle(Paint.Style.FILL);
    }

    /** an XP text box: a ground-coloured inset ringed in the edge colour, so the field's own text colour still reads */
    static void sunken(Canvas c, RectF r, float d) {
        SUNKEN.setStyle(Paint.Style.FILL);
        SUNKEN.setColor(Theme.MODAL);
        c.drawRect(r, SUNKEN);
        SUNKEN.setStyle(Paint.Style.STROKE);
        SUNKEN.setStrokeWidth(d);
        SUNKEN.setColor(Theme.EDGE);
        c.drawRect(r, SUNKEN);
    }
}
