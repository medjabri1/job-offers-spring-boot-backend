package com.mjrdev.JobOffers.Service.UserService;

import com.mjrdev.JobOffers.Model.User;
import com.mjrdev.JobOffers.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    // SAVE NEW USER

    @Override
    public User saveUser(User user) {

        if(userRepository.getByEmail(user.getEmail()) == null) {
            // EMAIL ALREADY TAKEN
            return userRepository.save(user);
        } else {
            // EMAIL AVAILABLE
            return new User();
        }
    }

    // GET ONE USER BY ID

    @Override
    public User getUser(int id) {
        return userRepository.getById(id);
    }

    // GET ONE USER BY EMAIL

    @Override
    public User getUser(String email) {
        return userRepository.getByEmail(email);
    }

    // GET ALL USERS

    @Override
    public List<User> getUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    // CHECK IF USER EXISTS BY ID

    @Override
    public boolean userExist(int id) {
        return userRepository.existsById(id);
    }

    // CHECK IF USER EXISTS BY EMAIL

    @Override
    public boolean userExist(String email) {
        return userRepository.getByEmail(email) != null;
    }

    // UPDATE USER

    @Override
    public boolean updateUser(User user) {

        if(userExist(user.getId())) {
            // USER EXISTS
            return userRepository.save(user) != null;
        } else {
            //USER DOESN'T EXIST
            return false;
        }
    }

    // DELETE USER

    @Override
    public boolean deleteUser(int id) {

        if(userExist(id)) {
            // USER EXISTS
            userRepository.deleteById(id);
            return true;
        } else {
            //USER DOESN'T EXIST
            return false;
        }
    }

    // AUTHORITIES

}
