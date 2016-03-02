package com.robopupu.feature.fsm.statemachine;

import com.robopupu.feature.fsm.presenter.FsmDemoPresenter;

public class Controller {

    private final FsmDemoPresenter mPresenter;

    public Controller(final FsmDemoPresenter presenter) {
        mPresenter = presenter;
    }

    public void onShowMessage(final String message) {
        if (mPresenter != null) {
            mPresenter.showMessage(message);
        }
    }
}
