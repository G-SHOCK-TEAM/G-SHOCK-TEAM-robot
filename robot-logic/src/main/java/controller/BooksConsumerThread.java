package controller;

import book.Book;

import java.util.concurrent.BlockingQueue;


final class BooksConsumerThread implements Runnable {

    static long cnt = 0;
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

        while (running()) {
            Book tmpBook = null;

            try {
                tmpBook = rootQueue.take();
                System.out.println(String.valueOf(++cnt) + tmpBook);

            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    }
}