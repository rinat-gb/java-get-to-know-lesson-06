package ru.gb;

import java.util.Random;

/**
 * Класс описывающий ноутбук (объём памяти, объём жёсткого диска, операционная
 * система, цвет)
 */
public class Notebook {
    /**
     * Перечисление для возможных цветов ноутбука
     */
    public enum Color {
        Black("Чёрный"),
        White("Белый"),
        Blue("Голубой"),
        Orange("Оранжевый"),
        Red("Красный");

        private String color;

        private Color(String color) {
            this.color = color;
        }

        public String getColorName() {
            return this.color;
        }
    }

    /**
     * Константы для возможных размеров ОЗУ, HDD, OS и цвета
     */
    private static final int[] ramPossible = { 8, 12, 16, 32 };
    private static final int[] hddPossible = { 128, 256, 512, 1024, 2048 };
    private static final String[] osPossible = { "FreeDOS", "Linux", "macOS", "Windows" };
    private static final Color[] colorsPossible = {
            Color.Black,
            Color.White,
            Color.Blue,
            Color.Orange,
            Color.Red };

    /**
     * Выбранный размер ОЗУ, HDD, OS и цвета для данного ноутбука
     */
    private int ramSize;
    private int hddSize;
    private String osName;
    private Color color;

    private Notebook(Integer ramSize, Integer hddSize, String osName, Color color) {
        this.ramSize = ramSize;
        this.hddSize = hddSize;
        this.osName = osName;
        this.color = color;
    }

    public static int[] getRampossible() {
        return Notebook.ramPossible;
    }

    public int getRamSize() {
        return this.ramSize;
    }

    public static int[] getHddPossible() {
        return hddPossible;
    }

    public int getHddSize() {
        return this.hddSize;
    }

    public static String[] getOsPossible() {
        return osPossible;
    }

    public String getOsName() {
        return this.osName;
    }

    public static Color[] getColorsPossible() {
        return colorsPossible;
    }

    public Color getColor() {
        return this.color;
    }

    /**
     * Строит модель ноутбука со случайно выбранными значениями
     * для размера ОЗУ, HDD, OS и цвета
     */
    public static Notebook buildNotebook() {
        Random r = new Random();

        int ramSize = ramPossible[r.nextInt(ramPossible.length)];
        int hddSize = hddPossible[r.nextInt(hddPossible.length)];
        String osName = osPossible[r.nextInt(osPossible.length)];
        Color color = colorsPossible[r.nextInt(colorsPossible.length)];

        return new Notebook(ramSize, hddSize, osName, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Notebook n = (Notebook) o;
        return this.ramSize == n.ramSize &&
                this.hddSize == n.hddSize &&
                this.osName.equals(n.osName) &&
                this.color.equals(n.color);
    }

    @Override
    public int hashCode() {
        return this.ramSize + this.hddSize + this.osName.hashCode() + this.color.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s ноубук, %dMb ОЗУ, жёсткий диск на %dMb, операционная система: %s",
                this.color.getColorName(), this.ramSize, this.hddSize, this.osName);
    }
}
