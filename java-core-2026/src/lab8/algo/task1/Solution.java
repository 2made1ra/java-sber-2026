package lab8.algo.task1;

import java.util.Arrays;

public class Solution {

    public int[] longestIncreasingSubsequence(int[] sequence) {
        if (sequence.length == 0) {
            return new int[0];
        }

        int[] length = new int[sequence.length];
        int[] previous = new int[sequence.length];
        Arrays.fill(length, 1);
        Arrays.fill(previous, -1);

        int bestEnd = 0;
        for (int current = 0; current < sequence.length; current++) {
            for (int candidate = 0; candidate < current; candidate++) {
                if (sequence[candidate] < sequence[current]
                        && length[candidate] + 1 > length[current]) {
                    length[current] = length[candidate] + 1;
                    previous[current] = candidate;
                }
            }

            if (length[current] > length[bestEnd]) {
                bestEnd = current;
            }
        }

        int[] answer = new int[length[bestEnd]];
        int position = answer.length - 1;
        int current = bestEnd;
        while (current != -1) {
            answer[position] = sequence[current];
            position--;
            current = previous[current];
        }

        return answer;
    }

    public String format(int[] sequence) {
        int[] answer = longestIncreasingSubsequence(sequence);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < answer.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(answer[i]);
        }

        return builder.toString();
    }
}
