package optimization;

import java.util.ArrayList;
import java.util.Iterator;

public class MainCriteria implements Lab {
    private ArrayList<Gazette> items = new ArrayList<>();
    private Float[] defaultFactors = {0.010f, 0.1f, 0.038f, 44000f, 400f, 2500000f, 0.3f, 10f};
    private Float[] uniqueFactors = {0.009f, 0.7f, 0.054f, 8900f, 500f, 300000f, 0.6f, 70f};

    @Override
    public void run() {
        System.out.println("Условия по умолчанию: ");
        initializeDefaultItems();
        runCriteria(defaultFactors);
        System.out.println("Индивидуальный выбор критериев: ");
        initializeIndividualItems();
        runCriteria(uniqueFactors);
    }

    private void runCriteria(Float[] factorsToCheck) {
        for (int j = 0; j < factorsToCheck.length; j++) {
            Iterator<Gazette> iterator = items.iterator();
            while (iterator.hasNext()) {
                Gazette gazette = iterator.next();
                if (gazette.getFactors()[j] < factorsToCheck[j]) {
                    iterator.remove();
                }
            }
        }
        items.forEach(item -> System.out.println(item.name));
    }

    private void initializeDefaultItems() {
        items = new ArrayList<>();
        items.add(new Gazette("Смена", new Float[]{0.008f, 0.100f, 0.500f, 44000f, 500f, 2800000f, 0.3f, 30f}));
        items.add(new Gazette("Час Пик", new Float[]{0.01f, 0.0625f, 0.0125f, 70000f, 700f, 3000000f, 0.8f, 45f}));
        items.add(new Gazette("Невское время", new Float[]{0.010f, 0.1111f, 0.200f, 47000f, 500f, 2550000f, 0.2f, 19f}));
        items.add(new Gazette("Вечерний Пб", new Float[]{0.010f, 0.125f, 0.050f, 49000f, 600f, 2600000f, 0.6f, 20f}));
        items.add(new Gazette("СПб ведомости", new Float[]{0.008f, 0.200f, 0.143f, 45000f, 400f, 2500000f, 0.3f, 13f}));
        items.add(new Gazette("Деловой Пб", new Float[]{0.03f, 0.2500f, 0.167f, 80000f, 600f, 3300000f, 0.1f, 92f}));
        items.add(new Gazette("Реклама - Шанс", new Float[]{0.001f, 0.7500f, 0.038f, 85000f, 600f, 2500000f, 0.9f, 11f}));
    }

    private void initializeIndividualItems() {
        items = new ArrayList<>();
        items.add(new Gazette("Смена", new Float[]{0.012f, 0.200f, 0.400f, 41200f, 560f, 283120f, 0.5f, 350f}));
        items.add(new Gazette("Час Пик", new Float[]{0.34f, 0.02325f, 0.0025f, 7900f, 7230f, 3230000f, 0.8f, 45f}));
        items.add(new Gazette("Невское время", new Float[]{0.0234f, 0.1111f, 0.234f, 423400f, 5450f, 23400f, 0.2f, 19f}));
        items.add(new Gazette("Вечерний Пб", new Float[]{0.01234f, 0.125f, 0.050f, 278930f, 600f, 978400f, 0.6f, 20f}));
        items.add(new Gazette("СПб ведомости", new Float[]{0.4568f, 0.200f, 0.143f, 789f, 400f, 250345f, 0.3f, 13f}));
        items.add(new Gazette("Деловой Пб", new Float[]{0.03f, 0.24560f, 0.167f, 80000f, 600f, 3354345000f, 0.1f, 92f}));
        items.add(new Gazette("Реклама - Шанс", new Float[]{0.345f, 0.73450f, 0.089f, 8503540f, 600f, 9000000f, 0.9f, 90f}));
    }

    private class Gazette {

        private String name;
        private Float[] factors;

        public Gazette(String name, Float[] factors) {
            this.name = name;
            this.factors = factors;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Float[] getFactors() {
            return factors;
        }

        public void setFactors(Float[] factors) {
            this.factors = factors;
        }
    }
}
