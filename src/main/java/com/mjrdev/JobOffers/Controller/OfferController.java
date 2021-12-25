package com.mjrdev.JobOffers.Controller;

import com.mjrdev.JobOffers.Model.Category;
import com.mjrdev.JobOffers.Model.Offer;
import com.mjrdev.JobOffers.Model.User;
import com.mjrdev.JobOffers.Service.CategoryService.CategoryService;
import com.mjrdev.JobOffers.Service.OfferService.OfferService;
import com.mjrdev.JobOffers.Service.UserService.UserService;
import com.mjrdev.JobOffers.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/offer")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    // GET ALL OFFERS

    @GetMapping("/all-offers")
    public List<Offer> getAllOffers() {
        return offerService.getOffers();
    }

    // GET OFFER BY ID

    @GetMapping("/id/")
    public HashMap<String, Object> getOfferById(@RequestParam(name="id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(offerService.offerExist(id)) {
            // OFFER EXISTS
            response.put("status", 1);
            response.put("OFFER", offerService.getOffer(id));
        } else {
            // OFFER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "OFFER DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

    // CREATE NEW OFFER

    @PostMapping("/new")
    public HashMap<String, Object> createOffer(@RequestBody Offer offer) {

        HashMap<String, Object> response = new HashMap<>();

        int recruiter_id = offer.getRecruiter().getId();

        if(userService.userExist(recruiter_id)) {
            // USER EXIST
            User user = userService.getUser(recruiter_id);

            if(user.getRole().toLowerCase(Locale.ROOT).equals(Utility.USER_ROLES.get("R").toLowerCase(Locale.ROOT))) {
                // USER IS A RECRUITER
                response.put("status", 1);
                response.put("offer", offerService.saveOffer(offer));
            } else {
                // USER IS NOT A RECRUITER
                response.put("status", 0);
                response.put("error", "THIS USER IS NOT A RECRUITER");
            }
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // UPDATE OFFER

    @PutMapping("/update")
    public HashMap<String, Object> updateOffer(@RequestBody Offer offer) {

        HashMap<String, Object> response = new HashMap<>();

        if(offerService.offerExist(offer.getId())) {
            // OFFER EXISTS
            if(offerService.updateOffer(offer)) {
                // OFFER UPDATED
                response.put("status", 1);
                response.put("action", "OFFER UPDATED");
            } else {
                // OFFER NOT UPDATED
                response.put("status", 0);
                response.put("error", "ERROR DURING UPDATE OPERATION");
            }
        } else {
            // OFFER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "OFFER DOESN'T EXIST");
        }

        return response;
    }
    
    // DELETE OFFER

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteOffer(@RequestParam(name="id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(offerService.offerExist(id)) {
            // OFFER EXISTS
            if(offerService.deleteOffer(id)) {
                // OFFER DELETED
                response.put("status", 1);
                response.put("action", "OFFER DELETED");
            } else {
                // OFFER NOT DELETED
                response.put("status", 0);
                response.put("error", "ERROR DURING DELETE OPERATION");
            }
        } else {
            // OFFER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "OFFER DOESN'T EXIST");
        }

        return response;
    }

    // GET ALL OFFERS BY CATEGORY

    @GetMapping("/all-offers-category")
    public HashMap<String, Object> getOffersByCategory(
            @RequestParam(name="category_id", required = false) Integer category_id,
            @RequestParam(name="limit", required = false) Integer limit
    ) {

        HashMap<String, Object> response = new HashMap<>();

        if(category_id != null) {
            // RETURN OFFER OF ONLY THIS CATEGORY
            if(categoryService.categoryExist(category_id)) {
                // CATEGORY EXISTS

                HashMap<String, Object> category_result = new HashMap<>();
                List<Offer> offerList;

                if(limit != null) {
                    offerList = offerService.getOffersByCategory(category_id, limit);
                } else {
                    offerList = offerService.getOffersByCategory(category_id);
                }

                category_result.put(String.valueOf(category_id), offerList);


                response.put("status", 1);
                response.put("result", category_result);

            } else {
                // CATEGORY DOESN'T EXIST
                response.put("status", 0);
                response.put("error", "CATEGORY DOESN'T EXIST");
            }
        } else {
            // RETURN OFFERS OF ALL CATEGORIES
            HashMap<String, Object> category_result = new HashMap<>();

            List<Category> categoriesList = categoryService.getCategories();

            for(int i = 0; i < categoriesList.size(); i++) {

                int current_id = categoriesList.get(i).getId();

                List<Offer> offerList;

                if(limit != null) {
                    offerList = offerService.getOffersByCategory(current_id, limit);
                } else {
                    offerList = offerService.getOffersByCategory(current_id);
                }

                category_result.put(String.valueOf(current_id), offerList);
            }

            response.put("status", 1);
            response.put("result", category_result);

        }

        return response;
    }

}
