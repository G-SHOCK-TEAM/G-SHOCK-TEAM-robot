package logic.parser.resources;

import domain.CategoryName;
import logic.controller.URIGenerator;
import logic.parser.EbooksComParser;
import logic.parser.Parser;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static domain.CategoryName.*;

/**
 * {@link XMLParser} is use to retrieve urls from .xml file.
 */
@Slf4j
public class XMLParser {

    /**
     * prepare map to be able to execute parser in multi threads. Each new url means 10 new threads.
     * @return
     */
    public Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> retrieveMapOfLinks() {
        return parse("links.xml");
    }

    /**
     * method goes through xml tags and retrieve data
     * @param source - file that we want to parse
     * @return
     */
    private Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> parse(String source) {

        Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> map = new HashMap<>();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File links = new File(classLoader.getResource(source).getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(links);

            // normalize xml
            doc.getDocumentElement().normalize();

            NodeList libraryList = doc.getElementsByTagName("library");


            for(int i=0; i<libraryList.getLength(); i++) {

                Node library = libraryList.item(i);
                Element eNode = (Element) library;
                Class<? extends Parser> parser = setClassType(eNode.getAttribute("id"));
                NodeList categories = eNode.getElementsByTagName("category");
                int delta = Integer.valueOf(eNode.getAttribute("delta"));

                map.putAll(setParserMap(categories, parser, delta));
            }

        } catch(FileNotFoundException e) {
            log.debug("File not found", e);
        } catch(Exception e) {
            log.debug("Main Exception", e);
        }

        return map;
    }

    /**
     * Base on links that are retrieved from xml file its generate List of URIGenerator
     * @param links
     * @return
     */
    private List<URIGenerator> retrieveLinks(NodeList links, int delta) {
        List<URIGenerator> parseLinks = new ArrayList<>();
        for(int i=0; i< links.getLength(); i++) {
            parseLinks.add(new URIGenerator(links.item(i).getTextContent(), delta));

        }
        return parseLinks;
    }

    /**
     * create CategoryName base on id from xml file
     * @param categoryName
     * @return
     */
    private CategoryName setCategoryName(String categoryName) {
        Map <String, CategoryName> categoryMap = new HashMap<>();
        categoryMap.put("eduScience", EDUCATION_AND_SCIENCE);
        categoryMap.put("travel", TRAVEL);
        categoryMap.put("lifestyle", LIFESTYLE);
        categoryMap.put("sex", SEX);
        categoryMap.put("medicine", MEDICINE);
        categoryMap.put("adventure", ADVENTURE);

        return categoryMap.get(categoryName);
    }

    /**
     * create class type base on id from xml file
     * @param className
     * @return
     */
    private Class<? extends Parser> setClassType(String className) {
        Map<String, Class<? extends Parser>> classMap = new HashMap<>();
        classMap.put("ebooks.com", EbooksComParser.class);

        return classMap.get(className);
    }

    /**
     * Create map based on single library
     * @param categories
     * @param parser
     * @return
     */
    private Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> setParserMap(NodeList categories, Class<? extends Parser> parser, int delta) {

        Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> map = new HashMap<>();
        Map<CategoryName, List<URIGenerator>> innerMapParser = new EnumMap<>(CategoryName.class);

        for(int j=0; j<categories.getLength(); j++) {
            Element eCategories = (Element) categories.item(j);
            CategoryName cn = setCategoryName(eCategories.getAttribute("id"));
            List<URIGenerator> parseLinks = retrieveLinks(eCategories.getElementsByTagName("link"), delta);
            innerMapParser.put(cn, parseLinks);
            map.put(parser, innerMapParser);
        }
        return map;
    }
}
