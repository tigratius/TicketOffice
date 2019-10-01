package com.tigratius.ticketoffice.view;

import com.tigratius.ticketoffice.controller.RouteController;
import com.tigratius.ticketoffice.model.BaseEntity;
import com.tigratius.ticketoffice.model.Message;
import com.tigratius.ticketoffice.model.Route;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class RouteView extends BaseView {

    private RouteController routeController;

    private final String mainMenuMessage = "Выберете действие над маршрутами:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список маршрутов\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список маршрутов:\n" +
            "ID; DEPARTURE_CITY; DEPARTURE_DATE; ARRIVAL_CITY; ARRIVAL_DATE";

    private final String createMenuMessage = "Создание маршрута.";

    private final String editMenuMessage = "Редактирование маршрута.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление маршрута\n" +
            Message.ID.getMessage();

    private final String inputDepartureCityIdMessage = "Введите ID номер города вылета:";
    private final String inputDepartureDateMessage = "Введите дату вылета в формате yyyy-MM-dd_HH:mm :";
    private final String inputArrivalCityIdMessage = "Введите ID номер города прилета:";
    private final String inputArrivalDateMessage = "Введите дату прилета в формате yyyy-MM-dd_HH:mm :";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");

    RouteView(RouteController routeController, Scanner sc) {
        this.routeController = routeController;
        super.sc = sc;
        super.mainMenuMessage = mainMenuMessage;
    }

    @Override
    void create() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(createMenuMessage);
        System.out.println(inputDepartureCityIdMessage);
        Long departureCityId = sc.nextLong();
        System.out.println(inputDepartureDateMessage);
        String departureDate = sc.next();
        System.out.println(inputArrivalCityIdMessage);
        Long arrivalCityId = sc.nextLong();
        System.out.println(inputArrivalDateMessage);
        String arrivalDate = sc.next();

        try {
            routeController.create(departureCityId, dateFormat.parse(departureDate), arrivalCityId, dateFormat.parse(arrivalDate));
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
        System.out.println(editMenuMessage);
        Long id = sc.nextLong();
        System.out.println(inputDepartureCityIdMessage);
        Long departureCityId = sc.nextLong();
        System.out.println(inputDepartureDateMessage);
        String departureDate = sc.next();
        System.out.println(inputArrivalCityIdMessage);
        Long arrivalCityId = sc.nextLong();
        System.out.println(inputArrivalDateMessage);
        String arrivalDate = sc.next();

        try {
            routeController.update(id, departureCityId, dateFormat.parse(departureDate), arrivalCityId, dateFormat.parse(arrivalDate));
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
        System.out.println(deleteMenuMessage);
        Long id = sc.nextLong();
        try {
            routeController.delete(id);
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
        List<Route> routes;

        try {
            routes = routeController.getAll();
            if (routes.isEmpty()) {
                System.out.println(Message.EMPTY_LIST.getMessage());
                return;
            }

            System.out.println(printMenuMessage);

            routes.sort(Comparator.comparing(BaseEntity::getId));
            for (Route r : routes
            ) {
                System.out.println(r.getId() + "; " + r.getDeparture().getName() + "; " +
                        r.getDepartureDate() + "; " + r.getArrival().getName() + "; " + r.getArrivalDate());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }
}
