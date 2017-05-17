package main.products;

public class Book implements Product {
    @Override
    public String whoAmI() {
        return "I am a book";
    }
}
