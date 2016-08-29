package parser;

import book.Book;
import book.Category;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static book.Book.*;
import static org.jsoup.Jsoup.*;


public class EbooksComParser implements Parser {

    private Document rootDocument;
    private BookBuilder bookBuilder;
    private Category category;
    private String link;

    public Parser setLink(String link) {
        this.link = link;
        return this;
    }

    public Parser setCategory(Category category) {
        this.category = category;
        return this;
    }

    @Override
    public List<Book> parse() {
        List<Book> resultList = new LinkedList<>();


        try {
            rootDocument = connect(link).timeout(0).get();
            Elements booksFound = findBooks();

            if (booksFound.text().equals("")) {
                return null;
            }


            for (Element e : booksFound) {

                // old price - to check if it is on discount
                String old = e.select("div.additional-info > span > span").select("[style*=text-decoration:line-through]").text();

                if (old.equals("")) {
                    continue;
                }

                // variables
                String title, printHouse, description, currency;
                List<String> authors = new LinkedList<>();
                short year;

                // currency
                currency = String.valueOf(old.charAt(0));

                // old price
                float oldPrice = Float.valueOf(old.replace(currency, "").trim());

                // new price
                float newPrice = Float.valueOf(e.select("div.additional-info > span > span").text().replace(old, "").replace(currency, "").trim());

                // title
                title = e.select("h4 > span.book-title > a").text();

                // find authors
                Elements authorsSet = e.select("h4 > span.author > a");
                authorsSet.forEach(author -> authors.add(author.text()));

                // print house
                printHouse = e.select("div.additional-info > span").first().text();

                // year
                year = Short.valueOf(e.select("div.additional-info > span").first().nextElementSibling().text().replace(";", ""));

                // description
                String descriptionLink = e.select("p > a").first().attr("abs:href");
                Document descriptionDoc = connect(descriptionLink).timeout(0).get();
                description = descriptionDoc.select("div.short-description").select("[itemprop]").text();


                bookBuilder = builder().title(title).authors(authors).printHouse(printHouse).year(year).currency(currency)
                        .oldPrice(oldPrice).newPrice(newPrice).description(description).link(link).category(category);

                resultList.add(bookBuilder.build());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private Elements findBooks() {
        return rootDocument.select("li.search-row.clearfix");
    }
}