package book_library_app.book.service;

import book_library_app.book.model.Book;
import book_library_app.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID ISBN) {
        return bookRepository.findById(ISBN)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }


    public Book updateBook(UUID ISBN, Book updatedBook) {
        Book existing = getBookById(ISBN);
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setType(updatedBook.getType());
        existing.setDescription(updatedBook.getDescription());
        existing.setCover(updatedBook.getCover());
        return bookRepository.save(existing);
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
