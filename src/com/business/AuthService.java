package com.business;

import com.data.User;

import javax.management.OperationsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthService {
    private final List<User> users = new ArrayList<>();

    public User register(String username, String password) throws OperationsException {
        if (password.length() < 4 || username.length() < 3) {
            throw new IllegalArgumentException("password is less than 4 symbols or username is less than 3 symbols");
        }
        Optional<User> log_user = users.stream().filter(user -> Objects.equals(user.getUsername(), username)).findFirst();
        if (log_user.isPresent()) {
            throw new OperationsException("User with provided username already exists");
        }
        User user = new User(username, password);
        users.add(user);
        return user;
    }

    public User login(String username, String password) throws OperationsException {
        Optional<User> log_user = users.stream().filter(user -> Objects.equals(user.getPassword(), password) && Objects.equals(user.getUsername(), username)).findFirst();
        if (log_user.isEmpty()) {
            throw new OperationsException("User with provided username and password does not exist");
        }
        return log_user.get();
    }

}
