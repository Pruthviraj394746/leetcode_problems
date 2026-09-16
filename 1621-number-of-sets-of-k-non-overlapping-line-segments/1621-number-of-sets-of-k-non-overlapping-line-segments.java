class Solution {
    private static final int MOD = 1_000_000_007;

    public int numberOfSets(int n, int k) {
        int[][] notDrawing = new int[n + 1][k + 1];
        int[][] drawing = new int[n + 1][k + 1];

        // Before processing points, we have one way
        // to draw 0 segments.
        notDrawing[1][0] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= k; j++) {

                // We are not currently extending a segment.
                notDrawing[i][j] =
                    (notDrawing[i - 1][j] + drawing[i - 1][j]) % MOD;

                // Continue an existing segment
                drawing[i][j] = drawing[i - 1][j];

                if (j > 0) {
                    // Start a new segment
                    drawing[i][j] += notDrawing[i - 1][j - 1];
                    drawing[i][j] %= MOD;

                    // Start/continue from an existing segment
                    drawing[i][j] += drawing[i - 1][j - 1];
                    drawing[i][j] %= MOD;
                }
            }
        }

        return (notDrawing[n][k] + drawing[n][k]) % MOD;
    }
}