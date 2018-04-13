import java.util.ArrayList;
import java.util.List;

public class Message {

    private List<Block> packages;

    public Message(int numberOfBlocks) {
        this.packages = new ArrayList<>(numberOfBlocks);
        for (int i = 0; i < numberOfBlocks; i++) {
            packages.add(new Block(i));
        }
    }

    public List<Block> getPackages() {
        return packages;
    }
}
