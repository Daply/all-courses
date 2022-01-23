package week3;

import java.util.Scanner;

public class SecondLargest {

    public static void secondLargest(int[] list)
    {
        if (list.length < 2) {
            System.out.println(-1);
        }
        else {
            int firstMaximum = Integer.MIN_VALUE;
            int previousFirstMaximum = Integer.MIN_VALUE;
            int secondMaximum = Integer.MIN_VALUE;
            for (int element : list) {
                if (element > firstMaximum) {
                    previousFirstMaximum = firstMaximum;
                    firstMaximum = element;
                }
                else if (element != firstMaximum && element > secondMaximum) {
                    secondMaximum = element;
                }
            }

            if (secondMaximum == Integer.MIN_VALUE) {
                if (previousFirstMaximum != Integer.MIN_VALUE)
                    secondMaximum = previousFirstMaximum;
                else
                    secondMaximum = -1;
            }

            System.out.print(secondMaximum);
        }
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
        secondLargest(readArray());
    }
}
