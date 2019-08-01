package com.tigratius.ticketoffice.service;

import com.tigratius.ticketoffice.model.Aircraft;
import com.tigratius.ticketoffice.model.Flight;
import com.tigratius.ticketoffice.model.Route;
import com.tigratius.ticketoffice.model.SeatType;
import com.tigratius.ticketoffice.repository.AircraftRepository;
import com.tigratius.ticketoffice.repository.FlightRepository;
import com.tigratius.ticketoffice.repository.RouteRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    /*public List<Flight> findFlights(Date date, String departure, String arrival) {

        List<Flight> flights = flightRepository.getAll();

        if (date != null) {
            flights = flights.stream().filter(flight -> sdf.format(flight.getRoute().getDepartureDate()).equals(sdf.format(date))).collect(Collectors.toList());
        }

        if (!departure.isEmpty()) {
            flights = flights.stream().filter(flight -> flight.getRoute().getDeparture().getName().toLowerCase().contains(departure.toLowerCase())).collect(Collectors.toList());
        }

        if (!arrival.isEmpty()) {
            flights = flights.stream().filter(flight -> flight.getRoute().getArrival().getName().toLowerCase().contains(arrival.toLowerCase())).collect(Collectors.toList());
        }

        return flights;
    }*/

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
        Flight flight = flightRepository.getById(id);
        Aircraft aircraft = aircraftRepository.getById(airCraftId);
        Route route = routeRepository.getById(routeId);

        flight.setAircraft(aircraft);
        flight.setRoute(route);

        flightRepository.update(flight);
    }

    private Flight getFlight(Long airCraftId, Long routeId) throws Exception {

        Aircraft aircraft = aircraftRepository.getById(airCraftId);
        Route route = routeRepository.getById(routeId);

        Flight flight = new Flight();
        flight.setAircraft(aircraft);
        flight.setRoute(route);
        HashMap<SeatType, Integer> occupiedSeatMap = new HashMap<>();
        occupiedSeatMap.put(SeatType.BUSINESS, 0);
        occupiedSeatMap.put(SeatType.ECONOMY, 0);
        flight.setSeatOccupiedMap(occupiedSeatMap);
        return flight;
    }
}
