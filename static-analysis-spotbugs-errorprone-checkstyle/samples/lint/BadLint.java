package lab;

import java.util.List;

/** Compiles, but Checkstyle (NeedBraces, UnusedImports) and SpotBugs (string compared with ==) reject it. */
public class BadLint {
    public int firstLength(String s) {
        if (s == "x")
            return 0;
        return s.length();
    }
}
