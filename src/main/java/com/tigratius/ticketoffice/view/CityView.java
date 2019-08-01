package com.tigratius.ticketoffice.view;

import com.tigratius.ticketoffice.controller.CityController;
import com.tigratius.ticketoffice.model.BaseEntity;
import com.tigratius.ticketoffice.model.City;
import com.tigratius.ticketoffice.model.Message;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class CityView extends BaseView{

    private CityController cityController;

    private final String mainMenuMessage = "Выберете действие над городами:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список городов\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список городов:\n" +
            "ID; NAME";

    private final String createMenuMessage = "Создание города.\n" +
            Message.NAME.getMessage();

    private final String editMenuMessage = "Редактирование города.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление города\n" +
            Message.ID.getMessage();

    CityView(CityController cityController, Scanner sc) {
        this.cityController = cityController;
        super.sc = sc;
        super.mainMenuMessage = mainMenuMessage;
    }

    @Override
    void create() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(createMenuMessage);
        String name = sc.next();
        try {
            cityController.create(name);
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
        try {
            cityController.update(id, name);
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
            cityController.delete(id);
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
        List<City> cities;

        try {
            cities = cityController.getAll();
            if (cities.isEmpty()) {
                System.out.println(Message.EMPTY_LIST.getMessage());
                return;
            }

            System.out.println(printMenuMessage);

            cities.sort(Comparator.comparing(BaseEntity::getId));
            for (City c : cities
            ) {
                System.out.println(c.getId() + "; " + c.getName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }
}
