package logic.parser;

import domain.CategoryName;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repositories.ParsedBook;
import repositories.ParsedBook.ParsedBookBuilder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.jsoup.Jsoup.connect;

@Slf4j
public class HelionParser implements Parser {


    private Document rootDocument;
    private ParsedBookBuilder parsedBookBuilder;
    private CategoryName category;
    private String link;

    @Override
    public Optional<List<ParsedBook>> parse () {
        List<ParsedBook> resultList = new LinkedList<>();

        try {
            rootDocument = openDocument();
            Elements booksFound = findBooks();
            if ("".equals(booksFound.text())) {
                return Optional.empty();
            }

            for (Element e : booksFound) {

                String old = e.select("p.price-incart > del").text();

                if ("".equals(old)) {
                    continue;
                }

                String title;
                String printHouse;
                String description;
                String currency;
                List<String> authors = new LinkedList<>();

                String[] priceCurrency = old.split(" ");

                currency = String.valueOf(priceCurrency[1]);

                float oldPrice = Float.parseFloat(old.replace(currency, "").trim());

                float newPrice = Float.parseFloat(
                        e.select("p.price-incart > a > ins > span ").text().replace(currency, "").trim());

                title = e.select("div.book-info > div > h3").text();

                Elements authorsSet = e.select("div.book-info > div > p");

                authorsSet.forEach(author -> authors.add(author.text()));

                String bookLink = e.select("a.niemby-link").first().attr("abs:href");

                Document bookDoc = connect(bookLink).timeout(0).get();

                printHouse = bookDoc.select("div.select_ebook > dd > a").first().text();

                description = bookDoc.select("div.book-description > div.text > div").text();

                parsedBookBuilder = ParsedBook
                        .builder()
                        .title(title)
                        .currency(currency)
                        .authors(authors)
                        .category(category)
                        .printHouse(printHouse)
                        .oldPrice(oldPrice)
                        .newPrice(newPrice)
                        .description(description)
                        .link(bookLink);

                resultList.add(parsedBookBuilder.build());
            }

        } catch (IOException e) {
            log.debug("IOException caught", e);
        }
        return Optional.of(resultList);
    }

    @Override
    public Parser setLink (String link) {
        this.link = link;
        return this;
    }

    @Override
    public Parser setCategory (CategoryName category) {
        this.category = category;
        return this;
    }

    private Document openDocument () throws IOException {
        return connect(link).timeout(0).get();
    }

    private Elements findBooks () {
        return rootDocument.select("li.classPresale");
    }
}
