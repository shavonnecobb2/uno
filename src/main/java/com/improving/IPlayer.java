package com.improving;

import java.util.List;

public interface IPlayer extends IPlayerInfo {

    public Card draw(IGame iGame);

    public void takeTurn(IGame iGame);

    public void newHand(List<Card> cards);
}

