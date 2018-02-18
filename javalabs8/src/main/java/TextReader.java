import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TextReader {

    private String text;

    public TextReader(String fileName) {
        try {
            text = new String(Files.readAllBytes(Paths.get("src/main/resources/" + fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Long> getStatistics(int minWordLength, int minCount) {
        cleanText();
        Map<String, Long> map = Utils.countWordsInList(getWordsList());
        map = map.entrySet().stream()
                .filter(entry -> entry.getKey().length() >= minWordLength && entry.getValue() >= minCount)
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new));
        return map;
    }

    public Map<Character, Long> getLettersStatistics() {
        cleanText();
        return Utils.countLettersInText(text);
    }

    public Map<String, Long> countAdjectives() {
        return getStatistics(1, 1)
                .entrySet()
                .stream()
                .filter(entry -> isAdjective(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new));
    }

    public String getText() {
        return text;
    }

    @SuppressWarnings("unchecked")
    public List<String> getWordsList() {
        return new ArrayList(Arrays.asList(text.split(" ")));
    }

    public TextReader cleanText() {
        text = removeLineBreaks();
        text = removeExtraSpace();
        text = removeNonLetterCharacters();
        return this;
    }

    private String removeLineBreaks() {
        return text.replaceAll("\\n+|\\r+", " ");
    }

    private String removeExtraSpace() {
        return text.replaceAll("( ){2,}", " ");
    }

    private String removeNonLetterCharacters() {
        return text.replaceAll("[^a-zA-Zа-яА-Я ]", "");
    }

    private boolean isAdjective(String word) {
        if (word.length() < 5) {
            return false;
        }
        List<String> adjectiveEndings = new ArrayList<>(Arrays.asList("ее", "ие", "ые", "ое", "ими", "ыми", "ей",
                "ий", "ый", "ой", "ем", "им", "ым", "ом", "его", "ого", "ему", "ому",
                "их", "ых", "ую", "юю", "ая", "яя", "ою", "ею"));
        return adjectiveEndings.stream().anyMatch(word::endsWith);
    }
}
