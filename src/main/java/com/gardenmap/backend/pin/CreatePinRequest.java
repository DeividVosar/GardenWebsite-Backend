package com.gardenmap.backend.pin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreatePinRequest(
        @NotNull @Min(0) @Max(100) Double xPercent,
        @NotNull @Min(0) @Max(100) Double yPercent,
        String name,
        String type,
        @Min(1) Integer wateringIntervalDays
) { }
