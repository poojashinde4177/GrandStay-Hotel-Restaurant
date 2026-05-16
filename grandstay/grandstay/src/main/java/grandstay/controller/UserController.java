package grandstay.controller;

import grandstay.model.User;
import grandstay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("customer");
        }

        return userRepository.save(user);
    }

    // LOGIN
    @PostMapping("/login")
    public User loginUser(@RequestBody User loginData) {

        Optional<User> userOptional = userRepository.findByEmail(loginData.getEmail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email not registered!");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(loginData.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }

        return user;
    }
}