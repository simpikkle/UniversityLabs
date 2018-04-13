import java.util.*;

public class Main {

    private static int numberOfBlocksInMessage = 4;
    private static int numberOfMessage = 100000;

    /**
     * Key - количество переспросов
     * Value - количество раз, когда пришлось переспрашивать key раз
     */
    private static Map<Integer, Integer> repeats = new HashMap<>();

    public static void main(String[] args) {
        List<Message> messages = createListOfMessages(numberOfMessage);
        Channel channel = new Channel();

        channel.sendAndReceive(messages);
        System.out.println("Всего передано: " + channel.getNumberOfBlocksSent());
        int resends = channel.getNumberOfBlocksSent() - numberOfBlocksInMessage * numberOfMessage;
        System.out.println("Переспросов : " + resends);

        messages.stream()
                .map(Message::getPackages)
                .flatMap(Collection::stream)
                .forEach(block -> addNumberOfRepeats(block.getNumberOfRepeats()));

        repeats.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue()));
    }

    private static void addNumberOfRepeats(int resends) {
        int existingResends = 1;
        if (repeats.get(resends) != null) {
            existingResends = repeats.get(resends) + 1;
        }
        repeats.put(resends, existingResends);
    }

    private static List<Message> createListOfMessages(int numberOfMessages) {
        List<Message> messages = new ArrayList<>();
        for (int j = 0; j < numberOfMessages; j++) {
            messages.add(new Message(numberOfBlocksInMessage));
        }
        return messages;
    }
}
