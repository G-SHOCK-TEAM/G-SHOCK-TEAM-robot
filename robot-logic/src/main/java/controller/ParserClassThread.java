package controller;

import book.Category;
import parser.Parser;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The root of the tree structure of thread tree. Propagates the mappings from the single parser class
 * to the children threads.
 */
class ParserClassThread implements Runnable {
    private final Class<? extends Parser> parserClass;
    private final Map<Category, List<URIGenerator>> categoryMappings;

    ParserClassThread(final Class<? extends Parser> clazz, final Map<Category, List<URIGenerator>> mappings) {
        parserClass = clazz;
        categoryMappings = Collections.unmodifiableMap(mappings);
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(categoryMappings.size());
        categoryMappings.keySet().forEach(e -> executor.submit(new SingleCategoryThread(parserClass, categoryMappings.get(e))));
        executor.shutdown();
    }

}