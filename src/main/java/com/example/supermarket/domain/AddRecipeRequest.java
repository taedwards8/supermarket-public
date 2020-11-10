package com.example.supermarket.domain;

import java.util.List;

public class AddRecipeRequest {

    private String recipeName;
    private List<IngredientNameAndQuantity> ingredientNamesAndQuantities;


    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<IngredientNameAndQuantity> getIngredientNamesAndQuantities() {
        return ingredientNamesAndQuantities;
    }

    public void setIngredientNamesAndQuantities(List<IngredientNameAndQuantity> ingredientNamesAndQuantities) {
        this.ingredientNamesAndQuantities = ingredientNamesAndQuantities;
    }
}
