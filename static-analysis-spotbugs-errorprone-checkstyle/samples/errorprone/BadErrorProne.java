package lab;

/** Error Prone rejects this at compile time: EqualsIncompatibleType (String vs Integer). */
public final class BadErrorProne {
    public boolean same(String a, Integer b) {
        return a.equals(b);
    }
}
