package com.mjrdev.JobOffers.Controller;

import com.mjrdev.JobOffers.Model.Category;
import com.mjrdev.JobOffers.Model.User;
import com.mjrdev.JobOffers.Service.CategoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // GET ALL CATEGORIES

    @GetMapping("/all-categories")
    public List<Category> getAllCategories() {
        return categoryService.getCategories();
    }

    // GET CATEGORY BY ID

    @GetMapping("/")
    public HashMap<String, Object> getCategory(@RequestParam(name="id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(categoryService.categoryExist(id)) {
            // CATEGORY EXISTS
            response.put("status", 1);
            response.put("category", categoryService.getCategory(id));
        } else {
            // CATEGORY DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "CATEGORY DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

    // CREATE NEW CATEGORY

    @PostMapping("/new")
    public HashMap<String, Object> createCategory(@RequestBody Category category) {

        HashMap<String, Object> response = new HashMap<>();

        response.put("status", 1);
        response.put("category", categoryService.saveCategory(category));

        return response;
    }

    // UPDATE CATEGORY

    @PutMapping("/update")
    public HashMap<String, Object> updateCategory(@RequestBody Category category) {

        HashMap<String, Object> response = new HashMap<>();

        if(categoryService.categoryExist(category.getId())) {
            // CATEGORY EXISTS
            if(categoryService.updateCategory(category)) {
                // CATEGORY UPDATED
                response.put("status", 1);
                response.put("action", "CATEGORY UPDATED");
            } else {
                // CATEGORY NOT UPDATED
                response.put("status", 0);
                response.put("error", "ERROR DURING UPDATE OPERATION");
            }
        } else {
            // CATEGORY DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "CATEGORY DOESN'T EXIST");
        }

        return response;
    }

    // DELETE CATEGORY

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteCategory(@RequestBody Map<String, Object> payload) {

        int id = Integer.parseInt(String.valueOf(payload.get("id")));

        HashMap<String, Object> response = new HashMap<>();

        if(categoryService.categoryExist(id)) {
            // CATEGORY EXISTS
            if(categoryService.deleteCategory(id)) {
                // CATEGORY DELETED
                response.put("status", 1);
                response.put("action", "CATEGORY DELETED");
            } else {
                // CATEGORY NOT DELETED
                response.put("status", 0);
                response.put("error", "ERROR DURING DELETE OPERATION");
            }
        } else {
            // CATEGORY DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "CATEGORY DOESN'T EXIST");
        }

        return response;
    }

}
