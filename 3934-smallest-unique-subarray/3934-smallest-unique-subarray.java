import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int left = 1, right = n;
        int ans = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (hasUnique(nums, mid)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUnique(int[] nums, int k) {
        long base = 1000003L;
        long mod = 1000000007L;

        long power = 1;
        for (int i = 1; i < k; i++) {
            power = (power * base) % mod;
        }

        long hash = 0;

        for (int i = 0; i < k; i++) {
            hash = (hash * base + nums[i]) % mod;
        }

        Map<Long, Integer> freq = new HashMap<>();
        freq.put(hash, 1);

        for (int i = k; i < nums.length; i++) {
            hash = (hash - (nums[i - k] * power) % mod + mod) % mod;
            hash = (hash * base + nums[i]) % mod;

            freq.put(hash, freq.getOrDefault(hash, 0) + 1);
        }

        for (int count : freq.values()) {
            if (count == 1) {
                return true;
            }
        }

        return false;
    }
}