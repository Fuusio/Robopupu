// ============================================================================
// Floxp.com : Java Class Source File
// ============================================================================
//
// Class: RowCell
// Package: FloXP.com Android APIs (org.fuusio.robopupu.api) -
// Graphics API (org.fuusio.robopupu.api.graphics) -
// Layout Managers (org.fuusio.api.graphics.layout)
//
// Author: Marko Salmela
//
// Copyright (C) Marko Salmela, 2000-2011. All Rights Reserved.
//
// This software is the proprietary information of Marko Salmela.
// Use is subject to license terms. This software is protected by
// copyright and distributed under licenses restricting its use,
// copying, distribution, and decompilation. No part of this software
// or associated documentation may be reproduced in any form by any
// means without prior written authorization of Marko Salmela.
//
// Disclaimer:
// -----------
//
// This software is provided by the author 'as is' and any express or implied
// warranties, including, but not limited to, the implied warranties of
// merchantability and fitness for a particular purpose are disclaimed.
// In no event shall the author be liable for any direct, indirect,
// incidental, special, exemplary, or consequential damages (including, but
// not limited to, procurement of substitute goods or services, loss of use,
// data, or profits; or business interruption) however caused and on any
// theory of liability, whether in contract, strict liability, or tort
// (including negligence or otherwise) arising in any way out of the use of
// this software, even if advised of the possibility of such damage.
// ============================================================================

package org.fuusio.api.view.layout.cell;

import android.view.View;

import org.fuusio.api.util.Dimension;

/**
 * {@code RowCell} implements {@link ContainerCell} that may contain a horizontally oriented group
 * of multiple {@link LayoutCell}s.
 */
public class RowCell extends ContainerCell {

    /**
     * Constructs a new instance of {@code RowCell} for the given {@link CellLayout}. The instance
     * is resizeable according to the specified resize mode.
     *
     * @param pLayout             A {@link CellLayout}.
     * @param pWidthResizePolicy  The horizontal {@link ResizePolicy}.
     * @param pHeightResizePolicy The vertical {@link ResizePolicy}.
     */
    public RowCell(final CellLayout pLayout, final ResizePolicy pWidthResizePolicy,
                   final ResizePolicy pHeightResizePolicy) {
        super(pLayout, pWidthResizePolicy, pHeightResizePolicy);
    }

    /**
     * Constructs a new instance of {@code RowCell} for the given {@link CellLayout}. The instance
     * defines a layout cell with fixed width and height.
     *
     * @param pLayout A {@link CellLayout}.
     * @param pWidth  The width of the {@code RowCell}.
     * @param pHeight The height of the {@code RowCell}.
     */
    public RowCell(final CellLayout pLayout, final int pWidth, final int pHeight) {
        super(pLayout, pWidth, pHeight);
    }

    /**
     * Constructs a new instance of {@code RowCell} for the given {@link CellLayout}. The instance
     * defines a layout cell with fixed height and resizeable width.
     *
     * @param pLayout            A {@link CellLayout}.
     * @param pWidthResizePolicy The horizontal {@link ResizePolicy}.
     * @param pHeight            The height of the {@code RowCell}.
     */
    public RowCell(final CellLayout pLayout, final ResizePolicy pWidthResizePolicy,
                   final int pHeight) {
        super(pLayout, pWidthResizePolicy, pHeight);
    }

    /**
     * Constructs a new instance of {@code RowCell} for the given {@link CellLayout}. The instance
     * defines a layout cell with fixed width and resizeable height.
     *
     * @param pLayout             A {@link CellLayout}.
     * @param pWidth              The width of the {@code RowCell}.
     * @param pHeightResizePolicy The vertical {@link ResizePolicy} to be added.
     */
    public RowCell(final CellLayout pLayout, final int pWidth,
                   final ResizePolicy pHeightResizePolicy) {
        super(pLayout, pWidth, pHeightResizePolicy);
    }

    /**
     * Gets the current size calculated for this {@code LayoutCell}.
     *
     * @param width  The width of the new size as an {@code int}.
     * @param height The height of the new size as an {@code int}.
     */
    @Override
    public void setSize(final int width, final int height) {
        super.setSize(width, height);

        final int cellCount = mComponentCells.size();
        int x = mLocation.x;
        int y = mLocation.y;
        int prefRowWidth = 0;
        int resizeableCount = 0;
        int setCellHeight = height;

        for (int i = 0; i < cellCount; i++) {
            final LayoutCell cell = mComponentCells.get(i);
            final Dimension prefSize = cell.getPreferredSize();
            prefRowWidth += prefSize.mWidth;

            if (cell.getWidthResizePolicy() == ResizePolicy.PREFERRED) {
                resizeableCount++;
            }

            if (prefSize.mHeight > setCellHeight) {
                setCellHeight = prefSize.mHeight;
            }
        }

        final int offset = (resizeableCount == 0) ? 0
                : ((width - prefRowWidth) / resizeableCount);
        final int lastOffset = offset + (width - prefRowWidth - resizeableCount * offset);

        for (int i = 0; i < cellCount; i++) {
            final LayoutCell cell = mComponentCells.get(i);
            final Dimension prefSize = cell.getPreferredSize();
            int cellWidth = prefSize.mWidth;
            int cellHeight = setCellHeight; // (prefSize.height > height) ? prefSize.height :
            // height;

            if (cell.getWidthResizePolicy() == ResizePolicy.PREFERRED) {
                cellWidth += (i < cellCount - 1) ? offset : lastOffset;
            }

            cell.setLocation(x, y);
            cell.setSize(cellWidth, cellHeight);
            x += cellWidth;
        }
    }

    /**
     * Adds the given {@code LayoutCell} to this {@code RowCell}.
     *
     * @param cell The {@code LayoutCell} to be added.
     * @return The reference for {@code RowCell} for method invocation linking.
     */
    @Override
    public RowCell addCell(final LayoutCell cell) {
        if (!mComponentCells.contains(cell)) {
            mComponentCells.add(cell);
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

        int cellCount = mComponentCells.size();
        int maxWidth = Integer.MAX_VALUE;
        int maxHeight = Integer.MAX_VALUE;
        int minWidth = 0;
        int minHeight = 0;
        int prefWidth = 0;
        int prefHeight = 0;

        for (int i = 0; i < cellCount; i++) {
            LayoutCell cell = mComponentCells.get(i);
            cell.measureSizes();

            Dimension maxCellSize = cell.getMaximumSize();
            Dimension minCellSize = cell.getMinimumSize();
            Dimension prefCellSize = cell.getPreferredSize();

            maxWidth += maxCellSize.mWidth;
            maxHeight = (maxCellSize.mHeight < maxHeight) ? maxCellSize.mHeight : maxHeight;

            minWidth += minCellSize.mWidth;
            minHeight = (minCellSize.mHeight > minHeight) ? minCellSize.mHeight : minHeight;

            prefWidth += prefCellSize.mWidth;
            prefHeight = (prefCellSize.mHeight > prefHeight) ? prefCellSize.mHeight : prefHeight;
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

    public ColumnCell addColumn(final int pWidth) {
        final ColumnCell cell = new ColumnCell(mLayout, pWidth, ResizePolicy.PREFERRED);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView) {
        final ViewCell cell = new ViewCell(mLayout, pView, ResizePolicy.PREFERRED, ResizePolicy.PREFERRED);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView, final int pWidth) {
        final ViewCell cell = new ViewCell(mLayout, pView, pWidth, ResizePolicy.PREFERRED);
        addCell(cell);
        return cell;
    }
}
