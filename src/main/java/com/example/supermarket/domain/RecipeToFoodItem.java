package com.example.supermarket.domain;

import javax.persistence.*;

@Entity
public class RecipeToFoodItem {
    @EmbeddedId
    RecipeToFoodItemKey id;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    Recipe recipe;

    @ManyToOne
    @MapsId("foodItemId")
    @JoinColumn(name = "food_item_id")
    FoodItem foodItem;

    int food_item_quantity;

    public RecipeToFoodItem(Recipe recipe, FoodItem foodItem, int food_item_quantity) {
        this.id = new RecipeToFoodItemKey(recipe.getId(), foodItem.getId());
        this.recipe = recipe;
        this.foodItem = foodItem;
        this.food_item_quantity = food_item_quantity;
    }

    //Needed to add this otherwise RecipeController.addRecipe throws an InstantiationException
    public RecipeToFoodItem() {
    }

    public RecipeToFoodItemKey getId() {
        return id;
    }

    public void setId(RecipeToFoodItemKey id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public int getFood_item_quantity() {
        return food_item_quantity;
    }

    public void setFood_item_quantity(int food_item_quantity) {
        this.food_item_quantity = food_item_quantity;
    }
}
