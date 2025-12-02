package book_library_app.book.service;

import book_library_app.book.model.Book;
import book_library_app.book.model.Type;
import book_library_app.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class BookServiceTest {

     @Mock
     private BookRepository bookRepository;

     @InjectMocks
     private BookService bookService;

     @Test
     void getAllBooks_returnsAllFromRepository() {
         // arrange
         Book b1 = new Book();
         b1.setISBN(UUID.randomUUID());
         b1.setTitle("Test Book 1");

         Book b2 = new Book();
         b2.setISBN(UUID.randomUUID());
         b2.setTitle("Test Book 2");

         when(bookRepository.findAll()).thenReturn(List.of(b1, b2));


         List<Book> result = bookService.getAllBooks();


         assertThat(result).hasSize(2);
         assertThat(result).extracting(Book::getTitle)
                 .containsExactly("Test Book 1", "Test Book 2");
         verify(bookRepository, times(1)).findAll();
     }

     @Test
     void addBook_savesBook() {
         // arrange
         Book book = new Book();
         book.setTitle("New Book");
         when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

         // act
         Book saved = bookService.addBook(book);

         // assert
         assertThat(saved.getTitle()).isEqualTo("New Book");
         verify(bookRepository, times(1)).save(book);
     }

     @Test
     void updateBook_updatesFields() {
         UUID id = UUID.randomUUID();

         Book existing = new Book();
         existing.setISBN(id);
         existing.setTitle("Old title");
         existing.setAuthor("Old author");
         existing.setDescription("Old desc");
         existing.setType(Type.HORROR);

         Book update = new Book();
         update.setTitle("New title");
         update.setAuthor("New author");
         update.setDescription("New desc");
         update.setType(Type.FANTASY);

         when(bookRepository.findById(id)).thenReturn(Optional.of(existing));
         when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

         // act
         Book result = bookService.updateBook(id, update);

         // assert
         assertThat(result.getTitle()).isEqualTo("New title");
         assertThat(result.getAuthor()).isEqualTo("New author");
         assertThat(result.getDescription()).isEqualTo("New desc");
         assertThat(result.getType()).isEqualTo(Type.FANTASY);

         ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
         verify(bookRepository).save(captor.capture());
         Book saved = captor.getValue();
         assertThat(saved.getISBN()).isEqualTo(id);
     }

     @Test
     void deleteBook_callsRepositoryDelete() {
         UUID id = UUID.randomUUID();


         bookService.deleteBook(id);


         verify(bookRepository, times(1)).deleteById(id);
     }
}
