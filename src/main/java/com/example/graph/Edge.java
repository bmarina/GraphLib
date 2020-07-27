package com.example.graph;

/**
 * Base edge interface.
 *
 * @param <V> vertex type.
 */
public interface Edge<V> {
    /**
     * Get source vertex.
     *
     * @return source vertex. Can't be null.
     */
    V getSource();

    /**
     * Get target vertex.
     *
     * @return target vertex. Can't be null.
     */
    V getTarget();

    /**
     * Crete opposite directed vertex.
     *
     * @return opposite vertex.
     */
    Edge<V> getOpposite();
}
