package com.robopupu.api.graph;

import android.test.suitebuilder.annotation.SmallTest;

import com.robopupu.api.graph.nodes.Zip3Node;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link GraphExamples} provides a set of Graph examples as test cases.
 */
@SmallTest
public class GraphExamples {

    @Test
    public void sumOfIntegers() {
        final int sumOfIntegers = Graph.begin(1, 2, 3).sum().intValue();
        assertEquals(6, sumOfIntegers);
    }

    @Test
    public void mapFromIntToString() {
        final String string = Graph.begin(123).stringValue();
        assertEquals("123", string);
    }

    @Test
    public void filterIntValues() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).filter(value -> value % 2 == 0).toList();
        assertTrue(equals(integers, 2, 4, 6));
    }

    @Test
    public void mapIntValuesToChars() {
        final List<Character> chars = Graph.begin(1, 2, 3, 4, 5, 6).map(value -> (char)('A' + value - 1)).toList();
        assertTrue(equals(chars, 'A', 'B', 'C', 'D', 'E', 'F'));
    }

    @Test
    public void convertCharValuesToString() {
        final String string = Graph.begin('A', 'B', 'C', 'D', 'E', 'F').string().concat().stringValue();
        assertEquals("ABCDEF", string);
    }

    @Test
    public void reverseString() {
        final String string = Graph.begin("upupoboR").map(value -> new StringBuilder(value).reverse()).stringValue();
        assertEquals("Robopupu", string);
    }

    @Test
    public void firstIntValue() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).first().toList();
        assertTrue(equals(integers, 1));
    }

    @Test
    public void distinctIntValues() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 1, 6, 4, 5, 3).distinct().toList();
        assertTrue(equals(integers, 1, 2, 3, 6, 4, 5));
    }

    @Test
    public void reversedIntValues() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).reverse().toList();
        assertTrue(equals(integers, 6, 5, 4, 3, 2, 1));
    }

    @Test
    public void listToList() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).toList();
        assertTrue(equals(integers, 1, 2, 3, 4, 5, 6));
    }


    @Test
    public void firstGreaterThanThreeIntValue() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).first(value -> value > 3).toList();
        assertTrue(equals(integers, 4));
    }

    @Test
    public void lastIntValue() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).last().toList();
        assertTrue(equals(integers, 6));
    }

    @Test
    public void lastLesserThanFourIntValue() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).last(value -> value < 4).toList();
        assertTrue(equals(integers, 3));
    }


    @Test
    public void skipIntValues() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).skip(3).toList();
        assertTrue(equals(integers, 4, 5, 6));
    }

    @Test
    public void skipWhileIntValues() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).skipWhile(value -> value < 5).toList();
        assertTrue(equals(integers, 5, 6));
    }

    @Test
    public void takeIntValues() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).take(3).toList();
        assertTrue(equals(integers, 1, 2, 3));
    }

    @Test
    public void repeatIntValues() {
        final List<Integer> integers = Graph.begin(1, 2, 3, 4, 5, 6).repeat(2).toList();
        assertTrue(equals(integers, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6));
    }

    @Test
    public void zipThreeStrings() {

        final Zip3Node<String, String, String, String> zipNode =
                new Zip3Node<>((input1, input2, input3) -> input1 + input2 + input3);
        final Tag<String> list  = Tag.create();

        final String string = Graph.begin(list, "Robopupu ", "is ", "awesome").
                node(list).nth(1).next(zipNode.input1).
                node(list).nth(2).next(zipNode.input2).
                node(list).nth(3).next(zipNode.input3).stringValue();

        assertEquals("Robopupu is awesome", string);
    }

    @Test
    public void concatFourStrings() {
        final String string = Graph.begin("Robo", "pupu ", "is ", "awesome").concat().stringValue();
        assertEquals("Robopupu is awesome", string);
    }

    private  static <T> boolean equals(final List<T> list, final T... inputs) {
        final int size = list.size();

        if (inputs.length == size) {
            for (int i = 0; i < size; i++) {
                if (!inputs[i].equals(list.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
