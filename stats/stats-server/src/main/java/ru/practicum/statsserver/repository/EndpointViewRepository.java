package ru.practicum.statsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.entity.EndpointViewEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointViewRepository extends JpaRepository<EndpointViewEntity, Long>  {
    List<EndpointViewEntity> findAllByTimestampAfterAndTimestampBeforeAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    List<EndpointViewEntity> findAllByTimestampAfterAndTimestampBefore(LocalDateTime start, LocalDateTime end);
}
