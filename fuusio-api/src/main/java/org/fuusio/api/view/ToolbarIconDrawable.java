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

import org.fuusio.api.graphics.Line;

import java.util.HashMap;

/**
 * {@link ToolbarIconDrawable} extends {@link Drawable} to implement a Toolbar icon that provides
 * animated transition between various visual icons: Back Icon, Check Icon, Close Icon, and
 * Menu Icon.
 */
public class ToolbarIconDrawable extends Drawable implements Animatable {

    private final float mDensityFactor;
    private final Paint mLinePaint;
    private final HashMap<IconState, MorphableIcon> mMorphableIcons;

    private AnimatorSet mAnimation;
    private long mAnimationDuration;
    private IconState mCurrentState;
    private boolean mInitialised;
    private MorphableIcon mMorphedIcon;
    private IconState mSourceIconState;
    private IconState mTargetIconState;
    private boolean mVisible;

    public ToolbarIconDrawable(final Context context) {
        this(context, IconState.MENU);
    }

    public ToolbarIconDrawable(final Context context, final IconState iconState) {
        final Resources resources = context.getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        mDensityFactor = displayMetrics.densityDpi / 160.0f;
        mAnimationDuration = 300;
        mCurrentState = iconState;
        mInitialised = false;
        mMorphableIcons = new HashMap<>();
        mLinePaint = new Paint ();
        mVisible = true;
    }

    protected void initialise() {
        final Rect bounds = getBounds();

        if (bounds.width() > 0) {

            // Create MorphableIcons

            for (final IconState state : IconState.values()) {
                final MorphableIcon icon = MorphableIcon.create(this, state, bounds);
                mMorphableIcons.put(state, icon);
            }

            // Initialise lines Paint

            initialiseLinePaint(mLinePaint);
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
        return mLinePaint.getColor();
    }

    public void setColor(final int color) {
        mLinePaint.setColor(color);
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
     * Gets the current state of this {@link ToolbarIconDrawable}
     * @return The state as a {@link IconState}.
     */
    public IconState getIconState() {
        return mCurrentState;
    }

    /**
     * Sets the current state of this {@link ToolbarIconDrawable}
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
        return mLinePaint.getStrokeWidth();
    }

    /**
     * Sets the width for stroking the icon lines. Pass 0 to stroke in hairline mode.
     * Hairlines always draws a single pixel independent of the {@link Canvas}'s matrix.
     *
     * @param width set the paint's stroke width as DP units, used whenever the {@link Paint)'s
     *              style is {@code Stroke} or {@code StrokeAndFill}.
     */
    public void setStrokeWidth(final int width) {
        mLinePaint.setStrokeWidth(toPx(width));
        invalidateSelf();
    }

    @Override
    public void setAlpha(final int alpha) {
        mLinePaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(final ColorFilter filter) {
        mLinePaint.setColorFilter(filter);
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
            mMorphedIcon.draw(canvas, mLinePaint);
        } else {
            mMorphableIcons.get(mCurrentState).draw(canvas, mLinePaint);
        }
    }

    private AnimatorSet createAnimation() {

        final Property<IconSegment, PointF> startPointProperty = new Property<IconSegment, PointF>(PointF.class, "startPoint") {
            @Override
            public PointF get(final IconSegment line) {
                return line.getStart();
            }

            @Override
            public void set(final IconSegment line, final PointF value) {
                line.setStart(value);
            }
        };

        final Property<IconSegment, PointF> endPointProperty = new Property<IconSegment, PointF>(PointF.class, "endPoint") {
            @Override
            public PointF get(final IconSegment line) {
                return line.getEnd();
            }

            @Override
            public void set(final IconSegment line, final PointF value) {
                line.setEnd(value);
            }
        };

        final TypeEvaluator<PointF>  pointEvaluator = new TypeEvaluator<PointF> () {
            public PointF evaluate(final float fraction, final PointF startValue, final PointF endValue) {
                final float dx = endValue.x - startValue.x;
                final float dy = endValue.y - startValue.y;
                return new PointF(startValue.x + fraction * dx, startValue.y + fraction * dy);
            }
        };

        mMorphedIcon = mMorphableIcons.get(mSourceIconState).copy();

        final MorphableIcon sourceIcon = mMorphedIcon;
        final MorphableIcon targetIcon = mMorphableIcons.get(mTargetIconState).copy();
        final AnimatorSet animation = new AnimatorSet();
        final ObjectAnimator[] animators = new ObjectAnimator[7];

        int index = 0;

        for (int i = 0; i < 3; i++) {
            final IconSegment sourceLine = sourceIcon.getSegment(i);
            final IconSegment targetLine = targetIcon.getSegment(i);

            animators[index++] = ObjectAnimator.ofObject(sourceLine, startPointProperty, pointEvaluator, sourceLine.getStart(), targetLine.getStart());
            animators[index++] = ObjectAnimator.ofObject(sourceLine, endPointProperty, pointEvaluator, sourceLine.getEnd(), targetLine.getEnd());
        }

        final Property<ToolbarIconDrawable, Float> fractionProperty = new Property<ToolbarIconDrawable, Float>(Float.class, "fraction") {

            private float mFraction;

            @Override
            public Float get(final ToolbarIconDrawable drawable) {
                return mFraction;
            }

            @Override
            public void set(final ToolbarIconDrawable drawable, final Float fraction) {
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
                mMorphedIcon = null;
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

    private static class IconSegment extends Line {

        private final int mIndex;
        private final IconState mState;

        public IconSegment(final int index, final IconState state, final Line line) {
            super(line);
            mIndex = index;
            mState = state;
        }

        @Override
        public void draw(final Canvas canvas, final Paint paint) {
            final PointF start = getStart();
            final PointF middle = getMiddle();
            final PointF end = getEnd();

            paint.setStrokeCap(mState.getCap(mIndex));
            canvas.drawLine(start.x, start.y, middle.x, middle.y, paint);
            paint.setStrokeCap(Paint.Cap.SQUARE);
            canvas.drawLine(middle.x, middle.y, end.x, end.y, paint);
        }
    }

    private static class MorphableIcon {

        private static final int SEGMENT_COUNT = 3;

        private final IconSegment[] mSegments;
        private final IconState mState;

        private  MorphableIcon(final IconState state, final Line[] lines) {
            mState = state;
            mSegments = new IconSegment[SEGMENT_COUNT];

            for (int i = 0; i < SEGMENT_COUNT; i++) {
                mSegments[i] = new IconSegment(i, mState, lines[i]);
            }
        }

        public static MorphableIcon create(final ToolbarIconDrawable drawable, final IconState state, final Rect bounds) {

            switch (state) {

                case BACK: {
                    final Line[] lines = {
                            new Line(bounds.left, bounds.centerY(), bounds.centerX(), bounds.top),
                            new Line(bounds.left, bounds.centerY(), bounds.right, bounds.centerY()),
                            new Line(bounds.left, bounds.centerY(), bounds.centerX(), bounds.right),
                    };

                    return new MorphableIcon(state, lines);
                }

                case CLOSE: {

                    final Line[] lines = {
                            new Line(bounds.left, bounds.bottom, bounds.right, bounds.top),
                            new Line(bounds.centerX(), bounds.centerY(), bounds.centerX(), bounds.centerY()),
                            new Line(bounds.left, bounds.top, bounds.right, bounds.bottom),
                    };

                    return new MorphableIcon(state, lines);
                }

                case MENU: {

                    final float padding = drawable.toPx(2);

                    final Line[] lines = {
                            new Line(bounds.left, bounds.top + padding, bounds.right, bounds.top + padding),
                            new Line(bounds.left, bounds.centerY(), bounds.right, bounds.centerY()),
                            new Line(bounds.left, bounds.bottom - padding, bounds.right, bounds.bottom - padding),
                    };

                    return new MorphableIcon(state, lines);
                }

                case TICK: {

                    final Line[] lines = {
                            new Line(bounds.centerX(), bounds.bottom, bounds.left, bounds.centerY()),
                            new Line(bounds.centerX(), bounds.centerY(), bounds.centerX(), bounds.centerY()),
                            new Line(bounds.centerX(), bounds.bottom, bounds.right, bounds.top),
                    };

                    return new MorphableIcon(state, lines);
                }
            }
            throw new IllegalStateException("Unhandled IconState");
        }

        public MorphableIcon copy() {
            return new MorphableIcon(mState, mSegments);
        }

        public IconSegment getSegment(final int index) {
            return mSegments[index];
        }

        public void draw(final Canvas canvas, final Paint paint) {

            for (int i = SEGMENT_COUNT - 1; i >= 0; i--) {
                mSegments[i].draw(canvas, paint);
            }
        }
    }
}
