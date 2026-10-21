class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();

        if (s.charAt(n - 1) == '1') {
            return false;
        }

        boolean[] dp = new boolean[n];
        dp[0] = true;

        int reachable = 0;

        for (int i = 1; i < n; i++) {
            int add = i - minJump;
            int remove = i - maxJump - 1;

            if (add >= 0 && dp[add]) {
                reachable++;
            }

            if (remove >= 0 && dp[remove]) {
                reachable--;
            }

            dp[i] = s.charAt(i) == '0' && reachable > 0;
        }

        return dp[n - 1];
    }
}