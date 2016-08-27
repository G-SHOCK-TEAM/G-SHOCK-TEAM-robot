package controller;

import book.Book;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Parser thread - launches parser and shows the results
 */
final class ParserThread implements Callable<List<Book>> {
    @Override
    public List<Book> call() throws Exception {
        System.out.println("dummy call...");
        return null;
    }
}
