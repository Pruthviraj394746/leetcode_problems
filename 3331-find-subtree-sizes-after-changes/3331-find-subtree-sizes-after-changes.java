import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        int[] newParent = new int[n];
        Arrays.fill(newParent, -1);

        int[] last = new int[26];
        Arrays.fill(last, -1);

        build(0, -1, children, newParent, last, s);

        List<Integer>[] tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[newParent[i]].add(i);
        }

        int[] ans = new int[n];
        dfs(0, tree, ans);

        return ans;
    }

    private void build(int node, int ancestor,
                       List<Integer>[] children,
                       int[] newParent, int[] last, String s) {

        int c = s.charAt(node) - 'a';
        int previous = last[c];

        newParent[node] = (previous == -1) ? ancestor : previous;

        last[c] = node;

        for (int child : children[node]) {
            build(child, node, children, newParent, last, s);
        }

        last[c] = previous;
    }

    private int dfs(int node, List<Integer>[] tree, int[] ans) {
        int size = 1;

        for (int child : tree[node]) {
            size += dfs(child, tree, ans);
        }

        ans[node] = size;
        return size;
    }
}