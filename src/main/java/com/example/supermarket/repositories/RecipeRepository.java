package com.example.supermarket.repositories;

import com.example.supermarket.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called recipeRepository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

}
