package views;

import menu.Menu;
import models.Product;
import services.ProductService;
import services.sortService.*;
import utils.AppUtils;
import utils.InstantUtils;

import java.util.List;
import java.util.Scanner;

public class SortView {
    private static ProductService productService = new ProductService();
    static Scanner input = new Scanner(System.in);

    public static void menuSort() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Sản Phẩm ◄                  ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     Sắp xếp theo ID sản phẩm                  ║");
            System.out.println("║       2.     Sắp xếp theo tên sản phẩm                 ║");
            System.out.println("║       3.     Sắp xếp theo giá sản phẩm                 ║");
            System.out.println("║       0.     Quay lại quản lý sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortIdProduct();
                        break;
                    case "2":
                        sortTitle();
                        break;
                    case "3":
                        sortPrice();
                        break;
                    case "0":
                        Menu.menuManageProduct();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void sortIdProduct() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Theo ID ◄                   ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     ID sản phẩm tăng dần                      ║");
            System.out.println("║       2.     ID sản phẩm giảm dần                      ║");
            System.out.println("║       0.     Quay lại xắp xếp sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortIdASC();
                        break;
                    case "2":
                        sortIdDESC();
                        break;
                    case "0":
                        menuSort();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void sortTitle() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Theo Tên ◄                  ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     Tên sản phẩm A -> Z                       ║");
            System.out.println("║       2.     Tên sản phẩm Z -> A                       ║");
            System.out.println("║       0.     Quay lại xắp xếp sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortTitleASC();
                        break;
                    case "2":
                        sortTitleDESC();
                        break;
                    case "0":
                        menuSort();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void sortPrice() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Theo Giá ◄                  ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     Giá sản phẩm tăng dần                     ║");
            System.out.println("║       2.     Giảm sản phẩm giảm dần                    ║");
            System.out.println("║       0.     Quay lại xắp xếp sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortPriceASC();
                        break;
                    case "2":
                        sortPriceDESC();
                        break;
                    case "0":
                        menuSort();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void sortIdASC() {
        List<Product> products = productService.findAllProducts();
        SortByIdASC sortByIdASC = new SortByIdASC();
        products.sort(sortByIdASC);
        showSortList(products);
        sortIdProduct();
    }

    public static void sortIdDESC() {
        List<Product> products = productService.findAllProducts();
        SortByIdDESC sortByIdDESC = new SortByIdDESC();
        products.sort(sortByIdDESC);
        showSortList(products);
        sortIdProduct();
    }

    public static void sortTitleASC() {
        List<Product> products = productService.findAllProducts();
        SortByTitleASC sortByTitleASC = new SortByTitleASC();
        products.sort(sortByTitleASC);
        showSortList(products);
        sortTitle();
    }

    public static void sortTitleDESC() {
        List<Product> products = productService.findAllProducts();
        SortByTitleDESC sortByTitleDESC = new SortByTitleDESC();
        products.sort(sortByTitleDESC);
        showSortList(products);
        sortTitle();
    }

    public static void sortPriceASC() {
        List<Product> products = productService.findAllProducts();
        SortByPriceASC sortByPriceASC = new SortByPriceASC();
        products.sort(sortByPriceASC);
        showSortList(products);
        sortPrice();
    }

    public static void sortPriceDESC() {
        List<Product> products = productService.findAllProducts();
        SortByPriceDESC sortByPriceDESC = new SortByPriceDESC();
        products.sort(sortByPriceDESC);
        showSortList(products);
        sortPrice();
    }

    public static void showSortList(List<Product> products) {
        System.out.println();
        System.out.println("══════════════════════════════════════ Danh Sách Sản Phẩm ═════════════════════════════════════════");
        System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
        System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
        for (Product product : products) {
            System.out.printf("%-25s %-20s %-20s %-20s\n",
                    product.getIdProduct(),
                    product.getTitle(),
                    InstantUtils.quantityProducts(product.getQuantity()),
                    InstantUtils.doubleToVND(product.getPrice()));
        }
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
        int choice;
        do {
            System.out.println("Nhấn 0 để quay lại sắp xếp sản phẩm.");
            System.out.print("=> ");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
    }
}
