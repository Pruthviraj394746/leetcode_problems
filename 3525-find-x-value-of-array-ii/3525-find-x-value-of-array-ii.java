class Solution {

    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            prod = 1;
            cnt = new int[k];
        }
    }

    int k;
    Node[] tree;

    private Node merge(Node a, Node b) {
        Node res = new Node(k);

        // Product of the complete segment
        res.prod = (a.prod * b.prod) % k;

        // Prefixes entirely inside the left segment
        for (int r = 0; r < k; r++) {
            res.cnt[r] = a.cnt[r];
        }

        // Prefixes that use all of left + part of right
        for (int r = 0; r < k; r++) {
            int newRemainder = (a.prod * r) % k;
            res.cnt[newRemainder] += b.cnt[r];
        }

        return res;
    }

    private Node makeNode(int value) {
        Node node = new Node(k);

        value %= k;
        node.prod = value;
        node.cnt[value] = 1;

        return node;
    }

    private void build(int idx, int left, int right, int[] nums) {
        if (left == right) {
            tree[idx] = makeNode(nums[left]);
            return;
        }

        int mid = left + (right - left) / 2;

        build(idx * 2, left, mid, nums);
        build(idx * 2 + 1, mid + 1, right, nums);

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private void update(int idx, int left, int right,
                        int pos, int value) {

        if (left == right) {
            tree[idx] = makeNode(value);
            return;
        }

        int mid = left + (right - left) / 2;

        if (pos <= mid) {
            update(idx * 2, left, mid, pos, value);
        } else {
            update(idx * 2 + 1, mid + 1, right, pos, value);
        }

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private Node query(int idx, int left, int right,
                       int ql, int qr) {

        if (ql <= left && right <= qr) {
            return tree[idx];
        }

        int mid = left + (right - left) / 2;

        if (qr <= mid) {
            return query(idx * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return query(idx * 2 + 1, mid + 1, right, ql, qr);
        }

        Node a = query(idx * 2, left, mid, ql, qr);
        Node b = query(idx * 2 + 1, mid + 1, right, ql, qr);

        return merge(a, b);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] answer = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Permanent update
            update(1, 0, n - 1, index, value);

            // Query nums[start ... n-1]
            Node result = query(1, 0, n - 1, start, n - 1);

            answer[q] = result.cnt[x];
        }

        return answer;
    }
}