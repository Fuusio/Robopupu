/*
 * Copyright (C) 2000-2015 Marko Salmela.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fuusio.api.view.layout.grid;

import android.view.View;

import org.fuusio.api.util.Insets;

/**
 * {@code ViewArea} implements TODO
 */
public class ViewArea {

    /**
     * Default layout cell insets for a {@link View}.
     */
    protected static Insets sDefaultViewInsets = new Insets(0, 0, 0, 0);

    /**
     * Top, left, bottom, and right margins of this {@link ViewArea}.
     */
    protected final Insets mInsets;

    /**
     * The {@link GridLayout} that contains this {@link ViewArea}.
     */
    protected final GridLayout mLayout;

    /**
     * Anchor specifies the location of the component in the area specified by this cell.
     */
    protected Anchor mAnchor;

    /**
     * The column of this {@link ViewArea}.
     */
    protected int mColumn;

    /**
     * The row of this {@link ViewArea}.
     */
    protected int mRow;

    /**
     * The column span of this {@link ViewArea}.
     */
    protected int mColumnSpan;

    /**
     * The row span of this {@link ViewArea}.
     */
    protected int mRowSpan;

    /**
     * The {@link View} managed by this {@link LayoutSpec}.
     */
    protected View mView;

    /**
     * {@link FillPolicy} specifies how the container {@link View} fills the area of this cell.
     */
    protected FillPolicy mFillPolicy;

    /**
     * Constructs a new instance of {@code ViewArea}.
     *
     * @param pLayout A {@code GridLayout}.
     */
    public ViewArea(final GridLayout pLayout) {
        this(pLayout, null);
    }

    /**
     * Constructs a new instance of {@code ViewArea} for adding the given foreground
     * {@link View} into the {@code ViewGroup} whose layout is managed by the given
     * {@code GridLayout}.
     *
     * @param pLayout A {@code GridLayout}.
     * @param pView   The {@link View} to be added.
     */
    public ViewArea(final GridLayout pLayout, final View pView) {
        mLayout = pLayout;
        mInsets = new Insets();
        mView = pView;
        mAnchor = Anchor.CENTER;
        mFillPolicy = FillPolicy.BOTH;

        pLayout.addView(mView); // TODO
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(final int pColumn) {
        mColumn = pColumn;
    }

    public int getRow() {
        return mRow;
    }

    public void setRow(final int pRow) {
        mRow = pRow;
    }

    public int getColumnSpan() {
        return mColumnSpan;
    }

    public void setColumnSpan(final int pColumnSpan) {
        mColumnSpan = pColumnSpan;
    }

    public int getRowSpan() {
        return mRowSpan;
    }

    public void setRowSpan(final int pRowSpan) {
        mRowSpan = pRowSpan;
    }

    public void setArea(final int pColumn, final int pRow, final int pWidth, final int pHeight) {
        mRow = pRow;
        mColumn = pColumn;
        mRowSpan = pWidth;
        mColumnSpan = pHeight;
    }

    /**
     * Gets the anchor defined to this {@code ViewCell}. The anchor is
     * used when the drawable is smaller than area calculated for its container
     * {@code LayoutCell}. It determines where, within the cell area, to place
     * the drawable. The possible anchor values are: {@code CENTER},
     * {@code NORTH}, {@code NORTHEAST}, {@code EAST},
     * {@code SOUTHEAST}, {@code SOUTH}, {@code SOUTHWEST},
     * {@code WEST}, and {@code NORTHWEST}. The default value is
     * {@code CENTER</code.
     *
     * @return An {@code Anchor}.
     */
    public final Anchor getAnchor() {
        return mAnchor;
    }

    /**
     * Sets the anchor defined to this {@code ViewCell}. The anchor is
     * used when the drawable is smaller than area calculated for its container
     * {@code LayoutCell}. It determines where, within the cell area, to place
     * the drawable. The possible anchor values are: {@code CENTER},
     * {@code NORTH}, {@code NORTHEAST}, {@code EAST},
     * {@code SOUTHEAST}, {@code SOUTH}, {@code SOUTHWEST},
     * {@code WEST}, and {@code NORTHWEST}. The default value is
     * {@code CENTER</code.
     *
     * @param pAnchor An {@code Direction} specifying the anchor.
     * @return This instance as a {@code ViewCell}.
     */
    public final ViewArea setAnchor(final Anchor pAnchor) {
        mAnchor = pAnchor;
        return this;
    }

    /**
     * Gets the location for the specified anchor.
     *
     * @return A location as a {@code Point}.
     *
    protected Point getAnchorLocation() {
    int x = mLocation.x + mSize.mWidth / 2;
    int y = mLocation.y + mSize.mHeight / 2;

    if (mView != null && mView.getVisibility() != View.GONE) {
    switch (mAnchor) {
    case NORTH_WEST: {
    x = mLocation.x;
    y = mLocation.y;
    break;
    }
    case NORTH: {
    x = mLocation.x + mSize.mWidth / 2;
    y = mLocation.y;
    break;
    }
    case NORTH_EAST: {
    x = mLocation.x + mSize.mWidth;
    y = mLocation.y;
    break;
    }
    case EAST: {
    x = mLocation.x + mSize.mWidth;
    y = mLocation.y + mSize.mHeight / 2;
    break;
    }
    case SOUTH_EAST: {
    x = mLocation.x + mSize.mWidth;
    y = mLocation.y + mSize.mHeight;
    break;
    }
    case SOUTH: {
    x = mLocation.x + mSize.mWidth / 2;
    y = mLocation.y + mSize.mHeight;
    break;
    }
    case SOUTH_WEST: {
    x = mLocation.x;
    y = mLocation.y + mSize.mHeight;
    break;
    }
    case WEST: {
    x = mLocation.x;
    y = mLocation.y + mSize.mHeight / 2;
    break;
    }
    case CENTER: {
    x = mLocation.x + mSize.mWidth / 2;
    y = mLocation.y + mSize.mHeight / 2;
    break;
    }
    }
    }

    return new Point(x, y);
    } */

    /**
     * Gets the foreground {@link View} assigned to this {@code ViewCell}.
     *
     * @return A {@link View}.
     */
    public final View getView() {
        return mView;
    }

    /**
     * Gets the default margins for {@code ViewCell}s.
     *
     * @return The margins as an {@link Insets}.
     */
    public static Insets getDefaultViewInsets() {
        return sDefaultViewInsets;
    }

    /**
     * Sets the default margins for {@code ComponentCells}.
     *
     * @param pTop    The top insets.
     * @param pLeft   The left insets.
     * @param pBottom The bottom insets.
     * @param pRight  The right insets.
     */
    public static void setDefaultViewInsets(final int pTop, final int pLeft, final int pBottom,
                                            final int pRight) {
        sDefaultViewInsets = new Insets(pTop, pLeft, pBottom, pRight);
    }

    /**
     * Gets the fill mode defined to this {@code ViewCell}. Fill mode
     * is mode is used to specify how the drawable fills up the area calculated
     * for its container {@code LayoutCell}.  The possible fill mode values are:
     * {@code NONE}, {@code HORIZONTAL}, {@code VERTICAL},
     * and {@code BOTH}. The default value is {@code NONE</code.
     *
     * @return A {@link FillPolicy }.
     */
    public final FillPolicy getFillMode() {
        return mFillPolicy;
    }

    /**
     * Sets the fill mode defined to this {@code ViewCell}. Fill mode
     * is mode is used to specify how the drawable fills up the area calculated
     * for its container {@code LayoutCell}.  The possible fill mode values are:
     * {@code NONE}, {@code HORIZONTAL}, {@code VERTICAL},
     * and {@code BOTH}. The default value is {@code NONE</code.
     *
     * @param pFillMode A {@link FillPolicy }.
     * @return This instance as a {@code ViewCell} for method invocation
     * linking.
     */
    public final ViewArea setFillPolicy(final FillPolicy pFillMode) {
        mFillPolicy = pFillMode;
        return this;
    }

    /**
     * Gets the current location calculated for this {@LayoutSpec}.
     *
     * @return The location as a {@code Point}.
     *
    public Point getLocation() {
    return mLocation;
    }*/

    /**
     * Sets the current location calculated for this {@LayoutSpec}.
     *
     * @param pX The x-coordinate of the new location as an {@code int}.
     * @param pY The y-coordinate of the new location as an {@code int}.
     *
    public void setLocation(final int pX, final int pY) {
    mLocation.x = pX;
    mLocation.y = pY;
    }*/

    /**
     * Gets the insets of this {@code ViewCell}.
     *
     * @return An {@link Insets}.
     */
    public final Insets getInsets() {
        return mInsets;
    }

    /**
     * Sets the insets of this {@code ViewCell}.
     *
     * @param pInsets An {@link Insets}.
     * @return This instance for method invocation linking.
     */
    public final ViewArea setInsets(final Insets pInsets) {
        return setInsets(pInsets.mTop, pInsets.mLeft, pInsets.mBottom, pInsets.mRight);
    }

    /**
     * Sets the insets of this {@code ViewCell}.
     *
     * @param pTop    The top insets.
     * @param pLeft   The left insets.
     * @param pBottom The bottom insets.
     * @param pRight  The right insets.
     * @return This instance for method invocation linking.
     */
    public final ViewArea setInsets(final int pTop, final int pLeft, final int pBottom,
                                    final int pRight) {
        mInsets.mTop = pTop;
        mInsets.mLeft = pLeft;
        mInsets.mBottom = pBottom;
        mInsets.mRight = pRight;
        return this;
    }

    /**
     * Sets the current size measure for this {@code LayoutCell}.
     *
     * @param pWidth  The width of the new size as an {@code int}.
     * @param pHeight The height of the new size as an {@code int}.
     *
    public void setSize(final int pWidth, final int pHeight) {

    mSize.mWidth = pWidth;
    mSize.mHeight = pHeight;
    // TODO
    if (mView != null) {
    layoutView(mView, pWidth, pHeight, mInsets);
    }
    }

    protected void layoutView(final View pView, final int pWidth, final int pHeight,
    final Insets pInsets) {

    if (mFillPolicy == FillPolicy.BOTH) {
    final int drawableWidth = pWidth - pInsets.mLeft - pInsets.mRight;
    final int drawableHeight = pHeight - pInsets.mTop - pInsets.mBottom;
    int x = mLocation.x + pInsets.mLeft;
    int y = mLocation.y + pInsets.mTop;
    pView.layout(x, y, x + drawableWidth, y + drawableHeight);
    return;
    }

    int x = pInsets.mLeft;
    int y = pInsets.mTop;

    if (mFillPolicy == FillPolicy.HORIZONTAL) {
    final int viewWidth = pWidth - pInsets.mLeft - pInsets.mRight;
    int viewHeight = pView.getMeasuredHeight();
    int maxHeight = pHeight - pInsets.mTop - pInsets.mBottom;
    viewHeight = (viewHeight > maxHeight) ? maxHeight : viewHeight;

    switch (mAnchor) {
    case NORTH:
    case NORTH_WEST:
    case NORTH_EAST: {
    y = mLocation.y;
    break;
    }
    case SOUTH:
    case SOUTH_WEST:
    case SOUTH_EAST: {
    y = mLocation.y + pHeight - viewHeight;
    break;
    }
    case WEST:
    case EAST:
    case CENTER: {
    y = mLocation.y + (pHeight - viewHeight) / 2;
    break;
    }
    }

    pView.layout(x, y, x + viewWidth, y + viewHeight);
    } else if (mFillPolicy == FillPolicy.VERTICAL) {
    int viewWidth = pView.getMeasuredWidth();
    final int maxWidth = pWidth - pInsets.mLeft - pInsets.mRight;
    viewWidth = (viewWidth > maxWidth) ? maxWidth : viewWidth;
    final int viewHeight = pHeight - pInsets.mTop - pInsets.mBottom;

    switch (mAnchor) {
    case WEST:
    case SOUTH_WEST:
    case NORTH_WEST: {
    x = mLocation.x;
    break;
    }
    case EAST:
    case NORTH_EAST:
    case SOUTH_EAST: {
    x = mLocation.x + pWidth - viewWidth;
    break;
    }
    case NORTH:
    case SOUTH:
    case CENTER: {
    x = mLocation.x + (pWidth - viewWidth) / 2;
    break;
    }
    }

    pView.layout(x, y, x + viewWidth, y + viewHeight);
    } else {
    int viewWidth = pView.getMeasuredWidth();
    final int maxWidth = pWidth - pInsets.mLeft - pInsets.mRight;
    viewWidth = (viewWidth > maxWidth) ? maxWidth : viewWidth;
    int viewHeight = pView.getMeasuredHeight();
    final int maxHeight = pHeight - pInsets.mTop - pInsets.mBottom;
    viewHeight = (viewHeight > maxHeight) ? maxHeight : viewHeight;

    switch (mAnchor) {
    case NORTH: {
    x = mLocation.x + (pWidth - viewWidth) / 2;
    y = mLocation.y;
    break;
    }
    case NORTH_EAST: {
    x = mLocation.x + pWidth - viewWidth;
    y = mLocation.y;
    break;
    }
    case EAST: {
    x = mLocation.x + pWidth - viewWidth;
    y = mLocation.y + (pHeight - viewHeight) / 2;
    break;
    }
    case SOUTH_EAST: {
    x = mLocation.x + pWidth - viewWidth;
    y = mLocation.y + pHeight - viewHeight;
    break;
    }
    case SOUTH: {
    x = mLocation.x + (pWidth - viewWidth) / 2;
    y = mLocation.y + pHeight - viewHeight;
    break;
    }
    case SOUTH_WEST: {
    x = mLocation.x;
    y = mLocation.y + pHeight - viewHeight;
    break;
    }
    case WEST: {
    x = mLocation.x;
    y = mLocation.y + (pHeight - viewHeight) / 2;
    break;
    }
    case NORTH_WEST: {
    x = mLocation.x;
    y = mLocation.y;
    break;
    }
    case CENTER: {
    x = mLocation.x + (pWidth - viewWidth) / 2;
    y = mLocation.y + (pHeight - viewHeight) / 2;
    break;
    }
    }

    pView.layout(x, y, x + viewWidth, y + viewHeight);
    }
    }*/

    /**
     * Calculates the minimum, maximum, and preferred sizes of this {@code LayoutCell}.
     */
/*
    @Override
    protected void measureSizes() {
        // View is not assigned for this ViewCell or the View
        // is not visible

        mMaximumSize.mWidth = Integer.MAX_VALUE;
        mMaximumSize.mHeight = Integer.MAX_VALUE;

        if (!isVisible()) {
            if (mWidthResizePolicy == ResizePolicy.FIXED) {
                mMinimumSize.mWidth = mFixedSize.mWidth;
                mMaximumSize.mWidth = mFixedSize.mWidth;
                mPreferredSize.mWidth = mFixedSize.mWidth;
            } else {
                mMinimumSize.mWidth = 0;
                mPreferredSize.mWidth = 0;
            }

            if (mHeightResizePolicy != ResizePolicy.FIXED) {
                mMinimumSize.mHeight = mFixedSize.mHeight;
                mMaximumSize.mHeight = mFixedSize.mHeight;
                mPreferredSize.mHeight = mFixedSize.mHeight;
            } else {
                mMinimumSize.mHeight = 0;
                mPreferredSize.mHeight = 0;
            }

            mMaximumSize.mWidth += mInsets.mLeft + mInsets.mRight;
            mMaximumSize.mHeight += mInsets.mTop + mInsets.mBottom;
            mMinimumSize.mWidth += mInsets.mLeft + mInsets.mRight;
            mMinimumSize.mHeight += mInsets.mTop + mInsets.mBottom;
            mPreferredSize.mWidth += mInsets.mLeft + mInsets.mRight;
            mPreferredSize.mHeight += mInsets.mTop + mInsets.mBottom;
            return;
        }

        if (mView != null) {
            mMinimumSize.mWidth = mView.getMeasuredWidth();
            mMinimumSize.mHeight = mView.getMeasuredHeight();
        }

        switch (mWidthResizePolicy) {
            case FIXED: {
                mMaximumSize.mWidth = mFixedSize.mWidth;
                mMinimumSize.mWidth = mFixedSize.mWidth;
                mPreferredSize.mWidth = mFixedSize.mWidth;
                break;
            }
            case MINIMUM: {
                mPreferredSize.mWidth = mMinimumSize.mWidth;
                break;
            }
            case PREFERRED: {
                if (mView != null) {
                    mPreferredSize.mWidth = mView.getMeasuredWidth();
                }
                break;
            }
        }

        switch (mHeightResizePolicy) {
            case FIXED: {
                mMaximumSize.mHeight = mFixedSize.mHeight;
                mMinimumSize.mHeight = mFixedSize.mHeight;
                mPreferredSize.mHeight = mFixedSize.mHeight;
                break;
            }
            case MINIMUM: {
                mPreferredSize.mHeight = mMinimumSize.mHeight;
                break;
            }
            case PREFERRED: {
                if (mView != null) {
                    mPreferredSize.mWidth = mView.getMeasuredHeight();
                }
                break;
            }
        }

        mMaximumSize.mWidth += mInsets.mLeft + mInsets.mRight;
        mMaximumSize.mHeight += mInsets.mTop + mInsets.mBottom;
        mMinimumSize.mWidth += mInsets.mLeft + mInsets.mRight;
        mMinimumSize.mHeight += mInsets.mTop + mInsets.mBottom;
        mPreferredSize.mWidth += mInsets.mLeft + mInsets.mRight;
        mPreferredSize.mHeight += mInsets.mTop + mInsets.mBottom;
    }*/

    /**
     * Tests whether the layout cell defined by this {@code LayoutCell} is visible or not.
     *
     * @return A {@code boolean} value.
     */
    public boolean isVisible() {
        boolean viewVisible = false;

        if (mView != null) {
            viewVisible = mView.getVisibility() != View.GONE;
        }

        return viewVisible;
    }


    /**
     * Sets the anchor defined to this {@code ViewCell}. The anchor is
     * used when the drawable is smaller than area calculated for its container
     * {@code LayoutCell}. It determines where, within the cell area, to place
     * the drawable. The possible anchor values are: {@code CENTER},
     * {@code NORTH}, {@code NORTHEAST}, {@code EAST},
     * {@code SOUTHEAST}, {@code SOUTH}, {@code SOUTHWEST},
     * {@code WEST}, and {@code NORTHWEST}. The default value is
     * {@code CENTER</code.
     *
     * @param pAnchor An {@code Direction} specifying the anchor.
     * @return This instance as a {@code ViewCell} for method invocation
     * linking.
     */
    public final ViewArea anchor(final Anchor pAnchor) {
        return setAnchor(pAnchor);
    }

    /**
     * Sets the fill mode defined to this {@code ViewCell}. Fill mode
     * is mode is used to specify how the drawable fills up the area calculated
     * for its container {@code LayoutCell}.  The possible fill mode values are:
     * {@code NONE}, {@code HORIZONTAL}, {@code VERTICAL},
     * and {@code BOTH}. The default value is {@code NONE</code.
     *
     * @param pFillMode A {@link FillPolicy }.
     * @return This instance as a {@code ViewCell}.
     */
    public final ViewArea fill(final FillPolicy pFillMode) {
        return setFillPolicy(pFillMode);
    }

    /**
     * Sets the insets of this {@code ViewCell}.
     *
     * @param pTop    The top insets.
     * @param pLeft   The left insets.
     * @param pBottom The bottom insets.
     * @param pRight  The right insets.
     * @return This instance for method invocation linking.
     */
    public final ViewArea insets(final int pTop, final int pLeft, final int pBottom, final int pRight) {
        return setInsets(pTop, pLeft, pBottom, pRight);
    }
}
