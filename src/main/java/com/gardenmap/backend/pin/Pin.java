package com.gardenmap.backend.pin;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.databind.JsonNode;
import com.gardenmap.backend.common.BadRequestException;

@Entity
@Table(name = "pins")
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "map_id", nullable = false)
    private Long mapId;

    @Column(name = "x_percent", nullable = false)
    private Double xPercent;

    @Column(name = "y_percent", nullable = false)
    private Double yPercent;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "last_watered")
    private OffsetDateTime lastWatered;

    @Column(name = "watering_interval_days")
    private Integer wateringIntervalDays;

    protected Pin() { } // required by JPA

    public Pin(Long mapId, Double xPercent, Double yPercent, String name, String type, Integer wateringIntervalDays) {
        this.mapId = mapId;
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.name = name;
        this.type = type;
        this.wateringIntervalDays = wateringIntervalDays;
    }

    public void setLastWatered(OffsetDateTime lastWatered) {
        this.lastWatered = lastWatered;
    }

    /**
     * Applies a JSON patch.
     *
     * Rules:
     * - If a field is not present => unchanged
     * - If a field is present with null => sets nullable fields to null
     * - xPercent/yPercent cannot be null and must be in [0, 100]
     * - wateringIntervalDays can be null or an integer >= 1
     * - name/type: null clears; blank string becomes null
     */
    public void applyPatch(JsonNode patch) {
        if (patch == null || patch.isNull()) return;

        if (patch.has("xPercent")) {
            this.xPercent = readPercentRequired(patch.get("xPercent"), "xPercent");
        }
        if (patch.has("yPercent")) {
            this.yPercent = readPercentRequired(patch.get("yPercent"), "yPercent");
        }
        if (patch.has("name")) {
            this.name = readTrimmedTextOrNull(patch.get("name"));
        }
        if (patch.has("type")) {
            this.type = readTrimmedTextOrNull(patch.get("type"));
        }
        if (patch.has("wateringIntervalDays")) {
            this.wateringIntervalDays = readIntervalOrNull(patch.get("wateringIntervalDays"));
        }
    }

    private static Double readPercentRequired(JsonNode node, String fieldName) {
        if (node == null || node.isNull()) {
            throw new BadRequestException(fieldName + " cannot be null");
        }
        if (!node.isNumber()) {
            throw new BadRequestException(fieldName + " must be a number");
        }
        double value = node.doubleValue();
        if (value < 0 || value > 100) {
            throw new BadRequestException(fieldName + " must be between 0 and 100");
        }
        return value;
    }

    private static String readTrimmedTextOrNull(JsonNode node) {
        if (node == null || node.isNull()) return null;
        if (!node.isTextual()) {
            throw new BadRequestException("name/type must be a string or null");
        }
        String trimmed = node.asText().trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private static Integer readIntervalOrNull(JsonNode node) {
        if (node == null || node.isNull()) return null;
        if (!node.isInt() && !node.isLong()) {
            throw new BadRequestException("wateringIntervalDays must be an integer or null");
        }
        int value = node.intValue();
        if (value < 1) {
            throw new BadRequestException("wateringIntervalDays must be at least 1");
        }
        return value;
    }

    public Long getId() { return id; }
    public Long getMapId() { return mapId; }
    public Double getXPercent() { return xPercent; }
    public Double getYPercent() { return yPercent; }
    public String getName() { return name; }
    public String getType() { return type; }
    public OffsetDateTime getLastWatered() { return lastWatered; }
    public Integer getWateringIntervalDays() { return wateringIntervalDays; }
}
