package com.example.supermarket.integration.controllers;

import com.example.supermarket.repositories.FoodItemRepository;
import com.example.supermarket.services.FoodItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FoodItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private FoodItemRepository repository;

    @Autowired
    private FoodItemService service;

    private void addTestVegetable() throws Exception {
        mvc.perform(post("/foodItem/addNew")
                .param("name", "testVegetable")
                .param("price", "2.4")
                .param("quantity", "6")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());
    }

    private void deleteTestVegetable() throws Exception {
        mvc.perform(delete("/foodItem/delete")
                .param("name", "testVegetable")).andDo(print())
                .andExpect(content().string("Deleted"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddNewFoodItem() throws Exception {
        //confirm that testVegetable isn't in the database when we begin
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());

        //add testVegetable
        addTestVegetable();

        //confirm that testVegetable is now in the database
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("testVegetable")))
                .andExpect(jsonPath("$[0].price", is(2.4)))
                .andExpect(jsonPath("$[0].quantity", is(6)))
                .andExpect(status().isOk());

        //delete the testVegetable
        deleteTestVegetable();
    }

    @Test
    public void testUpdateFoodItemPrice() throws Exception {
        //add testVegetable
        addTestVegetable();

        //confirm that testVegetable is now in the database and has the original price
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(2.4)))
                .andExpect(status().isOk());

        //update testVegetable price
        mvc.perform(post("/foodItem/updatePrice")
                .param("name", "testVegetable")
                .param("price", "4.82")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        //confirm that testVegetable has the new price
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(4.82)))
                .andExpect(status().isOk());

        //delete the testVegetable
        deleteTestVegetable();
    }

    @Test
    public void testIncrementFoodItemQuantity() throws Exception {
        //add testVegetable
        addTestVegetable();

        //confirm that testVegetable is now in the database and has the original quantity
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].quantity", is(6)))
                .andExpect(status().isOk());

        //update testVegetable quantity
        mvc.perform(post("/foodItem/incrementQuantity")
                .param("name", "testVegetable")
                .param("incrementQuantity", "4")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        //confirm that testVegetable has the new quantity
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].quantity", is(10)))
                .andExpect(status().isOk());

        //delete the testVegetable
        deleteTestVegetable();
    }

    @Test
    public void testGetAllFoodItems() throws Exception {
        mvc.perform(get("/foodItem/all")).andDo(print())
                .andExpect(jsonPath("$.size()", greaterThan(0)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFoodItemsByName() throws Exception {

        //add testVegetable
        addTestVegetable();

        //add secondTestVegetable
        mvc.perform(post("/foodItem/addNew")
                .param("name", "secondTestVegetable")
                .param("price", "4.4")
                .param("quantity", "8")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        //confirm that testVegetable and secondTestVegetable are returned
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable,secondTestVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("testVegetable")))
                .andExpect(jsonPath("$[1].price", is(2.4)))
                .andExpect(jsonPath("$[1].quantity", is(6)))
                .andExpect(jsonPath("$[0].name", is("secondTestVegetable")))
                .andExpect(jsonPath("$[0].price", is(4.4)))
                .andExpect(jsonPath("$[0].quantity", is(8)))
                .andExpect(status().isOk());

        //delete the testVegetable
        deleteTestVegetable();

        //delete the secondTestVegetable
        mvc.perform(delete("/foodItem/delete")
                .param("name", "secondTestVegetable")).andDo(print())
                .andExpect(content().string("Deleted"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHalfFoodItemPrice() throws Exception {
        //add testVegetable
        addTestVegetable();

        //confirm that testVegetable is now in the database and has the original quantity
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(2.4)))
                .andExpect(status().isOk());

        //update testVegetable quantity
        mvc.perform(post("/foodItem/halfPrice")
                .param("name", "testVegetable")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        //confirm that testVegetable's price has now halved
        mvc.perform(get("/foodItem/get/name")
                .param("names", "testVegetable")).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(1.2)))
                .andExpect(status().isOk());

        //delete the testVegetable
        deleteTestVegetable();
    }
}
