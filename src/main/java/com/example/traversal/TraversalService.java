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
@FunctionalInterface
public interface TraversalService<V, E extends Edge<V>> {
    /**
     * Returns list of edges between source and target vertex.
     *
     * @param sourceVertex source vertex to find path.
     * @param targetVertex target vertex to find path.
     * @return list of edges if path exists.
     *         If there is no path between provided edges, than empty list will be returned.
     * @throws NullPointerException if any of the specified vertices is null.
     */
    List<E> getPath(Graph<V, E> graph, V sourceVertex, V targetVertex);
}
