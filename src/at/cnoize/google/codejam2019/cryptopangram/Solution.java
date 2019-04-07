package at.cnoize.google.codejam2019.cryptopangram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase();

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; i++) {
            int n = in.nextInt();
            int l = in.nextInt();
            List<Integer> input = new ArrayList<>();
            for (int j = 1; j <= l; j++) {
                input.add(in.nextInt());
            }

            List<List<Integer>> factorPairs = new ArrayList<>();
            Set<Integer> factorSet = new HashSet<>();

            for (Integer encrypted : input) {
                final List<Integer> factors = primeFactors(encrypted);
                factorPairs.add(factors);
                factorSet.addAll(factors);
            }

            List<Integer> factorsInOrder = new ArrayList<>();
            int sortingStartIndex = 0;

            for (int j = 0; j < factorPairs.size()-1; j++) {
                Set<Integer> factorPairSet = new HashSet<>();
                factorPairSet.addAll(factorPairs.get(j));
                factorPairSet.addAll(factorPairs.get(j+1));
                if (factorPairSet.size() == 3) {
                    sortingStartIndex = j;
                    break;
                }
            }

            // sort backwards
            for (int j = sortingStartIndex; j > 0 ; j--) {
                final List<Integer> first = factorPairs.get(j);
                final List<Integer> second = factorPairs.get(j + 1);
//                first.stream().f
            }

            // sort forwards

            System.out.println("Case #" + i + ": " + "solution");
        }
    }

    // taken from https://www.vogella.com/tutorials/JavaAlgorithmsPrimeFactorization/article.html
    public static List<Integer> primeFactors(int number) {
        int n = number;
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }
}

