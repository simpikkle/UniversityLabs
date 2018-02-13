import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SwapTest {

    @Test
    public void swapIntTest() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(1,2,3));
        Utils.swap(integerList, 1,2);
        assertThat(integerList).containsExactly(1,3,2);
    }

    @Test
    public void swapStringTest() {
        List<String> integerList = new ArrayList<>(Arrays.asList("one","two","three"));
        Utils.swap(integerList, 0,1);
        assertThat(integerList).containsExactly("two","one","three");
    }
}
