import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {

    public static <T> void swap(List<T> list, int firstIndex, int secondIndex) {
        T temp = list.get(firstIndex);
        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, temp);
    }

    public static <T extends Comparable> T findMin(List<T> list, int startInclusively, int endExclusively) {
        if (list.size() < endExclusively) {
            throw new IllegalArgumentException("Index of the end is bigger than list size");
        }
        return list.stream()
                .limit(endExclusively - 1)
                .skip(startInclusively)
                .sorted()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Resulting list is empty, check your boundaries"));
    }

    public static <T> List<T> substractCollections(Iterator<T> firstIterator, Iterator<T> secondIterator) {
        List<T> result = new ArrayList<>();
        while (firstIterator.hasNext()) {
            result.add(firstIterator.next());
        }
        while (secondIterator.hasNext()) {
            result.remove(secondIterator.next());
        }
        return result;
    }
}
