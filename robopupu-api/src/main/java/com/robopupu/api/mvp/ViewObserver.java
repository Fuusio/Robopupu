package com.robopupu.api.mvp;

import com.robopupu.api.util.Params;

/**
 * {@link ViewObserver} a listener interface for receiving lifecycle events
 * from a {@link View}. This interface is mainly intended to be used by an attached {@link Presenter}.
 */
public interface ViewObserver {
    /**
     * Invoked by a {@link View} implementation when it is created-
     *
     * @param view    A {@link View}
     * @param params {@link Params} containing the initial state.
     */
    void onViewCreated(View view, Params params);

    /**
     * Invoked by a {@link View} implementation when it is resumed,
     * e.g. on {@link ViewCompatFragment#onResume()}.
     *
     * @param view A {@link View}
     */
    void onViewResume(View view);

    /**
     * Invoked by a {@link View} implementation when it is paused,
     * e.g. on {@link ViewCompatFragment#onPause()}.
     *
     * @param view A {@link View}
     */
    void onViewPause(View view);

    /**
     * Invoked by a {@link View} implementation when it is started,
     * e.g. on {@link ViewCompatFragment#onStart()}.
     *
     * @param view A {@link View}
     */
    void onViewStart(View view);

    /**
     * Invoked by a {@link View} implementation when it is stopped,
     * e.g. on {@link ViewCompatFragment#onStop()}.
     *
     * @param view A {@link View}
     */
    void onViewStop(View view);

    /**
     * Invoked by a {@link View} implementation when it is destroyed,
     * e.g. on {@link ViewCompatFragment#onDestroy()}.
     *
     * @param view A {@link View}
     */
    void onViewDestroy(View view);
}
