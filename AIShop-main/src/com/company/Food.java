package com.company;

import java.util.HashSet;
import java.util.Set;

public class Food extends Product {
    private int id;
    private String type;
    private double price;
    private String kitchen;
    private String taste;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    public Food(String type, double price, String kitchen) {
        super(type, price);

        this.id = idIterator;

        this.type = type;
        this.price = price;
        this.kitchen = kitchen;

        this.keywords.add(type);
        this.keywords.add(kitchen);
        this.keywords.add(String.valueOf(id));
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public String getKitchen() { return kitchen; }

    public String getTaste() { return taste; }

    public void setTaste(String taste) { this.taste = taste; }

    public double getPrice() {
        return this.price;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    @Override
    public int getId() {
        return id;
    }
}
