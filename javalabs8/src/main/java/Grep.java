import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Please, run
 * cat src/main/resources/grep.txt | java -jar out/artifacts/grep_jar/grep.jar "text"
 * from project directory to see it working
 */
public class Grep {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Please, provide something to look for");
        }
        List<String> inputList = Utils.readFromSystemInput();
        Utils.grep(inputList, args[0]).forEach(System.out::println);
    }
}
