package ru.practicum.mainservice.comments.entity;

import lombok.*;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.users.entity.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentator_id", nullable = false)
    private UserEntity commentator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventsEntity event;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || ((CommentEntity) o).id == null || this.id == null) return false;
        CommentEntity that = (CommentEntity) o;
        return Objects.equals(id, that.id);
    }
}
