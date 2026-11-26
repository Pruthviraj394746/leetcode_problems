class Solution {
    public boolean canAliceWin(int n) {
        int count = 0;
        int stones = 10;

        while (n >= stones) {
            n -= stones;
            stones--;
            count++;
        }

        return count % 2 == 1;
    }
}