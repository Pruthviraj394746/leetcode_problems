
class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int[][] valmorendi = items;

        int[] dp = new int[budget + 1];
        int minPrice = Integer.MAX_VALUE;

        int[] reward = new int[n];

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            minPrice = Math.min(minPrice, price);

            reward[i] = 1;

            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % factor == 0) {
                    reward[i]++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int price = items[i][1];

            for (int j = budget; j >= price; j--) {
                dp[j] = Math.max(
                    dp[j],
                    dp[j - price] + reward[i]
                );
            }
        }

        int ans = 0;

        for (int j = 0; j <= budget; j++) {
            int total = dp[j] + (budget - j) / minPrice;
            ans = Math.max(ans, total);
        }

        return ans;
    }
}
