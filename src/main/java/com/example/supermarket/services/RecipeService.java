package com.example.supermarket.services;

import com.example.supermarket.domain.AddRecipeRequest;
import com.example.supermarket.domain.FoodItem;
import com.example.supermarket.domain.IngredientNameAndQuantity;
import com.example.supermarket.domain.Recipe;
import com.example.supermarket.repositories.FoodItemRepository;
import com.example.supermarket.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public void addNewRecipe (AddRecipeRequest addRecipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setName(addRecipeRequest.getRecipeName());

        for(IngredientNameAndQuantity ingredientNameAndQuantity : addRecipeRequest.getIngredientNamesAndQuantities()){
            FoodItem foodItemToAdd = foodItemRepository.findByName(ingredientNameAndQuantity.getName());
            recipe.addFoodItem(foodItemToAdd, ingredientNameAndQuantity.getQuantity());
        }

        recipeRepository.save(recipe);
    }
}
