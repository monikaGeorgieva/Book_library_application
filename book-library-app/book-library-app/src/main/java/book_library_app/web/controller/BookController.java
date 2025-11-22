package book_library_app.web.controller;

import book_library_app.book.model.Book;
import book_library_app.book.model.Type;
import book_library_app.book.service.BookService;
import book_library_app.favorite.service.FavouriteService;
import book_library_app.user.model.User;
import book_library_app.user.service.UserService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import book_library_app.feign.ReviewClient;
import book_library_app.feign.dto.ReviewDTO;
import book_library_app.feign.dto.CreateReviewDTO;
import java.security.Principal;
import java.util.List;

import java.util.UUID;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final FavouriteService favouriteService;
    private final UserService userService;
    private final ReviewClient reviewClient;


    @GetMapping
    public String showBooksPage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";

    }



    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("type", Type.values());
        return "library/add";
    }


    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }


    @GetMapping("/edit/{ISBN}")
    public String showEditForm(@PathVariable UUID ISBN, Model model) {
        model.addAttribute("book", bookService.getBookById(ISBN));
        return "library/edit"; // -> templates/books/edit.html
    }


    @PostMapping("/edit/{ISBN}")
    public String updateBook(@PathVariable UUID ISBN, @ModelAttribute Book updatedBook) {
        bookService.updateBook(ISBN, updatedBook);
        return "redirect:/books";
    }

    // Изтриване на книга
    @PostMapping("/delete/{ISBN}")
    public String deleteBook(@PathVariable UUID ISBN) {
        bookService.deleteBook(ISBN);
        return "redirect:/books";
    }

    @GetMapping("/{ISBN}")
    public String bookDetails(@PathVariable UUID ISBN, Model model) {
        Book book = bookService.getBookById(ISBN);

        List<ReviewDTO> reviews = reviewClient.getReviewsForBook(ISBN);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReview", new CreateReviewDTO(ISBN, "", 5, ""));

        return "library/book-details"; // нов template
    }

    @PostMapping("/{ISBN}/reviews")
    public String addReview(@PathVariable UUID ISBN,
                            @ModelAttribute("newReview") CreateReviewDTO newReview,
                            Principal principal) {

        CreateReviewDTO dto = new CreateReviewDTO(
                ISBN,
                principal != null ? principal.getName() : "anonymous",
                newReview.rating(),
                newReview.comment()
        );

        reviewClient.addReview(dto);

        return "redirect:/books/" + ISBN;
    }
}
