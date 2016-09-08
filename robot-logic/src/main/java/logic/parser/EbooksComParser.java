package logic.parser;

import domain.CategoryName;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repositories.ParsedBook;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.jsoup.Jsoup.connect;

/**
 * Parser implementation for ebooks.com.
 * Warning: the code is a bit imperative - it might be reworked later.
 */
@Slf4j
public class EbooksComParser extends AbstractParser {

    Document openDocument() throws IOException {
        return connect(link).timeout(0).get();
    }

    private Elements findBooks(String htmlArg) {
        return rootDocument.select(htmlArg);
    }

    @Override
    public Optional<List<ParsedBook>> parse() {
        List<ParsedBook> resultList = new LinkedList<>();

        try {
            rootDocument = openDocument();
            Elements booksFound = findBooks("li.search-row.clearfix");
            if ("".equals(booksFound.text())) {
                return Optional.empty();
            }


            for (Element e : booksFound) {

                // old price - to check if it is on discount
                String old = e.select("div.additional-info > span > span").select("[style*=text-decoration:line-through]").text();

                if ("".equals(old)) {
                    continue;
                }

                // variables
                String title;
                String printHouse;
                String description;
                String currency;
                List<String> authors = new LinkedList<>();
                short year;

                // currency
                currency = String.valueOf(old.charAt(0));

                // old price
                float oldPrice = Float.parseFloat(old.replace(currency, "").trim());

                // new price
                float newPrice = Float.parseFloat(e.select("div.additional-info > span > span").text().replace(old, "").replace(currency, "").trim());

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


                ParsedBook parsedBook = ParsedBook
                        .builder()
                        .title(title)
                        .year(year)
                        .currency(currency)
                        .authors(authors)
                        .category(category)
                        .printHouse(printHouse)
                        .oldPrice(oldPrice)
                        .newPrice(newPrice)
                        .description(description)
                        .link(descriptionLink)
                        .build();

                resultList.add(parsedBook);
            }

        } catch (IOException e) {
            log.debug("IOException caught", e);
        }
        return Optional.of(resultList);
    }
}