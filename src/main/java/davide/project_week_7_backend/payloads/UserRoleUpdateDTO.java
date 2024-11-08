package davide.project_week_7_backend.payloads;

import davide.project_week_7_backend.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRoleUpdateDTO {
    @NotNull(message = "Role is required")
    private UserRole role;
}
