package views;

import menu.Menu;
import models.ItemOrder;
import models.Order;
import models.Product;
import services.ItemOrderService;
import services.OrderService;
import services.ProductService;
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
            System.out.println("Nhập tên người đặt hàng: (Vd: Quốc Huy)");
            System.out.print("=> ");
            String fullName = input.nextLine();
            while (fullName.trim().isEmpty()) {
                System.out.println("Tên bạn đang để trống");
                System.out.print("=> ");
                fullName = input.nextLine();
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
                        showPayIn4(order);
                        break;
                    case "2":
                        break;
                    case "0":
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
            System.out.println("Số lượng không được ( nhỏ hơn 0 )");
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
                        ContinueOrExit();
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

    public static void ContinueOrExit() {
        boolean flag = true;
        do {
            System.out.println("Nhấn 1 để tiếp tục or nhấn 2 để thoát");
            System.out.print("=> ");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    Menu.menuManageOrder();
                    break;
                case "2":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không đúng vui lòng nhập lại");
                    flag = false;
                    break;
            }
        } while (!flag);
    }

    public static void showPayIn4(Order order) {
        try {
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
                            itemOrder1.getQuantity(),
                            InstantUtils.doubleToVND(itemOrder1.getPrice()));
                    System.out.println("╟────┼──────────────────────────────┼──────────────────┼────────────────────╢");
                }
            }
            System.out.println("╟────┴──────────────────────────────┴──────────────────┴────────────────────╢");
            System.out.printf("║                                             Tổng tiền: %17s  ║\n", InstantUtils.doubleToVND(sum));
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝\n");
            ContinueOrExit();
        } catch (Exception e) {
            System.out.println("Nhập không đúng, vui lòng nhập lại");
        }
    }

    public static void showListOrder() {
        try {
            List<Order> orders = orderService.findAllOrder();
            List<ItemOrder> itemOrders = itemOrderService.findAllItemOrder();
            ItemOrder newOrderItem = new ItemOrder();
            int count = 0;
            double printTotal = 0;
            double total = 0;
            double sum = 0;
            double grandTotal = 0;
            System.out.println("──────────────────────────────────────────────────────────── DANH SACH HOA DON ─────────────────────────────────────────────────────────────────────");
            for (Order order : orders) {
                newOrderItem.setGrandTotal(grandTotal);
                itemOrderService.update(newOrderItem.getIdOrder(), newOrderItem.getPrice(), grandTotal);
                System.out.println("\t┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.printf("\t│\t%-20s %-20s %-30s %-20s %-25s %-25s│\n", "Id            : ", order.getIdOrder(), " ", "Ten khach hang :", order.getFullName(), "");
                System.out.printf("\t│\t%-20s %-20s %-30s %-20s %-25s %-25s│\n", "So dien thoai : ", order.getMobile(), " ", "Dia chi        : ", order.getAddress(), "");
                System.out.println("\t├───────┬───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
                System.out.printf("\t│\t%-1s │%-19s %-20s %-10s %-15s %-15s %-10s %-10s %-15s %-15s %-1s│\n", "STT", "", "Ten san pham", "", "", "So luong", "", "Gia", "  ", "Tong tien san pham", "");
                System.out.println("\t├───────┼───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
                for (ItemOrder itemOrder : itemOrders) {
                    if (itemOrder.getIdOrder().equals(order.getIdOrder())) {
                        count++;
                        total = itemOrder.getPrice() * itemOrder.getQuantity();
                        System.out.printf("\t│\t%-3d │%-1s %-20s %-20s %-10s %-15s %-15s %-10s %-18s %-11s %14s\t│\n",
                                count, " ", " ",
                                itemOrder.getNameProduct(), " ", " ",
                                itemOrder.getQuantity(), " ",
                                InstantUtils.doubleToVND(itemOrder.getPrice()), "",
                                InstantUtils.doubleToVND(total));
                    }
                    grandTotal += total;
                }
                printTotal += grandTotal;
                System.out.printf("\t└───────┴──────────────────────────────────────────────────────────────────────────────────────────────────── Tong don:  %15s ───────┘\n\n\n", InstantUtils.doubleToVND(grandTotal));
                sum = 0;
                grandTotal = 0;
                count = 0;
            }
            System.out.printf("\t\t\t\t\t\t\t┌────────────────── Tong doanh thu: %15s ────────────────────────┐\n", InstantUtils.doubleToVND(printTotal));
            System.out.println("\t\t\t\t\t\t\t└───────────────────────────────────────────────────────────────────────────┘ \n");
            ContinueOrExit();
        } catch (Exception e) {
            System.out.println("Không đúng, vui lòng nhập lại");
        }
    }
}