package com.mjrdev.JobOffers.Service.OfferService;

import com.mjrdev.JobOffers.Model.Offer;

import java.util.List;

public interface OfferService {

    // SAVE OFFER
    public Offer saveOffer(Offer offer);

    // GET OFFER / OFFERS
    public Offer getOffer(int id);
    public List<Offer> getOffers();

    // OFFER EXIST
    public boolean offerExist(int id);

    // UPDATE OFFER
    public boolean updateOffer(Offer offer);

    // DELETE OFFER
    public boolean deleteOffer(int id);
}
