package com.example.web;

import org.springframework.stereotype.Service;

import java.util.ArrayList;


public class User {

    private Long id;
    private String name;
    private Integer age;
    private Worker worker;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker=worker;
    }
}
