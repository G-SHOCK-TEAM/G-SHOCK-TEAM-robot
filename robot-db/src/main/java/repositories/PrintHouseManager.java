package repositories;

import dao.PrintHouseDAO;
import domain.PrintHouse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link PrintHouseManager} reads all print houses which exists in database and save them in memory
 */
@Repository
@Transactional
@Slf4j
class PrintHouseManager {

    @Autowired
    PrintHouseDAO printHouseDAO;
    private Map<String, PrintHouse> printHouses;

    @PostConstruct
    private void init() {
        printHouses = new HashMap<>();
        printHouseDAO.findAll().forEach(printHouse -> printHouses.put(printHouse.getName(), printHouse));
    }

    /**
     * Check if category with a given name exists in database if no create new entry
     * @param name  {@link PrintHouse} name of print house
     * @return category from the memory
     */
    PrintHouse getPrintHouse (String name) {
        if (printHouses.containsKey(name)) {
            return printHouses.get(name);
        }
        PrintHouse printHouse = PrintHouse.builder().name(name).build();
        printHouses.put(name, printHouse);
        log.info("printHouse " + printHouse);
        return printHouseDAO.save(printHouse);
    }
}
