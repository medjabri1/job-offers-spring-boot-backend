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

    @Query(
            value = "SELECT * FROM ( SELECT * FROM offers where category_id = ?1 ORDER BY created_at DESC ) WHERE ROWNUM <= ?2 and closed = 0",
            nativeQuery = true
    )
    public List<Offer> getOffersByCategory(int category_id, int limit);

    @Query(
            value = "SELECT * FROM offers WHERE category_id = ?1 and closed = 0 ORDER BY created_at DESC",
            nativeQuery = true
    )
    public List<Offer> getOffersByCategory(int category_id);

}
