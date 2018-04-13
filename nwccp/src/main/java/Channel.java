import java.util.Collection;
import java.util.List;

public class Channel {

    private Probability probability = new Probability();

    private int numberOfBlocksSent;
    private int numberOfBlocksSentCorrectly;

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
        numberOfBlocksSent++;
        if (probability.isSentCorrectlyBasedOnProbability(numberOfBlocksSentCorrectly)) {
            numberOfBlocksSentCorrectly++;
        } else {
            numberOfBlocksSentCorrectly = 0;
            block.addNumberOfRepeats();
            sendAndReceive(block);
        }
    }

    public int getNumberOfBlocksSent() {
        return numberOfBlocksSent;
    }
}
