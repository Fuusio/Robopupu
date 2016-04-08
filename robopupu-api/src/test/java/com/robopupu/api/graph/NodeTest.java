/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.graph;

import android.test.suitebuilder.annotation.SmallTest;

import com.robopupu.api.graph.nodes.ActionNode;
import com.robopupu.api.graph.nodes.SimpleNode;
import com.robopupu.api.graph.nodes.Zip2Node;
import com.robopupu.api.graph.nodes.Zip3Node;
import com.robopupu.api.graph.nodes.Zip9Node;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SmallTest
public class NodeTest {

    private TerminalNode<Integer> mEndNode = new TerminalNode<>();
    private List<Integer> mIntList;

    @Before
    public void beforeTests() {
        mIntList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mIntList.add(i);
        }
    }

    @After
    public void afterTests() {}

    @Test
    public void test_attach() {
        Graph<Integer> graph = new Graph<>();

        final Node<Integer, Integer> beginNode = new SimpleNode<>();
        final Node<Integer, Integer> node1 = new SimpleNode<>();
        final Node<Integer, Integer> node2 = new SimpleNode<>();

        mEndNode.reset();

        graph.next(beginNode).next(node1).next(node2).end(mEndNode);

        for (int i = 1; i < 4; i++) {
            beginNode.onInput(i);
        }

        assertTrue(mEndNode.received(1, 2, 3));

        graph = new Graph<>();
        graph.map(input -> Integer.toString(input)).
                map(Integer::parseInt).
                map(input -> input > 10).
                map(input -> input ? 1000 : 0).
                end(mEndNode);

        mEndNode.reset();
        final Node<Integer, Integer> functionNode = graph.getBeginNode();
        functionNode.onInput(20);
        assertTrue(mEndNode.received(1000));
    }

    @Test
    public void test_skip() {
        final Graph<Integer> graph = new Graph<>();

        final Node<Integer, Integer> node0 = new SimpleNode<>();
        final Node<Integer, Integer> node1 = new SimpleNode<>();
        final Node<Integer, Integer> node2 = new SimpleNode<>();

        mEndNode.reset();

        graph.skip(3).next(node0).next(node1).next(node2).end(mEndNode);

        final Node<Integer, Integer> beginNode = graph.getBeginNode();

        for (int i = 1; i < 7; i++) {
            beginNode.onInput(i);
        }

        assertTrue(mEndNode.received(4, 5, 6));
    }

    @Test
    public void test_repeat() {

        Graph<Integer> graph = new Graph<>();
        graph.repeat(0).next(mEndNode);
        mEndNode.reset();
        Node<Integer, Integer> beginNode = graph.getBeginNode();
        beginNode.onInput(1);
        assertTrue(mEndNode.received());

        graph = new Graph<>();
        graph.repeat(1).end(mEndNode);
        mEndNode.reset();
        beginNode = graph.getBeginNode();
        beginNode.onInput(1);
        assertTrue(mEndNode.received(1));

        graph = new Graph<>();
        graph.repeat(5).end(mEndNode);
        mEndNode.reset();
        beginNode = graph.getBeginNode();
        beginNode.onInput(1);
        assertTrue(mEndNode.received(1, 1, 1, 1, 1));
    }

    @Test
    public void test_skipWhile() {

        final Graph<Integer> graph = new Graph<>();
        graph.skipWhile(input -> input > 3).end(mEndNode);
        mEndNode.reset();
        Node<Integer, Integer> beginNode = graph.getBeginNode();
        beginNode.onInput(5);
        beginNode.onInput(6);
        beginNode.onInput(7);
        beginNode.onInput(1);
        beginNode.onInput(8);
        beginNode.onInput(9);
        beginNode.onInput(2);
        beginNode.onInput(3);
        assertTrue(mEndNode.received(1, 8, 9, 2, 3));
    }

    @Test
    public void test_take() {
        final Graph<Integer> graph = new Graph<>();

        final Node<Integer, Integer> node0 = new SimpleNode<>();
        final Node<Integer, Integer> node1 = new SimpleNode<>();

        mEndNode.reset();

        graph.take(3).next(node0).next(node1).end(mEndNode);

        final Node<Integer, Integer> beginNode = graph.getBeginNode();

        for (int i = 1; i < 7; i++) {
            beginNode.onInput(i);
        }

        assertTrue(mEndNode.received(1, 2, 3));
    }

    @Test
    public void test_filter() {
        final Graph<Integer> graph = new Graph<>();

        mEndNode.reset();

        graph.filter(value -> value > 3).end(mEndNode);

        final Node<Integer, Integer> beginNode = graph.getBeginNode();

        for (int i = 1; i < 7; i++) {
            beginNode.onInput(i);
        }

        assertTrue(mEndNode.received(4, 5, 6));
    }

    @Test
    public void test_list() {

        mEndNode.reset();
        Graph.begin(mIntList).take(3).end(mEndNode).emit();
        assertTrue(mEndNode.received(0, 1, 2));

        mEndNode.reset();
        Graph.begin(mIntList).skip(7).end(mEndNode).emit();
        assertTrue(mEndNode.received(7, 8, 9));
    }

    @Test
    public void test_sum() {

        final List<Item> items =
                createList(new Item(false, 1), new Item(true, 2), new Item(false, 3), new Item(true, 4), new Item(false, 5), new Item(true, 6));

        final int sum = Graph.begin(items).filter(item -> item.on).map(item -> item.value).sum().toInt();

        assertTrue(sum == 12);
    }

    private class Item {

        public final boolean on;
        public final int value;

        public Item(final boolean on, final int value) {
            this.on = on;
            this.value = value;
        }
    }


    @Test
    public void test_zip2() {

        final TerminalNode<String> endNode = new TerminalNode<>();
        final Zip2Node<Character, Integer, String> zipNode =
                new Zip2Node<>((input1, input2) -> Character.toString(input1) + Integer.toString(input2));
        final Tag<Character> begin = Tag.create();

        Graph.begin(begin, createList('A', 'B', 'C')).
                node(begin).next(zipNode.input1).
                node(begin).map(c -> c - 'A' + 1).next(zipNode.input2).
                end(endNode).emit();

        assertTrue(endNode.received("A1", "B2", "C3"));
    }

    @Test
    public void test_zip3() {

        final TerminalNode<String> endNode = new TerminalNode<>();
        final Zip3Node<Character, Integer, String, String> zipNode =
                new Zip3Node<>((input1, input2, input3) -> Character.toString(input1) + Integer.toString(input2) + input3);
        final Tag<Character> begin = Tag.create();

        Graph.begin(begin, createList('A', 'B', 'C')).
                next(zipNode.input1).
                node(begin).map(c -> c - 'A' + 1).next(zipNode.input2).
                node(begin).string().next(zipNode.input3).end(endNode).emit();

        assertTrue(endNode.received("A1A", "B2B", "C3C"));
    }

    @Test
    public void test_zip9() {

        final TerminalNode<String> endNode = new TerminalNode<>();
        final Zip9Node<Character, Integer, String, Character, Integer, String, Character, Integer, String, String> zipNode =
                new Zip9Node<>((input1, input2, input3, input4, input5, input6, input7, input8, input9) ->
                        Character.toString(input1) + Integer.toString(input2) + input3 + Character.toString(input4) + Integer.toString(input5) + input6 + Character.toString(input7) + Integer.toString(input8) + input9);
        final Tag<Character> begin = Tag.create();

        Graph.begin(begin, createList('A', 'B', 'C')).
                node(begin).next(zipNode.input1).
                node(begin).map(c -> c - 'A' + 1).next(zipNode.input2).
                node(begin).string().next(zipNode.input3).
                node(begin).next(zipNode.input4).
                node(begin).map(c -> c - 'A' + 1).next(zipNode.input5).
                node(begin).string().next(zipNode.input6).
                node(begin).next(zipNode.input7).
                node(begin).map(c -> c - 'A' + 1).next(zipNode.input8).
                node(begin).string().next(zipNode.input9).
                end(endNode).emit();

        assertTrue(endNode.received("A1AA1AA1A", "B2BB2BB2B", "C3CC3CC3C"));
    }

    @Test
    public void test_logic() {

        final AuthenticatorImpl authenticator = new AuthenticatorImpl();

        Node<Response, Response> loginNode;
        Response response;

        // Http 400, Error A

        response = new Response();
        response.error = Error.A.getErrorMsg();
        response.statusCode = 400;

        loginNode = createGraph(authenticator);
        authenticator.reset();
        loginNode.onInput(response);
        assertTrue(authenticator.failed());

        // Http 401, Error D

        response = new Response();
        response.error = Error.D.getErrorMsg();
        response.statusCode = 401;

        loginNode = createGraph(authenticator);
        authenticator.reset();
        loginNode.onInput(response);
        assertTrue(authenticator.failed());

        // Http 200

        response = new Response();
        response.statusCode = 200;

        loginNode = createGraph(authenticator);
        authenticator.reset();
        loginNode.onInput(response);
        assertTrue(authenticator.succeeded());
    }

    private Node<Response, Response> createGraph(final Authenticator authenticator) {

        final Tag<Response> authToken = Tag.create();
        final Tag<Response> code400 = Tag.create();
        final Tag<Response> code401 = Tag.create();
        final Graph<Response> graph = Graph.begin(authToken, response -> authenticator.onRequestAuthCode());

        graph.
            node(authToken).filter(response -> response.statusCode == 200).end(authenticator::onAuthenticationSucceeded).

            node(authToken).tag(code400).filter(response -> response.statusCode == 400).
                node(code400).filter(Error.A::is).end(authenticator::onAuthenticationFailed).
                node(code400).filter(Error.B::is).end(authenticator::onAuthenticationFailed).

            node(authToken).tag(code401).filter(response -> response.statusCode == 401).
                node(code401).filter(Error.C::is).end(authenticator::onAuthenticationFailed).
                node(code401).filter(Error.D::is).end(authenticator::onAuthenticationFailed).
                node(code401).filter(Error.E::is).end(authenticator::onAuthenticationFailed);

        return graph.getBeginNode();
    }

    @SuppressWarnings({"unchecked", "varargs"})
    private <T> List<T> createList(final T... values) {
        final ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, values);
        return list;
    }

    private class TerminalNode<T> extends AbstractNode<T, T> {

        private final ArrayList<T> mReceivedInputs;

        private boolean mCompleted;
        private boolean mErrorReceived;

        public TerminalNode() {
            mReceivedInputs = new ArrayList<>();
            reset();
        }

        @SuppressWarnings("unused")
        public boolean isCompleted() {
            return mCompleted;
        }

        @SuppressWarnings("unused")
        public boolean isErrorReceived() {
            return mErrorReceived;
        }

        public void reset() {
            mReceivedInputs.clear();
            mCompleted = false;
            mErrorReceived = false;
        }

        @Override
        public T processInput(final OutputNode<T> outputNode, final T input) {
            mReceivedInputs.add(input);
            return input;
        }

        @Override
        public void onCompleted(final OutputNode<?> outputNode) {
            mCompleted = true;
        }

        @Override
        public void onError(final OutputNode<?> outputNode, final Throwable throwable) {
            mErrorReceived = true;
        }

        @SuppressWarnings({"unchecked", "varargs"})
        public boolean received(final T... inputs) {
            final int receivedCount = mReceivedInputs.size();

            if (inputs.length == receivedCount) {
                for (int i = 0; i < receivedCount; i++) {
                    if (!inputs[i].equals(mReceivedInputs.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }

    private enum Error {
        A("A"),
        B("B"),
        C("C"),
        D("D"),
        E("E");

        private final String mErrorMsg;

        Error(final String errorMsg) {
            mErrorMsg = errorMsg;
        }

        public boolean is(final Response response) {
            return mErrorMsg.contentEquals(response.error);
        }

        public String getErrorMsg() {
            return mErrorMsg;
        }
    }

    private class Response {
        public String error;
        public int statusCode;
    }

    private interface Authenticator {

        void onAuthenticationFailed(Response response);
        void onAuthenticationSucceeded(Response response);
        void onRequestAuthCode();
    }

    private class AuthenticatorImpl implements Authenticator {

        private boolean mFailed;
        private boolean mSucceeded;

        public AuthenticatorImpl() {
            reset();
        }

        public void reset() {
            mFailed = false;
            mSucceeded = false;
        }

        @Override
        public void onAuthenticationFailed(Response response) {
            mFailed = true;
        }

        @Override
        public void onAuthenticationSucceeded(Response response) {
            mSucceeded = true;
        }

        @Override
        public void onRequestAuthCode() {
        }

        public boolean failed() {
            return mFailed;
        }

        public boolean succeeded() {
            return mSucceeded;
        }
    }

}
