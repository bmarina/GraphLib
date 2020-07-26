package com.example.graph.impl;

import com.example.graph.AbstractUndirectedGraph;

/**
 * Undirected graph implementation.
 *
 * @param <V> type of vertex.
 */
public class SimpleUndirectedGraphImpl<V> extends AbstractUndirectedGraph<V, DefaultEdge<V>> {

    public SimpleUndirectedGraphImpl() {
        super();
    }

    @Override
    protected DefaultEdge<V> createEdge(V sourceVertex, V targetVertex) {
        return new DefaultEdge<>(sourceVertex, targetVertex);
    }
}
