package com.mjrdev.JobOffers.Controller;

import com.mjrdev.JobOffers.Model.User;
import com.mjrdev.JobOffers.Service.OfferService.OfferService;
import com.mjrdev.JobOffers.Service.UserService.UserService;
import com.mjrdev.JobOffers.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;

@RestController
@RequestMapping("/api/recruiter")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class RecruiterController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    // GET ALL RECRUITER OFFERS

    @GetMapping("/all-offers")
    public HashMap<String, Object> getAllOffers(@RequestParam(name="recruiter_id") int recruiter_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(recruiter_id)) {
            // USER EXISTS
            User recruiter = userService.getUser(recruiter_id);
            if(recruiter.getRole().toLowerCase(Locale.ROOT).equals(Utility.USER_ROLES.get("R").toLowerCase(Locale.ROOT))) {
                // USER IS A RECRUITER
                response.put("status", 1);
                response.put("offers", offerService.getRecruiterOffers(recruiter_id));
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

                overview.put("total_offers", offerService.getRecruiterOffers(recruiter_id).size());
                overview.put("total_open", offerService.getRecruiterOffers(recruiter_id, 0).size());
                overview.put("total_closed", offerService.getRecruiterOffers(recruiter_id, 1).size());
                overview.put("total_submissions", 99999);
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
}
