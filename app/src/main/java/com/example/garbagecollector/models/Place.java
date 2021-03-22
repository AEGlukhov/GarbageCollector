package com.example.garbagecollector.models;

public class Place {
    Integer id;
    Integer ownerId;
    Integer cleanerId;
    String address;
    String date;
    String photo;
    Double lat;
    Double lon;
    String proof;

    public Place(Integer id, Integer ownerId, Integer cleanerId, String address, String date, String photo, Double lat, Double lon, String proof) {
        this.id = id;
        this.ownerId = ownerId;
        this.cleanerId = cleanerId;
        this.address = address;
        this.date = date;
        this.photo = photo;
        this.lat = lat;
        this.lon = lon;
        this.proof = proof;
    }

    public String getProof() {
        return proof;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Integer getCleanerId() {
        return cleanerId;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getPhoto() {
        return photo;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}

