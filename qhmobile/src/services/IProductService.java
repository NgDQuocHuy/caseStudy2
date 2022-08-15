package services;

import models.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAllProducts();

    void addProduct(Product newProduct);

    void editProduct(Product newProduct);

    void removeProduct(Long idProduct);

    List<Product> findProductByTitle(String title);

    boolean exitsById(Long idProduct);

    Product checkId(Long idProduct);

    void  updateQuantity(Long idProduct, Double quantity);
}
