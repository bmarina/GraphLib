package com.example.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract graph implementation.
 *
 * @param <V> vertex type.
 * @param <E> edge type.
 */
public abstract class AbstractGraph<V, E extends Edge<V>> implements Graph<V, E> {
    public static final String MSG_LOOPS_NOT_SUPPORTED = "Self loops are not supported";
    public static final String MSG_UNKNOWN_SOURCE_VERTEX = "Unknown source vertex";
    public static final String MSG_UNKNOWN_TARGET_VERTEX = "Unknown target vertex";
    public static final String MSG_VERTEX_SHOULD_BE_NON_NULL = "Vertex should be not null";
    public static final String MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL = "Source vertex should be not null";
    public static final String MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL = "Target vertex should be not null";
    public static final String MSG_EDGE_SHOULD_BE_UNIQUE = "Edge should be unique";

    // Map of vertex connections.
    private final Map<V, Set<E>> mapOfEdges;

    // Set of edges. It is used for checking edge uniqueness.
    private final Set<E> setOfEdges;

    public AbstractGraph() {
        this.mapOfEdges = new HashMap<>();
        this.setOfEdges = new HashSet<>();
    }

    /**
     * Abstract method that is used for new edges creation.
     * All new objects should be unique.
     * You could implement any logic you need.
     *
     * @param sourceVertex source vertex. Can't be null.
     * @param targetVertex target vertex. Can't be null.
     * @return new edge object.
     */
    protected abstract E createEdge(V sourceVertex, V targetVertex);

    @Override
    public void addVertex(final V vertex) {
        Objects.requireNonNull(vertex, MSG_VERTEX_SHOULD_BE_NON_NULL);
        mapOfEdges.putIfAbsent(vertex, new HashSet<>());
    }

    @Override
    public void addEdge(final V sourceVertex, final V targetVertex) {
        Objects.requireNonNull(sourceVertex, MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL);
        Objects.requireNonNull(targetVertex, MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL);

        if (!containsVertex(sourceVertex)) {
            throw new IllegalArgumentException(MSG_UNKNOWN_SOURCE_VERTEX);
        }

        if (!containsVertex(targetVertex)) {
            throw new IllegalArgumentException(MSG_UNKNOWN_TARGET_VERTEX);
        }

        if (!isSelfLoopSupported() && sourceVertex.equals(targetVertex)) {
            throw new IllegalArgumentException(MSG_LOOPS_NOT_SUPPORTED);
        }

        createLinkBetweenVertisies(sourceVertex, targetVertex);

        if (!isDirected()) {
            createLinkBetweenVertisies(targetVertex, sourceVertex);
        }
    }

    @Override
    public boolean containsVertex(V vertex) {
        return mapOfEdges.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(E edge) {
        return setOfEdges.contains(edge);
    }

    @Override
    public Set<E> getOutgoingEdges(V vertex) {
        containsVertex(vertex);
        if (isDirected()) {
            return mapOfEdges.get(vertex);
        } else {
            throw new IllegalStateException("Not implemented");
        }
    }

    @Override
    public Set<E> getIncomingEdges(V vertex) {
        throw new IllegalStateException("Not implemented");
    }

    protected void createLinkBetweenVertisies(final V sourceVertex, final V targetVertex) {
        final E edge = createEdge(sourceVertex, targetVertex);
        if (containsEdge(edge)) {
            throw new IllegalArgumentException(MSG_EDGE_SHOULD_BE_UNIQUE);
        }
        setOfEdges.add(edge);
        mapOfEdges.compute(sourceVertex, (v, es) -> {
            if (es == null) {
                es = new HashSet<>();
            }
            es.add(edge);
            return es;
        });
    }
}
