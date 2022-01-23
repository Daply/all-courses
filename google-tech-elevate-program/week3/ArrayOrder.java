package week3;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayOrder {

    public static int checkArrayOrder(int[] inputArray) {

        if (inputArray == null || inputArray.length < 2) {
            return 0;
        }

        //copy of array
        int[] sortedArray =  Arrays.copyOf(inputArray, inputArray.length);
        Arrays.sort(sortedArray);

        //check if array is sorted
        if (Arrays.equals(inputArray, sortedArray))
            return 1;

        //reverse sorted array
        for(int i = 0; i < sortedArray.length / 2; i++) {
            int temp = sortedArray[i];
            sortedArray[i] = sortedArray[sortedArray.length - i - 1];
            sortedArray[sortedArray.length - i - 1] = temp;
        }

        //check if array is sorted
        if (Arrays.equals(inputArray, sortedArray))
            return -1;

        //array not sorted
        return 0;
    }

    public static int[] readArray() {
        Scanner cin = new Scanner(System.in);
        int quantity = cin.nextInt();
        int[] list = {};
        if (quantity > 0)
            list = new int[quantity];

        int i = 0;
        while(i < quantity)
            list[i++] = cin.nextInt();

        cin.close();
        return list;
    }

    public static void main(String[] args) {
        int sortedOrder = checkArrayOrder(readArray());

        System.out.println(sortedOrder);
    }
}
