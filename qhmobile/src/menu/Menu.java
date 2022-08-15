package menu;

import models.Product;
import models.User;
import services.IProductService;
import utils.AppUtils;
import views.OrderView;
import views.ProductView;
import views.SortView;
import views.UserView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    static Scanner input = new Scanner(System.in);
    static ProductView productView = new ProductView();

    public static void qhStore() {
        try {
            boolean flagLogin = true;
            String choice;
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║                  ► QH Store ◄                 ║");
            System.out.println("╠═══════════════════════════════════════════════╣");
            System.out.println("║       1.     Đăng nhập (User)                 ║");
            System.out.println("║       2.     Đăng nhập (Admin)                ║");
            System.out.println("║       3.     Tạo tài khoản (User)             ║");
            System.out.println("║       0.     Thoát chương trình               ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        loginUser();
                        break;
                    case "2":
                        loginAdmin();
                        break;
                    case "3":
                        UserView.signin();
                        break;
                    case "0":
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flagLogin = false;
                }
            } while (!flagLogin);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //      Cần hàm sử lý
    public static void loginUser() {
        System.out.println();
        System.out.println("══════════► Đăng Nhập ◄══════════");
        System.out.println("Vui lòng nhập tài khoản:");
        System.out.print("=> ");
        System.out.println("Vui lòng nhập mật khẩu:");
        System.out.print("=> ");
        System.out.println("Đăng nhập thành công.");
        System.out.println("Tài khoản hoặc Mật khẩu không đúng.");
    }

    public static void loginAdmin() {
        boolean login = true;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("══════════► Đăng Nhập ◄══════════");
            System.out.println("Vui lòng nhập tài khoản:");
            System.out.print("=> ");
            String username = scanner.nextLine();
            System.out.println("Vui lòng nhập mật khẩu:");
            System.out.print("=> ");
            String password = scanner.nextLine();
            if (username.equals("admin") && password.equals("admin")) {
                System.out.println("Đăng nhập thành công.");
                login = false;
                menuAdmin();
            } else if (username == null || password == null || !username.equals("admin") && !password.equals("admin")) {
                System.out.println("Tài khoản hoặc Mật khẩu không đúng.");
            }
        } while (login);
    }

    public static void menuAdmin() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║                   ► Menu ◄                    ║");
            System.out.println("╠═══════════════════════════════════════════════╣");
            System.out.println("║       1.     Quản lý sản phẩm                 ║");
            System.out.println("║       2.     Quản lý đơn hàng                 ║");
            System.out.println("║       3.     Quản lý người dùng               ║");
            System.out.println("║       4.     Quay lại màn hình đăng nhập      ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        menuManageProduct();
                        break;
                    case "2":
                        menuManageOrder();
                        break;
                    case "3":
                        menuManageUsers();
                        break;
                    case "4":
                        qhStore();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flag = false;
                }
            } while (!flag);
        } catch (InputMismatchException io) {
            io.printStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //      Chưa có hàm xử lý
    public static void menuManageProduct() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Quản Lý Sản Phẩm ◄                  ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     Thêm sản phẩm                             ║");
            System.out.println("║       2.     Sửa thông tin sản phẩm                    ║");
            System.out.println("║       3.     Xoá sản phẩm                              ║");
            System.out.println("║       4.     Hiển thị danh sách sản phẩm               ║");
            System.out.println("║       5.     Tìm kiếm sản phẩm                         ║");
            System.out.println("║       6.     Sắp xếp sản sản phẩm                      ║");
            System.out.println("║       0.     Quay lại Menu Admin                       ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        productView.addProduct();
                        break;
                    case "2":
                        productView.editProduct();
                        break;
                    case "3":
                        productView.removeProduct();
                        break;
                    case "4":
                        productView.showProductListShowOutMenu();
                        break;
                    case "5":
                        productView.findProduct();
                        break;
                    case "6":
                        SortView.menuSort();
                        break;
                    case "0":
                        menuAdmin();
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

    //      Chưa có hàm sử lý
    public static void menuManageOrder() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║          ► Quản Lý Đơn Hàng ◄           ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║       1.     Tạo đơn hàng               ║");
            System.out.println("║       2.     Xem danh sách Order        ║");
            System.out.println("║       0.     Quay lại Menu Admin        ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        OrderView.addOrder();
                        break;
                    case "2":
                        OrderView.showListOrder();
                        break;
                    case "0":
                        menuAdmin();
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

    //      Chưa có hàm sử lý
    public static void menuManageUsers() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║             ► Quản Lý Users ◄           ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║       1.     Xem danh sách Users        ║");
            System.out.println("║       2.     Xóa Users                  ║");
            System.out.println("║       0.     Quay lại Menu Admin        ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.print("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                    case "2":
                    case "0":
                        menuAdmin();
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
}
