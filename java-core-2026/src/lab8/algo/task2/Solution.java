package lab8.algo.task2;

public class Solution {

    public int distance(String first, String second) {
        int[] previous = new int[second.length() + 1];
        int[] current = new int[second.length() + 1];

        for (int j = 0; j <= second.length(); j++) {
            previous[j] = j;
        }

        for (int i = 1; i <= first.length(); i++) {
            current[0] = i;
            for (int j = 1; j <= second.length(); j++) {
                int replaceCost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;
                int delete = previous[j] + 1;
                int insert = current[j - 1] + 1;
                int replace = previous[j - 1] + replaceCost;

                current[j] = Math.min(Math.min(delete, insert), replace);
            }

            int[] temporary = previous;
            previous = current;
            current = temporary;
        }

        return previous[second.length()];
    }
}
