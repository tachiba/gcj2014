package me.takashi.gcj2014.q.c;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by takashi on 5/24/14.
 */
public class Minesweeper {
    final int R;
    final int C;
    final int M;
    final int NON_MINE_CELLS_COUNT;

    Minesweeper(int r, int c, int m){
        R = r;
        C = c;
        M = m;

        NON_MINE_CELLS_COUNT = R * C - M;

        System.out.println("R=" + R + ", C=" + C + ", M=" + M + " N=" + NON_MINE_CELLS_COUNT);
    }

    String resolve() {
        for (int c = 0; c < C; c++) {
            for (int r = 0; r < R; r++) {
                MinesweeperAttempt attempt = new MinesweeperAttempt(this, r, c);
                if (attempt.resolve()) {
                    return attempt.result();
                }
            }
        }

        return "Impossible";
    }

    class MinesweeperAttempt {
        final int FirstClickedR;
        final int FirstClickedC;
        final int[][] cells;
        final int[][] OPERATIONS = {
                {1, -1}, {1, 0}, {1, 1},
                {0, -1}, {0, 1},
                {-1, -1}, {-1, 0}, {-1, 1},
        };
        final Minesweeper minesweeper;

        int nonMineCellsCount;

        int[] currentParentPosition;

        MinesweeperAttempt(Minesweeper m, final int r, final int c){
            FirstClickedR = r;
            FirstClickedC = c;
            minesweeper = m;
            nonMineCellsCount = m.NON_MINE_CELLS_COUNT;

//            System.out.println("FirstClickedR=" + FirstClickedR + " FirstClickedC=" + FirstClickedC);

            cells = new int[R][C];
            for (int i = 0; i < R; i++) {
                Arrays.fill(cells[i], -1);
//                System.out.println(Arrays.toString(cells[r]));
            }
        }

        boolean resolve(){
            click(FirstClickedR, FirstClickedC);

            /* empty cell count == 1 */
//            System.out.println(Arrays.toString(currentParentPosition));
//            System.out.println(cells[currentParentPosition[0]][currentParentPosition[1]]);

//            for (int i = 0; i < R; i++) {
//                System.out.println(Arrays.toString(cells[i]));
//            }

            if (currentParentPosition[0] == FirstClickedR && currentParentPosition[1] == FirstClickedC) {
                return true;
            } else if (cells[currentParentPosition[0]][currentParentPosition[1]] != 0){
                return true;
            } else {
                return false;
            }
        }

        String result(){
            StringBuilder sb = new StringBuilder();

            for (int r = 0; r < minesweeper.R; r++) {
                for (int c = 0; c < minesweeper.C; c++) {
                    if (r == FirstClickedR && c == FirstClickedC) {
                        sb.append("c");
                    } else if (cells[r][c] == -1) {
                        sb.append("*");
                    } else {
                        sb.append(".");
                    }
                }
                sb.append("\n");
            }

            return sb.toString();
        }

        protected
        void click(final int r, final int c){
//            System.out.println("Click r=" + r + " c=" + c);

            /* self */
            if (isInsideTheGrid(r, c)) {
                cells[r][c] = calculateCurrentNumber(r, c);
//                System.out.println("current=" + cells[r][c]);
                nonMineCellsCount--;

                if (nonMineCellsCount == 0) return;

                currentParentPosition = new int[]{r, c};
            }

            /* neighbors */
            int[][] positions = operationAppliedPositions(r, c);
//            for (int i = 0; i < positions.length; i++) {
//                System.out.println(Arrays.toString(positions[i]));
//            }

            for (int o = 0; o < positions.length; o++) {
                int[] p = positions[o];
                if (p == null) continue;
                if (cells[p[0]][p[1]] != -1) continue;

                click(p[0], p[1]);
            }
        }

        protected
        int calculateCurrentNumber(final int r, final int c){
            int sum = 0;

            int[][] positions = operationAppliedPositions(r, c);
            for (int o = 0; o < positions.length; o++) {
                int[] p = positions[o];
                if (p == null) continue;

                if (cells[p[0]][p[1]] == -1) {
                    sum += 1;
                }
            }

            return sum;
        }

        /**
         * returns existing cell options
         * @param r
         * @param c
         * @return
         */
        protected int[][] operationAppliedPositions(final int r, final int c){
            int[][] result = new int[8][2];

            for (int i = 0; i < OPERATIONS.length; i++) {
                int[] ops = OPERATIONS[i];
                final int nextR = ops[0] + r;
                final int nextC = ops[1] + c;

                if (isInsideTheGrid(nextR, nextC)) {
                    result[i] = new int[]{nextR, nextC};
                } else {
                    result[i] = null;
                }
            }

            return result;
        }

        protected
        boolean isInsideTheGrid(final int r, final int c){
            return r >= 0 && r < R && c >= 0 && c < C;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("data/C/sample.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/C/sample.out")));

        final int T = sc.nextInt();
        for (int t = 0; t < T; t++) {
            int r = sc.nextInt();
            int c = sc.nextInt();
            int m = sc.nextInt();

            String result = new Minesweeper(r, c, m).resolve();

            pw.println("Case #" + (t + 1) + ":");
            System.out.println(result);
            pw.println(result);
        }

        sc.close();
        pw.close();
    }
}
