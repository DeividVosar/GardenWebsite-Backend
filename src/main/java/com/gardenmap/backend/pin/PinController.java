package com.gardenmap.backend.pin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PinController {

    private final PinService pinService;

    public PinController(PinService pinService) {
        this.pinService = pinService;
    }

    @GetMapping("/maps/{mapId}/pins")
    public List<PinDto> getPinsForMap(@PathVariable Long mapId) {
        return pinService.getPinsForMap(mapId);
    }

    @PostMapping("/maps/{mapId}/pins")
    @ResponseStatus(HttpStatus.CREATED)
    public PinDto createPin(@PathVariable Long mapId, @Valid @RequestBody CreatePinRequest req) {
        return pinService.createPin(mapId, req);
    }

    @PostMapping("/pins/{pinId}/water")
    public PinDto markAsWatered(@PathVariable Long pinId) {
        return pinService.markAsWatered(pinId);
    }

    @PatchMapping("/pins/{pinId}")
    public PinDto updatePin(@PathVariable Long pinId, @RequestBody JsonNode patch) {
        return pinService.updatePin(pinId, patch);
    }

    @DeleteMapping("/pins/{pinId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePin(@PathVariable Long pinId) {
        pinService.deletePin(pinId);
    }
}
