package com.example.graph.impl;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.example.graph.AbstractGraph.MSG_EDGE_SHOULD_BE_UNIQUE;
import static com.example.graph.AbstractGraph.MSG_LOOPS_NOT_SUPPORTED;
import static com.example.graph.AbstractGraph.MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL;
import static com.example.graph.AbstractGraph.MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL;
import static com.example.graph.AbstractGraph.MSG_UNKNOWN_SOURCE_VERTEX;
import static com.example.graph.AbstractGraph.MSG_UNKNOWN_TARGET_VERTEX;
import static com.example.graph.AbstractGraph.MSG_VERTEX_SHOULD_BE_NON_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test base graph operations on SimpleUndirectedGraphImpl.
 * Node type: Integer.
 */
public class SimpleUndirectedGraphImplTest {

    @Test
    public void checkPropertiesTest() {
        SimpleUndirectedGraphImpl<String> graph = new SimpleUndirectedGraphImpl<String>();
        assertFalse(graph.isDirected(), "Graph should be undirected");
        assertFalse(graph.isSelfLoopSupported(), "Graph does not support self loops");
    }

    //@formatter:off
    /**
     * Build graph and perform checks.
     *
     *  1 -- 2
     *  |  \ |
     *  3 -- 4
     *
     */
    //@formatter:on
    @Test
    public void buildGraphTest() {
        final SimpleUndirectedGraphImpl<Integer> graph = new SimpleUndirectedGraphImpl<Integer>();

        List<Integer> nodesToAdd = Arrays.asList(1, 2, 3, 4);
        nodesToAdd.forEach(graph::addVertex);
        nodesToAdd.forEach(vertex -> {
            assertTrue(graph.containsVertex(vertex), "Graph should contain vertex");
        });

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        final String edgeNotExistsMsg = "Graph should contain edge";
        // Check both types because graph is undirected.
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(1, 2)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(2, 1)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(1, 3)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(3, 1)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(1, 4)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(4, 1)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(2, 4)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(4, 2)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(3, 4)), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<Integer>(4, 3)), edgeNotExistsMsg);
    }

    @Test
    public void addSelfLoopTest() {
        SimpleUndirectedGraphImpl<String> graph = new SimpleUndirectedGraphImpl<String>();
        graph.addVertex("A");
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge("A", "A"),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_LOOPS_NOT_SUPPORTED, thrown.getLocalizedMessage());
    }

    @Test
    public void addEdgeForNonExistingVertexTest() {
        SimpleUndirectedGraphImpl<String> graph = new SimpleUndirectedGraphImpl<String>();
        final IllegalArgumentException thrownOnSource = assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge("A", "B"),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_UNKNOWN_SOURCE_VERTEX, thrownOnSource.getLocalizedMessage());

        graph.addVertex("A");
        final IllegalArgumentException thrownOnTarget = assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge("A", "B"),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_UNKNOWN_TARGET_VERTEX, thrownOnTarget.getLocalizedMessage());
    }

    @Test
    public void addVertexNullTest() {
        SimpleUndirectedGraphImpl<String> graph = new SimpleUndirectedGraphImpl<String>();
        final NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> graph.addVertex(null),
                "Expected NullPointerException to throw, but it didn't"
        );
        assertEquals(MSG_VERTEX_SHOULD_BE_NON_NULL, thrown.getLocalizedMessage());
    }

    @Test
    public void addEdgeNullTest() {
        SimpleUndirectedGraphImpl<String> graph = new SimpleUndirectedGraphImpl<String>();
        final NullPointerException thrownOnSource = assertThrows(
                NullPointerException.class,
                () -> graph.addEdge(null, "A"),
                "Expected NullPointerException to throw, but it didn't"
        );
        assertEquals(MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL, thrownOnSource.getLocalizedMessage());
        graph.addVertex("A");

        final NullPointerException thrownOnTarget = assertThrows(
                NullPointerException.class,
                () -> graph.addEdge("A", null),
                "Expected NullPointerException to throw, but it didn't"
        );
        assertEquals(MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL, thrownOnTarget.getLocalizedMessage());
    }

    @Test
    public void addEdgeUniqueTest() {
        SimpleUndirectedGraphImpl<Integer> graph = new SimpleUndirectedGraphImpl<Integer>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        // Add one more time and expect exception.
        final IllegalArgumentException thrownFirst= assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(1, 2),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_EDGE_SHOULD_BE_UNIQUE, thrownFirst.getLocalizedMessage());


        // Check inverted type and expect exception.
        final IllegalArgumentException thrownSecond = assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(2, 1),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_EDGE_SHOULD_BE_UNIQUE, thrownSecond.getLocalizedMessage());
    }
}
