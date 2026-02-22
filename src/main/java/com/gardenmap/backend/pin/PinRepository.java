package com.gardenmap.backend.pin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findByMapId(Long mapId);
}
