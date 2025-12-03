package book_library_app.web.dto;


import book_library_app.user.model.Country;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEditRequest {


    @Size(max = 50, message = "Първото име е твърде дълго")
    private String firstName;

    @Size(max = 50, message = "Фамилията е твърде дълга")
    private String lastName;

    @Email(message = "Невалиден имейл адрес")
    private String email;

    private Country country;

    @Size(max = 255, message = "Линкът към снимката е твърде дълъг")
    private String profilePicture;
}
