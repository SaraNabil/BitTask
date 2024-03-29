package com.example.bittask.Model;

import java.io.Serializable;

public class HomeModel implements Serializable {
    private int id;
    private String title;
    private String image;

    public HomeModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
