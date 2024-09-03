package service;

import models.User;

public interface UserManager {
    User createUser(String name, String email, String password, String phoneNumber, boolean isAdmin);
    User login(String email, String password, String phoneNumber, Integer userId);
    User getUser(Integer userId);
}
