package com.example.usage.model;

import com.example.graph.AbstractUndirectedGraph;

/**
 * Implementation of undirected graph with custom nodes and edges.
 */
public class CustomColoredGraphImpl extends AbstractUndirectedGraph<ColoredFigure, ColoredEdge<ColoredFigure>> {
    // Factory to create edges.
    private final ColoredEdgeFactory coloredEdgeFactory = new ColoredEdgeFactory();

    public CustomColoredGraphImpl() {
        super();
    }

    @Override
    protected ColoredEdge<ColoredFigure> createEdge(final ColoredFigure sourceVertex, final ColoredFigure targetVertex) {
        return coloredEdgeFactory.getEdgeWithDefaultParams(sourceVertex, targetVertex);
    }
}
