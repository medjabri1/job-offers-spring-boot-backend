package com.mjrdev.JobOffers.Service.CategoryService;

import com.mjrdev.JobOffers.Model.Category;
import com.mjrdev.JobOffers.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(int id) {
        return categoryRepository.getById(id);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean categoryExist(int id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean updateCategory(Category category) {
        if(categoryExist(category.getId())) {
            // Category exists
            return categoryRepository.save(category) != null;
        } else {
            // Category doesn't exist
            return false;
        }
    }

    @Override
    public boolean deleteCategory(int id) {
        if(categoryExist(id)) {
            // Category exists
            categoryRepository.deleteById(id);
            return true;
        } else {
            // Category doesn't exist
            return false;
        }
    }
}
