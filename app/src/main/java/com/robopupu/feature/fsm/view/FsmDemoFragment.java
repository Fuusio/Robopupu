/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
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
package com.robopupu.feature.fsm.view;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.robopupu.R;
import com.robopupu.app.view.CoordinatorLayoutFragment;
import com.robopupu.component.AppManager;
import com.robopupu.feature.fsm.presenter.FsmDemoPresenter;
import com.robopupu.feature.fsm.presenter.FsmDemoPresenter.TransitionId;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class FsmDemoFragment extends CoordinatorLayoutFragment<FsmDemoPresenter> implements FsmDemoView {

    enum TriggerButtonInfo {
        TO_B_FROM_A(R.id.image_button_to_b_from_a, 355, 63),
        TO_B_FROM_C(R.id.image_button_to_b_from_c, 298,173),
        TO_B_FROM_D(R.id.image_button_to_b_from_d, 326,468),
        TO_C_OR_D(R.id.image_button_to_c_or_d, 352, 324),
        TO_SELF(R.id.image_button_to_self, 85,149),
        TO_B1(R.id.image_button_to_b1, 518, 344),
        TO_B2_FROM_B1(R.id.image_button_b2_from_b1, 665, 121),
        TO_B2_FROM_B3(R.id.image_button_to_b2_from_b3, 825, 404),
        TO_B3(R.id.image_button_to_b3, 673, 258);

        private final @IdRes int mId;
        private final float mX;
        private final float mY;

        TriggerButtonInfo(@IdRes final int id, final float x, final float y) {
            mId = id;
            mX = x;
            mY = y;
        }

        public @IdRes int getId() {
            return mId;
        }

        public float getX() {
            return mX;
        }

        public float getY() {
            return mY;
        }
    }

    private final ImageButton[] mImageButtons;

    private ViewGroup mContentViewGroup;
    private boolean mTriggerButtonPositionsInitialised;
    private ImageView mStateMachineImageView;
    private ImageButton mResetButton;
    private ImageButton mStopButton;
    private ImageButton mStartButton;
    private RadioButton mSelectCRadioButton;
    private RadioButton mSelectDRadioButton;

    @Plug AppManager mAppManager;
    @Plug FsmDemoPresenter mPresenter;

    @Provides(FsmDemoView.class)
    public FsmDemoFragment() {
        super(R.string.ft_fsm_demo_title);
        mImageButtons = new ImageButton[TriggerButtonInfo.values().length];
    }

    @Override
    protected FsmDemoPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_fsm_demo, container, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void createBindings() {
        super.createBindings();

        mContentViewGroup = getView(R.id.relative_layout_content);
        mStateMachineImageView = getView(R.id.image_view_state_machine);

        mStateMachineImageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (right > 0 && !mTriggerButtonPositionsInitialised) {
                    initialiseTriggerButtonsPositions();
                }
            }
        });

        mResetButton = getView(R.id.image_button_reset);
        mStopButton = getView(R.id.image_button_stop);
        mStartButton = getView(R.id.image_button_start);

        setImageButtonEnabled(mResetButton, false);
        setImageButtonEnabled(mStopButton, false);
        setImageButtonEnabled(mStartButton, true);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onStartClicked();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onStopClicked();
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onResetClicked();
            }
        });

        mSelectCRadioButton = getView(R.id.radio_button_select_c);
        mSelectDRadioButton = getView(R.id.radio_button_select_d);

        mSelectCRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    mPresenter.onSelectorChanged(1);
                }
            }
        });

        mSelectDRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    mPresenter.onSelectorChanged(2);
                }
            }
        });

        for (final TriggerButtonInfo info : TriggerButtonInfo.values()) {
            final ImageButton imageButton = getView(info.getId());
            final int index = info.ordinal();

            imageButton.setEnabled(false);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onTransitionClicked(TransitionId.values()[index]);
                }
            });

            mImageButtons[index] = imageButton;
        }
    }

    private void initialiseTriggerButtonsPositions() {

        mTriggerButtonPositionsInitialised = true;

        final float left = mStateMachineImageView.getLeft();
        final float top = mStateMachineImageView.getTop();
        final float imageViewWidth = mStateMachineImageView.getWidth();
        final float imageViewHeight = imageViewWidth * 524 / 939;
        final float imageWidth = 939;
        final float imageHeight = 524;
        final float offset = -40 * imageViewWidth / imageWidth;

        for (final TriggerButtonInfo info : TriggerButtonInfo.values()) {
            final int index = info.ordinal();
            final ImageButton imageButton =  mImageButtons[index];
            final float x = left + offset + imageViewWidth * info.getX() / imageWidth;
            final float y = top + offset + imageViewHeight * info.getY() / imageHeight;

            imageButton.setTranslationX(x);
            imageButton.setTranslationY(y);
        }
    }

    @Override
    public void setStateMachineImage(@DrawableRes int imageResId) {
        mStateMachineImageView.setImageResource(imageResId);
    }

    @Override
    public void setStartButtonEnabled(final boolean enabled) {
        setImageButtonEnabled(mStartButton, enabled);
    }

    @Override
    public void setStopButtonEnabled(final boolean enabled) {
        setImageButtonEnabled(mStopButton, enabled);
    }

    @Override
    public void setResetButtonEnabled(final boolean enabled) {
        setImageButtonEnabled(mResetButton, enabled);
    }

    @Override
    public void setStartButtonSelected() {
        enableTrigger(TransitionId.TO_B_FROM_A);
        setImageButtonSelected(mStartButton);
    }

    @Override
    public void setStopButtonSelected() {
        setImageButtonSelected(mStopButton);
    }

    @Override
    public void setResetButtonSelected() {
        setImageButtonSelected(mResetButton);
    }

    private void setImageButtonSelected(final ImageButton button) {
        button.setEnabled(false);
        button.setColorFilter(mAppManager.getColor(R.color.color_deep_orange_500));
    }

    private void setImageButtonEnabled(final ImageButton button, final boolean  enabled) {
        button.setEnabled(enabled);

        if (enabled) {
            button.setColorFilter(mAppManager.getColor(R.color.color_blue_gray_400));
        } else {
            button.setColorFilter(mAppManager.getColor(R.color.color_blue_gray_100));
        }
    }

    @Override
    public void showMessage(@StringRes final int message) {
        showMessage(mAppManager.getString(message));
    }

    @Override
    public void resetView() {
        mSelectCRadioButton.setSelected(true);

        for (final TransitionId id : TransitionId.values()) {
            disableTrigger(id);
        }

        enableTrigger(TransitionId.TO_B_FROM_A);
    }

    @Override
    public void disableTrigger(final TransitionId id) {
        mImageButtons[id.ordinal()].setEnabled(false);
    }

    @Override
    public void enableTrigger(final TransitionId id) {
        mImageButtons[id.ordinal()].setEnabled(true);
    }

    @Override
    public void setEnabledTriggers(final TransitionId... ids) {
        for (final TransitionId id : TransitionId.values()) {
            disableTrigger(id);
        }

        for (final TransitionId id : ids) {
            enableTrigger(id);
        }
    }
}
