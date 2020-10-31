package main.util;

public enum Seasons {
    WINTER(.12),
    SPRING(.15),
    SUMMER(.08),
    FALL(.08);

    private final double rainRate;

    Seasons(double rainRate) {
        this.rainRate = rainRate;
    }

    public double getRainRate() {
        return rainRate;
    }
}
