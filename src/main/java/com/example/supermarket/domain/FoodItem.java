package com.example.supermarket.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FoodItem {
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

    private double price;

    private int quantity;

    @OneToMany(
            mappedBy = "foodItem",
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<RecipeToFoodItem> getRecipesToFoodItems() {
        return recipesToFoodItems;
    }

    public void setRecipesToFoodItems(Set<RecipeToFoodItem> recipesToFoodItems) {
        this.recipesToFoodItems = recipesToFoodItems;
    }
}
