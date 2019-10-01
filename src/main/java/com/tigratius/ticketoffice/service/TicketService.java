package com.tigratius.ticketoffice.service;

import com.tigratius.ticketoffice.model.Flight;
import com.tigratius.ticketoffice.model.Passenger;
import com.tigratius.ticketoffice.model.SeatType;
import com.tigratius.ticketoffice.model.Ticket;
import com.tigratius.ticketoffice.repository.FlightRepository;
import com.tigratius.ticketoffice.repository.PassengerRepository;
import com.tigratius.ticketoffice.repository.TicketRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TicketService {

    private FlightRepository flightRepository;
    private PassengerRepository passengerRepository;
    private TicketRepository ticketRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final String noSeatsMessage = "Нет свободных мест!";

    public TicketService(FlightRepository flightRepository, TicketRepository ticketRepository, PassengerRepository passengerRepository) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
    }

    public void buyTicket(Long flightId, Long passengerId, SeatType seatType, Double price) throws Exception {

        Flight flight = flightRepository.getById(flightId);
        Passenger passenger = passengerRepository.getById(passengerId);

        if (!canOccupySeat(seatType, flightId))
            throw new Exception(noSeatsMessage);

        Ticket ticket = getTicket(flight, passenger, seatType, price);

        ticketRepository.add(ticket);
    }

    public void returnTicket(Long ticketId) throws Exception {
        ticketRepository.delete(ticketId);
    }

    public void update(Long id, Long flightId, Long passengerId, SeatType seatType, Double price) throws Exception {

        Flight flight = flightRepository.getById(flightId);
        Passenger passenger = passengerRepository.getById(passengerId);

        if (!canOccupySeat(seatType, flightId))
            throw new Exception(noSeatsMessage);

        Ticket ticket = getTicket(flight, passenger, seatType, price);
        ticket.setId(id);

        ticketRepository.update(ticket);
    }

    public List<Ticket> findTicket(Date date, String from, String to, SeatType seatType, String firstName, String lastName) {

        List<Ticket> tickets = ticketRepository.getAll();

        if (date != null) {
            tickets= tickets.stream().filter(ticket -> sdf.format(ticket.getFlight().getRoute().getDepartureDate()).equals(sdf.format(date))).collect(Collectors.toList());
        }

        if (!from.isEmpty()) {
            tickets = tickets.stream().filter(ticket -> ticket.getFlight().getRoute().getDeparture().getName().toLowerCase().contains(from.toLowerCase())).collect(Collectors.toList());
        }

        if (!to.isEmpty()) {
            tickets = tickets.stream().filter(ticket -> ticket.getFlight().getRoute().getArrival().getName().toLowerCase().contains(to.toLowerCase())).collect(Collectors.toList());
        }

        if (seatType != null) {
            tickets = tickets.stream().filter((ticket -> ticket.getSeatType() == seatType)).collect(Collectors.toList());
        }

        if (!firstName.isEmpty()) {
            tickets = tickets.stream().filter(ticket -> ticket.getPassenger().getFirstName().toLowerCase().contains(firstName.toLowerCase())).collect(Collectors.toList());
        }

        if (!lastName.isEmpty()) {
            tickets = tickets.stream().filter(ticket -> ticket.getPassenger().getLastName().toLowerCase().contains(lastName.toLowerCase())).collect(Collectors.toList());
        }

        return tickets;
    }

    public List<Ticket> getAll() {
        return ticketRepository.getAll();
    }

    private Ticket getTicket(Flight flight, Passenger passenger, SeatType seatType, Double price)
    {
        Ticket ticket = new Ticket();
        ticket.setSeatType(seatType);
        ticket.setFlight(flight);
        ticket.setPassenger(passenger);
        ticket.setPrice(price);
        return ticket;
    }

    private int getOccupiedSeatsAmount(SeatType seatType, Long flightId)
    {
        List<Ticket> tickets = ticketRepository.getAll();
        int amount = (int) tickets.stream().filter(ticket -> ticket.getSeatType() == seatType && ticket.getFlight().getId() == flightId).count();
        return amount;
    }

    private boolean canOccupySeat(SeatType seatType, Long flightId) throws Exception {

        Flight flight = flightRepository.getById(flightId);
        int seatsAmount =  flight.getAircraft().getNumberSeatsBySeatType(seatType);
        int occupiedSeats = getOccupiedSeatsAmount(seatType, flightId);

        /*System.out.println("***");
        System.out.println("seatsAmount = " + seatsAmount);
        System.out.println("occupiedSeats = " + occupiedSeats);
        System.out.println("***");*/

        return occupiedSeats < seatsAmount;
    }
}
