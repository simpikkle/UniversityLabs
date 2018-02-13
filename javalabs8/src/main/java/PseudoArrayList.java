import java.util.Arrays;
import java.util.function.IntPredicate;

public class PseudoArrayList {

    private int array[];

    public PseudoArrayList(int... numbers) {
        array = numbers;
    }

    public void deleteAll(IntPredicate predicate) {
        array = Arrays.stream(array)
                .filter(predicate.negate())
                .toArray();
    }

    public int[] getArray() {
        return this.array;
    }
}
