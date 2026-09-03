import java.util.List;

class Solution {
    public boolean uniformArray(int[] A) {
        if (A == null || A.length == 0) return true; // Safety check
        
        int xmin = A[0];
        boolean odd = false;
        
        for (int x : A) {
            xmin = Math.min(xmin, x);
            odd |= (x & 1) == 1;
        }
        
        return ((xmin & 1) == 1) == odd;
    }
}
