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
    private static final float INGREDIENT_PRICE = 10;
    private static final float BUN_PRICE = 9;
    private static final String BUN_NAME = "testBun";
    private static final String INGREDIENT_NAME = "testIngredient";
    private static final IngredientType INGREDIENT_TYPE = IngredientType.FILLING;
    private static final Ingredient INGREDIENT = new Ingredient(INGREDIENT_TYPE, INGREDIENT_NAME, INGREDIENT_PRICE);
    private static final Bun BUN = new Bun(BUN_NAME, BUN_PRICE);
    private static final int DEFAULT_TEST_INDEX = 1;
    private static final int ANOTHER_TEST_INDEX = 3;
    private Burger burger;

    @Mock
    private List<Ingredient> mockList;
    @InjectMocks
    private Burger mockedBurger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedBurger = new Burger();
        mockedBurger.ingredients = mockList;
        burger = new Burger();
    }

    @Test
    public void addIngredientShouldCallMockOnce() {
        mockedBurger.addIngredient(INGREDIENT);
        Mockito.verify(mockList, Mockito.times(1)).add(INGREDIENT);
        Mockito.verifyNoMoreInteractions(mockList);
    }

    @Test
    public void removeIngredientShouldCallMockOnce() {
        mockedBurger.removeIngredient(DEFAULT_TEST_INDEX);
        Mockito.verify(mockList, Mockito.times(1)).remove(DEFAULT_TEST_INDEX);
        Mockito.verifyNoMoreInteractions(mockList);
    }

    @Test
    public void moveIngredientShouldCallRemoveAndAdd() {
        Mockito.when(mockList.remove(DEFAULT_TEST_INDEX)).thenReturn(INGREDIENT);
        mockedBurger.moveIngredient(DEFAULT_TEST_INDEX, ANOTHER_TEST_INDEX);
        Mockito.verify(mockList, Mockito.times(1)).remove(DEFAULT_TEST_INDEX);
        Mockito.verify(mockList, Mockito.times(1)).add(ANOTHER_TEST_INDEX, INGREDIENT);
        Mockito.verifyNoMoreInteractions(mockList);
    }

    @Test
    public void getPriceShouldReturnExpectedPrice() {
        burger.setBuns(BUN);
        burger.addIngredient(INGREDIENT);
        float expectedPrice = 2 * BUN_PRICE + INGREDIENT_PRICE;
        float actualPrice = burger.getPrice();
        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void getPriceWithoutBunShouldThrowNPE() {
        burger.addIngredient(INGREDIENT);
        Assertions.assertThrowsExactly(NullPointerException.class, () -> burger.getPrice());
    }

    @Test
    public void getPriceWithoutIngredientShouldReturnDoubleBunPrice() {
        burger.setBuns(BUN);
        Assertions.assertEquals(2 * BUN_PRICE, burger.getPrice());
    }

    @Test
    public void getReceiptShouldReturnExpectedString() {
        Bun mockBun = Mockito.mock(Bun.class);
        Ingredient mockIngredient = Mockito.mock(Ingredient.class);
        Mockito.when(mockBun.getName()).thenReturn(BUN_NAME);
        Mockito.when(mockBun.getPrice()).thenReturn(BUN_PRICE);
        Mockito.when(mockIngredient.getName()).thenReturn(INGREDIENT_NAME);
        Mockito.when(mockIngredient.getType()).thenReturn(INGREDIENT_TYPE);
        Mockito.when(mockIngredient.getPrice()).thenReturn(INGREDIENT_PRICE);
        float expectedPrice = 2 * BUN_PRICE + INGREDIENT_PRICE;

        burger.setBuns(BUN);
        burger.addIngredient(INGREDIENT);

        StringBuilder buildingReceipt = new StringBuilder(String.format("(==== %s ====)%n", BUN_NAME));
        buildingReceipt.append(String.format("= %s %s =%n", INGREDIENT_TYPE.toString().toLowerCase(), INGREDIENT_NAME));
        buildingReceipt.append(String.format("(==== %s ====)%n", BUN_NAME));
        buildingReceipt.append(String.format("%nPrice: %f%n", expectedPrice));
        String expectedReceipt = buildingReceipt.toString();

        Assertions.assertEquals(expectedReceipt, burger.getReceipt());
    }

    @Test
    public void getReceiptWithoutBunShouldThrowNPE() {
        burger.addIngredient(INGREDIENT);
        Assertions.assertThrowsExactly(NullPointerException.class, () -> burger.getReceipt());
    }

    @Test
    public void getReceiptWithoutIngredientShouldReturnExpectedString() {
        Bun mockBun = Mockito.mock(Bun.class);
        Mockito.when(mockBun.getName()).thenReturn(BUN_NAME);
        Mockito.when(mockBun.getPrice()).thenReturn(BUN_PRICE);
        float expectedPrice = 2 * BUN_PRICE;

        burger.setBuns(mockBun);

        StringBuilder buildingReceipt = new StringBuilder(String.format("(==== %s ====)%n", BUN_NAME));
        buildingReceipt.append(String.format("(==== %s ====)%n", BUN_NAME));
        buildingReceipt.append(String.format("%nPrice: %f%n", expectedPrice));
        String expectedReceipt = buildingReceipt.toString();

        Assertions.assertEquals(expectedReceipt, burger.getReceipt());
    }

}
