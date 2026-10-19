package com.lutlab.pmca;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * The texture of the tube: a scanline veil drawn over a full-screen overlay once everything else is on it. Only the
 * overlays that cover the live image call this — on the panel, which the viewfinder shows through, the lines would
 * land on the picture.
 */
final class Crt {
    private Crt() {}

    private static final Paint VEIL = new Paint();

    /** one dark line every few pixels over {@code w} x {@code h}: the phosphor rows of a green-screen monitor */
    static void scanlines(Canvas c, float w, float h, float d) {
        if (!Theme.crt()) return;                               // the XP looks are a flat window, not a tube
        VEIL.setColor(Theme.SCAN);
        float step = Math.max(2f, 3 * d), thick = Math.max(1f, d * 0.5f);
        for (float y = 0; y < h; y += step) c.drawRect(0, y, w, y + thick, VEIL);
    }
}