package book_library_app.book.service;

import book_library_app.book.model.Book;
import book_library_app.book.repository.BookRepository;
import book_library_app.feign.ReviewClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    @Cacheable("books")
    public List<Book> getAllBooks() {
        log.info(">>> getAllBooks() –  тест:  ВЗИМАМ ОТ БАЗАТА (НЕ ОТ КЕША)");
        return bookRepository.findAll();
    }

    public Book getBookById(UUID ISBN) {
        return bookRepository.findById(ISBN)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @CacheEvict(value = "books", allEntries = true)
    public Book addBook(Book book) {
        log.info(">>> addBook()  тест – чистя кеша 'books'");
        bookRepository.save(book);
        return book;
    }


    @CacheEvict(value = "books", allEntries = true)
    public Book updateBook(UUID ISBN, Book updatedBook) {
        Book existing = getBookById(ISBN);
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setType(updatedBook.getType());
        existing.setDescription(updatedBook.getDescription());
        existing.setCover(updatedBook.getCover());
        return bookRepository.save(existing);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
