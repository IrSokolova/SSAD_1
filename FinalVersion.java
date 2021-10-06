package Code;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;

class Account {
    private String login;
    private String password;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Admin extends Account {
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

class User extends Account {
    Scanner in = new Scanner(System.in);

    private List<Product> cart = new ArrayList<>();// корзина

    private Set<String> searchHistory = new HashSet<>();

    public User(String login, String password) {
        super(login, password);
    }

    public void orderAssembly(AIShop aiShop) {
        boolean over = false;

        while (!over) {
            System.out.println("Choose an option: 1-search the product, 2-add the product to the cart " +
                    "3-remove an item from the cart, 4-Show the cart, 5-Clear the cart, 6-finish order assembly");
            int option = Integer.parseInt(in.nextLine());

            switch (option) {
                case 1 -> {
                    System.out.println("Enter keywords of the product, please (id / kitchen / type " +
                            "/ color / print / origin):");
                    String keywordsLine = in.nextLine();;
                    String[] keywords = keywordsLine.split(" ");
                    System.out.println("Thank you!");

                    List<Product> suggestion = aiShop.getItemsByKey(keywords[0]);
                    for (int i = 1; i < keywords.length; i++) {
                        suggestion = aiShop.getItemsByKey(suggestion, keywords[i]);
                    }

                    if (suggestion.size() == 0) {
                        System.out.println("Sorry, there are no products matching your description");
                    } else {
                        System.out.println("Search results:");
                        for (Product product : suggestion) {
                            System.out.println("id: " + product.getId() + "; type: " + product.getType() +
                                    "; price: " + product.getPrice());
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Enter the id of the product, please");
                    int id = Integer.parseInt(in.nextLine());

                    Product product = aiShop.findProductByID(id);
                    if (product != null) {
                        this.addToCart(product);
                    }
                }
                case 3 -> {
                    System.out.println("Enter the id of the product, please:");
                    int id = Integer.parseInt(in.nextLine());

                    this.removeFromCart(id);
                    System.out.println("Thank you!");
                }
                case 4 -> {
                    System.out.println("Your cart:");
                    for (Product product : this.cart) {
                        System.out.println("id: " + product.getId() + "; type: " + product.getType() +
                                "; price: " + product.getPrice());
                    }
                }
                case 5 -> {
                    this.clearCart();
                    System.out.println("The cart is empty now");
                }
                case 6 -> {
                    List<Product> recs = aiShop.getRecommendations(this);
                    System.out.println("You may also like");
                    for (Product product : recs) {
                        System.out.println("id: " + product.getId() + "; type: " + product.getType() +
                                "; price: " + product.getPrice());
                    }

                    System.out.println("Continue shopping? 1-yes, 2-no");
                    int decision = Integer.parseInt(in.nextLine());
                    if (decision == 1) {
                        continue;
                    }

                    System.out.println("Order assembly is finished");
                    aiShop.payment(this);
                    over = true;
                }
                default -> {
                    System.out.println("We have no such option. Try again");
                }
            }
        }
    }

    /**
     * add the product to the card
     * @param product : the product
     */
    public void addToCart(Product product) {
        searchHistory.addAll(product.getKeywords());
        cart.add(product);
    }

    /**
     * find & remove the product from the card
     * @param id : id of the product
     */
    public void removeFromCart(int id) {
        int ind = -1;
        for (int i = 0; i < cart.size(); i++) {
            if (this.cart.get(i).getId() == id) {
                ind = i;
            }
        }

        if (ind != -1) {
            cart.remove(ind);
            System.out.println("The product is removed successfully");
        } else {
            System.out.println("This product does not exist");
        }
    }

    public Set<String> getSearchHistory() {
        return this.searchHistory;
    }

    public double calculateTotalPrice() {
        double sum = 0;

        for (Product product : cart) {
            sum += product.getPrice();
        }

        return sum;
    }

    public void clearCart() {
        cart.clear();
    }

}

class AIShopData{
    private List<Account> admins = new ArrayList<>();
    private List<Account> users = new ArrayList<>();
    AIShop aiShop;
    Scanner scanner = new Scanner(System.in);

    public AIShopData(AIShop aiShop){
        this.aiShop = aiShop;
    }

    public void initialise(){
        admins.add(new Admin("Admin1", "987654"));
        admins.add(new Admin("Admin2", "999999"));
    }

    public void Registration(){
        boolean end = false;
        while(!end) {
            System.out.println("Enter your login");
            String login = scanner.nextLine();
            System.out.println("Enter your password");
            String password = scanner.nextLine();
            if (!containsLogin(users, login)) {
                users.add(new User(login, password));
                System.out.println("You are registered");
                end = true;
            } else {
                System.out.println("This login already exists");
            }
        }
    }

    public void LogIn(){

        boolean end = false;
        int option;
        while(!end) {
            System.out.println("Choose type of your account: 1-Admin, 2-Customer");
            option = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter your login");
            String login = scanner.nextLine();
            System.out.println("Enter your password");
            String password = scanner.nextLine();
            switch (option){
                case 1-> {
                    if (!findLogin(admins, login).equals(null)) {
                        if (!findLogin(admins, login).getPassword().equals(password)) {
                            ((User) findLogin(admins, login)).orderAssembly(aiShop);
                            end = true;
                        } else System.out.println("Your password is incorrect");
                    } else {
                        System.out.println("This login does not exist");
                    }
                }

                case 2 -> {
                    if (!findLogin(users, login).equals(null)) {
                        if (!findLogin(users, login).getPassword().equals(password)) {
                            ((Admin) findLogin(users, login)).productsManagement(aiShop);
                            end = true;
                        } else System.out.println("Your password is incorrect");
                    } else {
                        System.out.println("This login does not exist");
                    }
                }

                default -> {
                    System.out.println("We do not have such option");
                }
            }


        }
    }

    public boolean containsLogin(List<Account> accounts, String login){
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getLogin().equals(login))
                return true;
        }
        return false;
    }

    public Account findLogin(List<Account> accounts, String login){
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getLogin().equals(login))
                return accounts.get(i);
        }
        return null;
    }
}

class Product {
    protected static int idIterator = 0;

    private int id;
    private String type;
    private double price;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    public Product(String type, double price) {
        this.id = idIterator;
        idIterator++;

        this.type = type;
        this.price = price;

        this.keywords.add(type);
        this.keywords.add(String.valueOf(id));
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getPrice() {
        return this.price;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}

class Food extends Product {
    private int id;
    private String type;
    private double price;
    private String kitchen;
    private String origin;
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

class FrenchFood extends Food{
    final String taste = "Gracieux";

    public FrenchFood(String type, double price, String kitchen) {
        super(type, price, kitchen);
        this.setKitchen("French");
    }
}

class ItalianFood extends Food{
    String taste= "Aggraziato";

    public ItalianFood(String type, double price, String kitchen) {
        super(type, price, kitchen);
        this.setKitchen("Italian");
    }
}

class Item extends Product {
    private int id;
    private String type;
    private double price;
    private String color;
    private String print;
    private String origin;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    // Danechka????
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

    @Override
    public int getId() {
        return id;
    }
}

class FrenchItem extends Item{
    final String style = "Quotidien";

    public FrenchItem(String type, double price, String color, String print, String origin) {
        super(type, price, color, print, origin);
        this.setOrigin("France");
    }
}

class ItalianItem extends Item{
    final String style = "Ordinario";

    public ItalianItem(String type, double price, String color, String print, String origin) {
        super(type, price, color, print, origin);
        this.setOrigin("Italy");
    }
}



interface AbstractFactory {
    Item createClothes();

    Food createFood();
}

class ItalianFactory implements AbstractFactory {
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

        return new ItalianItem(type, price, color, print,"Italy");
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

class FrenchFactory implements AbstractFactory {
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

        return new FrenchItem(type,price,color,print,"France");
    }


    @Override
    public Food createFood() {
        System.out.println("Enter type of food");
        String type = scanner.nextLine();

        System.out.println("Enter price of food");
        double price = Double.parseDouble(scanner.next());

        return new FrenchFood(type, price, "French");
    }
}

class AIShop {
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

        this.products.add(new ItalianItem("dress", 6.22, "red", "dinosaurs","Italy"));

        this.products.add(new ItalianFood("croissant", 1.33,"Italian"));

        this.products.add(new FrenchItem("dress", 3.62, "red", "triangles","France"));

        this.products.add(new FrenchFood("frogs' legs", 84.44, "French"));
    }

    public void addProduct(Product product) {
        this.products.add(product);
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
            System.out.println("id: " + product.getId() + "; type: " + product.getType() +
                    "; price: " + product.getPrice());
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
     * @param  productsList : a list of products
     * @param keyword : the keyword
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
     * @param user : the user
     * @return : the list of recommendations
     */
    public List<Product> getRecommendations(User user) {
        Random random = new Random();

        Set<String> searchHistory = user.getSearchHistory();
        int fromIndex = random.nextInt(searchHistory.size() - KEYWORDS_SUBSET_SIZE);
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

public class Main {
    public static void main(String[] args) {
        AIShop ourAIShop = new AIShop();
        ourAIShop.fillTheListOfProducts();
        ourAIShop.printProducts();

        AIShopData data = new AIShopData(ourAIShop);

        data.initialise();
        data.Registration();
        data.LogIn();
    }
}
