package davide.project_week_7_backend.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
