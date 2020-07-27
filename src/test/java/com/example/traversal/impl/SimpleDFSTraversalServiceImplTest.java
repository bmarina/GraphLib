package com.example.traversal.impl;

import com.example.data.DirectedStringGraphs;
import com.example.data.UndirectedStringGraphs;
import com.example.graph.Graph;
import com.example.graph.impl.DefaultEdge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.example.graph.AbstractGraph.MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL;
import static com.example.graph.AbstractGraph.MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL;
import static com.example.graph.AbstractGraph.MSG_UNKNOWN_SOURCE_VERTEX;
import static com.example.graph.AbstractGraph.MSG_UNKNOWN_TARGET_VERTEX;
import static com.example.traversal.impl.SimpleDFSTraversalServiceImpl.MSG_GRAPH_SHOULD_BE_NON_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * SimpleDFSTraversalServiceImpl tests on directed and undirected graphs.
 * <p>
 * !!! Note: tests are not ideal and may not include some tricky paths in graph.
 * Be prepared to extend list of correct paths in case of traversal algorithm changes.
 */
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
            assertTrue(traversalResult.isEmpty(), "Empty result expected, but found: " + traversalResult);
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
            assertTrue(traversalResult.isEmpty(), "Empty result expected, but found: " + traversalResult);
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
                        Collections.singletonList(parseIntegerNodes("1-2"))
                ),
                Arguments.of(
                        "4",
                        UndirectedStringGraphs.TWO_VERTEX_CONNECTED,
                        2,
                        1,
                        Collections.singletonList(parseIntegerNodes("2-1"))
                ),
                Arguments.of(
                        "5",
                        UndirectedStringGraphs.LOOP,
                        1,
                        5,
                        Collections.singletonList(parseIntegerNodes("1-5"))

                ),
                Arguments.of(
                        "6",
                        UndirectedStringGraphs.LOOP,
                        2,
                        4,
                        Arrays.asList(
                                parseIntegerNodes("2-3;3-4"),
                                parseIntegerNodes("2-1;1-5;5-4")
                        )
                ),
                Arguments.of(
                        "7",
                        UndirectedStringGraphs.LOOP,
                        1,
                        3,
                        Arrays.asList(
                                parseIntegerNodes("1-2;2-3"),
                                parseIntegerNodes("1-5;5-4;4-3")
                        )
                ),
                Arguments.of(
                        "8",
                        UndirectedStringGraphs.LOOP,
                        3,
                        1,
                        Arrays.asList(
                                parseIntegerNodes("3-2;2-1"),
                                parseIntegerNodes("3-4;4-5;5-1")
                        )
                ),
                Arguments.of(
                        "9",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        1,
                        3,
                        Arrays.asList(parseIntegerNodes("1-2;2-3"))
                ),
                Arguments.of(
                        "10",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        1,
                        5,
                        Arrays.asList(
                                parseIntegerNodes("1-2;2-3;3-5"),
                                parseIntegerNodes("1-2;2-3;3-4;4-7;7-5"),
                                parseIntegerNodes("1-2;2-3;3-7;7-5")
                        )
                ),
                Arguments.of(
                        "11",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        1,
                        8,
                        Arrays.asList(
                                parseIntegerNodes("1-2;2-3;3-7;7-8"),
                                parseIntegerNodes("1-2;2-3;3-5;5-7;7-8"),
                                parseIntegerNodes("1-2;2-3;3-4;4-7;7-8"),
                                parseIntegerNodes("1-2;2-3;3-7;7-5;5-3;3-4;4-7;7-8")
                        )
                ),
                Arguments.of(
                        "12",
                        UndirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        1,
                        6,
                        Collections.emptyList()
                ),
                Arguments.of(
                        "13",
                        UndirectedStringGraphs.NOT_CONNECTED,
                        1,
                        6,
                        Collections.emptyList()
                ),
                Arguments.of(
                        "14",
                        UndirectedStringGraphs.NOT_CONNECTED,
                        1,
                        3,
                        Arrays.asList(
                                parseIntegerNodes("1-2;2-3"),
                                parseIntegerNodes("1-3")
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
                        Arrays.asList(parseStringNodes("A-B;B-C;C-D;D-E"))
                ),
                Arguments.of(
                        "5",
                        DirectedStringGraphs.LOOP,
                        "A",
                        "C",
                        Arrays.asList(parseStringNodes("A-B;B-C"))
                ),
                Arguments.of(
                        "6",
                        DirectedStringGraphs.LOOP,
                        "C",
                        "A",
                        Arrays.asList(parseStringNodes("C-D;D-E;E-A"))
                ),
                Arguments.of(
                        "7",
                        DirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        "A",
                        "C",
                        Arrays.asList(
                                parseStringNodes("A-B;B-C"),
                                parseStringNodes("A-B;B-D;D-E;E-C"),
                                parseStringNodes("A-B;B-O;O-E;E-C")
                        )
                ),
                Arguments.of(
                        "8",
                        DirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        "A",
                        "E",
                        Arrays.asList(
                                parseStringNodes("A-B;B-D;D-E"),
                                parseStringNodes("A-B;B-O;O-E"),
                                parseStringNodes("A-B;B-C;C-E")
                        )
                ),
                Arguments.of(
                        "9",
                        DirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        "A",
                        "F",
                        Arrays.asList(
                                parseStringNodes("A-B;B-D;D-E;E-F"),
                                parseStringNodes("A-B;B-O;O-E;E-F"),
                                parseStringNodes("A-B;B-C;C-E;E-F")
                        )
                ),
                Arguments.of(
                        "10",
                        DirectedStringGraphs.GRAPH_WITH_THREE_SAME_PATHS,
                        "A",
                        "Single",
                        Arrays.asList()
                ),
                Arguments.of(
                        "11",
                        DirectedStringGraphs.GRAPH_WITH_THREE_DIFFERENT_PATHS,
                        "A",
                        "F",
                        Arrays.asList(
                                parseStringNodes("A-B;B-G;G-H;H-I;I-E;E-F"),
                                parseStringNodes("A-B;B-C;C-D;D-E;E-F"),
                                parseStringNodes("A-B;B-E;E-F")
                        )
                ),
                Arguments.of(
                        "13",
                        DirectedStringGraphs.NOT_CONNECTED,
                        "A",
                        "C",
                        Collections.emptyList()
                ),
                Arguments.of(
                        "14",
                        DirectedStringGraphs.NOT_CONNECTED,
                        "F",
                        "D",
                        Arrays.asList(parseStringNodes("F-E;E-D"))
                ),
                Arguments.of(
                        "15",
                        DirectedStringGraphs.NOT_CONNECTED,
                        "A",
                        "D",
                        Collections.emptyList()
                ),
                Arguments.of(
                        "16",
                        DirectedStringGraphs.NOT_CONNECTED,
                        "B",
                        "E",
                        Collections.emptyList()
                )
        );
    }

    @Test
    public void getPathNullsTest() {
        final NullPointerException thrownOnGraph = assertThrows(
                NullPointerException.class,
                () -> serviceForTestingStringDirectedGraph.getPath(null,
                        "A",
                        "A"
                ),
                "Expected NullPointerException to throw, but it didn't"
        );
        assertEquals(MSG_GRAPH_SHOULD_BE_NON_NULL, thrownOnGraph.getLocalizedMessage());

        final NullPointerException thrownOnSource = assertThrows(
                NullPointerException.class,
                () -> serviceForTestingStringDirectedGraph.getPath(DirectedStringGraphs.ONE_VERTEX,
                        null,
                        "A"
                ),
                "Expected NullPointerException to throw, but it didn't"
        );
        assertEquals(MSG_SOURCE_VERTEX_SHOULD_BE_NON_NULL, thrownOnSource.getLocalizedMessage());

        final NullPointerException thrownOnTarget = assertThrows(
                NullPointerException.class,
                () -> serviceForTestingStringDirectedGraph.getPath(DirectedStringGraphs.ONE_VERTEX,
                        "A",
                        null
                ),
                "Expected NullPointerException to throw, but it didn't"
        );
        assertEquals(MSG_TARGET_VERTEX_SHOULD_BE_NON_NULL, thrownOnTarget.getLocalizedMessage());
    }

    @Test
    public void getPathUnknownNodesTest() {
        final IllegalArgumentException thrownOnSource = assertThrows(
                IllegalArgumentException.class,
                () -> serviceForTestingStringDirectedGraph.getPath(DirectedStringGraphs.ONE_VERTEX,
                        UUID.randomUUID().toString(),
                        "A"
                ),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_UNKNOWN_SOURCE_VERTEX, thrownOnSource.getLocalizedMessage());

        final IllegalArgumentException thrownOnTarget = assertThrows(
                IllegalArgumentException.class,
                () -> serviceForTestingStringDirectedGraph.getPath(DirectedStringGraphs.ONE_VERTEX,
                        "A",
                        UUID.randomUUID().toString()
                ),
                "Expected IllegalArgumentException to throw, but it didn't"
        );
        assertEquals(MSG_UNKNOWN_TARGET_VERTEX, thrownOnTarget.getLocalizedMessage());
    }

    /**
     * Parse string into String edges.
     */
    protected static List<DefaultEdge<String>> parseStringNodes(final String str) {
        return parse(str, strings -> new DefaultEdge<String>(strings[0], strings[1]));
    }

    /**
     * Parse string into Integer edges.
     */
    protected static List<DefaultEdge<Integer>> parseIntegerNodes(final String str) {
        return parse(str, strings -> new DefaultEdge<Integer>(Integer.valueOf(strings[0]), Integer.valueOf(strings[1])));
    }

    private static <T> List<DefaultEdge<T>> parse(final String str, Function<String[], DefaultEdge<T>> createObjectFunction) {
        if (str == null) {
            return Collections.emptyList();
        }

        final List<DefaultEdge<T>> result = new ArrayList<>();

        for (String link : str.split(";")) {
            String[] nodes = link.split("-", 2);
            // Let it fail if format is incorrect.
            result.add(createObjectFunction.apply(nodes));
        }
        return result;
    }

}
