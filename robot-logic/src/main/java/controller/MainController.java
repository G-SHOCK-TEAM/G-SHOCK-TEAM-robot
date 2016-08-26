package controller;

import book.Category;
import parser.EbooksComParser;
import parser.Parser;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static book.Category.*;

/**
 * This class is the main controller of creeper. The main goal of the class is to create an object
 * that iterates over all available kinds of parsers. Then, for each parser type the thread (ParserClassThread)
 * is created. The thread takes another mapping (which is a value inside fullMappings map) as an argument.
 * The class also contains the list of available parser classes (which is passed by a constructor).
 */
public class MainController {

    private final Map<Class<? extends Parser>, Map<Category, List<URIGenerator>>> fullMappings;

    /**
     * Default constructor.
     */
    public MainController() {
        fullMappings = initialize();
    }

    /**
     * Launches new parser threads for all different types of parsers.
     */
    public void launch() {
        ExecutorService executor = Executors.newFixedThreadPool(fullMappings.size());
        fullMappings.keySet().forEach(e -> executor.submit(new ParserClassThread(e, fullMappings.get(e))));
        executor.shutdown();
    }



    // WARNING: ugly code :)
    // TODO: consider moving to DB or XML/JSON
    private static Map<Class<? extends Parser>, Map<Category, List<URIGenerator>>> initialize() {

        Map<Class<? extends Parser>, Map<Category, List<URIGenerator>>> map = new HashMap<>();


        Map<Category, List<URIGenerator>> innerMapParser1 = new EnumMap<>(Category.class);

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