package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractOutputNode;

public class TriggerNode extends AbstractOutputNode<Long> {

    @Override
    public void emit() {
        out(System.currentTimeMillis());
    }
}
