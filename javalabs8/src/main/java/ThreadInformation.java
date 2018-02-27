import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ThreadInformation {

    private static Pattern LOCK = Pattern.compile("<(.*?)>");

    private String name;
    private State state;
    private String tid;
    private List<String> stackTrace = new ArrayList<>();
    private String lock;
    private String locked;

    public String getLock() {
        return lock;
    }

    public String getLocked() {
        return locked;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " [" + state + "] (" + tid + ")" + stackTraceForPrint();
    }

    private String stackTraceForPrint() {
        StringBuilder stringBuilder = new StringBuilder();
        stackTrace.forEach(line -> stringBuilder.append("\n").append(line));
        return stringBuilder.toString();
    }

    public void addToStackTrace(String line) {
        stackTrace.add(line);

        if (line.contains("waiting to lock")) {
            lock = Utils.findByPattern(LOCK, line);
        } else if (line.contains("locked")) {
            locked = Utils.findByPattern(LOCK, line);
        }
    }

    public enum State {
        NEW,
        RUNNABLE,
        BLOCKED,
        WAITING,
        TIMED_WAITING;

        public static State findByName(String name) {
            return Arrays.stream(values())
                    .filter(value -> name.toLowerCase().startsWith(value.toString().toLowerCase()))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Can't find state: " + name));
        }
    }
}
