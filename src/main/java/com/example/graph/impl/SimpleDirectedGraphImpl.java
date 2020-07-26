package com.example.graph.impl;

import com.example.graph.AbstractDirectedGraph;

/**
 * Directed graph implementation.
 *
 * @param <V> type of vertex.
 */
public class SimpleDirectedGraphImpl<V> extends AbstractDirectedGraph<V, DefaultEdge<V>> {

    public SimpleDirectedGraphImpl() {
        super();
    }

    @Override
    protected DefaultEdge<V> createEdge(V sourceVertex, V targetVertex) {
        return new DefaultEdge<>(sourceVertex, targetVertex);
    }
}
