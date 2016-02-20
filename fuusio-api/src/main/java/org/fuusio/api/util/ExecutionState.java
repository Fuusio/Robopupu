// ============================================================================
// Floxp.com : Java Enum Source File
// ============================================================================
//
// Enum: ExecutionState
// Package: FloXP Utilites (org.fuusio.api.util)
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

package org.fuusio.api.util;

public enum ExecutionState {

    DORMANT("Dormant"), //
    INITIALIZED("Initialized"), //
    ACTIVE("Active"), //
    SUSPENDED("Suspended"), //
    STOPPED("Stopped"), //
    ILLEGAL_STATE("Illegal State");

    private final String mLabel;

    ExecutionState(final String label) {
        mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }

    public final boolean isActive() {
        return (this == ACTIVE);
    }

    public final boolean isDormant() {
        return (this == DORMANT);
    }

    public final boolean isInitialized() {
        return (this == INITIALIZED);
    }

    public final boolean isStopped() {
        return (this == STOPPED);
    }

    public final boolean isSuspended() {
        return (this == SUSPENDED);
    }
}
