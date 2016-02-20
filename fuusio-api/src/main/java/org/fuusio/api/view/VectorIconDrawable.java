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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Property;

import java.util.HashMap;

/**
 * {@link VectorIconDrawable} implements {@link Drawable} that uses instances of {@link VectorIcon}
 * to draw vectorised icons. {@link VectorIconDrawable} implements also {@link Animatable} interface
 * to provide animated transitions where given {@link VectorIcon} is morphed to another
 * {@link VectorIcon}.
 */
public class VectorIconDrawable extends Drawable implements Animatable {

    private final float mDensityFactor;
    private final Paint mSegmentPaint;
    private final HashMap<IconState, VectorIcon> mVectorIcons;

    private AnimatorSet mAnimation;
    private long mAnimationDuration;
    private IconState mCurrentState;
    private boolean mInitialised;
    private VectorIcon mAnimatedIcon;
    private IconState mSourceIconState;
    private IconState mTargetIconState;
    private boolean mVisible;

    public VectorIconDrawable(final Context context) {
        this(context, IconState.MENU);
    }

    public VectorIconDrawable(final Context context, final IconState iconState) {
        final Resources resources = context.getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        mDensityFactor = displayMetrics.densityDpi / 160.0f;
        mAnimationDuration = 300;
        mCurrentState = iconState;
        mInitialised = false;
        mVectorIcons = new HashMap();
        mSegmentPaint = new Paint ();
        mVisible = true;
    }

    protected void initialise() {
        final Rect bounds = getBounds();

        if (bounds.width() > 0) {
            
            // Initialise lines Paint

            initialiseLinePaint(mSegmentPaint);
            mInitialised = true;
        }
    }

    protected Paint initialiseLinePaint(final Paint paint) {
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(toPx(2.5f));
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    public int getColor() {
        return mSegmentPaint.getColor();
    }

    public void setColor(final int color) {
        mSegmentPaint.setColor(color);
        invalidateSelf();
    }

    /**
     * Gets the duration of the state morphing animation.
     * @return The duration as milliseconds.
     */
    public long getAnimationDuration() {
        return mAnimationDuration;
    }

    /**
     * Sets the duration of the state morphing animation.
     * @param duration The duration as milliseconds.
     */
    public void setAnimationDuration(final long duration) {
        mAnimationDuration = duration;
    }

    /**
     * Gets the current state of this {@link VectorIconDrawable}
     * @return The state as a {@link IconState}.
     */
    public IconState getIconState() {
        return mCurrentState;
    }

    /**
     * Sets the current state of this {@link VectorIconDrawable}
     * @param state The state as a {@link IconState}.
     */
    public void setIconState(final IconState state) {
        mCurrentState = state;
        invalidateSelf();
    }

    /**
     * Gets the width for stroking thr icon lines.
     *
     * @return The paint's stroke width, used whenever the paint's
     *              style is Stroke or StrokeAndFill.
     */
    public float getStrokeWidth() {
        return mSegmentPaint.getStrokeWidth();
    }

    /**
     * Sets the width for stroking the icon lines. Pass 0 to stroke in hairline mode.
     * Hairlines always draws a single pixel independent of the {@link Canvas}'s matrix.
     *
     * @param width set the paint's stroke width as DP units, used whenever the {@link Paint)'s
     *              style is {@code Stroke} or {@code StrokeAndFill}.
     */
    public void setStrokeWidth(final int width) {
        mSegmentPaint.setStrokeWidth(toPx(width));
        invalidateSelf();
    }

    @Override
    public void setAlpha(final int alpha) {
        mSegmentPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(final ColorFilter filter) {
        mSegmentPaint.setColorFilter(filter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    public void setVisible(final boolean visible) {
        mVisible = visible;
        invalidateSelf();
    }

    @Override
    public void start() {
        if (mAnimation == null && mSourceIconState != null && mTargetIconState != null) {
            mAnimation = createAnimation();
            mAnimation.start();
        }
    }

    public void startAnimation(final IconState sourceIconState, final IconState targetIconState) {
        mSourceIconState = sourceIconState;
        mTargetIconState = targetIconState;
        start();
    }

    @Override
    public void stop() {
        if (mAnimation != null) {
            mAnimation.cancel();
            mAnimation = null;
        }
    }

    @Override
    public boolean isRunning() {
        return (mAnimation != null);
    }

    @Override
    public void draw(final Canvas canvas) {
        if (!mVisible) {
            return;
        }

        if (!mInitialised) {
            initialise();
        }

        if (mAnimation != null) {
            mAnimatedIcon.draw(canvas, mSegmentPaint);
        } else {
            mVectorIcons.get(mCurrentState).draw(canvas, mSegmentPaint);
        }
    }

    private AnimatorSet createAnimation() {

        final Property<VectorIcon.IconSegment, PointF> startPointProperty = new Property<VectorIcon.IconSegment, PointF>(PointF.class, "startPoint") {
            @Override
            public PointF get(final VectorIcon.IconSegment segment) {
                return segment.getStart();
            }

            @Override
            public void set(final VectorIcon.IconSegment segment, final PointF value) {
                segment.setStart(value);
            }
        };

        final Property<VectorIcon.IconSegment, PointF> endPointProperty = new Property<VectorIcon.IconSegment, PointF>(PointF.class, "endPoint") {
            @Override
            public PointF get(final VectorIcon.IconSegment segment) {
                return segment.getEnd();
            }

            @Override
            public void set(final VectorIcon.IconSegment segment, final PointF value) {
                segment.setEnd(value);
            }
        };

        final TypeEvaluator<PointF>  pointEvaluator = new TypeEvaluator<PointF> () {
            public PointF evaluate(final float fraction, final PointF startValue, final PointF endValue) {
                final float dx = endValue.x - startValue.x;
                final float dy = endValue.y - startValue.y;
                return new PointF(startValue.x + fraction * dx, startValue.y + fraction * dy);
            }
        };

        mAnimatedIcon = mVectorIcons.get(mSourceIconState).copy();

        final VectorIcon sourceIcon = mAnimatedIcon;
        final VectorIcon targetIcon = mVectorIcons.get(mTargetIconState).copy();
        final AnimatorSet animation = new AnimatorSet();
        final ObjectAnimator[] animators = new ObjectAnimator[7];

        int index = 0;

        for (int i = 0; i < 3; i++) {
            final VectorIcon.IconSegment sourceSegment = sourceIcon.getSegment(i);
            final VectorIcon.IconSegment targetSegment = targetIcon.getSegment(i);

            animators[index++] = ObjectAnimator.ofObject(sourceSegment, startPointProperty, pointEvaluator, sourceSegment.getStart(), targetSegment.getStart());
            animators[index++] = ObjectAnimator.ofObject(sourceSegment, endPointProperty, pointEvaluator, sourceSegment.getEnd(), targetSegment.getEnd());
        }

        final Property<VectorIconDrawable, Float> fractionProperty = new Property<VectorIconDrawable, Float>(Float.class, "fraction") {

            private float mFraction;

            @Override
            public Float get(final VectorIconDrawable drawable) {
                return mFraction;
            }

            @Override
            public void set(final VectorIconDrawable drawable, final Float fraction) {
                mFraction = fraction;
                invalidateSelf();
            }
        };

        final TypeEvaluator<Float> fractionEvaluator = new TypeEvaluator<Float>() {
            public Float evaluate(final float fraction, final Float startValue, final Float endValue) {
                return fraction;
            }
        };

        animators[index] = ObjectAnimator.ofObject(this, fractionProperty, fractionEvaluator, 0.0f, 1.0f);

        for (int i = 0; i < animators.length; i++) {
            animators[i].setDuration(mAnimationDuration);
        }

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                setIconState(mTargetIconState);
                mAnimation = null;
                mAnimatedIcon = null;
            }
        });

        animation.playTogether(animators);
        return animation;
    }

    protected int toPx(final float dp) {
        return (int)(dp * mDensityFactor);
    }

    public enum IconState {
        BACK(Paint.Cap.SQUARE, Paint.Cap.ROUND, Paint.Cap.SQUARE),
        TICK(Paint.Cap.ROUND, Paint.Cap.ROUND, Paint.Cap.ROUND),
        CLOSE(Paint.Cap.SQUARE, Paint.Cap.SQUARE, Paint.Cap.SQUARE),
        MENU(Paint.Cap.SQUARE, Paint.Cap.SQUARE, Paint.Cap.SQUARE);

        private final Paint.Cap[] mCaps;

        IconState(final Paint.Cap cap0, final Paint.Cap cap1, final Paint.Cap cap2) {
            mCaps = new Paint.Cap[3];
            mCaps[0] = cap0;
            mCaps[1] = cap1;
            mCaps[2] = cap2;
        }

        public Paint.Cap getCap(final int index) {
            return mCaps[index];
        }

        public int getIndex() {
            return ordinal();
        }
    }
}
