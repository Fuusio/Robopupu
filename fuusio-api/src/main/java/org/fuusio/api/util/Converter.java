package org.fuusio.api.util;

import android.os.Bundle;

/**
 * {@link Converter} is a utility class that can be used to convert an instance of
 * {@link Bundle} to instance of {@link Params}.
 */
public final class Converter {

    /**
     * Create an instance of {@link Params} from the given {@link Bundle}.
     * @param bundle A {@link Bundle}. May be {@code null}.
     * @return The created instance of {@link Params} if {@code bundle} was not null; otherwise
     * {@code null}.
     */
    public static Params toParams(final Bundle bundle) {
        final Params params = new Params();

        if (bundle != null) {
            for (final String key : bundle.keySet()) {
                params.put(key, bundle.get(key));
            }
        }
        return params;
    }
}
