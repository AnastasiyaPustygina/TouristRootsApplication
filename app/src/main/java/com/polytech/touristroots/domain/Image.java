package com.polytech.touristroots.domain;


import java.io.Serializable;

public class Image implements Serializable {
    private long id;
    private String path;

    public long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Image(long id, String path) {
        this.id = id;
        this.path = path;
    }
}
