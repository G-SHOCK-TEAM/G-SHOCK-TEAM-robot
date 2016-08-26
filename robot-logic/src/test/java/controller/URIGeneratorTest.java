package controller;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test()
public class URIGeneratorTest {
    @DataProvider(name = "URIdataSingle")
    private Object[][] provideURIData() {
        return new Object[][]{
                new Object[]{"http://www.something.com/books/ebook?list=###", 4, "http://www.something.com/books/ebook?list=4"},
                new Object[]{"http://www.something.com/books/ebook?list=###&sortBy=asc", 0, "http://www.something.com/books/ebook?list=0&sortBy=asc"}
        };
    }


    @DataProvider(name = "URIsequence")
    private Object[][] provideURISequence() {
        final URIGenerator generator = new URIGenerator("http://www.something.com/books/ebook?list=###&sortBy=asc", 0);

        return new Object[][]{
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/books/ebook?list=0&sortBy=asc"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/books/ebook?list=1&sortBy=asc"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/books/ebook?list=2&sortBy=asc"},
                new Object[]{generator.generateNextFullURI(), "http://www.something.com/books/ebook?list=3&sortBy=asc"}
        };
    }


    @Test(dataProvider = "URIdataSingle")
    public void testGeneratingFirstURI(String base, int startingIndex, String expected) {
        // given
        URIGenerator generator = new URIGenerator(base, startingIndex);
        // when
        String result = generator.generateNextFullURI();
        //then
        assertEquals(result, expected);
    }

    @Test(dataProvider = "URIsequence")
    public void testSequence(String actual, String expected) {
        //then
        assertEquals(actual, expected);
    }
}
