package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class AIShop {
    private AbstractFactory factory;
    Scanner in = new Scanner(System.in);

    private final static int KEYWORDS_SUBSET_SIZE = 5;
    private final static int RECOMMENDATIONS_SIZE = 5;

    private List<Product> products = new ArrayList<>();

    public void setFactory(AbstractFactory factory) {
        this.factory = factory;
    }

    public AbstractFactory getFactory() {
        return factory;
    }

    public void fillTheListOfProducts() {

        this.products.add(new ItalianItem("dress", 6.22, "red", "dinosaurs", "Italy"));

        this.products.add(new ItalianFood("croissant", 1.33, "Italian"));

        this.products.add(new FrenchItem("dress", 3.62, "red", "triangles", "France"));

        this.products.add(new FrenchFood("frogs' legs", 84.44, "French"));
    }

    public void addProduct(Product product) {
        this.products.add(product);
        System.out.println("The product has been added");
    }

    public void removeProduct(int id) {
        int ind = -1;
        for (int i = 0; i < products.size(); i++) {
            if (this.products.get(i).getId() == id) {
                ind = i;
            }
        }

        if (ind != -1) {
            products.remove(ind);
        } else {
            System.out.println("This product does not exist");
        }
    }

    public void printProducts() {
        System.out.println("Products:");
        for (Product product : this.products) {
            product.printProduct();
        }
    }

    /**
     * @param keyword : the keyword
     * @return : list of products with this keyword
     */
    public List<Product> getItemsByKey(String keyword) {
        return products.stream().filter(products -> products.isApplicable(keyword)).collect(Collectors.toList());
    }

    /**
     * @param productsList : a list of products
     * @param keyword      : the keyword
     * @return : list of products with this keyword
     */
    public List<Product> getItemsByKey(List<Product> productsList, String keyword) {
        return productsList.stream().filter(products -> products.isApplicable(keyword)).collect(Collectors.toList());
    }

    public Product findProductByID(int id) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getId() == id) {
                return this.products.get(i);
            }
        }

        System.out.println("The product does not exist");
        return null;
    }

    /**
     * gets random keywords from history & puts corresponding products in the list of recommendations
     *
     * @param user : the user
     * @return : the list of recommendations
     */
    public List<Product> getRecommendations(User user) {
        Random random = new Random();

        Set<String> searchHistory = user.getSearchHistory();
        if (searchHistory.isEmpty()) {
            return Collections.emptyList();
        }

        int fromIndex = random.nextInt(Math.max(1, searchHistory.size() - KEYWORDS_SUBSET_SIZE));
        int toIndex = fromIndex + KEYWORDS_SUBSET_SIZE;

        List<String> keywords = new ArrayList<>(searchHistory).subList(fromIndex, toIndex);
        List<Product> recommendations =
                keywords.stream().map(this::getItemsByKey).flatMap(Collection::stream).collect(Collectors.toList());

        if (recommendations.size() > RECOMMENDATIONS_SIZE) {
            return recommendations.subList(0, RECOMMENDATIONS_SIZE);
        } else {
            return recommendations;
        }
    }

    /**
     * Suggests the way of payment & provides the payment
     *
     * @param user : the user
     */
    public void payment(User user) {
        if (user.calculateTotalPrice() > 0) {
            System.out.println("The total price: " + user.calculateTotalPrice());

            boolean over = false;
            while (!over) {
                System.out.println("Choose the way of payment: 1-Card, 2-Cash");
                int wayOfPayment = Integer.parseInt(in.nextLine());

                switch (wayOfPayment) {
                    case 1 -> {
                        System.out.println("Waiting for confirmation ...\nPayment confirmed");
                        over = true;
                    }
                    case 2 -> {
                        System.out.println("You will pay for your order upon receiving");
                        over = true;
                    }
                    default -> {
                        System.out.println("We have no such option. Try again");
                    }
                }
            }
            System.out.println("Thank you for choosing our shop!");
        } else {
            System.out.println("It is ok, you do not need to pay this time:)");
        }
    }
}