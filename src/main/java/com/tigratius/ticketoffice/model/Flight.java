package com.tigratius.ticketoffice.model;


import javax.persistence.*;

@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    //
    /*private HashMap<SeatType, Integer> seatOccupiedMap = new HashMap<>();*/

    /**
     * Default Constructor
     */

    public Flight() {
    }

    /**
     * Getters and Setters
     */

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

   /* public void setSeatOccupiedMap(HashMap<SeatType, Integer> seatOccupiedMap) {
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
    }*/
}
