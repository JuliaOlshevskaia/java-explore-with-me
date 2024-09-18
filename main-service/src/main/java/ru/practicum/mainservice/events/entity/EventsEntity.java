package ru.practicum.mainservice.events.entity;

import lombok.*;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;
import ru.practicum.mainservice.events.dto.StateEnum;
import ru.practicum.mainservice.users.entity.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Generated
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "events")
public class EventsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoriesEntity category;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private UserEntity initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private LocationsEntity location;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    @Column(name = "state", nullable = false)
    private StateEnum state;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "views")
    private Integer views;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || ((EventsEntity) o).id == null || this.id == null) return false;
        EventsEntity that = (EventsEntity) o;
        return Objects.equals(id, that.id);
    }
}
