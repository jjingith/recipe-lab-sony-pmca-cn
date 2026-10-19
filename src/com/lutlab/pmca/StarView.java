package com.lutlab.pmca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/** A filled star next to the recipe name: the recipe is a favourite. Canvas-drawn (the camera font has no star glyph). */
public class StarView extends View {
    private final Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float d;
    private int styled = -1;                                     // the Theme version these paints were set from

    public StarView(Context c, AttributeSet a) {
        super(c, a);
        d = c.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onMeasure(int w, int h) { setMeasuredDimension((int) (16 * d), (int) (16 * d)); }

    @Override
    protected void onDraw(Canvas c) {
        if (styled != Theme.version()) { fill.setColor(Theme.STAR); styled = Theme.version(); }
        Legend.star(c, getWidth() / 2f, getHeight() / 2f, 7 * d, fill);
    }
}
