package com.example.usage.model;

/**
 * Edge factory.
 */
public class ColoredEdgeFactory {

    public ColoredEdge<ColoredFigure> getEdgeWithDefaultParams(ColoredFigure sourceVertex, ColoredFigure targetVertex) {
        return new ColoredEdge<ColoredFigure>(sourceVertex, targetVertex);
    }
}
