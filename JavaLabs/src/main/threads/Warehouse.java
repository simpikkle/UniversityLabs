package main.threads;

public class Warehouse {

    private Integer position = 0;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "\t" + position + "\n";
    }
}
