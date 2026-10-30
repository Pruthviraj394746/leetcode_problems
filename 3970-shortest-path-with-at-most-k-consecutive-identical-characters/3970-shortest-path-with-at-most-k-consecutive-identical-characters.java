import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        // dist[node][run] = minimum cost reaching this state
        long[][] dist = new long[n][k + 1];

        for (long[] row : dist) {
            Arrays.fill(row, Long.MAX_VALUE);
        }

        // State: {cost, node, consecutive count}
        PriorityQueue<long[]> pq = new PriorityQueue<>(
            Comparator.comparingLong(a -> a[0])
        );

        dist[0][1] = 0;
        pq.offer(new long[]{0, 0, 1});

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();

            long cost = curr[0];
            int u = (int) curr[1];
            int run = (int) curr[2];

            if (cost != dist[u][run]) {
                continue;
            }

            if (u == n - 1) {
                return (int) cost;
            }

            for (int[] edge : graph[u]) {
                int v = edge[0];
                int weight = edge[1];

                int nextRun;

                if (labels.charAt(u) == labels.charAt(v)) {
                    nextRun = run + 1;
                } else {
                    nextRun = 1;
                }

                if (nextRun > k) {
                    continue;
                }

                long newCost = cost + weight;

                if (newCost < dist[v][nextRun]) {
                    dist[v][nextRun] = newCost;
                    pq.offer(new long[]{newCost, v, nextRun});
                }
            }
        }

        return -1;
    }
}