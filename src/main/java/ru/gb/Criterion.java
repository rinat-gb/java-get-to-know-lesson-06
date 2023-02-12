package ru.gb;

import java.util.Scanner;

public class Criterion<T> extends CriterionBase {
    private T values;
    private Scanner scanner;

    public Criterion(Scanner scanner, String name, T values, boolean exactMatch) {
        super(name, exactMatch);

        this.values = values;
        this.scanner = scanner;
    }

    public T getValues() {
        return values;
    }

    @Override
    public void doChoice() {
        StringBuilder sb = new StringBuilder("Выберите ");

        if (getExactMatch()) {
            sb.append("одно ");
        } else {
            sb.append("минимальное ");
        }

        sb.append("значение из списка:");

        int maxNum;

        if (this.values instanceof int[]) {
            int[] v = (int[]) this.values;
            maxNum = v.length;

            for (int i = 0; i < v.length; i++) {
                sb.append('\n');
                sb.append(i + 1);
                sb.append(" - ");
                sb.append(v[i]);
                sb.append("Mb");
            }
        } else if (this.values instanceof String[]) {
            String[] v = (String[]) this.values;
            maxNum = v.length;

            for (int i = 0; i < v.length; i++) {
                sb.append('\n');
                sb.append(i + 1);
                sb.append(" - ");
                sb.append(v[i]);
            }
        } else {
            Notebook.Color[] v = (Notebook.Color[]) this.values;
            maxNum = v.length;

            for (int i = 0; i < v.length; i++) {
                sb.append('\n');
                sb.append(i + 1);
                sb.append(" - ");
                sb.append(v[i].getColorName());
            }
        }
        System.out.println(sb);

        while (true) {
            System.out.print("\nВаш выбор: ");

            char ch = Character.toLowerCase(this.scanner.next().charAt(0));
            System.out.println();

            if (Character.isDigit(ch)) {
                int num = Character.getNumericValue(ch);
                if (num >= 1 && num <= maxNum) {
                    setChoosenIdx(num - 1);
                    setChosen(true);
                    break;
                }
            }
            // какой-то некорректный ввод
            System.out.printf("Некорректный ввод, можно вводить цифры от 1 до %d, повторите ввод\n\n",
                    maxNum);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", ");

        if (!getChosen()) {
            sb.append("не выбрано");
        } else {
            if (getExactMatch()) {
                sb.append("только ");
            } else {
                sb.append("минимум ");
            }

            if (this.values instanceof int[]) {
                int[] v = (int[]) this.values;
                sb.append(v[getChoosenIdx()]);
                sb.append("Mb");
            } else if (this.values instanceof String[]) {
                String[] v = (String[]) this.values;
                sb.append(v[getChoosenIdx()]);
            } else {
                Notebook.Color[] v = (Notebook.Color[]) this.values;
                sb.append(v[getChoosenIdx()].getColorName());
            }
        }
        return sb.toString();
    }
}
