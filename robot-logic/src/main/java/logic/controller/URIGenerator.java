package logic.controller;

import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.valueOf;

/**
 * URI address helper class (generator).
 * The main goal of this class is to generate next URI to parse. The class contains base URI string and a thread safe
 * counter to generate current index. The index is put in the place of ### pattern in the base string.
 */
class URIGenerator {

    private final String baseURI;
    private AtomicInteger currentIndex;
    private final int delta;

    /**
     * Starting with value 1 of internal counter;
     *
     * @param baseURI - String representing the base URI of the web page.
     */
    URIGenerator(final String baseURI) {
        this.baseURI = baseURI;
        currentIndex = new AtomicInteger(1);
        this.delta = 1;
    }

    /**
     * This constructor takes base URI as the first argument and delta number to be added at each invocation of
     * generateNextFullURI() method.
     *
     * @param baseURI String representing the base URI of the web page.
     * @param delta Delta integer to be added in each invocation of {@Link generateNextFullURI()} method.
     */
    URIGenerator(final String baseURI, final int delta) {
        this.baseURI = baseURI;
        currentIndex = new AtomicInteger(1);
        this.delta = delta;
    }

    /**
     * Method returns next URL which is based on the current value of internal counter.
     *
     * @return - String representing next URI to parse
     */
    String generateNextFullURI() {
        return baseURI.replace("###", valueOf(currentIndex.getAndAdd(delta)));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(baseURI)
                .append(currentIndex.get())
                .append(delta)
                .toHashCode();
    }

    @Override
    public boolean equals(Object generator) {
        if (this == generator)
            return true;
        if (generator == null || getClass() != generator.getClass())
            return false;
        URIGenerator linkGeneratorObject = (URIGenerator) generator;
        return linkGeneratorObject.baseURI.equals(baseURI)
                && currentIndex.get() == linkGeneratorObject.currentIndex.get()
                && delta == linkGeneratorObject.delta;
    }
}
