package davide.project_week_7_backend.services;

import davide.project_week_7_backend.entities.User;
import davide.project_week_7_backend.enums.UserRole;
import davide.project_week_7_backend.exceptions.NotFoundException;
import davide.project_week_7_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public User updateUserRole(UUID userId, UserRole newRole) {
        User user = this.findById(userId);
        user.setRole(newRole);
        return userRepository.save(user);
    }
}
