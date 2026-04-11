package br.com.shecode.domain.trail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Long> {
    List<Trail> findByCategory(String category);
    List<Trail> findByLevel(String level);
    List<Trail> findByCategoryAndLevel(String category, String level);
}