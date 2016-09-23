/*
 * Copyright (C) 2015 - 2016 Marko Salmela, http://robopupu.com
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

/**
 * {@link TypedValue}, as name suggest, represents a value with information about its explicit type.
 */
public class TypedValue {

    private final Class<?> type;
    private final Object value;

    public TypedValue(final Class<?> type, final Object value) {
        this.type = type;
        this.value = value;
    }

    public static TypedValue create(final Object value) {
        return create(value.getClass(), value);
    }

    public static TypedValue create(final Class<?> type, final Object value) {
        return new TypedValue(type, value);
    }

    public Class<?> getType() {
        return type;
    }

    public Object get() {
        return value;
    }

    public TypedValue(final boolean value) {
        this(Boolean.TYPE, value);
    }

    public TypedValue(final boolean[] value) {
        this(boolean[].class, value);
    }

    public TypedValue(final byte value) {
        this(Byte.TYPE, value);
    }

    public TypedValue(final byte[] value) {
        this(byte[].class, value);
    }

    public TypedValue(final char value) {
        this(Character.TYPE, value);
    }

    public TypedValue(final char[] value) {
        this(char[].class, value);
    }

    public TypedValue(final CharSequence value) {
        this(CharSequence.class, value);
    }

    public TypedValue(final CharSequence[] value) {
        this(CharSequence[].class, value);
    }

    public TypedValue(final double value) {
        this(Double.TYPE, value);
    }

    public TypedValue(final double[] value) {
        this(double[].class, value);
    }

    public TypedValue(final float value) {
        this(Float.TYPE, value);
    }

    public TypedValue(final float[] value) {
        this(float[].class, value);
    }

    public TypedValue(final int value) {
        this(Integer.TYPE, value);
    }

    public TypedValue(final int[] value) {
        this(int[].class, value);
    }

    public TypedValue(final long value) {
        this(Long.TYPE, value);
    }

    public TypedValue(final long[] value) {
        this(long[].class, value);
    }

    public TypedValue(final short value) {
        this(Short.TYPE, value);
    }

    public TypedValue(final short[] value) {
        this(short[].class, value);
    }

    public TypedValue(final String value) {
        this(String.class, value);
    }

    public TypedValue(final String[] value) {
        this(String[].class, value);
    }


    @SuppressWarnings("unchecked")
    public boolean getBoolean() {
        return (boolean) value;
    }

    @SuppressWarnings("unchecked")
    public boolean[] getBooleans() {
        return (boolean[]) value;
    }

    @SuppressWarnings("unchecked")
    public byte getByte() {
        return (byte) value;
    }

    @SuppressWarnings("unchecked")
    public byte[] getBytes() {
        return (byte[]) value;
    }

    @SuppressWarnings("unchecked")
    public char getChar() {
        return (char) value;
    }

    @SuppressWarnings("unchecked")
    public char[] getChars() {
        return (char[]) value;
    }

    @SuppressWarnings("unchecked")
    public CharSequence getCharSequence() {
        return (CharSequence) value;
    }

    @SuppressWarnings("unchecked")
    public CharSequence[] getCharSequences() {
        return (CharSequence[]) value;
    }

    @SuppressWarnings("unchecked")
    public double getDouble() {
        return (double) value;
    }

    @SuppressWarnings("unchecked")
    public double[] getDoubles() {
        return (double[]) value;
    }

    @SuppressWarnings("unchecked")
    public float getFloat() {
        return (float) value;
    }

    @SuppressWarnings("unchecked")
    public float[] getFloats() {
        return (float[]) value;
    }

    @SuppressWarnings("unchecked")
    public int getInt() {
        return (int) value;
    }

    @SuppressWarnings("unchecked")
    public int[] getInts() {
        return (int[]) value;
    }

    @SuppressWarnings("unchecked")
    public long getLong() {
        return (long) value;
    }

    @SuppressWarnings("unchecked")
    public long[] getLongs() {
        return (long[]) value;
    }

    @SuppressWarnings("unchecked")
    public short getShort() {
        return (short) value;
    }

    @SuppressWarnings("unchecked")
    public short[] getShorts() {
        return (short[]) value;
    }

    @SuppressWarnings("unchecked")
    public String getString() {
        return (String) value;
    }

    @SuppressWarnings("unchecked")
    public String[] getStrings() {
        return (String[]) value;
    }
}


