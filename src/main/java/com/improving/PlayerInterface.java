package com.improving;

public interface PlayerInterface {
    public int handSize();

    public Card draw(Game game);

    public void takeTurn(Game game);
}
