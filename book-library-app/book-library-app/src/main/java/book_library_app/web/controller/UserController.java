package book_library_app.web.controller;


import book_library_app.user.model.User;
import book_library_app.user.model.UserRole;
import book_library_app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("roles", UserRole.values());

        return "admin/users";
    }


    @PostMapping("/{id}/change-role")
    public String changeUserRole(@PathVariable("id") UUID id,
                                 @RequestParam("role") UserRole role,
                                 RedirectAttributes redirectAttributes) {

        userService.changeUserRole(id, role);
        redirectAttributes.addFlashAttribute("message", "Role updated successfully!");

        return "redirect:/users";
    }



}

