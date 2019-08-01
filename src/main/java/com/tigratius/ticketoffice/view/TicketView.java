package com.tigratius.ticketoffice.view;

import com.tigratius.ticketoffice.controller.TicketController;
import com.tigratius.ticketoffice.model.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class TicketView extends BaseView {

    private TicketController ticketController;

    private final String printHeaderTickets = "Проданные билеты\n" +
            "ID; AircraftName; DepartureDate; DepartureCity; ArrivalDate; ArrivalCity; SeatType; FirstName; LastName; Price";

    private final String mainMenuMessage = "Выберете действие над билетами:\n" +
            " 1. Покупка билета\n" +
            " 2. Возврат билета\n" +
            " 3. Редактировать билет\n" +
            " 4. Искать билет\n" +
            " 5. Вывести список билетов\n" +
            " 6. Выход";

    private final String buyTicketMessage = "Покупка билета";
    private final String editTicketMessage = "Редкатирование билета";
    private final String findTicketMessage = "Поиск билетов";
    private final String returnTicketMessage = "Возврат билета";
    private final String inputFlightIdMessage = "Введите ID номер рейса:";
    private final String inputPassengerIdMessage = "Введите ID номер пассажира:";
    private final String inputTicketIdMessage = "Введите ID номер билета:";
    private final String inputSeatTypeMessage = "Выберете тип салона:\n" +
            "1. " + SeatType.BUSINESS + "\n" +
            "2. " + SeatType.ECONOMY;

    private final String inputPriceMessage = "Введите цену билета:";
    private final String inputFirstNameMessage = "Введите имя пассажира:";
    private final String inputLastNameMessage = "Введите фамилию пассажира:";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");

    TicketView(TicketController ticketController, Scanner sc) {
        this.ticketController = ticketController;
        super.sc = sc;
        super.mainMenuMessage = mainMenuMessage;
    }

    @Override
    void show() {
        boolean isExit = false;
        while (true) {
            System.out.println(Message.LINE.getMessage());
            System.out.println(mainMenuMessage);
            System.out.println(Message.LINE.getMessage());
            String response = sc.next();
            switch (response) {
                case "1":
                    create();
                    break;
                case "2":
                    delete();
                    break;
                case "3":
                    edit();
                    break;
                case "4":
                    findTickets();
                    break;
                case "5":
                    print();
                    break;
                case "6":
                    isExit = true;
                    break;
                default:
                    System.out.println(Message.ERROR_INPUT.getMessage());
                    break;
            }

            if (isExit)
                break;
        }
    }

    void findTickets() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(findTicketMessage);

        System.out.println(Message.INPUT_DATE.getMessage());
        sc.nextLine();
        String date = sc.nextLine();

        System.out.println(Message.INPUT_DEPARTURE.getMessage());
        String departure = sc.nextLine();

        System.out.println(Message.INPUT_ARRIVAL.getMessage());
        String arrival = sc.nextLine();

        System.out.println(inputSeatTypeMessage);
        String seatTypeNumber = sc.nextLine();

        System.out.println(inputFirstNameMessage);
        String firstName = sc.nextLine();

        System.out.println(inputLastNameMessage);
        String lastName = sc.nextLine();

        System.out.println(Message.LINE.getMessage());

        List<Ticket> tickets;
        try {
            Date parsedDate = null;
            SeatType seatType = null;

            if (!date.isEmpty()) {
                parsedDate = dateFormatSimple.parse(date);
            }

            if (!seatTypeNumber.isEmpty()) {
                seatType = SeatType.fromId(Integer.parseInt(seatTypeNumber));
            }

            tickets = ticketController.findTicket(parsedDate, departure, arrival, seatType, firstName, lastName);
            printListTickets(tickets);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        System.out.println(Message.LINE.getMessage());
    }

    private void printListTickets(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println(Message.EMPTY_LIST.getMessage());
            return;
        }

        System.out.println(printHeaderTickets);
        tickets.sort(Comparator.comparing(BaseEntity::getId));
        for (Ticket t : tickets
        ) {
            Flight f = t.getFlight();
            Aircraft aircraft = f.getAircraft();
            Route route = f.getRoute();
            SeatType seatType = t.getSeatType();

            System.out.println(t.getId() + "; " + aircraft.getName() + "; " + dateFormat.format(route.getDepartureDate()) + "; " + route.getDeparture().getName() + "; "
                    + dateFormat.format(route.getArrivalDate()) + "; " + route.getArrival().getName() + "; " + seatType + "; " + t.getPassenger().getFirstName() + "; " + t.getPassenger().getLastName() + "; "
                    + t.getPrice());
        }
    }

    @Override
    void create() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(buyTicketMessage);

        System.out.println(inputFlightIdMessage);
        long flightId = sc.nextLong();

        System.out.println(inputPassengerIdMessage);
        long passengerId = sc.nextLong();

        System.out.println(inputSeatTypeMessage);
        int seatTypeId = sc.nextInt();
        SeatType seatType = SeatType.fromId(seatTypeId);

        System.out.println(inputPriceMessage);
        double price = sc.nextDouble();

        try {
            ticketController.buyTicket(flightId, passengerId, seatType, price);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    @Override
    void edit() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(editTicketMessage);

        System.out.println(inputTicketIdMessage);
        long ticketId = sc.nextLong();

        System.out.println(inputFlightIdMessage);
        long flightId = sc.nextLong();

        System.out.println(inputPassengerIdMessage);
        long passengerId = sc.nextLong();

        System.out.println(inputSeatTypeMessage);
        int seatTypeId = sc.nextInt();
        SeatType seatType = SeatType.fromId(seatTypeId);

        System.out.println(inputPriceMessage);
        double price = sc.nextDouble();

        try {
            ticketController.update(ticketId, flightId, passengerId, seatType, price);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    @Override
    void delete() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(returnTicketMessage);

        System.out.println(inputTicketIdMessage);
        long ticketId = sc.nextLong();

        try {
            ticketController.returnTicket(ticketId);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    @Override
    void print() {
        System.out.println(Message.LINE.getMessage());
        List<Ticket> tickets;

        try {
            tickets = ticketController.getAll();
            printListTickets(tickets);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
            return;
        }

        System.out.println(Message.LINE.getMessage());
    }
}
