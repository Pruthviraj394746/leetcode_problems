import java.util.*;

class Solution {
    public long[] distance(int[] nums) {
        int n = nums.length;
        long[] ans = new long[n];

        // value -> list of indices where it occurs
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        // Calculate distances for each group
        for (List<Integer> indices : map.values()) {
            long prefixSum = 0;

            for (int j = 0; j < indices.size(); j++) {
                int index = indices.get(j);

                // Distance from all previous indices
                ans[index] += (long) index * j - prefixSum;

                prefixSum += index;
            }

            long suffixSum = 0;

            for (int j = indices.size() - 1; j >= 0; j--) {
                int index = indices.get(j);
                int countAfter = indices.size() - 1 - j;

                // Distance from all following indices
                ans[index] += suffixSum - (long) index * countAfter;

                suffixSum += index;
            }
        }

        return ans;
    }
}