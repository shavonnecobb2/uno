package com.improving;

import java.util.List;
import java.util.Optional;

public interface IGame {
    public void playCard(Card card, Optional<Colors> declaredColor);

    public boolean isPlayable(Card card);

    public Card draw();

    public List<IPlayerInfo> getPlayerInfo();

    public IPlayer getNextPlayer();

    public IPlayer getPreviousPlayer();

    public IPlayer getNextNextPlayer();


}

