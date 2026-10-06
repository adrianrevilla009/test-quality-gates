package lab;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

/** Policy: every quarantine must name a ticket, so quarantine cannot become a graveyard. */
class QuarantinePolicyTest {
    @Test
    void everyQuarantineHasAnIssue() {
        for (Class<?> c : new Class<?>[] {OrderTotalsTest.class}) {
            for (Method m : c.getDeclaredMethods()) {
                Quarantine q = m.getAnnotation(Quarantine.class);
                if (q != null) {
                    assertFalse(q.issue().isBlank(), m.getName() + " is quarantined without an issue");
                }
            }
        }
    }
}
