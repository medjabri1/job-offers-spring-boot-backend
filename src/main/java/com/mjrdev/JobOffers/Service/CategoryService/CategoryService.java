package com.mjrdev.JobOffers.Service.CategoryService;

import com.mjrdev.JobOffers.Model.Category;

import java.util.List;

public interface CategoryService {

    // SAVE CATEGORY
    public Category saveCategory(Category category);

    // GET CATEGORY | Categories
    public Category getCategory(int id);
    public List<Category> getCategories();

    // CHECK EXIST
    public boolean categoryExist(int id);

    // UPDATE CATEGORY
    public boolean updateCategory(Category category);

    // DELETE CATEGORY
    public boolean deleteCategory(int id);

}
