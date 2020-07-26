package com.example.graph;

/**
 * Abstract undirected graph.
 *
 * @param <V>
 * @param <E>
 */
public abstract class AbstractUndirectedGraph<V, E extends Edge<V>> extends AbstractGraph<V, E> {
    public AbstractUndirectedGraph() {
        super();
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public boolean isSelfLoopSupported() {
        return false;
    }
}
