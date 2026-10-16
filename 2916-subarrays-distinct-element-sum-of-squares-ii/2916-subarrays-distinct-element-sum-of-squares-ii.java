class Solution {
    private static final long MOD = 1_000_000_007L;

    private int n;
    private long[] sum;
    private long[] sumSq;
    private long[] lazy;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        sumSq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]] + 1;

            // Increase distinct counts for affected subarrays
            update(1, 0, n - 1, left, i);

            // Sum of squares for all subarrays ending at i
            ans = (ans + sumSq[1]) % MOD;

            last[nums[i]] = i;
        }

        return (int) ans;
    }

    private void apply(int node, int left, int right, long val) {
        long len = right - left + 1L;

        sumSq[node] = (
            sumSq[node]
            + 2L * val % MOD * sum[node] % MOD
            + val * val % MOD * len % MOD
        ) % MOD;

        sum[node] = (sum[node] + val * len) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }

    private void pushDown(int node, int left, int right) {
        if (lazy[node] == 0 || left == right) {
            return;
        }

        int mid = left + (right - left) / 2;
        long val = lazy[node];

        apply(node * 2, left, mid, val);
        apply(node * 2 + 1, mid + 1, right, val);

        lazy[node] = 0;
    }

    private void update(
        int node, int left, int right, int ql, int qr
    ) {
        if (ql > right || qr < left) {
            return;
        }

        if (ql <= left && right <= qr) {
            apply(node, left, right, 1);
            return;
        }

        pushDown(node, left, right);

        int mid = left + (right - left) / 2;

        update(node * 2, left, mid, ql, qr);
        update(node * 2 + 1, mid + 1, right, ql, qr);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sumSq[node] = (sumSq[node * 2] + sumSq[node * 2 + 1]) % MOD;
    }
}