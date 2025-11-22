package book_library_app.favorite.model;

import book_library_app.book.model.Book;
import book_library_app.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String notes;

    @Column(nullable = false)
    @Min(1)
    @Max(10)
    private int rating;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne //кой потребител е добавил книгата
    private User reader;

    @ManyToOne  //коя книга е добавена
    private Book favoriteBook;

    private java.time.LocalDateTime addedAt;

}
