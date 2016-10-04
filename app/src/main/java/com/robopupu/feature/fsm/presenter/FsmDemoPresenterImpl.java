package com.robopupu.feature.fsm.presenter;

import android.os.Handler;
import android.os.Looper;

import com.robopupu.R;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.feature.fsm.statemachine.Controller;
import com.robopupu.feature.fsm.statemachine.SimpleStateMachine;
import com.robopupu.feature.fsm.statemachine.State;
import com.robopupu.feature.fsm.statemachine.State_A;
import com.robopupu.feature.fsm.statemachine.State_B1;
import com.robopupu.feature.fsm.statemachine.State_B2;
import com.robopupu.feature.fsm.statemachine.State_B3;
import com.robopupu.feature.fsm.statemachine.State_C;
import com.robopupu.feature.fsm.statemachine.State_D;
import com.robopupu.feature.fsm.statemachine.TriggerEvents;
import com.robopupu.feature.fsm.view.FsmDemoView;

@Plugin
@Provides(FsmDemoPresenter.class)
public class FsmDemoPresenterImpl extends AbstractFeaturePresenter<FsmDemoView>
        implements FsmDemoPresenter {

    @Plug FsmDemoView view;

    private SimpleStateMachine stateMachine;
    private Controller controller;
    private String pendingMessage;
    private State stateEngine;
    private TriggerEvents triggerEvents;
    private int selector;

    @Override
    protected FsmDemoView getViewPlug() {
        return view;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(FsmDemoView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        selector = 1;
        controller = new Controller(this);
        stateMachine = new SimpleStateMachine(controller);
    }

    @Override
    public void onSelectorChanged(int value) {
        selector = value;
    }

    @Override
    public void onStartClick() {
        doStartStateMachine();
    }

    @Override
    public void onStopClick() {
        doStopStateMachine();
    }

    @Override
    public void onResetClick() {
        doResetStateMachine();
    }

    @Override
    public void onTransitionClicked(final TransitionId id) {
        switch (id) {
            case TO_B_FROM_A: {
                triggerEvents.toB();
                break;
            }
            case TO_B_FROM_C: {
                triggerEvents.toB();
                break;
            }
            case TO_B_FROM_D: {
                triggerEvents.toB();
                break;
            }
            case TO_C_OR_D: {
                triggerEvents.toCorD(selector);
                break;
            }
            case TO_SELF: {
                triggerEvents.toSelf();
                break;
            }
            case TO_B1: {
                triggerEvents.toB1();
                break;
            }
            case TO_B2_FROM_B1: {
                triggerEvents.toB2();
                break;
            }
            case TO_B2_FROM_B3: {
                triggerEvents.toB2();
                break;
            }
            case TO_B3: {
                triggerEvents.toB3();
                break;
            }
        }

        updateStateMachineImage(id);
    }

    private void doStartStateMachine() {

        if (!stateMachine.isStarted()) {
            stateMachine.start();
            final int[] sequence = {R.drawable.img_initial_state_top, R.drawable.img_state_a};
            new Sequencer(sequence).start();
            stateEngine = stateMachine.getStateEngine();
            triggerEvents = stateEngine;
            view.setStartButtonSelected();
            view.setStopButtonEnabled(true);
            view.setResetButtonEnabled(false);
            view.showMessage(R.string.ft_fsm_demo_text_statemachine_started);
        }
    }

    private void doStopStateMachine() {
        if (stateMachine.isStarted()) {
            view.setStateMachineImage(R.drawable.img_fsm_stopped);
            stateMachine.stop();
            view.setStartButtonEnabled(false);
            view.setStopButtonSelected();
            view.setResetButtonEnabled(true);
            view.showMessage(R.string.ft_fsm_demo_text_statemachine_stopped);
        }
    }

    private void doResetStateMachine() {
        if (!stateMachine.isStarted()) {
            selector = 1;
            view.resetView();
            stateMachine.reset();
            view.setStartButtonEnabled(true);
            view.setStopButtonEnabled(false);
            view.setResetButtonSelected();
            view.showMessage(R.string.ft_fsm_demo_text_statemachine_resetted);
        }
    }

    private void updateStateMachineImage(final TransitionId transitionId) {

        final State currentState = stateMachine.getCurrentState();

        if (currentState instanceof State_A) {
            view.setEnabledTriggers(TransitionId.TO_B_FROM_A);
            final int[] sequence = {R.drawable.img_state_a};
            new Sequencer(sequence).start();
        } else if (currentState instanceof State_B1) {
            view.setEnabledTriggers(TransitionId.TO_B2_FROM_B1, TransitionId.TO_C_OR_D);

            if (transitionId == TransitionId.TO_B_FROM_A) {
                final int[] sequence = {R.drawable.img_state_b, R.drawable.img_initial_state_b, R.drawable.img_state_b1};
                new Sequencer(sequence).start();
            } else if (transitionId == TransitionId.TO_B_FROM_C) {
                final int[] sequence = {R.drawable.img_state_b, R.drawable.img_history_point, R.drawable.img_state_b1};
                new Sequencer(sequence).start();
            } else {
                final int[] sequence = {R.drawable.img_state_b1};
                new Sequencer(sequence).start();
            }
        } else if (currentState instanceof State_B2) {
            view.setEnabledTriggers(TransitionId.TO_B3, TransitionId.TO_C_OR_D);

            if (transitionId == TransitionId.TO_B_FROM_C) {
                final int[] sequence = {R.drawable.img_state_b, R.drawable.img_history_point, R.drawable.img_state_b2};
                new Sequencer(sequence).start();
            } else {
                final int[] sequence = {R.drawable.img_state_b2};
                new Sequencer(sequence).start();
            }
        } else if (currentState instanceof State_B3) {
            view.setEnabledTriggers(TransitionId.TO_B1, TransitionId.TO_B2_FROM_B3, TransitionId.TO_C_OR_D);

            if (transitionId == TransitionId.TO_B_FROM_D) {
                final int[] sequence = {R.drawable.img_state_b, R.drawable.img_entry_point, R.drawable.img_state_b3};
                new Sequencer(sequence).start();
            } else if (transitionId == TransitionId.TO_B_FROM_C) {
                final int[] sequence = {R.drawable.img_state_b, R.drawable.img_history_point, R.drawable.img_state_b3};
                new Sequencer(sequence).start();
            } else {
                final int[] sequence = {R.drawable.img_state_b3};
                new Sequencer(sequence).start();
            }
        } else if (currentState instanceof State_C) {
            view.setEnabledTriggers(TransitionId.TO_SELF, TransitionId.TO_B_FROM_C);

            if (transitionId == TransitionId.TO_SELF) {
                final int[] sequence = {R.drawable.img_fsm_stopped, R.drawable.img_state_c};
                new Sequencer(sequence).start();
            } else {
                final int[] sequence = {R.drawable.img_choice_point, R.drawable.img_state_c};
                new Sequencer(sequence).start();
            }
        } else if (currentState instanceof State_D) {
            view.setEnabledTriggers(TransitionId.TO_B_FROM_D);
            final int[] sequence = {R.drawable.img_choice_point, R.drawable.img_state_d};
            new Sequencer(sequence).start();
        }
    }

    private class Sequencer {

        private final Handler mHandler;
        private final int[] mImageSequence;

        private int index;

        public Sequencer(final int[] imageSequence) {
            mImageSequence = imageSequence;
            mHandler = new Handler(Looper.getMainLooper());
        }

        public void start() {
            next();
        }

        private void next() {
            view.setStateMachineImage(mImageSequence[index++]);

            if (index < mImageSequence.length) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        next();
                    }
                }, 1000);
            } else {
                if (pendingMessage != null) {
                    view.showMessage(pendingMessage);
                    pendingMessage = null;
                }
            }
        }
    }

    public void showMessage(final String message) {
        pendingMessage = message;
    }
}
