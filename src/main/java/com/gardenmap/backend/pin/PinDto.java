package com.gardenmap.backend.pin;

import java.time.OffsetDateTime;

public record PinDto(
        Long id,
        Double xPercent,
        Double yPercent,
        String name,
        String type,
        OffsetDateTime lastWatered,
        Integer wateringIntervalDays
) {
    static PinDto fromEntity(Pin pin) {
        return new PinDto(
                pin.getId(),
                pin.getXPercent(),
                pin.getYPercent(),
                pin.getName(),
                pin.getType(),
                pin.getLastWatered(),
                pin.getWateringIntervalDays()
        );
    }
}
