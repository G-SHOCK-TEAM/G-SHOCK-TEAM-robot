import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by daniel on 24.08.16.
 */
public class TestTest {

    @Test
    public void testTest() {
        assertTrue(new TestClass().returnTrue());
    }
}
