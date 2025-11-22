package book_library_app.user.service;


import book_library_app.user.model.User;
import book_library_app.user.model.UserRole;
import book_library_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String role =
                user.getUserRole() == UserRole.ADMIN_ROLE
                        ? "ADMIN"
                        : "USER";

        // org.springframework.security.core.userdetails.User
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(role)                 // -> ROLE_USER / ROLE_ADMIN
                .disabled(!user.isActive())  // ако имаш флаг isActive
                .build();
    }
}
