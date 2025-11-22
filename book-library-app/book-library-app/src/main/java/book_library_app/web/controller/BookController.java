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

import java.util.UUID;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final FavouriteService favouriteService;
    private final UserService userService;


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
}
