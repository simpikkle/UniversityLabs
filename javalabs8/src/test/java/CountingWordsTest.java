import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CountingWordsTest {

    @Test
    public void countWords() {
        List<String> words = new ArrayList<>(Arrays.asList("one", "two", "zzz", "one", "two", "three", "two"));
        Map<String, Long> map = Utils.countWordsInList(words);
        map.forEach((k, v) -> System.out.println("[" + k + "]: " + v));
        assertThat(map.get("one")).isEqualTo(2);
        assertThat(map.get("two")).isEqualTo(3);
        assertThat(map.get("three")).isEqualTo(1);
    }
}
