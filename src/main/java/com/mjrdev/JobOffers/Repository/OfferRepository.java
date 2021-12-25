package com.mjrdev.JobOffers.Repository;

import com.mjrdev.JobOffers.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
