package com.booking.model;

public class Location {

    private Long locationId;
    private String name;
    private String type;
    private Long parentId;

    public Location() {
    }

    public Location(Long locationId, String name, String type, Long parentId) {
        this.locationId = locationId;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}