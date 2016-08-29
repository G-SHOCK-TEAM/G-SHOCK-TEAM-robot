package controller;

import book.Book;
import parser.Parser;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Parser thread - launches parser and shows the results
 */
final class ParserThread implements Callable<List<Book>> {

    private final Parser parser;
    private final String link;

    ParserThread(Class<? extends Parser> parserClass, String URI) {
        parser = createParser(parserClass);
        link = URI;
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
        return parser.parse(link);
    }
}