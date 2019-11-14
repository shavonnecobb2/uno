package com.improving;

import com.improving.exceptions.EndGameException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Game {
    public Deck deck = new Deck();
    private List<Player> players = new ArrayList<>();



    public void play() {
        Player playerOne = new Player("Shavonne");
        Player playerTwo = new Player("James");
        Player playerThree = new Player("Kimberly");
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        System.out.println(players);
        var firstCard = deck.draw();
        deck.getDiscardPile().add(firstCard);
        System.out.println("Top Card on Discard Pile: " + firstCard.toString());

        for (var player : players) {
            for (int i = 0; i < 7; i++) {
                player.getHandCards().add(deck.draw());
            }
        }

        boolean gameInProgress = true;
        while (gameInProgress) {
            try {
                for (var player : players) {
                    System.out.println("Starting the play: " + player + " has " + player.handSize() + " cards!");

                    if (deck.getDiscardPile().get(deck.getDiscardPile().size() - 1).getFace().equals(Faces.Draw_2)) {
                        System.out.println("UH OH - " + player + " now has to Draw 2!");
                        player.getHandCards().add(deck.draw());
                        player.getHandCards().add(deck.draw());
                    }
                    if (deck.getDiscardPile().get(deck.getDiscardPile().size() - 1).getFace().equals(Faces.Draw_4)) {
                        System.out.println("UH OH - " + player + " now has to Draw 4!");
                        player.getHandCards().add(deck.draw());
                        player.getHandCards().add(deck.draw());
                        player.getHandCards().add(deck.draw());
                        player.getHandCards().add(deck.draw());
                    }

                    if (player.handSize() == 1) {
                        System.out.println("UNNNOOOO");
                    }

                    player.takeTurn(this);

                    if (player.handSize() == 0) {
                        throw new EndGameException();
                    }
                }
            } catch (EndGameException EndGameEx) {
                for (var player : players) {
                    if (player.handSize() == 0) {
                        System.out.println("Congratulations " + player + "! You just won!");
                        gameInProgress = false;
                    }
                }
            }
        }
    }

    public boolean isLegalCard(Card playerCard) {
        if (deck.getDiscardPile().get(deck.getDiscardPile().size() - 1).getColor().equals(playerCard.getColor())
                || deck.getDiscardPile().get(deck.getDiscardPile().size() - 1).getFace().equals(playerCard.getFace())
                || playerCard.getColor().equals(Colors.Wild)
                || deck.getDiscardPile().get(deck.getDiscardPile().size() - 1).getColor().equals(Colors.Wild)) {
            return true;
        }
        return false;
    }
}

