class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int insertions = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                open++;
            } else {
                // Check whether this is the first ')' of a pair
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    // Insert a missing ')'
                    insertions++;
                }

                if (open > 0) {
                    open--;
                } else {
                    // Insert a missing '('
                    insertions++;
                }
            }
        }

        // Each remaining '(' needs two ')'
        return insertions + open * 2;
    }
}