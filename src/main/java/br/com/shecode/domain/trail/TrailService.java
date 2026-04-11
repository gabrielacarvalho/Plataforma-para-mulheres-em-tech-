package br.com.shecode.domain.trail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrailService {

    private final TrailRepository trailRepository;

    public List<Trail> findAll() {
        return trailRepository.findAll();
    }

    public List<Trail> findByCategory(String category) {
        return trailRepository.findByCategory(category);
    }

    public List<Trail> findByLevel(String level) {
        return trailRepository.findByLevel(level);
    }

    public List<Trail> findByCategoryAndLevel(String category, String level) {
        return trailRepository.findByCategoryAndLevel(category, level);
    }

    public Trail findById(Long id) {
        return trailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trilha não encontrada"));
    }

    public Trail save(Trail trail) {
        trail.setCreatedAt(LocalDateTime.now());
        return trailRepository.save(trail);
    }

    public void delete(Long id) {
        trailRepository.deleteById(id);
    }
}