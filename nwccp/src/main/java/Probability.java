import java.util.Random;

public class Probability {

    private static final Random RANDOM = new Random();

    private double a = 45;
    private double v = 1.12;

    private double n = 438;

    private double Pk[] = new double[10];

    public Probability() {
        init();
    }

    public boolean isSentCorrectlyBasedOnProbability(int numberOfPackage) {
        double probability = probabilityOfError(numberOfPackage);
        return isSentCorrectlyBasedOnProbability(probability);
    }

    public boolean isSentCorrectlyBasedOnProbability(double probability) {
        double random = RANDOM.nextDouble();
        return random < probability;
    }

    /**
     * @param k number of probability Pn
     * @return Pk
     */
    public double probabilityOfError(int k) {
        return Pk[k];
    }

    private void init() {
        for (int i = 0; i < Pk.length; i++) {
            if (i == 0) {
                Pk[i] = Math.pow(a / (a + n), v - 1);
            } else {
                Pk[i] = (i + v - 2) / i * n / (n + a) * Pk[i - 1];
            }
            System.out.println(String.format("P%d = %1.5f", i, Pk[i]));
        }
    }
}
