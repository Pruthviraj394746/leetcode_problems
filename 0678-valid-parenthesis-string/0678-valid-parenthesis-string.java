class Solution {
    public boolean checkValidString(String s) {
        int low = 0;
        int high = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                low++;
                high++;
            } 
            else if (c == ')') {
                low--;
                high--;
            } 
            else { // '*'
                low--;   // '*' acts as ')'
                high++;  // '*' acts as '('
            }

            // We cannot have negative minimum opens
            if (low < 0) {
                low = 0;
            }

            // Even maximum opens became negative
            if (high < 0) {
                return false;
            }
        }

        return low == 0;
    }
}