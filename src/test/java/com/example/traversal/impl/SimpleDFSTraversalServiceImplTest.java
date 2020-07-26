package com.example.traversal.impl;

import com.example.data.DirectedStringGraphs;
import com.example.data.UndirectedStringGraphs;
import com.example.graph.Graph;
import com.example.graph.impl.DefaultEdge;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SimpleDFSTraversalServiceImplTest {

    private SimpleDFSTraversalServiceImpl<String, DefaultEdge<String>> serviceForTestingStringDirectedGraph = new SimpleDFSTraversalServiceImpl<>();
    private SimpleDFSTraversalServiceImpl<Integer, DefaultEdge<Integer>> serviceForTestingIntegerUndirectedGraph = new SimpleDFSTraversalServiceImpl<>();

    @ParameterizedTest
    @MethodSource("dataProviderDirectedGraphs")
    public void directedGraphGetPathTest(final String caseName,
                                         final Graph<String, DefaultEdge<String>> graph,
                                         final String source,
                                         final String target,
                                         final List<List<DefaultEdge<String>>> correctPaths) {
        final List<DefaultEdge<String>> traversalResult = serviceForTestingStringDirectedGraph.getPath(graph, source, target);
        assertNotNull(traversalResult, "Result can't be null");
        if (correctPaths.isEmpty()) {
            assertTrue(traversalResult.isEmpty(), "Empty result expected");
        } else {
            if (!correctPaths.contains(traversalResult)) {
                System.out.println("Actual result:\n" + traversalResult);
                fail("Not found one of possible paths: \n" + correctPaths);
            }
        }
    }

    @ParameterizedTest
    @MethodSource("dataProviderUndirectedGraphs")
    public void undirectedGraphGetPathTest(final String caseName,
                                           final Graph<Integer, DefaultEdge<Integer>> graph,
                                           final Integer source,
                                           final Integer target,
                                           final List<List<DefaultEdge<Integer>>> correctPaths) {
        final List<DefaultEdge<Integer>> traversalResult = serviceForTestingIntegerUndirectedGraph.getPath(graph, source, target);
        assertNotNull(traversalResult, "Result can't be null");
        if (correctPaths.isEmpty()) {
            assertTrue(traversalResult.isEmpty(), "Empty result expected");
        } else {
            if (!correctPaths.contains(traversalResult)) {
                System.out.println("Actual result:\n" + traversalResult);
                fail("Not found one of possible paths: \n" + correctPaths);
            }
        }
    }

    /**
     * Data provider for String directed graph testing.
     */
    private static Stream<Arguments> dataProviderUndirectedGraphs() {
        return Stream.of(
                Arguments.of(
                        "1",
                        UndirectedStringGraphs.ONE_VERTEX,
                        1,
                        1,
                        Collections.emptyList()
                ),
                Arguments.of(
                        "2",
                        UndirectedStringGraphs.TWO_VERTEX_NOT_CONNECTED,
                        1,
                        2,
                        Collections.emptyList()
                ),
                Arguments.of(
                        "3",
                        UndirectedStringGraphs.TWO_VERTEX_CONNECTED,
                        1,
                        2,
                        Collections.singletonList(Collections.singletonList(new DefaultEdge<>(1, 2)))
                ),
                Arguments.of(
                        "4",
                        UndirectedStringGraphs.TWO_VERTEX_CONNECTED,
                        2,
                        1,
                        Collections.singletonList(Collections.singletonList(new DefaultEdge<>(1, 2)))
                ),
                Arguments.of(
                        "5",
                        UndirectedStringGraphs.LOOP,
                        1,
                        5,
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>(1, 2),
                                        new DefaultEdge<>(2, 3),
                                        new DefaultEdge<>(3, 4),
                                        new DefaultEdge<>(4, 5)
                                )
                        )
                ),
                Arguments.of(
                        "51",
                        UndirectedStringGraphs.LOOP,
                        1,
                        3,
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>(1, 2),
                                        new DefaultEdge<>(3, 3)
                                )
                        )
                ),
                Arguments.of(
                        "6",
                        UndirectedStringGraphs.LOOP,
                        3,
                        1,
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>(3, 4),
                                        new DefaultEdge<>(4, 5),
                                        new DefaultEdge<>(5, 1)
                                )
                        )
                ),
                Arguments.of(
                        "7",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        1,
                        3,
                        Arrays.asList(
                                /*Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "D"),
                                        new DefaultEdge<>("D", "E"),
                                        new DefaultEdge<>("E", "C")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "O"),
                                        new DefaultEdge<>("O", "E"),
                                        new DefaultEdge<>("E", "C")
                                )*/
                        )
                ),
                Arguments.of(
                        "8",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        1,
                        5,
                        Arrays.asList(
                               /* Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "D"),
                                        new DefaultEdge<>("D", "E")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "O"),
                                        new DefaultEdge<>("O", "E")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C"),
                                        new DefaultEdge<>("C", "E")
                                )*/
                        )
                ),
                Arguments.of(
                        "9",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        1,
                        6,
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "D"),
                                        new DefaultEdge<>("D", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "O"),
                                        new DefaultEdge<>("O", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C"),
                                        new DefaultEdge<>("C", "E"),
                                        new DefaultEdge<>("E", "F")
                                )
                        )
                ),
                Arguments.of(
                        "10",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_DIFFERENT_PATHS,
                        1,
                        6,
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "G"),
                                        new DefaultEdge<>("G", "H"),
                                        new DefaultEdge<>("H", "I"),
                                        new DefaultEdge<>("I", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C"),
                                        new DefaultEdge<>("C", "D"),
                                        new DefaultEdge<>("D", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "E"),
                                        new DefaultEdge<>("E", "F")
                                )
                        )
                )
        );
    }

    /**
     * Data provider for Integer undirected graph testing.
     */
    private static Stream<Arguments> dataProviderDirectedGraphs() {
        return Stream.of(
                Arguments.of(
                        "1",
                        DirectedStringGraphs.ONE_VERTEX,
                        "A",
                        "A",
                        Collections.emptyList()
                ),
                Arguments.of(
                        "2",
                        DirectedStringGraphs.TWO_VERTEX_NOT_CONNECTED,
                        "A",
                        "B",
                        Collections.emptyList()
                ),
                Arguments.of(
                        "3",
                        DirectedStringGraphs.TWO_VERTEX_CONNECTED,
                        "A",
                        "B",
                        Collections.singletonList(Collections.singletonList(new DefaultEdge<>("A", "B")))
                ),
                Arguments.of(
                        "4",
                        DirectedStringGraphs.LOOP,
                        "A",
                        "E",
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C"),
                                        new DefaultEdge<>("C", "D"),
                                        new DefaultEdge<>("D", "E")
                                )
                        )
                ),
                Arguments.of(
                        "5",
                        DirectedStringGraphs.LOOP,
                        "A",
                        "C",
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C")
                                )
                        )
                ),
                Arguments.of(
                        "6",
                        DirectedStringGraphs.LOOP,
                        "C",
                        "A",
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("C", "D"),
                                        new DefaultEdge<>("D", "E"),
                                        new DefaultEdge<>("E", "A")
                                )
                        )
                ),
                Arguments.of(
                        "7",
                        DirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        "A",
                        "C",
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "D"),
                                        new DefaultEdge<>("D", "E"),
                                        new DefaultEdge<>("E", "C")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "O"),
                                        new DefaultEdge<>("O", "E"),
                                        new DefaultEdge<>("E", "C")
                                )
                        )
                ),
                Arguments.of(
                        "8",
                        DirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        "A",
                        "E",
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "D"),
                                        new DefaultEdge<>("D", "E")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "O"),
                                        new DefaultEdge<>("O", "E")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C"),
                                        new DefaultEdge<>("C", "E")
                                )
                        )
                ),
                Arguments.of(
                        "9",
                        DirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        "A",
                        "F",
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "D"),
                                        new DefaultEdge<>("D", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "O"),
                                        new DefaultEdge<>("O", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C"),
                                        new DefaultEdge<>("C", "E"),
                                        new DefaultEdge<>("E", "F")
                                )
                        )
                ),
                Arguments.of(
                        "10",
                        DirectedStringGraphs.GRAPH_WITH_THREE_DIFFERENT_PATHS,
                        "A",
                        "F",
                        Arrays.asList(
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "G"),
                                        new DefaultEdge<>("G", "H"),
                                        new DefaultEdge<>("H", "I"),
                                        new DefaultEdge<>("I", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "C"),
                                        new DefaultEdge<>("C", "D"),
                                        new DefaultEdge<>("D", "E"),
                                        new DefaultEdge<>("E", "F")
                                ),
                                Arrays.asList(
                                        new DefaultEdge<>("A", "B"),
                                        new DefaultEdge<>("B", "E"),
                                        new DefaultEdge<>("E", "F")
                                )
                        )
                )
        );
    }
}
