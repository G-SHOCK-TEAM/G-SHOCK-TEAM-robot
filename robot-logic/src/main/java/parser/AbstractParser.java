package parser;

import book.Category;
import controller.URIGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for all parsers.
 * This class contains mapping between Category and List of all base URIs for each category in particular parser.
 */
public abstract class AbstractParser {

    private Map<Category, List<? extends URIGenerator>> mappings;

    /**
     * This constructor is used in child classes to initialize mappings between categories and available URIs.
     *
     * @param mappings - map that represents mappings generated in the child class.
     */
    protected AbstractParser(Map<Category, List<? extends URIGenerator>> mappings) {
        this.mappings = mappings;
    }

    /**
     * Method returns unmodifiable view of mappings stored inside this class.
     *
     * @return mappings between categories and URIs (unmodifiable).
     */
    Map<Category, List<? extends URIGenerator>> getCategoryAndURIMappings() {
        return Collections.unmodifiableMap(mappings);
    }
}
