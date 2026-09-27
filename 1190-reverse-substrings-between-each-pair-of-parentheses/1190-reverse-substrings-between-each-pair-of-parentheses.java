import java.util.*;

class Solution {
    public String reverseParentheses(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {

            if (c == ')') {
                StringBuilder temp = new StringBuilder();

                // Take characters until '('
                while (stack.peek() != '(') {
                    temp.append(stack.pop());
                }

                // Remove '('
                stack.pop();

                // Add reversed characters back
                for (int i = 0; i < temp.length(); i++) {
                    stack.push(temp.charAt(i));
                }

            } else {
                stack.push(c);
            }
        }

        StringBuilder ans = new StringBuilder();

        while (!stack.isEmpty()) {
            ans.append(stack.pop());
        }

        return ans.reverse().toString();
    }
}