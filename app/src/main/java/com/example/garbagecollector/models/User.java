package com.example.garbagecollector.models;

public class User {
    private Integer id;
    private String name, password, country;
    private Integer money, score;
    private String photo;


    public User(Integer id, String name, String password, String country, Integer money, Integer score, String photo) {
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

    public String getPhoto() {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
