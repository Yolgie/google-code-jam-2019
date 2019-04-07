package at.cnoize.google.codejam2019.datbae;

import java.util.*;
import java.util.stream.Collectors;

public class Simulator {
    private final static int MAX_COUNT_BROKEN = 15;
    private final static int MAX_LENGTH = 1024;
    public static final int MIN_LENGTH = 2;

    private final List<Integer> broken;
    private final int length;

    public Simulator(int length, List<Integer> broken) {
        this.length = length;
        this.broken = broken;

        System.out.println("Length: \n" + length + "\nBroken: \n" + broken.stream().map(Object::toString).collect(Collectors.joining(" ")));
    }

    public Simulator(int length) {
        this(length, generateBroken(length));
    }

    public Simulator() {
        this(generateLength());
    }

    private static int generateLength() {
        return Math.max(MIN_LENGTH, new Random().nextInt(MAX_LENGTH));
    }

    private static List<Integer> generateBroken(int length) {
        final Set<Integer> brokenSet = new HashSet<>();
        final Random random = new Random();
        final int count = random.nextInt(Math.min(MAX_COUNT_BROKEN, length));
        while (brokenSet.size() < count) {
            brokenSet.add(random.nextInt(length));
        }
        final List<Integer> broken = new ArrayList<>(brokenSet);
        broken.sort(Comparator.naturalOrder());
        return broken;
    }

    public Map<Integer, String> generateTestResults() {
        final Map<Integer, String> testResults = new HashMap<>();

        for (Integer inputSectionLength : Solution.INPUT_SECTION_LENGTHS) {
            if (length > inputSectionLength) {
                final String testInput = Solution.buildTestString(length, inputSectionLength);
                final String testOutput = generateTestResult(testInput);
                testResults.put(inputSectionLength, testOutput);
            }
        }
        return testResults;
    }

    private String generateTestResult(String testInput) {
        StringBuilder testOutput = new StringBuilder();
        for (int i = 0; i < testInput.length(); i++) {
            if (!broken.contains(i)) {
                testOutput.append(testInput.charAt(i));
            }
        }
        return testOutput.toString();
    }

    public List<Integer> getBroken() {
        return new ArrayList<>(broken);
    }

    public int getLength() {
        return length;
    }
}
