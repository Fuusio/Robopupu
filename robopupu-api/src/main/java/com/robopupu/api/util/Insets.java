/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.util;

public class Insets {

    public int mBottom;
    public int mLeft;
    public int mRight;
    public int mTop;

    public Insets() {
        this(0, 0, 0, 0);
    }

    public Insets(final int top, final int left, final int bottom, final int right) {
        mTop = top;
        mLeft = left;
        mBottom = bottom;
        mRight = right;
    }

    public Insets(final Insets source) {
        mTop = source.mTop;
        mLeft = source.mLeft;
        mBottom = source.mBottom;
        mRight = source.mRight;
    }

    public final int getBottom() {
        return mBottom;
    }

    public void setBottom(final int bottom) {
        mBottom = bottom;
    }

    public final int getLeft() {
        return mLeft;
    }

    public void setLeft(final int left) {
        mLeft = left;
    }

    public int getRight() {
        return mRight;
    }

    public void setRight(final int right) {
        mRight = right;
    }

    public final int getTop() {
        return mTop;
    }

    public void setTop(final int top) {
        mTop = top;
    }

    public void set(final int top, final int left, final int bottom, final int right) {
        mTop = top;
        mLeft = left;
        mBottom = bottom;
        mRight = right;
    }

    public void set(final Insets insets) {
        mTop = insets.mTop;
        mLeft = insets.mLeft;
        mBottom = insets.mBottom;
        mRight = insets.mRight;
    }
}
