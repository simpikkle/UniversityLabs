import com.sun.istack.internal.NotNull;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicAccount implements Account {

    // Added random id to prevent collisions; not needed in case of mapping to DB with auto id
    // Keeping id in string is questionable - consider using long
    private String id = UUID.randomUUID().toString();

    // Balance should be set to 0 when creating an account
    private AtomicInteger balance = new AtomicInteger(0);

    // "this" is redundant and prohibited in major company java style policies
    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void withdraw(@NotNull Number sum) {
        balance.getAndAdd(-sum.intValue());
    }

    @Override
    public void deposit(@NotNull Number  sum) {
        balance.getAndAdd(sum.intValue());
    }
}
