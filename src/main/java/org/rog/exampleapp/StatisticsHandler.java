package org.rog.exampleapp;

public class StatisticsHandler {
    private boolean fullStatistics = false;
    private int quantity = 0;
    private double min = 0;
    private double max = 0;

    public StatisticsHandler(boolean fullStatistics) {
        this.fullStatistics = fullStatistics;
    }

    public void fillStatistics(double value) {
        ++quantity;
        if (fullStatistics) {
            min = value < min ? value : min;
            max = value > max ? value : max;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMin() {
        return (int) min;
    }

    public double getMin(boolean needDouble) {
        return min;
    }

    public int getMax() {
        return (int) max;
    }

    public double getMax(boolean needDouble) {
        return max;
    }
}
