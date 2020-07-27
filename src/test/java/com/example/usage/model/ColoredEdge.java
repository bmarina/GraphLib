package com.example.usage.model;

import com.example.graph.Edge;

import java.util.Objects;

/**
 * Example of custom edge.
 *
 * @param <V> type of nodes.
 */
public class ColoredEdge<V> implements Edge<V> {
    private static final long DEFAULT_LENGTH = 0L;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    private final V source;
    private final V target;
    private final long length;
    private final Color color;

    public ColoredEdge(final V source, final V target) {
        this(source, target, DEFAULT_LENGTH, DEFAULT_COLOR);
    }

    public ColoredEdge(final V source,
                       final V target,
                       final long length,
                       final Color color) {
        Objects.requireNonNull(source, "Source can't be null");
        Objects.requireNonNull(target, "Target can't be null");

        this.source = source;
        this.target = target;
        this.length = length;
        this.color = color;
    }

    @Override
    public V getSource() {
        return this.source;
    }

    @Override
    public V getTarget() {
        return this.target;
    }

    @Override
    public Edge<V> getOpposite() {
        return new ColoredEdge<V>(getTarget(), getSource(), this.length, this.color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColoredEdge<?> that = (ColoredEdge<?>) o;

        if (length != that.length) return false;
        if (!source.equals(that.source)) return false;
        if (!target.equals(that.target)) return false;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + target.hashCode();
        result = 31 * result + (int) (length ^ (length >>> 32));
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                source +
                "\t\t--<" + length +
                "," + color +
                ">--\t\t" +
                target +
                ')';
    }
}
