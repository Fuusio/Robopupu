// ============================================================================
// Floxp.com : Java Class Source File
// ============================================================================
//
// Class: TextDrawable
// Package: FloXP.com Android APIs (org.fuusio.robopupu.api) -
// Graphics API (org.fuusio.robopupu.api.graphics)
//
// Author: Marko Salmela
//
// Copyright (C) Marko Salmela, 2009-2011. All Rights Reserved.
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

package org.fuusio.api.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * {@link TextDrawable} extends {@link Drawable} for drawing text.
 */
public class TextDrawable extends Drawable {

    /**
     * A {@code boolean} value specifying whether drawing is antialiazed.
     */
    protected boolean mAntialized;

    /**
     * A {@code boolean} flag specifying whether the background of this {@link TextDrawable} is
     * drawn.
     */
    protected boolean mBackgroundDrawn;

    /**
     * The {@link Paint} used for drawing the background of this {@link TextDrawable}.
     */
    protected Paint mBackgroundPaint;

    /**
     * The {@link Paint} used for filling the displayed font glyphs.
     */
    protected Paint mFillPaint;

    /**
     * The horizontal alignment of the display text.
     */
    protected HorizontalAlignment mHorizontalAlignment;

    /**
     * The opacity value of this {@link TextDrawable}.
     */
    protected int mOpacity;

    /**
     * A {@code boolean} flag indicating whether the stroke of the outlines of the font glyphs
     * displayed by this {@code TextNode} are drawn.
     */
    protected boolean mStroked;

    /**
     * The {@link Paint} used for drawing the stroke that outlines the displayed font glyphs.
     */
    protected Paint mStrokePaint;

    /**
     * The {@link String} to be displayed.
     */
    protected String mText;

    /**
     * The bounding box for the set text content.
     */
    protected Rect mTextBounds;

    /**
     * The vertical alignment of the display text.
     */
    protected VerticalAlignment mVerticalAlignment;

    /**
     * Constructs a new instance of {@link TextDrawable}.
     */
    public TextDrawable() {
        mAntialized = false;
        mBackgroundDrawn = false;
        mBackgroundPaint = createDefaultBackgroundPaint();
        mFillPaint = createDefaultFillPaint();
        mHorizontalAlignment = HorizontalAlignment.LEFT;
        mOpacity = 0xff;
        mStroked = false;
        mStrokePaint = createDefaultStrokePaint();
        mText = new String();
        mTextBounds = new Rect();
        mVerticalAlignment = VerticalAlignment.CENTER;
    }

    /**
     * Tests whether the drawing of this (@link TextDrawable) is set to be antialized.
     *
     * @return {@code boolean} value.
     */
    public boolean isAntialized() {
        return mAntialized;
    }

    /**
     * Sets the drawing of this (@link TextDrawable) to be antialized depending on the given
     * {@code boolean} value.
     *
     * @param antialized A {@code boolean} value.
     */
    public void setAntialized(final boolean antialized) {
        mAntialized = antialized;
    }

    /**
     * Gets the {@link Paint} used for drawing the background of this {@link TextDrawable}.
     *
     * @return A {@link Paint}.
     */
    public Paint getBackgroundPaint() {
        return mBackgroundPaint;
    }

    /**
     * Sets the {@link Paint} used for drawing the background of this {@link TextDrawable}.
     *
     * @param paint A {@link Paint}.
     */
    public void setBackgroundPaint(final Paint paint) {
        assert (paint != null);
        mBackgroundPaint = paint;
        mBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * Sets the alpha value of the {@link Color} of the {@link Paint} used for drawing the
     * background of this {@link TextDrawable}.
     *
     * @param alpha The alpha value as an {@code int}.
     */
    public void setBackgroundAlpha(final int alpha) {
        mBackgroundPaint.setAlpha(alpha);
    }

    /**
     * Sets the {@link Color} of {@link Paint} used for drawing the background of this
     * {@link TextDrawable}.
     *
     * @param color A {@link Paint}.
     */
    public void setBackgroundColor(final int color) {
        mBackgroundPaint.setColor(color);
    }

    /**
     * Tests whether the background of this {@link TextDrawable} is drawn.
     *
     * @return A {@code boolean} value.
     */
    public boolean isBackgroundDrawn() {
        return mBackgroundDrawn;
    }

    /**
     * Sets the background of this {@link TextDrawable} to be drawn depending on the given
     * {@code boolean} value.
     *
     * @param isDrawn A {@code boolean} value.
     */
    public void setBackgroundDrawn(final boolean isDrawn) {
        mBackgroundDrawn = isDrawn;
    }

    /**
     * Gets the {@link Paint} used for filling the displayed font glyphs.
     *
     * @return A {@link Paint}.
     */
    public Paint getFillPaint() {
        return mFillPaint;
    }

    /**
     * Sets the {@link Paint} used for filling the displayed font glyphs.
     *
     * @param paint A {@link Paint}.
     */
    public void setFillPaint(final Paint paint) {
        assert (paint != null);
        mFillPaint = paint;
        mFillPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * Sets the alpha value of the {@link Color} of the {@link Paint} used filling the displayed
     * font glyphs.
     *
     * @param alpha The alpha value as an {@code int}.
     */
    public void setFillAlpha(final int alpha) {
        mFillPaint.setAlpha(alpha);
    }

    /**
     * Sets the {@link Color} of {@link Paint} used for filling the displayed font glyphs.
     *
     * @param color the color as an {@code int} value.
     */
    public void setFillColor(final int color) {
        mFillPaint.setColor(color);
    }

    /**
     * Gets the horizontal alignment.
     *
     * @return The alignment value as a {@link HorizontalAlignment}.
     */
    public HorizontalAlignment getHorizontalAlignment() {
        return mHorizontalAlignment;
    }

    /**
     * Sets the horizontal alignment.
     *
     * @param alignment The alignment value as a {@link HorizontalAlignment}.
     */
    public void setHorizontalAlignment(final HorizontalAlignment alignment) {
        mHorizontalAlignment = alignment;
    }

    /**
     * Gets the the intrinsic height of the underlying drawable object. Returns -1 if it has no
     * intrinsic height, such as with a solid color.
     *
     * @return The intrinsic height as an {@code int} value.
     */
    @Override
    public int getIntrinsicHeight() {
        if (mText != null && mFillPaint != null) {
            mFillPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
            return mTextBounds.height();
        }

        return super.getIntrinsicHeight();
    }

    /**
     * Gets the the intrinsic width of the underlying drawable object. Returns -1 if it has no
     * intrinsic width, such as with a solid color.
     *
     * @return The intrinsic width as an {@code int} value.
     */
    @Override
    public int getIntrinsicWidth() {
        if (mText != null && mFillPaint != null) {
            mFillPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
            return mTextBounds.width();
        }

        return super.getIntrinsicWidth();
    }

    @Override
    public void setAlpha(final int alpha) {
    }

    @Override
    public void setColorFilter(final ColorFilter colorFilter) {
    }

    /**
     * Gets the opacity value.
     *
     * @return The opacity value as an {@code int}.
     */
    @Override
    public int getOpacity() {
        return mOpacity;
    }

    /**
     * Sets the opacity value.
     *
     * @param opacity The opacity value as an {@code int}.
     */
    public void setOpacity(final int opacity) {
        mOpacity = opacity;
    }

    /**
     * Gets the {@link Paint} used for drawing the stroke that outlines the displayed font glyphs.
     *
     * @return A {@link Paint}.
     */
    public Paint getStrokePaint() {
        return mStrokePaint;
    }

    /**
     * Sets the {@link Paint} used for drawing the stroke that outlines the displayed font glyphs.
     *
     * @param paint A {@link Paint}.
     */
    public void setStrokePaint(final Paint paint) {
        assert (paint != null);
        mStrokePaint = paint;
        mStrokePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Sets the cap parameter of {@link Paint} used for drawing the stroke that outlines the
     * displayed font glyphs.
     *
     * @param cap A {@link Paint.Cap} value.
     */
    public void setStrokeCap(final Paint.Cap cap) {
        mStrokePaint.setStrokeCap(cap);
    }

    /**
     * Sets the alpha value of the {@link Color} of the {@link Paint} used for drawing the stroke
     * the displayed font glyphs.
     *
     * @param alpha The alpha value as an {@code int}.
     */
    public void setStrokeAlpha(final int alpha) {
        mStrokePaint.setAlpha(alpha);
    }

    /**
     * Sets the {@link Color} of {@link Paint} used for drawing the stroke that outlines the
     * displayed font glyphs.
     *
     * @param color the color as a {@link int} value.
     */
    public void setStrokeColor(final int color) {
        mStrokePaint.setColor(color);
    }

    public void setStroked(final boolean stroked) {
        mStroked = stroked;
    }

    /**
     * Sets the join parameter of {@link Paint} used for drawing the stroke that outlines the
     * displayed font glyphs.
     *
     * @param join A {@link Paint.Join} value.
     */
    public void setStrokeJoin(final Paint.Join join) {
        mStrokePaint.setStrokeJoin(join);
    }

    /**
     * Sets the miter parameter of {@link Paint} used for drawing the stroke that outlines the
     * displayed font glyphs.
     *
     * @param miter A {@code float} value.
     */
    public void setStrokeMiter(final float miter) {
        mStrokePaint.setStrokeMiter(miter);
    }

    /**
     * Sets the width parameter of {@link Paint} used for drawing the stroke that outlines the
     * displayed font glyphs.
     *
     * @param width A {@code float} value.
     */
    public void setStrokeWidth(final float width) {
        mStrokePaint.setStrokeWidth(width);
    }

    /**
     * Gets the {@link String} to be displayed.
     *
     * @return A {@link String}.
     */
    public String getText() {
        return mText;
    }

    /**
     * Sets the {@link String} to be displayed.
     *
     * @param text A {@link String}.
     */
    public void setText(final String text) {
        mText = text;
    }

    /**
     * Gets the text size.
     *
     * @return The size as a {@link float}.
     */
    public float getTextSize() {
        return mFillPaint.getTextSize();
    }

    /**
     * Sets the text size.
     *
     * @param size The size as a {@link float}.
     */
    public void setTextSize(final float size) {
        mFillPaint.setTextSize(size);
        mStrokePaint.setTextSize(size);
    }

    /**
     * Gets the used {@link Typeface}.
     *
     * @return A {@link Typeface}. May be {@link null} if not set.
     */
    public Typeface getTypeface() {
        return mFillPaint.getTypeface();
    }

    /**
     * Sets the used {@link Typeface}.
     *
     * @param typeface A {@link Typeface}. May be {@link null}.
     */
    public void setTypeface(final Typeface typeface) {
        mFillPaint.setTypeface(typeface);
        mStrokePaint.setTypeface(typeface);
    }

    /**
     * Gets the vertical alignment.
     *
     * @return The alignment value as a {@link VerticalAlignment}.
     */
    public VerticalAlignment getVerticalAlignment() {
        return mVerticalAlignment;
    }

    /**
     * Sets the vertical alignment.
     *
     * @param alignment The alignment value as a {@link VerticalAlignment}.
     */
    public void setVerticalAlignment(final VerticalAlignment alignment) {
        mVerticalAlignment = alignment;
    }

    /**
     * Creates an appropriate instance of {@link Paint} to be used as a default background paint to
     * draw the background of a {@link TextDrawable}.
     *
     * @return The created {@link Paint} instance.
     */
    protected Paint createDefaultBackgroundPaint() {
        final Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    /**
     * Creates an appropriate instance of {@link Paint} to be used as a default fillPaint to fill
     * the interior of a {@link TextDrawable}.
     *
     * @return The created {@link Paint} instance.
     */
    protected Paint createDefaultFillPaint() {
        final Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

    /**
     * Creates an appropriate instance of {@link Paint} to be used as a default fillPaint of a
     * stroke drawn around the outline of a {@link TextDrawable}.
     *
     * @return The created {@link Paint} instance.
     */
    protected Paint createDefaultStrokePaint() {
        final Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    /**
     * Clears the contents of the drawn {@code String}.
     */
    public void clearText() {
        mText = "";
    }

    /**
     * Applies the rendering parameters defined for this {@link TextDrawable}.
     */
    protected void applyRenderingParameters() {
        if (mOpacity < 1.0f) {
            float alphaValue = mBackgroundPaint.getAlpha();
            int alpha = (int) (alphaValue * mOpacity);
            mBackgroundPaint.setAlpha(alpha);

            alphaValue = mFillPaint.getAlpha();
            alpha = (int) (alphaValue * mOpacity);
            mFillPaint.setAlpha(alpha);

            alphaValue = mStrokePaint.getAlpha();
            alpha = (int) (alphaValue * mOpacity);
            mStrokePaint.setAlpha(alpha);
        }
    }

    /**
     * Draws this {@link TextDrawable} on the given {@link Canvas}.
     *
     * @param canvas A {@link Canvas}.
     */
    @Override
    public void draw(final Canvas canvas) {
        if (mText != null) {
            final Rect bounds = getBounds();
            int x = bounds.left;
            int y = bounds.top;

            mFillPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);

            switch (mHorizontalAlignment) {
                case LEFT: {
                    break;
                }
                case CENTER: {
                    x += (bounds.width() - mTextBounds.width()) / 2;
                    break;
                }
                case RIGHT: {
                    x = bounds.right - mTextBounds.width();
                    break;
                }
            }

            switch (mVerticalAlignment) {
                case TOP: {
                    y += mTextBounds.height();
                    break;
                }
                case CENTER: {
                    y += (bounds.height() + mTextBounds.height()) / 2;
                    break;
                }
                case BOTTOM: {
                    y = bounds.bottom;
                    break;
                }
            }

            mFillPaint.setAntiAlias(mAntialized);
            mFillPaint.setTextAlign(mHorizontalAlignment.getPaintAlign());
            canvas.drawText(mText, x, y, mFillPaint);

            if (mStroked) {
                mStrokePaint.setAntiAlias(mAntialized);
                mStrokePaint.setTextAlign(mHorizontalAlignment.getPaintAlign());
                canvas.drawText(mText, x, y, mStrokePaint);
            }
        }
    }
}
