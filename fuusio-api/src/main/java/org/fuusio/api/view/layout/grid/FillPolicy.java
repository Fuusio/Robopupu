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

import org.fuusio.api.view.layout.cell.CellLayout;
import org.fuusio.api.view.layout.cell.ViewCell;

/**
 * {@code FillPolicy} defines an enumerated type for representing
 * the various fill policies for {@link android.graphics.drawable.Drawable}s
 * contained by {@link ViewCell} instances in a {@link CellLayout}.
 *
 * @author Marko Salmela
 */
public enum FillPolicy {

    /**
     * In None policy, the component is not stretched to fill entire cell are.
     */
    NONE("None"),

    /**
     * In Horizontal policy, the component is stretched to horizontally fill
     * the entire cell area.
     */
    HORIZONTAL("Horizontal"),

    /**
     * In Horizontal policy, the component is stretched to horizontally fill
     * the entire cell area.
     */
    VERTICAL("Vertical"),

    /**
     * In Horizontal policy, the component is stretched to fill both horizontally
     * and vertically the entire cell area.
     */
    BOTH("Both");

    /**
     * The displayable label of {@code FillPolicy} item values.
     */
    private String mLabel;

    /**
     * Constructs a new instance of {@link org.fuusio.api.view.layout.cell.FillPolicy} with the given
     * displayable label.
     *
     * @param pLabel The displayable label as a {@link String};
     */
    FillPolicy(final String pLabel) {
        mLabel = pLabel;
    }

    /**
     * Gets the displayable label for this {@code FillPolicy}.
     *
     * @return A {@link String} for displayable label.
     */
    public final String getLabel() {
        return mLabel;
    }

    /**
     * Gets a {@link String} representation of this
     * {@code FillPolicy}.
     *
     * @return A {@link String} representation of this
     * {@link org.fuusio.api.view.layout.cell.FillPolicy}.
     */
    @Override
    public String toString() {
        return mLabel;
    }
}
