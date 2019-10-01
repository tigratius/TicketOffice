package com.tigratius.ticketoffice.view;

import com.tigratius.ticketoffice.controller.*;
import com.tigratius.ticketoffice.model.Message;
import com.tigratius.ticketoffice.repository.*;
import com.tigratius.ticketoffice.repository.hibernate.*;
import com.tigratius.ticketoffice.service.*;
import com.tigratius.ticketoffice.util.DbUtil;
import com.tigratius.ticketoffice.repository.jdbc.*;
import com.tigratius.ticketoffice.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.Scanner;

public class ConsoleRunner {

    private Scanner sc = new Scanner(System.in);

    private TicketView ticketView;
    private FlightView flightView;
    private CityView cityView;
    private PassengerView passengerView;
    private RouteView routeView;
    private AirCraftView airCraftView;

    private final String damagedDataMessage = "Данные повреждены!";
    private final String menuMessage = "Выберете действие:\n" +
            "1. Рейсы\n" +
            "2. Билеты\n" +
            "3. Маршруты\n" +
            "4. Самолеты\n" +
            "5. Города\n" +
            "6. Пассажиры\n" +
            "7. Выход";

    private final String headMessage = "*Приложение по продаже билетов*";

    public ConsoleRunner() {
        try {
            //create repo using jdbc
            /*Connection connection = DbUtil.getConnection();
            CityRepository cityRepository = new JavaJDBCCityRepositoryImpl(connection);
            AircraftRepository aircraftRepository = new JavaJDBCAirCraftRepositoryImpl(connection);
            PassengerRepository passengerRepository = new JavaJDBCPassengerRepositoryImpl(connection);
            RouteRepository routeRepository = new JavaJDBCRouteRepositoryImpl(connection, cityRepository);
            FlightRepository flightRepository = new JavaJDBCFlightRepositoryImpl(connection, aircraftRepository, routeRepository);
            TicketRepository ticketRepository = new JavaJDBCTicketRepositoryImpl(connection, flightRepository, passengerRepository);*/

            //create repo using hibernate
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            CityRepository cityRepository = new JavaHibernateCityRepositoryImpl(sessionFactory);
            AircraftRepository aircraftRepository = new JavaHibernateAircraftRepositoryImpl(sessionFactory);
            PassengerRepository passengerRepository = new JavaHibernatePassengerRepositoryImpl(sessionFactory);
            RouteRepository routeRepository = new JavaHibernateRouteRepositoryImpl(sessionFactory);
            FlightRepository flightRepository = new JavaHibernateFlightRepositoryImpl(sessionFactory);
            TicketRepository ticketRepository = new JavaHibernateTicketRepositoryImpl(sessionFactory);

            //create services
            FlightService flightService = new FlightService(flightRepository, aircraftRepository, routeRepository);
            TicketService ticketService = new TicketService(flightRepository, ticketRepository, passengerRepository);
            CityService cityService = new CityService(cityRepository);
            AirCraftService airCraftService = new AirCraftService(aircraftRepository);
            RouteService routeService = new RouteService(routeRepository, cityRepository);
            PassengerService passengerService = new PassengerService(passengerRepository);

            //create controllers
            TicketController ticketController = new TicketController(ticketService);
            FlightController flightController = new FlightController(flightService);
            CityController cityController = new CityController(cityService);
            PassengerController passengerController = new PassengerController(passengerService);
            AirCraftController airCraftController = new AirCraftController(airCraftService);
            RouteController routeController = new RouteController(routeService);

            //create views
            ticketView = new TicketView(ticketController, sc);
            flightView = new FlightView(flightController, sc);
            cityView = new CityView(cityController, sc);
            passengerView = new PassengerView(passengerController, sc);
            airCraftView = new AirCraftView(airCraftController, sc);
            routeView = new RouteView(routeController, sc);

        } catch (Exception ex) {
            System.out.println(damagedDataMessage);
        }
    }

    public void run() {
        boolean isExit = false;
        System.out.println(headMessage);
        do {
            System.out.println(Message.LINE.getMessage());
            System.out.println(menuMessage);
            System.out.println(Message.LINE.getMessage());
            String response = sc.next();
            switch (response) {
                case "1":
                    flightView.show();
                    break;
                case "2":
                    ticketView.show();
                    break;
                case "3":
                    routeView.show();
                    break;
                case "4":
                    airCraftView.show();
                    break;
                case "5":
                    cityView.show();
                    break;
                case "6":
                    passengerView.show();
                    break;
                case "7":
                    isExit = true;
                    break;
                default:
                    System.out.println(Message.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);

        try {
            /*DbUtil.getConnection().close();*/
            HibernateUtil.getSessionFactory().close();
        } catch (/*SQLException |*/ HibernateException e) {
            e.printStackTrace();
        }
    }
}
