package com.example.ussage.model;

/**
 * Example of custom node.
 */
public final class ColoredFigure {
    private final long id;
    private final Color color;
    private final Shape shape;

    public ColoredFigure(long id, Color color, Shape shape) {
        this.id = id;
        this.color = color;
        this.shape = shape;
    }

    public long getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColoredFigure figure = (ColoredFigure) o;

        if (id != figure.id) return false;
        if (color != figure.color) return false;
        return shape == figure.shape;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + color.hashCode();
        result = 31 * result + shape.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                 + id +
                "," + color +
                "," + shape +
                '}';
    }
}
