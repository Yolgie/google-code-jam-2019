package at.cnoize.google.codejam2019.youcangoyourownway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        in.nextLine();
        for (int i = 1; i <= t; i++) {
            in.nextLine();
            String path = in.nextLine();

            String solution = path.replaceAll("S", "P").replaceAll("E", "S").replaceAll("P", "E");

            System.out.println("Case #" + i + ": " + solution);
        }
    }
}

