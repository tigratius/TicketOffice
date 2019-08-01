package com.tigratius.ticketoffice.service;

import com.tigratius.ticketoffice.model.Passenger;
import com.tigratius.ticketoffice.repository.PassengerRepository;

import java.sql.Date;
import java.util.List;

public class PassengerService {

    private PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {

        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAll(){
        return passengerRepository.getAll();
    }

    public void create(String firstName, String lastName, Date date) {
        Passenger passenger = getPassenger(firstName, lastName, date);
        passengerRepository.add(passenger);
    }

    public void delete(Long id){
        passengerRepository.delete(id);
    }

    public void update(Long id, String firstName, String lastName, Date date){
        Passenger passenger = getPassenger(firstName, lastName, date);
        passenger.setId(id);
        passengerRepository.update(passenger);
    }

    private Passenger getPassenger(String firstName, String lastName, Date date) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setBirthDate(date);
        return passenger;
    }
}
