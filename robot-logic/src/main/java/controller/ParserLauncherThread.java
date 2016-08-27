package controller;

import parser.Parser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

/**
 * Class to launch the constant size thread pool of parsers.
 */
class ParserLauncherThread implements Runnable {
    private final Class<? extends Parser> parserClass;
    private final URIGenerator generator;

    private static final int POOL_SIZE = 10;

    private boolean executing = true;


    private synchronized boolean isExecuting() {
        return executing;
    }

    synchronized void resetExecutingFlag() {
        executing = false;
    }


    ParserLauncherThread(final Class<? extends Parser> parserClass, final URIGenerator generator) {
        this.parserClass = parserClass;
        this.generator = generator;
    }


    @Override
    public void run() {

        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        LinkedBlockingQueue<ParserWrapperThread> queue = new LinkedBlockingQueue<>();

        // TODO: remove later - after changing the flag from child thread is implemented
        // just for test
        int i = 0;

        while (isExecuting()) {

            try {
                int count = ((ThreadPoolExecutor) executor).getActiveCount();
                int toRun = POOL_SIZE - count;

                if (toRun > 0) {
                    queue.put(new ParserWrapperThread(parserClass, generator.generateNextFullURI(), this));
                    --toRun;

                    // TODO: remove later
                    i++;
                }

                if (queue.size() > 0) {
                    executor.execute(queue.take());
                }

                sleep(10);

                // TODO: remove later
                if (i == 50) resetExecutingFlag();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}
