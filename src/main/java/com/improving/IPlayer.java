package com.improving;

public interface IPlayer extends IPlayerInfo {

    public Card draw(IGame iGame);

    public void takeTurn(IGame iGame);

}

