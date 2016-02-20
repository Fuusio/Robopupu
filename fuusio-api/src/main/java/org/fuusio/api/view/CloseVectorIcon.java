package org.fuusio.api.view;

/**
 * {@link CloseVectorIcon} implements a {@link VectorIcon} for Close Icon.
 */
public class CloseVectorIcon extends VectorIcon {

    public CloseVectorIcon() {
    }

    @Override
    public VectorIcon copy() {
        return new CloseVectorIcon();
    }

    @Override
    protected void initialise() {

        final float topY = mBounds.top;
        final float bottomY = mBounds.bottom;
        final float leftX = mBounds.left;
        final float rightX = mBounds.right;
        final float centerX = mBounds.centerX();
        final float centerY = mBounds.centerY();

        mSegments[0] = new IconSegment(this, 0, leftX, topY, centerX, centerY);
        mSegments[1] = new IconSegment(this, 1, centerX, centerY, rightX, topY);
        mSegments[2] = new IconSegment(this, 2, leftX, topY, centerX, centerY);
        mSegments[3] = new IconSegment(this, 3, centerX, centerY, rightX, bottomY);
        mSegments[4] = new IconSegment(this, 4, leftX, bottomY, centerX, centerY);
        mSegments[5] = new IconSegment(this, 5, centerX, centerY, rightX, bottomY);
    }
}
