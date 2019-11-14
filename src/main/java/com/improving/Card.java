package com.improving;

public class Card {
    private final Colors color;
    private final Faces face;

    public Card(Faces face, Colors color) {
        this.face = face;
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    public Faces getFace() {
        return face;
    }

    @Override
    public String toString() {
        return "" + "A " + color.toString() + " " + face.toString();
    }
}
