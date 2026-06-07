package lab8.algo.task2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    public void returnsDistanceForExample() {
        assertEquals(3, solution.distance("ABCDEFGH", "ACDEXGIH"));
    }

    @Test
    public void returnsZeroForEqualStrings() {
        assertEquals(0, solution.distance("YANDEX", "YANDEX"));
        assertEquals(0, solution.distance("", ""));
    }

    @Test
    public void countsInsertionsAndDeletions() {
        assertEquals(3, solution.distance("", "ABC"));
        assertEquals(4, solution.distance("JAVA", ""));
        assertEquals(1, solution.distance("CODE", "CODER"));
    }

    @Test
    public void countsReplacementsTogetherWithOtherOperations() {
        assertEquals(3, solution.distance("SUNDAY", "SATURDAY"));
        assertEquals(3, solution.distance("KITTEN", "SITTING"));
    }
}
