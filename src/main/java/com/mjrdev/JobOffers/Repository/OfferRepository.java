package com.mjrdev.JobOffers.Repository;

import com.mjrdev.JobOffers.Model.Offer;
import com.mjrdev.JobOffers.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

    // GET RECRUITER OFFERS

    @Query(
            value = "SELECT * FROM offers WHERE recruiter_id = ?1",
            nativeQuery = true
    )
    public List<Offer> getRecruiterOffers(int recruiter_id);

    @Query(
            value = "SELECT * FROM offers WHERE recruiter_id = ?1 and closed = ?2",
            nativeQuery = true
    )
    public List<Offer> getRecruiterOffers(int recruiter_id, int closed);
}
