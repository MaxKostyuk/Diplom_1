import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void getPriceShouldReturnExpectedPrice(int numberOfIngredients) {
        burger.setBuns(BUN);
        for (int i = 0; i < numberOfIngredients; i++)
            burger.addIngredient(INGREDIENT);
        float expectedPrice = getExpectedPrice(numberOfIngredients);

        Assertions.assertEquals(expectedPrice, burger.getPrice());
    }

    @Test
    public void getPriceWithoutBunShouldThrowNPE() {
        burger.addIngredient(INGREDIENT);
        Assertions.assertThrowsExactly(NullPointerException.class, () -> burger.getPrice());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void getReceiptShouldReturnExpectedString(int numOfIngredients) {
        Bun mockBun = Mockito.mock(Bun.class);
        Ingredient mockIngredient = Mockito.mock(Ingredient.class);
        Mockito.when(mockBun.getName()).thenReturn(BUN_NAME);
        Mockito.when(mockBun.getPrice()).thenReturn(BUN_PRICE);
        Mockito.when(mockIngredient.getName()).thenReturn(INGREDIENT_NAME);
        Mockito.when(mockIngredient.getType()).thenReturn(INGREDIENT_TYPE);
        Mockito.when(mockIngredient.getPrice()).thenReturn(INGREDIENT_PRICE);
        String expectedReceipt = getExpectedReceipt(numOfIngredients);

        burger.setBuns(BUN);
        for (int i = 0; i < numOfIngredients; i++)
            burger.addIngredient(INGREDIENT);

        Assertions.assertEquals(expectedReceipt, burger.getReceipt());
    }

    @Test
    public void getReceiptWithoutBunShouldThrowNPE() {
        burger.addIngredient(INGREDIENT);
        Assertions.assertThrowsExactly(NullPointerException.class, () -> burger.getReceipt());
    }

    private float getExpectedPrice(int numOfIngredients) {
        return BUN_PRICE * 2 + INGREDIENT_PRICE * numOfIngredients;
    }

    private String getExpectedReceipt(int numOfIngredients) {
        StringBuilder buildingReceipt = new StringBuilder(String.format("(==== %s ====)%n", BUN_NAME));
        for (int i = 0; i < numOfIngredients; i++)
            buildingReceipt.append(String.format("= %s %s =%n", INGREDIENT_TYPE.toString().toLowerCase(), INGREDIENT_NAME));
        buildingReceipt.append(String.format("(==== %s ====)%n", BUN_NAME));
        buildingReceipt.append(String.format("%nPrice: %f%n", getExpectedPrice(numOfIngredients)));
        String expectedReceipt = buildingReceipt.toString();
        return expectedReceipt;
    }
}
