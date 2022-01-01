package com.mjrdev.JobOffers.Controller;

import com.mjrdev.JobOffers.Model.Postulation;
import com.mjrdev.JobOffers.Service.PostulationService.PostulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/postulation")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class PostulationController {

    @Autowired
    private PostulationService postulationService;

    // ADD NEW POSTULATION

    @PostMapping("/new")
    public HashMap<String, Object> addNewPostulation(@RequestBody Postulation postulation) {

        HashMap<String, Object> response = new HashMap<>();

        if(!postulationService.postulationExist(postulation.getApplier().getId(), postulation.getOffer().getId())) {
            // USER DID NOT APPLY FOR THIS OFFER BEFORE
            response.put("status", 1);
            response.put("postulation", postulationService.savePostulation(postulation));
        } else {
            // USER ALREADY APPLIED TO THIS OFFER
            response.put("status", 0);
            response.put("error", "USER ALREADY APPLIED FOR THIS OFFER");
        }

        return response;
    }

    // GET ALL POSTULATIONS

    @GetMapping("/all-postulations")
    public HashMap<String, Object> getAllPostulations() {

        HashMap<String, Object> response = new HashMap<>();

        response.put("status", 1);
        response.put("postulations", postulationService.getPostulations());

        return response;
    }

    // GET USER POSTULATIONS

    @GetMapping("/user/")
    public HashMap<String, Object> getUserPostulations(@RequestParam(name="user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        response.put("status", 1);
        response.put("postulations", postulationService.getUserPostulations(user_id));

        return response;
    }

    // GET OFFER POSTULATIONS

    @GetMapping("/offer/")
    public HashMap<String, Object> getOfferPostulations(@RequestParam(name="offer_id") int offer_id) {

        HashMap<String, Object> response = new HashMap<>();

        response.put("status", 1);
        response.put("postulations", postulationService.getOfferPostulations(offer_id));
        response.put("total_submissions", postulationService.getOfferPostulations(offer_id).size());

        return response;
    }

    // CHECK POSTULATION USER/OFFER EXIST

    @GetMapping("/exist")
    public HashMap<String, Object> checkExist(@RequestParam(name="user_id") int user_id, @RequestParam(name="offer_id") int offer_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(postulationService.postulationExist(user_id, offer_id)) {
            response.put("status", 1);
            response.put("exist", 1);
        } else {
            response.put("status", 1);
            response.put("exist", 0);
        }

        return response;
    }

    // DELETE POSTULATION BY ID

    @DeleteMapping("/delete")
    public HashMap<String, Object> deletePostulation(@RequestParam(name="postulation_id") int postulation_id) {
        HashMap<String, Object> response = new HashMap<>();

        if(postulationService.deletePostulation(postulation_id)) {
            response.put("status", 1);
        } else {
            response.put("status", 0);
        }

        return response;
    }

}
