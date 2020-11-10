package com.example.supermarket.services;

import com.example.supermarket.domain.FoodItem;
import com.example.supermarket.domain.FoodItemLite;
import com.example.supermarket.repositories.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class FoodItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;

    public void deleteFoodItemByName(String name) {
        foodItemRepository.deleteByName(name);
    }

    public void halfPriceOfFoodItem(String name){
        FoodItem foodItem = foodItemRepository.findByName(name);
        double newPrice = getHalvedPrice(foodItem.getPrice());
        foodItemRepository.updatePriceForFoodItem(name, newPrice);
    }

    // Doesn't really need to be extracted out of halfPriceOfFoodItem but I extracted it so my Cucumber tests would have something to run
    public double getHalvedPrice(double currentPrice) {
        return currentPrice/2;
    }

    public Iterable<FoodItemLite> getFoodItemsByName(Collection<String> names){
        return foodItemRepository.findByNameIn(names);
    }

    //FoodItemLite projection used here as we don't want to see all the recipes for each Food Item also returned.
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
    //https://www.baeldung.com/spring-data-jpa-projections
    //As far as I can tell the foodItemRepository method must be called findBy or findAllBy
    public Iterable<FoodItemLite> getAllFoodItems(){
        return foodItemRepository.findBy();
    }

    public void incrementFoodItemQuantity(String name, int incrementQuantity){
        FoodItem foodItem = foodItemRepository.findByName(name);
        int currentQuantity = foodItem.getQuantity();
        foodItem.setQuantity(currentQuantity + incrementQuantity);
        foodItemRepository.save(foodItem);
    }

    public void updateFoodItemPrice(String name, double price){
        FoodItem foodItem = foodItemRepository.findByName(name);
        foodItem.setPrice(price);
        foodItemRepository.save(foodItem);
    }

    public void addNewFoodItem (String name, double price, int quantity) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(name);
        foodItem.setPrice(price);
        foodItem.setQuantity(quantity);

        foodItemRepository.save(foodItem);
    }
}
