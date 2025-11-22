package book_library_app.favorite.repository;

import book_library_app.book.model.Book;
import book_library_app.favorite.model.Favorite;
import book_library_app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    List<Favorite> findAllByReader(User reader);

    Optional<Favorite> findByReaderAndFavoriteBook(User reader, Book favoriteBook);

}
