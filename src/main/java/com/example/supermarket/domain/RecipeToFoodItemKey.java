package com.example.supermarket.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeToFoodItemKey implements Serializable {
    @Column(name="recipe_id")
    Integer recipeId;

    @Column(name="food_item_id")
    Integer foodItemId;

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    public RecipeToFoodItemKey(Integer recipeId, Integer foodItemId) {
        this.recipeId = recipeId;
        this.foodItemId = foodItemId;
    }

    //Needed to add this otherwise RecipeController.addRecipe throws an InstantiationException
    public RecipeToFoodItemKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeToFoodItemKey that = (RecipeToFoodItemKey) o;
        return Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(foodItemId, that.foodItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, foodItemId);
    }
}
