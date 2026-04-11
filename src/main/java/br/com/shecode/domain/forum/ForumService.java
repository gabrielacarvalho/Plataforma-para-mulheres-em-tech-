package br.com.shecode.domain.forum;

import br.com.shecode.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final AnswerRepository answerRepository;
    private final UserService userService;

    public List<Post> findAll() {
        return forumRepository.findAll();
    }

    public List<Post> findByCategory(String category) {
        return forumRepository.findByCategory(category);
    }

    public Post findById(Long id) {
        return forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));
    }

    public Post save(Post post, Long userId) {
        post.setAuthor(userService.findById(userId));
        post.setCreatedAt(LocalDateTime.now());
        return forumRepository.save(post);
    }

    public Answer reply(Long postId, String content, Long userId) {
        Post post = findById(postId);

        Answer answer = new Answer();
        answer.setPost(post);
        answer.setContent(content);
        answer.setAuthor(userService.findById(userId));
        answer.setAccepted(false);
        answer.setCreatedAt(LocalDateTime.now());

        return answerRepository.save(answer);
    }

    public Answer accept(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Resposta não encontrada"));
        answer.setAccepted(true);
        return answerRepository.save(answer);
    }

    public void delete(Long id) {
        forumRepository.deleteById(id);
    }
}