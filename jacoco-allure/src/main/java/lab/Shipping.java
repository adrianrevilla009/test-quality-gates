package lab;

/** Tiny Orders domain: shipping cost in cents by weight band. */
public final class Shipping {
    private Shipping() {}

    public static long cost(int grams) {
        if (grams <= 0) {
            throw new IllegalArgumentException("grams must be > 0");
        }
        if (grams <= 500) {
            return 400;
        }
        if (grams <= 2000) {
            return 700;
        }
        return 700 + 100L * ((grams - 2000 + 999) / 1000);
    }
}
