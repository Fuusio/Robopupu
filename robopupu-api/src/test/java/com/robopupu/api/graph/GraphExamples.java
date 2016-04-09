package com.robopupu.api.graph;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@link GraphExamples} provides a set of Graph examples as test cases.
 */
@SmallTest
public class GraphExamples {

    @Test
    public void sumOfIntegers() {
        final int sumOfIntegers = Graph.begin(1, 2, 3).sum().toInt();
        assertEquals(6, sumOfIntegers);
    }
}
