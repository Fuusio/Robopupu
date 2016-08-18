package com.robopupu.feature.jokes.view;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robopupu.R;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.app.view.CoordinatorLayoutFragment;
import com.robopupu.feature.jokes.presenter.JokesPresenter;

@Plugin
public class JokesFragment extends CoordinatorLayoutFragment<JokesPresenter>
        implements JokesView {

    private TextView jokesTextView;
    private NestedScrollView scrollView;

    @Plug JokesPresenter presenter;

    @Provides(JokesView.class)
    public JokesFragment() {
        super(R.string.ft_jokes_title);
    }

    @Override
    public JokesPresenter getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_jokes, container, false);
    }

    @Override
    protected void onCreateBindings() {
        super.onCreateBindings();
        jokesTextView = getView(R.id.text_view_jokes);
        scrollView = getView(R.id.nested_scroll_view_jokes);
    }

    @Override
    public void displayJoke(final String formattedJoke) {
        jokesTextView.append(formattedJoke);
        jokesTextView.append("\n\n\n");
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

}
