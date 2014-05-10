package me.takashi.gcj2014.q.b;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class CookieClickerAlpha {
    final double C;
    final double F;
    final double X;
    static final double INITIAL_PRODUCTION = 2;

    final HashMap<Integer, Double> farmCost = new HashMap<Integer, Double>();

    CookieClickerAlpha(String line) {
        Scanner sc = new Scanner(line);

        C = sc.nextDouble();
        F = sc.nextDouble();
        X = sc.nextDouble();

        sc.close();

        farmCost.put(0, 0.0);
        farmCost.put(1, C / 2.0);
    }

    double resolve(){
        double min = Double.MAX_VALUE;

        for (int f = 0; true; f++) {
            min = Math.min(getSecondsToWin(f), min);
            double cost = getFarmCost(f);

            if (min < cost) break;
        }

        return min;
    }

    protected double getSecondsToWin(int f){
        if (f == 0) return X / getProduction(f); //= X/2
        if (f == 1) return C / 2 + X / getProduction(f); //= C/2 + X/2+F

        return getFarmCost(f) + X / getProduction(f);
    }

    protected double getFarmCost(int f){
        Double cost = farmCost.get(f);
        if (cost == null) {
            double prevCost = farmCost.get(f - 1);
            cost = prevCost + C / getProduction(f - 1);
            farmCost.put(f, cost);
        }
//        System.out.println("f=" + f + " cost=" + cost);

        return cost;
    }

    protected double getProduction(int f){
        return INITIAL_PRODUCTION + (f * F);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("data/B/B-large-practice.in"));
        PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter("data/B/B-large-practice.out")));

        final int T = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < T; i++) {
            String line = sc.nextLine();
            double sec = new CookieClickerAlpha(line).resolve();

            String output = "Case #" + (i + 1) + ": " + sec;

            System.out.println(output);
            w.println(output);
        }

        w.close();
        sc.close();
    }
}
