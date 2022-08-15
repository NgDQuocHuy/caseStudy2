package views;

import models.Product;
import models.User;
import services.IUserService;
import services.UserService;

import java.util.List;
import java.util.Scanner;

public class UserView {
    public List<User> products;
    private static UserService userService = new UserService();
    static Scanner input = new Scanner(System.in);

    public static void signin() {
        System.out.println();
        System.out.println("══════════► Tạo Tài Khoản ◄══════════");
        System.out.println("Vui lòng nhập tài khoản mới:");
        System.out.print("=> ");
        System.out.println("Vui lòng nhập mật khẩu mới:");
        System.out.print("=> ");
        System.out.println("Tạo tài khoản thành công.");
    }




    public static String inputFullName(ChoiceStatus status) {
        String fullName;
        switch (status) {
            case ADD:
                System.out.println("Nhập tên của bạn: ");
                break;
            case EDIT:
                System.out.println("Nhập tên mới: ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            fullName = input.nextLine().trim();
            boolean exits = (!fullName.isEmpty());
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
        return fullName;
    }
}
