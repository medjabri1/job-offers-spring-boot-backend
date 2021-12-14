package com.mjrdev.JobOffers.Service.UserService;

import com.mjrdev.JobOffers.Model.User;

import java.util.List;

public interface UserService {

    // SAVE USER
    public User saveUser(User user);

    // GET USER | USERS
    public User getUser(int id);
    public User getUser(String email);
    public List<User> getUsers();

    // CHECK EXISTENCE
    public boolean userExist(int id);
    public boolean userExist(String email);

    // UPDATE USER
    public boolean updateUser(User user);

    // DELETE USER
    public boolean deleteUser(int id);


}
