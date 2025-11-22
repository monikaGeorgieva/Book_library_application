package book_library_app.web.controller;

import book_library_app.user.model.User;
import book_library_app.user.service.UserService;

import book_library_app.web.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;


@Controller
public class IndexController {


    private final UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage(){

        return "index";
    }

    @GetMapping("/index")
    public String getMyIndexPage(){

        return "index";
    }

    @GetMapping("/about")
    public String getAboutPage(){

        return "about";
    }


    @GetMapping("/login")
    public String getLoginPage(){

        return "login";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequest", new RegisterRequest());

        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }
        User registeredUser = userService.register(registerRequest);
        return new ModelAndView("redirect:/profile?userId=" +  registeredUser.getId());
    }


    @GetMapping("/profile")
    public ModelAndView getProfilePage() {
        User user = userService.getCurrentUser();

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("favourites", userService.getCurrentUserFavouriteBooks());

        return modelAndView;
    }



}
