package pl.mimuw.users;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;

    public void addUser(UserDto userDto) throws RuntimeException {
        if (userRepository.existsById(userDto.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(userDto.toUser());
    }

    public User findUserByCredentials(String username, String password) throws RuntimeException {
        return userRepository.findUserByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateBalance(String username, Double amount) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance() + amount < 0) {
            throw new RuntimeException("Not enough money");
        }
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }
}
