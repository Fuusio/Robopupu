/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.util;

import android.view.MotionEvent;

public class TouchManager {

    private final int mMaxTouchPoints;
    private final Vector2D[] mPoints;
    private final Vector2D[] mPreviousPoints;

    public TouchManager(final int maxTouchPoints) {
        mMaxTouchPoints = maxTouchPoints;
        mPoints = new Vector2D[maxTouchPoints];
        mPreviousPoints = new Vector2D[maxTouchPoints];
    }

    public boolean isPressed(final int index) {
        return (mPoints[index] != null);
    }

    public int getPressCount() {
        int count = 0;

        for (final Vector2D point : mPoints) {
            if (point != null) {
                ++count;
            }
        }
        return count;
    }

    public Vector2D moveDelta(final int index) {

        if (isPressed(index)) {
            final Vector2D previous = mPreviousPoints[index] != null ? mPreviousPoints[index]
                    : mPoints[index];
            return Vector2D.subtract(mPoints[index], previous);
        } else {
            return new Vector2D();
        }
    }

    private static Vector2D getVector(final Vector2D vector1, final Vector2D vector2) {
        if (vector1 == null || vector2 == null) {
            throw new RuntimeException("Can't do this on nulls");
        }

        return Vector2D.subtract(vector2, vector1);
    }

    public Vector2D getPoint(final int index) {
        return mPoints[index] != null ? mPoints[index] : new Vector2D();
    }

    public Vector2D getPreviousPoint(final int index) {
        return mPreviousPoints[index] != null ? mPreviousPoints[index] : new Vector2D();
    }

    public Vector2D getVector(final int indexA, final int indexB) {
        return getVector(mPoints[indexA], mPoints[indexB]);
    }

    public Vector2D getPreviousVector(final int indexA, final int indexB) {
        if (mPreviousPoints[indexA] == null || mPreviousPoints[indexB] == null) {
            return getVector(mPoints[indexA], mPoints[indexB]);
        } else {
            return getVector(mPreviousPoints[indexA], mPreviousPoints[indexB]);
        }
    }

    public void update(final MotionEvent event) {

        final int action = event.getAction() & MotionEvent.ACTION_MASK;

        if (action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_UP) {

            final int index = event.getAction() >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

            if (index < mMaxTouchPoints) {
                mPreviousPoints[index] = null;
                mPoints[index] = null;
                // System.out.println("CLEARED: " + index);
            }
        } else {

            final int pointerCount = event.getPointerCount();

            for (int i = 0; i < mMaxTouchPoints; ++i) {

                if (i < pointerCount) {
                    final int index = event.getPointerId(i);
                    final Vector2D newPoint = new Vector2D(event.getX(i), event.getY(i));

                    if (mPoints[index] == null) {
                        mPoints[index] = newPoint;
                        // System.out.println("ADDED: " + index);
                    } else {
                        if (mPreviousPoints[index] != null) {
                            mPreviousPoints[index].set(mPoints[index]);
                        } else {
                            mPreviousPoints[index] = new Vector2D(newPoint);
                        }

                        if (Vector2D.subtract(mPoints[index], newPoint).getLength() < 64) {
                            mPoints[index].set(newPoint);
                        }

                        // System.out.println("UPDATED: " + index);
                    }
                } else {
                    mPreviousPoints[i] = null;
                    mPoints[i] = null;
                }
            }
        }
    }
}
