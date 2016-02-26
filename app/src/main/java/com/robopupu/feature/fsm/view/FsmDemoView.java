package com.robopupu.feature.fsm.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.robopupu.feature.fsm.presenter.FsmDemoPresenter;

import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface FsmDemoView extends View {

    void disableTrigger(FsmDemoPresenter.TransitionId id);

    void enableTrigger(FsmDemoPresenter.TransitionId id);

    void setEnabledTriggers(FsmDemoPresenter.TransitionId... ids);

    void resetView();

    void showMessage(@StringRes final int message);

    void showMessage(String message);

    void setStateMachineImage(@DrawableRes final int imageResId);

    void setStartButtonEnabled(boolean enabled);

    void setStopButtonEnabled(boolean enabled);

    void setResetButtonEnabled(boolean enabled);

    void setStartButtonSelected();

    void setStopButtonSelected();

    void setResetButtonSelected();
}
