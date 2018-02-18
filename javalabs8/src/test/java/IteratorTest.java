import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class IteratorTest {
    private List<Integer> firstListInt;
    private List<Integer> secondListInt;

    private List<String> firstListString;
    private List<String> secondListString;

    @Before
    public void setUpLists() {
        firstListInt = new ArrayList<>(Arrays.asList(1, 3, 5, 2, 9));
        secondListInt = new ArrayList<>(Arrays.asList(7, 5, 2, 4));

        firstListString = new ArrayList<>(Arrays.asList("Unique", "Duplicate", "Another Unique"));
        secondListString = new ArrayList<>(Arrays.asList("Duplicate", "Some other"));
    }

    @Test
    public void iteratorIntTest() {
        Collections.sort(firstListInt);
        Collections.sort(secondListInt);

        Iterator<Integer> firstIterator = firstListInt.iterator();
        Iterator<Integer> secondIterator = secondListInt.iterator();

        assertThat(Utils.substractCollections(firstIterator, secondIterator))
                .containsExactly(1, 3, 9);
    }

    @Test
    public void iteratorStringTest() {
        Collections.sort(firstListString);
        Collections.sort(secondListString);

        Iterator<String> firstIterator = firstListString.iterator();
        Iterator<String> secondIterator = secondListString.iterator();

        assertThat(Utils.substractCollections(firstIterator, secondIterator))
                .containsExactly("Another Unique", "Unique");
    }

    @Test
    public void iteratorIntTestUsingStreams() {
        Collections.sort(firstListInt);
        Collections.sort(secondListInt);

        Iterator<Integer> firstIterator = firstListInt.iterator();
        Iterator<Integer> secondIterator = secondListInt.iterator();

        assertThat(Utils.substractCollectionsUsingStreams(firstIterator, secondIterator))
                .containsExactly(1, 3, 9);
    }

    @Test
    public void iteratorStringTestUsingStreams() {
        Collections.sort(firstListString);
        Collections.sort(secondListString);

        Iterator<String> firstIterator = firstListString.iterator();
        Iterator<String> secondIterator = secondListString.iterator();

        assertThat(Utils.substractCollectionsUsingStreams(firstIterator, secondIterator))
                .containsExactly("Another Unique", "Unique");
    }
}
