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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.fuusio.api.util.Dimension;
import org.fuusio.api.util.Insets;

import java.util.ArrayList;

/**
 * {@link GridLayout} implements a layout manager for defining and managing the layout of
 * {@link View}s. Layout managing is based on TODO
 */
public class GridLayout extends ViewGroup {

    /**
     * A {@link ArrayList} {@link ViewArea}
     */
    protected final ArrayList<ViewArea> mViewAreas;


    /**
     * An array containing the a {@link ColumnSpec} for each column in this {@link GridLayout}.
     */
    protected ColumnSpec[] mColumnSpecs;
    /**
     * An array containing the a {@link RowSpec} for each row in this {@link GridLayout}.
     */
    protected RowSpec[] mRowSpecs;
    /**
     * Top, left, bottom, and right margins of this {@link GridLayout}.
     */
    protected Insets mInsets;

    /**
     * A boolean flag indicating whether the layout needs to be re-calculated.
     */
    protected boolean mInvalidated;

    /**
     * Calculated maximum size of this {@link GridLayout}.
     */
    protected Dimension mMaximumLayoutSize;

    /**
     * Calculated minimum size of this {@link GridLayout}.
     */
    protected Dimension mMinimumLayoutSize;

    /**
     * Calculated preferred size of this {@link GridLayout}.
     */
    protected Dimension mPreferredLayoutSize;

    /**
     * Constructs a new instance of {@link GridLayout}.
     *
     * @param pContext A {@link Context}.
     */
    public GridLayout(final Context pContext) {
        this(pContext, null, 0);
    }

    public GridLayout(final Context pContext, final AttributeSet pAttrs) {
        this(pContext, pAttrs, 0);
    }

    public GridLayout(final Context pContext, final AttributeSet pAttrs, final int pDefStyleAttr) {
        super(pContext, pAttrs, pDefStyleAttr);
        mInsets = new Insets(0, 0, 0, 0);
        mViewAreas = new ArrayList<>();
    }

    public void setSpecs(final RowSpec[] pRowSpecs, final ColumnSpec[] pColumnSpecs) {
        mRowSpecs = pRowSpecs;
        mColumnSpecs = pColumnSpecs;
    }

    /**
     * Gets the default margins for {@link LayoutSpec}s containing {@link View}s.
     *
     * @return The margins as an {@link Insets}.
     */
    public Insets getDefaultViewInsets() {
        return ViewArea.getDefaultViewInsets();
    }

    /**
     * Adds the given {@link ViewArea} into this {@link GridLayout}.
     *
     * @param pArea A {@link ViewArea}.
     */
    public void addViewArea(final ViewArea pArea) {
        if (!mViewAreas.contains(pArea)) {
            mViewAreas.add(pArea);
        }
    }

    /**
     * Sets the default margins for {@link LayoutSpec}s containing {@link View}s.
     *
     * @param pTop    The top insets.
     * @param pLeft   The left insets.
     * @param pBottom The bottom insets.
     * @param pRight  The right insets.
     */
    public void setDefaultViewInsets(final int pTop, final int pLeft, final int pBottom, final int pRight) {
        ViewArea.setDefaultViewInsets(pTop, pLeft, pBottom, pRight);
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
     * Creates a new instance of {@link GridLayout}.
     *
     * @param pContext A {@link Context}.
     * @return The created {@link GridLayout}.
     */
    public static GridLayout create(final Context pContext) {
        final GridLayout layout = new GridLayout(pContext);
        return layout;
    }

    /**
     * Invalidates this {@link GridLayout}.
     */
    public void invalidateLayout() {

        mInvalidated = true;
        mMaximumLayoutSize = null;
        mMinimumLayoutSize = null;
        mPreferredLayoutSize = null;
    }

    @Override
    protected void onLayout(final boolean pChanged, final int pLeft, final int pTop, final int pRight, final int pBottom) {

        if (getVisibility() != View.VISIBLE) {
            return;
        }

        final int width = pRight - pLeft;
        final int height = pBottom - pTop;
        final int lastRowIndex = mRowSpecs.length - 1;
        final int lastColumnIndex = mColumnSpecs.length - 1;

        int offset = height;
        int resizeableCount = 0;

        for (int i = 0; i <= lastRowIndex; i++) {
            final RowSpec spec = mRowSpecs[i];

            if (spec.isFixed()) {
                resizeableCount++;
                offset -= spec.getFixedHeight();
            } else {
                offset -= spec.getPreferredHeight();
            }
        }

        final int rowOffset = offset / resizeableCount;
        final int lastRowOffset = rowOffset + (offset - resizeableCount * rowOffset);

        for (int i = 0; i <= lastRowIndex; i++) {
            final RowSpec spec = mRowSpecs[i];

            if (!spec.isFixed()) {
                final int rowHeight = spec.getPreferredHeight() + ((i == lastRowIndex) ? lastRowOffset : rowOffset);
                spec.setHeight(rowHeight);
            }
        }

        offset = width;
        resizeableCount = 0;

        for (int i = 0; i <= lastColumnIndex; i++) {
            final ColumnSpec spec = mColumnSpecs[i];

            if (spec.isFixed()) {
                resizeableCount++;
                offset -= spec.getFixedWidth();
            } else {
                offset -= spec.getPreferredWidth();
            }
        }

        final int columnOffset = offset / resizeableCount;
        final int lastColumnOffset = columnOffset + (offset - resizeableCount * columnOffset);

        for (int i = 0; i <= lastColumnIndex; i++) {
            final ColumnSpec spec = mColumnSpecs[i];

            if (!spec.isFixed()) {
                final int columnWidth = spec.getPreferredWidth() + ((i == lastColumnIndex) ? lastColumnOffset : columnOffset);
                spec.setWidth(columnWidth);
            }
        }
    }

    public ColumnSpec createColumn(final ResizePolicy pPolicy) {
        final ColumnSpec spec = new ColumnSpec(this);
        spec.setResizePolicy(pPolicy);
        return spec;
    }

    public ColumnSpec createColumn(final int pWidth) {
        final ColumnSpec spec = new ColumnSpec(this);
        spec.setResizePolicy(ResizePolicy.FIXED);
        spec.setFixedWidth(pWidth);
        return spec;
    }

    public RowSpec createRow(final ResizePolicy pPolicy) {
        final RowSpec spec = new RowSpec(this);
        spec.setResizePolicy(pPolicy);
        return spec;
    }

    public RowSpec createRow(final int pHeight) {
        final RowSpec spec = new RowSpec(this);
        spec.setResizePolicy(ResizePolicy.FIXED);
        spec.setFixedHeight(pHeight);
        return spec;
    }
}
