package week1;

import java.util.Scanner;

public class ExpressionEvaluator {

    // Implementation using recursive descent parser

    public ExpressionEvaluator(String expr) {
        expr_ = expr;
        position_ = -1;
    }

    void next() {
        curr_char_ = (++position_ < expr_.length()) ? expr_.charAt(position_) : -1;
    }

    boolean should_apply(char c) {
        while (curr_char_ == ' ') next();
        if (curr_char_ == c) {
            next();
            return true;
        }
        return false;
    }

    String evaluate() {
        try {
            double result = eval();
            return String.format("%.2f", result);
        }
        catch (IllegalArgumentException e) {
            return "Invalid mathematical expression.";
        }
    }

    double eval() {
        next();
        double x = evalExpr();
        if (position_ < expr_.length()) throw new IllegalArgumentException();
        return x;
    }

    double eval_term() {
        double result = eval_factor(true);
        while(true) {
            if (should_apply('*')) {
                result *= eval_factor(true);
            } else if (should_apply('/')) {
                double value = eval_factor(true);
                if (value == 0)
                    throw new IllegalArgumentException();
                result /= value;
            }
            else return result;
        }
    }

    double evalExpr() {
        double result = eval_term();
        while (true) {
            if (should_apply('+')) {
                result += eval_term();
            } else if (should_apply('-')) {
                result -= eval_term();
            }
            else return result;
        }
    }

    double eval_factor(boolean bracket) {
        if (should_apply('+')) throw new IllegalArgumentException();
        if (should_apply('-')) return -eval_factor(true);

        double result;
        int startPosition = position_;
        if (should_apply('(')) {
            result = evalExpr();
            if (bracket) {
                if (!should_apply(')')) {
                    throw new IllegalArgumentException();
                }
            }
        } else if ((curr_char_ >= '0' && curr_char_ <= '9') || curr_char_ == '.') {
            while ((curr_char_ >= '0' && curr_char_ <= '9') || curr_char_ == '.') next();
            result = Double.parseDouble(expr_.substring(startPosition, position_));
        } else if (curr_char_ >= 'a' && curr_char_ <= 'z') {
            while (curr_char_ >= 'a' && curr_char_ <= 'z') next();
            String func = expr_.substring(startPosition, position_);
            if (expr_.charAt(position_) != '(')
                throw new IllegalArgumentException();
            result = eval_factor(false);
            if (!should_apply(')')) {
                throw new IllegalArgumentException();
            }
            if (func.equals("sqrt")) {
                if (result < 0)
                    throw new IllegalArgumentException();
                result = Math.sqrt(result);
            }
            else if (func.equals("abs")) {
                result = Math.abs(result);
            }
            else throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException();
        }

        if (should_apply('^')) {
            if (result < 0)
                throw new IllegalArgumentException();
            result = Math.pow(result, eval_factor(true));
        }

        return result;
    }

    int position_;
    int curr_char_;
    String expr_;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        System.out.println(new ExpressionEvaluator(expression).evaluate());
        scanner.close();
    }

    public static void test1() {
        //abs((-5)
        // ((7+2)

    }


}
