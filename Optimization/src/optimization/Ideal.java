package optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ideal implements Lab {

    private static final int countOfCriterias = 3;
    private static List<Item> list = new ArrayList<>();
    private static Float[] weight = new Float[]{6f, 6f, 2f};
    private static Boolean[] booleans = new Boolean[countOfCriterias];
    private static Float[] degreeOfConcentration = new Float[]{1f, 2f, 3f, 4f, 5f};
    private static int count = 0;
    private static int concentrationIndex = 0;

    @Override
    public void run() {
        System.out.println("Пример");
        initializeDefaultList();
        findBestItem();

        System.out.println("Собственные значения");
        initializeUniqueItems();
        findBestItem();
    }

    private void findBestItem() {
        for (int k = 0; k < list.size() - 1; k++) {
            Item ideal = makeIdeal(list);
            System.out.print("MKO (идеальный) \t");
            ideal.printK();

            Item worse = makeWorse(list);
            System.out.print("MKO (худший) \t");
            worse.printK();

            makeD(ideal, worse);

            System.out.println("\n" + "Таблица в относительных единицах измерения: ");
            list.forEach(Item::printD);

            countDistanceToIdeal();
            System.out.println("\n" + "Таблица с учетом весов и степени концентрации: ");
            for (Item e : list) {
                e.printL();
            }

            Item[] last = new Item[degreeOfConcentration.length];
            for (int i = 0; i < degreeOfConcentration.length; i++) {
                concentrationIndex = i;
                Collections.sort(list);
                last[i] = list.get(list.size() - 1);
            }

            boolean allLast = false;
            for (int i = 1; i < last.length; i++) {
                if (last[i] == last[i - 1]) {
                    allLast = true;
                } else {
                    allLast = false;
                    break;
                }
            }

            System.out.println("После удаления элементов с наиболее удаленными МКО: ");
            if (allLast) {
                list.remove(last[0]);
                k--;
            }

            list.stream().map(Item::toString).forEach(System.out::println);
            System.out.println("_________________________________");
        }

    }

    private void countDistanceToIdeal() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < degreeOfConcentration.length; j++) {
                Float tempL = 0f;
                for (int k = 0; k < countOfCriterias; k++) {
                    tempL += (weight[k] * (1 - list.get(i).arrayD[k]));
                }
                list.get(i).arrayL[j] = (float) Math.pow(tempL, 1 / degreeOfConcentration[j]);
            }
        }
    }

    private void makeD(Item ideal, Item worse) {
        for (int i = 0; i < list.size(); i++) {
            Float[] tmp_D = new Float[countOfCriterias];
            for (int j = 0; j < countOfCriterias; j++) {
                if (ideal.arrayK[j] - worse.arrayK[j] != 0)
                    tmp_D[j] = (ideal.arrayK[j] - list.get(i).arrayK[j]) / (ideal.arrayK[j] - worse.arrayK[j]);
                else tmp_D[j] = 0f;
            }
            list.get(i).setArrayD(tmp_D);
        }
        Float[] tmp_D = new Float[countOfCriterias];
        for (int j = 0; j < countOfCriterias; j++) {
            tmp_D[j] = (ideal.arrayK[j] - ideal.arrayK[j]) / (ideal.arrayK[j] - worse.arrayK[j]);
        }
        ideal.setArrayD(tmp_D);
        for (int j = 0; j < countOfCriterias; j++) {
            tmp_D[j] = (ideal.arrayK[j] - worse.arrayK[j]) / (ideal.arrayK[j] - worse.arrayK[j]);
        }
        worse.setArrayD(tmp_D);
    }

    private void initializeDefaultList() {
        list = new ArrayList<>();
        count = 0;
        concentrationIndex = 0;
        list.add(new Item(new Float[]{5.0f, 1.0f, 50.0f}));
        list.add(new Item(new Float[]{10.0f, 1.5f, 40.0f}));
        list.add(new Item(new Float[]{2.0f, 1.5f, 90.0f}));
        list.add(new Item(new Float[]{1.0f, 1.0f, 100.0f}));
        list.add(new Item(new Float[]{6.0f, 3.0f, 100.0f}));
        list.add(new Item(new Float[]{16.0f, 3.5f, 50.0f}));

        booleans = new Boolean[]{true, true, false};
        weight = new Float[]{6f, 6f, 2f};
    }

    private void initializeUniqueItems() {
        list = new ArrayList<>();
        count = 0;
        concentrationIndex = 0;
        list.add(new Item(new Float[]{80.0f, 12.0f, 531.0f}));
        list.add(new Item(new Float[]{10.0f, 16.0f, 120.0f}));
        list.add(new Item(new Float[]{75.0f, 32.0f, 500.0f}));
        list.add(new Item(new Float[]{80.0f, 40.0f, 339.0f}));
        list.add(new Item(new Float[]{34.0f, 12.0f, 123.0f}));
        list.add(new Item(new Float[]{72.0f, 17.0f, 101.0f}));
        list.add(new Item(new Float[]{53.0f, 38.0f, 899.0f}));
        list.add(new Item(new Float[]{39.0f, 87.0f, 1230.0f}));

        booleans = new Boolean[]{true, true, false};
        weight = new Float[]{4f, 2f, 3f};
    }

    private Item makeIdeal(List<Item> list) {
        Float[] tmp = new Float[countOfCriterias];
        for (int i = 0; i < tmp.length; i++) {
            Float max = list.get(0).arrayK[i];
            for (int j = 0; j < list.size(); j++) {
                if (booleans[i] && list.get(j).arrayK[i] > max) max = list.get(j).arrayK[i];
                else if (!booleans[i] && list.get(j).arrayK[i] < max) max = list.get(j).arrayK[i];
            }
            tmp[i] = max;
        }
        return new Item(tmp);
    }

    private Item makeWorse(List<Item> list) {
        Float[] tmp = new Float[countOfCriterias];
        for (int i = 0; i < tmp.length; i++) {
            Float min = list.get(0).arrayK[i];
            for (int j = 0; j < list.size(); j++) {
                if (booleans[i] && list.get(j).arrayK[i] < min) min = list.get(j).arrayK[i];
                else if (!booleans[i] && list.get(j).arrayK[i] > min) min = list.get(j).arrayK[i];
            }
            tmp[i] = min;
        }
        return new Item(tmp);
    }

    private static class Item implements Comparable<Item> {
        private int index;
        private Float[] arrayK = new Float[countOfCriterias];
        private Float[] arrayD = new Float[countOfCriterias];
        private Float[] arrayL = new Float[degreeOfConcentration.length];

        public Item(Float arrayK[]) {
            this.arrayK = arrayK;
            this.index = ++count;
        }

        public int compareTo(Item element) {
            float diff = arrayL[concentrationIndex] - element.arrayL[concentrationIndex];
            if (diff > 0) {
                return -1;
            } else if (diff < 0) {
                return 1;
            } else {
                return 0;
            }
        }

        public void setArrayD(Float[] D) {
            this.arrayD = D;
        }

        public String toString() {
            return ("Item " + index);
        }

        public void printL() {
            System.out.print(this.toString() + "\t");
            for (float l : arrayL) {
                System.out.print(Math.rint(l * 100) / 100 + "\t");
            }
            System.out.println();
        }

        public void printK() {
            for (Float k : arrayK) {
                System.out.print(k + "\t");
            }
            System.out.println();
        }

        public void printD() {
            for (Float d : arrayD) {
                System.out.print(Math.rint(d * 100) / 100 + "\t" + "\t");
            }
            System.out.println();
        }

    }
}
