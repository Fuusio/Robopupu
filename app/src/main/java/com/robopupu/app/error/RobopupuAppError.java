/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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
package com.robopupu.app.error;

import android.content.Context;

import com.robopupu.R;
import com.robopupu.api.app.AppError;

public enum RobopupuAppError implements AppError {

	ERROR_NONE(0, 0),
	
	// Native app errors

	ERROR_UNKNOWN(Integer.MAX_VALUE, R.string.error_unknown_error);

	private static Context context;

	private final int code;
	private final int messageFormat;
	
	RobopupuAppError(final int code, final int messageFormat) {
		this.code = code;
		this.messageFormat = messageFormat;
	}

	public final int getCode() {
		return code;
	}

	@Override
	public String getMessage(final Object... args) {
		return context.getString(messageFormat, args);
	}

	public static void setContext(final Context context) {
		RobopupuAppError.context = context;
	}
}
