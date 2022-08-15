package services;

import models.ItemOrder;

import java.util.List;

public interface IItemOrderService {
    List<ItemOrder> findAllItemOrder();

    void addItemOrder(ItemOrder newItemOrder);

    void update(Long idOrder, Double price, Double grandTotal);

    ItemOrder getOrderItemById(Long Id);
}
