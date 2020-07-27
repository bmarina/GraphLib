package com.example.ussage;

import com.example.traversal.TraversalService;
import com.example.traversal.impl.SimpleDFSTraversalServiceImpl;
import com.example.ussage.model.Color;
import com.example.ussage.model.ColoredEdge;
import com.example.ussage.model.ColoredFigure;
import com.example.ussage.model.CustomColoredGraphImpl;
import com.example.ussage.model.Shape;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GeometricalFiguresTest {

    private final Random random = new Random();
    private final TraversalService<ColoredFigure, ColoredEdge<ColoredFigure>> traversalService = new SimpleDFSTraversalServiceImpl<>();

    @Test
    public void buildRandomGraphAndFindPath() {
        int countOfNodes = 10000;
        int countOfEdges = 10000;
        CustomColoredGraphImpl graph = new CustomColoredGraphImpl();
        List<ColoredFigure> listOfNodes = generateNodes(countOfNodes);
        // Add nodes to graph.
        listOfNodes.stream().forEach(graph::addVertex);
        // Generate edges.
        Set<ColoredEdge<ColoredFigure>> listOfEdges = generateEdges(graph, listOfNodes, countOfEdges);
        // Add edges.
        listOfEdges.stream().forEach(graph::addEdge);

        // Try to find path between random nodes.
        ColoredFigure source = getRandomNode(listOfNodes);
        ColoredFigure target = getRandomNode(listOfNodes);
        final List<ColoredEdge<ColoredFigure>> path = traversalService.getPath(graph, source, target);
        System.out.println("Source: " + source);
        System.out.println("Target: " + target);
        System.out.println("Path: " + printPath(path));
    }

    private String printPath(List<ColoredEdge<ColoredFigure>> path) {
        return path.stream()
                .map(ColoredEdge::toString)
                .collect(Collectors.joining("\n"));
    }

    private ColoredFigure getRandomNode(List<ColoredFigure> listOfNodes) {
        return listOfNodes.get(random.nextInt(listOfNodes.size()));
    }

    private List<ColoredFigure> generateNodes(int countOf) {
        final List<ColoredFigure> result = new ArrayList<>(countOf);
        for (int i = 0; i < countOf; i++) {
            result.add(new ColoredFigure(
                    i,
                    getRandomColor(),
                    getRandomShape()
            ));
        }
        return result;
    }

    private Shape getRandomShape() {
        final int max = Shape.values().length;
        return Shape.values()[random.nextInt(max)];
    }

    private Color getRandomColor() {
        final int max = Color.values().length;
        return Color.values()[random.nextInt(max)];
    }

    private Set<ColoredEdge<ColoredFigure>> generateEdges(final CustomColoredGraphImpl graph,
                                                          final List<ColoredFigure> listOfNodes,
                                                          final int count) {
        final Set<ColoredEdge<ColoredFigure>> result = new HashSet<>();

        int i = 0;
        while (result.size() < count) {
            // For safety.
            if (i++ > 10000000) {
                break;
            }
            ColoredFigure source = getRandomNode(listOfNodes);
            ColoredFigure target = getRandomNode(listOfNodes);
            if (source.equals(target)) {
                continue;
            }

            ColoredEdge<ColoredFigure> newEdge = new ColoredEdge<ColoredFigure>(
                    source,
                    target,
                    Long.valueOf(random.nextInt(1000)),
                    getRandomColor()
            );

            if (!graph.containsEdge(newEdge)) {
                result.add(newEdge);
            }
        }
        return result;
    }
}
