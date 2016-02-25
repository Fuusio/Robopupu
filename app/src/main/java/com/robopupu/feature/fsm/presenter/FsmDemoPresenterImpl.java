package com.robopupu.feature.fsm.presenter;

import android.os.Handler;
import android.os.Looper;

import com.robopupu.R;
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

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

@Plugin
public class FsmDemoPresenterImpl extends AbstractFeaturePresenter<FsmDemoView>
        implements FsmDemoPresenter {

    private SimpleStateMachine mStateMachine;
    private Controller mController;
    private String mPendingMessage;
    private State mStateEngine;
    private TriggerEvents mTriggerEvents;
    private int mSelector;

    @Plug FsmDemoView mView;

    @Provides(FsmDemoPresenter.class)
    public FsmDemoPresenterImpl() {
    }

    @Override
    protected FsmDemoView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(FsmDemoView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        mSelector = 1;
        mController = new Controller(this);
        mStateMachine = new SimpleStateMachine(mController);
    }

    @Override
    public void onSelectorChanged(int value) {
        mSelector = value;
    }

    @Override
    public void onStartClicked() {
        doStartStateMachine();
    }

    @Override
    public void onStopClicked() {
        doStopStateMachine();
    }

    @Override
    public void onResetClicked() {
        doResetStateMachine();
    }

    @Override
    public void onTransitionClicked(final TransitionId id) {
        switch (id) {
            case TO_B_FROM_A: {
                mTriggerEvents.toB();
                break;
            }
            case TO_B_FROM_C: {
                mTriggerEvents.toB();
                break;
            }
            case TO_B_FROM_D: {
                mTriggerEvents.toB();
                break;
            }
            case TO_C_OR_D: {
                mTriggerEvents.toCorD(mSelector);
                break;
            }
            case TO_SELF: {
                mTriggerEvents.toSelf();
                break;
            }
            case TO_B1: {
                mTriggerEvents.toB1();
                break;
            }
            case TO_B2_FROM_B1: {
                mTriggerEvents.toB2();
                break;
            }
            case TO_B2_FROM_B3: {
                mTriggerEvents.toB2();
                break;
            }
            case TO_B3: {
                mTriggerEvents.toB3();
                break;
            }
        }

        updateStateMachineImage(id);
    }

    private void doStartStateMachine() {

        if (!mStateMachine.isStarted()) {
            mStateMachine.start();
            final int[] sequence = {R.drawable.img_initial_state_top, R.drawable.img_state_a};
            new Sequencer(sequence).start();
            mStateEngine = mStateMachine.getStateEngine();
            mTriggerEvents = mStateEngine;
            mView.setStartButtonEnabled(false);
            mView.setStopButtonEnabled(true);
        }
    }

    private void doStopStateMachine() {
        if (mStateMachine.isStarted()) {
            mView.setStateMachineImage(R.drawable.img_fsm_stopped);
            mStateMachine.stop();
            mView.setResetButtonEnabled(true);
            mView.setStopButtonEnabled(false);
        }
    }

    private void doResetStateMachine() {
        if (!mStateMachine.isStarted()) {
            mSelector = 1;
            mView.resetView();
            mStateMachine.reset();
            mView.setStartButtonEnabled(true);
            mView.setResetButtonEnabled(false);
        } else {

        }
    }

    private void updateStateMachineImage(final TransitionId transitionId) {

        final State currentState = mStateMachine.getCurrentState();

        if (currentState instanceof State_A) {
            mView.setEnabledTriggers(TransitionId.TO_B_FROM_A);
            final int[] sequence = {R.drawable.img_state_a};
            new Sequencer(sequence).start();
        } else if (currentState instanceof State_B1) {
            mView.setEnabledTriggers(TransitionId.TO_B2_FROM_B1, TransitionId.TO_C_OR_D);

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
            mView.setEnabledTriggers(TransitionId.TO_B3, TransitionId.TO_C_OR_D);

            if (transitionId == TransitionId.TO_B_FROM_C) {
                final int[] sequence = {R.drawable.img_state_b, R.drawable.img_history_point, R.drawable.img_state_b2};
                new Sequencer(sequence).start();
            } else {
                final int[] sequence = {R.drawable.img_state_b2};
                new Sequencer(sequence).start();
            }
        } else if (currentState instanceof State_B3) {
            mView.setEnabledTriggers(TransitionId.TO_B1, TransitionId.TO_B2_FROM_B3, TransitionId.TO_C_OR_D);

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
            mView.setEnabledTriggers(TransitionId.TO_SELF, TransitionId.TO_B_FROM_C);
            final int[] sequence = {R.drawable.img_choice_point, R.drawable.img_state_c};
            new Sequencer(sequence).start();
        } else if (currentState instanceof State_D) {
            mView.setEnabledTriggers(TransitionId.TO_B_FROM_D);
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
            mView.setStateMachineImage(mImageSequence[index++]);

            if (index < mImageSequence.length) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        next();
                    }
                }, 1000);
            } else {
                if (mPendingMessage != null) {
                    mView.showMessage(mPendingMessage);
                    mPendingMessage = null;
                }
            }
        }
    }

    public void showMessage(final String message) {
        mPendingMessage = message;
    }
}
