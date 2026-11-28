
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int[] freq = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // Count all items whose factors are multiples
        int[] multiples = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                multiples[i] += freq[j];
            }
        }

        // Group profitable boosted purchases by price
        TreeMap<Integer, Long> deals = new TreeMap<>();

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            // A boosted purchase gives 2 items
            if (price >= 2L * minPrice) {
                continue;
            }

            int gain = multiples[factor] - 1;

            if (gain > 0) {
                deals.merge(price, (long) gain, Long::sum);
            }
        }

        long remaining = budget;
        long answer = 0;

        // Buy cheapest boosted purchases first
        for (Map.Entry<Integer, Long> entry : deals.entrySet()) {
            int price = entry.getKey();
            long count = entry.getValue();

            long buy = Math.min(count, remaining / price);

            answer += buy * 2;
            remaining -= buy * price;
        }

        // Spend remaining budget on cheapest regular items
        answer += remaining / minPrice;

        return (int) answer;
    }
}
