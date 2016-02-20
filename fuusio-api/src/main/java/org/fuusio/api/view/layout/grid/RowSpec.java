// ============================================================================
// Floxp.com : Java Class Source File
// ============================================================================
//
// Class: RowCell
// Package: FloXP.com Android APIs (org.fuusio.robopupu.api) -
// Graphics API (org.fuusio.robopupu.api.graphics) -
// Layout Managers (org.fuusio.api.graphics.layout)
//
// Author: Marko Salmela
//
// Copyright (C) Marko Salmela, 2000-2011. All Rights Reserved.
//
// This software is the proprietary information of Marko Salmela.
// Use is subject to license terms. This software is protected by
// copyright and distributed under licenses restricting its use,
// copying, distribution, and decompilation. No part of this software
// or associated documentation may be reproduced in any form by any
// means without prior written authorization of Marko Salmela.
//
// Disclaimer:
// -----------
//
// This software is provided by the author 'as is' and any express or implied
// warranties, including, but not limited to, the implied warranties of
// merchantability and fitness for a particular purpose are disclaimed.
// In no event shall the author be liable for any direct, indirect,
// incidental, special, exemplary, or consequential damages (including, but
// not limited to, procurement of substitute goods or services, loss of use,
// data, or profits; or business interruption) however caused and on any
// theory of liability, whether in contract, strict liability, or tort
// (including negligence or otherwise) arising in any way out of the use of
// this software, even if advised of the possibility of such damage.
// ============================================================================

package org.fuusio.api.view.layout.grid;

/**
 * {@code RowCell} implements {@link LayoutSpec} TODO
 */
public class RowSpec extends LayoutSpec {

    /**
     * Constructs a new instance of {@code RowCell} for the given {@link GridLayout}.
     *
     * @param pLayout A {@link GridLayout}.
     */
    public RowSpec(final GridLayout pLayout) {
        super(pLayout);
    }

    /**
     * Gets the fixed height set for this {@RowSpec}.
     *
     * @return The fixed height as an {@code int}.
     */
    public int getFixedHeight() {
        return getFixedSize();
    }

    /**
     * Sets the fixed height set for this {@RowSpec}.
     *
     * @param pHeight The fixed height as an {@code int}.
     */
    public void setFixedHeight(final int pHeight) {
        setFixedSize(pHeight);
    }

    /**
     * Gets the preferred height set for this {@RowSpec}.
     *
     * @return The preferred height as an {@code int}.
     */
    public int getPreferredHeight() {
        int height = getPreferredSize();

        if (height == 0) {
            height = getMinimumSize();
        }
        return height;
    }

    /**
     * Gets the current height of the row.
     *
     * @return The width as an {@code int}.
     */
    public final int getHeight() {

        if (isFixed()) {
            return getFixedHeight();
        }
        return getSize();
    }

    /**
     * Sets the height set for this {@RowSpec}.
     *
     * @param pHeight The height as an {@code int}.
     */
    public void setHeight(final int pHeight) {
        setSize(pHeight);
    }
}
