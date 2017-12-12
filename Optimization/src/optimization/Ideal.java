package optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ideal implements Lab {

    private static final int COUNT = 3;
    private static List<Item> list = new ArrayList<>();
    private static Float[] weight = new Float[]{4f, 3f, 3f};
    private static Boolean[] adv = new Boolean[COUNT];
    private static Float[] P = new Float[]{1f, 2f, 3f, 4f, 5f};
    private static int count = 0;
    private static int P_ind = 0;

    @Override
    public void run() {
        initializeDefaultList(list);

        for (int k = 0; k < list.size() - 1; k++) {
            Item ideal = makeIdeal(list);
            System.out.print("MKO +\t");
            ideal.printK();

            Item worse = makeWorse(list);
            System.out.print("MKO -\t");
            worse.printK();

            makeD(ideal, worse);

            System.out.println("\n" + "Table D");
            list.forEach(Item::printD);

            makeL(list);
            System.out.println("\n" + "Table L");
            for (Item e : list) {
                e.printL();
            }

            Item[] last = new Item[P.length];
            for (int i = 0; i < P.length; i++) {
                P_ind = i;
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

            System.out.println("После удаления худшего элемента остается");
            if (allLast) {
                list.remove(last[0]);
                k--;
            }

            list.stream().map(Item::toString).forEach(System.out::println);
            System.out.println("_____________________");
        }

    }

    private void makeL(List<Item> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < P.length; j++) {
                Float tmp_L = 0f;
                for (int k = 0; k < COUNT; k++) {
                    tmp_L += (weight[k] * (1 - list.get(i).arrayD[k]));
                }
                list.get(i).arrayL[j] = (float) Math.pow(tmp_L, 1 / P[j]);
            }

        }
    }

    private void makeD(Item ideal, Item worse) {
        for (int i = 0; i < list.size(); i++) {
            Float[] tmp_D = new Float[COUNT];
            for (int j = 0; j < COUNT; j++) {
                if (ideal.arrayK[j] - worse.arrayK[j] != 0)
                    tmp_D[j] = (ideal.arrayK[j] - list.get(i).arrayK[j]) / (ideal.arrayK[j] - worse.arrayK[j]);
                else tmp_D[j] = 0f;
            }
            list.get(i).setArrayD(tmp_D);
        }
        Float[] tmp_D = new Float[COUNT];
        for (int j = 0; j < COUNT; j++) {
            tmp_D[j] = (ideal.arrayK[j] - ideal.arrayK[j]) / (ideal.arrayK[j] - worse.arrayK[j]);
            //System.out.print(tmp_D[j]+" ");
        }
        ideal.setArrayD(tmp_D);
        for (int j = 0; j < COUNT; j++) {
            tmp_D[j] = (ideal.arrayK[j] - worse.arrayK[j]) / (ideal.arrayK[j] - worse.arrayK[j]);
        }
        worse.setArrayD(tmp_D);
    }

    private void initializeDefaultList(List<Item> list) {
        list.add(new Item(new Float[]{5.0f, 1.0f, 50.0f}));
        list.add(new Item(new Float[]{10.0f, 1.5f, 40.0f}));
        list.add(new Item(new Float[]{2.0f, 1.5f, 90.0f}));
        list.add(new Item(new Float[]{1.0f, 1.0f, 100.0f}));
        list.add(new Item(new Float[]{6.0f, 3.0f, 100.0f}));
        list.add(new Item(new Float[]{16.0f, 3.5f, 50.0f}));

        adv = new Boolean[]{false, true, false};
    }

    private void initializeUniqueItems(List<Item> list) {
        list.add(new Item(new Float[]{50.0f, 40.0f, 5.0f}));
        list.add(new Item(new Float[]{40.0f, 30.0f, 6.0f}));
        list.add(new Item(new Float[]{75.0f, 60.0f, 5.0f}));
        list.add(new Item(new Float[]{60.0f, 50.0f, 9.0f}));
        list.add(new Item(new Float[]{80.0f, 80.0f, 7.0f}));
        list.add(new Item(new Float[]{70.0f, 60.0f, 4.0f}));
        list.add(new Item(new Float[]{80.0f, 70.0f, 4.0f}));
        list.add(new Item(new Float[]{65.0f, 60.0f, 8.0f}));

        adv = new Boolean[]{true, false, true};
    }

    private Item makeIdeal(List<Item> list) {
        Float[] tmp = new Float[COUNT];
        for (int i = 0; i < tmp.length; i++) {
            Float max = list.get(0).arrayK[i];
            for (int j = 0; j < list.size(); j++) {
                if (adv[i] && list.get(j).arrayK[i] > max) max = list.get(j).arrayK[i];
                else if (!adv[i] && list.get(j).arrayK[i] < max) max = list.get(j).arrayK[i];
            }
            tmp[i] = max;
        }
        return new Item(tmp);
    }

    private Item makeWorse(List<Item> list) {
        Float[] tmp = new Float[COUNT];
        for (int i = 0; i < tmp.length; i++) {
            Float min = list.get(0).arrayK[i];
            for (int j = 0; j < list.size(); j++) {
                if (adv[i] && list.get(j).arrayK[i] < min) min = list.get(j).arrayK[i];
                else if (!adv[i] && list.get(j).arrayK[i] > min) min = list.get(j).arrayK[i];
            }
            tmp[i] = min;
        }
        return new Item(tmp);
    }

    private static class Item implements Comparable<Item> {
        private int index;
        private Float[] arrayK = new Float[COUNT];
        private Float[] arrayD = new Float[COUNT];
        private Float[] arrayL = new Float[P.length];

        public Item(Float arrayK[]) {
            this.arrayK = arrayK;
            this.index = ++count;
        }

        public void setArrayD(Float[] D) {
            this.arrayD = D;
        }

        public int compareTo(Item element) {
            float diff = arrayL[P_ind] - element.arrayL[P_ind];
            if (diff > 0) {
                return -1;
            } else if (diff < 0) {
                return 1;
            } else {
                return 0;
            }
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
