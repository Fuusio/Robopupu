package com.robopupu.api.mvp;

/**
 * {@link com.robopupu.api.mvp.PresenterListener} a listener interface for receiving lifecycle events
 * from a {@link Presenter}.
 */
public interface PresenterListener {
    /**
     * Invoked by a {@link Presenter} when it is started. A {@link Presenter} is
     * started when its attached {@link View} is started.
     *
     * @param presenter The started {@link Presenter}.
     */
    void onPresenterStarted(Presenter presenter);

    /**
     * Invoked by a {@link Presenter} when it is resumed. A {@link Presenter} is
     * resumed when its attached {@link View} is resumed.
     *
     * @param presenter The resumed {@link Presenter}.
     */
    void onPresenterResumed(Presenter presenter);

    /**
     * Invoked by a {@link Presenter} when it is paused. A {@link Presenter} is
     * paused when its attached {@link View} is paused.
     *
     * @param presenter The paused {@link Presenter}.
     */
    void onPresenterPaused(Presenter presenter);

    /**
     * Invoked by a {@link Presenter} when it is stopped. A {@link Presenter} is
     * stopped when its attached {@link View} is stopped.
     *
     * @param presenter The stopped {@link Presenter}.
     */
    void onPresenterStopped(Presenter presenter);

    /**
     * Invoked by a {@link Presenter} when it is destroyed. A {@link Presenter} is
     * stopped when its attached {@link View} is stopped.
     *
     * @param presenter The destroyed {@link Presenter}.
     */
    void onPresenterDestroyed(Presenter presenter);

    /**
     * Invoked by a {@link Presenter} when it is finished by invoking {@link Presenter#finish()}.
     *
     * @param presenter The finished {@link Presenter}.
     */
    void onPresenterFinished(Presenter presenter);
}
