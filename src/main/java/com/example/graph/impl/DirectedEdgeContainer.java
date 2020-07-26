package com.example.graph.impl;


import com.example.graph.EdgeContainer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Edge container for directed graph.
 *
 * @param <E> edge type.
 */
public final class DirectedEdgeContainer<E> implements EdgeContainer<E> {
    public static final String MSG_EDGE_SHOULD_BE_NON_NULL = "Edge should be non null";

    private final Set<E> incomingEdges;
    private final Set<E> outgoingEdges;

    /**
     * Default constructor.
     */
    public DirectedEdgeContainer() {
        this.incomingEdges = new HashSet<>();
        this.outgoingEdges = new HashSet<>();
    }

    @Override
    public Set<E> getIncomingEdges() {
        return this.incomingEdges;
    }

    @Override
    public Set<E> getOutgoingEdges() {
        return this.outgoingEdges;
    }

    @Override
    public void addIncomingEdge(E edge) {
        Objects.requireNonNull(edge, MSG_EDGE_SHOULD_BE_NON_NULL);
        this.incomingEdges.add(edge);
    }

    @Override
    public void addOutgoingEdge(E edge) {
        Objects.requireNonNull(edge, MSG_EDGE_SHOULD_BE_NON_NULL);
        this.outgoingEdges.add(edge);
    }
}
