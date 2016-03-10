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

/**
 * {@code Direction} defines a new enum type for representing directions.
 */
public enum Direction {

    NORTH("North", 0), //
    NORTH_EAST("North East", 45), //
    EAST("East", 90), //
    SOUTH_EAST("South East", 135), //
    SOUTH("South", 180), //
    SOUTH_WEST("South West", 225), //
    WEST("West", 270), //
    NORTH_WEST("North West", 315);

    private final float mDegrees;
    private final String mLabel;

    Direction(final String label, final float degrees) {
        mLabel = label;
        mDegrees = degrees;
    }

    public String getLabel() {
        return mLabel;
    }

}
