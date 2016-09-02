package dao;

import dbconfiguration.SpringDBConfiguration;
import domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {SpringDBConfiguration.class})
public class BookDAOTest extends AbstractTestNGSpringContextTests {

    @Autowired
    BookDAO bookDAO;

    @Test
    public void SelectAllFromBook () {
        // Given
        Book expectedBook = Book.builder().title("#!@#$%^&*(*()_{}{][]';';/./.,mm,m<>+_9876543śćń''").build();

        // When
        bookDAO.save(expectedBook);
        Book book = bookDAO.findOne(1L);

        // Then
        assertEquals(book.getTitle(),expectedBook.getTitle());
    }
}