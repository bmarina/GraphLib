package com.example.graph;

/**
 * Abstract directed graph.
 *
 * @param <V>
 * @param <E>
 */
public abstract class AbstractDirectedGraph<V, E extends Edge<V>> extends AbstractGraph<V, E> {
    public AbstractDirectedGraph() {
        super();
    }

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public boolean isSelfLoopSupported() {
        return false;
    }
}
