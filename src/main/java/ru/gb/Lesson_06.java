/*
Урок 6. Хранение и обработка данных ч3: множество коллекций Set

Подумать над структурой класса Ноутбук для магазина техники - выделить поля и методы.
Реализовать в java. Создать множество ноутбуков.

Написать метод, который будет запрашивать у пользователя критерий (или критерии)
фильтрации и выведет ноутбуки, отвечающие фильтру. Критерии фильтрации можно хранить в Map.
Например: “Введите цифру, соответствующую необходимому критерию:
1 - ОЗУ
2 - Объем ЖД
3 - Операционная система
4 - Цвет

Далее нужно запросить минимальные значения для указанных критериев - сохранить параметры
фильтрации можно также в Map. Отфильтровать ноутбуки из первоначального множества
и вывести проходящие по условиям.
*/
package ru.gb;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Lesson_06 {
    // все возможные критерии выбора
    private enum AllCriterions {
        RAM_CRITERION,
        HDD_CRITERION,
        OS_CRITERION,
        COLOR_CRITERION,
        LAST_CRITERION
    }

    // количество случайных оригинальных ноутбуков которые мы создаём
    private static final int NUM_OF_NOTEBOOKS = 16;

    // используется для приёма пользовательского ввода
    private Scanner scanner = new Scanner(System.in);

    // HashSet используемый для хранения сгенерированных ноутбуков
    private HashSet<Notebook> nbSet = new HashSet<>();

    // Для хранения классов для выбора критерии отбора ноутбука используем TreeMap.
    // Используется именно TreeMap чтобы критерии были отсортированы по ключу, в
    // качестве которого используется AllCriterions.
    private TreeMap<AllCriterions, CriterionBase> criterionMap = new TreeMap<>();

    /**
     * Основная функция урока №6
     */
    public void run() {
        // заполняем TreeMap критериями для сравнения
        criterionMap.put(AllCriterions.RAM_CRITERION,
                new Criterion<int[]>(scanner, "ОЗУ", Notebook.getRampossible(), false));
        criterionMap.put(AllCriterions.HDD_CRITERION,
                new Criterion<int[]>(scanner, "Объем ЖД", Notebook.getHddPossible(), false));
        criterionMap.put(AllCriterions.OS_CRITERION,
                new Criterion<String[]>(scanner, "Операционная система", Notebook.getOsPossible(), true));
        criterionMap.put(AllCriterions.COLOR_CRITERION,
                new Criterion<Notebook.Color[]>(scanner, "Цвет", Notebook.getColorsPossible(), true));

        // Случайным образом создаём NUM_OF_NOTEBOOKS разных ноубуков.
        // Если аналогичный ноутбук уже есть, идём и пытаемся создать новый
        // Если ноутбук получился оригинальный, то помещаем его во множество ноубуков
        System.out.printf("Создаём множество из %d ноутбуков...", NUM_OF_NOTEBOOKS);

        for (int i = 0;;) {
            Notebook nb = Notebook.buildNotebook();

            if (nbSet.contains(nb)) {
                continue;
            }

            nbSet.add(nb);

            if (++i == NUM_OF_NOTEBOOKS) {
                break;
            }
        }
        System.out.println('\n');

        // основной цикл урока №6
        while (true) {
            char ch = displayMainMenu();

            switch (ch) {
                case '1':
                    displayNotebooks();
                    break;
                case '2':
                    chooseCriterions();
                    break;
                case '3':
                    displayChoice();
                    break;
                case 'q':
                    return;
            }
        }
    }

    private char displayMainMenu() {
        while (true) {
            System.out.print("Что вы хотите сделать?\n\n" +
                    "1 - вывести список имеющихся ноутбуков\n" +
                    "2 - выбрать критерии выбора ноутбука\n" +
                    "3 - показать ноутбуки по выбранным ранее критериям\n" +
                    "Q - завершить программу\n\n" +
                    "Ваш выбор: ");

            char ch = Character.toLowerCase(this.scanner.next().charAt(0));
            System.out.println();

            if (ch == '1' || ch == '2' || ch == '3' || ch == 'q') {
                return ch;
            } else {
                System.out.println("Некорректный ввод, можно вводить '1', '2', '3' или 'Q', повторите ввод\n");
            }
        }
    }

    private void displayNotebooks() {
        System.out.println("Имeющиеся ноутбуки:");
        System.out.println("-------------------");

        int i = 1;
        for (Notebook nb : nbSet) {
            System.out.printf("%02d: %s\n", i++, nb);
        }
        System.out.println();
    }

    private void displayCriterions() {
        System.out.println("Возможные критерии выбора:");
        System.out.println("--------------------------");

        for (AllCriterions criterionValue : AllCriterions.values()) {
            if (criterionValue == AllCriterions.LAST_CRITERION) {
                break;
            }
            CriterionBase criterionBase = criterionMap.get(criterionValue);
            System.out.printf("%d - %s\n", criterionValue.ordinal() + 1, criterionBase);
        }
        System.out.println();
    }

    private void makeChoise(int criteriumNum) {
        for (AllCriterions criterionValue : AllCriterions.values()) {
            if (criteriumNum == criterionValue.ordinal()) {
                criterionMap.get(criterionValue).doChoice();
                return;
            }
        }
    }

    private void chooseCriterions() {
        while (true) {
            displayCriterions();

            System.out.print("Выберите критерий по которому хотите выбрать ноутбук\n" +
                    "или нажмите 'Q' для возврата в предыдущее меню\n\n" +
                    "Ваш выбор: ");

            char ch = Character.toLowerCase(this.scanner.next().charAt(0));
            System.out.println();

            // если выбран выход то просто завершаем функцию
            if (ch == 'q') {
                return;
            }
            // если нажата цифра то проверяем допустимость выбора критерия
            if (Character.isDigit(ch)) {
                int criterionNum = Character.getNumericValue(ch);
                if (criterionNum >= 1 && criterionNum <= AllCriterions.LAST_CRITERION.ordinal()) {
                    makeChoise(--criterionNum);
                    continue;
                }
            }
            // какой-то некорректный ввод
            System.out.printf("Некорректный ввод, можно вводить цифры от 1 до %d или 'Q', повторите ввод\n\n",
                    AllCriterions.LAST_CRITERION.ordinal());
        }
    }

    private void displayChoice() {
        for (CriterionBase cb : criterionMap.values()) {
            if (!cb.getChosen()) {
                System.out.println("Не все критерии поиска заданы, не знаю что выбирать.");
                return;
            }
        }
        HashSet<Notebook> matchedNotebooks = new HashSet<>();

        for (Notebook nb : nbSet) {
            boolean matched = true;

            for (Map.Entry<AllCriterions, CriterionBase> entry : criterionMap.entrySet()) {
                CriterionBase cb = entry.getValue();

                switch (entry.getKey()) {
                    case RAM_CRITERION:
                        @SuppressWarnings("unchecked")
                        Criterion<int[]> criterionRam = (Criterion<int[]>) cb;
                        matched = nb.getRamSize() >= criterionRam.getValues()[criterionRam.getChoosenIdx()];
                        break;

                    case HDD_CRITERION:
                        @SuppressWarnings("unchecked")
                        Criterion<int[]> criterionHdd = (Criterion<int[]>) cb;
                        matched = nb.getHddSize() >= criterionHdd.getValues()[criterionHdd.getChoosenIdx()];
                        break;

                    case OS_CRITERION:
                        @SuppressWarnings("unchecked")
                        Criterion<String[]> criterionOs = (Criterion<String[]>) cb;
                        matched = nb.getOsName().equals(criterionOs.getValues()[criterionOs.getChoosenIdx()]);
                        break;

                    case COLOR_CRITERION:
                        @SuppressWarnings("unchecked")
                        Criterion<Notebook.Color[]> criterionColor = (Criterion<Notebook.Color[]>) cb;
                        matched = nb.getColor().equals(criterionColor.getValues()[criterionColor.getChoosenIdx()]);
                        break;

                    default:
                        break;
                }
                if (!matched) {
                    break;
                }
            }
            if (matched) {
                matchedNotebooks.add(nb);
            }
        }

        if (matchedNotebooks.isEmpty()) {
            System.out.println("Не найдено ни одного ноутбука соответствующего критериям поиска");
        } else {
            System.out.println("Найдены следующие ноутбуки, соответствующие критериям поиска:\n");

            int i = 1;
            for (Notebook nb : matchedNotebooks) {
                System.out.printf("%d - %s\n", i, nb);
                i++;
            }
        }
        System.out.println();
    }
}
