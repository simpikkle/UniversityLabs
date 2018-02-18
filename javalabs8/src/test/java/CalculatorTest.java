import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CalculatorTest {

    private Calculator calculator = new DummyCalculator();

    @Test
    public void testSum() {
        assertThat(calculator.sum(1, 2)).isEqualTo(3);
        assertThat(calculator.sum(-1, -2)).isEqualTo(-3);
        assertThat(calculator.sum(0, -2)).isEqualTo(-2);
    }

    @Test
    public void testSumMax() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            calculator.sum(Integer.MAX_VALUE, 1);
        }).withMessageContaining("Augend or addend exceeds the limits");
    }

    @Test
    public void testSumMin() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            calculator.sum(Integer.MIN_VALUE, -1);
        }).withMessageContaining("Augend or addend exceeds the limits");
    }
}
