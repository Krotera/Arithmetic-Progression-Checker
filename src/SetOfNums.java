import java.util.ArrayList;

/**
 * Created by Patrick on 2016-10-24.
 */
public class SetOfNums {

    private ArrayList<Integer> numbers; // The given set of numbers
    private ArrayList<String> goodPartitions; // All partitions of non-redundant partitioning schemes without an arithmetic progression
    private int length;

    /**
     * Initializes a SetOfNums object with a range of integers [n, n + 1, ..., k - 1, k]
     * @param start Starting value n
     * @param end Ending value k
     */
    public SetOfNums(int start, int end) {
        goodPartitions = new ArrayList<>();
        if (end < 4) {
            throw new IllegalArgumentException("The second argument must be greater than or equal to 4! Rerun the program.");
        }
        else if (start <= 0) {
            throw new IllegalArgumentException("The first argument must be greater than 0! Rerun the program.");
        }
        else if (start >= end) {
            throw new IllegalArgumentException("The first argument must be less than the second argument! Rerun the program.");
        }

        int temp = start; // Temporary value for populating the array

        // Initializing class members
        length = end - start + 1;
        numbers = new ArrayList<>(length);
        // Populating array
        for (int i = 0; i < length; i++) {
            numbers.add(i, temp++);
        }
    }

    /**
     * !! THE BEAST METHOD !!
     * Builds all relevant partition schemes for the current SetOfNums. A scheme consists of two partitions. The
     * partitions of each scheme are checked for an arithmetic progression in at least 4 terms. If none is found in
     * a partition, that partition is stored in goodPartitions. A summary of how schemes are built follows:
     *
     * k = 2^(n/2) / 2 is the number of relevant schemes when n is even (because half of the possible schemes
     * will be redundant), and k = 2^((n - 1)/2) when n is odd.
     *
     * Count from 0 to k - 1 in binary strings
     *
     * Left pad the strings to floor(n/2) bits
     *
     * Mirror these strings on the right side (directly if n is even, across a middle 1 if n is odd)
     *
     * This binary pattern informs which elements in the array of n elements to recruit into the first partition
     * (e.g., all 1's). The others are recruited into the second partition.
     */
    public void buildPartitionSchemes() {
        int k; // Our super special magic number
        int bitTarget = (int) Math.floor(length / 2);
        boolean isOdd = false; // When n is odd, the middle element can be put in any partition and the rest trated as an even set.
        String middleBit = ""; // If n is odd, this will be the middle element, 1, across which strings are mirrored.
        ArrayList<String> binaryStrings = new ArrayList<>();
        ArrayList<String> paddedBinaryStrings = new ArrayList<>();
        ArrayList<String> finishedBinaryStrings = new ArrayList<>();

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

        // Passing each scheme string to buildAndCheckPartitions()
        for (String s : finishedBinaryStrings) {
            buildAndCheckPartitions(s);
        }
    }

    /**
     * Builds and checks two partitions for a given partition scheme and stores that partition (plus its binary scheme
     * string) as a string in goodPartitions
     * @param partitionScheme binary string representing a partition pattern
     */
    private void buildAndCheckPartitions(String partitionScheme) {
        ArrayList<Integer> partitionA = new ArrayList<>(); // 1's
        ArrayList<Integer> partitionB = new ArrayList<>(); // 0's
        String decoratedScheme = ""; // Partition scheme put in [brackets with spaces], built upon demand
        String awardString = ""; // String representation of a good partition and the

        // Assigning all Integers in the original set of numbers
        for (int i = 0; i < partitionScheme.length(); i++) {
            // to partitionA if they coincide with a 1 in the scheme string
            if (partitionScheme.charAt(i) == '1') {
                partitionA.add(numbers.get(i));
            }
            // and to partitionB if they coincide with a 0
            else if (partitionScheme.charAt(i) == '0') {
                partitionB.add(numbers.get(i));
            }
        }

        // Checking partitions for arithmetic progression and adding them to goodPartitions if they have both none
        if (!partitionA.isEmpty() && !containsAP(partitionA)
                && !partitionB.isEmpty() && !containsAP(partitionB)) {
            // Preparing decoratedScheme
            decoratedScheme = "[";
            for (int i = 0; i < partitionScheme.length(); i++) {
                decoratedScheme += partitionScheme.charAt(i) + " ";
            }
            decoratedScheme = decoratedScheme.substring(0, decoratedScheme.length() - 1) + "]";

            // Preparing awardString for partition A
            awardString = "[";
            for (Integer n : partitionA) {
                awardString += Integer.toString(n) + " ";
            }
            awardString = awardString.substring(0, awardString.length() - 1) + "] " + "was found to have no arithmetic " +
                    "progression. The partition scheme was: \n" + decoratedScheme;
            goodPartitions.add(awardString);

            // Preparing awardString for partition B
            awardString = "[";
            for (Integer n : partitionB) {
                awardString += Integer.toString(n) + " ";
            }
            awardString = awardString.substring(0, awardString.length() - 1) + "] " + "was found to have no arithmetic " +
                    "progression. The partition scheme was: \n" + decoratedScheme;
            goodPartitions.add(awardString);
        }

        /*
         This is a leftover of a previous progression check that was dedicated to one partition at a time (in this case,
         partitionB). This had the advantage of supplying information about where a partition was in a set when returned
         (whether it was the 1's or 0's, as described in awardString).

         The commissioner of this project deemed this information superfluous and would have any partition scheme containing
          a partition with an arithmetic progression discarded, so the progression check was refactored to the one above.

          The check below is retained for future cases where that information may be desirable or when one partition might
          be checked differently from another (e.g., A for four- and B for five term arithmetic progressions).
          */
        /*if (!partitionB.isEmpty() && !containsAP(partitionB)) {
            decoratedScheme = "[";
            for (int i = 0; i < partitionScheme.length(); i++) {
                decoratedScheme += partitionScheme.charAt(i) + " ";
            }
            decoratedScheme = decoratedScheme.substring(0, decoratedScheme.length() - 1) + "]";

            awardString = "[";
            for (Integer n : partitionB) {
                awardString += Integer.toString(n) + " ";
            }
            awardString = awardString.substring(0, awardString.length() - 1) + "] " + "was found to have no arithmetic " +
                    "progression. It represents the 0's in:\n" + decoratedScheme;
            goodPartitions.add(awardString);
        }*/
    }

    /**
     * Checks whether there is an arithmetic progression of at least four terms in a partition
     *
     * An arithmetic progression is defined as a pattern like:
     * a, a + d, a + 2d, ..., a + nd
     * @param part a partition
     * @return true if an arithmetic progression was detected; otherwise, false
     */
    private boolean containsAP(ArrayList<Integer> part) {
        int partLength = part.size();
        // Eligible minimums of a partition where a = min and d = nextMin - min
        int min, nextMin;
        int d;
        // The first four terms of an arithmetic progression whose membership to query of the given partition
        // int term1; // a
        // int term2; // a + d
        int term3; // a + 2d
        int term4; // a + 3d

        // If the partition is shorter than 4 elements, having an arithmetic progression is impossible.
        if (partLength < 4) {
            return false;
        }
        for (int i = 0; i < partLength - 1; i++) {
            for (int j = i + 1; j < partLength; j++) {
                // Initialize all the things for the current (min, nextMin) pair
                min = part.get(i);
                nextMin = part.get(j);
                d = nextMin - min;
                // term1 = min; // min is always going to be present
                // term2 = min + d; // min + d = nextMin, which will also always be present
                // Thanks, Julzies.
                term3 = min + 2*d;
                term4 = min + 3*d;

                // Query partition for membership. If all of the terms exist, this partition is disqualified.
                if (part.contains(term3) && part.contains(term4)) {
                    return true;
                }
                // Otherwise, proceed to return false.
            }
        }
        return false;
    }

    /**
     * @return length of this set of numbers
     */
    public int getLength() {
        return length;
    }

    /**
     * @return string breakdown of all partitions in schemes where neither partition has an arithmetic progression
     * or an indication that none such exist
     */
    public String getResults() {
        String numberSetString, result;

        numberSetString = "[";
        for (Integer n : numbers) {
            numberSetString += Integer.toString(n) + " ";
        }
        numberSetString = numberSetString.substring(0, numberSetString.length() - 1) + "]";
        result = "\nFor " + numberSetString + "," + "\n\n";

        for (String s : goodPartitions) {
            result += s + "\n\n";
        }

        // Checking if result indicates that no schemes without at least one arithmetic progression were found (i.e., a low line count)
        if (result.split("\n").length <= 2) {
            // If so, give a clear message indicating so.
            result = "\nNo partitions in schemes without at least one arithmetic progression were found for the set " + numberSetString + ".";
        }
        return result;
    }

}
