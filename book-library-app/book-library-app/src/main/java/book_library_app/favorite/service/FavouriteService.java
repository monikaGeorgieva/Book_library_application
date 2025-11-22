package book_library_app.favorite.service;

import book_library_app.book.model.Book;
import book_library_app.book.repository.BookRepository;
import book_library_app.favorite.model.Favorite;
import book_library_app.favorite.repository.FavoriteRepository;
import book_library_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FavouriteService {

    private final FavoriteRepository favoriteRepository;
    private final BookRepository bookRepository;

    public void addFavourite(User user, UUID isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        boolean exists = favoriteRepository
                .findByReaderAndFavoriteBook(user, book)
                .isPresent();

        if (exists) return;

        Favorite favorite = new Favorite();
        favorite.setReader(user);
        favorite.setFavoriteBook(book);
        favorite.setRating(10);
        favorite.setAddedAt(LocalDateTime.now());

        favoriteRepository.save(favorite);
    }

    public void removeFavourite(UUID favouriteId) {
        favoriteRepository.deleteById(favouriteId);
    }

    public List<Favorite> getFavourites(User user) {
        return favoriteRepository.findAllByReader(user);
    }

    public boolean isBookFavouriteForUser(User user, Book book) {
        return favoriteRepository.findByReaderAndFavoriteBook(user, book).isPresent();
    }
}
