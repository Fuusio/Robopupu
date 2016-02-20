package org.fuusio.robopupu.app;

import android.content.Context;

import org.fuusio.robopupu.R;

import org.fuusio.api.app.AppError;

public enum RobopupuAppError implements AppError {

	ERROR_NONE(0, 0),
	
	// Native app errors

	ERROR_UNKNOWN(Integer.MAX_VALUE, R.string.error_unknown_error);

	private static Context sContext;

	private final int mCode;
	private final int mMessageFormat;
	
	RobopupuAppError(final int code, final int messageFormat) {
		mCode = code;
		mMessageFormat = messageFormat;
	}

	public final int getCode() {
		return mCode;
	}

	@Override
	public String getMessage(final Object... args) {
		return sContext.getString(mMessageFormat, args);
	}

	public static void setContext(final Context context) {
		sContext = context;
	}
}
