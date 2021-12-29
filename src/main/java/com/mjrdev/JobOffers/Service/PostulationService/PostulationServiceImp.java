package com.mjrdev.JobOffers.Service.PostulationService;

import com.mjrdev.JobOffers.Model.Postulation;
import com.mjrdev.JobOffers.Repository.PostulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulationServiceImp implements PostulationService {

    @Autowired
    private PostulationRepository postulationRepository;

    @Override
    public Postulation savePostulation(Postulation postulation) {
        return postulationRepository.save(postulation);
    }

    @Override
    public List<Postulation> getPostulations() {
        return postulationRepository.findAll();
    }

    @Override
    public List<Postulation> getUserPostulations(int user_id) {
        return postulationRepository.getUserPostulations(user_id);
    }

    @Override
    public List<Postulation> getOfferPostulations(int offer_id) {
        return postulationRepository.getOfferPostulations(offer_id);
    }

    @Override
    public boolean postulationExist(int postulation_id) {
        return postulationRepository.existsById(postulation_id);
    }

    @Override
    public boolean postulationExist(int user_id, int offer_id) {
        return postulationRepository.getUserOfferPostulation(user_id, offer_id) != null;
    }

    @Override
    public boolean deletePostulation(int id) {
        if(postulationExist(id)) {
            postulationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
