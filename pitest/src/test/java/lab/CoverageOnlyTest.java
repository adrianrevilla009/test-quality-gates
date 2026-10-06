package lab;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Executes every line but asserts almost nothing: great coverage, poor mutation score. */
class CoverageOnlyTest {
    @Test
    void runsEveryLineButChecksLittle() {
        assertTrue(OrderPricer.total(100, 1) > 0);
        assertTrue(OrderPricer.total(100, 10) > 0);
        try {
            OrderPricer.total(1, 0);
        } catch (IllegalArgumentException expected) {
            // swallowed on purpose
        }
    }
}
