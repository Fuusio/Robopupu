// ============================================================================
// Floxp.com : Java Enum Source File
// ============================================================================
//
// Enum: VerticalAlignment
// Package: FloXP.com Android APIs (org.fuusio.robopupu.api) -
// Graphics API (org.fuusio.robopupu.api.graphics)
//
// Author: Marko Salmela
//
// Copyright (C) Marko Salmela, 2009-2011. All Rights Reserved.
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

package org.fuusio.api.graphics;

/**
 * {@code VerticalAlignment} defines a new enum type for representing vertical alignment values, for
 * instance, for positioning text items.
 *
 * @author Marko Salmela
 */
public enum VerticalAlignment {

    TOP("Top"), //
    CENTER("Center"), //
    BOTTOM("Bottom");

    /**
     * The displayable label of {@code HorizontalAlignment} values.
     */
    private final String mLabel;

    /**
     * Constructs a new instance of {@code HorizontalAlignment} with the given displayable label.
     *
     * @param pLabel A {@link String} for displayable label.
     */
    VerticalAlignment(final String pLabel) {
        mLabel = pLabel;
    }

    /**
     * Gets the displayable label of this {@code HorizontalAlignment} value.
     *
     * @return The displayable label as a {@link String}.
     */
    public final String getLabel() {
        return mLabel;
    }
}
