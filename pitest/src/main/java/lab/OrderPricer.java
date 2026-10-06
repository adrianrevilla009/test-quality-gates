package lab;

/** Tiny Orders domain: price an order with a bulk discount. */
public final class OrderPricer {
    public static final int BULK_QTY = 10;

    private OrderPricer() {}

    /** Total in cents; 10% off when quantity reaches BULK_QTY. */
    public static long total(long unitCents, int qty) {
        if (qty <= 0 || unitCents < 0) {
            throw new IllegalArgumentException("qty must be > 0 and price >= 0");
        }
        long gross = unitCents * qty;
        return qty >= BULK_QTY ? gross - gross / 10 : gross;
    }
}
