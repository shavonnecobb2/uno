package com.improving;

import java.util.Optional;

public interface IGame {
    public void playCard(Card card, Optional<Colors> declaredColor);

    public boolean isPlayable(Card card);

    public Card draw();
}

