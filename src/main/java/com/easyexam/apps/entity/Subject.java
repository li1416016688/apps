package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Subject implements Serializable {
    private int id;
    private String name;
    public Subject(){}

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                '}';
    }
}
