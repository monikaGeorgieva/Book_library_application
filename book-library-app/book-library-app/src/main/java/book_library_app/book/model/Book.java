package book_library_app.book.model;

import book_library_app.favorite.model.Favorite;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ISBN;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String cover;

    @Column(nullable = false,length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate publishedOn;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "favoriteBook")
    private List<Favorite> favourites = new ArrayList<>();


}
