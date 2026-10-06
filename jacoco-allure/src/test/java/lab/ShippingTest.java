package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

@Feature("Shipping")
class ShippingTest {
    @Test
    @Description("Light parcels cost a flat 400")
    void light() {
        assertEquals(400, Shipping.cost(500));
    }

    @Test
    @Description("Medium parcels cost a flat 700")
    void medium() {
        assertEquals(700, Shipping.cost(501));
        assertEquals(700, Shipping.cost(2000));
    }

    @Test
    @Description("Heavy parcels add 100 per started extra kilo")
    void heavy() {
        assertEquals(800, Shipping.cost(2001));
        assertEquals(800, Shipping.cost(3000));
        assertEquals(900, Shipping.cost(3001));
    }

    @Test
    @Description("Non-positive weight is rejected")
    void invalid() {
        assertThrows(IllegalArgumentException.class, () -> Shipping.cost(0));
    }
}
