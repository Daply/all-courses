package week3;

import java.util.Scanner;

public class EvaluateMathExpression {

    public static double evaluateMathExpression(String expression) {
        String[] expressionParts = expression.split(" ");
        if (expressionParts.length != 3) {
            throw new IllegalArgumentException("Must have 3 tokens separated by spaces");
        }
        // first number as string
        String firstNumberAsString = expressionParts[0];
        // math operator as string (e.g. '+').
        String operatorAsString = expressionParts[1];
        // is second number as string
        String secondNumberAsString = expressionParts[2];

        // first number
        double firstNumber = getNumber(firstNumberAsString);

        // second number
        double secondNumber = getNumber(secondNumberAsString);

        if (operatorAsString.equals("+")) {
            // Returns first number plus second number.
            return firstNumber + secondNumber;
        } else if (operatorAsString.equals("-")) {
            // Returns first number minus second number.
            return firstNumber - secondNumber;
        } else if (operatorAsString.equals("*")) {
            // Returns first number multiply on second number.
            return firstNumber * secondNumber;
        } else if (operatorAsString.equals("/")) {
            // Returns first number divide on second number.
            if (secondNumber == 0)
                throw new IllegalArgumentException("Can't divide by " + ((int)secondNumber));
            return firstNumber / secondNumber;
        }
        else {
            throw new NumberFormatException("Can't convert character to operator: +, -, /, *");
        }
    }

    public static double getNumber(String numberAsString) {
        double number = 0;
        for (int i = 0; i < numberAsString.length(); i++) {
            char c = numberAsString.charAt(i);
            if (c < '0' || c > '9') {
                throw new NumberFormatException("Can't convert character to digit: " + c);
            }
            int digit = c - '0';
            number += digit * Math.pow(10, numberAsString.length() - i - 1);
        }
        return number;
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        String s = cin.nextLine();
        cin.close();
        try {
            double result = evaluateMathExpression(s);
            System.out.println(result);
        }
        catch(NumberFormatException e) {
            System.out.println("NumberFormatException " + e.getMessage());
        }
        catch(IllegalArgumentException e) {
            System.out.println("IllegalArgumentException " + e.getMessage());
        }
    }
}