import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import praktikum.Ingredient;
import praktikum.IngredientType;

public class IngredientTest {
    private static final IngredientType INGREDIENT_TYPE = IngredientType.FILLING;
    private static final String INGREDIENT_NAME = "testName";
    private static final float INGREDIENT_PRICE = 0.1F;

    @Test
    public void getTypeShouldReturnSetValue() {
        Ingredient ingredient = new Ingredient(INGREDIENT_TYPE, INGREDIENT_NAME, INGREDIENT_PRICE);
        Assertions.assertEquals(INGREDIENT_TYPE, ingredient.getType());
    }

    @Test
    public void getNameShouldReturnSetValue() {
        Ingredient ingredient = new Ingredient(INGREDIENT_TYPE, INGREDIENT_NAME, INGREDIENT_PRICE);
        Assertions.assertEquals(INGREDIENT_NAME, ingredient.getName());
    }

    @Test
    public void getPriceShouldReturnSetValue() {
        Ingredient ingredient = new Ingredient(INGREDIENT_TYPE, INGREDIENT_NAME, INGREDIENT_PRICE);
        Assertions.assertEquals(INGREDIENT_PRICE, ingredient.getPrice());
    }
}
