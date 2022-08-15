package services;

import models.ItemOrder;
import utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderService implements IItemOrderService{
    private final static String PATH = "C:\\Users\\huynd\\OneDrive\\Desktop\\caseStudy2\\caseStudy2\\qhmobile\\data\\items.csv";
    private static ItemOrderService instanceItemOrder;

    public ItemOrderService() {}

    public static ItemOrderService instanceItemOrder() {
        if (instanceItemOrder == null) {
            instanceItemOrder = new ItemOrderService();
        }
        return instanceItemOrder;
    }

    @Override
    public List<ItemOrder> findAllItemOrder() {
        List<ItemOrder> itemOrders = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH);
        for (String record : records) {
            itemOrders.add(ItemOrder.parseItemOrder(record));
        }
        return itemOrders;
    }

    @Override
    public void addItemOrder(ItemOrder newItemOrder) {
        List<ItemOrder> itemOrders = findAllItemOrder();
        itemOrders.add(newItemOrder);
        CSVUtils.write(PATH, itemOrders);
    }

    @Override
    public void update(Long idOrder, Double price, Double grandTotal) {
        List<ItemOrder> itemOrders = findAllItemOrder();
        for (ItemOrder itemOrder : itemOrders) {
            if (itemOrder.getIdItemOrder().equals(idOrder)) {
                if (itemOrder.getPrice().equals(price)) {
                    itemOrder.setGrandTotal(grandTotal);
                    CSVUtils.write(PATH, itemOrders);
                    break;
                }
            }
        }
    }

    @Override
    public ItemOrder getOrderItemById(Long Id) {
        List<ItemOrder> itemOrders = findAllItemOrder();
        for (ItemOrder itemOrder: itemOrders) {
            if (itemOrder.getIdItemOrder().equals(Id)) {
                return itemOrder;
            }
        }
        return null;
    }
}
