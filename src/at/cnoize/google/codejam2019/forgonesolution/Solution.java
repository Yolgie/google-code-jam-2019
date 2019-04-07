package at.cnoize.google.codejam2019.forgonesolution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int targetAmount = in.nextInt();

            int amountOne = Integer.parseInt(String.valueOf(targetAmount).replaceAll("[12356789]", "0").replaceAll("4","1"));
            int amountTwo = targetAmount - amountOne;

            System.out.println("Case #" + i + ": " + amountOne + " " + amountTwo);
        }
    }
}

