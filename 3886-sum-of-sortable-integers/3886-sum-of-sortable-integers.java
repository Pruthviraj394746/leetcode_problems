class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int answer = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;
            int previousMax = Integer.MIN_VALUE;

            for (int start = 0; start < n; start += k) {
                int end = start + k;
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                int descents = 0;

                for (int i = start; i < end; i++) {
                    min = Math.min(min, nums[i]);
                    max = Math.max(max, nums[i]);

                    if (i > start && nums[i - 1] > nums[i]) {
                        descents++;
                    }
                }

                // Check the circular boundary
                if (nums[end - 1] > nums[start]) {
                    descents++;
                }

                if (descents > 1 || previousMax > min) {
                    valid = false;
                    break;
                }

                previousMax = max;
            }

            if (valid) {
                answer += k;
            }
        }

        return answer;
    }
}