package com.example.graph;

import java.util.Set;

/**
 * Base graph interface.
 *
 * @param <V> vertex type.
 * @param <E> edge type.
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
     * Adds vertex to graph.
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
     * Creates a new edge in this graph, going from the source vertex to the target vertex, and
     * returns the created edge. Some graphs do not allow edge-multiplicity. In such cases, if the
     * graph already contains an edge from the specified source to the specified target, than this
     * method does not change the graph and returns <code>null</code>.
     *
     * @param sourceVertex source vertex of the edge.
     * @param targetVertex target vertex of the edge.
     * @return The newly created edge if added to the graph or null if edge already exists.
     * @throws IllegalArgumentException if source or target vertices are not found in the graph.
     * @throws NullPointerException if any of the specified vertices is <code>null</code>.
     */
    void addEdge(V sourceVertex, V targetVertex);

    /**
     * Check if edge exists in graph.
     *
     * @param edge edge to find.
     * @return true - edge exists in graph;
     *         false - edge does not exist in graph.
     */
    boolean containsEdge(E edge);

    Set<E> getOutgoingEdges(V vertex);

    Set<E> getIncomingEdges(V vertex);
}
