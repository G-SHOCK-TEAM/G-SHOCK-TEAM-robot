package parser;

/**
 * Parser interface - the main goal of the interface is to parse the data for current category
 * (obtained by JSOUP from the Internet).
 */
@FunctionalInterface
public interface Parser {
    /**
     * Method to find the data which is to be stored in DB.
     */
    void parse();
}