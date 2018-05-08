import java.util.ArrayList;
import java.util.List;

public class Message {

    private int name;
    private List<Block> packages;

    public Message(int j, int numberOfBlocks) {
        name = j;
        this.packages = new ArrayList<>(numberOfBlocks);
        for (int i = 0; i < numberOfBlocks; i++) {
            packages.add(new Block(i, name));
        }
    }

    public List<Block> getPackages() {
        return packages;
    }
}
