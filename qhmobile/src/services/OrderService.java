package services;

import models.Order;
import utils.CSVUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService{
    public final static String PATH = "C:\\Users\\huynd\\OneDrive\\Desktop\\caseStudy2\\caseStudy2\\qhmobile\\data\\orders.csv";
    private static OrderService instanceOrder;

    public OrderService() {};

    public static OrderService getInstanceOrder() {
        if (instanceOrder == null) {
            instanceOrder = new OrderService();
        }
        return instanceOrder;
    }

    @Override
    public List<Order> findAllOrder() {
        List<Order> orders = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH);
        for (String record : records) {
            orders.add(Order.parseOrder(record));
        }
        return orders;
    }

    @Override
    public void add(Order newOrder) {
        List<Order> orders = findAllOrder();
        newOrder.setTimeCreatOrder(Instant.now());
        orders.add(newOrder);
        CSVUtils.write(PATH, orders);
    }

    @Override
    public void editOrder(Order newOrder) {
        List<Order> orders = findAllOrder();
        for (Order oldOrder : orders) {
            if (oldOrder.getIdOrder().equals(newOrder.getIdOrder())) {
                String fullName = newOrder.getFullName();
                if (fullName != null && !fullName.isEmpty()) {
                    oldOrder.setFullName(fullName);
                }
                String mobile = newOrder.getMobile();
                if (mobile != null && !mobile.isEmpty()) {
                    oldOrder.setMobile(mobile);
                }
                String address = newOrder.getAddress();
                if (address != null && !address.isEmpty()) {
                    oldOrder.setAddress(address);
                }
                oldOrder.setTimeCreatOrder(Instant.now());
                CSVUtils.write(PATH, orders);
                break;
            }
        }
    }

    @Override
    public void editOrder() {
        List<Order> orders = findAllOrder();
        CSVUtils.write(PATH, orders);
    }

    @Override
    public Order findOrderById(Long idOrder) {
        List<Order> orders = findAllOrder();
        for (Order order : orders) {
            if (order.getIdOrder().equals(idOrder)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public boolean exitsOrderId(Long idOrder) {
        return findOrderById(idOrder) != null;
    }

    @Override
    public List<Order> findOrderByUserId(Long idOrder) {
        List<Order> order =  new ArrayList<>();
        for (Order orders: findAllOrder()) {
            if (orders.getIdOrder().equals(idOrder)) {
                order.add(orders);
            }
            return order;
        }
        return null;
    }
}
