package br.com.shecode.domain.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(String category);
    List<Post> findByAuthorId(Long authorId);
}