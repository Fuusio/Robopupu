package com.robopupu.api.graph;

import android.support.annotation.CallSuper;
import android.view.View;
import android.widget.TextView;

import com.robopupu.api.graph.actions.Action;
import com.robopupu.api.graph.actions.Action1;
import com.robopupu.api.graph.actions.OutputAction;
import com.robopupu.api.graph.functions.BooleanFunction;
import com.robopupu.api.graph.nodes.Action1Node;
import com.robopupu.api.graph.nodes.ActionNode;
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
import com.robopupu.api.graph.nodes.IntegerNode;
import com.robopupu.api.graph.nodes.LastNode;
import com.robopupu.api.graph.nodes.LongNode;
import com.robopupu.api.graph.nodes.MergeNode;
import com.robopupu.api.graph.nodes.NthNode;
import com.robopupu.api.graph.nodes.ObserverNode;
import com.robopupu.api.graph.nodes.OutputActionNode;
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
import com.robopupu.api.graph.nodes.TimerNode;
import com.robopupu.api.graph.nodes.ViewNode;
import com.robopupu.api.network.RequestDelegate;

import java.util.ArrayList;

/**
 * {@link Node} is a base class for all nodes.
 * @param <IN> The input type.
 * @param <OUT> The output type.
 */
@SuppressWarnings("WeakerAccess")
public class Node<IN, OUT> implements InputNode<IN>, OutputNode<OUT> {

    protected final ArrayList<InputNode<OUT>> inputNodes;

    protected Graph<?> graph;

    private boolean errorReceived;

    /**
     * Constructs a new instance of {@link Node}.
     */
    protected Node() {
        errorReceived = false;
        inputNodes = new ArrayList<>();
    }

    /**
     * Creates an {@link OutputActionNode} for the given {@link OutputAction}.
     * @param action A {@link OutputAction}.
     * @return The created {@link OutputActionNode}.
     */
    public static <OUT> OutputActionNode<OUT> begin(final OutputAction<OUT> action) {
        return new OutputActionNode<>(action);
    }

    /**
     * Creates an {@link RequestNode} for the given {@link RequestDelegate}.
     * @param delegate A {@link RequestDelegate}.
     * @return The created {@link RequestNode}.
     */
    public static <OUT> RequestNode<OUT, OUT> begin(final RequestDelegate<OUT> delegate) {
        return new RequestNode<>(delegate);
    }

    /**
     * Creates a {@link TimerNode} with the given input value and delay.
     * @param input The input value.
     * @param delay The delay in milliseconds.
     * @return The created {@link RequestNode}.
     */
    public static <OUT> TimerNode<OUT> beginTimer(final OUT input, final long delay) {
        return new TimerNode<>(input, delay);
    }

    /**
     * Creates a {@link TimerNode} with the given input value and parameters.
     * @param input The input value.
     * @param delay The delay in milliseconds.
     * @param interval The interval in milliseconds for repeated timeouts.
     * @param repeatCount The number of repeats.
     * @return The created {@link RequestNode}.
     */
    public static <OUT> TimerNode<OUT> beginTimer(final OUT input, final long delay, final long interval, final int repeatCount) {
        return new TimerNode<>(input, delay, interval, repeatCount);
    }

    /**
     * Creates an {@link ViewNode} for the given {@link View}.
     * @param view A {@link View}.
     * @return The created {@link ViewNode}.
     */
    public static ViewNode whenClicked(final View view) {
        return new ViewNode(view);
    }

    /**
     * Creates an {@link TextViewNode} for the given {@link TextView}.
     * @param textView A {@link TextView}.
     * @return The created {@link TextViewNode}.
     */
    public static TextViewNode whenTextChanged(final TextView textView) {
        return new TextViewNode(textView);
    }

    /**
     * Tests if an error has been received.
     * @return A {@code boolean}.
     */
    public boolean isErrorReceived() {
        return errorReceived;
    }

    /**
     * Sets the flag that indicates if an error is received.
     * @param errorReceived A {@code boolean}.
     */
    protected void setErrorReceived(final boolean errorReceived) {
        this.errorReceived = errorReceived;
    }

    public Graph<?> getGraph() {
        if (graph == null) {
            for (final InputNode<OUT> inputNode : inputNodes) {
                graph = inputNode.getGraph();

                if (graph != null) {
                    break;
                }
            }
        }
        return graph;
    }

    @Override
    public void setGraph(final Graph<?> graph) {
        this.graph = graph;
    }

    /**
     * Invoked to emit the given output to attached {@link InputNode}s, if any.
     * @param output The outbut {@link Object}.
     */
    protected void emitOutput(final OUT output) {
        if (!errorReceived && output != null) {
            for (final InputNode<OUT> inputNode : inputNodes) {
                inputNode.onInput(this, output);
            }
        }
    }

    /**
     * Invoked to notify all {@link InputNode}s about completion.
     * @param source The completed {@link OutputNode}.
     */
    protected void completed(final OutputNode<?> source) {
        for (final InputNode<OUT> inputNode : inputNodes) {
            inputNode.onCompleted(source);
        }
    }

    /**
     * Dispatches an error to all {@link InputNode}s, if any.
     * @param source The {@link OutputNode} that has detected the error.
     * @param throwable A {@link Throwable} representing the error.
     */
    protected void dispatchError(final OutputNode<?> source, final Throwable throwable) {
        errorReceived = true;

        for (final InputNode<OUT> inputNode : inputNodes) {
            inputNode.onError(source, throwable);
        }
    }

    @Override
    public InputNode<OUT> attach(final InputNode<OUT> inputNode) {
        return addInputNode(inputNode);
    }

    /**
     * Adds the given {@link InputNode} to the set of attached {@link InputNode}s.
     * @param inputNode A {@link InputNode}.
     * @return The added {@link InputNode}.
     */
    protected InputNode<OUT> addInputNode(final InputNode<OUT> inputNode) {
        if (!inputNodes.contains(inputNode)) {
            inputNodes.add(inputNode);
            onAttached(inputNode);
        }
        return inputNode;
    }

    /**
     * Invoked when the given output {@link InputNode} has been attached to this
     * {@link Node}.
     * @param inputNode The attached {@link InputNode}.
     */
    protected void onAttached(final InputNode<OUT> inputNode) {
        // By default do nothing
    }

    @Override
    public void detach(final InputNode<OUT> inputNode) {
        removeInputNode(inputNode);
    }

    /**
     * Removes the given {@link InputNode} from the set of attached {@link InputNode}s.
     * @param inputNode A {@link InputNode}.
     */
    protected void removeInputNode(final InputNode<OUT> inputNode) {
        if (inputNodes.remove(inputNode)) {
            onDetached(inputNode);
        }
    }

    /**
     * Invoked when the given  {@link InputNode} has been detached from this
     * {@link Node}.
     * @param inputNode The detached {@link InputNode}.
     */
    @SuppressWarnings("unused")
    protected void onDetached(final InputNode<OUT> inputNode) {
        // By default do nothing
    }

    /**
     * Tests if this {@link OutputNode} has any attached @link InputNode}s.
     * @return A {@code boolean} value.
     */
    public boolean hasInputNodes() {
        return !inputNodes.isEmpty();
    }

    @Override
    public void emitOutput() {
        // By default do nothing
    }

    /**
     * Invoked to reset this {@link Node}.
     */
    @CallSuper
    @Override
    public void onReset() {
        errorReceived = false;
        dispatchReset();
    }

    /**
     * Dispatches a resetting to all {@link InputNode}s, if any.
     */
    protected void dispatchReset() {
        doOnReset();

        for (final InputNode<OUT> inputNode : inputNodes) {
            inputNode.onReset();
        }
    }

    /**
     * Invoked by {@link InputNode#onCompleted(OutputNode)}. This method can be overridden
     * in subclasses for hooking on the reset events.
     */
    protected void doOnReset() {
        // By default do nothing
    }

    @CallSuper
    @Override
    public void onInput(final OutputNode<IN> source, final IN input) {
        if (!errorReceived) {
            doOnInput(source, input);
            emitOutput(processInput(source, input));
        }
    }

    /**
     * Invoked by {@link InputNode#onInput(OutputNode, Object)} when the given input {@link Object}
     * has been received from the {@link OutputNode} that emitted it. This method can be overridden
     * in subclasses for hooking on the input received events.
     * @param source An {@link OutputNode} that emitted the input.
     * @param input The input {@link Object}.
     */
    protected void doOnInput(final OutputNode<IN> source, final IN input) {
        // By default do nothing
    }

    /**
     * Invoked by {@link InputNode#onInput(OutputNode, Object)} when the given input {@link Object}
     * has been received from the {@link OutputNode} that emitted it.
     * @param source An {@link OutputNode} that emitted the input.
     * @param input The input {@link Object}.
     */
    @SuppressWarnings("unchecked")
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            try {
                return (OUT) input;
            } catch (ClassCastException e) {
                dispatchError(this, e);
            }
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        doOnCompleted(source);

        for (final InputNode<OUT> inputNode : inputNodes) {
            inputNode.onCompleted(source);
        }
    }

    /**
     * Invoked by {@link InputNode#onCompleted(OutputNode)}. This method can be overridden
     * in subclasses for hooking on the completed events.
     * @param source An {@link OutputNode} that was completed.
     */
    protected void doOnCompleted(final OutputNode<?> source) {
        // By default do nothing
    }

    @CallSuper
    @Override
    public void onError(final OutputNode<?> source, final Throwable throwable) {
        doOnError(source, throwable);
        dispatchError(source, throwable);
    }

    /**
     * Invoked by {@link InputNode#onCompleted(OutputNode)}. This method can be overridden
     * in subclasses for hooking on the error received events.
     * @param source The {@link OutputNode} notifying about error.
     * @param throwable A {@link Throwable} representing the error.
     */
    protected void doOnError(final OutputNode<?> source, final Throwable throwable) {
        // By default do nothing
    }

    /**
     * Invokes the begin node of the {@link Graph} to emit its value(s).
     */
    public void start() {
        getGraph().start();
    }

    /**
     * Creates and formats an error message.
     * @param message The error message as a {@link String}. May contain place holders for formatting.
     * @param args Optional formats args.
     * @return The created error message as a {@link String}.
     */
    protected String createErrorMessage(final String message, final String... args) {
        final String formattedMessage = String.format(message, (Object[]) args);
        return "Error in nodes " + getClass().getSimpleName() + " : " + formattedMessage;
    }

    /**
     * Attaches an {@link ActionNode} with the given {@link Action} to this {@link Node}.
     * @param action An {@link Action}.
     * @return The attached {@link Node}.
     */
    public <T> Node<OUT, T> action(final Action action) {
        final ActionNode<OUT, T> node = new ActionNode<>(action);
        attach(node);
        return node;
    }

    /**
     * Attaches an {@link Action1Node} with the given {@link Action1} to this {@link Node}.
     * @param action An {@link Action1}.
     * @return The attached {@link Node}.
     */
    public <T> Node<OUT, T> action(final Action1<OUT> action) {
        final Action1Node<OUT, T> node = new Action1Node<>(action);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link BufferNode} with the given capacity to this {@link Node}.
     * @link capacity The capacity as an {@code int} value.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> buffer(final int capacity) {
        final BufferNode<OUT> node = new BufferNode<>(capacity);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link ConcatNode} to the current {@link OutputNode}.
     * @return The attached {@link Node}.
     */
    @SafeVarargs
    public final  Node<OUT, OUT> concat(final OutputNode<OUT>... sources) {
        final ConcatNode<OUT> node = new ConcatNode<>(sources);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link ConcatStringsNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, String> concatStrings() {
        final ConcatStringsNode<OUT> node = new ConcatStringsNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link CountNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Integer> count() {
        final CountNode<OUT> node = new CountNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link DistinctNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> distinct() {
        final DistinctNode<OUT> node = new DistinctNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link FilterNode} with the given {@link BooleanFunction} to this {@link Node}.
     * @param condition A {@link BooleanFunction}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> filter(final BooleanFunction<OUT> condition) {
        final FilterNode<OUT> node = new FilterNode<>(condition);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link FirstNode} with the given {@link BooleanFunction} to this {@link Node}.
     * @param condition A {@link BooleanFunction}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> first(final BooleanFunction<OUT> condition) {
        final FirstNode<OUT> node = new FirstNode<>(condition);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link FirstNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> first() {
        final FirstNode<OUT> node = new FirstNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link FunctionNode} with the given {@link Function} to this {@link Node}.
     * @param function A {@link Function}.
     * @return The attached {@link Node}.
     */
    public <T> Node<OUT, T> map(final Function<OUT, T> function) {
        final FunctionNode<OUT, T> node = new FunctionNode<>(function);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link LastNode} with the given {@link BooleanFunction} to this {@link Node}.
     * @param condition A {@link BooleanFunction}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> last(final BooleanFunction<OUT> condition) {
        final LastNode<OUT> node = new LastNode<>(condition);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link LastNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> last() {
        final LastNode<OUT> node = new LastNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link MergeNode} to the current {@link OutputNode}.
     * @return The attached {@link Node}.
     */
    @SafeVarargs
    public final  Node<OUT, OUT> merge(final OutputNode<OUT>... sources) {
        final MergeNode<OUT> node = new MergeNode<>(sources);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link NthNode} with the given index parameter to this {@link Node}.
     * @param steps The index value.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> nth(final int steps) {
        final NthNode<OUT> node = new NthNode<>(steps);
        attach(node);
        return node;
    }

    /**
     * Attaches an {@link ObserverNode} with the given {@link NodeObserver} to this {@link Node}.
     * @param observer A {@link NodeObserver}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> observer(final NodeObserver<OUT> observer) {
        final ObserverNode<OUT> node = new ObserverNode<>(observer);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link RepeatNode} with the given times parameter to this {@link Node}.
     * @param times The times parameter.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> repeat(final int times) {
        final RepeatNode<OUT> node = new RepeatNode<>(times);
        attach(node);
        return node;
    }

    /**
     * Attaches an {@link RequestNode} with the given {@link RequestDelegate} to this {@link Node}.
     * @param delegate A {@link RequestDelegate}.
     * @return The attached {@link Node}.
     */
    public <T> Node<OUT, T> observer(final RequestDelegate<T> delegate) {
        final RequestNode<OUT, T> node = new RequestNode<>(delegate);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link ReverseNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> reverse() {
        final ReverseNode<OUT> node = new ReverseNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link SkipNode} with the given steps parameter to this {@link Node}.
     * @param steps The steps value.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> skip(final int steps) {
        final SkipNode<OUT> node = new SkipNode<>(steps);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link SkipWhileNode} with the given {@link BooleanFunction} to this {@link Node}.
     * @param condition A {@link BooleanFunction}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> skipWhile(final BooleanFunction<OUT> condition) {
        final SkipWhileNode<OUT> node = new SkipWhileNode<>(condition);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link StringNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, String> string() {
        final StringNode<OUT> node = new StringNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link SumNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Double> sum() {
        final SumNode<OUT> node = new SumNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link TakeNode} with the given steps parameter to this {@link Node}.
     * @param steps The steps value.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> take(final int steps) {
        final TakeNode<OUT> node = new TakeNode<>(steps);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link TimerNode} with the given delay to this {@link Node}.
     * @param delay The delay in milliseconds.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> timer(final long delay) {
        final TimerNode<OUT> node = new TimerNode<>(delay);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link TimerNode} with the given parameters to this {@link Node}.
     * @param delay The delay in milliseconds.
     * @param interval The interval in milliseconds for repeated timeouts.
     * @param repeatCount The number of repeats.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> timer(final long delay, final long interval, final int repeatCount) {
        final TimerNode<OUT> node = new TimerNode<>(delay, interval, repeatCount);
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link BooleanNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Boolean> toBoolean() {
        final BooleanNode<OUT> node = new BooleanNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link ByteNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Byte> toByte() {
        final ByteNode<OUT> node = new ByteNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link CharacterNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Character> toChar() {
        final CharacterNode<OUT> node = new CharacterNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link DoubleNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Double> toDouble() {
        final DoubleNode<OUT> node = new DoubleNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link FloatNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Float> toFloat() {
        final FloatNode<OUT> node = new FloatNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches an {@link IntegerNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Integer> toInt() {
        final IntegerNode<OUT> node = new IntegerNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link LongNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Long> toLong() {
        final LongNode<OUT> node = new LongNode<>();
        attach(node);
        return node;
    }

    /**
     * Attaches a {@link ShortNode} to this {@link Node}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, Short> toShort() {
        final ShortNode<OUT> node = new ShortNode<>();
        attach(node);
        return node;
    }

    /**
     * Transfers the execution to the main thread by attaching a {@link ThreadNode}.
     * @return The attached {@link Node}.
     */
    public Node<OUT, OUT> toMain() {
        final ThreadNode<OUT, OUT> node = new ThreadNode<>(true);
        attach(node);
        return node;
    }

    /**
     * Transfers the execution to a worker thread by attaching a {@link ThreadNode}.
     * @return The attached {@link Node}.
     */
    public <T> Node<OUT, OUT> toWorker() {
        final ThreadNode<OUT, OUT> node = new ThreadNode<>(false);
        attach(node);
        return node;
    }
}
