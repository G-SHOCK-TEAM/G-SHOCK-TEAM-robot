package controller;

import parser.Parser;

public class ParserWrapperThread implements Runnable {
    private final Class<? extends Parser> parserClass;
    private final URIGenerator generator;

    ParserWrapperThread(final Class<? extends Parser> parserClass, final URIGenerator generator) {
        this.parserClass = parserClass;
        this.generator = generator;
    }

    
    @Override
    public void run() {
        System.out.println("dummy...");
    }
}
