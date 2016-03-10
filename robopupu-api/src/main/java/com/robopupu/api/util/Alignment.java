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
 * {@code Alignment} defines a new enum type for representing priority values at five levels.
 *
 * @author Marko Salmela
 */
public enum Alignment {

    TOP("Top"), //
    LEFT("Left"), //
    BOTTOM("Bottom"), //
    RIGHT("Right"), //
    CENTER("Center");

    /**
     * The displayable name of this {@code Alignment}.
     */
    private final String mLabel;

    /**
     * Constructs a new instance of {@code Alignment} with the given displayable label.
     *
     * @param label A {@link String} for displayable label.
     */
    Alignment(final String label) {
        mLabel = label;
    }

    /**
     * Gets the displayable label of this {@code Alignment} value.
     *
     * @return The displayable label as a {@link String}.
     */
    public final String getLabel() {
        return mLabel;
    }
}
