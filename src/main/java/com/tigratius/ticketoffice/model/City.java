package com.tigratius.ticketoffice.model;

import javax.persistence.*;

@Entity
@Table(name = "cities")
public class City extends BaseEntity {

    private String name;

    /**
     * Default Constructor
     */

    public City() {
    }

    /**
     * Getters and Setters
     */

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
