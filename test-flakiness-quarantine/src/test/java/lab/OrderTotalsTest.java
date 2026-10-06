package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class OrderTotalsTest {
    private static final AtomicInteger ATTEMPTS = new AtomicInteger();

    @Test
    void stableSum() {
        assertEquals(30, 10 + 20);
    }

    /** Simulates a race: fails on the first attempt of every JVM, passes on retry. */
    @Test
    @Quarantine(issue = "ORD-123")
    void flakyAuditTimestamp() {
        assertTrue(ATTEMPTS.incrementAndGet() >= 2, "audit row not yet visible");
    }

    /** Always fails: quarantined so it cannot block merges, but it keeps showing up in the nightly report. */
    @Test
    @Quarantine(issue = "ORD-456")
    void brokenCurrencyRounding() {
        assertEquals(1, 2, "known bug ORD-456");
    }
}
