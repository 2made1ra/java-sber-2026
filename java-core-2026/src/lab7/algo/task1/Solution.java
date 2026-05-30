package lab7.algo.task1;

public class Solution {

    public int middleElement(int a, int b, int c) {
        int min = Math.min(a, Math.min(b, c));
        int max = Math.max(a, Math.max(b, c));

        return a + b + c - min - max;
    }
}
