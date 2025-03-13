package com.example.proiectlab4.domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected int id;

    Entity(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String toFile();
}
