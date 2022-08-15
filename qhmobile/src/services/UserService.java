package services;

import models.Product;
import models.User;
import utils.CSVUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserService  implements IUserService{
    public final static String PATH = "C:\\Users\\huynd\\OneDrive\\Desktop\\caseStudy2\\caseStudy2\\qhmobile\\data\\users.csv";
    private static UserService instanceUser;
    public UserService() {}

    public static UserService getInstanceUser() {
        if (instanceUser == null) {
            instanceUser = new UserService();
        }
        return instanceUser;
    }


    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH);
        for (String record : records) {
            users.add(User.parseUser(record));
        }
        return users;
    }

    @Override
    public User loginUser(String username, String password) {
        List<User> users = findAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User newUser) {
        List<User> users = findAllUsers();
        users.add(newUser);
        CSVUtils.write(PATH, users);
    }

    @Override
    public void revomeUser(Long idUser) {
        List<User> users = findAllUsers();
        users.removeIf(id -> id.getIdUser().equals(idUser));
        CSVUtils.write(PATH, users);
    }

    @Override
    public void editUser(User newUser) {
        List<User> users = findAllUsers();
        for (User oldUser : users) {
            if (oldUser.getIdUser().equals(newUser.getIdUser())) {
                String fullName = newUser.getFullName();
                if (fullName != null && !fullName.isEmpty()) {
                    oldUser.setFullName(fullName);
                }
                String phoneNumber = newUser.getPhoneNumber();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    oldUser.setPhoneNumber(phoneNumber);
                }
                String email = newUser.getEmail();
                if (email != null && !email.isEmpty()) {
                    oldUser.setEmail(email);
                }
                String address = newUser.getAddress();
                if (address != null && !address.isEmpty()) {
                    oldUser.setAddress(address);
                }
                String role = newUser.getRole();
                if (role != null && !role.isEmpty()) {
                    oldUser.setRole(role);
                }
                oldUser.setTimeCreatUser(Instant.now());
                CSVUtils.write(PATH, users);
                break;
            }
        }
    }

    @Override
    public User findIdUser(Long idUser) {
        List<User> users = findAllUsers();
        for (User user : users) {
            if (user.getIdUser().equals(idUser)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean exitsUserId(Long idUser) {
        return findIdUser(idUser) != null;
    }

    @Override
    public boolean exitsUserEmail(String email) {
        List<User> users = findAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exitsUserPhone(String phone) {
        List<User> users = findAllUsers();
        for (User user : users) {
            if (user.getPhoneNumber().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exitsUserName(String nameUser) {
        List<User> users = findAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(nameUser)) {
                return true;
            }
        }
        return false;
    }

}
