package logic.parser;

import domain.CategoryName;
import org.jsoup.nodes.Document;

/**
 * Abstract partial implementation of Parser interface. The main reason of that is to avoid code duplicates.
 * NOTE: This class is public because of mocking methods in tests (ISSUE 212 {@link https://code.google.com/archive/p/mockito/issues/212})
 */
public abstract class AbstractParser implements Parser {

    protected CategoryName category;
    Document rootDocument;
    String link;

    @Override
    public Parser setLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public Parser setCategory(CategoryName category) {
        this.category = category;
        return this;
    }
}
