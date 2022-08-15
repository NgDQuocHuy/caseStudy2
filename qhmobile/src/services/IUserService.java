package services;

import models.Product;
import models.User;

import java.util.List;

public interface IUserService {
    List<User> findAllUsers();

    User loginUser(String username, String password);

    void addUser(User newUser);

    void revomeUser(Long idUser);

    void editUser(User newUser);

    User findIdUser(Long idUser);

    boolean exitsUserId(Long idUser);

    boolean exitsUserEmail(String email);

    boolean exitsUserPhone(String phone);

    boolean exitsUserName(String nameUser);
}
