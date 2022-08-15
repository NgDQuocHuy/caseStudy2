package models;

import java.time.Instant;

public class Order {
    private Long idOrder;
    private String fullName;
    private String mobile;
    private String address;
    private Double grandTotal;
    private Instant timeCreatOrder;

    public Order() {}

    public Order(Long idOrder, String fullName, String mobile, String address, Instant timeCreatOrder) {
        this.idOrder = idOrder;
        this.fullName = fullName;
        this.mobile = mobile;
        this.address = address;
        this.timeCreatOrder = timeCreatOrder;
    }

    public static Order parseOrder(String rawOrder) {
        Order order = new Order();
        String[] array = rawOrder.split(",");
        order.setIdOrder(Long.parseLong(array[0]));
        order.setFullName(array[1]);
        order.setMobile(array[2]);
        order.setAddress(array[3]);
        order.setTimeCreatOrder(Instant.parse(array[4]));
        return order;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getTimeCreatOrder() {
        return timeCreatOrder;
    }

    public void setTimeCreatOrder(Instant timeCreatOrder) {
        this.timeCreatOrder = timeCreatOrder;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",
                idOrder,
                fullName,
                mobile,
                address,
                timeCreatOrder);
    }
}
