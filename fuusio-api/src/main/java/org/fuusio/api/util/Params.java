package org.fuusio.api.util;

import android.os.Bundle;

import java.util.HashMap;

/**
 * {@link Params} extends {@link HashMap} to provide a convenience object for passing parameters.
 */
public class Params extends HashMap<String, Object> {

    public Params() {
        this(null);
    }

    public Params(final Bundle bundle) {

        if (bundle != null) {
            for (final String key : bundle.keySet()) {
                put(key, bundle.get(key));
            }
        }
    }

    /**
     * Create an instance of {@link Params} from the given {@link Bundle}.
     * @param bundle A {@link Bundle}. May be {@code null}.
     * @return The created instance of {@link Params} if {@code bundle} was not null; otherwise
     * {@code null}.
     */
    public static Params from(final Bundle bundle) {
        return new Params(bundle);
    }

}
