import com.sun.istack.internal.NotNull;

public interface Account {

    Number getBalance();

    String getId();

    void withdraw(@NotNull Number sum);

    void deposit(@NotNull Number sum);
}
