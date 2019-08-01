package com.tigratius.ticketoffice.controller;

import com.tigratius.ticketoffice.model.Flight;
import com.tigratius.ticketoffice.service.FlightService;

import java.util.Date;
import java.util.List;

public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /*public List<Flight> findFlights(Date date, String departure, String arrival) {
        return flightService.findFlights(date, departure, arrival);
    }*/

    public List<Flight> getAll() {
        return flightService.getAll();
    }

    public void create(Long airCraftId, Long routeId) throws Exception {
        flightService.create(airCraftId, routeId);
    }

    public void update(Long id, Long airCraftId, Long routeId) throws Exception {
        flightService.update(id, airCraftId, routeId);
    }

    public void delete(Long id) {
        flightService.delete(id);
    }
}
