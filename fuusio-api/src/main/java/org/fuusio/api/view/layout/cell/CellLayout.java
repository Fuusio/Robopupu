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
package org.fuusio.api.view.layout.cell;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.fuusio.api.util.Dimension;
import org.fuusio.api.util.Insets;

/**
 * {@link CellLayout} implements a layout manager for defining and managing the layout of
 * {@link View}s. Layout managing is based on a number of {@link LayoutCell} instances that each
 * define constraints for setting the layout for given set of {@link View}s.
 */
public class CellLayout extends ViewGroup {
    /**
     * Top, left, bottom, and right margins of this {@link CellLayout}.
     */
    protected Insets mInsets;

    /**
     * A boolean flag indicating whether the layout needs to be re-calculated.
     */
    protected boolean mInvalidated;

    /**
     * Calculated maximum size of this {@link CellLayout}.
     */
    protected Dimension mMaximumLayoutSize;

    /**
     * Calculated minimum size of this {@link CellLayout}.
     */
    protected Dimension mMinimumLayoutSize;

    /**
     * Calculated preferred size of this {@link CellLayout}.
     */
    protected Dimension mPreferredLayoutSize;

    /**
     * The top level {@link ContainerCell} that hierarchically contains all the other cells.
     */
    protected ContainerCell mRootCell;

    /**
     * Constructs a new instance of {@link CellLayout}.
     *
     * @param pContext A {@link Context}.
     */
    public CellLayout(final Context pContext) {
        super(pContext);
        mInsets = new Insets(0, 0, 0, 0);
    }

    public CellLayout(final Context pContext, final AttributeSet pAttrs) {
        super(pContext, pAttrs);
        mInsets = new Insets(0, 0, 0, 0);
    }

    public CellLayout(final Context pContext, final AttributeSet pAttrs, final int pDefStyleAttr) {
        super(pContext, pAttrs, pDefStyleAttr);
        mInsets = new Insets(0, 0, 0, 0);
    }

    /**
     * Gets the default margins for {@link LayoutCell}s containing {@link View}s.
     *
     * @return The margins as an {@link Insets}.
     */
    public Insets getDefaultViewInsets() {
        return ViewCell.getDefaultViewInsets();
    }

    /**
     * Sets the default margins for {@link LayoutCell}s containing {@link View}s.
     *
     * @param pTop    The top insets.
     * @param pLeft   The left insets.
     * @param pBottom The bottom insets.
     * @param pRight  The right insets.
     */
    public void setDefaultViewInsets(final int pTop, final int pLeft, final int pBottom, final int pRight) {
        ViewCell.setDefaultViewInsets(pTop, pLeft, pBottom, pRight);
    }

    /**
     * Sets the layout insets that are used for defining top, left, bottom, and right margins.
     *
     * @param pInsets An {@link Insets}.
     */
    public void setLayoutInsets(final Insets pInsets) {
        mInsets.mTop = pInsets.mTop;
        mInsets.mLeft = pInsets.mLeft;
        mInsets.mBottom = pInsets.mBottom;
        mInsets.mRight = pInsets.mRight;
    }

    /**
     * Gets the root {@link ContainerCell} of the build {@link CellLayout}.
     *
     * @return A {@link ContainerCell}.
     */
    public ContainerCell getRootCell() {
        return mRootCell;
    }

    /**
     * Sets the root {@link ContainerCell} of the build {@link CellLayout}.
     *
     * @param pCell A {@link ContainerCell}.
     */
    public void setRootCell(final ContainerCell pCell) {
        mRootCell = pCell;

    }

    /**
     * Creates a new instance of {@link CellLayout}.
     *
     * @param pContext A {@link Context}.
     * @return The created {@link CellLayout}.
     */
    public static CellLayout create(final Context pContext) {
        final CellLayout layout = new CellLayout(pContext);
        return layout;
    }

    /**
     * Invalidates the layout for the given {@link View}, indicating that if this
     * {@link CellLayout} has cached information it should be discarded.
     *
     * @param pView The {@link View} to be isInvalidated.
     */
    public void invalidateLayout(final View pView) {

        mInvalidated = true;
        mMaximumLayoutSize = null;
        mMinimumLayoutSize = null;
        mPreferredLayoutSize = null;

        if (mRootCell != null) {
            mRootCell.invalidate();
        }
    }

    @Override
    protected void onLayout(final boolean pChanged, final int pLeft, final int pTop, final int pRight, final int pBottom) {

        if (mRootCell == null) {
            return;
        }

        if (mRootCell.isVisible()) {
            mRootCell.measureSizes();
            mMaximumLayoutSize = mRootCell.getMaximumSize();
            mMinimumLayoutSize = mRootCell.getMinimumSize();
            mPreferredLayoutSize = mRootCell.getPreferredSize();
            mRootCell.setSize(pRight - pLeft, pBottom - pTop);
        }
    }

    public ColumnCell addColumn(final ResizePolicy pPolicy) {
        final ColumnCell cell = new ColumnCell(this, pPolicy, ResizePolicy.PREFERRED);
        addCell(cell);
        return cell;
    }

    public ColumnCell addColumn(final int pWidth) {
        final ColumnCell cell = new ColumnCell(this, pWidth, ResizePolicy.PREFERRED);
        addCell(cell);
        return cell;
    }

    public RowCell addRow(final ResizePolicy pPolicy) {
        final RowCell cell = new RowCell(this, ResizePolicy.PREFERRED, pPolicy);
        addCell(cell);
        return cell;
    }

    public RowCell addRow(final int pHeight) {
        final RowCell cell = new RowCell(this, ResizePolicy.PREFERRED, pHeight);
        addCell(cell);
        return cell;
    }

    /**
     * Adds the given {@link LayoutCell} to this {@code ContainerCell}.
     *
     * @param pCell The {@link LayoutCell} to be added.
     * @return The added {@link LayoutCell} if adding succeeds, otherwise {@code null}.
     */
    public LayoutCell addCell(final LayoutCell pCell) {
        if (mRootCell == null) {
            if (pCell instanceof RowCell) {
                mRootCell = new ColumnCell(this, ResizePolicy.PREFERRED, ResizePolicy.PREFERRED);
            } else {
                mRootCell = new RowCell(this, ResizePolicy.PREFERRED, ResizePolicy.PREFERRED);
            }
        }
        mRootCell.addCell(pCell);
        return pCell;
    }
}
