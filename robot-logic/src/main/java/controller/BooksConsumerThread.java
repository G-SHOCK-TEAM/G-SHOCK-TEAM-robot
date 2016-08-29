package controller;

import book.Book;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;


final class BooksConsumerThread implements Runnable {

    //    static long cnt = 0;
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

        Queue<Book> drained = new LinkedList<>();

        while (running()) {

            int n = rootQueue.size();

            if (n > 0) {
                rootQueue.drainTo(drained);
                System.out.println(drained);
            }
        }
    }
}