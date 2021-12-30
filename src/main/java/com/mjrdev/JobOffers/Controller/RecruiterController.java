package com.mjrdev.JobOffers.Controller;

import com.mjrdev.JobOffers.Model.Offer;
import com.mjrdev.JobOffers.Model.User;
import com.mjrdev.JobOffers.Service.OfferService.OfferService;
import com.mjrdev.JobOffers.Service.PostulationService.PostulationService;
import com.mjrdev.JobOffers.Service.UserService.UserService;
import com.mjrdev.JobOffers.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/recruiter")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class RecruiterController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostulationService postulationService;

    // GET ALL RECRUITER OFFERS

    @GetMapping("/all-offers")
    public HashMap<String, Object> getAllOffers(@RequestParam(name="recruiter_id") int recruiter_id, @RequestParam(name="closed", required = false) Integer closed_obj) {

        HashMap<String, Object> response = new HashMap<>();

        int closed = closed_obj != null ? closed_obj.intValue() : -1;

        if(userService.userExist(recruiter_id)) {
            // USER EXISTS
            User recruiter = userService.getUser(recruiter_id);
            if(recruiter.getRole().toLowerCase(Locale.ROOT).equals(Utility.USER_ROLES.get("R").toLowerCase(Locale.ROOT))) {
                // USER IS A RECRUITER
                response.put("status", 1);

                if(closed == 0) {
                    // GET RECRUITER OPEN OFFERS
                    response.put("offers", offerService.getRecruiterOffers(recruiter_id, closed));
                    response.put("action", "OPEN OFFERS");

                } else if(closed == 1) {
                    // GET RECRUITER CLOSED OFFERS
                    response.put("offers", offerService.getRecruiterOffers(recruiter_id, closed));
                    response.put("action", "CLOSED OFFERS");

                } else {
                    // GET ALL OFFERS
                    response.put("offers", offerService.getRecruiterOffers(recruiter_id));
                    response.put("action", "ALL OFFERS");

                }

            } else {
                // USER IS NOT A RECRUITER
                response.put("status", 0);
                response.put("error", "USER IS NOT A RECRUITER");
            }
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // GET RECRUITER OVERVIEW STATS

    @GetMapping("/overview")
    public HashMap<String, Object> getOverview(@RequestParam(name="recruiter_id") int recruiter_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(recruiter_id)) {
            // USER EXISTS
            User recruiter = userService.getUser(recruiter_id);
            if(recruiter.getRole().toLowerCase(Locale.ROOT).equals(Utility.USER_ROLES.get("R").toLowerCase(Locale.ROOT))) {

                HashMap<String, Object> overview = new HashMap<>();

                int submissions_count = 0;
                List<Offer> recruiterOffers = offerService.getRecruiterOffers(recruiter_id);

                for(int i = 0; i < recruiterOffers.size(); i++) {
                    Offer currentOffer = recruiterOffers.get(i);
                    submissions_count += postulationService.getOfferPostulations(currentOffer.getId()).size();
                }

                overview.put("total_offers", recruiterOffers.size());
                overview.put("total_open", offerService.getRecruiterOffers(recruiter_id, 0).size());
                overview.put("total_closed", offerService.getRecruiterOffers(recruiter_id, 1).size());
                overview.put("total_submissions", submissions_count);
                overview.put("total_views", 99999);
                overview.put("total_favorites", 99999);

                // USER IS A RECRUITER
                response.put("status", 1);
                response.put("overview", overview);
            } else {
                // USER IS NOT A RECRUITER
                response.put("status", 0);
                response.put("error", "USER IS NOT A RECRUITER");
            }
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // GET RECRUITER OPEN OFFERS
}
