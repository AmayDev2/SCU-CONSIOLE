package com.amay.scu.model;

public class SLELocation {
    private String location;
    private String name;
    private String status;
    private String type;
    private String id;

    public SLELocation(String location, String name, String status, String type, String id) {
        this.location = location;
        this.name = name;
        this.status = status;
        this.type = type;
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
