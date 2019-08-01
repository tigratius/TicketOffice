package com.tigratius.ticketoffice.view;

import com.tigratius.ticketoffice.controller.PassengerController;
import com.tigratius.ticketoffice.model.BaseEntity;
import com.tigratius.ticketoffice.model.Message;
import com.tigratius.ticketoffice.model.Passenger;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PassengerView extends BaseView {

    private PassengerController passengerController;

    private final String mainMenuMessage = "Выберете действие над пассажирами:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список пассажиров\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список пассажиров:\n" +
            "ID; FIRST_NAME; LAST_NAME; BIRTH_DATE";

    private final String createMenuMessage = "Создание пассажира.\n";

    private final String editMenuMessage = "Редактирование пассажира.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление пассажира\n" +
            Message.ID.getMessage();

    private final String inputFirstNameMessage = "Введите имя:";
    private final String inputLastNameMessage = "Введите фамилию:";
    private final String inputBirthDateMessage = "Введите дату рождения в формате yyyy-MM-dd:";

    PassengerView(PassengerController passengerController, Scanner sc) {
        this.passengerController = passengerController;
        super.sc = sc;
        super.mainMenuMessage = mainMenuMessage;
    }

    @Override
    void create() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(createMenuMessage);
        System.out.println(inputFirstNameMessage);
        String firstName = sc.next();
        System.out.println(inputLastNameMessage);
        String lastName = sc.next();
        System.out.println(inputBirthDateMessage);
        String date = sc.next();
        try {
            passengerController.create(firstName, lastName, Date.valueOf(date));
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
        System.out.println(inputFirstNameMessage);
        String firstName = sc.next();
        System.out.println(inputLastNameMessage);
        String lastName = sc.next();
        System.out.println(inputBirthDateMessage);
        String date = sc.next();
        try {
            passengerController.update(id, firstName, lastName, Date.valueOf(date));
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
            passengerController.delete(id);
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
        List<Passenger> passengers;

        try {
            passengers = passengerController.getAll();
            if (passengers.isEmpty()) {
                System.out.println(Message.EMPTY_LIST.getMessage());
                return;
            }

            System.out.println(printMenuMessage);

            passengers.sort(Comparator.comparing(BaseEntity::getId));
            for (Passenger p : passengers
            ) {
                System.out.println(p.getId() + "; " + p.getFirstName() + "; " + p.getLastName() + "; " + p.getBirthDate());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }
}
