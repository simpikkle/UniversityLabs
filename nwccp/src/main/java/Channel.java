import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Channel {

    private Probability probability = new Probability();

    private int numberOfBlocksSent;
    private int numberOfBlocksSentCorrectly;

    private Long startTime;

    public void sendAndReceive(Message message) {
        message.getPackages().forEach(this::sendAndReceive);
    }

    public void sendAndReceive(List<Message> messages) {
        messages.stream()
                .map(Message::getPackages)
                .flatMap(Collection::stream)
                .forEach(this::sendAndReceive);
    }

    private void sendAndReceive(Block block) {
        if (startTime == null) {
            startTime = System.nanoTime();
        }
        numberOfBlocksSent++;
        if (probability.isSentCorrectlyBasedOnProbability(numberOfBlocksSentCorrectly)) {
            numberOfBlocksSentCorrectly++;
            System.out.println(time() + String.format("M %d\tP %d\tErrors: %d",
                    block.getMessageName(), block.getName(), 0));
        } else {
            numberOfBlocksSentCorrectly = 0;
            block.addNumberOfRepeats();
            System.out.println(time() + String.format("M %d\tP %d\tErrors: %d",
                    block.getMessageName(), block.getName(), new Random().nextInt(7)+2));
            sendAndReceive(block);
        }
    }

    private String time() {
        long ns = System.nanoTime() - startTime;
        return ns/(1000) + " ms\t\t";
    }

    public int getNumberOfBlocksSent() {
        return numberOfBlocksSent;
    }
}
