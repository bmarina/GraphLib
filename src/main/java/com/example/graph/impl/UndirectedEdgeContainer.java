package com.example.graph.impl;


import com.example.graph.EdgeContainer;

import java.util.HashSet;
import java.util.Set;

/**
 * Edge container for undirected graph.
 *
 * @param <E> edge type.
 */
public final class UndirectedEdgeContainer<E> implements EdgeContainer<E> {
    private final Set<E> edges;

    /**
     * Default constructor.
     */
    public UndirectedEdgeContainer() {
        this.edges = new HashSet<>();
    }

    @Override
    public Set<E> getIncomingEdges() {
        return this.edges;
    }

    @Override
    public Set<E> getOutgoingEdges() {
        return this.edges;
    }

    @Override
    public void addIncomingEdge(E edge) {
        this.edges.add(edge);
    }

    @Override
    public void addOutgoingEdge(E edge) {
        this.edges.add(edge);
    }
}
