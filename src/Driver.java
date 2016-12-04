/*
 * Driver
 *
 * v2.2.0
 *
 * 2016-12-04
 *
 * Copyright (C) 2016 Krotera
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Scanner;

/**
 * Driver class containing main() and taking user input
 */
public class Driver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int start, end, p, q;
        SetOfNums nums;
        String rerun; // User input to rerun or exit the program
        String stepOne = "STEP ONE: Specify the initial set of numbers"; // Changed after the first run for cosmetics

        System.out.println("Arithmetic Progression Checker v2.1.0\n\nFor a set of sequential, contiguous integers " +
                "[n, n + 1, ..., k - 1, k], palindromic partitions without arithmetic progressions of length p and q, " +
                "if any, will be returned.\n");
        do {
            System.out.println(stepOne);
            System.out.println("============================================\n");
            System.out.println("Enter starting number n (greater than 0): ");
            start = sc.nextInt();
            // Admonishment
            while (!(start > 0)) {
                System.out.println("The starting number must be greater than 0. Please re-enter.");
                start = sc.nextInt();
            }
            System.out.println("Enter ending number k (greater than 0): ");
            end = sc.nextInt();
            // Admonishment
            while (!(end > 0)) {
                System.out.println("The ending number must be greater than 0. Please re-enter.");
                end = sc.nextInt();
            }
            System.out.println("\nSTEP TWO: Specify the arithmetic progression lengths");
            System.out.println("====================================================\n");
            System.out.println("Enter first progression length p (greater than 0): ");
            p = sc.nextInt();
            // Admonishment
            while (!(p > 0)) {
                System.out.println("Progression lengths must be greater than 0. Please re-enter.");
                p = sc.nextInt();
            }
            System.out.println("Enter second progression length q (greater than 0): ");
            q = sc.nextInt();
            // Admonishment
            while (!(q > 0)) {
                System.out.println("Progression lengths must be greater than 0. Please re-enter.");
                q = sc.nextInt();
            }
            // Rock 'n roll
            nums = new SetOfNums(start, end, p, q);
            System.out.println("\nWorking...");
            nums.buildPartitionSchemes();
            // Afterparty
            System.out.println(nums.getResults());
            System.out.println("Would you like to run another check?");
            rerun = sc.next().toLowerCase();
            // Admonishment
            while (rerun.charAt(0) != 'y' && rerun.charAt(0) != 'n') {
                System.out.println("Please use either \"Y\" or \"N\".");
                rerun = sc.next().toLowerCase();
            }
            stepOne = "\nSTEP ONE: Specify the initial set of numbers";
        } while (rerun.charAt(0) == 'y');
        System.out.println("Exiting...");
        sc.close();
    }
}
