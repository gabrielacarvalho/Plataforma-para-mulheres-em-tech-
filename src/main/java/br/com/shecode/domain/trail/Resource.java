package br.com.shecode.domain.trail;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resources")
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String type;
    private boolean free;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Step step;
}