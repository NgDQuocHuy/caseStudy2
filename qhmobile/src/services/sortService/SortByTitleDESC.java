package services.sortService;

import models.Product;

import java.util.Comparator;

public class SortByTitleDESC implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getTitle().compareTo(o1.getTitle());
    }
}
