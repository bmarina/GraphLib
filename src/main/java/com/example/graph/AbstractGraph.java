package com.example.graph;

import java.util.Collections;
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
    public static final String MSG_UNKNOWN_VERTEX = "Unknown vertex";
    public static final String MSG_VERTEX_SHOULD_BE_NON_NULL = "Vertex should be not null";
    public static final String MSG_EDGE_SHOULD_BE_NON_NULL = "Edge should be not null";
    public static final String MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL = "Source vertex should be not null";
    public static final String MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL = "Target vertex should be not null";
    public static final String MSG_EDGE_SHOULD_BE_UNIQUE = "Edge should be unique";

    // Map of vertex connections.
    private final Map<V, EdgeContainer<E>> map;

    /**
     * Set of graph edges.
     * Now it is used only for checking global edge uniqueness.
     */
    private final Set<E> setOfEdges;

    /**
     * Default constructor.
     */
    public AbstractGraph() {
        this.map = new HashMap<>();
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

    /**
     * Creates graph specific edge container.
     *
     * @return edge container object.
     */
    protected abstract EdgeContainer<E> createEdgeContainer();

    /**
     * Add edge into graph.
     * Both vertices should exist. If they don't exist this method will create them.
     *
     * @param edge edge to add.
     */
    protected abstract void createLinkBetweenVertices(E edge);

    @Override
    public void addVertex(final V vertex) {
        Objects.requireNonNull(vertex, MSG_VERTEX_SHOULD_BE_NON_NULL);
        map.putIfAbsent(vertex, createEdgeContainer());
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

        final E edge = createEdge(sourceVertex, targetVertex);
        createLinkBetweenVertices(edge);
    }

    @Override
    public void addEdge(E edge) {
        Objects.requireNonNull(edge, MSG_EDGE_SHOULD_BE_NON_NULL);
        if (!containsVertex(edge.getSource())) {
            throw new IllegalArgumentException(MSG_UNKNOWN_SOURCE_VERTEX);
        }

        if (!containsVertex(edge.getTarget())) {
            throw new IllegalArgumentException(MSG_UNKNOWN_TARGET_VERTEX);
        }

        if (!isSelfLoopSupported() && edge.getSource().equals(edge.getTarget())) {
            throw new IllegalArgumentException(MSG_LOOPS_NOT_SUPPORTED);
        }

        createLinkBetweenVertices(edge);
    }

    @Override
    public boolean containsVertex(V vertex) {
        Objects.requireNonNull(vertex, MSG_VERTEX_SHOULD_BE_NON_NULL);
        return map.containsKey(vertex);
    }

    @Override
    public Set<E> getOutgoingEdges(V vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException(MSG_UNKNOWN_VERTEX);
        }
        return Collections.unmodifiableSet(map.get(vertex).getOutgoingEdges());
    }

    @Override
    public Set<E> getIncomingEdges(V vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException(MSG_UNKNOWN_VERTEX);
        }

        return Collections.unmodifiableSet(map.get(vertex).getIncomingEdges());
    }

    @Override
    public boolean containsEdge(E edge) {
        return setOfEdges.contains(edge);
    }

    protected Map<V, EdgeContainer<E>> getMap() {
        return map;
    }

    protected void registerEdge(E edge) {
        setOfEdges.add(edge);
    }
}
