class Solution {
    private char[][] grid;
    private byte[][][] memo;
    private int m, n;

    public boolean hasValidPath(char[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }

        memo = new byte[m][n][(m + n) / 2 + 1];

        return dfs(0, 0, 0);
    }

    private boolean dfs(int i, int j, int balance) {
        if (i >= m || j >= n) {
            return false;
        }

        if (grid[i][j] == '(') {
            balance++;
        } else {
            balance--;
        }

        if (balance < 0) {
            return false;
        }

        int remaining = (m - 1 - i) + (n - 1 - j);

        if (balance > remaining) {
            return false;
        }

        if (i == m - 1 && j == n - 1) {
            return balance == 0;
        }

        if (balance >= memo[0][0].length) {
            return false;
        }

        if (memo[i][j][balance] != 0) {
            return memo[i][j][balance] == 1;
        }

        boolean result = dfs(i + 1, j, balance)
                      || dfs(i, j + 1, balance);

        memo[i][j][balance] = (byte) (result ? 1 : 2);

        return result;
    }
}