package controller;

import parser.Parser;

import java.util.List;

public class SingleCategoryThread implements Runnable {
    private final Class<? extends Parser> parserClass;
    private final List<URIGenerator> listOfURIGenerators;

    SingleCategoryThread(Class<? extends Parser> parserClass, List<URIGenerator> listOfURIGenerators) {
        this.parserClass = parserClass;
        this.listOfURIGenerators = listOfURIGenerators;
    }

    @Override
    public void run() {
        System.out.println("dummy child...");
    }
}
