package com.example.traversal.impl;

import com.example.graph.Edge;
import com.example.graph.Graph;
import com.example.traversal.TraversalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.example.graph.AbstractGraph.MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL;
import static com.example.graph.AbstractGraph.MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL;
import static com.example.graph.AbstractGraph.MSG_UNKNOWN_SOURCE_VERTEX;
import static com.example.graph.AbstractGraph.MSG_UNKNOWN_TARGET_VERTEX;

/**
 * Simple implementation of traversal service.
 * Uses DFS (depth first search) traversal algorithm to find a path between vertices.
 * Works with all graph types, including undirected.
 * !!! Note: path is not optimal.
 *
 * @param <V> vertex type.
 * @param <E> edge type.
 */
public class SimpleDFSTraversalServiceImpl<V, E extends Edge<V>> implements TraversalService<V, E> {
    public static final String MSG_GRAPH_SHOULD_BE_NON_NULL = "Graph should be not null";

    @Override
    public List<E> getPath(final Graph<V, E> graph, final V sourceVertex, final V targetVertex) {
        Objects.requireNonNull(graph, MSG_GRAPH_SHOULD_BE_NON_NULL);
        Objects.requireNonNull(sourceVertex, MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL);
        Objects.requireNonNull(targetVertex, MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL);

        // Check vertices existence.
        if (!graph.containsVertex(sourceVertex)) {
            throw new IllegalArgumentException(MSG_UNKNOWN_SOURCE_VERTEX);
        }

        if (!graph.containsVertex(targetVertex)) {
            throw new IllegalArgumentException(MSG_UNKNOWN_TARGET_VERTEX);
        }

        if (sourceVertex.equals(targetVertex)) {
            return Collections.emptyList();
        }

        // Before traversing check nodes without connections.
        if (graph.getOutgoingEdges(sourceVertex).isEmpty() || graph.getIncomingEdges(targetVertex).isEmpty()) {
            return Collections.emptyList();
        }

        return traverse(graph, sourceVertex, targetVertex);
    }

    /**
     * Simple traverse function to get path from source to target.
     * Uses DFS algorithm.
     * !!! Note: path is not optimal.
     * Does not work with weights.
     *
     * @param graph graph to traverse. Can't be null;
     * @param sourceVertex source vertex to find path. Can't be null;
     * @param targetVertex target vertes to find path. Can't be null;
     * @return list of edges between source and target. Returns empty list if nodes are not connected.
     */
    protected List<E> traverse(final Graph<V, E> graph, final V sourceVertex, final V targetVertex) {
        // Path from start.
        final Deque<E> currentPathFromSource = new LinkedList<>();

        // Set of visited edges.
        final Set<E> visitedEdges = new HashSet<>();

        // Queue of edges to visit.
        final Deque<EdgeLevelInfo<E>> queue = new LinkedList<>();

        // Add to queue outgoing edges from start node.
        getOutgoingPaths(graph, sourceVertex).stream()
                .forEach(e -> queue.addFirst(new EdgeLevelInfo(1, e)));

        // Current edge info.
        EdgeLevelInfo<E> currentEdgeInfo = null;
        int level = 1;
        boolean pathFound = false;
        while (!queue.isEmpty()) {
            // Get edge from queue.
            currentEdgeInfo = queue.pollFirst();

            // Clear path when it is going up.
            if (currentEdgeInfo.getLevel() < level && !currentPathFromSource.isEmpty()) {
                int count = level - currentEdgeInfo.getLevel();
                for (int i = 0; i < count; i++) {
                    currentPathFromSource.removeLast();
                }
            }
            level = currentEdgeInfo.getLevel();

            // Skip visited edges.
            if (!isEdgeVisited(graph.isDirected(), visitedEdges, currentEdgeInfo.getEdge())) {
                // Check target vertex.
                if (currentEdgeInfo.getEdge().getTarget().equals(targetVertex)) {
                    // Remember path.
                    currentPathFromSource.addLast(currentEdgeInfo.getEdge());
                    // Stop processing and return result.
                    pathFound = true;
                    break;
                } else {
                    // Get all neighbors.
                    final Set<E> neighbors = getOutgoingPaths(graph, currentEdgeInfo.getEdge().getTarget());
                    if (!neighbors.isEmpty()) {
                        // Remember path.
                        currentPathFromSource.addLast(currentEdgeInfo.getEdge());
                        // Add edges into the queue.
                        int nexLevel = currentEdgeInfo.getLevel() + 1;
                        neighbors.forEach(e -> queue.addFirst(new EdgeLevelInfo<>(nexLevel, e)));
                    }
                    // Mark edge as visited.
                    visitedEdges.add(currentEdgeInfo.getEdge());
                }
            }
        }

        return pathFound ? new ArrayList<>(currentPathFromSource) : Collections.emptyList();
    }

    /**
     * Checks if edge was visited.
     *
     * @param isDirected true - graph is directed, false - graph is undirected.
     * @param seen - set of visited edges.
     * @param edge edge to check.
     * @return treu - edge was already visited;
     *         false - edge wasn't visited.
     */
    protected boolean isEdgeVisited(boolean isDirected, Set<E> seen, E edge) {
        final boolean directSearchResult = seen.contains(edge);
        if (directSearchResult) {
            return true;
        }

        if (!isDirected) {
            return seen.contains(edge.getOpposite());
        }
        return false;
    }

    /**
     * Get vertex outgoing path.
     * For undirected graphs converts path in to have correct source/target values.
     *
     * @param graph graph to traverse.
     * @param vertex vertex to get outgoing vertices.
     * @return set of outgoing vertices. Can't be null.
     */
    protected Set<E> getOutgoingPaths(Graph<V, E> graph, V vertex) {
        final Set<E> edges = graph.getOutgoingEdges(vertex);
        if (graph.isDirected()) {
            return edges;
        }
        // Transform edges for undirected graph.
        final Set<E> processedEdges = new HashSet<>();
        edges.forEach(e -> {
            if (vertex.equals(e.getSource())) {
                processedEdges.add(e);
            } else {
                processedEdges.add((E) e.getOpposite());
            }
        });
        return processedEdges;
    }

    /**
     * Inner class for storing info about path level.
     *
     * @param <E> edge class.
     */
    private final class EdgeLevelInfo<E> {
        // It is used because of checkstyle [MagicNumber] error.
        private static final int CONSTANT_FOR_HASH_CODE = 31;

        private final int level;
        private final E edge;

        private EdgeLevelInfo(int level, E edge) {
            Objects.requireNonNull(edge, "Edge should not be null");
            this.level = level;
            this.edge = edge;
        }

        public int getLevel() {
            return level;
        }

        public E getEdge() {
            return edge;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }

            EdgeLevelInfo<?> pair = (EdgeLevelInfo<?>) o;

            if (level != pair.level) {
                return false;
            }
            return edge.equals(pair.edge);
        }

        @Override
        public int hashCode() {
            int result = level;
            result = CONSTANT_FOR_HASH_CODE * result + edge.hashCode();
            return result;
        }
    }

}
