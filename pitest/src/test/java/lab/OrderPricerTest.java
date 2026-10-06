package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OrderPricerTest {
    @Test
    void noDiscountBelowBulk() {
        assertEquals(900, OrderPricer.total(100, 9));
    }

    @Test
    void discountAtBoundary() {
        assertEquals(900, OrderPricer.total(100, OrderPricer.BULK_QTY));
    }

    @Test
    void discountAboveBoundary() {
        assertEquals(1980, OrderPricer.total(200, 11));
    }

    @Test
    void rejectsBadInput() {
        assertThrows(IllegalArgumentException.class, () -> OrderPricer.total(100, 0));
        assertThrows(IllegalArgumentException.class, () -> OrderPricer.total(-1, 1));
        assertEquals(0, OrderPricer.total(0, 1));
    }
}
