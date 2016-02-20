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

import android.view.View;

import org.fuusio.api.util.Dimension;

/**
 * {@code ColumnCell} implements {@link ContainerCell} that may contain a vertically oriented group
 * of multiple {@link LayoutCell}s.
 */
public class ColumnCell extends ContainerCell {

    /**
     * Constructs a new instance of {@code ColumnCell} for the given {@code CellLayout}. The
     * instance is resizeable according to the specified resize mode.
     *
     * @param pLayout             A {@code CellLayout}.
     * @param pWidthResizePolicy  The horizontal {@code ResizePolicy}.
     * @param pHeightResizePolicy The vertical {@code ResizePolicy}.
     */
    public ColumnCell(final CellLayout pLayout, final ResizePolicy pWidthResizePolicy,
                      final ResizePolicy pHeightResizePolicy) {
        super(pLayout, pWidthResizePolicy, pHeightResizePolicy);
    }

    /**
     * Constructs a new instance of {@code ColumnCell} for the given {@code CellLayout}. The
     * instance defines a layout cell with fixed width and height.
     *
     * @param pLayout A {@code CellLayout}.
     * @param pWidth  The width of the {@code CompositeCell}.
     * @param pHeight The height of the {@code CompositeCell}.
     */
    public ColumnCell(final CellLayout pLayout, final int pWidth, final int pHeight) {
        super(pLayout, pWidth, pHeight);
    }

    /**
     * Constructs a new instance of {@code ColumnCell} for the given {@code CellLayout}. The
     * instance defines a layout cell with fixed height and resizeable width.
     *
     * @param pLayout            A {@code CellLayout}.
     * @param pWidthResizePolicy The horizontal {@code ResizePolicy}.
     * @param pHeight            The height of the {@code CompositeCell}.
     */
    public ColumnCell(final CellLayout pLayout, final ResizePolicy pWidthResizePolicy,
                      final int pHeight) {
        super(pLayout, pWidthResizePolicy, pHeight);
    }

    /**
     * Constructs a new instance of {@code ColumnCell} for the given {@code CellLayout}. The
     * instance defines a layout cell with fixed width and resizeable height.
     *
     * @param pLayout             A {@code CellLayout}.
     * @param pWidth              The width of the {@code CompositeCell}.
     * @param pHeightResizePolicy The vertical {@code ResizePolicy} to be added.
     */
    public ColumnCell(final CellLayout pLayout, final int pWidth,
                      final ResizePolicy pHeightResizePolicy) {
        super(pLayout, pWidth, pHeightResizePolicy);
    }

    /**
     * Gets the current size calculated for this {@code LayoutCell}.
     *
     * @param pWidth  The width of the new size as an {@code int}.
     * @param pHeight The height of the new size as an {@code int}.
     */
    @Override
    public void setSize(final int pWidth, final int pHeight) {
        super.setSize(pWidth, pHeight);

        final int cellCount = mComponentCells.size();
        int x = mLocation.x;
        int y = mLocation.y;
        int prefColumHeight = 0;
        int setCellWidth = pWidth;
        int resizeableCount = 0;

        for (int i = 0; i < cellCount; i++) {
            final LayoutCell cell = mComponentCells.get(i);
            final Dimension prefSize = cell.getPreferredSize();
            prefColumHeight += prefSize.mHeight;

            if (cell.getHeightResizePolicy() == ResizePolicy.PREFERRED) {
                resizeableCount++;
            }

            if (prefSize.mWidth > setCellWidth) {
                setCellWidth = prefSize.mWidth;
            }
        }

        int offset = (resizeableCount == 0) ? 0
                : ((pHeight - prefColumHeight) / resizeableCount);
        int lastOffset = offset + (pHeight - prefColumHeight - resizeableCount * offset);

        for (int i = 0; i < cellCount; i++) {
            final LayoutCell cell = mComponentCells.get(i);
            final Dimension prefSize = cell.getPreferredSize();
            int cellWidth = setCellWidth; // (prefSize.width > width) ? prefSize.width : width;
            int cellHeight = prefSize.mHeight;

            if (cell.getHeightResizePolicy() == ResizePolicy.PREFERRED) {
                cellHeight += (i < cellCount - 1) ? offset : lastOffset;
            }

            cell.setLocation(x, y);
            cell.setSize(cellWidth, cellHeight);
            y += cellHeight;
        }
    }

    /**
     * Adds the given {@code LayoutCell} to this {@code ColumnCell}.
     *
     * @param pCell The {@code LayoutCell} to be added.
     * @return The reference for {@code ColumnCell} for method invocation linking.
     */

    @Override
    public ColumnCell addCell(final LayoutCell pCell) {
        if (!mComponentCells.contains(pCell)) {
            mComponentCells.add(pCell);
        }

        return this;
    }

    /**
     * Calculates the minimum, maximum, and preferred sizes of this {@code LayoutCell}.
     */
    @Override
    protected void measureSizes() {
        if (!isVisible()) {
            if (mWidthResizePolicy == ResizePolicy.FIXED) {
                mMinimumSize.mWidth = mFixedSize.mWidth;
                mMaximumSize.mWidth = mFixedSize.mWidth;
                mPreferredSize.mWidth = mFixedSize.mWidth;
            } else {
                mMinimumSize.mWidth = 0;
                mMaximumSize.mWidth = Integer.MAX_VALUE;
                mPreferredSize.mWidth = 0;
            }

            if (mHeightResizePolicy != ResizePolicy.FIXED) {
                mMinimumSize.mHeight = mFixedSize.mHeight;
                mMaximumSize.mHeight = mFixedSize.mHeight;
                mPreferredSize.mHeight = mFixedSize.mHeight;
            } else {
                mMinimumSize.mHeight = 0;
                mMaximumSize.mHeight = Integer.MAX_VALUE;
                mPreferredSize.mHeight = 0;
            }

            return;
        }

        resetSizes();

        final int cellCount = mComponentCells.size();
        int maxWidth = 0;
        int maxHeight = 0;
        int minWidth = 0;
        int minHeight = 0;
        int prefWidth = 0;
        int prefHeight = 0;

        for (int i = 0; i < cellCount; i++) {
            final LayoutCell cell = mComponentCells.get(i);
            cell.measureSizes();

            Dimension maxCellSize = cell.getMaximumSize();
            Dimension minCellSize = cell.getMinimumSize();
            Dimension prefCellSize = cell.getPreferredSize();

            maxWidth = (maxCellSize.mWidth > maxWidth) ? maxCellSize.mWidth : maxWidth;
            maxHeight += maxCellSize.mHeight;

            minWidth = (minCellSize.mWidth > minWidth) ? minCellSize.mWidth : minWidth;
            minHeight += minCellSize.mHeight;

            prefWidth = (prefCellSize.mWidth > prefWidth) ? prefCellSize.mWidth : prefWidth;
            prefHeight += prefCellSize.mHeight;
        }

        mMaximumSize.mWidth = maxWidth;
        mMaximumSize.mHeight = maxHeight;

        mMinimumSize.mWidth = minWidth;
        mMinimumSize.mHeight = minHeight;

        if (mWidthResizePolicy == ResizePolicy.MINIMUM) {
            mPreferredSize.mWidth = minWidth;
        } else if (mWidthResizePolicy == ResizePolicy.PREFERRED) {
            mPreferredSize.mWidth = prefWidth;
        } else if (mWidthResizePolicy == ResizePolicy.FIXED) {
            mMinimumSize.mWidth = mFixedSize.mWidth;
            mMaximumSize.mWidth = mFixedSize.mWidth;
            mPreferredSize.mWidth = mFixedSize.mWidth;
        }

        if (mHeightResizePolicy == ResizePolicy.MINIMUM) {
            mPreferredSize.mHeight = minHeight;
        } else if (mHeightResizePolicy == ResizePolicy.PREFERRED) {
            mPreferredSize.mHeight = prefHeight;
        } else if (mHeightResizePolicy == ResizePolicy.FIXED) {
            mMinimumSize.mHeight = mFixedSize.mHeight;
            mMaximumSize.mHeight = mFixedSize.mHeight;
            mPreferredSize.mHeight = mFixedSize.mHeight;
        }
    }

    public RowCell addRow(final int pHeight) {
        final RowCell cell = new RowCell(mLayout, ResizePolicy.PREFERRED, pHeight);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView) {
        final ViewCell cell = new ViewCell(mLayout, pView, ResizePolicy.PREFERRED,
                ResizePolicy.PREFERRED);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView, final int pHeight) {
        final ViewCell cell = new ViewCell(mLayout, pView, ResizePolicy.PREFERRED, pHeight);
        addCell(cell);
        return cell;
    }
}
