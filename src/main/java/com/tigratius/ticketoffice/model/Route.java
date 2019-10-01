package com.tigratius.ticketoffice.model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "routes")
public class Route  extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "departure_city_id")
    private City departure;
    @Column(name ="departure_date")
    private Date departureDate;


    @ManyToOne
    @JoinColumn(name = "arrival_city_id")
    private City arrival;


    @Column(name ="arrival_date")
    private Date arrivalDate;

    /**
     * Default Constructor
     */

    public Route() {
    }

    /**
     * Getters and Setters
     */

    public City getArrival() {
                return arrival;
    }

    public void setArrival(City arrival) {
        this.arrival = arrival;
    }

    public City getDeparture() {
        return departure;
    }

    public void setDeparture(City departure) {
        this.departure = departure;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
