package book_library_app.web.controller;



import book_library_app.favorite.service.FavouriteService;
import book_library_app.user.model.User;
import book_library_app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@Controller
@RequestMapping("/favourites")
@RequiredArgsConstructor
public class FavouritesController {

    private final FavouriteService favouriteService;
    private final UserService userService;

    @PostMapping("/add/{isbn}")
    public String addFavourite(@PathVariable UUID isbn,
                               @RequestHeader(value = "Referer", required = false) String referer) {

        User currentUser = userService.getCurrentUser();
        favouriteService.addFavourite(currentUser, isbn);

        return "redirect:" + (referer != null ? referer : "/books/" + isbn);
    }

    @PostMapping("/remove/{id}")
    public String removeFavourite(@PathVariable UUID id,
                                  @RequestHeader(value = "Referer", required = false) String referer) {

        favouriteService.removeFavourite(id);
        return "redirect:" + (referer != null ? referer : "/favourites");
    }

    @GetMapping
    public String getFavourites(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("favourites", favouriteService.getFavourites(currentUser));
        return "favourites";
    }

}
