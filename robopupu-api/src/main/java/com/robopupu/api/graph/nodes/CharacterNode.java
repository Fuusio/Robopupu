package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link CharacterNode} converts the input value to a {@link Character}.
 * @param <IN> The input type.
 */
public class CharacterNode<IN> extends Node<IN, Character> {

    private char mValue;

    @Override
    protected Character processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Character) {
            mValue = (Character)input;
            return mValue;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Character")));
            return null;
        }
    }

    public char getValue() {
        return mValue;
    }
}
