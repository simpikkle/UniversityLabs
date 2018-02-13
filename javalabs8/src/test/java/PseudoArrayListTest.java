import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PseudoArrayListTest {

    @Test
    public void testRemoval() {
        PseudoArrayList pseudoArrayList = new PseudoArrayList(1, 2, 3, 4);
        pseudoArrayList.deleteAll(number -> number < 3);
        assertThat(pseudoArrayList.getArray()).contains(3, 4);
    }

    @Test
    public void testRemovalAll() {
        PseudoArrayList pseudoArrayList = new PseudoArrayList(1, 2, 3, 4);
        pseudoArrayList.deleteAll(number -> number < 10);
        assertThat(pseudoArrayList.getArray()).isEmpty();
    }
}
