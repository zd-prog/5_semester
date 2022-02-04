package by.ziziko;

import java.io.Serializable;

public class Recipe implements Comparable<Recipe>, Serializable {

    public String name;
    public String category;
    public String ingredients;
    public String recipe;
    public String time;

    Recipe(String name, String category, String ingredients, String recipe, String time)
    {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.time = time;
    }

    @Override
    public int compareTo(Recipe recipe) {
        return name.compareTo(recipe.name);
    }
}
