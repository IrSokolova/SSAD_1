package com.company;

import java.util.Scanner;

public class ItalianFactory implements AbstractFactory {
    Scanner scanner = new Scanner(System.in);


    @Override
    public Item createClothes() {
        System.out.println("Enter type of cloth");
        String type = scanner.nextLine();

        System.out.println("Enter price of cloth");
        double price = Double.parseDouble(scanner.next());

        System.out.println("Enter color of cloth");
        String color = scanner.nextLine();

        System.out.println("Enter print of cloth");
        String print = scanner.nextLine();

        return new ItalianItem(type, price, color, print, "Italy");
    }


    @Override
    public Food createFood() {
        System.out.println("Enter type of food");
        String type = scanner.nextLine();

        System.out.println("Enter price of food");
        double price = Double.parseDouble(scanner.next());

        return new ItalianFood(type, price, "Italian");
    }
}