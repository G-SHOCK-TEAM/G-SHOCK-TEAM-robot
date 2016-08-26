package controller;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.valueOf;

/**
 * URI address helper class (generator).
 * The main goal of this class is to generate next URI to parse. The class contains base URI string and a thread safe
 * counter to generate current index. The index is put in the place of ### pattern in the base string.
 */
public class URIGenerator {

    private final String baseURI;
    private AtomicInteger currentIndex;

    /**
     * The main URIGenerator constructor.
     *
     * @param baseURI       - String representing the base URI of the web page.
     * @param startingIndex - int representing starting index to be put in the place of '###' pattern.
     */
    URIGenerator(String baseURI, int startingIndex) {
        this.baseURI = baseURI;
        currentIndex = new AtomicInteger(startingIndex);
    }

    /**
     * Method returns next URL which is based on the current value of internal counter.
     *
     * @return - String representing next URI to parse
     */
    String generateNextFullURI() {
        return baseURI.replace("###", valueOf(currentIndex.getAndIncrement()));
    }
}
