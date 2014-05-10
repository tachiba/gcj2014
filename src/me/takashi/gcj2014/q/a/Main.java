package me.takashi.gcj2014.q.a;

import java.io.*;
import java.util.Scanner;

public class Main {
    static final int GRID_SIZE = 4;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("A-small-practice.in"));

        FileWriter fw = new FileWriter("A-small-practice.out");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        final int T = sc.nextInt();
        for (int t = 0; t < T; t++) {
            int first_choice = sc.nextInt();
            int[][] first_arrangements = new int[GRID_SIZE][GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    first_arrangements[i][j] = sc.nextInt();
                }
            }
            int[] first_row = first_arrangements[first_choice - 1];

            int second_choice = sc.nextInt();
            int[][] second_arrangements = new int[GRID_SIZE][GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    second_arrangements[i][j] = sc.nextInt();
                }
            }
            int[] second_row = second_arrangements[second_choice - 1];

            int matched = 0;
            int lastMatchedValue = -1;
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    if (first_row[i] == second_row[j]) {
                        matched++;
                        lastMatchedValue = first_row[i];
                    }
                }
            }

            pw.print    ("Case #" + (t + 1) + ": ");
            switch (matched) {
                case 0:
                    pw.println("Volunteer cheated!");
                    break;
                case 1:
                    pw.println("" + lastMatchedValue);
                    break;
                default:
                    pw.println("Bad magician!");
            }
        }

        pw.flush();
        pw.close();
        sc.close();
    }
}
