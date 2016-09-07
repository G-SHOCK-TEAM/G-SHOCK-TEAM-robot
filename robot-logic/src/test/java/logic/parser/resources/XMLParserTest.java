package logic.parser.resources;

import domain.CategoryName;
import logic.controller.URIGenerator;
import logic.parser.EbooksComParser;
import logic.parser.Parser;
import org.testng.annotations.Test;

import java.util.*;

import static domain.CategoryName.*;
import static org.testng.Assert.assertEquals;

public class XMLParserTest {

    @Test
    public void testOpeningFile() {
        XMLParser xmlParser = new XMLParser();

        assertEquals(xmlParser.retrieveMapOfLinks(), initialize());
    }

    private Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> initialize() {

        Map<Class<? extends Parser>, Map<CategoryName, List<URIGenerator>>> map = new HashMap<>();


        Map<CategoryName, List<URIGenerator>> innerMapParser1 = new EnumMap<>(CategoryName.class);

        List<URIGenerator> parser1EduScience = new LinkedList<>();
        parser1EduScience.add(new URIGenerator("http://www.ebooks.com/subjects/computers/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));
        parser1EduScience.add(new URIGenerator("http://www.ebooks.com/subjects/science/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));
        parser1EduScience.add(new URIGenerator("http://www.ebooks.com/subjects/education/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));
        parser1EduScience.add(new URIGenerator("http://www.ebooks.com/subjects/mathematics/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));

        List<URIGenerator> parser1Travel = new LinkedList<>();
        parser1Travel.add(new URIGenerator("http://www.ebooks.com/subjects/travel/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));

        List<URIGenerator> parser1Lifestyle = new LinkedList<>();
        parser1Lifestyle.add(new URIGenerator("http://www.ebooks.com/subjects/sports-recreation/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));
        parser1Lifestyle.add(new URIGenerator("http://www.ebooks.com/subjects/family-relationships/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));

        List<URIGenerator> parser1Sex = new LinkedList<>();
        parser1Sex.add(new URIGenerator("http://www.ebooks.com/subjects/sex/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));

        List<URIGenerator> parser1Medicine = new LinkedList<>();
        parser1Medicine.add(new URIGenerator("http://www.ebooks.com/subjects/medicine/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));

        List<URIGenerator> parser1Adventure = new LinkedList<>();
        parser1Adventure.add(new URIGenerator("http://www.ebooks.com/subjects/crafts-hobbies/?sortBy=&sortOrder=&RestrictBy=&countryCode=pl&page=###"));


        innerMapParser1.put(EDUCATION_AND_SCIENCE, parser1EduScience);
        innerMapParser1.put(TRAVEL, parser1Travel);
        innerMapParser1.put(LIFESTYLE, parser1Lifestyle);
        innerMapParser1.put(SEX, parser1Sex);
        innerMapParser1.put(MEDICINE, parser1Medicine);
        innerMapParser1.put(ADVENTURE, parser1Adventure);

        // other parsers
        // ...
        // ...

        map.put(EbooksComParser.class, innerMapParser1);

        return map;
    }
}