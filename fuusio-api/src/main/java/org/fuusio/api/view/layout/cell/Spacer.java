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

/**
 * {@code Spacer} implements {@link LayoutCell} that is used to just fill space horizontally,
 * vertically, or both directions.
 */
public class Spacer extends LayoutCell {

    /**
     * {@code FillPolicy} specifies how the {@code Spacer} fills this cell.
     */
    protected FillPolicy mFillMode;

    /**
     * The fixed height of this {@code Spacer}. Value is set to be -1 if this {@code Spacer} if this
     * spacer does not have fixed height.
     */
    protected int mFixedHeight;

    /**
     * The fixed width of this {@code Spacer}. Value is set to be -1 if this {@code Spacer} if this
     * spacer does not have width height.
     */
    protected int mFixedWidth;

    /**
     * Constructs a new instance of {@code Spacer} for the given {@code CellLayout}. By default,
     * this instance fills space both horizontally and vertically.
     *
     * @param pLayout A {@code CellLayout}.
     */
    public Spacer(final CellLayout pLayout) {
        this(pLayout, FillPolicy.BOTH, -1, -1);
    }

    /**
     * Constructs a new instance of {@code Spacer} for the given {@code CellLayout}. The instance
     * fills space according to the given {@code FillPolicy}.
     *
     * @param pLayout      A {@code CellLayout}.
     * @param pFillMode    A {@code FillPolicy}.
     * @param pFixedWidth  The fixed width of this {@code Spacer}. Value is -1 if this {@code Spacer}
     *                     if this spacer does not have fixed width.
     * @param pFixedHeight The fixed height of this {@code Spacer}. Value is -1 if this
     *                     {@code Spacer} if this spacer does not have fixed height.
     */
    public Spacer(final CellLayout pLayout, final FillPolicy pFillMode, final int pFixedWidth,
                  final int pFixedHeight) {
        super(pLayout);
        mFillMode = pFillMode;
        mFixedHeight = pFixedHeight;
        mFixedWidth = pFixedWidth;
    }

    /**
     * Constructs a copy instance of the given {@code Spacer} for the given {@code CellLayout}.
     *
     * @param pLayout A {@code CellLayout}.
     * @param pSource A {@code Spacer}.
     */
    public Spacer(final CellLayout pLayout, final Spacer pSource) {
        super(pLayout, pSource);
        mFillMode = pSource.mFillMode;
        mFixedHeight = pSource.mFixedHeight;
        mFixedWidth = pSource.mFixedWidth;
    }


    /**
     * Gets the fill mode defined to this {@code Spacer}. Fill mode
     * is mode is used to specify how the component fills up the area calculated
     * for its container {@code LayoutCell}.  The possible fill mode values are:
     * {@code NONE}, {@code HORIZONTAL}, {@code VERTICAL},
     * and {@code BOTH}. The default value is {@code NONE</code.
     *
     * @return A {@link FillPolicy }.
     */
    public final FillPolicy getFillMode() {
        return mFillMode;
    }

    /**
     * Calculates the minimum, maximum, and preferred sizes of this {@code LayoutCell}.
     */
    @Override
    protected void measureSizes() {
        if (mFixedWidth >= 0) {
            mMaximumSize.mWidth = mFixedWidth;
            mMinimumSize.mWidth = mFixedWidth;
            mPreferredSize.mWidth = mFixedWidth;
        } else {
            mMaximumSize.mWidth = Integer.MAX_VALUE;
            mMinimumSize.mWidth = 0;
            mPreferredSize.mWidth = mMaximumSize.mWidth;
        }

        if (mFixedHeight >= 0) {
            mMaximumSize.mHeight = mFixedHeight;
            mMinimumSize.mHeight = mFixedHeight;
            mPreferredSize.mHeight = mFixedHeight;
        } else {
            mMaximumSize.mHeight = Integer.MAX_VALUE;
            mMinimumSize.mHeight = 0;
            mPreferredSize.mHeight = mMaximumSize.mHeight;
        }
    }
}
