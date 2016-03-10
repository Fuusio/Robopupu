/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.util;

public final class ByteToolkit {

    private ByteToolkit() {
    }

    public static byte[] concatenate(final byte[]... byteArrays) {
        int length = 0;

        for (final byte[] array : byteArrays) {
            length += array.length;
        }

        final byte[] concatenatedArray = new byte[length];
        int index = 0;

        for (final byte[] array : byteArrays) {
            System.arraycopy(array, 0, concatenatedArray, index, array.length);
            index += array.length;
        }
        return concatenatedArray;
    }
}
