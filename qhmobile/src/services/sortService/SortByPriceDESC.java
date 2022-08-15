package services.sortService;

import models.Product;

import java.util.Comparator;

public class SortByPriceDESC implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return (int) (o2.getPrice() - o1.getPrice());
    }
}
