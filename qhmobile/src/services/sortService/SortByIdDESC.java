package services.sortService;

import models.Product;

import java.util.Comparator;

public class SortByIdDESC implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return (int) (o2.getIdProduct() - o1.getIdProduct());
    }
}
