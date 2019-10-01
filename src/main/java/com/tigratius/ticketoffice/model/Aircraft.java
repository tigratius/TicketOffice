package com.tigratius.ticketoffice.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "aircrafts")
public class Aircraft extends BaseEntity {

    private String name;

    @MapKeyColumn(name = "seat_type")
    @MapKeyEnumerated(EnumType.STRING)
    @JoinColumn(name = "aircraft_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Map<SeatType, AircraftSeatAmount> seatNumberMap;

    /**
     * Default Constructor
     */

    public Aircraft() {
    }

    /**
     * Getters and Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<SeatType, AircraftSeatAmount> getSeatNumberMap() {
        return seatNumberMap;
    }

    public void setSeatNumberMap(Map<SeatType, AircraftSeatAmount> seatNumberMap) {
        this.seatNumberMap = seatNumberMap;
    }

    public int getNumberSeatsBySeatType(SeatType seatType) {
        return seatNumberMap.get(seatType).getAmount();
    }
}
