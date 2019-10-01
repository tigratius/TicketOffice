package com.tigratius.ticketoffice.service;

import com.tigratius.ticketoffice.model.Aircraft;
import com.tigratius.ticketoffice.model.AircraftSeatAmount;
import com.tigratius.ticketoffice.model.SeatType;
import com.tigratius.ticketoffice.repository.AircraftRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirCraftService {

    private AircraftRepository aircraftRepository;

    public AirCraftService(AircraftRepository aircraftRepository)
    {
        this.aircraftRepository = aircraftRepository;
    }

    public List<Aircraft> getAll() {
        return aircraftRepository.getAll();
    }

    public void create(String name, int busSeatAmount, int ecoSeatAmount) {
        Aircraft aircraft = getAircraft(name, busSeatAmount, ecoSeatAmount);
        aircraftRepository.add(aircraft);
    }

    public void delete(Long id) {
        aircraftRepository.delete(id);
    }

    public void update(Long id, String name, int busSeatAmount, int ecoSeatAmount) {
        Aircraft aircraft = getAircraft(name, busSeatAmount, ecoSeatAmount);
        aircraft.setId(id);
        aircraftRepository.update(aircraft);
    }

    private Aircraft getAircraft(String name, int busSeatAmount, int ecoSeatAmount) {
        Aircraft aircraft = new Aircraft();
        aircraft.setName(name);
        /*Map<String, AircraftSeatAmount> seatNumberMap = new HashMap<>();
        seatNumberMap.put(SeatType.BUSINESS + "", new AircraftSeatAmount(busSeatAmount));
        seatNumberMap.put(SeatType.ECONOMY + "", new AircraftSeatAmount(ecoSeatAmount));*/
        Map<SeatType, AircraftSeatAmount> seatNumberMap = new HashMap<>();
        seatNumberMap.put(SeatType.BUSINESS, new AircraftSeatAmount(busSeatAmount));
        seatNumberMap.put(SeatType.ECONOMY, new AircraftSeatAmount(ecoSeatAmount));
        aircraft.setSeatNumberMap(seatNumberMap);
        return aircraft;
    }
}
