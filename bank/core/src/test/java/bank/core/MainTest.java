package bank.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    
    @Test
    public void testAssertTrue() {
        Main main = new Main();
        Assertions.assertEquals(main.ex(), 3);
    }

}
