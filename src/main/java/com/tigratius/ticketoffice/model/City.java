package com.tigratius.ticketoffice.model;

public class City extends BaseEntity{

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
