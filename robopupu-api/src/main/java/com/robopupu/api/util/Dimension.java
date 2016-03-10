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

public class Dimension {

    public int mHeight;
    public int mWidth;

    public Dimension() {
        this(0, 0);
    }

    public Dimension(final int width, final int height) {
        mWidth = width;
        mHeight = height;
    }

    public Dimension(final Dimension source) {
        mWidth = source.mWidth;
        mHeight = source.mHeight;
    }

    public final int getHeight() {
        return mHeight;
    }


    public void setHeight(final int height) {
        mHeight = height;
    }

    public final int getWidth() {
        return mWidth;
    }

    public void setWidth(final int width) {
        mWidth = width;
    }

    public void set(final int width, final int height) {
        mWidth = width;
        mHeight = height;
    }

    public void set(final Dimension value) {
        mWidth = value.mWidth;
        mHeight = value.mHeight;
    }
}
