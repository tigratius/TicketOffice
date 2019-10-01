package com.tigratius.ticketoffice.service;

import com.tigratius.ticketoffice.model.*;
import com.tigratius.ticketoffice.repository.AircraftRepository;
import com.tigratius.ticketoffice.repository.FlightRepository;
import com.tigratius.ticketoffice.repository.RouteRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public class FlightService {

    private FlightRepository flightRepository;
    private AircraftRepository aircraftRepository;
    private RouteRepository routeRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FlightService(FlightRepository flightRepository, AircraftRepository aircraftRepository, RouteRepository routeRepository) {
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
    }

    public List<Flight> getAll() {
        return flightRepository.getAll();
    }

    public void create(Long airCraftId, Long routeId) throws Exception {
        Flight flight = getFlight(airCraftId, routeId);
        flightRepository.add(flight);
    }

    public void delete(Long id) {
        flightRepository.delete(id);
    }

    public void update(Long id, Long airCraftId, Long routeId) throws Exception {
        Flight flight = getFlight(airCraftId, routeId);
        flight.setId(id);
        flightRepository.update(flight);
    }

    private Flight getFlight(Long airCraftId, Long routeId) throws Exception {

        Aircraft aircraft = aircraftRepository.getById(airCraftId);
        Route route = routeRepository.getById(routeId);

        Flight flight = new Flight();
        flight.setAircraft(aircraft);
        flight.setRoute(route);
        return flight;
    }
}
