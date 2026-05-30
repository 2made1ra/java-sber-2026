package lab7.algo.task1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    public void returnsMiddleElementForExamples() {
        assertEquals(2, solution.middleElement(1, 2, 3));
        assertEquals(0, solution.middleElement(1000, -1000, 0));
    }

    @Test
    public void worksForAnyOrder() {
        assertEquals(5, solution.middleElement(9, 5, 1));
        assertEquals(5, solution.middleElement(5, 9, 1));
        assertEquals(5, solution.middleElement(1, 9, 5));
    }

    @Test
    public void handlesEqualValues() {
        assertEquals(7, solution.middleElement(7, 7, 1));
        assertEquals(-4, solution.middleElement(-4, 10, -4));
        assertEquals(3, solution.middleElement(3, 3, 3));
    }
}
