package book;

import lombok.Builder;

import java.util.List;

/**
 * Single book representation
 */
@Builder
public class Book {
    private final String title;
    private final String printHouse;
    private final short year;
    private final List<String> authors;
    private final float oldPrice;
    private final float newPrice;
    private final String currency;
    private final Category category;
    private final String description;
    private final String link;
}