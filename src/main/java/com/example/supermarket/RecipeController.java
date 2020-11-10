package com.example.supermarket;

import com.example.supermarket.domain.AddRecipeRequest;
import com.example.supermarket.domain.Recipe;
import com.example.supermarket.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    //So only POST requests will be mapped here
    @PostMapping(path="/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String addNewRecipe (@RequestBody AddRecipeRequest addRecipeRequest) {
        // @Response
        // Body means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        recipeService.addNewRecipe(addRecipeRequest);

        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Recipe> getAllRecipes(){
        return recipeService.getAllRecipes();
    }
}
