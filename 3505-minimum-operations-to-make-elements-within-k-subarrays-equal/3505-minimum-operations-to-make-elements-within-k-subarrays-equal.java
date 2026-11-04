
import java.util.*;

class Solution {

    class MedianWindow {
        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        int leftSize = 0, rightSize = 0;
        long leftSum = 0, rightSum = 0;

        void add(int val) {
            if (left.isEmpty() || val <= left.lastKey()) {
                left.merge(val, 1, Integer::sum);
                leftSize++;
                leftSum += val;
            } else {
                right.merge(val, 1, Integer::sum);
                rightSize++;
                rightSum += val;
            }
            balance();
        }

        void remove(int val) {
            if (left.containsKey(val)) {
                removeOne(left, val);
                leftSize--;
                leftSum -= val;
            } else {
                removeOne(right, val);
                rightSize--;
                rightSum -= val;
            }
            balance();
        }

        void removeOne(TreeMap<Integer, Integer> map, int val) {
            int count = map.get(val);
            if (count == 1) map.remove(val);
            else map.put(val, count - 1);
        }

        void balance() {
            while (leftSize > rightSize + 1) {
                int val = left.lastKey();
                removeOne(left, val);
                leftSize--;
                leftSum -= val;

                right.merge(val, 1, Integer::sum);
                rightSize++;
                rightSum += val;
            }

            while (leftSize < rightSize) {
                int val = right.firstKey();
                removeOne(right, val);
                rightSize--;
                rightSum -= val;

                left.merge(val, 1, Integer::sum);
                leftSize++;
                leftSum += val;
            }
        }

        long getCost() {
            long median = left.lastKey();

            return median * leftSize - leftSum
                 + rightSum - median * rightSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;

        long[] cost = new long[m];

        MedianWindow window = new MedianWindow();

        // Calculate first window
        for (int i = 0; i < x; i++) {
            window.add(nums[i]);
        }

        cost[0] = window.getCost();

        // Sliding window
        for (int i = x; i < n; i++) {
            window.remove(nums[i - x]);
            window.add(nums[i]);

            cost[i - x + 1] = window.getCost();
        }

        // DP: minimum cost using j windows
        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        Arrays.fill(dp[0], 0);

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {

                // Skip current element
                dp[j][i] = dp[j][i - 1];

                // Select window ending at i - 1
                if (i >= x && dp[j - 1][i - x] != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
