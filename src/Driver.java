/*
 * Driver
 *
 * v2.4.0
 *
 * 2017-05-29
 *
 * Copyright (C) 2017 Krotera
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
        int mode = 1;
        SetOfNums nums;
        String input, splitInput[];
        String rerun; // User input to rerun or exit the program

        System.out.println("Arithmetic Progression Checker v2.4.0\n\nFor a set of sequential, contiguous integers " +
                "[n, n + 1, ..., k - 1, k], palindromic partitions without arithmetic progressions of length p and q, " +
                "if any, will be returned in 'mode 1'. In 'mode 2', only the first progressionless partition will be" +
                " returned.");
        System.out.println("The default mode is 'mode 1'.\n");
        do {
            nums = null;
            System.out.println("Enter \"n k p q\" (without quotes) where n is a positive starting number, k is an ending number, p is the first progression length p, and q is the second progression length (or type \"mode 1\" or \"mode 2\" to change mode): ");
            input = sc.nextLine();
            // Check for mode change
            while ((input.toLowerCase()).startsWith("mode")) {
                // Mode admonishment
                while (!(input.equals("mode 1")) && !(input.equals("mode 2"))) {
                    System.out.println("Enter either \"mode 1\" (returns all progressionless partitions) or" +
                            " \"mode 2\" (returns only the first progressionless partition).");
                    input = sc.nextLine();
                }
                mode = Character.getNumericValue(input.charAt(5));
                System.out.println("\nRunning in mode " + mode + ".");
                System.out.println("\nEnter \"n k p q\" (without quotes) where n is a positive starting number, k is an ending number, p is the first progression length p, and q is the second progression length (or type \"mode 1\" or \"mode 2\" to change mode): ");
                input = sc.nextLine();
            }
            // Otherwise, try to parse string for numbers
            try {
                splitInput = input.split(" ");
                // Input quantity admonishment
                while (splitInput.length < 4) {
                    System.out.println("Not enough numeric arguments. Please re-enter the numbers as \"n k p q\".");
                    input = sc.nextLine();
                    splitInput = input.split(" ");
                }
                start = Integer.valueOf(splitInput[0]);
                end = Integer.valueOf(splitInput[1]);
                p = Integer.valueOf(splitInput[2]);
                q = Integer.valueOf(splitInput[3]);
            }
            catch (NumberFormatException x) {
                // Format admonishment
                System.out.println("Error: " + x + "\nPlease re-enter the numbers as \"n k p q\" (without quotes).");
                input = sc.nextLine();
                splitInput = input.split(" ");
                start = Integer.valueOf(splitInput[0]);
                end = Integer.valueOf(splitInput[1]);
                p = Integer.valueOf(splitInput[2]);
                q = Integer.valueOf(splitInput[3]);
            }
            // Arithmetic admonishment
            while (!(start > 0)
                    || !(end > 0)
                    || !(p > 0)
                    || !(q > 0)) {
                System.out.println("All numbers must be greater than 0. Please re-enter.");
                input = sc.nextLine();
                splitInput = input.split(" ");
                start = Integer.valueOf(splitInput[0]);
                end = Integer.valueOf(splitInput[1]);
                p = Integer.valueOf(splitInput[2]);
                q = Integer.valueOf(splitInput[3]);
            }
            // Rock 'n roll
            nums = new SetOfNums(start, end, p, q, mode);
            System.out.println("\nWorking...");
            nums.buildPartitionSchemes();
            // Afterparty
            System.out.println(nums.getResults());
            System.out.println("Would you like to run another check (y/n)?");
            rerun = sc.next().toLowerCase();
            // Admonishment
            while (rerun.charAt(0) != 'y' && rerun.charAt(0) != 'n') {
                System.out.println("Please use either \"y\" or \"n\" to run another check or exit.");
                rerun = sc.next().toLowerCase();
            }
            sc.nextLine(); // Eating garbage newline
            System.out.print("\n");
        } while (rerun.charAt(0) == 'y');
        System.out.println("Exiting...");
        sc.close();
    }
}
