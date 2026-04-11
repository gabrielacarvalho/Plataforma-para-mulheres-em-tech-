package br.com.shecode.domain.trail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/trails")
@RequiredArgsConstructor
public class TrailController {

    private final TrailService trailService;

    @GetMapping
    public ResponseEntity<List<Trail>> findAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level) {

        if (category != null && level != null) {
            return ResponseEntity.ok(trailService.findByCategoryAndLevel(category, level));
        }
        if (category != null) {
            return ResponseEntity.ok(trailService.findByCategory(category));
        }
        if (level != null) {
            return ResponseEntity.ok(trailService.findByLevel(level));
        }

        return ResponseEntity.ok(trailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trail> findById(@PathVariable Long id) {
        return ResponseEntity.ok(trailService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Trail> save(@RequestBody Trail trail) {
        return ResponseEntity.ok(trailService.save(trail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}