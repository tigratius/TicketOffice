package com.tigratius.ticketoffice.service;

import com.tigratius.ticketoffice.model.City;
import com.tigratius.ticketoffice.model.Route;
import com.tigratius.ticketoffice.repository.CityRepository;
import com.tigratius.ticketoffice.repository.RouteRepository;

import java.sql.Date;
import java.util.List;

public class RouteService {

    private RouteRepository routeRepository;
    private CityRepository cityRepository;

    public RouteService(RouteRepository routeRepository, CityRepository cityRepository) {
        this.routeRepository = routeRepository;
        this.cityRepository = cityRepository;
    }

    public List<Route> getAll() {

        return routeRepository.getAll();
    }

    public void create(Long departureCityId, Date departureDate, Long arrivalCityId, Date arrivalDate) throws Exception {
        Route route = getRoute(departureCityId, departureDate, arrivalCityId, arrivalDate);
        routeRepository.add(route);
    }

    public void delete(Long id) {
        routeRepository.delete(id);
    }

    public void update(Long id, Long departureCityId, Date departureDate, Long arrivalCityId, Date arrivalDate) throws Exception {
        Route route = getRoute(departureCityId, departureDate, arrivalCityId, arrivalDate);
        route.setId(id);
        routeRepository.update(route);
    }

    private Route getRoute(Long departureCityId, Date departureDate, Long arrivalCityId, Date arrivalDate) throws Exception {
        City departureCity = cityRepository.getById(departureCityId);
        City arrivalCity = cityRepository.getById(arrivalCityId);
        Route route = new Route();
        route.setDeparture(departureCity);
        route.setDepartureDate(departureDate);
        route.setArrival(arrivalCity);
        route.setArrivalDate(arrivalDate);
        return route;
    }
}
