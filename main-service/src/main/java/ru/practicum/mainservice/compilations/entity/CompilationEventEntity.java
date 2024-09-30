package ru.practicum.mainservice.compilations.entity;

import lombok.*;
import ru.practicum.mainservice.events.entity.EventsEntity;

import javax.persistence.*;
import java.util.Objects;

//@Generated
//@Getter
//@Setter
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(schema = "public", name = "compilation_events")
public class CompilationEventEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false, updatable = false, unique = true)
//    private Integer id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "compilation_id", nullable = false)
//    private CompilationEntity compilation;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_id", nullable = false)
//    private EventsEntity event;
//
//    @OneToMany(mappedBy = "requests", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
//    private Set<ItemEntity> items = new HashSet<>();
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass() || ((CompilationEventEntity) o).id == null || this.id == null) return false;
//        CompilationEventEntity that = (CompilationEventEntity) o;
//        return Objects.equals(id, that.id);
//    }
}
