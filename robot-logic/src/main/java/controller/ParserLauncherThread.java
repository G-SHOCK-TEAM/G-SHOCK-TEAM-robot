package controller;

import book.Book;
import book.Category;
import parser.Parser;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Class to launch the constant size thread pool of parsers.
 */
final class ParserLauncherThread implements Runnable {
    private final Class<? extends Parser> parserClass;
    private final URIGenerator generator;
    private final BlockingQueue<Book> rootQueue;
    private final Category category;


    private static final int MAX_POOL_SIZE = 4;
    private boolean executing = true;


    private synchronized boolean isExecuting() {
        return executing;
    }

    synchronized void resetExecutingFlag() {
        executing = false;
    }


    ParserLauncherThread(final Class<? extends Parser> parserClass, final URIGenerator generator,
                         final BlockingQueue<Book> queue, Category category) {
        this.parserClass = parserClass;
        this.generator = generator;
        rootQueue = queue;
        this.category = category;
    }


    @Override
    public void run() {

        ExecutorService executor = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        LinkedBlockingQueue<ParserWrapperThread> queue = new LinkedBlockingQueue<>();

        while (isExecuting()) {

            try {
                int count = ((ThreadPoolExecutor) executor).getActiveCount();
                int toRun = MAX_POOL_SIZE - count;

                if (toRun > 0) {
                    queue.put(new ParserWrapperThread(parserClass, generator.generateNextFullURI(), this, rootQueue, category));
                    --toRun;
                }

                if (queue.size() > 0) {
                    executor.execute(queue.take());
                }

                sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}