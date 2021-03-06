package com.example.garbagecollector.models;

public class User {
    private Integer id;
    private String name, password, country;
    private Integer money, score, photo;


    public User(Integer id, String name, String password, String country, Integer money, Integer score, Integer photo) {
        this.id = id;
        this.money = money;
        this.score = score;
        this.photo = photo;
        this.name = name;
        this.password = password;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

}
