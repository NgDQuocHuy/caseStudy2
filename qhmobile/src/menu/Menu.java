package menu;

import utils.AppUtils;
import views.OrderView;
import views.ProductView;
import views.SortView;
import views.UserView;

import java.util.InputMismatchException;
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
            System.out.println("║       1.     Đăng nhập                        ║");
            System.out.println("║       2.     Tạo tài khoản                    ║");
            System.out.println("║       0.     Thoát chương trình               ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        UserView.login();
                        break;
                    case "2":
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

    public static void menuAdmin() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║                ► Menu Quản Lý ◄               ║");
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

    public static void menuUser() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║                   ► Menu ◄                    ║");
            System.out.println("╠═══════════════════════════════════════════════╣");
            System.out.println("║       1.     Xem sản phẩm                     ║");
            System.out.println("║       2.     Đặt sản phẩm                     ║");
            System.out.println("║       0.     Quay lại màn hình đăng nhập      ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        productView.showProductListShowUser();
                        break;
                    case "2":
                        OrderView.addOrderUser();
                        break;
                    case "0":
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
            System.out.println("║       0.     Quay lại menu quản lý      ║");
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
            System.out.println("║       3.     Set Role Users             ║");
            System.out.println("║       0.     Quay lại Menu Admin        ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        UserView.showUserListChoice();
                        break;
                    case "2":
                        UserView.removeUser();
                        break;
                    case "3":
                        UserView.setRoleUser();
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
}
