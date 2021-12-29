package com.mjrdev.JobOffers.Service.PostulationService;

import com.mjrdev.JobOffers.Model.Postulation;

import java.util.List;

public interface PostulationService {

    // SAVE POSTULATION
    public Postulation savePostulation(Postulation postulation);

    // GET POSTULATIONS
    public List<Postulation> getPostulations();
    public List<Postulation> getUserPostulations(int user_id);
    public List<Postulation> getOfferPostulations(int offer_id);

    // CHECK EXIST
    public boolean postulationExist(int postulation_id);
    public boolean postulationExist(int user_id, int offer_id);

    // DELETE POSTULATION
    public boolean deletePostulation(int id);

}
