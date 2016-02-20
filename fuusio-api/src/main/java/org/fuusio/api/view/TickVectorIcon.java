package org.fuusio.api.view;

import android.graphics.Paint;

/**
 * {@link TickVectorIcon} implements a {@link VectorIcon} for Tick Icon.
 */
public class TickVectorIcon extends VectorIcon {

    private final static Paint.Cap[] CAPS = {Paint.Cap.SQUARE, Paint.Cap.ROUND, Paint.Cap.SQUARE, Paint.Cap.SQUARE, Paint.Cap.ROUND, Paint.Cap.SQUARE};

    public TickVectorIcon() {
    }

    @Override
    public VectorIcon copy() {
        return new TickVectorIcon();
    }

    @Override
    protected void initialise() {

        final float topY = mBounds.top;
        final float bottomY = mBounds.bottom;
        final float leftX = mBounds.left;
        final float rightX = mBounds.right;
        final float centerX = mBounds.centerX();
        final float centerY = mBounds.centerY();

        mSegments[0] = new IconSegment(this, 0, leftX, topY, centerX, bottomY);
        mSegments[1] = new IconSegment(this, 1, centerX, topY, rightX, topY);
        mSegments[2] = new IconSegment(this, 2, leftX, centerY, centerX, centerY);
        mSegments[3] = new IconSegment(this, 3, centerX, centerY, rightX, centerY);
        mSegments[4] = new IconSegment(this, 4, leftX, bottomY, centerX, bottomY);
        mSegments[5] = new IconSegment(this, 5, centerX, bottomY, rightX, bottomY);
    }

    @Override
    protected Paint.Cap getCap(final int index) {
        return CAPS[index];
    }
}
