package parser;

import book.Book;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;


@Test()
public class EbooksComParserTest {

    @Test
    public void testParser() throws IOException {
        // given
        InputStream is =  ClassLoader.getSystemResourceAsStream("education_example.html");
        String html = IOUtils.toString(is);
        EbooksComParser parser = mock(EbooksComParser.class);

        // when
        when(parser.openDocument()).thenReturn(Jsoup.parse(html));
        when(parser.parse()).thenCallRealMethod();
        List<Book> listOfBooks = parser.parse();

        // then
        assertTrue(listOfBooks.size() == 8);
    }
}