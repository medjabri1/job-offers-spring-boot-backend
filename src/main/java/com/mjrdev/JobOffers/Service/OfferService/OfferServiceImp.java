package com.mjrdev.JobOffers.Service.OfferService;

import com.mjrdev.JobOffers.Model.Offer;
import com.mjrdev.JobOffers.Repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImp implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Override
    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer getOffer(int id) {
        return offerRepository.getById(id);
    }

    @Override
    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> getRecruiterOffers(int recruiter_id) {
        return offerRepository.getRecruiterOffers(recruiter_id);
    }

    @Override
    public List<Offer> getRecruiterOffers(int recruited_id, int closed) {
        return offerRepository.getRecruiterOffers(recruited_id, closed);
    }

    @Override
    public List<Offer> getOffersByCategory(int category_id, int limit) {
        return offerRepository.getOffersByCategory(category_id, limit);
    }

    @Override
    public List<Offer> getOffersByCategory(int category_id) {
        return offerRepository.getOffersByCategory(category_id);
    }

    @Override
    public boolean offerExist(int id) {
        return offerRepository.existsById(id);
    }

    @Override
    public boolean updateOffer(Offer offer) {
        if(offerExist(offer.getId())) {
            // OFFER EXISTS
            return offerRepository.save(offer) != null;
        } else {
            // OFFER DOESN'T EXIST
            return false;
        }
    }

    @Override
    public boolean deleteOffer(int id) {
        if(offerExist(id)) {
            // OFFER EXISTS
            offerRepository.deleteById(id);
            return true;
        } else {
            // OFFER DOESN'T EXIST
            return false;
        }
    }
}
