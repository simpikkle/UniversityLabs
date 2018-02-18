import org.junit.Ignore;
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

    @Test
    public void countWordsInText() {
        TextReader reader = new TextReader("text.txt");
        Map<String, Long> map = reader.getStatistics(1, 1);
        map.forEach((key, value) -> System.out.println("[" + key + "]: " + value));
        assertThat(map).containsKeys("сказала", "говорил", "Михайловна");
        assertThat(map).doesNotContainKeys("и", "в", "к");
    }

    @Test
    public void countMinLengthWordsInText() {
        TextReader reader = new TextReader("text.txt");
        Map<String, Long> map = reader.getStatistics(1, 1);
        map.forEach((key, value) -> System.out.println("[" + key + "]: " + value));
        assertThat(map).containsKeys("и", "в", "к");
    }

    @Test
    @Ignore
    public void countAdjectivesInText() {
        TextReader reader = new TextReader("text.txt");
        Map<String, Long> map = reader.countAdjectives();
        map.forEach((key, value) -> System.out.println("[" + key + "]: " + value));
        assertThat(map).containsKeys("французские", "молодые", "последний");
        assertThat(map).doesNotContainKeys("главнокомандующему", "известие", "Михайловной");
    }
}
