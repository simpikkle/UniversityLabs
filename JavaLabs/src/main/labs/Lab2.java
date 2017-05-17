package main.labs;

import main.products.*;

import java.util.*;

public class Lab2 {

    private List<Product> products = new ArrayList<>();

    private Map<Integer, ProductCreator> randomMap = new HashMap<>();
    private Random random = new Random();

    public Lab2(int n) {
        fillMap();
        while (n-- > 0) {
            products.add(getRandomProduct());
        }
    }

    public String allProducts() {
        StringBuilder result = new StringBuilder("All products:\n");
        products.forEach(p -> result.append(p.whoAmI()).append("\n"));
        return result.toString();
    }

    public String presentOnly() {
        StringBuilder result = new StringBuilder("Only present:\n");
        products.forEach(p -> {
            if (p instanceof Present) {
                result.append(p.whoAmI())
                        .append("\t")
                        .append(((Present) p).isPresent())
                        .append("\n");
            }
        });
        return result.toString();
    }

    private Product getRandomProduct() {
        int selectedNumber = random.nextInt(4);
        return randomMap.get(selectedNumber).createProduct();
    }

    private void fillMap() {
        randomMap.put(0, Book::new);
        randomMap.put(1, Shoe::new);
        randomMap.put(2, Picture::new);
        randomMap.put(3, Toy::new);
    }

    public static void main(String[] args) {
        Lab2 lab2 = new Lab2(new Random().nextInt(32));
        System.out.println(lab2.allProducts());
        System.out.println(lab2.presentOnly());
    }
}
