package com.example.graph;

import com.example.graph.impl.DirectedEdgeContainer;

/**
 * Abstract directed graph.
 *
 * @param <V>
 * @param <E>
 */
public abstract class AbstractDirectedGraph<V, E extends Edge<V>> extends AbstractGraph<V, E> {
    /**
     * Default constructor.
     */
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

    @Override
    protected EdgeContainer<E> createEdgeContainer() {
        return new DirectedEdgeContainer<E>();
    }

    @Override
    protected void createLinkBetweenVertices(V sourceVertex, V targetVertex) {
        // Both vertices should exist. If they don't exist this method will create them.
        final E edge = createEdge(sourceVertex, targetVertex);
        if (containsEdge(edge)) {
            throw new IllegalArgumentException(MSG_EDGE_SHOULD_BE_UNIQUE);
        }

        // Register outgoing edge.
        getMap().compute(sourceVertex, (v, edgeContainer) -> {
            if (edgeContainer == null) {
                edgeContainer = createEdgeContainer();
            }
            edgeContainer.addOutgoingEdge(edge);
            return edgeContainer;
        });

        // Register incoming edge.
        getMap().compute(targetVertex, (v, edgeContainer) -> {
            if (edgeContainer == null) {
                edgeContainer = createEdgeContainer();
            }
            edgeContainer.addIncomingEdge(edge);
            return edgeContainer;
        });

        // Register edge.
        registerEdge(edge);
    }
}
