package davide.project_week_7_backend.controllers;

import davide.project_week_7_backend.entities.User;
import davide.project_week_7_backend.payloads.UserRoleUpdateDTO;
import davide.project_week_7_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page<User> getAllUsers(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) 
            Pageable pageable) {
        return userService.getAll(pageable);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/users/{userId}/role")
    public User updateUserRole(@PathVariable UUID userId, @RequestBody @Validated UserRoleUpdateDTO body) {
        return userService.updateUserRole(userId, body.getRole());
    }
}
