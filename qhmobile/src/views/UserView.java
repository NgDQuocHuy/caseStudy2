package views;

import menu.Menu;
import models.User;
import services.UserService;
import utils.AppUtils;
import utils.InstantUtils;
import utils.ValidateUtils;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class UserView {
    public List<User> products;
    private static UserService userService = new UserService();
    static Scanner input = new Scanner(System.in);

    public static void signin() {
        boolean flag = true;
        do {
            System.out.println();
            System.out.println("══════════► Tạo Tài Khoản ◄══════════");
            Long idUser = System.currentTimeMillis() / 1000;
            System.out.println("Mã số tài khoản của bạn là: ");
            System.out.println("=> qhs" + idUser);
            String userName = "qhs" + idUser;
            String password = inputPassword(ChoiceStatus.ADD);
            String fullName = inputFullName(ChoiceStatus.ADD);
            String phoneNumber = inputPhoneNumber(ChoiceStatus.ADD);
            String email = inputEmail(ChoiceStatus.ADD);
            String address = inputAddress(ChoiceStatus.ADD);
            String roleUser = inputRoleUser();
            User user = new User(idUser, userName, password, fullName, phoneNumber, email, address, roleUser, Instant.now());
            System.out.println("Bạn đã đăng ký thành công, bạn có thể trở về màn hình đăng nhập.");
            userService.addUser(user);
            int choice;
            do {
                System.out.println("Nhấn 0 để quay về màn hình đăng nhập.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
            Menu.qhStore();
        } while (!flag);
    }

    public static void removeUser() {
        try {
            boolean flag = true;
            showUserList();
            Long id = inputIdUser(ChoiceStatus.REMOVE);
            System.out.println();
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║          ► Xóa User ◄          ║");
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
                        userService.revomeUser(id);
                        System.out.println("Người dùng đã được xóa.");
                        showUserListChoice();
                        Menu.menuManageUsers();
                        break;
                    case "2":
                        Menu.menuManageUsers();
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

    public static void setRoleUser() {
        try {
            showUserList();
            System.out.println("Nhập ID User cần set");
            System.out.print("=> ");
            Long id = AppUtils.retryParseLong();
            if (userService.exitsUserId(id)) {
                boolean flagUpdate = true;
                System.out.println();
                System.out.println("╔═════════════════════════════════════════╗");
                System.out.println("║             ► Set Role User ◄           ║");
                System.out.println("╠═════════════════════════════════════════╣");
                System.out.println("║       1.     Set quyền Admin            ║");
                System.out.println("║       2.     Set làm User               ║");
                System.out.println("║       0.     Quay lại quản lý Users     ║");
                System.out.println("╚═════════════════════════════════════════╝");
                System.out.println("Chọn chức năng: ");
                System.out.print("=> ");
                User newUser = new User();
                newUser.setIdUser(id);
                do {
                    String choice = input.nextLine();
                    switch (choice) {
                        case "1":
                            setAdmin(newUser);
                            break;
                        case "2":
                            setUser(newUser);
                            break;
                        case "0":
                            Menu.menuManageUsers();
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                            System.out.print("=> ");
                            flagUpdate = false;
                    }
                } while (!flagUpdate);
            } else {
                System.out.println("Không tìm thấy ID User.");
                setRoleUser();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void setAdmin(User user) {
        user.setRole("Admin");
        userService.editUser(user);
        System.out.println("Role User đã được cập nhật.");
        showUserListChoice();
        Menu.menuManageUsers();
    }

    private static void setUser(User user) {
        user.setRole("User");
        userService.editUser(user);
        System.out.println("Role User đã được cập nhật.");
        showUserListChoice();
        Menu.menuManageUsers();
    }

    public static String inputPassword(ChoiceStatus status) {
        String password;
        switch (status) {
            case ADD:
                System.out.println("Nhập mật khẩu của bạn (8 <= Password <= 16): ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            password = input.nextLine().trim();
            boolean exits = (password.length() >= 8 && password.length() <= 16);
            switch (status) {
                case ADD:
                    if (!exits) {
                        System.out.println("Bạn nhập sai định dạng, vui lòng nhập lại:");
                        System.out.print("=> ");
                    }
                    flagInputTitle = !exits;
                    break;
            }
        } while (flagInputTitle);
        return password;
    }

    public static String inputFullName(ChoiceStatus status) {
        String fullName;
        switch (status) {
            case ADD:
                System.out.println("Nhập tên của bạn (VD: Quoc Huy): ");
                break;
            case EDIT:
                System.out.println("Nhập tên bạn muốn thay đổi: ");
                break;
        }
        System.out.print("=> ");
        while (!ValidateUtils.isNameValid(fullName = input.nextLine())) {
            System.out.println("Tên " + fullName + " không đúng định dạng." + " Vui lòng nhập lại!" + " (Tên phải viết hoa chữ cái đầu và không dấu)");
            System.out.print("=> ");
        }
        return fullName;
    }

    public static String inputPhoneNumber(ChoiceStatus status) {
        String phoneNumber;
        switch (status) {
            case ADD:
                System.out.println("Nhập số điện thoại của bạn: ");
                break;
            case EDIT:
                System.out.println("Nhập số điện thoại mới của bạn: ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            phoneNumber = input.nextLine().trim();
            if (!ValidateUtils.isPhoneValid(phoneNumber)) {
                System.out.println("Số " + phoneNumber + " của bạn không đúng. Vui lòng nhập lại. (Số điện thoại bao gồm 10 -> 11 số và bắt đầu là số 0)");
                System.out.println("Nhập số điện thoại (VD: 0397502235)");
                System.out.print("=> ");
                continue;
            }
            break;
        } while (flagInputTitle);
        return phoneNumber;
    }

    public static String inputEmail(ChoiceStatus status) {
        String email;
        switch (status) {
            case ADD:
                System.out.println("Nhập Email của bạn: ");
                break;
            case EDIT:
                System.out.println("Nhập Email mới của bạn: ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            if (!ValidateUtils.isEmailValid(email = input.nextLine())) {
                System.out.println("Email " + email + "của bạn không đúng định dạng! Vui lòng kiểm tra và nhập lại ");
                System.out.println("Nhập email (VD: ngdquochuy241@gmail.com)");
                System.out.print("=> ");
                continue;
            }
            break;
        } while (flagInputTitle);
        return email;
    }

    public static String inputAddress(ChoiceStatus status) {
        String address;
        switch (status) {
            case ADD:
                System.out.println("Nhập địa chỉ của bạn: ");
                break;
            case EDIT:
                System.out.println("Nhập địa chỉ mới của bạn: ");
                break;
        }
        System.out.print("=> ");
        boolean flagInputTitle = true;
        do {
            address = input.nextLine().trim();
            boolean exits = (!address.isEmpty());
            switch (status) {
                case ADD:
                case EDIT:
                    if (!exits) {
                        System.out.println("Địa chỉ không được để trống, vui lòng nhập lại:");
                        System.out.print("=> ");
                    }
                    flagInputTitle = !exits;
                    break;
            }
        } while (flagInputTitle);
        return address;
    }

    public static String inputRoleUser() {
        System.out.println("Nhập mã Admin của bạn, nếu nhập sai hoặc không nhập bạn là User.");
        System.out.print("=> ");
        String setRole = input.nextLine();
        String role;
        if (setRole.equals("24011999")) {
            role = "Admin";
        } else {
            role = "User";
        }
        return role;
    }

    public static Long inputIdUser(ChoiceStatus status) {
        Long idUser;
        switch (status) {
            case EDIT:
            case REMOVE:
                System.out.println("Nhập ID User cần xóa:");
                break;
        }
        System.out.print("=> ");
        boolean flagInputID = true;
        do {
            idUser = AppUtils.retryParseLong();
            boolean exits = userService.exitsUserId(idUser);
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
        return idUser;
    }

    public static void login() {
        User users;
        do {
            try {
                System.out.println();
                System.out.println("══════════► Đăng Nhập ◄══════════");
                System.out.println("Vui lòng nhập mã số tài khoản:");
                System.out.print("=> ");
                String username = input.nextLine();
                System.out.println("Vui lòng nhập mật khẩu:");
                System.out.print("=> ");
                String password = input.nextLine();
                users = userService.loginUser(username, password);
                if (username.equals("admin") && password.equals("admin")) {
                    System.out.println("Đăng nhập thành công.");
                    Menu.menuAdmin();
                } else if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
                    if (users.getRole().equals("User")) {
                        System.out.println("Đăng nhập thành công.");
                        Menu.menuUser();
                    } else if (users.getRole().equals("Admin")) {
                        System.out.println("Đăng nhập thành công.");
                        Menu.menuAdmin();
                    }
                }
                break;
            } catch (Exception ex) {
                System.out.println("Mã số tài khoản hoặc mật khẩu không đúng vui lòng nhập lại");
                System.out.print("=> ");
                ContinueOrExit();
            }
        } while (true);
    }

    public static void showUserListChoice() {
        showUserList();
        int choice;
        do {
            System.out.println("Nhấn 0 để quay lại Users.");
            System.out.print("=> ");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
        Menu.menuManageUsers();
    }

    public static void showUserList() {
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════════════════════════ Danh Sách Users ════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.printf("%-15s %-15s %-25s %-25s %-15s %-25s %-25s %-10s %-25s\n", "ID", "Username", "Password", "FullName", "Phone Number", "Email", "Address", "Role", "Time Creat");
        System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        for (User user : userService.findAllUsers()) {
            System.out.printf("%-15s %-15s %-25s %-25s %-15s %-25s %-25s %-10s %-25s\n",
                    user.getIdUser(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    InstantUtils.instantToString(user.getTimeCreatUser()));
        }
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    public static void ContinueOrExit() {
        boolean flag = true;
        do {
            System.out.println("Nhấn 1 để tiếp tục or nhấn 2 để thoát");
            System.out.print("=> ");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    break;
                case "2":
                    Menu.qhStore();
                    break;
                default:
                    System.out.println("Lựa chọn không đúng vui lòng nhập lại");
                    flag = false;
                    break;
            }
        } while (!flag);
    }
}
