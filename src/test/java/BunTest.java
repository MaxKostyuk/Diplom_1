import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import praktikum.Bun;

public class BunTest {
    private static final String BUN_NAME = "testName";
    private static final float BUN_PRICE = 0.1F;

    @Test
    public void getNameShouldReturnExactSetValue() {
        Bun bun = new Bun(BUN_NAME, BUN_PRICE);
        Assertions.assertEquals(BUN_NAME, bun.getName());
    }

    @Test
    public void getPriceShouldReturnExactSetValue() {
        Bun bun = new Bun(BUN_NAME, BUN_PRICE);
        Assertions.assertEquals(BUN_PRICE, bun.getPrice());
    }

}
