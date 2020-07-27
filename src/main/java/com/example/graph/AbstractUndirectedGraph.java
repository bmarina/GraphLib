package com.example.graph;

import com.example.graph.impl.UndirectedEdgeContainer;

/**
 * Abstract undirected graph.
 *
 * @param <V>
 * @param <E>
 */
public abstract class AbstractUndirectedGraph<V, E extends Edge<V>> extends AbstractGraph<V, E> {
    /**
     * Default constructor.
     */
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

    @Override
    public boolean containsEdge(E edge) {
        return super.containsEdge(edge) || super.containsEdge((E) edge.getOpposite());
    }

    @Override
    protected EdgeContainer<E> createEdgeContainer() {
        return new UndirectedEdgeContainer<E>();
    }

    @Override
    protected void createLinkBetweenVertices(E edge) {
        // Checks both types sorce->target and target->source.
        if (containsEdge(edge)) {
            throw new IllegalArgumentException(MSG_EDGE_SHOULD_BE_UNIQUE);
        }

        // Register edge for source.
        getMap().compute(edge.getSource(), (v, edgeContainer) -> {
            if (edgeContainer == null) {
                edgeContainer = createEdgeContainer();
            }
            edgeContainer.addOutgoingEdge(edge);
            return edgeContainer;
        });

        // Register edge for source.
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
