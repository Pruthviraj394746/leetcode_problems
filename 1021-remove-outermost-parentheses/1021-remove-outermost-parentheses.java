class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder ans = new StringBuilder();
        int depth = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                // Add '(' only if it's not the outermost one
                if (depth > 0) {
                    ans.append(c);
                }
                depth++;
            } else {
                depth--;

                // Add ')' only if it's not the outermost one
                if (depth > 0) {
                    ans.append(c);
                }
            }
        }

        return ans.toString();
    }
}