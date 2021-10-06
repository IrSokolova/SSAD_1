package com.company;

import java.util.HashSet;
import java.util.Set;

public class Item extends Product {
    private int id;
    private String type;
    private double price;
    private String color;
    private String print;
    private String origin;
    private Set<String> keywords = new HashSet<>();
    private String style;

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    public Item(String type, double price, String color, String print, String origin) {
        super(type, price);

        this.id = idIterator;

        this.type = type;
        this.price = price;
        this.color = color;
        this.print = print;
        this.origin = origin;

        this.keywords.add(type);
        this.keywords.add(color);
        this.keywords.add(print);
        this.keywords.add(origin);
        this.keywords.add(String.valueOf(id));
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getPrice() {
        return this.price;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setStyle(String style) { this.style = style; }

    public String getColor(){ return this.color; }

    public String getPrint(){ return this.print; }

    public String getOrigin() { return origin; }

    public String getStyle() { return style; }

    @Override
    public int getId() {
        return id;
    }
}