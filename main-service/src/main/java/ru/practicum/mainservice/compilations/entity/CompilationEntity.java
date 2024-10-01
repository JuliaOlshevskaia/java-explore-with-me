package ru.practicum.mainservice.compilations.entity;

import lombok.*;
import ru.practicum.mainservice.events.entity.EventsEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Generated
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "compilation")
public class CompilationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pinned")
    private Boolean pinned;

    @OneToMany(mappedBy = "compilation", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private Set<EventsEntity> events = new HashSet<>();
}
