import java.util.Scanner;

/**
 * Created by Patrick on 2016-10-24.
 */
public class Driver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int start, end;
        SetOfNums nums;

        System.out.println("This program will take a set of sequential, contiguous integers [n, n + 1, ..., k - 1, k], " +
                "split the set into two for each (non-redundant) palindromic partition scheme, and check the two " +
                "partitions of each for an arithmetic progression in at least four terms. \nThe partitions of the" +
                " schemes without an arithmetic progression in either partition, if any, are then returned.\n");
        System.out.println("Enter starting number n (greater than 0): ");
        start = sc.nextInt();
        System.out.println("Enter ending number k (greater than or equal to 4): ");
        end = sc.nextInt();

        nums = new SetOfNums(start, end);
        nums.buildPartitionSchemes();

        System.out.println(nums.getResults());
    }

}
