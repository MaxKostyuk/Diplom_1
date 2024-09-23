import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.List;

public class BurgerTest {
    private static final int INGREDIENT_PRICE = 10;
    private static final int BUN_PRICE = 9;
    private static final Ingredient INGREDIENT = new Ingredient(IngredientType.FILLING, "testIngredient", INGREDIENT_PRICE);
    private static final Bun BUN = new Bun("testBun", BUN_PRICE);
    private static final int DEFAULT_TEST_INDEX = 1;
    private static final int ANOTHER_TEST_INDEX = 3;

    @Mock
    private List<Ingredient> mockList;
    @InjectMocks
    private Burger burger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
        burger.ingredients = mockList;
    }

    @Test
    public void addIngredientShouldCallMockOnce() {
        burger.addIngredient(INGREDIENT);
        Mockito.verify(mockList, Mockito.times(1)).add(INGREDIENT);
        Mockito.verifyNoMoreInteractions(mockList);
    }

    @Test
    public void removeIngredientShouldCallMockOnce() {
        burger.removeIngredient(DEFAULT_TEST_INDEX);
        Mockito.verify(mockList, Mockito.times(1)).remove(DEFAULT_TEST_INDEX);
        Mockito.verifyNoMoreInteractions(mockList);
    }

    @Test
    public void moveIngredientShouldCallRemoveAndAdd() {
        Mockito.when(mockList.remove(DEFAULT_TEST_INDEX)).thenReturn(INGREDIENT);
        burger.moveIngredient(DEFAULT_TEST_INDEX, ANOTHER_TEST_INDEX);
        Mockito.verify(mockList, Mockito.times(1)).remove(DEFAULT_TEST_INDEX);
        Mockito.verify(mockList, Mockito.times(1)).add(ANOTHER_TEST_INDEX, INGREDIENT);
        Mockito.verifyNoMoreInteractions(mockList);
    }

    @Test
    public void getPriceShouldReturnExpectedPrice() {
        burger = new Burger();
        burger.setBuns(BUN);
        burger.addIngredient(INGREDIENT);
        float expectedPrice = 2 * BUN_PRICE + INGREDIENT_PRICE;
        float actualPrice = burger.getPrice();
        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void getPriceWithoutBunShouldThrowNPE() {
        burger = new Burger();
        burger.addIngredient(INGREDIENT);
        Assertions.assertThrowsExactly(NullPointerException.class, () -> burger.getPrice());
    }

    @Test
    public void getPriceWithoutIngredientShouldReturnDoubleBunPrice() {
        burger = new Burger();
        burger.setBuns(BUN);
        Assertions.assertEquals(2 * BUN_PRICE, burger.getPrice());
    }

    @Test
    public void getReceiptShouldReturnExpectedString() {
        burger = new Burger();
        burger.setBuns(BUN);
        burger.addIngredient(INGREDIENT);

        StringBuilder buildingReceipt = new StringBuilder(String.format("(==== %s ====)%n", BUN.getName()));
        buildingReceipt.append(String.format("= %s %s =%n", INGREDIENT.getType().toString().toLowerCase(), INGREDIENT.getName()));
        buildingReceipt.append(String.format("(==== %s ====)%n", BUN.getName()));
        buildingReceipt.append(String.format("%nPrice: %f%n", burger.getPrice()));
        String expectedReceipt = buildingReceipt.toString();

        Assertions.assertEquals(expectedReceipt, burger.getReceipt());
    }

    @Test
    public void getReceiptWithoutBunShouldThrowNPE() {
        burger = new Burger();
        burger.addIngredient(INGREDIENT);
        Assertions.assertThrowsExactly(NullPointerException.class, () -> burger.getReceipt());
    }

    @Test
    public void getReceiptWithoutIngredientShouldReturnExpectedString() {
        burger = new Burger();
        burger.setBuns(BUN);

        StringBuilder buildingReceipt = new StringBuilder(String.format("(==== %s ====)%n", BUN.getName()));
        buildingReceipt.append(String.format("(==== %s ====)%n", BUN.getName()));
        buildingReceipt.append(String.format("%nPrice: %f%n", burger.getPrice()));
        String expectedReceipt = buildingReceipt.toString();

        Assertions.assertEquals(expectedReceipt, burger.getReceipt());
    }

}
