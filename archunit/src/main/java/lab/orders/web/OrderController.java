package lab.orders.web;

import lab.orders.service.OrderService;

public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    public String get(String id) {
        return "total=" + service.total(id);
    }
}
