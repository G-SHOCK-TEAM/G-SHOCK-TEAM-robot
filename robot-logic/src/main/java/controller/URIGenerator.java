package controller;

import org.apache.commons.lang.builder.HashCodeBuilder;

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
     * Starting with value 1 of internal counter;
     *
     * @param baseURI - String representing the base URI of the web page.
     */
    URIGenerator(String baseURI) {
        this.baseURI = baseURI;
        currentIndex = new AtomicInteger(1);
    }

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(baseURI).append(currentIndex).toHashCode();
    }

    @Override
    public boolean equals(Object generator) {
        if (this == generator) return true;
        if (generator == null || !(this.getClass().equals(generator.getClass())))
            return false;
        URIGenerator URIGeneratorObject = (URIGenerator) generator;
        return URIGeneratorObject.baseURI.equals(baseURI) && URIGeneratorObject.currentIndex.equals(currentIndex);
    }
}
