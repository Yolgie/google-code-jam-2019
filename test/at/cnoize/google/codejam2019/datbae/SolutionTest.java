package at.cnoize.google.codejam2019.datbae;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @ParameterizedTest
    @CsvSource({
            "0, 1, 0",
            "0, 2, 00",
            "0, 4, 0000",
            "0, 8, 00000000",
            "1, 8, 11111111",
    })
    public void testRepeatChar(char pattern, int count, String expected) {
        assertTrue(count > 0);
        assertEquals(expected, Solution.repeatChar(pattern, count).toString());
    }

    @ParameterizedTest
    @CsvSource({
            " 6, 3, 000111",
            " 7, 3, 0001110",
            " 8, 3, 00011100",
            " 9, 3, 000111000",
            "10, 3, 0001110001",
    })
    public void testBuildTestString(int length, int sectionLength, String expected) {
        assertEquals(expected, Solution.buildTestString(length, sectionLength));
    }

    private static Stream<Arguments> provideForMarkSectionAs() {
        return Stream.of(
                Arguments.of(
                        Solution.BrokenState.DEFINITELY_BROKEN,
                        new ArrayList<>(Collections.nCopies(6, Solution.BrokenState.NOT_SET)),
                        4,
                        5,
                        ImmutableList.of(
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.DEFINITELY_BROKEN,
                                Solution.BrokenState.DEFINITELY_BROKEN)),
                Arguments.of(
                        Solution.BrokenState.DEFINITELY_BROKEN,
                        new ArrayList<>(Collections.nCopies(6, Solution.BrokenState.NOT_SET)),
                        4,
                        4,
                        ImmutableList.of(
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.DEFINITELY_BROKEN,
                                Solution.BrokenState.DEFINITELY_BROKEN)),
                Arguments.of(
                        Solution.BrokenState.DEFINITELY_BROKEN,
                        new ArrayList<>(Collections.nCopies(6, Solution.BrokenState.NOT_SET)),
                        4,
                        3,
                        ImmutableList.of(
                                Solution.BrokenState.DEFINITELY_BROKEN,
                                Solution.BrokenState.DEFINITELY_BROKEN,
                                Solution.BrokenState.DEFINITELY_BROKEN,
                                Solution.BrokenState.DEFINITELY_BROKEN,
                                Solution.BrokenState.NOT_SET,
                                Solution.BrokenState.NOT_SET))
        );
    }

    @ParameterizedTest
    @MethodSource("provideForMarkSectionAs")
    public void testMarkSectionAs(Solution.BrokenState newState, List<Solution.BrokenState> marks, int sectionLength, int index, List<Solution.BrokenState> expected) {
        final List<Solution.BrokenState> actual = Solution.markSectionAs(newState, marks, sectionLength, index);
        System.out.println(actual.stream().map(Object::toString).collect(Collectors.joining(" ")));
        assertIterableEquals(expected, actual);
    }

    private static Stream<Arguments> provideForFindBrokenWorkers() {
        return Stream.of(
                Arguments.of(
                        16,
                        ImmutableMap.builder()
                                .put(4, "0000111100001111")
                                .put(2, "0011001100110011")
                                .put(1, "0101010101010101")
                                .build(),
                        ImmutableList.of()),
                Arguments.of(
                        16,
                        ImmutableMap.builder()
                                .put(4, "11100001111")
                                .put(2, "01100110011")
                                .put(1, "10101010101")
                                .build(),
                        ImmutableList.of(0, 1, 2, 3, 4)),
                Arguments.of(
                        16,
                        ImmutableMap.builder()
                                .put(4, "00110011")
                                .put(2, "01010101")
                                .put(1, "00000000")
                                .build(),
                        ImmutableList.of(1, 3, 5, 7, 9, 11, 13, 15)),
                Arguments.of(
                        4,
                        ImmutableMap.builder()
                                .put(2, "0")
                                .put(1, "0")
                                .build(),
                        ImmutableList.of(1, 2, 3)),
                Arguments.of(
                        6,
                        ImmutableMap.builder()
                                .put(4, "001")
                                .put(2, "000")
                                .put(1, "010")
                                .build(),
                        ImmutableList.of(2, 3, 5)),
                Arguments.of(
                        6,
                        ImmutableMap.builder()
                                .put(4, "001")
                                .put(2, "000")
                                .put(1, "011")
                                .build(),
                        ImmutableList.of(2, 3, 4)),
                Arguments.of(
                        6,
                        ImmutableMap.builder()
                                .put(4, "00")
                                .put(2, "00")
                                .put(1, "01")
                                .build(),
                        ImmutableList.of(2, 3, 4, 5)),
                Arguments.of(
                        1024,
                        ImmutableMap.builder()
                                .put(16, "000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000000111111111111111100000000000000001111111111111111000000000000000011111111111111110000000000000001111111111111111")
                                .put(8, "000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111111000000001111111100000000111111110000000011111110000000011111111")
                                .put(4, "000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100001111000011110000111100011110000111100001111")
                                .put(2, "001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001100110011001101100110011001100110011")
                                .put(1, "010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010110101010101010101010101")
                                .build(),
                        ImmutableList.of(1000))
        );
    }

    @ParameterizedTest
    @MethodSource({"provideForFindBrokenWorkers", "provideForFindBrokenWorkersWithSimulator"})
    public void testFindBrokenWorkers(int length, Map<Integer, String> testResults, List<Integer> expected) {
        final List<Integer> brokenWorkers = Solution.findBrokenWorkers(length, testResults);
        System.out.println(brokenWorkers.stream().map(Object::toString).collect(Collectors.joining(" ")));
        assertIterableEquals(expected, brokenWorkers);
    }

    private static Stream<Arguments> provideForFindBrokenWorkersWithSimulator() {
        final List<Simulator> simulators = ImmutableList.of(
                new Simulator(24, ImmutableList.of(7, 17, 18, 23)),
                new Simulator(12, ImmutableList.of(1, 5, 6, 8, 9)),
                new Simulator(12, ImmutableList.of(1, 2, 3, 5, 6, 8, 9))
        );
        return simulators.stream()
                .map(simulator -> Arguments.of(simulator.getLength(), simulator.generateTestResults(), simulator.getBroken()));
    }

    @Test
    public void testSimulation() {
        final Simulator simulator = new Simulator(12, ImmutableList.of(1, 2, 3, 5, 6, 8, 9));
        testFindBrokenWorkers(simulator.getLength(), simulator.generateTestResults(), simulator.getBroken());
    }

    @RepeatedTest(1000)
    void bruteForceSimulation() {
        final Simulator simulator = new Simulator(24);
        testFindBrokenWorkers(simulator.getLength(), simulator.generateTestResults(), simulator.getBroken());
    }
}