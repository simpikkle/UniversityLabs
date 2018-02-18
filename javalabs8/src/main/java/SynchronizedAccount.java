import com.sun.istack.internal.NotNull;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedAccount implements Account {

    private String id = UUID.randomUUID().toString();

    // Balance should be set to 0 when creating an account
    private Double balance = 0d;

    @Override
    public Double getBalance() {
        return balance;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void withdraw(@NotNull Number sum) {
        synchronized (this) {
            balance -= sum.doubleValue();
        }
    }

    @Override
    public void deposit(@NotNull Number sum) {
        synchronized (this) {
            balance += sum.doubleValue();
        }
    }
}
