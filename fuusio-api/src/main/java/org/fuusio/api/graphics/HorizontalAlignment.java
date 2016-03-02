// ============================================================================
// Fuusio.org : Java Class Source File
// ============================================================================
//
// Class: CompositeDrawable
// Package: Fuusio Graphics API (org.fuusio.api.graphics) -
// Painter Nodes (org.fuusio.api.graphics.painter)
//
// Author: Marko Salmela
//
// Copyright (C) Marko Salmela, 2000-2008. All Rights Reserved.
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

import android.graphics.Paint;

/**
 * {@code HorizontalAlignment} defines an enum type for representing horizontal alignment values,
 * for instance, for positioning text items in {@link TextDrawable}s.
 */
public enum HorizontalAlignment {

    LEFT("Left", Paint.Align.LEFT), //
    CENTER("Center", Paint.Align.CENTER), //
    RIGHT("Right", Paint.Align.RIGHT);

    /**
     * The displayable label of {@code HorizontalAlignment} values.
     */
    private final String mLabel;

    /**
     * Corresponding {@link Paint.Align} value.
     */
    private final Paint.Align mPaintAlign;

    /**
     * Constructs a new instance of {@code HorizontalAlignment} with the given displayable label and
     * {@link Paint.Align} value.
     *
     * @param label      A {@link String} for displayable label.
     * @param paintAlign The {@link Paint.Align} value.
     */

    HorizontalAlignment(final String label, final Paint.Align paintAlign) {
        mLabel = label;
        mPaintAlign = paintAlign;
    }

    /**
     * Gets the displayable label of this {@code HorizontalAlignment} value.
     *
     * @return The displayable label as a {@link String}.
     */
    public final String getLabel() {
        return mLabel;
    }

    /**
     * Gets the {@link Paint.Align} value.
     *
     * @return A {@link Paint.Align} value.
     */
    public final Paint.Align getPaintAlign() {
        return mPaintAlign;
    }

}
