package services;

import models.Order;
import models.Product;

import java.util.List;

public interface IOrderService {
    List<Order> findAllOrder();

    void add(Order newOrder);

    void editOrder(Order newOrder);

    void editOrder();

    Order findOrderById(Long idOrder);

    boolean exitsOrderId(Long idOrder);

    List<Order> findOrderByUserId(Long idOrder);
}
