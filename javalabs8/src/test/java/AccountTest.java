import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    private long startTime;

    @Before
    public void initialize() {
        startTime = System.nanoTime();
    }

    @Test
    public void testDepositAndWithdrawForAtomicAccount() {
        Account account = new AtomicAccount();
        account.deposit(10);
        account.withdraw(5);
        assertThat(account.getBalance()).isEqualTo(5);
    }

    @Test
    public void testDepositAndWithdrawForSynchronizedAccount() {
        Account account = new SynchronizedAccount();
        account.deposit(10);
        account.withdraw(5);
        assertThat(account.getBalance()).isEqualTo(5d);
    }

    @Test
    public void testAtomicAccountInThreads() {
        int numberOfRepeats = 10000;
        int sum = 10;
        int initialSum = sum * numberOfRepeats;

        Account firstAccount = new AtomicAccount();
        Account secondAccount = new AtomicAccount();
        firstAccount.deposit(initialSum);

        Runnable runnable = () -> {
            firstAccount.withdraw(sum);
            secondAccount.deposit(sum);
        };

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < numberOfRepeats; i++) {
            service.execute(runnable);
        }
        service.shutdown();
        assertThat(firstAccount.getBalance()).isEqualTo(0);
        assertThat(secondAccount.getBalance()).isEqualTo(initialSum);
    }

    @Test
    public void testSynchronizedAccountInThreads() {
        int numberOfRepeats = 10000;
        double sum = 10;
        double initialSum = sum * numberOfRepeats;

        Account firstAccount = new SynchronizedAccount();
        Account secondAccount = new SynchronizedAccount();
        firstAccount.deposit(initialSum);

        Runnable runnable = () -> {
            firstAccount.withdraw(sum);
            secondAccount.deposit(sum);
        };

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < numberOfRepeats; i++) {
            service.execute(runnable);
        }
        service.shutdown();
        assertThat(firstAccount.getBalance()).isEqualTo(0d);
        assertThat(secondAccount.getBalance()).isEqualTo(initialSum);
    }

    @After
    public void countTime() {
        long endTime = System.nanoTime();
        System.out.println("Test took "
                + TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS)
                + " ms");
    }
}
