package com.robopupu.feature.feedback.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robopupu.R;
import com.robopupu.app.view.CoordinatorLayoutFragment;
import com.robopupu.feature.feedback.presenter.FeedbackPresenter;

import org.fuusio.api.binding.Binding;
import org.fuusio.api.binding.ClickBinding;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class FeedbackFragment extends CoordinatorLayoutFragment<FeedbackPresenter>
        implements FeedbackView {

    private Binding mFeedbackTextBinding;

    @Plug FeedbackPresenter mPresenter;

    @Provides(FeedbackView.class)
    public FeedbackFragment() {
        super(R.string.ft_feedback_title);
    }

    public FeedbackPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFeedbackTextBinding.requestFocus();
    }

    @Override
    protected void createBindings() {

        mFeedbackTextBinding = new Binding(this, R.id.edit_text_feedback);

        new ClickBinding(this, R.id.fab_send) {
            @Override
            protected void clicked() {
                mPresenter.onSendClicked(mFeedbackTextBinding.getText());
            }
        };
    }

    @Override
    public void clearFeedbackText() {
        mFeedbackTextBinding.setText("");
    }
}
