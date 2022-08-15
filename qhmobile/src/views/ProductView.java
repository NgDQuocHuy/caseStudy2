package views;

import menu.Menu;
import models.Product;
import services.IProductService;
import services.ProductService;
import utils.AppUtils;
import utils.InstantUtils;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    public List<Product> products;
    private static ProductService productService = new ProductService();
    private static final Scanner input = new Scanner(System.in);

    public void addProduct() {
        boolean flagProduct = true;
        do {
            System.out.println("══════════► Thêm Sản Phẩm ◄══════════");
            Long productId = System.currentTimeMillis() / 1000;
            String title = inputTitle(ChoiceStatus.ADD);
            Double price = inputPrice(ChoiceStatus.ADD);
            Double quantity = inputQuantity(ChoiceStatus.ADD);
            Product product = new Product(productId, title, price, quantity);
            productService.addProduct(product);
            System.out.println("Đã thêm thành công.");
            showProductListShow();
            Menu.menuManageProduct();
        } while (!flagProduct);
    }

    public Long inputIdProduct(ChoiceStatus status) {
        Long idProduct;
        switch (status) {
            case EDIT:
            case REMOVE:
                System.out.println("Nhập ID sản phẩm cần xóa:");
                break;
        }
        System.out.print("=> ");
        boolean flagInputID = true;
        do {
            idProduct = AppUtils.retryParseLong();
            boolean exits = productService.exitsById(idProduct);
            switch (status) {
                case EDIT:
                case REMOVE:
                    if (!exits) {
                        System.out.println("Không tìm thấy ID, vui lòng nhập lại:");
                        System.out.print("=> ");
                    }
                    flagInputID = !exits;
                    break;
            }
        } while (flagInputID);
        return idProduct;
    }

    public static String inputTitle(ChoiceStatus status) {
        String title;
        switch (status) {
            case ADD:
                System.out.println("Nhập tên sản phẩm: ");
                break;
            case EDIT:
                System.out.println("Nhập tên sản phẩm muốn sửa: ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            title = input.nextLine().trim();
            boolean exits = (!title.isEmpty());
            switch (status) {
                case ADD:
                case EDIT:
                    if (!exits) {
                        System.out.println("Bạn nhập sai định dạng, vui lòng nhập lại:");
                        System.out.print("=> ");
                    }
                    flagInputTitle = !exits;
                    break;
            }
        } while (flagInputTitle);
        return title;
    }

    public static Double inputPrice(ChoiceStatus status) {
        Double price;
        switch (status) {
            case ADD:
                System.out.println("Nhập giá sản phẩm: ");
                break;
            case EDIT:
                System.out.println("Nhập giá sản phẩm muốn sửa: ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            price = AppUtils.retryParseDouble();
            boolean exits = (price >= 1000);
            switch (status) {
                case ADD:
                case EDIT:
                case REMOVE:
                    if (!exits) {
                        System.out.println("Bạn nhập sai định dạng (Giá tiền phải lớn hơn 1000)");
                        System.out.print("=> ");
                    }
                    flagInputTitle = !exits;
                    break;
            }
        } while (flagInputTitle);
        return price;
    }

    public static Double inputQuantity(ChoiceStatus status) {
        Double quantity;
        switch (status) {
            case ADD:
                System.out.println("Nhập số lượng sản phẩm: ");
                break;
            case EDIT:
                System.out.println("Nhập số lượng sản phẩm muốn sửa: ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            quantity = AppUtils.retryParseDouble();
            boolean exits = (quantity > 0);
            switch (status) {
                case ADD:
                case EDIT:
                case REMOVE:
                    if (!exits) {
                        System.out.println("Bạn nhập sai định dạng (Số lượng phải lớn hơn 1)");
                        System.out.print("=> ");
                    }
                    flagInputTitle = !exits;
                    break;
            }
        } while (flagInputTitle);
        return quantity;
    }

    public void editProduct() {
        try {
            showProductList();
            System.out.println("Nhập ID sản phẩm cần sửa");
            System.out.print("=> ");
            Long id = AppUtils.retryParseLong();
            if (productService.exitsById(id)) {
                boolean flagUpdate = true;
                System.out.println();
                System.out.println("╔═════════════════════════════════════════╗");
                System.out.println("║             ► Sửa Sản Phẩm ◄            ║");
                System.out.println("╠═════════════════════════════════════════╣");
                System.out.println("║       1.     Sửa tên sản phẩm           ║");
                System.out.println("║       2.     Sửa giá sản phẩm           ║");
                System.out.println("║       3.     Sửa số lượng sản phẩm      ║");
                System.out.println("║       0.     Quay lại quản lý sản phẩm  ║");
                System.out.println("╚═════════════════════════════════════════╝");
                System.out.println("Chọn chức năng: ");
                System.out.print("=> ");
                Product newProduct = new Product();
                newProduct.setIdProduct(id);
                do {
                    String choice = input.nextLine();
                    switch (choice) {
                        case "1":
                            editTitle(newProduct);
                            break;
                        case "2":
                            editPrice(newProduct);
                            break;
                        case "3":
                            editQuantity(newProduct);
                            break;
                        case "0":
                            Menu.menuManageProduct();
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                            System.out.print("=> ");
                            flagUpdate = false;
                    }
                } while (!flagUpdate);
            } else {
                System.out.println("Không tìm thấy ID sản phầm.");
                ContinueOrExist();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void editTitle(Product newProduct) {
        String title = inputTitle(ChoiceStatus.EDIT);
        newProduct.setTitle(title);
        productService.editProduct(newProduct);
        System.out.println("Tên sản phẩm đã được cập nhật.");
        showProductList();
        ContinueOrExist();
    }

    private void editPrice(Product newProduct) {
        Double price = inputPrice(ChoiceStatus.EDIT);
        newProduct.setPrice(price);
        productService.editProduct(newProduct);
        System.out.println("Giá sản phẩm đã được cập nhật.");
        showProductList();
        ContinueOrExist();
    }

    private void editQuantity(Product newProduct) {
        Double quantity = inputQuantity(ChoiceStatus.EDIT);
        newProduct.setQuantity(quantity);
        productService.editProduct(newProduct);
        System.out.println("Số lượng sản phẩm đã được cập nhật.");
        showProductList();
        ContinueOrExist();
    }

    public void removeProduct() {
        try {
            boolean flag = true;
            showProductList();
            Long id = inputIdProduct(ChoiceStatus.REMOVE);
            System.out.println();
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║        ► Xóa Sản Phẩm ◄        ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║       1.     Đồng ý            ║");
            System.out.println("║       2.     Quay lại          ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                String choice = input.nextLine();
                switch (choice) {
                    case "1":
                        productService.removeProduct(id);
                        System.out.println("Sản phẩm đã được xóa.");
                        showProductListShow();
                        Menu.menuManageProduct();
                        break;
                    case "2":
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

    public void showProductList() {
        System.out.println();
        System.out.println("══════════════════════════════════════ Danh Sách Sản Phẩm ═════════════════════════════════════════");
        System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
        System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
        for (Product product : productService.findAllProducts()) {
            System.out.printf("%-25s %-20s %-20s %-20s\n",
                    product.getIdProduct(),
                    product.getTitle(),
                    InstantUtils.quantityProducts(product.getQuantity()),
                    InstantUtils.doubleToVND(product.getPrice()));
        }
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    public void showProductListShow() {
        showProductList();
        int choice;
        do {
            System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
            System.out.print("=> ");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
    }

    public void showProductListShowOutMenu() {
        showProductListShow();
        Menu.menuManageProduct();
    }

    public void showProductListShowUser() {
        showProductListShow();
        Menu.menuUser();
    }

    public void findProduct() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║          ► Tìm kiếm sản phẩm ◄          ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║                                         ║");
            System.out.println("║       1.     Tìm theo tên sản phẩm      ║");
            System.out.println("║       2.     Tìm theo ID sản phẩm       ║");
            System.out.println("║       0.     Quay lại quản lý sản phẩm  ║");
            System.out.println("║                                         ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        findByTitle();
                        break;
                    case "2":
                        findById();
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
            findProduct();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void findByTitle() {
        System.out.println("Nhập tên sản phẩm muốn tìm");
        System.out.print("=> ");
        String title = input.nextLine().trim();
        title.toLowerCase();
        List<Product> products = productService.findProductByTitle(title);
        if (products.size() != 0) {
            System.out.println();
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
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
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
        } else {
            System.out.println();
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
            System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%60s", "Sản phẩm không được tìm thấy!!!\n");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
        }
        findProduct();
    }

    public void findById() {
        System.out.println("Nhập ID sản phẩm muốn tìm");
        System.out.print("=> ");
        Long id = AppUtils.retryParseLong();
        Product product = productService.checkId(id);
        if (product != null) {
            System.out.println();
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
            System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-25s %-20s %-20s %-20s\n",
                    product.getIdProduct(),
                    product.getTitle(),
                    InstantUtils.quantityProducts(product.getQuantity()),
                    InstantUtils.doubleToVND(product.getPrice()));
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
        } else {
            System.out.println();
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
            System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%60s", "Sản phẩm không được tìm thấy!!!\n");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
        }
        findProduct();
    }

    public void ContinueOrExist() {
        boolean is = true;
        do {
            System.out.println("Nhấn '1' để tiếp tục \t|\t '2' để trở về quản lý sản phẩm");
            System.out.print("=> ");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    editProduct();
                    break;
                case "2":
                    Menu.menuManageProduct();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
                    is = false;
                    break;
            }
        } while (!is);
    }
}
