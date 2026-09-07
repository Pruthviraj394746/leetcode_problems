class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;

        long[] dp = new long[26];

        for (char c : s.toCharArray()) {
            int index = c - 'a';

            long total = 1;

            for (int i = 0; i < 26; i++) {
                total = (total + dp[i]) % MOD;
            }

            dp[index] = total;
        }

        long answer = 0;

        for (int i = 0; i < 26; i++) {
            answer = (answer + dp[i]) % MOD;
        }

        return (int) answer;
    }
}