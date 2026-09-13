import java.util.*;

class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;

        List<int[]> ones1 = new ArrayList<>();
        List<int[]> ones2 = new ArrayList<>();

        // Store positions of 1s in both images
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) {
                    ones1.add(new int[]{i, j});
                }

                if (img2[i][j] == 1) {
                    ones2.add(new int[]{i, j});
                }
            }
        }

        Map<Integer, Integer> count = new HashMap<>();
        int answer = 0;

        // Compare every 1 in img1 with every 1 in img2
        for (int[] a : ones1) {
            for (int[] b : ones2) {

                int dx = a[0] - b[0];
                int dy = a[1] - b[1];

                // Encode (dx, dy) into one integer
                int key = dx * 100 + dy;

                int frequency = count.getOrDefault(key, 0) + 1;
                count.put(key, frequency);

                answer = Math.max(answer, frequency);
            }
        }

        return answer;
    }
}