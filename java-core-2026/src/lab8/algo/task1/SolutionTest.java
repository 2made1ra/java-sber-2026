package lab8.algo.task1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    public void returnsSubsequenceForExamples() {
        assertArrayEquals(
                new int[] {3, 5, 28},
                solution.longestIncreasingSubsequence(new int[] {3, 29, 5, 5, 28, 6})
        );
        assertArrayEquals(
                new int[] {4, 8, 10, 29, 58},
                solution.longestIncreasingSubsequence(new int[] {4, 8, 2, 6, 2, 10, 6, 29, 58, 9})
        );
    }

    @Test
    public void keepsSubsequenceStrictlyIncreasing() {
        assertArrayEquals(
                new int[] {1, 2, 3},
                solution.longestIncreasingSubsequence(new int[] {1, 2, 2, 3})
        );
    }

    @Test
    public void handlesNegativeAndDescendingValues() {
        assertArrayEquals(
                new int[] {-5, -3, 0},
                solution.longestIncreasingSubsequence(new int[] {-5, -3, -4, 0})
        );
        assertArrayEquals(
                new int[] {9},
                solution.longestIncreasingSubsequence(new int[] {9, 7, 5, 3})
        );
    }

    @Test
    public void formatsAnswerForOutput() {
        assertEquals("3 5 28", solution.format(new int[] {3, 29, 5, 5, 28, 6}));
    }
}
