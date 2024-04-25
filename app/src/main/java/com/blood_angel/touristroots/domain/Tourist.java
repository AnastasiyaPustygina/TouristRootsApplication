package com.blood_angel.touristroots.domain;

import com.blood_angel.touristroots.domain.enums.AgePeriod;
import com.blood_angel.touristroots.domain.enums.Gender;

import java.io.Serializable;

public class Tourist implements Serializable {
    private long id;
    private String name;
    private String email;
    private Gender gender;
    private AgePeriod agePeriod;
    private String information;
    private String password;

    public Tourist(long id, String name, String email, Gender gender, AgePeriod agePeriod,
                   String information, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.agePeriod = agePeriod;
        this.information = information;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public AgePeriod getAgePeriod() {
        return agePeriod;
    }

    public String getInformation() {
        return information;
    }

    public String getPassword() {
        return password;
    }
}
