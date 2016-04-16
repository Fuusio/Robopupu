package com.robopupu.api.graph;

import android.view.View;
import android.widget.TextView;

import com.robopupu.api.graph.functions.BooleanFunction;
import com.robopupu.api.graph.nodes.Action1Node;
import com.robopupu.api.graph.nodes.BooleanNode;
import com.robopupu.api.graph.nodes.BufferNode;
import com.robopupu.api.graph.nodes.ByteNode;
import com.robopupu.api.graph.nodes.CharacterNode;
import com.robopupu.api.graph.nodes.ConcatNode;
import com.robopupu.api.graph.nodes.ConcatStringsNode;
import com.robopupu.api.graph.nodes.CountNode;
import com.robopupu.api.graph.nodes.DistinctNode;
import com.robopupu.api.graph.nodes.DoubleNode;
import com.robopupu.api.graph.nodes.FilterNode;
import com.robopupu.api.graph.nodes.FirstNode;
import com.robopupu.api.graph.nodes.FloatNode;
import com.robopupu.api.graph.nodes.FunctionNode;
import com.robopupu.api.graph.nodes.LastNode;
import com.robopupu.api.graph.nodes.NthNode;
import com.robopupu.api.graph.nodes.IntegerNode;
import com.robopupu.api.graph.nodes.ListNode;
import com.robopupu.api.graph.nodes.LongNode;
import com.robopupu.api.graph.nodes.ObserverNode;
import com.robopupu.api.graph.nodes.RepeatNode;
import com.robopupu.api.graph.nodes.RequestNode;
import com.robopupu.api.graph.nodes.ReverseNode;
import com.robopupu.api.graph.nodes.ShortNode;
import com.robopupu.api.graph.nodes.SkipNode;
import com.robopupu.api.graph.nodes.SkipWhileNode;
import com.robopupu.api.graph.nodes.StringNode;
import com.robopupu.api.graph.nodes.SumNode;
import com.robopupu.api.graph.nodes.TakeNode;
import com.robopupu.api.graph.nodes.TextViewNode;
import com.robopupu.api.graph.nodes.ThreadNode;
import com.robopupu.api.graph.nodes.ViewNode;
import com.robopupu.api.graph.nodes.ZipInputNode;
import com.robopupu.api.network.RequestDelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * {@link Graph} is a builder utility for constructing graphs consisting of {@link Node}s.
 *
 * @param <T> The parametrized output type of the {@link Graph}.
 */
public class Graph<T> {

    protected final HashMap<Tag, OutputNode<?>> mTaggedNodes;
    protected final Tag<T> mBeginTag;

    protected OutputNode<T> mBeginNode;
    protected OutputNode<?> mCurrentNode;
    protected Tag mPendingAttachTag;

    protected Graph() {
        mBeginTag = new Tag<>();
        mTaggedNodes = new HashMap<>();
    }

    /**
     * Gets the begin {@link Tag}.
     * @return A {@link Tag}.
     */
    public Tag<T> getBeginTag() {
        return mBeginTag;
    }

    /**
     * Gets the begin {@link OutputNode} for this {@link Graph}.
     * @return The begin {@link OutputNode}.
     */
    @SuppressWarnings("unchecked")
    public <T_Node extends OutputNode<?>> T_Node getBeginNode() {
        return (T_Node)mBeginNode;
    }

    /**
     * Sets the begin {@link OutputNode}.
     * @param outputNode A {@link OutputNode}.
     */
    protected void setBeginNode(final OutputNode<T> outputNode) {
        if (mPendingAttachTag != null) {
            mTaggedNodes.put(mPendingAttachTag, outputNode);
            mPendingAttachTag = null;
        } else {
            mTaggedNodes.put(mBeginTag, outputNode);
        }
        mBeginNode = outputNode;
        mCurrentNode = outputNode;
    }

    /**
     * Sets the begin {@link OutputNode} with the given tag.
     * @param tag An attach {@link Tag}.
     * @param outputNode A {@link OutputNode}.
     */
    protected void setBeginNode(final Tag<T> tag, final OutputNode<T> outputNode) {
        mTaggedNodes.put(tag, outputNode);
        mBeginNode = outputNode;
        mCurrentNode = outputNode;
    }

    /**
     * Gets the current {@link Node}.
     * @return A {@link Node}.
     */
    @SuppressWarnings("unchecked")
    public <T_Node> T_Node getCurrentNode() {
        return (T_Node)mCurrentNode;
    }


    /**
     * Begins this {@link Graph} with an {@link Action1Node} as a begin node. The given {@link Action1}
     * is used to create the {@link Action1Node}.
     * @param action The action as an {@link Action1}.
     * @return A {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public static <OUT> Graph<OUT> begin(final Action1<OUT> action) {
        final Graph<OUT> graph = new Graph<>();
        graph.setBeginNode(new Action1Node<>(action));
        return graph;
    }

    /**
     * Begins this {@link Graph} with a {@link Action1Node} as a begin node. The begin
     * node is tagged with the given {@link Tag}. The given {@link Action1}
     * is used to create the {@link Action1Node}.
     * @param tag A {@link Tag}.
     * @param action The action as an {@link Action1}.
     * @return A {@link Graph}.
     */
    public static <OUT> Graph<OUT> begin(final Tag<OUT> tag, final Action1<OUT> action) {
        final Graph<OUT> graph = new Graph<>();
        graph.setBeginNode(tag, new Action1Node<>(action));
        return graph;
    }

    /**
     * Begins this {@link Graph} with the given {@link OutputNode} as a begin node.
     * @param outputNode A {@link OutputNode}
     * @return A {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public static <OUT> Graph<OUT> begin(final OutputNode<OUT> outputNode) {
        final Graph<OUT> graph = new Graph<>();
        graph.setBeginNode(outputNode);
        return graph;
    }

    /**
     * Begins this {@link Graph} with a {@link RequestNode} created for the given
     * {@link RequestDelegate}as a begin node.
     * @param delegate A {@link RequestDelegate}
     * @return A {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public static <OUT> Graph<OUT> begin(final RequestDelegate<OUT> delegate) {
        return begin(new RequestNode<>(delegate));
    }

    /**
     * Begins this {@link Graph} with the given {@link OutputNode} as a begin node. The begin
     * node is tagged with the given {@link Tag}.
     * @param tag An attach tag {@link Tag}.
     * @param outputNode A {@link OutputNode}
     * @return A {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public static <OUT> Graph<OUT> begin(final Tag<OUT> tag, final OutputNode<OUT> outputNode) {
        final Graph<OUT> graph = new Graph<>();
        graph.setBeginNode(tag, outputNode);
        return graph;
    }

    /**
     * Begins this {@link Graph} with a {@link ListNode} as a begin node.
     * @param list A {@link List}
     * @return A {@link Graph}.
     */
    public static <OUT> Graph<OUT> begin(final List<OUT> list) {
        return begin(new ListNode<>(list));
    }

    /**
     * Begins this {@link Graph} with a {@link ListNode} as a begin node.
     * @param items A variable length parameter of items for a  {@link List}
     * @return A {@link Graph}.
     */
    @SafeVarargs
    public static <OUT> Graph<OUT> begin(final OUT... items) {
        final ArrayList<OUT> itemsList = new ArrayList<>();
        Collections.addAll(itemsList, items);
        return begin(new ListNode<>(itemsList));
    }

    /**
     * Begins this {@link Graph} with a {@link ListNode} as a begin node. The begin
     * node is tagged with the given {@link Tag}.
     * @param tag A {@link Tag}.
     * @param items A variable length parameter of items for a  {@link List}
     * @return A {@link Graph}.
     */
    @SafeVarargs
    public static <OUT> Graph<OUT> begin(final Tag<OUT> tag, final OUT... items) {
        final ArrayList<OUT> itemsList = new ArrayList<>();
        Collections.addAll(itemsList, items);
        return begin(tag, new ListNode<>(itemsList));
    }

    /**
     * Begins this {@link Graph} with a {@link ListNode} as a begin node. The begin
     * node is tagged with the given {@link Tag}.
     * @param tag A {@link Tag}.
     * @param list A {@link List}
     * @return A {@link Graph}.
     */
    public static <OUT> Graph<OUT> begin(final Tag<OUT> tag, final List<OUT> list) {
        return begin(tag, new ListNode<>(list));
    }

    /**
     * Finds a {@link OutputNode} tagged with the given {@link Tag} and sets it to be current node.
     * @param tag A {@link Tag}.
     * @param <OUT> The output type of the current {@link Node}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public <OUT> Graph<OUT> node(final Tag<OUT> tag) {
        mCurrentNode = mTaggedNodes.get(tag);
        return (Graph<OUT>)this;
    }

    /**
     * Finds a {@link OutputNode} tagged with the given {@link Tag}.
     * @param tag A {@link Tag}.
     * @param <OUT> The output type of the {@link OutputNode}.
     * @return The found {@link OutputNode}. May return {@code null}.
     */
    @SuppressWarnings("unchecked")
    public <OUT> OutputNode<OUT> findNode(final Tag<OUT> tag) {
        return (OutputNode<OUT>)mTaggedNodes.get(tag);
    }

    /**
     * Attaches an {@link Action1Node} with the given action to the current {@link OutputNode}.
     * @param action The action as an {@link Action1}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> action(final Action1<T> action) {
        return to(new Action1Node<>(action));
    }

    /**
     * Attaches a {@link CountNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    public Graph<Integer> count() {
        return to(new CountNode<>());
    }


    /**
     * Attaches a {@link FunctionNode} with the given mapping function to the current {@link OutputNode}.
     * @param function The function as a {@link Function}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public <OUT> Graph<OUT> map(final Function<T, OUT> function) {
        return to(new FunctionNode<>(function));
    }

    /**
     * Tags the next {@link Node} with the given {@link Tag}.
     * @param tag The tag {@link Object}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> tag(final Tag<T> tag) {
        mPendingAttachTag = tag;
        return this;
    }

    /**
     * Attaches the given {@link Node} to be the next {@link Node} after the current {@link Node}.
     * @param node A {@link Node}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public <OUT> Graph<OUT> to(final Node<T, OUT> node) {
        Node<T, OUT> nextNode = node;

        if (node instanceof ZipInputNode) {
            nextNode = (Node<T, OUT>)((ZipInputNode)node).getZipNode();
        }

        if (mPendingAttachTag != null) {
            mTaggedNodes.put(mPendingAttachTag, nextNode);
            mPendingAttachTag = null;
        }

        if (mCurrentNode != null) {
            ((OutputNode<T>)mCurrentNode).attach(node);
        }
        mCurrentNode = nextNode;

        if (mBeginNode == null) {
            mBeginNode = (OutputNode<T>)node;
        }
        return (Graph<OUT>)this;
    }

    /**
     * Attaches the given {@link Node} to the specified {@link OutputNode}.
     * The attached {@link Node} is tagged with the given tag {@link Object}.
     * @param attachTag The tag {@link Object} that is used to tag the attached {@link Node}.
     * @param node A {@link Node}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public <OUT> Graph<OUT> to(final Tag attachTag, final Node<T, OUT> node) {
        tag(attachTag);
        return to(node);
    }

    /**
     * Attaches a {@link FilterNode} with the given condition to the current {@link OutputNode}.
     * @param condition The condition as a {@link BooleanFunction}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> filter(final BooleanFunction<T> condition) {
        return to(new FilterNode<>(condition));
    }

    /**
     * Attaches a {@link FirstNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> first() {
        return to(new FirstNode<>());
    }

    /**
     * Attaches a {@link FirstNode} with the given condition to the current {@link OutputNode}.
     * @param condition The condition as a {@link BooleanFunction}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> first(final BooleanFunction<T> condition) {
        return to(new FirstNode<>(condition));
    }

    /**
     * Attaches a {@link LastNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> last() {
        return to(new LastNode<>());
    }

    /**
     * Attaches a {@link LastNode} with the given condition to the current {@link OutputNode}.
     * @param condition The condition as a {@link BooleanFunction}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> last(final BooleanFunction<T> condition) {
        return to(new LastNode<>(condition));
    }

    /**
     * Attaches a {@link DistinctNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> distinct() {
        return to(new DistinctNode<>());
    }

    /**
     * Attaches a {@link ReverseNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> reverse() {
        return to(new ReverseNode<>());
    }

    /**
     * Attaches a {@link BufferNode} with the given capacity value to the current {@link OutputNode}.
     * @param capacity The buffer capacity value.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> buffer(final int capacity) {
        return to(new BufferNode<>(capacity));
    }

    /**
     * Attaches a {@link ObserverNode} for the given {@link NodeObserver} to the current {@link OutputNode}.
     * @param callback A {@link NodeObserver}.
     * @return This {@link Graph}.
     */
    public Graph<T> observer(final NodeObserver<T> callback) {
        return to(new ObserverNode<>(callback));
    }

    /**
     * Attaches a {@link RepeatNode} with the given times parameter value to the current {@link OutputNode}.
     * @param times The steps value.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> repeat(final int times) {
        return to(new RepeatNode<>(times));
    }

    /**
     * Attaches a {@link SkipNode} with the given steps parameter value to the current {@link OutputNode}.
     * @param steps The steps value.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> skip(final int steps) {
        return to(new SkipNode<>(steps));
    }

    /**
     * Attaches a {@link SkipWhileNode} with the given condition to the current {@link OutputNode}.
     * @param condition The condition as a {@link BooleanFunction}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> skipWhile(final BooleanFunction<T> condition) {
        return to(new SkipWhileNode<>(condition));
    }

    /**
     * Attaches a {@link StringNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    public Graph<String> string() {
        return to(new StringNode<>());
    }

    /**
     * Attaches a {@link TakeNode} with the given steps parameter value to the current {@link OutputNode}.
     * @param steps The steps value.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> take(final int steps) {
        return to(new TakeNode<>(steps));
    }

    /**
     * Attaches a {@link NthNode} with the given index parameter value to the current {@link OutputNode}.
     * @param index The index value.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public Graph<T> nth(final int index) {
        return to(new NthNode<>(index));
    }

    /**
     * Attaches a {@link SumNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    public Graph<Double> sum() {
        return to(new SumNode<>());
    }

    /**
     * Attaches a {@link ConcatNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    @SafeVarargs
    public final Graph<T> concat(final OutputNode<T>... sources) {
        return to(new ConcatNode<>(sources));
    }

    /**
     * Attaches a {@link ConcatStringsNode} to the current {@link OutputNode}.
     * @return This {@link Graph}.
     */
    public Graph<String> concatStrings() {
        return to(new ConcatStringsNode<>());
    }

    /**
     * Attaches an {@link ViewNode} for the given {@link View} to produce click outputs.
     * @param view A {@link View}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public static Graph<View> onClick(final View view) {
        final Graph<View> graph = new Graph<>();
        graph.setBeginNode(new ViewNode(view));
        return graph;
    }

    /**
     * Attaches an {@link TextViewNode} for the given {@link TextView} to produce inputted text
     * as an output.
     * @param view A {@link TextView}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public static Graph<String> onTextChanged(final TextView view) {
        final Graph<String> graph = new Graph<>();
        graph.setBeginNode(new TextViewNode(view));
        return graph;
    }

    /**
     * Attaches an {@link RequestNode} for the given {@link RequestDelegate}.
     * @param delegate A {@link RequestDelegate}.
     * @return This {@link Graph}.
     */
    @SuppressWarnings("unchecked")
    public <OUT> Graph<OUT> request(final RequestDelegate<OUT> delegate) {
        return to(new RequestNode<>(delegate));
    }

    /**
     * Resets this {@link Graph} by starting from the begin node.
     */
    public void reset() {
        onReset();
        getBeginNode().onReset();
    }

    /**
     * Invoked by {@link Graph#reset()}.
     */
    protected void onReset() {
        // By default do nothing
    }

    /**
     * Invokes the begin node to emit its value(s).
     */
    public void start() {
        onEmit();
        getBeginNode().emitOutput();
    }

    /**
     * Invoked by {@link Graph#start()}.
     */
    protected void onEmit() {
        // By default do nothing
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code boolean} value.
     * @return A {@code boolean} value.
     */
    public boolean booleanValue() {
        final BooleanNode<T> node = new BooleanNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code byte} value.
     * @return A {@code byte} value.
     */
    public byte byteValue() {
        final ByteNode<T> node = new ByteNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code char} value.
     * @return A {@code char} value.
     */
    public char charValue() {
        final CharacterNode<T> node = new CharacterNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code double} value.
     * @return A {@code double} value.
     */
    public double doubleValue() {
        final DoubleNode<T> node = new DoubleNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code float} value.
     * @return A {@code float} value.
     */
    public float floatValue() {
        final FloatNode<T> node = new FloatNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code int} value.
     * @return An {@code int} value.
     */
    public int intValue() {
        final IntegerNode<T> node = new IntegerNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code long} value.
     * @return A {@code long} value.
     */
    public long longValue() {
        final LongNode<T> node = new LongNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@code short} value.
     * @return A {@code short} value.
     */
    public short shortValue() {
        final ShortNode<T> node = new ShortNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted output to {@link String} value.
     * @return A {@code short} value.
     */
    public String stringValue() {
        final StringNode<T> node = new StringNode<>();
        to(node);
        start();
        return node.getValue();
    }

    /**
     * Invokes emit on {@link Graph} and converts the emitted outputs to {@link List}.
     * @return A {@link List}.
     */
    public List<T> toList() {
        final ListNode<T> node = new ListNode<>();
        to(node);
        start();
        return node.getList();
    }

    /**
     * Adds the given {@link InputNode} as an end node.
     * @param inputNode An {@link InputNode}.
     */
    @SuppressWarnings("unchecked")
    public <IN> Graph<IN> end(final InputNode<IN> inputNode) {
        ((OutputNode<IN>)mCurrentNode).attach(inputNode);
        return (Graph<IN>)this;
    }

    /**
     * Adds the specified {@link Action1Node} as and end nodes.
     * @param action An {@link Action1} specifying the added {@link Action1Node}.
     */
    @SuppressWarnings("unchecked")
    public <IN> Graph<IN> end(final Action1<IN> action) {
        ((OutputNode<IN>)mCurrentNode).attach(new Action1Node<>(action));
        return (Graph<IN>)this;
    }

    /**
     * Transfers the execution to the main thread.
     * @return This {@link Graph}.
     */
    public Graph<T> toMain() {
        return to(new ThreadNode<>(true));
    }

    /**
     * Transfers the execution to a worker thread.
     * @return This {@link Graph}.
     */
    public Graph<T> toWorker() {
        return to(new ThreadNode<>(false));
    }
}
