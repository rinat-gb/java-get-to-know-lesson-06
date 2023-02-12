package ru.gb;

/**
 * Абстрактный базовый класс для критериев поиска
 */
public abstract class CriterionBase {
    private String name;
    private boolean exactMatch;
    private boolean chosen;
    private int choosenIdx;

    public CriterionBase(String name, boolean exactMatch) {
        this.name = name;
        this.exactMatch = exactMatch;
        this.chosen = false;
    }

    /**
     * Искать строгое совпадение или можно искать минимум
     */
    public boolean getExactMatch() {
        return this.exactMatch;
    }

    /**
     * Выбран ли данный критерий поиска
     */
    public boolean getChosen() {
        return this.chosen;
    }

    public void setChosen(Boolean value) {
        this.chosen = value;
    }

    /**
     * Возвращает индекс в массиве значений данного критерия поиска
     */
    public int getChoosenIdx() {
        return this.choosenIdx;
    }

    public void setChoosenIdx(int value) {
        this.choosenIdx = value;
    }

    /*
     * Функция выбора необходимого значения для критерия
     * 
     * Абстрактная, должна быть переопределена в классе соответстующего критерия
     */
    public abstract void doChoice();

    @Override
    public String toString() {
        return this.name;
    }
}
