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
 * {@code ResizePolicy} defines an enumerated type for representing the various resize policies for
 * {@link LayoutSpec} instances defined in a {@link org.fuusio.api.view.layout.grid.GridLayout}.
 *
 * @author Marko Salmela
 */
public enum ResizePolicy {

    /**
     * In Fixed policy, the width of a column or height of a row is not resizeable but is fixed.
     */
    FIXED("Fixed"),

    /**
     * In Resizable policy, the width of a column or height of a row is not resizeable but is fixed.
     */
    RESIZABLE("Resizable");

    /**
     * The displayable label of {@code ResizePolicy} item values.
     */
    private final String mLabel;

    /**
     * Constructs a new instance of {@code ResizePolicy} with the given displayable label.
     *
     * @param pLabel A {@link String} for the displayable label.
     */
    ResizePolicy(final String pLabel) {
        mLabel = pLabel;
    }

    /**
     * Gets the displayable label for this {@code ResizePolicy}.
     *
     * @return A {@link String} for displayable label.
     */
    public String getLabel() {
        return mLabel;
    }

    /**
     * Gets a {@link String} representation of this {@code ResizePolicy}.
     *
     * @return A {@link String} representation of this @code ResizePolicy}.
     */
    @Override
    public String toString() {
        return mLabel;
    }
}
