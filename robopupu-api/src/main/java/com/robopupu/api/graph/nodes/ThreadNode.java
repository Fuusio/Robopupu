package com.robopupu.api.graph.nodes;

import android.os.Handler;
import android.os.Looper;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.InputNode;
import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ThreadNode} implements {@link Node} that can be used to transfer execution to a worker
 * {@link Thread} or to main {@link Thread}.
 * @param <IN> The type of input value.
 * @param <OUT> The type of output value.
 */
public class ThreadNode<IN, OUT> extends AbstractNode<IN, OUT> {

    private final Handler mHandler;
    private final boolean mExecuteInMainThread;

    private boolean mCancelled;
    private Runnable mRunnable;

    /**
     * Constructs a new instance of {@link ThreadNode}.
     */
    public ThreadNode() {
        this(true);
    }

    /**
     * Constructs a new instance of {@link ThreadNode} for transferring the execution to main or
     * background {@link Thread} depending on the given boolean flag.
     *
     * @param executeInMainThread A {@code boolean} flag.
     */
    public ThreadNode(final boolean executeInMainThread) {
        mExecuteInMainThread = executeInMainThread;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Cancels the worker {@link Thread}.
     */
    public void cancel() {
        mCancelled = true;
    }

    @Override
    public void emitOutput() {
        emitOutput(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<IN> outputNode, final IN input) {
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
    protected void emitOutput(final OUT output) {
        if (mExecuteInMainThread) {
            // Note : This could be replaced with a lambda expression
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    for (final InputNode<OUT> inputNode : mInputNodes) {
                        inputNode.onInput(ThreadNode.this, output);
                    }
                }
            };
            mHandler.post(mRunnable);
        } else {
            mCancelled = false;

            // Note : This could be replaced with a lambda expression
            mRunnable = new Runnable() {
                public void run() {
                    for (final InputNode<OUT> inputNode : mInputNodes) {
                        if (!mCancelled) {
                            inputNode.onInput(ThreadNode.this, output);
                        } else {
                            break;
                        }
                    }
                }
            };
            new Thread(mRunnable).start();
        }
    }

    @Override
    public void doOnReset() {
        mCancelled = false;
    }
}
