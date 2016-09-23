package com.robopupu.api.util;

import android.content.Intent;

import java.util.HashMap;

/**
 * {@link IntentConstructor} constructs an {@link Intent} from the given {@link IntentSpec}.
 */

public class IntentConstructor {

    /**
     * Constructs an {@link Intent} from the given {@link IntentSpec}.
     * @param spec An {@link IntentSpec}.
     * @return An {@link Intent}.
     */
    public static Intent construct(final IntentSpec spec) {
        final Intent intent = new Intent();
        intent.setAction(spec.getAction());
        intent.setType(spec.getType());

        for (final String category : spec.getCategories()) {
            intent.addCategory(category);
        }

        final HashMap<String, TypedValue> extras = spec.getExtras();

        for (final String key : extras.keySet()) {
            putExtra(intent, key, extras.get(key));
        }
        return intent;
    }

    private static void putExtra(final Intent intent, final String key, final TypedValue typedValue) {
        final Class<?> type = typedValue.getType();
        final Object value = typedValue.get();

        if (type.equals(Boolean.TYPE) || value instanceof Boolean) {
            intent.putExtra(key, typedValue.getBoolean());
        } else if (value instanceof boolean[]) {
            intent.putExtra(key, typedValue.getBooleans());
        } else if (type.equals(Byte.TYPE) || value instanceof Byte) {
            intent.putExtra(key, typedValue.getByte());
        } else if (value instanceof byte[]) {
            intent.putExtra(key, typedValue.getBytes());
        } else if (type.equals(Character.TYPE) || value instanceof Character) {
            intent.putExtra(key, typedValue.getChar());
        } else if (value instanceof char[]) {
            intent.putExtra(key, typedValue.getChars());
        } else if (value instanceof CharSequence) {
            intent.putExtra(key, typedValue.getCharSequence());
        } else if (value instanceof CharSequence[]) {
            intent.putExtra(key, typedValue.getCharSequences());
        } else if (type.equals(Double.TYPE) || value instanceof Double) {
            intent.putExtra(key, typedValue.getDouble());
        } else if (value instanceof double[]) {
            intent.putExtra(key, typedValue.getDoubles());
        } else if (type.equals(Integer.TYPE) || value instanceof Integer) {
            intent.putExtra(key, typedValue.getInt());
        } else if (value instanceof int[]) {
            intent.putExtra(key, typedValue.getInts());
        } else if (type.equals(Long.TYPE) || value instanceof Long) {
            intent.putExtra(key, typedValue.getLong());
        } else if (value instanceof long[]) {
            intent.putExtra(key, typedValue.getLongs());
        } else if (type.equals(Short.TYPE) || value instanceof Short) {
            intent.putExtra(key, typedValue.getShort());
        } else if (value instanceof short[]) {
            intent.putExtra(key, typedValue.getShorts());
        } else if (value instanceof String) {
            intent.putExtra(key, typedValue.getString());
        } else if (value instanceof String[]) {
            intent.putExtra(key, typedValue.getStrings());
        }
    }
}
