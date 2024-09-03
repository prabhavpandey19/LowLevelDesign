package service.impl;

import models.User;
import service.UserManager;

import java.util.HashMap;
import java.util.Map;

public class UserManagerImpl implements UserManager {
    private Map<Integer, User> userIdvsUser;
    private Map<String, User> emailvsUser;
    private Map<String, User> phoneNumbervsUser;

    private static UserManagerImpl userManager;
    private Integer counter;
    private UserManagerImpl() {
        userIdvsUser = new HashMap<>();
        emailvsUser = new HashMap<>();
        phoneNumbervsUser = new HashMap<>();
        counter = 0;
    }

    public static UserManagerImpl getInstance() {
        if (userManager == null) {
            synchronized (UserManagerImpl.class) {
                if (userManager == null) {
                    userManager = new UserManagerImpl();
                }
            }
        }
        return userManager;
    }


    @Override
    public User createUser(String name, String email, String password, String phoneNumber, boolean isAdmin) {
        checkAlreadyExists(email, phoneNumber);
        User user = new User();
        user.setUserId(getCounter());
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setPhoneNumber(phoneNumber);
        userIdvsUser.put(user.getUserId(), user);
        emailvsUser.put(user.getEmail(), user);
        phoneNumbervsUser.put(user.getPhoneNumber(), user);
        return user;
    }

    private void checkAlreadyExists(String email, String phoneNumber) {
        if (emailvsUser.get(email) != null) {
            throw new RuntimeException("Email already exists");
        }
        if (phoneNumbervsUser.get(phoneNumber) != null) {
            throw new RuntimeException("Phone number already exists");
        }
    }

    @Override
    public User login(String email, String password, String phoneNumber, Integer userId) {
        if (email == null && phoneNumber == null && userId == null) {
            throw new RuntimeException("Invalid input");
        }
        User user = null;
        if (userId != null) {
            user = userIdvsUser.get(userId);
        } else if (email != null) {
            user = emailvsUser.get(email);
        } else if (phoneNumber != null) {
            user = phoneNumbervsUser.get(phoneNumber);
        }
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (user != null && !user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    @Override
    public User getUser(Integer userId) {
        if (userIdvsUser.get(userId) == null) {
            throw new RuntimeException("User not found");
        }
        return userIdvsUser.get(userId);
    }

    private int getCounter() {
        synchronized (counter) {
            return counter++;
        }
    }
}
