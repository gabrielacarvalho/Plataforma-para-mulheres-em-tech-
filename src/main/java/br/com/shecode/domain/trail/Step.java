package br.com.shecode.domain.trail;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "steps")
@Data
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "step_order")
    private int order;
    private String title;
    private String objective;

    @ManyToOne
    @JoinColumn(name = "trail_id")
    private Trail trail;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    private List<Resource> resources;
}