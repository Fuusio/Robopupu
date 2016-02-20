/*
 * Copyright (C) 2016 Marko Salmela.
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
package org.fuusio.api.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import org.fuusio.api.graphics.Line;

/**
 * {@link VectorIcon} extends {@link Drawable} to provide an abstract base class TODO
 */
public abstract class VectorIcon {

    private static final int SEGMENT_COUNT = 6;

    protected final IconSegment[] mSegments;

    protected RectF mBounds;
    protected float mDensityFactor;
    protected boolean mInitialised;

    protected VectorIcon() {
        mSegments = new IconSegment[SEGMENT_COUNT];
        mInitialised = false;
    }

    protected abstract void initialise();

    protected Paint.Cap getCap(final int index) {
        return Paint.Cap.SQUARE;
    }

    public void setBounds(final RectF bounds) {
        mBounds = bounds;
    }

    public void setDensityFactor(final float densityFactor) {
        mDensityFactor = densityFactor;
    }

    protected int toPx(final float dp) {
        return (int)(dp * mDensityFactor);
    }

    public static class IconSegment extends Line {

        private final VectorIcon mIcon;
        private final int mIndex;

        private IconSegment mConnectedSegment;

        public IconSegment(final VectorIcon icon, final int index, final float startX, final float startY, final float endX, final float endY) {
            super(startX, startY, endX, endY);
            mIcon = icon;
            mIndex = index;
        }

        @Override
        public void draw(final Canvas canvas, final Paint paint) {
            paint.setStrokeCap(mIcon.getCap(mIndex));
            super.draw(canvas, paint);
        }
    }

    public abstract VectorIcon copy();

    public IconSegment getSegment(final int index) {
        return mSegments[index];
    }

    public void draw(final Canvas canvas, final Paint paint) {

        if (!mInitialised) {
            initialise();
        }

        for (int i = SEGMENT_COUNT - 1; i >= 0; i--) {
            mSegments[i].draw(canvas, paint);
        }
    }
}
