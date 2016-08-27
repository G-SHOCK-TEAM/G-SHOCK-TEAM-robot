package controller;

import parser.Parser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread for single Category.
 */
class SingleCategoryThread implements Runnable {
    private final Class<? extends Parser> parserClass;
    private final List<URIGenerator> listOfURIGenerators;

    /**
     * @param parserClass
     * @param listOfURIGenerators
     */
    SingleCategoryThread(final Class<? extends Parser> parserClass, final List<URIGenerator> listOfURIGenerators) {
        this.parserClass = parserClass;
        this.listOfURIGenerators = listOfURIGenerators;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(listOfURIGenerators.size());
        listOfURIGenerators.forEach(e -> executor.submit(new ParserLauncherThread(parserClass, e)));
        executor.shutdown();
    }
}