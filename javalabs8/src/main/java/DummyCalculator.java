public class DummyCalculator implements Calculator {

    @Override
    public int sum(int x, int y) {
        if (x > 0 && y > 0 && x + y < 0 || x < 0 && y < 0 && x + y > 0) {
            throw new IllegalArgumentException("Augend or addend exceeds the limits");
        }
        return x + y;
    }
}
