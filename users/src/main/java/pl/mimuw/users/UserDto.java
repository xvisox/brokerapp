package pl.mimuw.users;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setBalance(10_000.0); // TODO: hardcoded, should be configurable
        return user;
    }
}
