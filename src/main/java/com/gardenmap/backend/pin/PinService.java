package com.gardenmap.backend.pin;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gardenmap.backend.common.NotFoundException;

@Service
@Transactional
public class PinService {

    private final PinRepository pinRepository;

    public PinService(PinRepository pinRepository) {
        this.pinRepository = pinRepository;
    }

    @Transactional(readOnly = true)
    public List<PinDto> getPinsForMap(Long mapId) {
        return pinRepository.findByMapId(mapId).stream()
                .map(PinDto::fromEntity)
                .toList();
    }

    public PinDto createPin(Long mapId, CreatePinRequest req) {
        Pin pin = new Pin(
                mapId,
                req.xPercent(),
                req.yPercent(),
                req.name(),
                req.type(),
                req.wateringIntervalDays()
        );

        Pin saved = pinRepository.save(pin);
        return PinDto.fromEntity(saved);
    }

    public PinDto markAsWatered(Long pinId) {
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new NotFoundException("Pin not found: " + pinId));

        // Always store UTC for consistency
        pin.setLastWatered(OffsetDateTime.now(ZoneOffset.UTC));

        return PinDto.fromEntity(pinRepository.save(pin));
    }

    public PinDto updatePin(Long pinId, JsonNode patch) {
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new NotFoundException("Pin not found: " + pinId));

        pin.applyPatch(patch);

        return PinDto.fromEntity(pinRepository.save(pin));
    }

    public void deletePin(Long pinId) {
        try {
            pinRepository.deleteById(pinId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Pin not found: " + pinId);
        }
    }
}
