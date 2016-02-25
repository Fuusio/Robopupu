package com.robopupu.feature.fsm.view;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.robopupu.R;
import com.robopupu.feature.fsm.presenter.FsmDemoPresenter;
import com.robopupu.feature.fsm.presenter.FsmDemoPresenter.TransitionId;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.FeatureFragment;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class FsmDemoFragment extends FeatureFragment<FsmDemoPresenter> implements FsmDemoView {

    private ImageView mStateMachineImageView;

    private Button mResetButton;
    private Button mStopButton;
    private Button mStartButton;

    private RadioButton mSelectCRadioButton;
    private RadioButton mSelectDRadioButton;

    @Plug FsmDemoPresenter mPresenter;

    @Provides(FsmDemoView.class)
    public FsmDemoFragment() {
    }

    @Override
    protected FsmDemoPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_fsm_demo, container, false);
    }

    @Override
    protected void createBindings() {
        super.createBindings();

        mStateMachineImageView = getView(R.id.image_view_state_machine);

        mResetButton = getView(R.id.button_reset);
        mStopButton = getView(R.id.button_stop);
        mStartButton = getView(R.id.button_start);

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

        final ImageButton imageButton_To_B2_From_B1 = getView(R.id.image_button_b2_from_b1);
        final ImageButton imageButton_To_B1 = getView(R.id.image_button_to_b1);
        final ImageButton imageButton_To_B2_From_B3 = getView(R.id.image_button_to_b2_from_b3);
        final ImageButton imageButton_To_B3 = getView(R.id.image_button_to_b3);
        final ImageButton imageButton_To_B_From_A = getView(R.id.image_button_to_b_from_a);
        final ImageButton imageButton_To_B_From_C = getView(R.id.image_button_to_b_from_c);
        final ImageButton imageButton_To_B_From_D = getView(R.id.image_button_to_b_from_d);
        final ImageButton imageButton_To_C_Or_D = getView(R.id.image_button_to_c_or_d);
        final ImageButton imageButton_To_Self = getView(R.id.image_button_to_self);

        imageButton_To_B2_From_B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_B2_FROM_B1);
            }
        });

        imageButton_To_B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_B1);
            }
        });

        imageButton_To_B2_From_B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_B2_FROM_B3);
            }
        });

        imageButton_To_B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_B3);
            }
        });

        imageButton_To_B_From_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_B_FROM_A);
            }
        });

        imageButton_To_B_From_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(FsmDemoPresenter.TransitionId.TO_B_FROM_C);
            }
        });

        imageButton_To_B_From_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_B_FROM_D);
            }
        });

        imageButton_To_C_Or_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_C_OR_D);
            }
        });

        imageButton_To_Self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTransitionClicked(TransitionId.TO_SELF);
            }
        });
    }

    @Override
    public void setStateMachineImage(@DrawableRes int imageResId) {
        mStateMachineImageView.setImageResource(imageResId);
    }

    @Override
    public void setStartButtonEnabled(final boolean enabled) {
        mStartButton.setEnabled(enabled);
    }

    @Override
    public void setStopButtonEnabled(final boolean enabled) {
        mStopButton.setEnabled(enabled);
    }

    @Override
    public void setResetButtonEnabled(final boolean enabled) {
        mResetButton.setEnabled(enabled);
    }

    @Override
    public void showMessage(final String message) {
        // TODO Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resetView() {
        mSelectCRadioButton.setSelected(true);
    }
}
