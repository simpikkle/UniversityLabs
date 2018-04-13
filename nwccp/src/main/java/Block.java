public class Block {

    private final int name;

    int numberOfRepeats;

    public Block(int i) {
        this.name = i;
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
}
