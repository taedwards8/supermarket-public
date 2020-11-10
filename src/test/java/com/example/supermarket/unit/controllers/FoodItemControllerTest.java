package com.example.supermarket.unit.controllers;

import com.example.supermarket.FoodItemController;
import com.example.supermarket.domain.FoodItemLite;
import com.example.supermarket.services.FoodItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FoodItemController.class)
public class FoodItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FoodItemService service;

    FoodItemLite aubergine;

    FoodItemLite courgette;

    List<FoodItemLite> allFoodItems;

    @Before
    public void setup(){
        aubergine = new FoodItemLiteImpl(1, "aubergine", 1, 2);
        courgette = new FoodItemLiteImpl(2, "courgette", 4, 5);
        allFoodItems = Arrays.asList(aubergine, courgette);
    }

    @Test
    public void testAddNewFoodItem() throws Exception {
        //when and then
        mvc.perform(post("/foodItem/addNew")
                .param("name", "sprout")
                .param("price", "2.4")
                .param("quantity", "6")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        verify(service, times(1)).addNewFoodItem("sprout", 2.4, 6);
    }

    @Test
    public void testUpdateFoodItemPrice() throws Exception {
        //when and then
        mvc.perform(post("/foodItem/updatePrice")
                .param("name", "swede")
                .param("price", "1.31")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        verify(service, times(1)).updateFoodItemPrice("swede",1.31);
    }

    @Test
    public void testIncrementFoodItemQuantity() throws Exception {
        //when and then
        int incrementQuantityBy = 4;
        mvc.perform(post("/foodItem/incrementQuantity")
                .param("name", "turnip")
                .param("incrementQuantity", Integer.toString(incrementQuantityBy))).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        verify(service, times(1)).incrementFoodItemQuantity("turnip",incrementQuantityBy);
    }

    @Test
    public void testGetAllFoodItems() throws Exception {
        //given
        when(service.getAllFoodItems()).thenReturn(allFoodItems);

        //when and then
        mvc.perform(get("/foodItem/all")).andDo(print())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(allFoodItems)))
                .andExpect(status().isOk());

        verify(service, times(1)).getAllFoodItems();
    }

    @Test
    public void testGetFoodItemsByName() throws Exception {
        //given
        Collection<String> callArguments = new LinkedHashSet<>();
        callArguments.add("aubergine");
        callArguments.add("courgette");
        when(service.getFoodItemsByName(callArguments)).thenReturn(allFoodItems);

        //when and then
        mvc.perform(get("/foodItem/get/name")
                .param("names", "aubergine, courgette")).andDo(print())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(allFoodItems)))
                .andExpect(status().isOk());

        verify(service, times(1)).getFoodItemsByName(callArguments);
    }

    @Test
    public void testHalfFoodItemPrice() throws Exception {
        //when and then
        mvc.perform(post("/foodItem/halfPrice")
                .param("name", "kale")).andDo(print())
                .andExpect(content().string("Saved"))
                .andExpect(status().isOk());

        verify(service, times(1)).halfPriceOfFoodItem("kale");
    }

    class FoodItemLiteImpl implements FoodItemLite {
        Integer id;
        String name;
        double price;
        int quantity;

        public FoodItemLiteImpl(Integer id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public double getPrice() {
            return price;
        }

        @Override
        public int getQuantity() {
            return quantity;
        }
    }

}
