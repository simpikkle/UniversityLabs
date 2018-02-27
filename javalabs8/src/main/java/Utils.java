import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public static <T> List<T> substractCollectionsUsingStreams(Iterator<T> firstIterator, Iterator<T> secondIterator) {
        Iterable<T> firstIterable = () -> firstIterator;
        Stream<T> firstStream = StreamSupport.stream(firstIterable.spliterator(), false);

        Iterable<T> secondIterable = () -> secondIterator;
        List<T> second = StreamSupport.stream(secondIterable.spliterator(), false)
                .collect(Collectors.toList());

        return firstStream
                .filter(firstItem -> !second.contains(firstItem))
                .collect(Collectors.toList());
    }

    /**
     * N*log(N)
     */
    public static String removeDuplicates(String string) {
        Map<Character, Integer> letterIndex = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < stringBuilder.length(); i++) {
            Character currentChar = Character.toLowerCase(stringBuilder.charAt(i));
            if (letterIndex.get(currentChar) == null) {
                letterIndex.put(currentChar, i);
            } else {
                stringBuilder.deleteCharAt(letterIndex.get(currentChar));
                stringBuilder.deleteCharAt(i - 1);
                letterIndex = new HashMap<>();
                i = -1;
            }
        }
        return stringBuilder.toString();
    }

    public static Map<String, Long> countWordsInList(List<String> words) {
        return new TreeMap<>(words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }

    public static Map<Character, Long> countLettersInText(String text) {
        return text.chars()
                .mapToObj(character -> (char) character)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new));
    }

    public static List<String> grep(String filename, String textToFind) {
        String text = new TextReader(filename).getText();
        return grep(Arrays.asList(text.split("\n")), textToFind);
    }

    public static List<String> grep(List<String> stringList, String textToFind) {
        List<String> modifiableList = new ArrayList<>(stringList);
        modifiableList.removeIf(line -> !line.contains(textToFind));
        return modifiableList;
    }
}
