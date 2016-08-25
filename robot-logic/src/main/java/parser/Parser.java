package parser;

import book.Book;

import java.util.List;

/**
 * Parser interface - the main goal of the interface is to parse the data for current category
 * (obtained by JSOUP from the Internet).
 */
@FunctionalInterface
public interface Parser {
    /**
     * Method to find the data which is to be stored in DB.
     */
    List<Book> parse();
}