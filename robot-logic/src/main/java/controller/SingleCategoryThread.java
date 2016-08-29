package controller;

import book.Book;
import book.Category;
import parser.Parser;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Thread for single Category.
 */
final class SingleCategoryThread implements Runnable {
    private final Class<? extends Parser> parserClass;
    private final List<URIGenerator> listOfURIGenerators;
    private final BlockingQueue<Book> rootQueue;
    private final Category category;

    SingleCategoryThread(final Class<? extends Parser> parserClass, final List<URIGenerator> listOfURIGenerators,
                         BlockingQueue<Book> queue, Category category) {
        this.parserClass = parserClass;
        this.listOfURIGenerators = listOfURIGenerators;
        rootQueue = queue;
        this.category = category;
    }

    @Override
    public void run() {

        ExecutorService executor = Executors.newFixedThreadPool(listOfURIGenerators.size());
        listOfURIGenerators.forEach(e -> executor.submit(new ParserLauncherThread(parserClass, e, rootQueue, category)));
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}