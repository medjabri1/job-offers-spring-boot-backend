package com.mjrdev.JobOffers.Service.OfferService;

import com.mjrdev.JobOffers.Model.Offer;

import java.util.List;

public interface OfferService {

    // SAVE OFFER
    public Offer saveOffer(Offer offer);

    // GET OFFER / OFFERS
    public Offer getOffer(int id);
    public List<Offer> getOffers();

    // GET OFFERS OF RECRUITER
    public List<Offer> getRecruiterOffers(int recruiter_id);
    public List<Offer> getRecruiterOffers(int recruited_id, int closed);

    // GET OFFERS BY CATEGORY
    public List<Offer> getOffersByCategory(int category_id);
    public List<Offer> getOffersByCategory(int category_id, int limit);

    // OFFER EXIST
    public boolean offerExist(int id);

    // UPDATE OFFER
    public boolean updateOffer(Offer offer);

    // DELETE OFFER
    public boolean deleteOffer(int id);
}
