# Supermarket
The repository acts as a sandbox for me to experiment with different Java tools and technologies.

## Purpose
The repository and database represent a supermarket.  It contains tables to describes food items and recipes involving those food items

## Database Tables
### food_item
* **id** is the ID number for this food item
* **name** is the name of this food item
* **price** is the price of one instance of this food item
* **quantity** is the quantity of this food item currently in stock

### recipe
* **id** is the ID number for this recipe
* **name** is the name of this recipe

### recipe_to_food_item
This is an intermediary table and it is required because a recipe can contain multiple different food items and a food item can appear in multiple recipes
* **recipe_id** is the ID number for the entry in the recipe table
* **food_item_id** is the ID number for the entry in the food_item table
* **food_item_quantity** this is the quantity of the specific food item that are required to create this specific recipe

## Database Procedures
### UPDATE_PRICE_FOR_FOOD_ITEM
```
CREATE PROCEDURE `UPDATE_PRICE_FOR_FOOD_ITEM`(IN p_name varchar(255), IN p_price double)
BEGIN
UPDATE food_item SET price = p_price WHERE name = p_name;
END
```
The UPDATE_PRICE_FOR_FOOD_ITEM procedure didn't actually need to be created as that functionality can be provided by implementing the CrudRepository interface.  I added the procedure to this project so that I could see how to call a procedure from a Java class

## RESTful Endpoints With Examples
Example commands are those run from the windows command line.  The syntax required if you're calling the endpoints via Postman or your web browser    may differ.
### /foodItem
#### /addNew
````
curl -X POST "localhost:8080/foodItem/addNew?name="Sprout"&price=0.25&quantity=9"
````
#### /updatePriced
````
curl -X POST "localhost:8080/foodItem/updatePrice?name="Onion"&price=2.25"
````
#### /incrementQuantity
Increments the quantity of the food item by the incrementQuantity value
````
curl -X POST "localhost:8080/foodItem/incrementQuantity?name="Onion"&incrementQuantity=5"
````
#### /all
````
curl -X GET "localhost:8080/foodItem/all"
````
#### /get/name
This returns the entries from food_item where the item's name matches of one those passed in via the request
````
curl -X GET "localhost:8080/foodItem/get/name?names=Onion,Beef%20Mince"
````
#### /delete
Remove the food item described from the database
````
curl -X DELETE "localhost:8080/foodItem/delete?name="Onion""
````
#### /halfPrice
Update the entry in foot_item, halving its price value
````
curl -X POST "localhost:8080/foodItem/halfPrice?name="Onion""
````
### /recipe
#### /add
This call takes the name of the new recipe followed by a collection containing each of those ingredients along with the quantity of each that is involved in the recipe e.g. in the examples below the recipe is called Curry and will contain one mushroom and two onions.  The input **must in json format**.
````
curl localhost:8080/recipe/add -H "Content-Type: application/json" -X POST -d "{\"recipeName\":\"Curry\",\"ingredientNamesAndQuantities\": [{\"name\": \"Mushroom\", \"quantity\":1}, {\"name\": \"Onion\", \"quantity\":2}]}"
````
#### /all
Returns all the recipes and their ingredients and quantities
````
curl -X GET "localhost:8080/recipe/all"
````
## Main Things Learnt
* My intention was to create and define the database entirely through Java and annotations.
At a basic level I have succeeded as running this application will lead to the creation of the recipe, food_item and recipe_to_food_item tables.
However Spring Boot has trouble with more complication notions like many-to-many relationships and intermediary tables (hence why the RecipeToFoodItemKey class is required) and this has led to issues adding and getting entries to/from the recipe table.
If I was beginning this project again then although I would make use of the CrudRepository interface for simpler database queries I would define the schema itself manually in SQL rather than relying in Spring Annotations.
