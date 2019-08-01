package com.tigratius.ticketoffice.controller;

import com.tigratius.ticketoffice.model.Aircraft;
import com.tigratius.ticketoffice.service.AirCraftService;

import java.util.List;

public class AirCraftController {

    private AirCraftService airCraftService;

    public AirCraftController(AirCraftService airCraftService) {

        this.airCraftService = airCraftService;
    }

    public List<Aircraft> getAll() {
        return airCraftService.getAll();
    }

    public void create(String name, int busSeatAmount, int ecoSeatAmount) {
        airCraftService.create(name, busSeatAmount, ecoSeatAmount);
    }

    public void update(Long id, String name, int busSeatAmount, int ecoSeatAmount) {

        airCraftService.update(id, name, busSeatAmount, ecoSeatAmount);
    }

    public void delete(Long id) {

        airCraftService.delete(id);
    }

}
