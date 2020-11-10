package com.example.supermarket.repositories;

import com.example.supermarket.domain.FoodItem;
import com.example.supermarket.domain.FoodItemLite;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called foodItemRepository
public interface FoodItemRepository extends CrudRepository<FoodItem, Integer> {
    //The query to run on DB is magically filled in based on the name of the method and the arguments.
    //See https://stackoverflow.com/questions/32796419/crudrepository-findby-method-signature-with-multiple-in-operators

    FoodItem findByName(String name);

    List<FoodItemLite> findBy();

    List<FoodItemLite> findByNameIn(Collection<String> names);

    void deleteByName(String name);

    //A specific stored procedure isn't really required in order to update the price, but I'm using one in order to
    //demonstrate how to call stored procedures
    @Procedure(procedureName = "UPDATE_PRICE_FOR_FOOD_ITEM")
    void updatePriceForFoodItem(String p_name, double p_price);
}

