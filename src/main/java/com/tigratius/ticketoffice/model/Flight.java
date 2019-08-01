package com.tigratius.ticketoffice.model;

import java.util.HashMap;

public class Flight extends BaseEntity {

    private Aircraft aircraft;
    private Route route;

    private HashMap<SeatType, Integer> seatOccupiedMap = new HashMap<>();

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    /*OccupiedSeats*/

    public void setSeatOccupiedMap(HashMap<SeatType, Integer> seatOccupiedMap) {
        this.seatOccupiedMap = seatOccupiedMap;
    }

    public int getOccupiedSeatsBySeatType(SeatType seatType)
    {

        return seatOccupiedMap.get(seatType);
    }

    public int getFreeSeatsBySeatType(SeatType seatType)
    {
        return aircraft.getNumberSeatsBySeatType(seatType) - getOccupiedSeatsBySeatType(seatType);
    }

    public boolean occupySeat(SeatType seatType)
    {
        int occupiedSeats = getOccupiedSeatsBySeatType(seatType);

        if (occupiedSeats < getFreeSeatsBySeatType(seatType)) {
            seatOccupiedMap.put(seatType, occupiedSeats + 1);
            return true;
        }

        return false;
    }

    public void reassignSeat(SeatType seatType)
    {
        int occupiedSeats = getOccupiedSeatsBySeatType(seatType);
        seatOccupiedMap.put(seatType, occupiedSeats - 1);
    }
}
