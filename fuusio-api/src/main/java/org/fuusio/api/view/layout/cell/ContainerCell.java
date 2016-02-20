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

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ContainerCell} implements {@link LayoutCell} that may contain a horizontally or vertically
 * oriented group of multiple {@link LayoutCell}s.
 */
public abstract class ContainerCell extends LayoutCell {

    /**
     * A list of contained component {@link LayoutCell}s.
     */
    protected final ArrayList<LayoutCell> mComponentCells;

    /**
     * Constructs a new instance of {@code ContainerCell} for the given {@link CellLayout}.
     *
     * @param pLayout A {@link CellLayout}.
     */
    protected ContainerCell(final CellLayout pLayout) {
        super(pLayout);
        mComponentCells = new ArrayList<>();
    }

    /**
     * Constructs a new instance of {@code ContainerCell} for the given {@link CellLayout}.
     * The instance is resizeable according to the specified resize mode.
     *
     * @param pLayout             A {@link CellLayout}.
     * @param pWidthResizePolicy  The horizontal {@code ResizePolicy}.
     * @param pHeightResizePolicy The vertical {@code ResizePolicy}.
     */
    protected ContainerCell(final CellLayout pLayout,
                            final ResizePolicy pWidthResizePolicy, final ResizePolicy pHeightResizePolicy) {
        this(pLayout);
        mWidthResizePolicy = pWidthResizePolicy;
        mHeightResizePolicy = pHeightResizePolicy;
    }

    /**
     * Constructs a new instance of {@code ContainerCell} for the given {@link CellLayout}.
     * The instance defines a layout cell with fixed width and height.
     *
     * @param pLayout A {@link CellLayout}.
     * @param pWidth  The width of the {@code ContainerCell}.
     * @param pHeight The height of the {@code ContainerCell}.
     */
    protected ContainerCell(final CellLayout pLayout, final int pWidth, final int pHeight) {
        this(pLayout);
        mFixedSize.mWidth = pWidth;
        mFixedSize.mHeight = pHeight;
    }

    /**
     * Constructs a new instance of {@code ContainerCell} for the given {@link CellLayout}.
     * The instance defines a layout cell with fixed height and resizeable width.
     *
     * @param pLayout            A {@link CellLayout}.
     * @param pWidthResizePolicy The horizontal {@code ResizePolicy}.
     * @param pHeight            The height of the {@code ContainerCell}.
     */
    protected ContainerCell(final CellLayout pLayout,
                            final ResizePolicy pWidthResizePolicy, final int pHeight) {
        this(pLayout);
        mWidthResizePolicy = pWidthResizePolicy;
        mFixedSize.mHeight = pHeight;
    }

    /**
     * Constructs a new instance of {@code ContainerCell} for the given {@link CellLayout}.
     * The instance defines a layout cell with fixed width and resizeable height.
     *
     * @param pLayout             A {@link CellLayout}.
     * @param pWidth              The width of the {@code ContainerCell}.
     * @param pHeightResizePolicy The vertical {@code ResizePolicy} to be added.
     */
    protected ContainerCell(final CellLayout pLayout, final int pWidth,
                            final ResizePolicy pHeightResizePolicy) {
        this(pLayout);
        mFixedSize.mWidth = pWidth;
        mHeightResizePolicy = pHeightResizePolicy;
    }

    /**
     * Collects the {@link View}s contained by this {@link ContainerCell}.
     *
     * @param pViews
     */
    @Override
    public void collectViews(final List<View> pViews) {
        for (final LayoutCell cell : mComponentCells) {
            cell.collectViews(pViews);
        }
    }

    /**
     * Adds the given {@link LayoutCell} to this {@code ContainerCell}.
     *
     * @param pCell The {@link LayoutCell} to be added.
     * @return The added {@link LayoutCell} if adding succeeds, otherwise {@code null}.
     */
    public LayoutCell addCell(final LayoutCell pCell) {
        if (!mComponentCells.contains(pCell)) {
            mComponentCells.add(pCell);
            return pCell;
        }

        return null;
    }

    /**
     * Tests whether the layout cell defined by this {@link LayoutCell} is visible or not.
     *
     * @return A {@code boolean} value.
     */
    @Override
    public boolean isVisible() {
        final int cellCount = mComponentCells.size();

        for (int i = 0; i < cellCount; i++) {
            final LayoutCell cell = mComponentCells.get(i);
            if (cell.isVisible()) {
                return true;
            }
        }

        return false;
    }


    /**
     * Adds a {@link Spacer}.
     *
     * @return The added {@link Spacer}.
     */
    public Spacer addSpacer() {
        final Spacer spacer = new Spacer(mLayout);
        addCell(spacer);
        return spacer;
    }

    /**
     * Adds a {@link Spacer} with the given parameters.
     *
     * @param pFillPolicy  A {@link FillPolicy}.
     * @param pFixedWidth  Fixed width.
     * @param pFixedHeight Fixed height.
     * @return {@link Spacer} The added {@link Spacer}.
     */
    public Spacer addSpacer(final FillPolicy pFillPolicy, final int pFixedWidth, final int pFixedHeight) {
        final Spacer spacer = new Spacer(mLayout, pFillPolicy, pFixedWidth, pFixedHeight);
        addCell(spacer);
        return spacer;
    }

    /**
     * Adds a copy {@link Spacer} of the given source {@link Spacer}.
     *
     * @param pSource The source {@link Spacer}.
     * @return The added {@link Spacer}.
     */
    public Spacer addSpacer(final Spacer pSource) {
        final Spacer spacer = new Spacer(mLayout, pSource);
        addCell(spacer);
        return spacer;
    }

    public ViewCell addView(final CellLayout pLayout, final View pView) {
        final ViewCell cell = new ViewCell(mLayout, pView);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView, final ResizePolicy pWidthResizePolicy,
                            final ResizePolicy pHeightResizePolicy) {
        final ViewCell cell = new ViewCell(mLayout, pView);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView, final int pWidth, final int pHeight) {
        final ViewCell cell = new ViewCell(mLayout, pView, pWidth, pHeight);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView, final ResizePolicy pWidthResizePolicy,
                            final int pHeight) {
        final ViewCell cell = new ViewCell(mLayout, pView, pWidthResizePolicy, pHeight);
        addCell(cell);
        return cell;
    }

    public ViewCell addView(final View pView, final int pWidth,
                            final ResizePolicy pHeightResizePolicy) {
        final ViewCell cell = new ViewCell(mLayout, pView, pWidth, pHeightResizePolicy);
        addCell(cell);
        return cell;
    }
}
