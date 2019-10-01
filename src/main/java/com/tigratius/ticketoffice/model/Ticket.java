package com.tigratius.ticketoffice.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private SeatType seatType;
    @ManyToOne
    @JoinColumn(name ="flight_id")
    private Flight flight;
    @ManyToOne
    @JoinColumn(name ="passenger_id")
    private Passenger passenger;
    private double price;

    /**
     * Default Constructor
     */

    public Ticket() {
    }

    /**
     * Getters and Setters
     */

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
