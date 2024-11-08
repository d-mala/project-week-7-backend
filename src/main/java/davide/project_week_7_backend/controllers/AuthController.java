package davide.project_week_7_backend.controllers;

import davide.project_week_7_backend.entities.User;
import davide.project_week_7_backend.enums.UserRole;
import davide.project_week_7_backend.exceptions.UnauthorizedException;
import davide.project_week_7_backend.payloads.LoginDTO;
import davide.project_week_7_backend.payloads.RegisterDTO;
import davide.project_week_7_backend.security.JWTTools;
import davide.project_week_7_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated RegisterDTO body) {
        User user = new User();
        user.setName(body.getName());
        user.setSurname(body.getSurname());
        user.setEmail(body.getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setRole(UserRole.NORMAL_USER);
        return userService.save(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Validated LoginDTO body) {
        User user = userService.findByEmail(body.getEmail());
        if (passwordEncoder.matches(body.getPassword(), user.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("token", jwtTools.createToken(user));
            return response;
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
