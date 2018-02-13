import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MinTest {

    @Test
    public void findMinIntegerTest() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(1,3,5,2,4));
        Integer min = Utils.findMin(integerList, 1, 5);
        assertThat(min).isEqualTo(2);
    }

    @Test
    public void findMinStringTest() {
        List<String> integerList = new ArrayList<>(Arrays.asList("abc", "aab", "bcd", "aaa"));
        String min = Utils.findMin(integerList, 1, 4);
        assertThat(min).isEqualTo("aab");
    }

    @Test
    public void exceptionShouldBeThrownIfIndexOutOfBoundaries() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(1,3));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            Utils.findMin(integerList, 1, 4);
        }).withMessageContaining("Index of the end is bigger than list size");
    }

    @Test
    public void exceptionShouldBeThrownIfListIsEmpty() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(1,3));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            Utils.findMin(integerList, 1, 2);
        }).withMessageContaining("Resulting list is empty, check your boundaries");
    }
}
