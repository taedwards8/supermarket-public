package com.example.supermarket.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL)
    private Set<RecipeToFoodItem> recipesToFoodItems = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFoodItem(FoodItem foodItem, int quantity) {
        RecipeToFoodItem recipeToFoodItem = new RecipeToFoodItem(this, foodItem, quantity);
        recipesToFoodItems.add(recipeToFoodItem);
        foodItem.getRecipesToFoodItems().add(recipeToFoodItem);
    }

    public Set<RecipeToFoodItem> getRecipesToFoodItems() {
        return recipesToFoodItems;
    }
}
