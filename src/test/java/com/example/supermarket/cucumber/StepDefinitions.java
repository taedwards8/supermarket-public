package com.example.supermarket.cucumber;

import com.example.supermarket.services.FoodItemService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {
    double price;
    double answer;
    FoodItemService foodItemService;


    @Given("price is {double}")
    public void price_is(double price){
        this.price = price;
        foodItemService = new FoodItemService();
    }

    @When("I say to half it")
    public void i_say_to_half_it() {
        answer = foodItemService.getHalvedPrice(price);
    }

    @Then("I should be told {double}")
    public void i_should_be_told(double expectedAnswer){
        assertThat(answer).isEqualTo(expectedAnswer);
    }
}
