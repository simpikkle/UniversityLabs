public class Block {

    private final int name;

    private int numberOfRepeats;
    private int messageName;

    public Block(int i, int messageName) {
        this.name = i;
        this.messageName = messageName;
    }

    public int getNumberOfRepeats() {
        return numberOfRepeats;
    }

    public void setNumberOfRepeats(int numberOfRepeats) {
        this.numberOfRepeats = numberOfRepeats;
    }

    public void addNumberOfRepeats() {
        this.numberOfRepeats++;
    }

    public int getName() {
        return name;
    }

    public int getMessageName() {
        return messageName;
    }

    public void setMessageName(int messageName) {
        this.messageName = messageName;
    }
}
