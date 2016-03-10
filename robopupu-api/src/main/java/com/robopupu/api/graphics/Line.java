/*
 * Copyright (C) 2014-2015 Marko Salmela.
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
package com.robopupu.api.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * {@link Line} implements a simple graphical object to draw lines on a {@link Canvas) using
 * a given {@link Paint}.
 */
public class Line {

    private final PointF mStart;
    private final PointF mEnd;

    private Paint mPaint;
    private float mPivotX;
    private float mPivotY;

    public Line() {
        mStart = new PointF();
        mEnd = new PointF();
        mPivotX = 0.5f;
        mPivotY = 0.5f;
    }

    public Line(final Line line) {
        this();
        mStart.set(line.getStart());
        mEnd.set(line.getEnd());
    }

    public Line(final float startX, final float startY, final float endX, final float endY) {
        this();
        mStart.set(startX, startY);
        mEnd.set(endX, endY);
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(final Paint paint) {
        mPaint = paint;
    }

    public PointF getStart() {
        return mStart;
    }

    public PointF getMiddle() {
        final float dx = mEnd.x - mStart.x;
        final float dy = mEnd.y - mStart.y;
        return new PointF(mStart.x + dx / 2, mStart.y + dy / 2);
    }

    public PointF getPivot() {
        final float dx = mEnd.x - mStart.x;
        final float dy = mEnd.y - mStart.y;
        return new PointF(mStart.x + dx * mPivotX, mStart.y + dy * mPivotY);
    }

    public PointF getEnd() {
        return mEnd;
    }

    public float getPivotX() {
        return mPivotX;
    }

    public void setPivotX(final float value) {
        mPivotX = value;
    }

    public float getPivotY() {
        return mPivotY;
    }

    public void setPivotY(final float value) {
        mPivotY = value;
    }

    public Line setStart(final PointF start) {
        mStart.set(start);
        return this;
    }

    public Line setStart(final float x, final float y) {
        mStart.set(x, y);
        return this;
    }

    public Line setStartX(final float x) {
        mStart.set(x, mStart.y);
        return this;
    }

    public Line setStartY(final float y) {
        mStart.set(mStart.x, y);
        return this;
    }

    public Line setEnd(final PointF end) {
        mEnd.set(end);
        return this;
    }

    public Line setEnd(final float x, final float y) {
        mEnd.set(x, y);
        return this;
    }

    public Line setEndX(final float x) {
        mEnd.set(x, mEnd.y);
        return this;
    }

    public Line setEndY(final float y) {
        mEnd.set(mEnd.x, y);
        return this;
    }

    public Line translate(final float dx, final float dy) {
        mStart.offset(dx, dy);
        mEnd.offset(dx, dy);
        return this;
    }

    public Line translateStart(final float dx, final float dy) {
        mStart.offset(dx, dy);
        return this;
    }

    public Line translateEnd(final float dx, final float dy) {
        mEnd.offset(dx, dy);
        return this;
    }

    public Line rotate(final float radians) {
        final PointF pivot = getPivot();

        float length = (float)Math.pow(Math.pow(mStart.x - pivot.x, 2) + Math.pow(mStart.y - pivot.y , 2), 0.5);
        float dx = (float)(length * Math.cos(radians));
        float dy = (float)(length * Math.sin(radians));
        mStart.offset(dx, dy);

        length = (float)Math.pow(Math.pow(mEnd.x - pivot.x, 2) + Math.pow(mEnd.y - pivot.y , 2), 0.5);
        dx = (float)(length * Math.cos(radians));
        dy = (float)(length * Math.sin(radians));
        mEnd.offset(dx, dy);

        return this;
    }

    public Line rotateByAngle(final float angle) {
        return rotate((float)Math.toRadians(angle));
    }

    public void draw(final Canvas canvas) {
        draw(canvas, mPaint);
    }

    public void draw(final Canvas canvas, final Paint paint) {
        canvas.drawLine(mStart.x, mStart.y, mEnd.x, mEnd.y, paint);
    }

    public void set(final Line line) {
        mStart.set(line.mStart);
        mEnd.set(line.mEnd);
    }
}
