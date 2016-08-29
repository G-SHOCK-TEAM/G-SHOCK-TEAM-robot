package controller;

import book.Book;
import parser.Parser;

import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * This class is a wrapper for the threads of parser (because ParserThread is Callable). It allows to
 * avoid blocking executor at the level of ParserLauncherThread.
 */
final class ParserWrapperThread implements Runnable {

    private final Class<? extends Parser> parserClass;
    private final BlockingQueue<Book> rootQueue;
    private String URIToParse;

    private final ParserLauncherThread parentThread;


    ParserWrapperThread(Class<? extends Parser> parserClass, String URIToParse, ParserLauncherThread parentThread, BlockingQueue<Book> queue) {
        this.parserClass = parserClass;
        this.URIToParse = URIToParse;
        this.parentThread = parentThread;
        rootQueue = queue;
    }

    private void stopExecutingParent() {
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        parentThread.resetExecutingFlag();
    }

    private void handleResults(List<Book> list) {
        if (list == null) {
            stopExecutingParent();
            return;
        }

        if (!list.isEmpty()) {
            list.forEach(e -> {
                try {
                    rootQueue.put(e);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            });

        }
    }

    @Override
    public void run() {
        Callable<List<Book>> callable = new ParserThread(parserClass, URIToParse);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Book>> futureListOfBooks = executor.submit(callable);

        try {
            handleResults(futureListOfBooks.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}