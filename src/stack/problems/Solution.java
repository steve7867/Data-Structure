package stack.problems;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {

    // Integers should be single digit.
    public static String convertInfixToPostfix(String infix) {
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(infix);

        for (char c : infix.toCharArray()) {
            if (Character.isWhitespace(c))
                continue;

            switch (c) {
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    char pop = stack.pop();
                    while (pop != '(') {
                        sb.append(pop);
                        pop = stack.pop();
                    }
                    break;
                case '*':
                case '/':
                    while (stack.peek() == '+' || stack.peek() == '-')
                        sb.append(stack.pop());
                    stack.push(c);
                    break;
                case '+':
                case '-':
                    stack.push(c);
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }

        while (!stack.isEmpty())
            sb.append(stack.pop());

        return sb.toString();
    }

    // https://leetcode.com/problems/evaluate-reverse-polish-notation/
    // Evaluate the postfix notation
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String s : tokens) {
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();
                int res = 0;
                switch (s) {
                    case "+":
                        res = a + b;
                        break;
                    case "-":
                        res = a - b;
                        break;
                    case "*":
                        res = a * b;
                        break;
                    case "/":
                        res = a / b;
                        break;
                }

                stack.push(res);
            } else {
                stack.push(Integer.parseInt(s));
            }
        }

        return stack.pop();
    }
}
