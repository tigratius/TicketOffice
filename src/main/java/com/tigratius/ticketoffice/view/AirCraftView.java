package com.tigratius.ticketoffice.view;

import com.tigratius.ticketoffice.controller.AirCraftController;
import com.tigratius.ticketoffice.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class AirCraftView extends BaseView{

    private AirCraftController airCraftController;

    private final String mainMenuMessage = "Выберете действие над самолетами:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список самолетов\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список самолетов:\n" +
            "ID; NAME; BUSINESS_SEAT_AMOUNT; ECONOMY_SEAT_AMOUNT";

    private final String createMenuMessage = "Создание самолета.\n" +
            Message.NAME.getMessage();

    private final String editMenuMessage = "Редактирование самолета.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление самолета\n" +
            Message.ID.getMessage();

    private final String inputBusSeatAmountMessage = "Введите количество мест в бизнес классе:";
    private final String inputEcoSeatAmountMessage = "Введите количество мест в эконом классе:";

    AirCraftView(AirCraftController airCraftController, Scanner sc) {
        this.airCraftController = airCraftController;
        super.sc = sc;
        super.mainMenuMessage = mainMenuMessage;
    }

    @Override
    void create() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(createMenuMessage);
        String name = sc.next();
        System.out.println(inputBusSeatAmountMessage);
        int busSeatAmount = sc.nextInt();
        System.out.println(inputEcoSeatAmountMessage);
        int ecoSeatAmount = sc.nextInt();
        try {
            airCraftController.create(name, busSeatAmount, ecoSeatAmount);
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
        System.out.println(Message.NAME.getMessage());
        String name = sc.next();
        System.out.println(inputBusSeatAmountMessage);
        int busSeatAmount = sc.nextInt();
        System.out.println(inputEcoSeatAmountMessage);
        int ecoSeatAmount = sc.nextInt();
        try {
            airCraftController.update(id, name, busSeatAmount, ecoSeatAmount);
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
        Long id = sc.nextLong();
        try {
            airCraftController.delete(id);
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
    void print() {
        System.out.println(Message.LINE.getMessage());
        List<Aircraft> aircrafts;

        try {
            aircrafts = airCraftController.getAll();
            if (aircrafts.isEmpty()) {
                System.out.println(Message.EMPTY_LIST.getMessage());
                return;
            }

            System.out.println(printMenuMessage);

            aircrafts.sort(Comparator.comparing(BaseEntity::getId));
            for (Aircraft aircraft : aircrafts
            ) {
                System.out.println(aircraft.getId() + "; " + aircraft.getName() + "; " +
                        aircraft.getNumberSeatsBySeatType(SeatType.BUSINESS) + "; " + aircraft.getNumberSeatsBySeatType(SeatType.ECONOMY));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }
}
