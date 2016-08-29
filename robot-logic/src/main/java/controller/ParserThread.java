package controller;

import book.Book;
import book.Category;
import parser.EbooksComParser;
import parser.Parser;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Parser thread - launches parser and shows the results
 */
final class ParserThread implements Callable<List<Book>> {

    private final Parser parser;
    private final String link;
    private final Category category;

    ParserThread(Class<? extends Parser> parserClass, Category category, String URI) {
        parser = createParser(parserClass);
        link = URI;
        this.category = category;
    }

    private static Parser createParser(Class<? extends Parser> parserClass) {
        Parser parser = null;

        try {
            parser = parserClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return parser;
    }

    @Override
    public List<Book> call() throws Exception {
        parser.setLink(link).setCategory(category);
        return parser.parse();
    }
}