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

    public Double updateBalance(String username, Double amount) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance() + amount < 0) {
            throw new RuntimeException("Not enough money");
        }
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return roundBalance(user.getBalance());
    }

    public Double getBalance(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return roundBalance(user.getBalance());
    }

    private Double roundBalance(Double balance) {
        return Math.round(balance * 100.0) / 100.0;
    }
}
