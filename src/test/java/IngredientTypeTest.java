import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import praktikum.IngredientType;

public class IngredientTypeTest {

    @Test
    public void classIncludesOnlyExpectedValues() {
            IngredientType[] expectedValues = {IngredientType.SAUCE, IngredientType.FILLING};
            Assertions.assertArrayEquals(expectedValues, IngredientType.values());
        }
}
