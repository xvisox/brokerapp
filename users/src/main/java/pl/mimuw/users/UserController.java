package pl.mimuw.users;

import io.jsonwebtoken.JwtException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static pl.mimuw.users.Utility.*;

@RestController
@RequestMapping("api/v1/users")
@Data
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    /* Endpoints exposed to the client */

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserDto user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok(toResponse(Utility.MESSAGE, "User registered"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(toResponse(MESSAGE, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserDto user) {
        try {
            User foundUser = userService.findUserByCredentials(user.getUsername(), user.getPassword());
            String token = jwtService.createToken(foundUser.getUsername());
            return ResponseEntity.ok(toResponse(TOKEN, token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(toResponse(MESSAGE, e.getMessage()));
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtService.validateTokenAndGetUsername(token.substring(7));
            return ResponseEntity.ok(toResponse(BALANCE, userService.getBalance(username)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(toResponse(MESSAGE, e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtService.validateTokenAndGetUsername(token.substring(7));
            return ResponseEntity.ok(
                    toResponse(
                            List.of(USERNAME, BALANCE),
                            List.of(username, userService.getBalance(username))
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(toResponse(MESSAGE, e.getMessage()));
        }
    }

    /* Endpoints exposed to the other services */

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validate(@PathVariable String token) {
        try {
            String username = jwtService.validateTokenAndGetUsername(token);
            return ResponseEntity.ok(username);
        } catch (JwtException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/balance/{amount}/increase")
    public ResponseEntity<?> increaseBalance(@PathVariable String username, @PathVariable Double amount) {
        try {
            userService.updateBalance(username, amount);
            return ResponseEntity.ok("Balance updated");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/balance/{amount}/decrease")
    public ResponseEntity<?> decreaseBalance(@PathVariable String username, @PathVariable Double amount) {
        try {
            userService.updateBalance(username, -amount);
            return ResponseEntity.ok("Balance updated");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
