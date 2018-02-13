import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class IteratorTest {

    @Test
    public void iteratorIntTest() {
        List<Integer> firstList = new ArrayList<>(Arrays.asList(1, 3, 5, 2, 9));
        List<Integer> secondList = new ArrayList<>(Arrays.asList(7, 5, 2, 4));

        Collections.sort(firstList);
        Collections.sort(secondList);

        Iterator<Integer> firstIterator = firstList.iterator();
        Iterator<Integer> secondIterator = secondList.iterator();

        assertThat(Utils.substractCollections(firstIterator, secondIterator)).containsExactly(1, 3, 9);
    }

    @Test
    public void iteratorStringTest() {
        List<String> firstList = new ArrayList<>(Arrays.asList("Unique", "Duplicate", "Another Unique"));
        List<String> secondList = new ArrayList<>(Arrays.asList("Duplicate", "Some other"));

        Collections.sort(firstList);
        Collections.sort(secondList);

        Iterator<String> firstIterator = firstList.iterator();
        Iterator<String> secondIterator = secondList.iterator();

        assertThat(Utils.substractCollections(firstIterator, secondIterator)).containsExactly("Another Unique", "Unique");
    }
}
