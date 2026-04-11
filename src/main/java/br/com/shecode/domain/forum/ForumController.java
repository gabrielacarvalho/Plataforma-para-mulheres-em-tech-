package br.com.shecode.domain.forum;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumController {

    private final ForumService forumService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAll(
            @RequestParam(required = false) String category) {

        if (category != null) {
            return ResponseEntity.ok(forumService.findByCategory(category));
        }

        return ResponseEntity.ok(forumService.findAll());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        return ResponseEntity.ok(forumService.findById(id));
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> save(@RequestBody Post post,
                                     @RequestParam Long userId) {
        return ResponseEntity.ok(forumService.save(post, userId));
    }

    @PostMapping("/posts/{id}/answers")
    public ResponseEntity<Answer> reply(@PathVariable Long id,
                                        @RequestParam String content,
                                        @RequestParam Long userId) {
        return ResponseEntity.ok(forumService.reply(id, content, userId));
    }

    @PatchMapping("/answers/{id}/accept")
    public ResponseEntity<Answer> accept(@PathVariable Long id) {
        return ResponseEntity.ok(forumService.accept(id));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        forumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}