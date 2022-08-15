package models;

import java.time.Instant;

public class User {
    private Long idUser;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String role;
    private Instant timeCreatUser;

    public User() {}

    public User(Long idUser, String username, String password, String fullName, String mobile, String email, String address, String role, Instant timeCreatUser) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = mobile;
        this.email = email;
        this.address = address;
        this.role = role;
        this.timeCreatUser = timeCreatUser;
    }

    public static User parseUser(String rawUser) {
        String[] array = rawUser.split(",");
        User user = new User();
        user.setIdUser(Long.parseLong(array[0]));
        user.setUsername(array[1]);
        user.setPassword(array[2]);
        user.setFullName(array[3]);
        user.setPhoneNumber(array[4]);
        user.setEmail(array[5]);
        user.setAddress(array[6]);
        user.setRole(array[7]);
        user.setTimeCreatUser(Instant.parse(array[8]));
        return user;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getTimeCreatUser() {
        return timeCreatUser;
    }

    public void setTimeCreatUser(Instant timeCreatUser) {
        this.timeCreatUser = timeCreatUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                idUser,
                username,
                password,
                fullName,
                phoneNumber,
                email,
                address,
                role,
                timeCreatUser);
    }
}
