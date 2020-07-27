package com.example.traversal;

import com.example.graph.Edge;
import com.example.graph.Graph;

import java.util.List;

/**
 * Graph traversal service.
 *
 * @param <V> vertex type.
 * @param <E> edge type.
 */
public interface TraversalService<V, E extends Edge<V>> {
    /**
     * Returns list of edges between source and target vertex.
     *
     * @param sourceVertex source vertex to find path. Can't be null.
     * @param targetVertex target vertex to find path. Can't be nul.
     * @return List of edges if path exists.
     *         If there is no path between provided vertices, than empty list will be returned.
     * @throws NullPointerException if any of the specified vertices or graph is null.
     * @throws IllegalArgumentException if provided vertices do not exists in graph.
     */
    List<E> getPath(Graph<V, E> graph, V sourceVertex, V targetVertex);
}
