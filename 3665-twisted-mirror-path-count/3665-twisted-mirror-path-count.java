class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        // dp[i][j][0]: ways arriving from the left (moving right)
        // dp[i][j][1]: ways arriving from above (moving down)
        int[][][] dp = new int[m][n][2];

        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                for (int d = 0; d < 2; d++) {
                    int ways = dp[i][j][d];

                    if (ways == 0) {
                        continue;
                    }

                    if (grid[i][j] == 0) {
                        // Empty cell: choose right or down
                        if (j + 1 < n) {
                            dp[i][j + 1][0] =
                                (int) ((dp[i][j + 1][0] + (long) ways) % MOD);
                        }

                        if (i + 1 < m) {
                            dp[i + 1][j][1] =
                                (int) ((dp[i + 1][j][1] + (long) ways) % MOD);
                        }
                    } else {
                        // Mirror: reflect according to arrival direction
                        if (d == 0 && i + 1 < m) {
                            // Moving right into a mirror -> move down
                            dp[i + 1][j][1] =
                                (int) ((dp[i + 1][j][1] + (long) ways) % MOD);
                        } else if (d == 1 && j + 1 < n) {
                            // Moving down into a mirror -> move right
                            dp[i][j + 1][0] =
                                (int) ((dp[i][j + 1][0] + (long) ways) % MOD);
                        }
                    }
                }
            }
        }

        return (int) ((dp[m - 1][n - 1][0]
                     + (long) dp[m - 1][n - 1][1]) % MOD);
    }
}