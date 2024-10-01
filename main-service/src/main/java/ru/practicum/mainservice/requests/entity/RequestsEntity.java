package ru.practicum.mainservice.requests.entity;

import lombok.*;
import ru.practicum.mainservice.events.dto.StateEnum;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.users.entity.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Generated
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "requests")
public class RequestsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventsEntity event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private UserEntity requester;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "status", nullable = false)
    private StateEnum status;
}
