package com.example.graph;

/**
 * Weighted Edge interface.
 *
 * @param <V> type of vertex.
 */
public interface WeightedEdge<V> extends Edge<V> {
    /**
     * Default edge weight constant.
     */
    double DEFAULT_WEIGHT = 0.0d;

    /**
     * Get edge weight.
     *
     * @return edge weight.
     */
    double getWeight();
}
