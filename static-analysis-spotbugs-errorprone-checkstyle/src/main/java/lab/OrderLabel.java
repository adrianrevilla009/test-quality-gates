package lab;

import java.util.Locale;
import java.util.Objects;

/** Tiny Orders domain: builds a display label for an order. */
public final class OrderLabel {
    private OrderLabel() {}

    public static String of(String customer, int lines) {
        Objects.requireNonNull(customer, "customer");
        if (lines < 0) {
            throw new IllegalArgumentException("lines must be >= 0");
        }
        return customer.trim().toUpperCase(Locale.ROOT) + " (" + lines + ")";
    }
}
