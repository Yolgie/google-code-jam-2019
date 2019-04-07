package at.cnoize.google.codejam2019.datbae;


import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public static final List<Integer> INPUT_SECTION_LENGTHS = Arrays.asList(16, 8, 4, 2, 1);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int ks = 1; ks <= testcase; ks++) {
            int n = input.nextInt();
            int b = input.nextInt();
            int f = input.nextInt();
            solve(input, n);
        }
    }

    private static void solve(Scanner input, int length) {

        final Map<Integer, String> testResults = new HashMap<>();

        for (Integer inputSectionLength : INPUT_SECTION_LENGTHS) {
            if (length > inputSectionLength) {
                System.out.println(buildTestString(length, inputSectionLength));
                testResults.put(inputSectionLength, input.next());
                if (testResults.get(inputSectionLength).equals("-1")) {
                    System.err.println("doh, wrong input " + inputSectionLength);
                    System.exit(-1);
                }
            }
        }

        final List<Integer> brokenWorkerIndexes = findBrokenWorkers(length, testResults);

        System.out.println(brokenWorkerIndexes.stream().map(Object::toString).collect(Collectors.joining(" ")));

        if (input.nextInt() != 1) {
            System.err.println("doh");
            System.exit(-1);
        }
    }

    public enum BrokenState {
        NOT_SET, NOT_BROKEN, POSSIBLY_BROKEN, DEFINITELY_BROKEN
    }

    public static List<Integer> findBrokenWorkers(int length, Map<Integer, String> testResults) {
        List<List<BrokenState>> results = new ArrayList<>();
        List<BrokenState> previous = null;
        int previousSectionLength = length;

        for (Integer sectionLength : INPUT_SECTION_LENGTHS) {
            if (testResults.containsKey(sectionLength)) {
                List<BrokenState> marks = findBrokenWorkersForTestResult(length, sectionLength, testResults.get(sectionLength), previous, previousSectionLength);
                previous = marks;
                previousSectionLength = sectionLength;
                results.add(marks);
            }
        }

        // aggregate
        List<Integer> brokenIndices = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            final int index = i;
            final Set<BrokenState> statesForIndex = results.stream()
                    .map(brokenStates -> brokenStates.get(index))
                    .collect(Collectors.toSet());
            if (statesForIndex.contains(BrokenState.DEFINITELY_BROKEN)) {
                brokenIndices.add(i);
            }
        }

        return brokenIndices;
    }

    private static List<BrokenState> findBrokenWorkersForTestResult(final int length,
                                                                    final int sectionLength,
                                                                    final String testString,
                                                                    final List<BrokenState> previousMarks,
                                                                    final int previousSectionLength) {
        final String originalString = buildTestString(length, sectionLength);
        int offset = 0;
        List<BrokenState> marks = new ArrayList<>(Collections.nCopies(length, BrokenState.NOT_SET));
        for (int i = 0; i < length; i++) {
            // handle previously fixed
            if (previousMarks != null
                    && (previousMarks.get(i).equals(BrokenState.NOT_BROKEN)
                    || previousMarks.get(i).equals(BrokenState.DEFINITELY_BROKEN))) {
                marks.set(i, previousMarks.get(i));
            }
            if (marks.get(i).equals(BrokenState.DEFINITELY_BROKEN)) {
                offset--;
            }
            // handle new and previously possible
            if (marks.get(i).equals(BrokenState.NOT_SET)
                    && (testString.length() <= i + offset
                    || testString.charAt(i + offset) != originalString.charAt(i))) {
                marks.set(i, BrokenState.POSSIBLY_BROKEN);
                offset--;
            }
            if (previousMarks != null && ((i + 1) % previousSectionLength == 0 || i == length - 1)) {
                final int containedMarks = countContainedPossiblesInSection(marks, previousSectionLength, i);
                final int containedMarksPrevious = countContainedPossiblesInSection(previousMarks, previousSectionLength, i);
                final int difference = containedMarksPrevious - containedMarks;
                if (difference != 0) {
                    marks = markLastInSectionAs(BrokenState.POSSIBLY_BROKEN, marks, previousSectionLength, i, difference);
                    offset -= difference;
                }
            }
        }
        for (int i = 0; i < length; i += sectionLength) {
            final Set<BrokenState> containedMarks = getContainedMarksInSection(marks, sectionLength, i);
            if (containedMarks.size() == 1 && containedMarks.contains(BrokenState.NOT_SET)) {
                marks = markSectionAs(BrokenState.NOT_BROKEN, marks, sectionLength, i);
            }
            if (containedMarks.size() == 1 && containedMarks.contains(BrokenState.POSSIBLY_BROKEN)) {
                marks = markSectionAs(BrokenState.DEFINITELY_BROKEN, marks, sectionLength, i);
            }
        }
        return marks;
    }

    private static Set<BrokenState> getContainedMarksInSection(List<BrokenState> marks, int sectionLength, int index) {
        final int sectionStartIndex = (index / sectionLength) * sectionLength;
        final int sectionEndIndex = Math.min(((index + sectionLength) / sectionLength) * sectionLength, marks.size());
        return new HashSet<>(marks.subList(sectionStartIndex, sectionEndIndex));
    }

    private static int countContainedPossiblesInSection(List<BrokenState> marks, int sectionLength, int index) {
        final int sectionStartIndex = (index / sectionLength) * sectionLength;
        final int sectionEndIndex = Math.min(((index + sectionLength) / sectionLength) * sectionLength, marks.size());
        return Collections.frequency(marks.subList(sectionStartIndex, sectionEndIndex), BrokenState.POSSIBLY_BROKEN);
    }

    public static List<BrokenState> markSectionAs(final BrokenState newState,
                                                  final List<BrokenState> marks,
                                                  final int sectionLength,
                                                  final int index) {
        final int sectionStartIndex = (index / sectionLength) * sectionLength;
        final int sectionEndIndex = Math.min(((index + sectionLength) / sectionLength) * sectionLength, marks.size());
        for (int i = sectionStartIndex; i < sectionEndIndex; i++) {
            marks.set(i, newState);
        }
        return marks;
    }

    public static List<BrokenState> markLastInSectionAs(final BrokenState newState,
                                                        final List<BrokenState> marks,
                                                        final int sectionLength,
                                                        final int index,
                                                        final int count) {
        final int sectionStartIndex = (index / sectionLength) * sectionLength;
        final int sectionEndIndex = Math.min(((index + sectionLength) / sectionLength) * sectionLength, marks.size());
        int set = 0;
        for (int i = sectionEndIndex - 1; i >= sectionStartIndex && set < count; i--) {
            if (!marks.get(i).equals(newState)) {
                marks.set(i, newState);
                set++;
            }
        }
        return marks;
    }

    public static String buildTestString(int length, int sectionLength) {
        final String pattern = "01";
        final int sectionCount = (length + sectionLength - 1) / sectionLength;
        final StringBuilder testString = new StringBuilder(sectionCount * sectionLength);

        for (int sectionNumber = 0; sectionNumber < sectionCount; sectionNumber++) {
            testString.append(repeatChar(pattern.charAt(sectionNumber % 2), sectionLength));
        }

        return testString.substring(0, length);
    }

    public static StringBuilder repeatChar(char pattern, int count) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(pattern);
        }
        return result;
    }
}
