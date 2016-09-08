package logic.controller;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Test class for URIGenerator
 */
@Test()
public class URIGeneratorTest {
    @DataProvider(name = "URIdataSingle")
    private Object[][] provideURIData() {
        return new Object[][]{
                new Object[]{"http://www.something.com/books/ebook?list=###", 4, "http://www.something.com/books/ebook?list=4"},
                new Object[]{"http://www.something.com/books/ebook?list=###&sortBy=asc", 0, "http://www.something.com/books/ebook?list=0&sortBy=asc"}
        };
    }

    @DataProvider(name = "URIsequenceSingleArg")
    private Object[][] provideSequenceWithSingleArgs() {
        final URIGenerator generator = new URIGenerator("http://www.something.com/logic.book=###");

        return new Object[][]{
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/logic.book=1"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/logic.book=2"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/logic.book=3"}
        };
    }

    @DataProvider(name = "URISequenceWithDelta")
    private Object[][] provideSequenceWithNonDefaultDeltaConstructorArg() {
        final URIGenerator generator = new URIGenerator("http://www.something.com/logic.book=###", 40);

        return new Object[][]{
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/logic.book=1"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/logic.book=41"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/logic.book=81"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/logic.book=121"}
        };
    }

    /**
     * Test that checks the incremental behavior of URIGenerator.
     *
     * @param actual   - generated link
     * @param expected - expected link
     */
    @Test(dataProvider = "URIsequenceSingleArg")
    public void testGeneratingSequenceWithSingleArg(String actual, String expected) {
        // then
        assertEquals(actual, expected);
    }

    /**
     * Test of HashCode calculated by HashCodeBuilder.
     */
    @Test
    public void hashCodeTest() {
        // given
        URIGenerator u1 = new URIGenerator("aa");
        URIGenerator u2 = new URIGenerator("bb");

        // when
        int u1Hash = u1.hashCode();
        int u2Hash = u2.hashCode();

        // then
        assertTrue(u1Hash != u2Hash);
    }


    /**
     * Equality test.
     */
    @Test
    public void equalsTest() {
        // given
        URIGenerator u1 = new URIGenerator("aa");
        URIGenerator u2 = new URIGenerator("bb");
        URIGenerator u3 = new URIGenerator("aa");

        // when
        u3.generateNextFullURI();

        // then
        assertTrue(u1.equals(u1));
        assertTrue(u2.equals(u2));
        assertFalse(u1.equals(u2));
        assertFalse(u1.equals(null));
        assertFalse(u1.equals(new Integer(4)));
        assertFalse(u1.equals(u3));
        assertFalse(u2.equals(u1.generateNextFullURI()));
    }

    /**
     * Hashcode test with deltas
     */
    @Test
    public void hashCodeTestWithDeltas() {
        // given
        URIGenerator u1 = new URIGenerator("aa", 40);
        URIGenerator u2 = new URIGenerator("bb", 40);
        URIGenerator u3 = new URIGenerator("aa");
        URIGenerator u4 = new URIGenerator("aa", 40);

        // when, then
        assertEquals(u1.hashCode(), u1.hashCode());
        assertNotEquals(u1.hashCode(), u2.hashCode());
        assertNotEquals(u2.hashCode(), u3.hashCode());
        assertNotEquals(u1.hashCode(), u3.hashCode());
        assertEquals(u1.hashCode(), u4.hashCode());
    }

    /**
     * Equality test with deltas
     */
    @Test
    public void equalityTestWithDeltas() {
        // given
        URIGenerator u1 = new URIGenerator("aa", 40);
        URIGenerator u2 = new URIGenerator("bb", 40);
        URIGenerator u3 = new URIGenerator("aa");
        URIGenerator u4 = new URIGenerator("aa", 40);

        // when, then
        assertFalse(u1.equals(u2));
        assertFalse(u2.equals(u1));
        assertFalse(u1.equals(u3));
        assertFalse(u3.equals(u1));
        assertTrue(u1.equals(u1));
        assertTrue(u1.equals(u4));
        assertTrue(u4.equals(u1));
    }

    /**
     * Sequence test with deltas
     *
     * @param actual   generated value
     * @param expected expected value
     */
    @Test(dataProvider = "URISequenceWithDelta")
    public void sequnceWithDeltasTest(String actual, String expected) {
        // then
        assertEquals(actual, expected);
    }
}
