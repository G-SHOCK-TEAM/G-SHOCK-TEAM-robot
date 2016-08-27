package controller;

import book.Book;
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

    private static final int POOL_SIZE = 10;
    private boolean executing = true;


    private synchronized boolean isExecuting() {
        return executing;
    }

    synchronized void resetExecutingFlag() {
        executing = false;
    }


    ParserLauncherThread(final Class<? extends Parser> parserClass, final URIGenerator generator, final BlockingQueue<Book> queue) {
        this.parserClass = parserClass;
        this.generator = generator;
        rootQueue = queue;
    }


    @Override
    public void run() {

        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        LinkedBlockingQueue<ParserWrapperThread> queue = new LinkedBlockingQueue<>();

        while (isExecuting()) {

            try {
                int count = ((ThreadPoolExecutor) executor).getActiveCount();
                int toRun = POOL_SIZE - count;

                if (toRun > 0) {
                    queue.put(new ParserWrapperThread(parserClass, generator.generateNextFullURI(), this, rootQueue));
                    --toRun;
                }

                if (queue.size() > 0) {
                    executor.execute(queue.take());
                }

                sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}
