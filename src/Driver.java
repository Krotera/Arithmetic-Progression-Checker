/*
 * Driver
 *
 * v1.0.4
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

import java.util.Scanner;

/**
 * Driver class
 */
public class Driver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int start, end;
        SetOfNums nums;

        System.out.println("Arithmetic Progression Checker v0.1.9\n\nFor a set of sequential, contiguous integers [n, n + 1, ..., k - 1, k], partitions of the " +
                "palindromic partition schemes without arithmetic progressions in either partition, if any, will be returned.\n");
        System.out.println("Enter starting number n (greater than 0): ");
        start = sc.nextInt();
        System.out.println("Enter ending number k (greater than or equal to 4): ");
        end = sc.nextInt();

        nums = new SetOfNums(start, end);
        nums.buildPartitionSchemes();

        System.out.println(nums.getResults());
    }

}
