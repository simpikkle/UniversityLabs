import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GrepTest {

    @Test
    public void grepMultipleTest() {
        List<String> result = Utils.grep("grep.txt", "text");
        assertThat(result).contains("some text", "another text", "very important and unique text");
    }

    @Test
    public void grepUniqueTest() {
        List<String> result = Utils.grep("grep.txt", "unique");
        assertThat(result).contains("very important and unique text");
    }

    @Test
    public void grepListTest() {
        List<String> input = Arrays.asList("hello", "world", "helloworld");
        List<String> result = Utils.grep(input, "hello");
        assertThat(result).contains("hello", "helloworld");
    }
}
