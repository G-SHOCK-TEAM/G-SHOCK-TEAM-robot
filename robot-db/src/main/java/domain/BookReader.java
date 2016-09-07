package domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
public class BookReader extends Book{
//    @Id
//    @GeneratedValue
//    @Setter
//    @Getter
//    private long bookReadID;

    @ManyToMany(fetch = FetchType.EAGER)
    @Setter
    @Getter
    private List<Author> readAuthors;

}
