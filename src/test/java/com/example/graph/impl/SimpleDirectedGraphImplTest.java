package com.example.graph.impl;

import com.example.graph.impl.DefaultEdge;
import com.example.graph.impl.SimpleDirectedGraphImpl;
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
 * Test base graph operations on SimpleDirectedGraphImpl.
 * Node type: String.
 */
public class SimpleDirectedGraphImplTest {

    @Test
    public void checkPropertiesTest() {
        SimpleDirectedGraphImpl<String> graph = new SimpleDirectedGraphImpl<String>();
        assertTrue(graph.isDirected(), "Graph should be directed");
        assertFalse(graph.isSelfLoopSupported(), "Graph does not support self loops");
    }

    //@formatter:off
    /**
     * Build graph and perform checks.
     *
     *            C
     *         >     >
     * A ->  B    ->   E  ->  F
     *         >     >
     *            D
     *
     */
    //@formatter:on
    @Test
    public void buildGraphTest() {
        final SimpleDirectedGraphImpl<String> graph = new SimpleDirectedGraphImpl<String>();

        List<String> nodesToAdd = Arrays.asList("A", "B", "C", "D", "E", "F");
        nodesToAdd.forEach(graph::addVertex);
        nodesToAdd.forEach(vertex -> {
            assertTrue(graph.containsVertex(vertex), "Graph should contain vertex");
        });

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("B", "E");
        graph.addEdge("B", "D");
        graph.addEdge("C", "E");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");

        final String edgeNotExistsMsg = "Graph should contain edge";
        assertTrue(graph.containsEdge(new DefaultEdge<>("A", "B")), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<>("B", "C")), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<>("B", "D")), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<>("B", "E")), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<>("C", "E")), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<>("D", "E")), edgeNotExistsMsg);
        assertTrue(graph.containsEdge(new DefaultEdge<>("E", "F")), edgeNotExistsMsg);
    }

    @Test
    public void addSelfLoopTest() {
        SimpleDirectedGraphImpl<String> graph = new SimpleDirectedGraphImpl<String>();
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
        SimpleDirectedGraphImpl<String> graph = new SimpleDirectedGraphImpl<String>();
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
        SimpleDirectedGraphImpl<String> graph = new SimpleDirectedGraphImpl<String>();
        final NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> graph.addVertex(null),
                "Expected NullPointerException to throw, but it didn't"
        );
        assertEquals(MSG_VERTEX_SHOULD_BE_NON_NULL, thrown.getLocalizedMessage());
    }

    @Test
    public void addEdgeNullTest() {
        SimpleDirectedGraphImpl<String> graph = new SimpleDirectedGraphImpl<String>();
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
        SimpleDirectedGraphImpl<String> graph = new SimpleDirectedGraphImpl<String>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        // Add one more time and expect exception.
        final IllegalArgumentException thrownOnTarget = assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge("A", "B"),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_EDGE_SHOULD_BE_UNIQUE, thrownOnTarget.getLocalizedMessage());
    }
}
