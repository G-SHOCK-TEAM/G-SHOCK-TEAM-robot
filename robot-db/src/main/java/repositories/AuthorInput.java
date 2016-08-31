package repositories;

import dao.AuthorDAO;
import domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthorInput {

    Map<String, Author> authors;
    @Autowired
    private AuthorDAO authorDAO;

    @PostConstruct
    protected void init() {
        authors = new HashMap<>();
        authorDAO.findAll().forEach(author -> authors.put(author.getName(), author));
    }

    Author saveAuthor(String name) {
        if (!authors.containsKey(name)) {
            Author author = Author.builder().name(name).build();
            authors.put(name, author);
            authorDAO.save(author);
        }
        return authors.get(name);
    }
}