package com.example.graph.impl;

import com.example.graph.Edge;

import java.util.Objects;

/**
 * Default edge implementation.
 * Edge does not contain any useful information except basic info about source and target.
 * Should be immutable.
 *
 * @param <V> vertex class.
 */
public class DefaultEdge<V> implements Edge<V> {
    // It is used because of checkstyle [MagicNumber] error.
    private static final int CONSTANT_FOR_HASH_CODE = 31;

    private final V source;
    private final V target;

    /**
     * Default constructor.
     *
     * @param source source vertex; Can't be null.
     * @param target target vertex; Can't be null.
     * @throws NullPointerException when source or target are null.
     */
    public DefaultEdge(final V source, final V target) {
        Objects.requireNonNull(source, "Edge source vertex can't be null");
        Objects.requireNonNull(target, "Edge target vertex can't be null");

        this.source = source;
        this.target = target;
    }

    @Override
    public V getSource() {
        return source;
    }

    @Override
    public V getTarget() {
        return target;
    }

    @Override
    public DefaultEdge<V> getOpposite() {
        return new DefaultEdge<V>(target, source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultEdge<?> that = (DefaultEdge<?>) o;

        if (!source.equals(that.source)) {
            return false;
        }
        return target.equals(that.target);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = CONSTANT_FOR_HASH_CODE * result + target.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DefaultEdge{"
                + "source=" + source
                + ", target=" + target
                + '}';
    }
}
