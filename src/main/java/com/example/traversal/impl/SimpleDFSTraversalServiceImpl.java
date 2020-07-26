package com.example.traversal.impl;

import com.example.graph.Edge;
import com.example.graph.Graph;
import com.example.traversal.TraversalService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import static com.example.graph.AbstractGraph.MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL;
import static com.example.graph.AbstractGraph.MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL;

/**
 * Simple implementation of traversal service.
 * Uses DFS (depth first search) traversal algorithm to find a path between vertices.
 * Works with all graph types, including undirected.
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

        if (sourceVertex.equals(targetVertex)) {
            return Collections.emptyList();
        }

        return traverse(graph, sourceVertex, targetVertex);
    }

    /**
     * Simple traverse function to get path from source to target.
     * Uses DFS algorithm. Path is not optimal.
     *
     * @param graph graph to traverse. Can't be null;
     * @param sourceVertex source vertex to find path. Can't be null;
     * @param targetVertex target vertes to find path. Can't be null;
     * @return list of edges between source and target.
     */
    protected List<E> traverse(final Graph<V, E> graph, final V sourceVertex, final V targetVertex) {
        // Path from start.
        final Stack<E> result = new Stack<>();

        // Set of seen edges.
        final Set<E> seen = new HashSet<>();
        // Queue of edges to visit.
        final Deque<EdgeLevelInfo<E>> queue = new ArrayDeque<>();

        // Add to queue outgoing edges from start node.
        graph.getOutgoingEdges(sourceVertex).stream()
                .forEach(e -> queue.addFirst(new EdgeLevelInfo(1, e)));

        // Current edge info.
        EdgeLevelInfo<E> currentEdgeInfo = null;
        int level = 1;
        while (!queue.isEmpty()) {
            currentEdgeInfo = queue.pollFirst();
            if (currentEdgeInfo.getLevel() < level && !result.isEmpty()) {
                int count = level - currentEdgeInfo.getLevel();
                for (int i = 0; i < count; i++) {
                    result.pop();
                }
            }
            level = currentEdgeInfo.getLevel();
            // Remember path.
            result.push(currentEdgeInfo.getEdge());
            if (currentEdgeInfo.getEdge().getTarget().equals(targetVertex)) {
                // Stop processing.
                break;
            } else if (seen.contains(currentEdgeInfo.getEdge())) {
                // Remove from path
                result.pop();
            } else {
                // Get all neighbors and add to the begging.
                Collection<E> neighbors = getNeighbors(graph, currentEdgeInfo.getEdge().getTarget());
                if (neighbors.isEmpty()) {
                    // Remove from path
                    result.pop();
                } else {
                    int nexLevel = currentEdgeInfo.getLevel() + 1;
                    neighbors.forEach(e -> queue.addFirst(new EdgeLevelInfo<>(nexLevel, e)));
                }
                // Mark edge as visited.
                seen.add(currentEdgeInfo.getEdge());
            }
        }
        return new ArrayList<>(result);
    }

    protected Collection<E> getNeighbors(Graph<V, E> graph, V vertex) {
        return graph.getOutgoingEdges(vertex);
    }

    /**
     * Inner class for storing info about path level.
     *
     * @param <E> edge class.
     */
    private final class EdgeLevelInfo<E> {
        private final int level;
        private final E edge;

        public EdgeLevelInfo(int level, E edge) {
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EdgeLevelInfo<?> pair = (EdgeLevelInfo<?>) o;

            if (level != pair.level) return false;
            return edge.equals(pair.edge);
        }

        @Override
        public int hashCode() {
            int result = level;
            result = 31 * result + edge.hashCode();
            return result;
        }
    }

}
