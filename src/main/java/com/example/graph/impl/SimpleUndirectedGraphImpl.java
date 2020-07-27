package com.example.graph.impl;

import com.example.graph.AbstractUndirectedGraph;

/**
 * Undirected graph implementation.
 * Edges are implemented by DefaultEdge.class and can be replaces.
 *
 * @param <V> type of vertex. Any immutable class.
 */
public class SimpleUndirectedGraphImpl<V> extends AbstractUndirectedGraph<V, DefaultEdge<V>> {

    /**
     * Default constructor.
     */
    public SimpleUndirectedGraphImpl() {
        super();
    }

    @Override
    protected DefaultEdge<V> createEdge(V sourceVertex, V targetVertex) {
        return new DefaultEdge<>(sourceVertex, targetVertex);
    }
}
