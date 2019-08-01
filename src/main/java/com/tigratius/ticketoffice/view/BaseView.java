package com.tigratius.ticketoffice.view;


import com.tigratius.ticketoffice.model.Message;

import java.util.Scanner;

abstract class BaseView {

    String mainMenuMessage;
    Scanner sc;

    abstract void create();

    abstract void edit();

    abstract void delete();

    abstract void print();

    void show(){
        boolean isExit = false;
        while (true) {
            print();
            System.out.println(Message.LINE.getMessage());
            System.out.println(mainMenuMessage);
            System.out.println(Message.LINE.getMessage());
            String response = sc.next();
            switch (response) {
                case "1":
                    create();
                    break;
                case "2":
                    edit();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    print();
                    break;
                case "5":
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
}
