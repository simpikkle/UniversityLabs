package main.products;

public interface Present extends Product {
    default String isPresent() {
        return "Present!";
    }
}
