package lab7.algo.task2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    public void returnsRoutesForExamples() {
        assertEquals(1L, solution.countRoutes(3, 2));
        assertEquals(293930L, solution.countRoutes(31, 34));
    }

    @Test
    public void countsZeroLengthRoute() {
        assertEquals(1L, solution.countRoutes(1, 1));
    }

    @Test
    public void returnsZeroWhenFinishIsUnreachable() {
        assertEquals(0L, solution.countRoutes(1, 2));
        assertEquals(0L, solution.countRoutes(2, 2));
        assertEquals(0L, solution.countRoutes(3, 3));
    }

    @Test
    public void countsSingleAndMultipleRoutes() {
        assertEquals(1L, solution.countRoutes(2, 3));
        assertEquals(1L, solution.countRoutes(3, 2));
        assertEquals(2L, solution.countRoutes(4, 4));
        assertEquals(3L, solution.countRoutes(5, 6));
    }
}
