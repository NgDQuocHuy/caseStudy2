package models;

import java.util.List;
import java.util.Objects;

public class Product{
    private Long idProduct;
    private String title;
    private Double price;
    private Double quantity;

    public List<Product> products;
    public Product() {}

    public Product(Long id, String title, Double price, Double quantity) {
        this.idProduct = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product parseProduct(String rawProduct) {
        String[] array = rawProduct.split(",");
        Product product = new Product();
        product.setIdProduct(Long.parseLong(array[0]));
        product.setTitle(array[1]);
        product.setPrice(Double.parseDouble(array[2]));
        product.setQuantity(Double.parseDouble(array[3]));
        return product;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s",
                idProduct,
                title,
                price,
                quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return idProduct.equals(product.idProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct);
    }
}
