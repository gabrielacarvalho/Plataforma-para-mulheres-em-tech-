package br.com.shecode.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreate(String name, String email, String avatarUrl) {
        Optional<User> existing = userRepository.findByEmail(email);

        if (existing.isPresent()) {
            return existing.get();
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setAvatarUrl(avatarUrl);
        newUser.setCreatedAt(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuária não encontrada"));
    }

    public User update(Long id, String name, String avatarUrl) {
        User user = findById(id);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }
}