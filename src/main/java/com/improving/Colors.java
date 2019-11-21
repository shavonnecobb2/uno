package com.improving;

public enum Colors {
    Red ("red", 1),
    Green ("green", 1),
    Blue ("blue", 1),
    Yellow ("yellow", 1),
    Wild ("wild", 1);

    private final String colorName;
    private final int value;

    Colors(String colorName, int value) {
        this.colorName = colorName;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getColorName() {
        return colorName;
    }
}
