package book_library_app.user.service;

import book_library_app.book.model.Book;
import book_library_app.exeption.DomainException;
import book_library_app.favorite.model.Favorite;
import book_library_app.favorite.service.FavouriteService;
import book_library_app.user.model.User;
import book_library_app.user.model.UserRole;
import book_library_app.user.repository.UserRepository;
import book_library_app.web.dto.LoginRequest;
import book_library_app.web.dto.ProfileEditRequest;
import book_library_app.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FavouriteService favouriteService;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FavouriteService favouriteService, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.favouriteService = favouriteService;

    }

    public User login(LoginRequest loginRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Incorrect username or password.");
        }

        String rawPassword = loginRequest.getPassword();
        String hashedPassword = optionalUser.get().getPassword();
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw new RuntimeException("Incorrect username or password.");
        }

        return optionalUser.get();
    }



    public User register(RegisterRequest registerRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User with [%s] username already exist.".formatted(registerRequest.getUsername()));
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .userRole(UserRole.USER_ROLE)
                .country(registerRequest.getCountry())
                .isActive(true)
                .createdOn(LocalDateTime.now())
                .build();

        user = userRepository.save(user);


        log.info("New user profile was registered in the system for user [%s].".formatted(registerRequest.getUsername()));

        return user;
    }

    public  List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public  User getById(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new DomainException("User with id [%s] does not exist.".formatted(id)));
    }



    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
    }

    public List<Book> getCurrentUserFavouriteBooks() {
        User currentUser = getCurrentUser();

        if (currentUser.getFavourites() == null) {
            return List.of();
        }

        return currentUser.getFavourites()
                .stream()
                .map(Favorite::getFavoriteBook)  // взимаме Book от Favorite
                .toList();
    }

    public ProfileEditRequest getProfileEditData(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DomainException("User not found"));

        ProfileEditRequest dto = new ProfileEditRequest();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCountry(user.getCountry());
        dto.setProfilePicture(user.getProfilePicture());

        return dto;
    }

    public void updateProfile(String username, ProfileEditRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DomainException("User not found"));


        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setCountry(request.getCountry());
        user.setProfilePicture(request.getProfilePicture());

        userRepository.save(user);
    }



    public void changeUserRole(UUID userId, UserRole newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException("User not found"));

        user.setUserRole(newRole);
        userRepository.save(user);
    }
}
