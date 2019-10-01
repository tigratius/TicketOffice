package com.tigratius.ticketoffice.view;

import com.tigratius.ticketoffice.controller.FlightController;
import com.tigratius.ticketoffice.model.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class FlightView extends BaseView{

    private FlightController flightController;

    private final String mainMenuMessage = "Выберете действие над рейсами:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список рейсов\n" +
            " 5. Выход";

    private final String headerFlights = "Доступные рейсы\n" +
            "ID; AIRCRAFT_NAME; DEPARTURE_DATE; DEPARTURE_CITY; ARRIVAL_DATE; ARRIVAL_CITY;";

    private final String createMenuMessage = "Создание рейса.";

    private final String editMenuMessage = "Редактирование рейса.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление рейса\n" +
            Message.ID.getMessage();

    private final String inputAirCraftIdMessage = "Введите ID номер самолета:";
    private final String inputRouteIdMessage = "Введите ID номер маршрута:";

    //    private final String findFlightsMessage = "Поиск рейсов";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//    private SimpleDateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");

    FlightView(FlightController flightController, Scanner sc) {
        this.flightController = flightController;
        super.sc = sc;
        super.mainMenuMessage = mainMenuMessage;
    }

/*    void findFlights(){
        System.out.println(Message.LINE.getMessage());
        System.out.println(findFlightsMessage);
        System.out.println(Message.INPUT_DATE.getMessage());
        sc.nextLine();
        String date = sc.nextLine();

        System.out.println(Message.INPUT_DEPARTURE.getMessage());
        String departure = sc.nextLine();

        System.out.println(Message.INPUT_ARRIVAL.getMessage());
        String arrival = sc.nextLine();

        System.out.println(Message.LINE.getMessage());

        List<Flight> flights;
        try
        {
            Date parsedDate = null;
            if (!date.isEmpty()) {
                parsedDate = dateFormatSimple.parse(date);
            }
            flights = flightController.findFlights(parsedDate, departure, arrival);
            printFlights(flights);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());

        }

        System.out.println(Message.LINE.getMessage());
    }*/

    @Override
    void create() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(createMenuMessage);
        System.out.println(inputAirCraftIdMessage);
        long airCraftId = sc.nextLong();
        System.out.println(inputRouteIdMessage);
        long routeId = sc.nextLong();
        try {
            flightController.create(airCraftId, routeId);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }
        System.out.println(Message.LINE.getMessage());
    }

    @Override
    void edit() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(editMenuMessage);
        Long id = sc.nextLong();
        System.out.println(inputAirCraftIdMessage);
        long airCraftId = sc.nextLong();
        System.out.println(inputRouteIdMessage);
        long routeId = sc.nextLong();
        try {
            flightController.update(id, airCraftId, routeId);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    @Override
    void delete() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(deleteMenuMessage);
        long id = sc.nextLong();
        try {
            flightController.delete(id);
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
        List<Flight> flights;

        try {
            flights = flightController.getAll();
            if (flights.isEmpty())
            {
                System.out.println(Message.EMPTY_LIST.getMessage());
                return;
            }
            System.out.println(headerFlights);

            flights.sort(Comparator.comparing(BaseEntity::getId));
            for (Flight f : flights
            ) {
                Aircraft aircraft = f.getAircraft();
                Route route = f.getRoute();

                System.out.println(f.getId() + "; " + aircraft.getName() + "; " + dateFormat.format(route.getDepartureDate()) + "; " + route.getDeparture().getName() + "; "
                        + dateFormat.format(route.getArrivalDate()) + "; " + route.getArrival().getName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }
}
