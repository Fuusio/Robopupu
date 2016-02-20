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
 * {@code ColumnSpec} implements {@link LayoutSpec} TODO
 */
public class ColumnSpec extends LayoutSpec {

    /**
     * Constructs a new instance of {@code ColumnCell} for the given {@code GridLayout}. The
     * instance is resizeable according to the specified resize mode.
     *
     * @param pLayout A {@code GridLayout}.
     */
    public ColumnSpec(final GridLayout pLayout) {
        super(pLayout);
    }

    /**
     * Gets the fixed width set for this {@ColumnSpec}.
     *
     * @return The fixed width as an {@code int}.
     */
    public int getFixedWidth() {
        return getFixedSize();
    }

    /**
     * Gets the preferred width set for this {@ColumnSpec}.
     *
     * @return The preferred width as an {@code int}.
     */
    public int getPreferredWidth() {
        int width = getPreferredSize();

        if (width == 0) {
            width = getMinimumSize();
        }
        return width;
    }

    /**
     * Sets the fixed width set for this {@ColumnSpec}.
     *
     * @param pWidth The fixed width as an {@code int}.
     */
    public void setFixedWidth(final int pWidth) {
        setFixedSize(pWidth);
    }

    /**
     * Gets the current width of the column.
     *
     * @return The width as an {@code int}.
     */
    public final int getWidth() {

        if (isFixed()) {
            return getFixedWidth();
        }
        return getSize();
    }

    /**
     * Sets the current width of the column.
     *
     * @param pWidth The width as an {@code int}.
     */
    public final void setWidth(final int pWidth) {
        setSize(pWidth);
    }
}
