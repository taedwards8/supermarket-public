package com.example.supermarket;

import com.example.supermarket.domain.FoodItem;
import com.example.supermarket.domain.Recipe;
import com.example.supermarket.domain.RecipeToFoodItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamingPractice {
    FoodItem onion;
    FoodItem chicken;
    FoodItem bread;

    Recipe bolognaise;
    Recipe chilli;

    List<FoodItem> allStock;
    List<Recipe> allRecipes;

    @Before
    public void setup() {
        onion = new FoodItem();
        onion.setPrice(2);
        onion.setQuantity(5);
        onion.setName("onion");

        chicken = new FoodItem();
        chicken.setName("chicken");
        chicken.setPrice(4);
        chicken.setQuantity(10);

        bread = new FoodItem();
        bread.setName("bread");
        bread.setPrice(0.75);
        bread.setQuantity(4);

        bolognaise = new Recipe();
        bolognaise.setName("bolognaise");
        bolognaise.addFoodItem(onion, 3);
        bolognaise.addFoodItem(chicken, 2);

        chilli = new Recipe();
        chilli.setName("chilli");
        chilli.addFoodItem(chicken, 1);
        chilli.addFoodItem(bread, 3);

        allStock = new ArrayList();
        allStock.add(onion);
        allStock.add(chicken);
        allStock.add(bread);

        allRecipes = new ArrayList<>();
        allRecipes.add(bolognaise);
        allRecipes.add(chilli);
    }

    @Test
    public void testTotalPriceOfRecipes() {
        Collection<RecipeToFoodItem> allRecipeToFoodItems = allRecipes.stream().flatMap(recipe -> recipe.getRecipesToFoodItems().stream()).collect(Collectors.toList());

        Map<FoodItem, Integer> foodItemToQuantities = new HashMap<>();

        //Merge together the entries in allRecipeToFoodItems so we end up with a mapping from FoodItem to the total quantity of that food item across all of allRecipes
        for (RecipeToFoodItem recipeToFoodItem : allRecipeToFoodItems) {
            foodItemToQuantities.merge(recipeToFoodItem.getFoodItem(), recipeToFoodItem.getFood_item_quantity(), (a, b) -> a + b);
        }

        double totalPrice = foodItemToQuantities.entrySet().stream().map(foodItemToQuantity -> foodItemToQuantity.getKey().getPrice() * foodItemToQuantity.getValue()).reduce(0.0, (a, b) -> a + b);


        Assert.assertEquals(20.25, totalPrice, 0);
    }

    @Test
    public void testSumTotalNumberOfFoodItems() {
        int totalStock = allStock.stream().map(foodItem -> foodItem.getQuantity()).reduce(0, (a, b) -> a + b);
        Assert.assertEquals(19, totalStock);
    }

    @Test
    public void testSumTotalOfPriceOfFoodItems() {
        double totalPrice = allStock.stream().map(foodItem -> foodItem.getQuantity() * foodItem.getPrice()).reduce(new Double(0), (a, b) -> a + b);
        Assert.assertEquals(53, totalPrice, 0);
    }
}
