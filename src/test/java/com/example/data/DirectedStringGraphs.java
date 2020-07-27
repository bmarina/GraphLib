package com.example.data;

import com.example.graph.impl.SimpleDirectedGraphImpl;

/**
 * Directed graphs for tests.
 * Vertex type is String.
 * For Edge type is used DefaultEdge class.
 */
public class DirectedStringGraphs {

    public static final SimpleDirectedGraphImpl<String> ONE_VERTEX = new SimpleDirectedGraphImpl<String>() {{
        addVertex("A");
    }};

    public static final SimpleDirectedGraphImpl<String> TWO_VERTEX_NOT_CONNECTED = new SimpleDirectedGraphImpl<String>() {{
        addVertex("A");
        addVertex("B");
    }};

    /**
     * A -> B
     */
    public static final SimpleDirectedGraphImpl<String> TWO_VERTEX_CONNECTED = new SimpleDirectedGraphImpl<String>() {{
        addVertex("A");
        addVertex("B");
        addEdge("A", "B");
    }};

    //@formatter:off
    /**
     *
     *  A -> B -> C -> D -> E
     *  <-------------------|
     *
     */
    //@formatter:on
    public static final SimpleDirectedGraphImpl<String> LOOP = new SimpleDirectedGraphImpl<String>() {{
        addVertex("A");
        addVertex("B");
        addVertex("C");
        addVertex("D");
        addVertex("E");
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("C", "D");
        addEdge("D", "E");
        addEdge("E", "A");
    }};

    //@formatter:off
    /**
     *
     *            C
     *         >     >
     * A ->  B -> O  ->  E  ->  F
     *         >     >
     *            D
     *
     */
    //@formatter:on
    public static final SimpleDirectedGraphImpl<String> GRAPH_WITH_THREE_SAME_PATHS = new SimpleDirectedGraphImpl<String>() {{
        addVertex("A");
        addVertex("B");
        addVertex("C");
        addVertex("D");
        addVertex("E");
        addVertex("F");
        addVertex("O");
        addVertex("Single");
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("B", "O");
        addEdge("O", "E");
        addEdge("B", "D");
        addEdge("C", "E");
        addEdge("D", "E");
        addEdge("E", "F");
    }};

    //@formatter:off
    /**
     *
     *             C  -->  D
     *          >             >
     * A ->  B ----------------> E  ->  F
     *          >             >
     *            G -> H -> I
     *
     */
    //@formatter:on
    public static final SimpleDirectedGraphImpl<String> GRAPH_WITH_THREE_DIFFERENT_PATHS = new SimpleDirectedGraphImpl<String>() {{
        addVertex("A");
        addVertex("B");
        addVertex("C");
        addVertex("D");
        addVertex("E");
        addVertex("F");
        addVertex("G");
        addVertex("H");
        addVertex("I");
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("B", "E");
        addEdge("B", "G");
        addEdge("C", "D");
        addEdge("D", "E");
        addEdge("E", "F");
        addEdge("G", "H");
        addEdge("H", "I");
        addEdge("I", "E");
    }};

    //@formatter:off
    /**
     *     >A<           >D
     *   /     \       /    >
     *  B ----> C     E <--> F
     */
    //@formatter:on
    public static final SimpleDirectedGraphImpl<String> NOT_CONNECTED = new SimpleDirectedGraphImpl<String>() {{
        addVertex("A");
        addVertex("B");
        addVertex("C");
        addVertex("D");
        addVertex("E");
        addVertex("F");
        addEdge("B", "A");
        addEdge("B", "C");
        addEdge("C", "A");

        addEdge("E", "D");
        addEdge("E", "F");
        addEdge("F", "E");
        addEdge("D", "F");
    }};
}
