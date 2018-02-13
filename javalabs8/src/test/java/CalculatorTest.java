import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CalculatorTest {

    private Calculator calculator = new DummyCalculator();

    @Test
    public void testSum() {
        assertThat(calculator.sum(1, 2)).isEqualTo(3);
        assertThat(calculator.sum(-1, -2)).isEqualTo(-3);
    }
}
