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
 * EmpikParser parse books from empik.pl web site. It's very important the way how empik change pages in url.
 * Diffrent in url page number is based on number of books that are shown.
 */
@Slf4j
public class EmpikParser implements Parser {
    private Document rootDocument;
    private ParsedBook.ParsedBookBuilder parsedBookBuilder;
    private CategoryName category;
    private String link;

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

    @Override
    public Optional<List<ParsedBook>> parse() {
        List<ParsedBook> resultList = new LinkedList<>();
        String whiteSign = "\u00A0";

        try {
            rootDocument = openDocument();
            Elements booksFound = findBooks();
            if ("".equals(booksFound.text())) {
                return Optional.empty();
            }


            for (Element e : booksFound) {

                // old price - to check if it is on discount
                String old = e.select(".prodPrice").select(".oldPrice").text();

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
                currency = String.valueOf(old.substring(old.length()-3).replace(whiteSign,""));

                // old price
                float oldPrice = Float.parseFloat(old.replace(currency, "").trim().replace(",",".").replace(whiteSign,""));

                // new price
                float newPrice = Float.parseFloat(e.select(".prodPrice").select(".currentPrice").text()
                        .replace(old, "")
                        .replace(currency, "")
                        .trim()
                        .replace(",", ".")
                        .replace(whiteSign,""));

                // find authors
                Elements authorsSet = e.select(".smartAuthor");
                authorsSet.forEach(author -> authors.add(author.text()));

                // go to url
                String descriptionLink = e.select(".productBox-450Title").first().attr("abs:href");
                Document descriptionDoc = connect(descriptionLink).timeout(0).get();


                Elements details = descriptionDoc.select(".productDetailsValue");

                // print house
                printHouse = details.get(1).select("a").text();

                // year
                year = Short.valueOf(details.get(4).select("span").text().substring(0, 4));


                description = descriptionDoc.select(".longDescription").text();

                // title
                title = descriptionDoc.select(".productMainTitle > span").text();

                parsedBookBuilder = ParsedBook
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
                        .link(descriptionLink);

                resultList.add(parsedBookBuilder.build());
            }

        } catch (IOException e) {
            log.debug("IOException caught", e);
        }
        return Optional.of(resultList);

    }

    Document openDocument() throws IOException {
        return connect(link).timeout(0).get();
    }

    private Elements findBooks() {
        return rootDocument.select(".productBox-450");
    }

}
