package com.improving;

public enum Colors {
    Red ("red"),
    Green ("green"),
    Blue ("blue"),
    Yellow ("yellow"),
    Wild ("wild");

    private final String colorName;

    Colors(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }
}
