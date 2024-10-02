package ru.practicum.mainservice.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.events.entity.LocationsEntity;

@Repository
public interface LocationsRepository extends JpaRepository<LocationsEntity, Integer> {
    LocationsEntity findAllByLatAndLon(Float lat, Float lon);

}
