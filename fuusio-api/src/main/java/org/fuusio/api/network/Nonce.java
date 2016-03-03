/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.network;

import android.util.Log;

import org.fuusio.api.util.MersenneTwister;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public final class Nonce {

    private static final String TAG = Nonce.class.getSimpleName();

    /**
     * Generates a nonce string using the given time stamp as a seed.
     *
     * @param timeStamp A time stamp as a {@link String}.
     * @return A nonce {@link String}.
     */
    private static String generate(final String timeStamp) {
        final Random random = new MersenneTwister(System.currentTimeMillis());
        final String message = timeStamp + Integer.toString(random.nextInt());

        try {
            final MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(message.getBytes());
            final byte[] digestBytes = digest.digest();
            final StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < digestBytes.length; i++) {
                String hex = Integer.toHexString(0xFF & digestBytes[i]);

                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "generate(String) : " + e.getMessage());
        }

        return null;
    }

    /**
     * Generates a nonce string using a time stamp as a seed.
     *
     * @param timeStamp A time stamp.
     * @return A nonce {@link String}.
     */
    private static String generate(final long timeStamp) {
        final String timeStampString = Long.toString(timeStamp);
        final int endIndex = timeStampString.length() - 3;
        return generate(timeStampString.substring(0, endIndex));
    }

    /**
     * Generates a nonce string.
     *
     * @return A {@link String}.
     */
    public final String generate() {
        return generate(System.currentTimeMillis());
    }
}
