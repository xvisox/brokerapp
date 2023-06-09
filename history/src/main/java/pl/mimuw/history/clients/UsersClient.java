package pl.mimuw.history.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service", url = "${application.config.users-url}")
public interface UsersClient {
    @GetMapping("/validate/{token}")
    ResponseEntity<String> validate(@PathVariable String token);
}
