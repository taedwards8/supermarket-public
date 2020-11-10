package com.example.supermarket.unit.repositories;

import com.example.supermarket.domain.FoodItem;
import com.example.supermarket.domain.FoodItemLite;
import com.example.supermarket.repositories.FoodItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//This is integration testing our persistence layer.  @DataJpaTest means we'll use an in memory database rather than our actual MySQL database

@RunWith(SpringRunner.class)
@DataJpaTest
public class FoodItemRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FoodItemRepository foodItemRepository;

    private FoodItem broccoli;

    private FoodItem parsnip;

    private FoodItem turnip;

    @Before
    public void setup(){
        broccoli = new FoodItem();
        broccoli.setName("broccoli");
        broccoli.setQuantity(10);
        broccoli.setPrice(3.5);

        parsnip = new FoodItem();
        parsnip.setName("parsnip");
        parsnip.setQuantity(5);
        parsnip.setPrice(6.21);

        turnip = new FoodItem();
        turnip.setName("turnip");
        turnip.setQuantity(2);
        turnip.setPrice(9.08);

        entityManager.persist(broccoli);
        entityManager.persist(parsnip);
        entityManager.persist(turnip);
        entityManager.flush();
    }

    @Test
    public void testFindByName() {
        //when
        FoodItem returnedFoodItem = foodItemRepository.findByName("broccoli");

        //then
        assertThat(returnedFoodItem).isEqualTo(broccoli);
    }

    @Test
    public void testFindBy(){
        //when
        List<FoodItemLite> returnedFoodItemLites = foodItemRepository.findBy();

        //then
        //Because FoodItemLite is an interface rather than a concrete class we have to compare field by field rather than by entire object
        assertThat(returnedFoodItemLites.size()).isEqualTo(3);

        FoodItemLite firstItemRetrieved = returnedFoodItemLites.get(0);
        assertThat(firstItemRetrieved.getName()).isEqualTo("broccoli");
        assertThat(firstItemRetrieved.getQuantity()).isEqualTo(10);
        assertThat(firstItemRetrieved.getPrice()).isEqualTo(3.5);

        FoodItemLite secondItemRetrieved = returnedFoodItemLites.get(1);
        assertThat(secondItemRetrieved.getName()).isEqualTo("parsnip");
        assertThat(secondItemRetrieved.getQuantity()).isEqualTo(5);
        assertThat(secondItemRetrieved.getPrice()).isEqualTo(6.21);

        FoodItemLite thirdItemRetrieved = returnedFoodItemLites.get(2);
        assertThat(thirdItemRetrieved.getName()).isEqualTo("turnip");
        assertThat(thirdItemRetrieved.getQuantity()).isEqualTo(2);
        assertThat(thirdItemRetrieved.getPrice()).isEqualTo(9.08);
    }

    @Test
    public void testFindByNameIn(){
        //when
        List<FoodItemLite> returnedFoodItemLites = foodItemRepository.findByNameIn(Arrays.asList("parsnip", "turnip"));

        //then
        //Because FoodItemLite is an interface rather than a concrete class we have to compare field by field rather than by entire object
        assertThat(returnedFoodItemLites.size()).isEqualTo(2);

        FoodItemLite secondItemRetrieved = returnedFoodItemLites.get(0);
        assertThat(secondItemRetrieved.getName()).isEqualTo("parsnip");
        assertThat(secondItemRetrieved.getQuantity()).isEqualTo(5);
        assertThat(secondItemRetrieved.getPrice()).isEqualTo(6.21);

        FoodItemLite thirdItemRetrieved = returnedFoodItemLites.get(1);
        assertThat(thirdItemRetrieved.getName()).isEqualTo("turnip");
        assertThat(thirdItemRetrieved.getQuantity()).isEqualTo(2);
        assertThat(thirdItemRetrieved.getPrice()).isEqualTo(9.08);
    }

    @Test
    public void testDeleteByName(){
        //when
        foodItemRepository.deleteByName("parsnip");
        List<FoodItemLite> returnedFoodItemLites = foodItemRepository.findBy();

        //then
        //Because FoodItemLite is an interface rather than a concrete class we have to compare field by field rather than by entire object
        assertThat(returnedFoodItemLites.size()).isEqualTo(2);

        FoodItemLite firstItemRetrieved = returnedFoodItemLites.get(0);
        assertThat(firstItemRetrieved.getName()).isEqualTo("broccoli");
        assertThat(firstItemRetrieved.getQuantity()).isEqualTo(10);
        assertThat(firstItemRetrieved.getPrice()).isEqualTo(3.5);

        FoodItemLite thirdItemRetrieved = returnedFoodItemLites.get(1);
        assertThat(thirdItemRetrieved.getName()).isEqualTo("turnip");
        assertThat(thirdItemRetrieved.getQuantity()).isEqualTo(2);
        assertThat(thirdItemRetrieved.getPrice()).isEqualTo(9.08);
    }
}
