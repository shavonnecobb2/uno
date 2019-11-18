package com.improving;

public interface IPlayer {
    public int handSize();

    public Card draw(Game game);

    public void takeTurn(Game game);
}
