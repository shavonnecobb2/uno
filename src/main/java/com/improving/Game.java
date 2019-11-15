package com.improving;

import com.improving.exceptions.EndGameException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Game {
    public Deck deck = new Deck();
    private List<Player> players = new ArrayList<>();
    private Player player;
    private int numPlayers;
    private int currentPlayer = 0;
    public int turnDirection = 1;
    public int turnEngine = 0;
    private boolean gameInProgress = true;

    public List<Player> getPlayers() {
        return players;
    }


    public void play() {
        Player playerOne = new Player("Shavonne");
        Player playerTwo = new Player("James");
        Player playerThree = new Player("Little Kitty");
        Player playerFour = new Player("Smokie");
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        players.add(playerFour);
        numPlayers = players.size();

        System.out.println(players);
        var firstCard = deck.draw();
        deck.getDiscardPile().add(firstCard);
        System.out.println("Top Card on Discard Pile: " + firstCard.toString());

        for (var player : players) {
            for (int i = 0; i < 7; i++) {
                player.getHandCards().add(deck.draw());
            }
        }

        if (hasAction(topCard())) {
            executeCardActionForFirstPerson(topCard(), this);
        }

        while (gameInProgress) {
            try {
                if (turnEngine < 0) {
                    turnEngine = turnEngine + numPlayers;
                }
                currentPlayer = turnEngine % numPlayers;
                this.player = players.get(currentPlayer);
                System.out.println("Current player is : " + player + " and they have " + player.handSize() + " cards!");

                player.takeTurn(this);

                if (player.handSize() == 0) {
                    throw new EndGameException();
                }

            } catch (EndGameException EndGameEx) {
                for (var player : players) {
                    if (player.handSize() == 0) {
                        System.out.println("Congratulations " + player + "! You just won!");
                        gameInProgress = false;
                    }
                }
            }
            turnEngine = turnEngine + turnDirection;
        }

    }

    public boolean isLegalCard(Card playerCard) {
        if (topCard().getColor().equals(playerCard.getColor())
                || topCard().getFace().equals(playerCard.getFace())
                || playerCard.getColor().equals(Colors.Wild)
                || topCard().getColor().equals(Colors.Wild)) {
            return true;
        }
        return false;
    }

    public Boolean hasAction(Card card) {
        if (card.getFace().toString().equalsIgnoreCase("Draw_4")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("Draw_2")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("Skip")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("Reverse")) {
            return true;
        } else {
            return false;
        }

    }

    public void executeCardAction(Card card, Game game) {
        if (topCard().getFace().equals(Faces.Draw_2)) {
            if (turnEngine <= 0) {
                turnEngine = turnEngine + numPlayers;
            }
            var nextPlayerIndex = (turnEngine + turnDirection) % numPlayers;
            players.get(nextPlayerIndex);
            players.get(nextPlayerIndex).draw(game);
            players.get(nextPlayerIndex).draw(game);
            //this skips the next player
            turnEngine = turnEngine + turnDirection;
            System.out.println("--Player " + players.get(nextPlayerIndex).toString() + " had to DRAW 2 and skip their turn - OUCH--");
            System.out.println("");
        } else if (topCard().getFace().equals(Faces.Draw_4)) {
            if (turnEngine <= 0) {
                turnEngine = turnEngine + numPlayers;
            }
            var nextPlayerIndex = (turnEngine + turnDirection) % numPlayers;
            players.get(nextPlayerIndex);
            players.get(nextPlayerIndex).draw(game);
            players.get(nextPlayerIndex).draw(game);
            players.get(nextPlayerIndex).draw(game);
            players.get(nextPlayerIndex).draw(game);
            //this skips the next player
            turnEngine = turnEngine + turnDirection;
            System.out.println("--Player " + players.get(nextPlayerIndex).toString() + " had to DRAW 4 and skip their turn - OUCH--");
            System.out.println("");

        } else if (topCard().getFace().equals(Faces.Skip)) {
            if (turnEngine <= 0) {
                turnEngine = turnEngine + numPlayers;
            }
            var nextPlayerIndex = (turnEngine + turnDirection) % numPlayers;
            players.get(nextPlayerIndex);
            System.out.println("--Player " + players.get(nextPlayerIndex).toString() + " was skipped--");
            System.out.println("");
            turnEngine = turnEngine + turnDirection;
        } else if (topCard().getFace().equals(Faces.Reverse)) {
            turnDirection = turnDirection * (-1);
            System.out.println("--TURN ORDER WAS REVERSED--");
            System.out.println("");
        }

    }

    public void executeCardActionForFirstPerson(Card card, Game game) {
        if (topCard().getFace().equals(Faces.Draw_2)) {
            this.player = players.get(0);
            player.draw(game);
            player.draw(game);
            //this skips the next player
            turnEngine = turnEngine + turnDirection;
            System.out.println("--Player " + player.toString() + " had to DRAW 2 and skip their turn - OUCH--");
            System.out.println("");
        } else if (topCard().getFace().equals(Faces.Draw_4)) {
            this.player = players.get(0);
            player.draw(game);
            player.draw(game);
            player.draw(game);
            player.draw(game);
            //this skips the next player
            turnEngine = turnEngine + turnDirection;
            System.out.println("--Player " + player.toString() + " had to DRAW 4 and skip their turn - OUCH--");
            System.out.println("");
        } else if (topCard().getFace().equals(Faces.Skip)) {
            this.player = players.get(0);
            System.out.println("--Player " + player.toString() + " was skipped--");
            System.out.println("");
            turnEngine = turnEngine + turnDirection;
        } else if (topCard().getFace().equals(Faces.Reverse)) {
            turnDirection = turnDirection * (-1);
            System.out.println("--TURN ORDER WAS REVERSED--");
            System.out.println("");
        }
    }

    public void playCard(Card card, Colors declaredColor) {
        deck.getDiscardPile().add(card);
        if (hasAction(card)) {
            executeCardAction(card, this);
        }
    }

    public Card topCard() {
        return deck.getDiscardPile().get(deck.getDiscardPile().size() - 1);
    }
}

