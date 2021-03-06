package com.example.data;

import com.example.graph.impl.SimpleUndirectedGraphImpl;

/**
 * Undirected graphs for tests.
 * Vertex type is Integer.
 * For Edge type is used DefaultEdge class.
 */
public class UndirectedStringGraphs {

    public static final SimpleUndirectedGraphImpl<Integer> ONE_VERTEX = new SimpleUndirectedGraphImpl<Integer>() {{
        addVertex(1);
    }};

    public static final SimpleUndirectedGraphImpl<Integer> TWO_VERTEX_NOT_CONNECTED = new SimpleUndirectedGraphImpl<Integer>() {{
        addVertex(1);
        addVertex(2);
    }};

    /**
     * 1 - 2
     */
    public static final SimpleUndirectedGraphImpl<Integer> TWO_VERTEX_CONNECTED = new SimpleUndirectedGraphImpl<Integer>() {{
        addVertex(1);
        addVertex(2);
        addEdge(1, 2);
    }};

    //@formatter:off
    /**
     *
     *  1 - 2 - 3 - 4 - 5
     *  |---------------|
     *
     */
    //@formatter:on
    public static final SimpleUndirectedGraphImpl<Integer> LOOP = new SimpleUndirectedGraphImpl<Integer>() {{
        addVertex(1);
        addVertex(2);
        addVertex(3);
        addVertex(4);
        addVertex(5);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(3, 4);
        addEdge(4, 5);
        addEdge(5, 1);
    }};

    //@formatter:off
    /**
     *
     *            4
     *          /   \
     * 1 - 2 - 3  -  7 - 8
     *          \   /
     *            5
     *
     */
    //@formatter:on
    public static final SimpleUndirectedGraphImpl<Integer> GRAPH_WITH_THREE_SAME_PATHS = new SimpleUndirectedGraphImpl<Integer>() {{
        addVertex(1);
        addVertex(2);
        addVertex(3);
        addVertex(4);
        addVertex(5);
        addVertex(6);
        addVertex(7);
        addVertex(8);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(3, 4);
        addEdge(3, 7);
        addEdge(3, 5);
        addEdge(4, 7);
        addEdge(5, 7);
        addEdge(7, 8);
    }};

    //@formatter:off
    /**
     *     1           5
     *   /   \       /   \
     *  2 --- 3     4 --- 6
     */
    //@formatter:on
    public static final SimpleUndirectedGraphImpl<Integer> NOT_CONNECTED = new SimpleUndirectedGraphImpl<Integer>() {{
        addVertex(1);
        addVertex(2);
        addVertex(3);
        addVertex(4);
        addVertex(5);
        addVertex(6);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(3, 1);

        addEdge(4, 5);
        addEdge(6, 5);
        addEdge(4, 6);
    }};
}
