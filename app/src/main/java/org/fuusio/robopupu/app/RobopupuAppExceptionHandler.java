package org.fuusio.robopupu.app;

import org.fuusio.api.app.AppExceptionHandler;

public class RobopupuAppExceptionHandler extends AppExceptionHandler<RobopupuAppError> {

	private RobopupuAppExceptionHandler() {
        super(null); // TODO
	}

    @Override
    protected RobopupuAppError getAppSpecificUnknownError() {
        return RobopupuAppError.ERROR_UNKNOWN;
    }


}
