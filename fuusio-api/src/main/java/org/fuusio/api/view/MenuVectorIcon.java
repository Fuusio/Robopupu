package org.fuusio.api.view;

/**
 * {@link MenuVectorIcon} implements a {@link VectorIcon} for Menu Icon.
 */
public class MenuVectorIcon extends VectorIcon {

    public MenuVectorIcon() {
    }

    @Override
    public VectorIcon copy() {
        return new MenuVectorIcon();
    }

    @Override
    protected void initialise() {

        final float offset = toPx(2.5f);
        final float topY = mBounds.top + offset;
        final float bottomY = mBounds.bottom - offset;
        final float leftX = mBounds.left;
        final float rightX = mBounds.right;
        final float centerX = mBounds.centerX();
        final float centerY = mBounds.centerY();

        mSegments[0] = new IconSegment(this, 0, leftX, topY, centerX, topY);
        mSegments[1] = new IconSegment(this, 1, centerX, topY, rightX, topY);
        mSegments[2] = new IconSegment(this, 2, leftX, centerY, centerX, centerY);
        mSegments[3] = new IconSegment(this, 3, centerX, centerY, rightX, centerY);
        mSegments[4] = new IconSegment(this, 4, leftX, bottomY, centerX, bottomY);
        mSegments[5] = new IconSegment(this, 5, centerX, bottomY, rightX, bottomY);
    }
}
