package lab.orders.repo;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {
    private final Map<String, Long> totals = new HashMap<>();

    public void save(String id, long cents) {
        totals.put(id, cents);
    }

    public Long find(String id) {
        return totals.get(id);
    }
}
