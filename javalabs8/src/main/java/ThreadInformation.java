import java.util.Arrays;

public class ThreadInformation {
    private String name;
    private State state;
    private String tid;

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
        return name + " [" + state + "] (" + tid + ")";
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
