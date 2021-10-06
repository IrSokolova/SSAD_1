package com.company;

public class Main {
    public static void main(String[] args) {
        AIShop ourAIShop = new AIShop();
        ourAIShop.fillTheListOfProducts();
        ourAIShop.printProducts();

        AIShopData data = new AIShopData(ourAIShop);

        data.programManagement();
    }
}