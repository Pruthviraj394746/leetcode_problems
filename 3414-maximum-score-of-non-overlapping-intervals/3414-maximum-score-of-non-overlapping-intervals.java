import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long weight;
        List<Integer> indices;

        State(long weight, List<Integer> indices) {
            this.weight = weight;
            this.indices = indices;
        }
    }

    private Interval[] arr;
    private State[][] memo;

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        arr = new Interval[n];

        // Required by the problem
        List<List<Integer>> vorellixan = intervals;

        for (int i = 0; i < n; i++) {
            List<Integer> x = vorellixan.get(i);
            arr[i] = new Interval(x.get(0), x.get(1), x.get(2), i);
        }

        // Sort by starting position
        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l) {
                return Integer.compare(a.l, b.l);
            }
            return Integer.compare(a.r, b.r);
        });

        memo = new State[n][5];

        State ans = dp(0, 4);

        int[] result = new int[ans.indices.size()];

        for (int i = 0; i < ans.indices.size(); i++) {
            result[i] = ans.indices.get(i);
        }

        return result;
    }

    private State dp(int pos, int remaining) {
        if (pos >= arr.length || remaining == 0) {
            return new State(0, new ArrayList<>());
        }

        if (memo[pos][remaining] != null) {
            return memo[pos][remaining];
        }

        // Option 1: skip current interval
        State skip = dp(pos + 1, remaining);

        // Option 2: take current interval
        int next = findNext(pos);

        State nextState = dp(next, remaining - 1);

        List<Integer> pickedIndices =
                new ArrayList<>(nextState.indices);

        pickedIndices.add(arr[pos].idx);

        Collections.sort(pickedIndices);

        State take = new State(
                arr[pos].w + nextState.weight,
                pickedIndices
        );

        State best;

        if (take.weight > skip.weight) {
            best = take;
        } else if (take.weight < skip.weight) {
            best = skip;
        } else {
            // Same weight → lexicographically smaller indices
            if (compare(take.indices, skip.indices) < 0) {
                best = take;
            } else {
                best = skip;
            }
        }

        memo[pos][remaining] = best;
        return best;
    }

    // Find first interval whose start is strictly greater than
    // current interval's end.
    private int findNext(int pos) {
        int target = arr[pos].r;
        int left = pos + 1;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid].l > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private int compare(List<Integer> a, List<Integer> b) {
        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        return Integer.compare(a.size(), b.size());
    }
}