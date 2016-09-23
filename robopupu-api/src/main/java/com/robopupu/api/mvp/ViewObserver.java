package com.robopupu.api.mvp;

import com.robopupu.api.util.Params;

/**
 * {@link ViewObserver} a listener interface for receiving lifecycle events
 * from a {@link View}. The interface is mainly intended to be used by an attached {@link Presenter}.
 * Typically {@link Presenter} implementation should not care about {@link View}s's lifecycle events,
 * but in some cases it is necessary.
 */
public interface ViewObserver {
    /**
     * Invoked by a {@link View} implementation when it is created.
     *
     * @param view    A {@link View}
     * @param params {@link Params} containing the initial state.
     */
    void onViewCreated(View view, Params params);

    /**
     * Invoked by a {@link View} implementation when it is resumed,
     * e.g. on {@code ViewCompatFragment#onResume()}.
     *
     * @param view A {@link View}
     */
    void onViewResume(View view);

    /**
     * Invoked by a {@link View} implementation when it is paused,
     * e.g. on {@code ViewCompatFragment#onPause()}.
     *
     * @param view A {@link View}
     */
    void onViewPause(View view);

    /**
     * Invoked by a {@link View} implementation when it is started,
     * e.g. on {@code ViewCompatFragment#onStart()}.
     *
     * @param view A {@link View}
     */
    void onViewStart(View view);

    /**
     * Invoked by a {@link View} implementation when it is stopped,
     * e.g. on {@code ViewCompatFragment#onStop()}.
     *
     * @param view A {@link View}
     */
    void onViewStop(View view);

    /**
     * Invoked by a {@link View} implementation when it is destroyed,
     * e.g. on {@code ViewCompatFragment#onDestroy()}.
     *
     * @param view A {@link View}
     */
    void onViewDestroy(View view);
}
