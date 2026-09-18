import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        // Find first and last occurrence of every character
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        List<int[]> intervals = new ArrayList<>();

        // Build the smallest valid interval for each character
        for (int c = 0; c < 26; c++) {
            if (first[c] == n) {
                continue;
            }

            int left = first[c];
            int right = last[c];

            boolean valid = true;

            for (int i = left; i <= right; i++) {
                int x = s.charAt(i) - 'a';

                // This character started before our interval
                if (first[x] < left) {
                    valid = false;
                    break;
                }

                // Need to include all occurrences of this character
                right = Math.max(right, last[x]);
            }

            if (valid) {
                intervals.add(new int[]{left, right});
            }
        }

        // Sort by ending position
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));

        List<String> result = new ArrayList<>();

        int previousEnd = -1;

        for (int[] interval : intervals) {
            int left = interval[0];
            int right = interval[1];

            if (left > previousEnd) {
                result.add(s.substring(left, right + 1));
                previousEnd = right;
            }
        }

        return result;
    }
}