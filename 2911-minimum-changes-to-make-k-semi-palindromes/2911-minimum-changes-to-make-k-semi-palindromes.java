
import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {

        int n = s.length();

        // Precompute divisors
        List<Integer>[] factors = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            factors[i] = new ArrayList<>();
        }

        for (int d = 1; d < n; d++) {
            for (int i = d * 2; i <= n; i += d) {
                factors[i].add(d);
            }
        }

        // Cost to convert each substring into a semi-palindrome
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int len = j - i + 1;
                int minCost = n;

                for (int d : factors[len]) {

                    int changes = 0;

                    for (int offset = 0; offset < d; offset++) {

                        int left = i + offset;
                        int right = j - d + 1 + offset;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    minCost = Math.min(minCost, changes);
                }

                cost[i][j] = minCost;
            }
        }

        // DP
        int INF = 1000000;
        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int parts = 1; parts <= k; parts++) {

                for (int j = i - 2; j >= 0; j--) {

                    if (dp[j][parts - 1] == INF) {
                        continue;
                    }

                    dp[i][parts] = Math.min(
                        dp[i][parts],
                        dp[j][parts - 1] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
