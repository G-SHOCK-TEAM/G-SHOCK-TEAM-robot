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

@Slf4j
public class PublioParser implements Parser {


    private Document rootDocument;
    private ParsedBook.ParsedBookBuilder parsedBookBuilder;
    private CategoryName category;
    private String link;

    public static void main (String[] args) {
        PublioParser publioParser = new PublioParser();
        publioParser.setLink("http://www.publio.pl/e-booki,turystyka-podroze,k1913,strona10.html");
        publioParser.setCategory(CategoryName.EDUCATION_AND_SCIENCE);
        Optional<List<ParsedBook>> parse = publioParser.parse();
        System.out.println(parse);
    }

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

                String old = e.select("ins.product-tile-price-promotion").text();

                if ("".equals(old)) {
                    continue;
                }

                String title;
                String printHouse;
                String description;
                String currency;
                List<String> authors = new LinkedList<>();

                currency = String.valueOf(old.substring(old.length() - 2));
                old = old.replace(",", ".").replace("\u00A0", "");

                float newPrice = Float.parseFloat(old.replace(currency, "").trim());

                float oldPrice = Float.parseFloat(e.select("del.product-tile-price-old")
                        .text()
                        .replace(currency, "")
                        .replace(",", ".")
                        .replace("\u00A0", "")
                        .trim());

                title = e.select("span.product-tile-title-short").text();

                Elements authorsSet = e.select("span.product-tile-author > a");

                authorsSet.forEach(author -> authors.add(author.text()));

                String bookLink = e.select("a.product-tile-cover").first().attr("abs:href");

                Document bookDoc = connect(bookLink).timeout(0).get();

                printHouse = bookDoc.select("div.product-detail").first().nextElementSibling().select("a").text();

                description = bookDoc.select("div.product-lead").text() +
                        bookDoc.select("div.product-description").text();

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
        return rootDocument.select("div.product-tile");
    }
}
