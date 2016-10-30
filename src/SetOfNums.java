/*
 * SetOfNums
 *
 * v2.0.0
 *
 * 2016-10-27
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

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Contains a set of consecutive integers and operates on it to return partitions without arithmetic progressions
 */
public class SetOfNums {

    private ArrayList<Integer> numbers; // The initial set of numbers
    private ArrayList<String> goodPartitions; // All partitions without an arithmetic progression
    private int length; // Length of the initial set of numbers
    private int p, q; // Lengths of the arithmetic progressions

    /**
     * Initializes a SetOfNums object with a range of integers [n, n + 1, ..., k - 1, k]
     * @param start Starting value n
     * @param end Ending value k
     */
    public SetOfNums(int start, int end, int p, int q) {
        // Initializing class members
        length = end - start + 1;
        numbers = new ArrayList<>(length);
        this.p = p;
        this.q = q;
        goodPartitions = new ArrayList<>();
        int temp = start; // Temporary value for populating the array

        // Populating array
        for (int i = 0; i < length; i++) {
            numbers.add(i, temp++);
        }
    }

    /**
     * !! THE BEAST METHOD !!
     * Builds all relevant partitions for the current SetOfNums. How partitions are built follows:
     *
     * A useful number k = 2^(n/2) / 2 is the number of relevant partitions when n is even (because half of the
     * possible partitions will be redundant), and k = 2^((n - 1)/2) when n is odd.
     *
     * Count from 0 to k - 1 in binary strings
     *
     * Left pad the strings to floor(n/2) bits
     *
     * Mirror these strings on the right side (directly if n is even, across a middle 1 if n is odd)
     */
    public void buildPartitionSchemes() {
        int k; // Our super special magic number
        int bitTarget = (int) Math.floor(length / 2); // The number of bits to pad to
        boolean isOdd = false; // When n is odd, the middle element can be put in any subset of the partition (chosen as 1)
        String middleBit = ""; // If n is odd, this will be the middle element, 1, across which strings are mirrored.
        ArrayList<String> binaryStrings = new ArrayList<>(); // Fresh counts from 0 to k - 1
        ArrayList<String> paddedBinaryStrings = new ArrayList<>(); // Counts padded to bitTarget
        ArrayList<String> finishedBinaryStrings = new ArrayList<>(); // Padded counts mirrored

        // Determine k. If n (the length of the array) is even,
        if (length % 2 == 0) {
            // k = 2^(n/2) / 2
            k = ((int) Math.pow(2, (length / 2))) / 2;
        }
        // If it's odd,
        else {
            // k = 2^((n - 1)/2)
            k = (int) Math.pow(2, (length - 1) / 2);
            isOdd = true;
        }

        // Count from 0 to k - 1 and storing the numbers as strings.
        for (int i = 0; i < k; i++) {
            Integer temp = i;

            binaryStrings.add(Integer.toBinaryString(temp));
        }

        // Pad the strings to floor(n/2) bits.
        for (String s : binaryStrings) {
            // If the string needs padding,
            if (s.length() != bitTarget) {
                // find the difference,
                int diff = bitTarget - s.length();
                // and append that many 0's to its left side.
                for (int i = 0; i < diff; i++) {
                    s = "0" + s;
                }
            }
            paddedBinaryStrings.add(s);
        }

        // Mirror strings.
        if (isOdd) {
            middleBit = "1";
        }
        for (String s : paddedBinaryStrings) {
            String sMirror = "";
            for (int i = s.length() - 1; i >= 0; i--) {
                sMirror += s.charAt(i);
            }
            finishedBinaryStrings.add(s + middleBit + sMirror);
        }

        // Passing each partitions string to buildAndCheckPartitions()
        for (String s : finishedBinaryStrings) {
            buildAndCheckPartitions(s);
        }
    }

    /**
     * Builds and checks the subsets of a partition for the arithmetic progressions of the two given lengths twice --
     * once for the original partition and once for its "inverse" -- and stores all partitions whose two subsets do not
     * contain the queried progressions as strings in goodPartitions
     *
     * In practice, the same partition's subsets are checked for the opposite progressions as before (subsetOf1s for q
     * and subsetOf0s for p). If no progressions are found in the subsets, this time the inverse of the given partition
     * is added to goodPartitions (e.g. 1001 if the given partition was 0110). This simulates checking the inverted
     * partition, cutting the number of partitions actually checked by half, and was the easiest way to implement the
     * current functionality of this method given the way its previous functionality was implemented. For an example
     * of the summary above,
     *
     * assume the original set of numbers is [1 2 3 4].
     *
     * p = 6, q = 5
     *
     * A given partition for this set is 0110.
     *
     * CHECKING ORIGINAL PARTITION:
     *
     * Does the 1 subset have an arithmetic progression of length 6 (AP6)?    (I.e., "Inner p?")     No.
     * Does the 0 subset have an AP5?                                         (I.e., "Outer q?")     No.
     *
     * Store partition 0110.
     *
     * The inverse of the given partition is 1001.
     *
     * CHECKING "INVERSE" PARTITION:
     *
     * Does the 1 subset have an AP5?                             (For the original, "Inner q?")     No.
     * Does the 0 subset have an AP6?                             (For the original, "Outer p?")     No.
     *
     * Store the inverse of partition 0110.
     *
     * If one of those questions had a positive answer, the corresponding partition would not be stored.
     *
     * @param partition binary string representing a partition
     */
    private void buildAndCheckPartitions(String partition) {
        ArrayList<Integer> subsetOf1s = new ArrayList<>(); // 1's
        ArrayList<Integer> subsetOf0s = new ArrayList<>(); // 0's
        String awardString = ""; // Partition put in [brackets with spaces], built upon demand

        // Assigning all integers in the original set of numbers
        for (int i = 0; i < partition.length(); i++) {
            // to subsetOf1s if they coincide with a 1 in the partition string
            if (partition.charAt(i) == '1') {
                subsetOf1s.add(numbers.get(i));
            }
            // and to subsetOf0s if they coincide with a 0
            else if (partition.charAt(i) == '0') {
                subsetOf0s.add(numbers.get(i));
            }
        }

        // If the subsets of the given partition contain none of their assigned progressions,
        if (!containsAP(subsetOf1s, p) && !containsAP(subsetOf0s, q)) {
            // compile the given partition to a String,
            awardString = "[";
            for (int i = 0; i < partition.length(); i++) {
                awardString += partition.charAt(i) + " ";
            }
            awardString = awardString.substring(0, awardString.length() - 1) + "]";
            goodPartitions.add(awardString); // and add the given partition to goodPartitions.
        }

        // If the subsets of the "inverse" of the given partition contain none of their assigned progrssions,
        if (!containsAP(subsetOf1s, q) && !containsAP(subsetOf0s, p)) {
            // compile the inverse partition to a String,
            awardString = "[";
            for (int i = 0; i < partition.length(); i++) {
                // adding every given partition character's opposite
                if (partition.charAt(i) == '1') {
                    awardString += '0' + " ";
                }
                else if (partition.charAt(i) == '0') {
                    awardString += '1' + " ";
                }
            }
            awardString = awardString.substring(0, awardString.length() - 1) + "]";
            goodPartitions.add(awardString); // and add it to goodPartitions.
        }
    }

    /**
     * Checks whether there is an arithmetic progression in a subset of a partition
     *
     * An arithmetic progression is defined as a series of terms like:
     * a, a + d, a + 2d, ..., a + nd (where n is 0 to the desired length of the arithmetic progression minus one)
     * @param subset
     * @param APLength length of the arithmetic progression to search for
     * @return true if an arithmetic progression was detected; otherwise, false
     */
    private boolean containsAP(ArrayList<Integer> subset, int APLength) {
        int partLength = subset.size();
        // Eligible minimums of a partition where a = min and d = nextMin - min
        int min, nextMin;
        int d;
        HashSet<Integer> APTerms; // Set of terms for the arithmetic progression of given length
        boolean termMissing; // Canary

        // If the subset has less elements than the progression length, having an arithmetic progression is impossible.
        if (partLength < 4) {
            return false;
        }
        for (int i = 0; i < partLength - 1; i++) {
            for (int j = i + 1; j < partLength; j++) {
                // Initialize all the things for the current (min, nextMin) pair
                APTerms = new HashSet<>();
                min = subset.get(i);
                nextMin = subset.get(j);
                d = nextMin - min;
                termMissing = false; // Canary's alive

                // Build the set of arithmetic progression terms for the current (min, nextMin) pair
                for (int k = 0; k < APLength; k++) {
                    APTerms.add(min + k * d);
                }

                // Query subset for membership of all terms of the current (min, nextMin) pair.
                for (Integer term : APTerms) {
                    if (!subset.contains(term)) {
                        termMissing = true; // Kill the canary (i.e., a term is missing).
                    }
                }
                // Is the canary still alive (i.e., was there no term missing)?
                if (!termMissing) {
                    // Then all terms were present, so we have an arithmetic progression. Return true.
                    return true;
                }
                // Otherwise, proceed to the next pair or, if none remains, to conclude that the subset has no progression.
            }
        }
        return false;
    }

    /**
     * @return string breakdown of all partitions without an arithmetic progression or an indication that none such exist
     */
    public String getResults() {
        String numberSetString, result;

        numberSetString = "[";
        for (Integer n : numbers) {
            numberSetString += Integer.toString(n) + " ";
        }
        numberSetString = numberSetString.substring(0, numberSetString.length() - 1) + "]";
        result = "\nFor " + numberSetString + ", the following partitions did not have arithmetic progressions:\n\n";

        for (String s : goodPartitions) {
            result += s + "\n\n";
        }

        // Checking if result indicates that no partition without at least one arithmetic progression was found (i.e., a low line count)
        if (result.split("\n").length <= 2) {
            // If so, give a clear message indicating so.
            result = "\nNo partition without at least one arithmetic progression was found for the set " + numberSetString + ".\n";
        }
        return result;
    }

}