package controller;

import parser.Parser;

import static java.lang.Thread.sleep;

/**
 * This class is a wrapper for the threads of parser (because ParserThread is Callable). It allows to
 * avoid blocking executor at the level of ParserLauncherThread.
 */
class ParserWrapperThread implements Runnable {

    private final Class<? extends Parser> parserClass;
    private String URIToParse;

    private final ParserLauncherThread parentThread;

    ParserWrapperThread(Class<? extends Parser> parserClass, String URIToParse, ParserLauncherThread parentThread) {
        this.parserClass = parserClass;
        this.URIToParse = URIToParse;
        this.parentThread = parentThread;
    }

    @Override
    public void run() {
        System.out.println("dummy... " + URIToParse);
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
