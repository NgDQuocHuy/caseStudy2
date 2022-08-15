package services;

import models.Product;
import utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {
    public final static String PATH = "C:\\Users\\huynd\\OneDrive\\Desktop\\caseStudy2\\qhmobile\\data\\products.csv";
    private static ProductService instanceProduct;

    public static ProductService getInstanceProduct() {
        if (instanceProduct == null) {
            instanceProduct = new ProductService();
        }
        return instanceProduct;
    }

    public ProductService() {
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH);
        for (String record : records) {
            products.add(Product.parseProduct(record));
        }
        return products;
    }

    @Override
    public void addProduct(Product newProduct) {
        List<Product> products = findAllProducts();
        products.add(newProduct);
        CSVUtils.write(PATH, products);
    }

    @Override
    public void editProduct(Product newProduct) {
        List<Product> products = findAllProducts();
        for (Product oldProduct : products) {
            if (oldProduct.getIdProduct().equals(newProduct.getIdProduct())) {
                String title = newProduct.getTitle();
                if (title != null && !title.isEmpty()) {
                    oldProduct.setTitle(newProduct.getTitle());
                }
                Double quantity = newProduct.getQuantity();
                if (quantity != null) {
                    oldProduct.setQuantity(quantity);
                }
                Double price = newProduct.getPrice();
                if (price != null) {
                    oldProduct.setPrice(price);
                }
                CSVUtils.write(PATH, products);
                break;
            }
        }
    }

    @Override
    public void removeProduct(Long idProduct) {
        List<Product> products = findAllProducts();
        products.removeIf(id -> id.getIdProduct().equals(idProduct));
        CSVUtils.write(PATH, products);
    }

    @Override
    public List<Product> findProductByTitle(String title) {
        List<Product> products = findAllProducts();
        List<Product> listFind = new ArrayList<>();
        if (title != null) {
            for (Product oldProduct : products) {
                if (oldProduct.getTitle().toLowerCase().contains(title)) {
                    listFind.add(oldProduct);
                }
            }
        }
        return listFind;
    }

    @Override
    public boolean exitsById(Long idProduct) {
        return checkId(idProduct) != null;
    }

    @Override
    public Product checkId(Long idProduct) {
        List<Product> products = findAllProducts();
        for (Product product : products) {
            if (product.getIdProduct().equals(idProduct))
                return product;
        }
        return null;
    }

    @Override
    public void updateQuantity(Long idProduct, Double quantity) {
        List<Product> products = findAllProducts();
        for (Product oldProduct : products) {
            if (oldProduct.getIdProduct().equals(idProduct)) {
                Double oldQuantity = oldProduct.getQuantity();
                if (oldQuantity >= quantity) {
                    oldProduct.setQuantity(oldQuantity - quantity);
                    CSVUtils.write(PATH, products);
                    break;
                }
            }
        }
    }
}
