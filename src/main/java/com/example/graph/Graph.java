package com.example.graph;

import java.util.Set;

/**
 * Base graph interface.
 *
 * @param <V> vertex type. Should be immutable.
 * @param <E> edge type. Should be immutable.
 */
public interface Graph<V, E extends Edge<V>> {
    /**
     * Check if graph is directed.
     *
     * @return true - graph is directed;
     *         false - graph is undirected.
     */
    boolean isDirected();

    /**
     * Check if graph supports self loops.
     * Self loop is kind of edge where source and target vertex is the same.
     *
     * @return true - self loops are supported;
     *         false - self loops are not supported.
     */
    boolean isSelfLoopSupported();

    /**
     * Add vertex to graph if it does not exists.
     *
     * @param vertex vertex to be added to this graph.
     * @throws NullPointerException if the specified vertex is null.
     */
    void addVertex(V vertex);

    /**
     * Check if vertex exists in graph.
     *
     * @param vertex vertex to find.
     * @return true - vertex exists in graph;
     *         false - vertex does not exist in graph.
     */
    boolean containsVertex(V vertex);

    /**
     * Creates a new edge in this graph, going from the source vertex to the target vertex.
     * Allows to create multiple DIFFERENT edges between nodes.
     * For undirected graphs edges with same properties except source and target are considered as same.
     *
     * @param sourceVertex source vertex of the edge.
     * @param targetVertex target vertex of the edge.
     * @throws IllegalArgumentException - if source or target vertices are not found in the graph;
     *         - if such vertex already exists in graph;
     *         - if source and target are the same, but self loops are not allowed.
     * @throws NullPointerException if any of the specified vertices is null.
     */
    void addEdge(V sourceVertex, V targetVertex);

    /**
     * Check if edge exists in graph.
     * For undirected graphs edges with same properties except source and target are considered as same.
     *
     * @param edge edge to find.
     * @return true - edge exists in graph;
     *         false - edge does not exist in graph.
     */
    boolean containsEdge(E edge);

    /**
     * Returns unmodifiable set of vertex outgoing edges.
     *
     * @param vertex graph vertex. Can't be null;
     * @return set of outgoing edges.
     * @throws IllegalArgumentException if vertex not found in the graph;
     * @throws NullPointerException if vertex is null;
     */
    Set<E> getOutgoingEdges(V vertex);

    /**
     * Returns unmodifiable set of vertex incoming edges.
     *
     * @param vertex graph vertex. Can't be null;
     * @return set of outgoing edges.
     * @throws IllegalArgumentException if vertex not found in the graph;
     * @throws NullPointerException if vertex is null;
     */
    Set<E> getIncomingEdges(V vertex);
}
