package com.mjrdev.JobOffers.Repository;

import com.mjrdev.JobOffers.Model.Postulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulationRepository extends JpaRepository<Postulation, Integer> {

    @Query(
            value = "SELECT * FROM postulations WHERE applier_id = ?1",
            nativeQuery = true
    )
    public List<Postulation> getUserPostulations(int applier_id);

    @Query(
            value = "SELECT * FROM postulations WHERE offer_id = ?1",
            nativeQuery = true
    )
    public List<Postulation> getOfferPostulations(int offer_id);

    @Query(
            value = "SELECT * FROM postulations WHERE applier_id = ?1 and offer_id = ?2",
            nativeQuery = true
    )
    public Postulation getUserOfferPostulation(int user_id, int offer_id);

}
