package book_library_app.web.controller;



import book_library_app.user.service.UserService;
import book_library_app.web.dto.ProfileEditRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile/edit")
    public String showEditProfilePage(Principal principal, Model model) {
        String username = principal.getName();

        if (!model.containsAttribute("profileEditRequest")) {
            ProfileEditRequest data = userService.getProfileEditData(username);
            model.addAttribute("profileEditRequest", data);
        }

        model.addAttribute("countries", book_library_app.user.model.Country.values());

        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@Valid @ModelAttribute("profileEditRequest") ProfileEditRequest profileEditRequest,
                              BindingResult bindingResult,
                              Principal principal,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (bindingResult.hasErrors()) {
            // върни грешките към формата
            model.addAttribute("countries", book_library_app.user.model.Country.values());
            return "profile-edit";
        }

        String username = principal.getName();
        userService.updateProfile(username, profileEditRequest);

        redirectAttributes.addFlashAttribute("successMessage", "Профилът е обновен успешно!");

        return "redirect:/profile";
    }
}

