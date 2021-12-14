package com.mjrdev.JobOffers.Repository;

import com.mjrdev.JobOffers.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User getByEmail(String email);
}
