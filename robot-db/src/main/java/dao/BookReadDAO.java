package dao;

import domain.Book;
import domain.BookReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BookReadDAO extends CrudRepository<BookReader, Long> {
    /**
     * Selects from a database all information about the book
     * for given pageable parameter
     * example of usage:
     * @<code> findAll(new PageRequest(1,20).getContent() </code>
     * it will return list of first 20 books
     * @param pageable {@link Pageable}
     * @return {@link Page}
     */
    Page<BookReader> findAll(Pageable pageable);
}
