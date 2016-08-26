package controller;

import book.Category;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ParserClassThread implements Runnable {
    final Class<?> parserClass;
    final Map<Category, List<URIGenerator>> categoryMappings;

    ParserClassThread(final Class<?> clazz, final Map<Category, List<URIGenerator>> mappings) {
        parserClass = clazz;
        categoryMappings = Collections.unmodifiableMap(mappings);
    }


    // dummy for now...
    @Override
    public void run() {
        System.out.println("dummy...");

    }

}