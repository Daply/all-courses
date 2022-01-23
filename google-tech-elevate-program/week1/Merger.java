package week1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Merge N sorted arrays into 1 sorted array. Read the input and print the result using the provided starter code.
//        Input
//        -----
//        The first line of the input contains one integer N, the number of sorted arrays. The next line contains N integers, the lengths of the N arrays. The following N lines contain the sorted elements of the arrays, separated by whitespaces. All elements are integers.

//        Output
//        ------
//        A sorted array containing the elements of all N arrays. The result should be output in one line, with elements separated by whitespaces.

//        Constraints:
//        ------
//        1 <= N <= 23’000
//
//        For all 1 <= p <= N and all  0 <= k < lenp, where lenp is the length of the array Ap, it holds that:
//
//        0 < len1, …, lenn < 1'000
//        the elements Ap[k] are of type int and 0 <= Ap[k] <= 1’000’000
//        Ap[i] <= Ap[j] for 0 <= i < j < lenp
//
//
//Example
//        -------
//        Input:
//
//        3
//
//        2 3 4
//
//        1 3
//
//        2 4 5
//
//        2 3 3 4
//
//
//
//        Output:
//
//        1 2 2 3 3 3 4 4 5
//
//
//
//        For the example above:
//
//        A_1 = [1, 3], A_2 = [2, 4, 5], A_3 = [2, 3, 3, 4];
//
//        The result is [1, 2, 2, 3, 3, 3, 4, 4, 5].

public class Merger {

    // Implementation with simple finding minimum function

    public static ArrayList<Integer> merge(ArrayList<ArrayList<Integer>> arrays, int[] arrayLengths) {

        int summLength = 0;
        for (int arrayLength: arrayLengths) {
            summLength += arrayLength;
        }
        ArrayList<Integer> resultArray = new ArrayList<>();
        int resultArrayIndex = 0;
        int [] arraysCurrentPointers = new int [arrays.size()];
        while (resultArrayIndex < summLength) {
            ArrayList<Integer> result = getMinimum(arrays, arraysCurrentPointers);
            resultArray.add(result.get(0));
            arraysCurrentPointers[result.get(1)]++;
            resultArrayIndex++;
        }

        return resultArray;
    }

    public static ArrayList<Integer> getMinimum(ArrayList<ArrayList<Integer>> arrays, int [] arraysCurrentPointers) {
        // result as minimum and index of array where this minimum ws found
        ArrayList<Integer> result = new ArrayList<>();
        int arrayIndex = 0;
        int minimum = -1;
        int arrayIndexOfMinimum = -1;
        for (int arrayPointer: arraysCurrentPointers) {
            if (arrayPointer < arrays.get(arrayIndex).size()) {
                int currentNumber = arrays.get(arrayIndex).get(arrayPointer);
                if (currentNumber < minimum || minimum == -1) {
                    minimum = currentNumber;
                    arrayIndexOfMinimum = arrayIndex;
                }
            }
            arrayIndex++;
        }
        result.add(minimum);
        result.add(arrayIndexOfMinimum);
        return result;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numArrays = Integer.parseInt(scanner.nextLine());
        int arrayLengths[] = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ArrayList<ArrayList<Integer>> arrays = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < numArrays; ++i) {
            int[] array = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int el : array) arrayList.add(Integer.valueOf(el));
            arrays.add(arrayList);
        }
        scanner.close();

        ArrayList<Integer> merged = merge(arrays, arrayLengths);
        StringBuffer sb = new StringBuffer();
        for (int s : merged) {
            sb.append(s);
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }

}
