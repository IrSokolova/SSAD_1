package com.company;

import java.util.Scanner;

public class Admin extends Account {
    Scanner in = new Scanner(System.in);

    public Admin(String login, String password) {
        super(login, password);
    }

    public void productsManagement(AIShop aiShop) {
        System.out.println("Welcome to the products management!!!!!!!!!!!!!");
        boolean over = false;

        while (!over) {
            System.out.println("Choose an option: 1-add the product, " +
                    "2-remove the product, 3-get products, 4-finish work");
            int option = Integer.parseInt(in.nextLine());

            switch (option) {
                case 1 -> {
                    Product product;

                    System.out.println("Choose a kind of the product: 1-food, 2-item");
                    int kind = Integer.parseInt(in.nextLine());

                    System.out.println("Choose a factory: 1-Italian factory, 2-French factory");
                    int factory = Integer.parseInt(in.nextLine());

                    switch (kind) {
                        case 1:
                            switch (factory) {
                                case 1 -> {
                                    aiShop.setFactory(new ItalianFactory());
                                    product = aiShop.getFactory().createFood();
                                }
                                case 2 -> {
                                    aiShop.setFactory(new FrenchFactory());
                                    product = aiShop.getFactory().createFood();
                                }
                                default -> {
                                    System.out.println("We have no such option. Try again");
                                    continue;
                                }
                            }
                            break;
                        case 2:
                            switch (factory) {
                                case 1 -> {
                                    aiShop.setFactory(new ItalianFactory());
                                    product = aiShop.getFactory().createClothes();
                                }
                                case 2 -> {
                                    aiShop.setFactory(new FrenchFactory());
                                    product = aiShop.getFactory().createClothes();
                                }
                                default -> {
                                    System.out.println("We have no such option. Try again");
                                    continue;
                                }
                            }
                            break;
                        default:
                            System.out.println("We have no such option. Try again");
                            continue;
                    }

                    aiShop.addProduct(product);
                    System.out.println("Thank you!");
                }
                case 2 -> {
                    System.out.println("Enter the ID of the product, please:");
                    int id = Integer.parseInt(in.nextLine());

                    aiShop.removeProduct(id);
                    System.out.println("Thank you!");
                }
                case 3 -> {
                    aiShop.printProducts();
                }
                case 4 -> {
                    System.out.println("Work is finished. Thank you!");
                    over = true;
                }
                default -> {
                    System.out.println("We have no such option. Try again");
                }
            }
        }
    }

}
