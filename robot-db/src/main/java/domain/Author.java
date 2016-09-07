package domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

/**
 * {@link Author} is a representation of an author in a database.
 * Field name brakes rule of normalization because we store name and surname
 * The instance of book can be created by a builder.
 * Do not use CascadeType or fetchType.Eager due to performance issue.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long authorId;

    @Getter
    @Setter
    private String name;

    @ManyToMany(mappedBy = "authors")
    @Getter
    @Setter
    private Collection<Book> books;

    @ManyToMany(mappedBy = "readAuthors")
    @Getter
    @Setter
    private Collection<BookReader> bookReaders;

    @Override
    public String toString () {
        return new StringBuilder("Author{authorId=")
                .append(authorId)
                .append(", name='")
                .append(name)
                .append("'}")
                .toString();
    }
}
