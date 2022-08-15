package views;

import menu.Menu;
import models.ItemOrder;
import models.Order;
import models.Product;
import services.ItemOrderService;
import services.OrderService;
import services.ProductService;
import utils.AppUtils;
import utils.InstantUtils;
import utils.ValidateUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    public List<Order> orders;
    private static OrderService orderService = new OrderService();
    private static ProductService productService = new ProductService();
    private static ItemOrderService itemOrderService = new ItemOrderService();
    private static ProductView productView = new ProductView();
    private static final Scanner input = new Scanner(System.in);

    public OrderView() {
        orderService = OrderService.getInstanceOrder();
        productService = ProductService.getInstanceProduct();
        itemOrderService = ItemOrderService.instanceItemOrder();
    }

    public static void addOrder() {
        try {
            itemOrderService.findAllItemOrder();
            Long idOrder = System.currentTimeMillis() / 1000;
            System.out.println("Nhập tên người đặt hàng: (Vd: Quoc Huy)");
            System.out.print("=> ");
            String fullName;
            while (!ValidateUtils.isNameValid(fullName = input.nextLine())) {
                System.out.println("Tên " + fullName + " không đúng định dạng." + " Vui lòng nhập lại!" + " (Tên phải viết hoa chữ cái đầu và không dấu)");
                System.out.print("=> ");
            }
            System.out.println("Nhập số điện thoại: ");
            System.out.print("=> ");
            String numberPhone = input.nextLine();
            while (!ValidateUtils.isPhoneValid(numberPhone) || numberPhone.trim().isEmpty()) {
                System.out.println("Số điện thoại: " + numberPhone + " Không đúng đinh dạng, vui lòng nhập lại.");
                System.out.println("Nhập số điện thoại (Số điện thoại bao gồm 10 -> 11 số và bắt đầu là số 0)");
                System.out.print("=> ");
                numberPhone = input.nextLine();
            }
            System.out.println("Nhập địa chỉ của bạn: ");
            System.out.print("=> ");
            String address = input.nextLine();
            while (address.trim().isEmpty()) {
                System.out.println("Địa chỉ của bạn không được để trống, vui lòng nhập lại.");
                System.out.print("=> ");
                address = input.nextLine();
            }

            Order order = new Order(idOrder, fullName, numberPhone, address, Instant.now());
            List<ItemOrder> itemOrders = addItemsOrder(idOrder);
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderService.addItemOrder(itemOrder);
            }
            orderService.add(order);
            confirmOrder(order);
        } catch (Exception e) {
            System.out.println("Không đúng! Vui lòng nhập lại.");
        }
    }

    public static void confirmOrder(Order order) {
        try {
            boolean flag = true;
            String choice;
            System.out.println("Đã tạo order thành công:");
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║               ► Đơn Hàng ◄              ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║       1.     In bill                    ║");
            System.out.println("║       2.     Quay lại                   ║");
            System.out.println("║       0.     Exit                       ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        showPayIn4Admin(order);
                        break;
                    case "2":
                        Menu.menuManageOrder();
                        break;
                    case "0":
                        System.exit(5);
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

    public static List<ItemOrder> addItemsOrder(Long id) {
        List<ItemOrder> itemOrders = new ArrayList<>();
        productView.showProductList();
        System.out.println("Nhập số sản phẩm đơn hàng mà bạn muốn mua");
        System.out.print("=> ");
        int choice = Integer.parseInt(input.nextLine());
        while (choice < 0) {
            System.out.println("Số sản phẩm đơn hàng không hợp lệ (không được nhỏ hơn 0 )");
            System.out.print("=> ");
            choice = Integer.parseInt(input.nextLine());
        }
        int count = 0;
        do {
            try {
                itemOrders.add(addItemOrders(id));
                count++;
            } catch (Exception e) {
                System.out.println("Không đúng, vui lòng nhập lại.");
            }
        } while (count < choice);
        return itemOrders;
    }

    public static ItemOrder addItemOrders(Long idOrder) {
        do {
            try {
                itemOrderService.findAllItemOrder();
                productView.showProductList();
                Long id = System.currentTimeMillis() / 1000;
                System.out.println("Nhập ID sản phẩm bạn muốn mua");
                System.out.print("=> ");
                Long idProduct = Long.parseLong(input.nextLine());
                while (!productService.exitsById(idProduct)) {
                    System.out.println("ID sản phẩm không tồn tại, vui lòng nhập lại");
                    System.out.print("=> ");
                    idProduct = Long.parseLong(input.nextLine());
                }
                Product product = productService.checkId(idProduct);
                Double price = product.getPrice();
                System.out.println("Nhập số lượng bạn muốn mua");
                System.out.print("=> ");
                Double quantity = Double.parseDouble(input.nextLine());
                while (!checkQuantityProduct(product, quantity)) {
                    System.out.println("Số lượng sản phẩm không đủ, vui lòng nhập lại:");
                    System.out.print("=> ");
                    quantity = Double.parseDouble(input.nextLine());
                    if (product.getQuantity() == 0) {
                        System.out.println("Sản phẩm đã hết hàng.");
                        int choice;
                        do {
                            System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                            choice = AppUtils.retryParseInt();
                        } while (choice != 0);
                        Menu.menuManageOrder();
                    }
                }
                String nameProduct = product.getTitle();
                Double total = quantity * price;
                Double grandTotal = 0.0;
                ItemOrder itemOrder = new ItemOrder(id, price, quantity, idOrder, idProduct, nameProduct, total, grandTotal);
                productService.updateQuantity(idProduct, quantity);
                return itemOrder;
            } catch (Exception e) {
                System.out.println("Không đúng vui lòng nhập lại.");
            }
        } while (true);
    }

    public static boolean checkQuantityProduct(Product product, Double quantity) {
        if (quantity <= product.getQuantity()) {
            return true;
        }
        return false;
    }

    public static void showPayIn4(Order order) {
        try {
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                 HÓA ĐƠN                                   ║");
            System.out.println("╠═════════════════════╤═════════════════════════════════════════════════════╣");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Tên người đặt       │", order.getFullName(), "");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Số điện thoại       │", order.getMobile(), "");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Địa chỉ             │", order.getAddress(), "");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Ngày tạo đơn        │", InstantUtils.instantToString(order.getTimeCreatOrder()), "");
            System.out.println("╠════╤════════════════╧═════════════╤══════════════════╤════════════════════╣");
            System.out.printf("║%-3s │\t%-27s │\t%-14s │\t%-15s ║\n", "STT", "Tên sản phẩm", "Số lượng", "Giá");
            System.out.println("╟────┼──────────────────────────────┼──────────────────┼────────────────────╢");
            List<ItemOrder> itemOrders = itemOrderService.findAllItemOrder();
            double sum = 0;
            int count = 0;
            for (ItemOrder itemOrder1 : itemOrders) {
                if (itemOrder1.getIdOrder().equals(order.getIdOrder())) {
                    sum += itemOrder1.getTotal();
                    count++;
                    itemOrder1.setGrandTotal(sum);
                    itemOrderService.update(itemOrder1.getIdOrder(), itemOrder1.getPrice(), sum);
                    System.out.printf("║ %-2s │\t%-27s │\t%-14s │\t%-15s ║\n",
                            count,
                            itemOrder1.getNameProduct(),
                            InstantUtils.quantityProducts(itemOrder1.getQuantity()),
                            InstantUtils.doubleToVND(itemOrder1.getPrice()));
                    System.out.println("╟────┼──────────────────────────────┼──────────────────┼────────────────────╢");
                }
            }
            System.out.println("╟────┴──────────────────────────────┴──────────────────┴────────────────────╢");
            System.out.printf("║                                             Tổng tiền: %17s  ║\n", InstantUtils.doubleToVND(sum));
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
        } catch (Exception e) {
            System.out.println("Nhập không đúng, vui lòng nhập lại");
        }
    }

    public static void showPayIn4Admin(Order order) {
        showPayIn4(order);
        int choice;
        do {
            System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
        Menu.menuManageOrder();
    }

    public static void showPayIn4User(Order order) {
        showPayIn4(order);
        int choice;
        do {
            System.out.println("Nhấn 0 để quay Menu.");
            System.out.print("=> ");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
        Menu.menuUser();
    }

    public static void showListOrder() {
        try {
            List<Order> orders = orderService.findAllOrder();
            List<ItemOrder> itemOrders = itemOrderService.findAllItemOrder();
            int count = 0;
            double printTotal = 0;
            double total = 0;
            double sum = 0;
            double grandTotal = 0;
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════════ Danh Sách Hóa Đơn ══════════════════════════════════════════════════════════════════════════════════════════════════════════");
            for (Order order : orders) {
                System.out.println("\t╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.printf("\t║\t%-20s %-50s %-20s %-47s║\n", "Id            : ", order.getIdOrder(), "Tên khách hàng :", order.getFullName());
                System.out.printf("\t║\t%-20s %-50s %-20s %-47s║\n", "Số điện thoại : ", order.getMobile(), "Địa chỉ        : ", order.getAddress());
                System.out.println("\t╠═══════╤═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("\t║\t%-2s │%-10s %-25s %-10s %-20s %-10s %-20s %-10s %-23s║\n", "STT", "", "Tên Sản Phẩm", "", "Số Lượng", "", "Giá", "", "Tổng Tiền Sản Phẩm");
                System.out.println("\t╟───────┼───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢");
                for (ItemOrder itemOrder : itemOrders) {
                    if (itemOrder.getIdOrder().equals(order.getIdOrder())) {
                        count++;
                        total = itemOrder.getPrice() * itemOrder.getQuantity();
                        System.out.printf("\t║\t%-3s │%-10s %-25s %-10s %-20s %-10s %-20s %-10s %-23s║\n",
                                count, "",
                                itemOrder.getNameProduct(), " ",
                                InstantUtils.quantityProducts(itemOrder.getQuantity()), " ",
                                InstantUtils.doubleToVND(itemOrder.getPrice()), "",
                                InstantUtils.doubleToVND(total));
                        grandTotal += total;
                    }
                }
                printTotal += grandTotal;
                System.out.printf("\t╚═══════╧════════════════════════════════════════════════════════════════════════════════════════════════ Tổng Hóa Đơn:  %15s ═══════╝\n\n\n", InstantUtils.doubleToVND(grandTotal));
                sum = 0;
                grandTotal = 0;
                count = 0;
            }
            System.out.println("\t\t\t\t\t\t\t╔════════════════════════════════════════════════════════════════╗");
            System.out.printf("\t\t\t\t\t\t\t╟───────────── Tổng Doanh Thu: %20s ─────────────╢\n", InstantUtils.doubleToVND(printTotal));
            System.out.println("\t\t\t\t\t\t\t╚════════════════════════════════════════════════════════════════╝\n");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
            Menu.menuManageOrder();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //    Order của Users
    public static void addOrderUser() {
        try {
            itemOrderService.findAllItemOrder();
            Long idOrder = System.currentTimeMillis() / 1000;
            System.out.println("Nhập tên người đặt hàng: (Vd: Quoc Huy)");
            System.out.print("=> ");
            String fullName;
            while (!ValidateUtils.isNameValid(fullName = input.nextLine())) {
                System.out.println("Tên " + fullName + " không đúng định dạng." + " Vui lòng nhập lại!" + " (Tên phải viết hoa chữ cái đầu và không dấu)");
                System.out.print("=> ");
            }
            System.out.println("Nhập số điện thoại: ");
            System.out.print("=> ");
            String numberPhone = input.nextLine();
            while (!ValidateUtils.isPhoneValid(numberPhone) || numberPhone.trim().isEmpty()) {
                System.out.println("Số điện thoại: " + numberPhone + " Không đúng đinh dạng, vui lòng nhập lại.");
                System.out.println("Nhập số điện thoại (từ 10 -> 11 số bắt đầu từ ))");
                System.out.print("=> ");
                numberPhone = input.nextLine();
            }
            System.out.println("Nhập địa chỉ của bạn: ");
            System.out.print("=> ");
            String address = input.nextLine();
            while (address.trim().isEmpty()) {
                System.out.println("Địa chỉ của bạn không được để trống, vui lòng nhập lại.");
                System.out.print("=> ");
                address = input.nextLine();
            }

            Order order = new Order(idOrder, fullName, numberPhone, address, Instant.now());
            List<ItemOrder> itemOrders = addItemsOrder(idOrder);
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderService.addItemOrder(itemOrder);
            }
            orderService.add(order);
            confirmOrderUser(order);
        } catch (Exception e) {
            System.out.println("Không đúng! Vui lòng nhập lại.");
        }
    }

    public static void confirmOrderUser(Order order) {
        try {
            boolean flag = true;
            String choice;
            System.out.println("Đã tạo order thành công:");
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║               ► Đơn Hàng ◄              ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║       1.     In bill                    ║");
            System.out.println("║       2.     Quay lại                   ║");
            System.out.println("║       0.     Exit                       ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        showPayIn4User(order);
                        break;
                    case "2":
                        Menu.menuUser();
                        break;
                    case "0":
                        System.exit(5);
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