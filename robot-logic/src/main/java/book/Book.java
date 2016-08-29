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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title).append("\n");
        sb.append("Print house: ").append(printHouse).append("\n");
        sb.append("Year: ").append(year).append("\n");
        sb.append("Authors: ").append(authors).append("\n");
        sb.append("Old price: ").append(oldPrice).append("\n");
        sb.append("New price: ").append(newPrice).append("\n");
        sb.append("Currency: ").append(currency).append("\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Link: ").append(link).append("\n");

        return sb.toString();
    }
}