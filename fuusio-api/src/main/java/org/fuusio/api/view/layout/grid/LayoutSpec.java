// ============================================================================
// Floxp.com : Java Class Source File
// ============================================================================
//
// Class: LayoutCell
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

package org.fuusio.api.view.layout.grid;

/**
 * {@LayoutSpec} is an abstract base class for the layout cell objects that are used to define
 * a {@link GridLayout}.
 *
 * @author Marko Salmela
 */
public abstract class LayoutSpec {

    /**
     * The index of the specified row or column.
     */
    protected int mIndex;
    /**
     * The fixed size of the specified row or column.
     */
    protected int mFixedSize;

    /**
     * The maximum size of the specified row or column.
     */
    protected int mMaximumSize;
    /**
     * The minimum size of the specified row or column.
     */
    protected int mMinimumSize;
    /**
     * The preferred size of the specified row or column.
     */
    protected int mPreferredSize;
    /**
     * The Current size of the specified row or column.
     */
    protected int mSize;
    /**
     * The resizing policy of the specified row or column.
     */
    protected ResizePolicy mResizePolicy;
    /**
     * The {@link GridLayout} that owns this {@LayoutSpec}.
     */
    protected GridLayout mLayout;

    /**
     * Constructs a new instance of {@LayoutSpec} for the given {@link GridLayout}.
     *
     * @param pLayout A {@link GridLayout}.
     */
    protected LayoutSpec(final GridLayout pLayout) {
        mLayout = pLayout;
        mFixedSize = 0;
        mResizePolicy = ResizePolicy.FIXED;
        mMaximumSize = Integer.MAX_VALUE;
        mMinimumSize = 0;
        mPreferredSize = 0;
        mSize = 0;
    }

    /**
     * Gets the index of the specified row or column.
     *
     * @return The index as an {@code int}.
     */
    public final int getIndex() {
        return mIndex;
    }

    /**
     * Sets the index of the specified row or column.
     *
     * @param pIndex The index as an {@code int}.
     */
    public void setIndex(final int pIndex) {
        mIndex = pIndex;
    }

    /**
     * Gets the {@link ResizePolicy}.
     *
     * @return A {@link ResizePolicy}.
     */
    public final ResizePolicy getResizePolicy() {
        return mResizePolicy;
    }

    /**
     * Sets the {@link ResizePolicy}.
     *
     * @param pPolicy A {@link ResizePolicy}.
     */
    public final void setResizePolicy(final ResizePolicy pPolicy) {
        mResizePolicy = pPolicy;
    }

    /**
     * Gets the {@link GridLayout} that contains this {@LayoutSpec}.
     *
     * @return A {@link GridLayout}.
     */
    public final GridLayout getLayout() {
        return mLayout;
    }

    /**
     * Sets the {@link GridLayout} defined to this {@LayoutSpec}.
     *
     * @param pLayout A {@link GridLayout}.
     */
    protected void setLayout(final GridLayout pLayout) {
        if (mLayout != null) {
            throw new IllegalStateException("LayoutCell is already assigned to a CellLayout.");
        }

        mLayout = pLayout;
    }

    /**
     * Gets the fixed size set for this {@LayoutSpec}.
     *
     * @return The fixed size as an {@code int}.
     */
    public int getFixedSize() {
        return mFixedSize;
    }

    /**
     * Sets the fixed size set for this {@LayoutSpec}.
     *
     * @param pSize The fixed size as an {@code int}.
     */
    public void setFixedSize(final int pSize) {
        mFixedSize = pSize;
    }

    /**
     * Gets the maximum size set for this {@LayoutSpec}.
     *
     * @return The maximum size as an {@code int}.
     */
    public int getMaximumSize() {
        return mMaximumSize;
    }

    /**
     * Sets the maximum size set for this {@LayoutSpec}.
     *
     * @param pSize The maximum size as an {@code int}.
     */
    public void setMaximumSize(final int pSize) {
        mMaximumSize = pSize;
    }

    /**
     * Gets the minimum size set for this {@LayoutSpec}.
     *
     * @return The minimum size as an {@code int}.
     */
    public int getMinimumSize() {
        return mMinimumSize;
    }

    /**
     * Sets the minimum size set for this {@LayoutSpec}.
     *
     * @param pSize The minimum size as an {@code int}.
     */
    public void setMinimumSize(final int pSize) {
        mMinimumSize = pSize;
    }

    /**
     * Gets the preferred size set for this {@LayoutSpec}.
     *
     * @return The preferred size as an {@code int}.
     */
    public int getPreferredSize() {
        return mPreferredSize;
    }

    /**
     * Sets the preferred size set for this {@LayoutSpec}.
     *
     * @param pSize The preferred size as an {@code int}.
     */
    public void setPreferredSize(final int pSize) {
        mPreferredSize = pSize;
    }

    /**
     * Gets the current size set for this {@LayoutSpec}.
     *
     * @return The size as an {@code int}.
     */
    public int getSize() {
        return mSize;
    }

    /**
     * Gets the current size for this {@LayoutSpec}.
     *
     * @param pSize The ew size as an {@code int}.
     */
    public void setSize(final int pSize) {
        mSize = pSize;
    }

    /**
     * Tests whether the layout cell defined by this {@LayoutSpec} is visible or not.
     *
     * @return A {@code boolean} value.
     */
    public boolean isVisible() {
        return true;
    }

    /**
     * Tests if this {@link LayoutSpec} defines a fix sized row or column.
     *
     * @return A {@code boolean} value.
     */
    public final boolean isFixed() {
        return (mResizePolicy == ResizePolicy.FIXED);
    }
}
