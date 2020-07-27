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
    protected void createLinkBetweenVertices(E edge) {
        if (containsEdge(edge)) {
            throw new IllegalArgumentException(MSG_EDGE_SHOULD_BE_UNIQUE);
        }

        // Register outgoing edge.
        getMap().compute(edge.getSource(), (v, edgeContainer) -> {
            if (edgeContainer == null) {
                edgeContainer = createEdgeContainer();
            }
            edgeContainer.addOutgoingEdge(edge);
            return edgeContainer;
        });

        // Register incoming edge.
        getMap().compute(edge.getTarget(), (v, edgeContainer) -> {
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
