import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DuplicatesTest {

    @Test
    public void findRemoveDuplicatesTest() {
        assertThat(Utils.removeDuplicates("aab")).isEqualTo("b");
        assertThat(Utils.removeDuplicates("aabba")).isEqualTo("a");
        assertThat(Utils.removeDuplicates("fabccbv")).isEqualTo("fav");
        assertThat(Utils.removeDuplicates("abcfcba")).isEqualTo("f");
        assertThat(Utils.removeDuplicates("abccbaf")).isEqualTo("f");
        assertThat(Utils.removeDuplicates("aaaa")).isEqualTo("");
        assertThat(Utils.removeDuplicates("aaa")).isEqualTo("a");
        assertThat(Utils.removeDuplicates("aAa")).isEqualTo("a");
        assertThat(Utils.removeDuplicates("VeryComplicatedPhrase")).isEqualTo("Vyomlitdhse");
    }
}
