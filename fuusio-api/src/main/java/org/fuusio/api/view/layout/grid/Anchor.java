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
package org.fuusio.api.view.layout.grid;

/**
 * {@code Anchor} defines an enumerated type for representing anchoring directions (up, left,
 * down, and right) for {@link LayoutSpec}s used in {@link ViewArea}.
 */
public enum Anchor {

    /**
     * The central position in the area of a {@link LayoutSpec}.
     */
    CENTER("Center"),

    /**
     * The top-middle position in the area of a {@link LayoutSpec}.
     */
    NORTH("North"),

    /**
     * The top-right corner position in the area of a {@link LayoutSpec}.
     */
    NORTH_EAST("NorthEast"),

    /**
     * The right-middle position in the area of a {@link LayoutSpec}.
     */
    EAST("East"),

    /**
     * The bottom-right corner position in the area of a {@link LayoutSpec}.
     */
    SOUTH_EAST("SouthEast"),

    /**
     * The bottom-middle position in the area of a {@link LayoutSpec}.
     */
    SOUTH("SOUTH"),

    /**
     * The bottom-left corner position in the area of a {@link LayoutSpec}.
     */
    SOUTH_WEST("SouthWest"),

    /**
     * The left-middle position in the area of a cell.
     */
    WEST("West"),

    /**
     * The top-left corner position in the area of a {@link LayoutSpec}.
     */
    NORTH_WEST("NorthWest");

    /**
     * The displayable label of {@code Anchor} item values.
     */
    private final String mLabel;

    /**
     * Constructs a new instance of {@code Anchor} with the given displayable label.
     *
     * @param pLabel A {@link String} for displayable label.
     */
    Anchor(final String pLabel) {
        mLabel = pLabel;
    }

    /**
     * Gets the displayable label for this {@code Anchor}.
     *
     * @return A {@link String} for displayable label.
     */
    public final String getLabel() {
        return mLabel;
    }

    /**
     * Gets a {@link String} representation of this {@code Anchor}.
     *
     * @return A {@link String}.
     */
    @Override
    public String toString() {
        return mLabel;
    }
}
