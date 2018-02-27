import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ThreadAnalyzer {
    private static Pattern NAME = Pattern.compile("\"(.*?)\"");
    private static Pattern TID = Pattern.compile("tid=(.*?) nid");
    private static Pattern STATE = Pattern.compile("State: ([A-Z]+)");

    public static void main(String[] args) {
//        Uncomment to read from console using
//        jstack 6282 | java -jar out/artifacts/thread_jar/thread.jar
//        List<String> inputList = Utils.readFromSystemInput();
        List<String> inputList = new TextReader("jstack.txt").getLinesList();
        if (inputList.isEmpty()) {
            System.out.println("Can't read information from Jstack");
        }
        List<ThreadInformation> threadList = new ArrayList<>();
        ThreadInformation threadInformation = new ThreadInformation();
        boolean stackTrace = false;
        for (String line : inputList) {
            if (line.startsWith("Found")) {
                break;
            } else if (line.startsWith("\"")) {
                stackTrace = false;
                if (threadInformation.getName() != null && threadInformation.getState() != null) {
                    threadList.add(threadInformation);
                }
                threadInformation = new ThreadInformation();
                threadInformation.setName(Utils.findByPattern(NAME, line));
                threadInformation.setTid(Utils.findByPattern(TID, line));
            } else if (line.contains("State")) {
                threadInformation.setState(ThreadInformation.State.findByName(Utils.findByPattern(STATE, line)));
                stackTrace = threadInformation.getState() == ThreadInformation.State.BLOCKED;
            } else if (stackTrace && !line.isEmpty()) {
                threadInformation.addToStackTrace(line);
            }
        }
        print(threadList);
    }

    private static void print(List<ThreadInformation> threadList) {
        System.out.println("All threads: ");
        threadList.forEach(System.out::println);
        System.out.println("\n\nGrouping by state: ");
        threadList.stream()
                .collect(Collectors.groupingBy(thread -> thread.getState()))
                .forEach((state, threads) -> {
                    System.out.println("\n" + state + ": ");
                    threads.forEach(thread -> {
                        System.out.println(thread);
                        if (thread.getLock() != null) {
                            System.out.println("Blocked by: ");
                            System.out.println(findThreadWithLock(threads, thread.getLock()));
                            System.out.println();
                        }
                    });
                });
    }

    private static ThreadInformation findThreadWithLock(List<ThreadInformation> threads, String lock) {
        for (ThreadInformation threadInformation: threads) {
            if (threadInformation.getLocked().equals(lock)) {
                return threadInformation;
            }
        }
        return null;
    }


}
