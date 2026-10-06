package lab.orders.service;

import lab.orders.repo.OrderRepository;

public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public void place(String id, long cents) {
        repo.save(id, cents);
    }

    public Long total(String id) {
        return repo.find(id);
    }
}
