package book_library_app.web.dto;

import book_library_app.user.model.Country;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Size(min = 8, message = "Username must be at least 8 symbols")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 symbols")
    private String password;

    @NotNull
    private Country country;
}
