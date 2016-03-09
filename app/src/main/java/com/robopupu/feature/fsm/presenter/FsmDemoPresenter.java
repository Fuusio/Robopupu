package com.robopupu.feature.fsm.presenter;

import org.fuusio.api.feature.FeaturePresenter;
import org.fuusio.api.mvp.OnClick;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface FsmDemoPresenter extends FeaturePresenter {

    enum TransitionId {
        TO_B_FROM_A,
        TO_B_FROM_C,
        TO_B_FROM_D,
        TO_C_OR_D,
        TO_SELF,
        TO_B1,
        TO_B2_FROM_B1,
        TO_B2_FROM_B3,
        TO_B3;
    }

    void showMessage(String message);

    void onSelectorChanged(int value);

    @OnClick
    void onStartClick();

    @OnClick
    void onStopClick();

    @OnClick
    void onResetClick();

    void onTransitionClicked(TransitionId id);
}
