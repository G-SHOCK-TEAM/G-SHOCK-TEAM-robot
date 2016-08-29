package parser;

import book.Book;
import book.Category;

import java.util.List;

/**
 * Parser interface - the main goal of the interface is to parse the data for current category
 * (obtained by JSOUP from the Internet).
 */
public interface Parser {
    List<Book> parse();
    Parser setLink(String link);
    Parser setCategory(Category category);
}