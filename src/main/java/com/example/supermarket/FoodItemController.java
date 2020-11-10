package com.example.supermarket;

import com.example.supermarket.domain.FoodItemLite;
import com.example.supermarket.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path="/foodItem")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    //So only POST requests will be mapped here
    @PostMapping(path="/addNew")
    public @ResponseBody String addNewFoodItem (@RequestParam String name, @RequestParam double price, @RequestParam int quantity) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        foodItemService.addNewFoodItem(name, price, quantity);

        return "Saved";
    }

    @PostMapping(path="/updatePrice")
    public @ResponseBody String updateFoodItemPrice(@RequestParam String name, @RequestParam double price){
        foodItemService.updateFoodItemPrice(name, price);

        return "Saved";
    }

    @PostMapping(path="/incrementQuantity")
    public @ResponseBody String incrementFoodItemQuantity(@RequestParam String name, @RequestParam int incrementQuantity){
        foodItemService.incrementFoodItemQuantity(name, incrementQuantity);

        return "Saved";
    }

    //FoodItemLite projection used here as we don't want to see all the recipes for each Food Item also returned.
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
    //https://www.baeldung.com/spring-data-jpa-projections
    //As far as I can tell the foodItemRepository method must be called findBy or findAllBy
    @GetMapping(path="/all")
    public @ResponseBody Iterable<FoodItemLite> getAllFoodItems(){
        return foodItemService.getAllFoodItems();
    }

    @GetMapping(path = "/get/name")
    public @ResponseBody Iterable<FoodItemLite> getFoodItemsByName(@RequestParam Collection<String> names){
        return foodItemService.getFoodItemsByName(names);
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteFoodItemByName(@RequestParam String name){
        foodItemService.deleteFoodItemByName(name);

        return "Deleted";
    }

    @PostMapping(path = "/halfPrice")
    public @ResponseBody String halfFoodItemPrice(@RequestParam String name){
        foodItemService.halfPriceOfFoodItem(name);

        return "Saved";
    }
}
