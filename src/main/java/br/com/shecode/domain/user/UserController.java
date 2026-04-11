package br.com.shecode.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getMe(@RequestParam Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMe(@RequestParam Long id,
                                         @RequestParam String name,
                                         @RequestParam String avatarUrl) {
        User user = userService.update(id, name, avatarUrl);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setCreatedAt(java.time.LocalDateTime.now());
        return ResponseEntity.ok(userService.findOrCreate(
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl()
        ));
    }
}