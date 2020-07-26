package com.example.graph.impl;

import com.example.graph.AbstractDirectedGraph;

/**
 * Directed graph implementation.
 * Edges are implemented by DefaultEdge.class and can be replaces.
 *
 * @param <V> type of vertex. Any immutable class.
 */
public class SimpleDirectedGraphImpl<V> extends AbstractDirectedGraph<V, DefaultEdge<V>> {

    /**
     * Default constructor.
     */
    public SimpleDirectedGraphImpl() {
        super();
    }

    @Override
    protected DefaultEdge<V> createEdge(V sourceVertex, V targetVertex) {
        return new DefaultEdge<>(sourceVertex, targetVertex);
    }
}
