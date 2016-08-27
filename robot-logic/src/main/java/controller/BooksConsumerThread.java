package controller;

import book.Book;

import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

final class BooksConsumerThread implements Runnable {

    private final BlockingQueue<Book> rootQueue;

    private boolean run = true;

    BooksConsumerThread(BlockingQueue<Book> queue) {
        rootQueue = queue;
    }

    private synchronized boolean running() {
        return run;
    }

    synchronized void stopRunning() {
        run = false;
    }

    // TODO: DB connection
    @Override
    public void run() {

        // TODO: add stop conditions  - from parent thread
        // or consider creating cyclic task instead of one thread
        while (running()) {
            try {
                if (!rootQueue.isEmpty()) {
                    //
                    Book tmpBook = rootQueue.take();
                    System.out.println(tmpBook);
                }

                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}