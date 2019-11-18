package com.improving;

public class TopCard {
    private Card card;
    private Colors declaredColor;

    public TopCard() {}

    public TopCard(Card card, Colors declaredColor) {
        this.card = card;
        this.declaredColor = declaredColor;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Colors getDeclaredColor() {
        return declaredColor;
    }

    public void setDeclaredColor(Colors declaredColor) {
        this.declaredColor = declaredColor;
    }

    @Override
    public String toString() {
        return this.getCard().toString() + ". The declared color is " + declaredColor.toString();
    }
}
