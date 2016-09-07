package logic.controller;

import domain.CategoryName;
import logic.parser.Parser;
import logic.parser.resources.XMLParser;
import repositories.ParsedBook;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * This class is the main controller of creeper. The main goal of the class is to create an object
 * that iterates over all available kinds of parsers. Then, for each logic.parser type the thread (ParserClassThread)
 * is created. The thread takes another mapping (which is a value inside fullMappings map) as an argument.
 * The class also contains the list of available logic.parser classes (which is passed by a constructor).
 */
public final class MainController {

    private static final int MAX_QUEUE_SIZE = 1000;
    private final Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> fullMappings;
    private final BlockingQueue<ParsedBook> bookQueue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);

    /**
     * Default constructor.
     */
    public MainController() {
        fullMappings = new XMLParser().retrieveMapOfLinks();
    }


    /**
     * Launches new parser threads for all different types of parsers.
     */
    public void launch() throws InterruptedException {
        ExecutorService mainExecutor = Executors.newFixedThreadPool(fullMappings.size());
        ExecutorService queueExecutor = Executors.newSingleThreadExecutor();    // just single for now...
        fullMappings.keySet().forEach(e -> mainExecutor.submit(new ParserClassThread(e, fullMappings.get(e), bookQueue)));
        mainExecutor.shutdown();

        sleep(1000);

        BooksConsumerThread consumer = new BooksConsumerThread(bookQueue);
        Future<?> future = queueExecutor.submit(consumer);
        queueExecutor.shutdown();

        mainExecutor.awaitTermination(1, TimeUnit.DAYS);

        while (!bookQueue.isEmpty()) {
            sleep(100);
        }

        consumer.running.set(false);
        future.cancel(true);
        queueExecutor.awaitTermination(1, TimeUnit.HOURS);
    }
}