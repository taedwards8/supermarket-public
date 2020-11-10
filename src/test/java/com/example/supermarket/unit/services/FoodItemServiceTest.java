package com.example.supermarket.unit.services;

import com.example.supermarket.domain.FoodItem;
import com.example.supermarket.repositories.FoodItemRepository;
import com.example.supermarket.services.FoodItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FoodItemServiceTest {
    @Autowired
    private FoodItemService foodItemService;

    @MockBean
    private FoodItemRepository repository;

    //See https://www.baeldung.com/spring-boot-testing.
    //Without this @TestConfiguration we would get NoSuchBeanDefinitionException for foodItemService
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public FoodItemService foodItemService() {
            return new FoodItemService();
        }
    }

    @Test
    public void testIncrementFoodItemQuantity() throws Exception {
        //given
        FoodItem turnip = mock(FoodItem.class);
        when(repository.findByName("turnip")).thenReturn(turnip);
        int originalQuantity = 9;
        when(turnip.getQuantity()).thenReturn(originalQuantity);

        //when
        int incrementQuantityBy = 4;
        foodItemService.incrementFoodItemQuantity("turnip", incrementQuantityBy);

        //then
        verify(repository, times(1)).findByName("turnip");
        verify(turnip, times(1)).getQuantity();
        verify(turnip, times(1)).setQuantity(originalQuantity + incrementQuantityBy);
        verify(repository, times(1)).save(turnip);
    }

    @Test
    public void testHalfFoodItemPrice() throws Exception {
        //given
        FoodItem kale = mock(FoodItem.class);
        when(repository.findByName("kale")).thenReturn(kale);
        double originalPrice = 7.36;
        when(kale.getPrice()).thenReturn(originalPrice);

        //when
        foodItemService.halfPriceOfFoodItem("kale");

        //then
        verify(repository, times(1)).findByName("kale");
        verify(repository, times(1)).updatePriceForFoodItem("kale", 3.68);
    }

    @Test
    public void testUpdateFoodItemPrice() throws Exception {
        //given
        FoodItem swede = mock(FoodItem.class);
        when(repository.findByName("swede")).thenReturn(swede);

        //when
        foodItemService.updateFoodItemPrice("swede", 1.31);

        //then
        verify(repository, times(1)).findByName("swede");
        verify(repository, times(1)).save(swede);
        verify(swede, times(1)).setPrice(1.31);
    }
}
