package com.improving;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class Game implements IGame {
    public Deck deck = new Deck();
    private List<IPlayer> players = new ArrayList<>();
    private IPlayer player;
    private TopCard topCard = new TopCard();
    private int numPlayers;
    private int currentPlayer = 0;
    public int turnDirection = 1;
    public int turnEngine = 0;
    private boolean gameInProgress = true;

    public void play() {
        List<Card> startingHand = getStartingHand(deck);
        IPlayer shavonne = new ShavonnePlayer(startingHand);
        IPlayer dummy = new DumbPlayer(startingHand);
        players.add(shavonne);
        players.add(dummy);
        numPlayers = players.size();

        arrangeStartingDeck(deck);
        System.out.println("Top Card on Discard Pile: " + topCard.toString());

        System.out.println(players);

        if (hasAction(topCard.getCard())) {
            executeCardActionForFirstPerson(topCard.getCard(), this);
        }

        while (gameInProgress) {
            numPlayers = players.size();
            if (turnEngine < 0) {
                turnEngine = turnEngine + numPlayers;
            }
            currentPlayer = turnEngine % numPlayers;
            this.player = players.get(currentPlayer);
            System.out.println("Current player is : " + player + " and they have " + player.handSize() + " cards!");

            player.takeTurn(this);

                if (player.handSize() == 0) {
                    System.out.println("Congratulations " + player + "! You just won!");
                    gameInProgress = false;

            }
            turnEngine = turnEngine + turnDirection;

        }
    }

    @Override
    public Card draw() {
        return deck.draw();
    }

    private void arrangeStartingDeck(Deck deck) {
        this.deck = deck;
        deck.shuffle(deck.getDrawPile());
        Card firstCard = deck.draw();
        setTopCard(firstCard, firstCard.getColor());
        deck.getDiscardPile().add(topCard.getCard());
    }

    private List<Card> getStartingHand(Deck deck) {
        this.deck = deck;
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            hand.add(this.draw());
        }
        return hand;
    }

    private void setTopCard(Card card, Colors declaredColor) {
        topCard.setCard(card);

        if (declaredColor.toString().equalsIgnoreCase("Wild")) {
            Random random = new Random();
            int number = random.nextInt(100000) % 4;
            if (number == 0) {
                declaredColor = Colors.Red;
            } else if (number == 1) {
                declaredColor = Colors.Green;
            } else if (number == 2) {
                declaredColor = Colors.Blue;
            } else if (number == 3) {
                declaredColor = Colors.Yellow;
            }
        }
        topCard.setDeclaredColor(declaredColor);
    }

    @Override
    public boolean isPlayable(Card playerCard) {
        if (topCard.getCard().getColor().equals(playerCard.getColor())
                || topCard.getCard().getFace().equals(playerCard.getFace())
                || playerCard.getColor().equals(Colors.Wild)
                || topCard.getCard().getColor().equals(Colors.Wild)) {
            return true;
        }
        return false;
    }

    private Boolean hasAction(Card card) {
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

    private void executeCardAction(Card card, Game game) {
        if (topCard.getCard().getFace().equals(Faces.Draw_2)) {
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
        } else if (topCard.getCard().getFace().equals(Faces.Draw_4)) {
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
        } else if (topCard.getCard().getFace().equals(Faces.Skip)) {
            if (turnEngine <= 0) {
                turnEngine = turnEngine + numPlayers;
            }
            var nextPlayerIndex = (turnEngine + turnDirection) % numPlayers;
            players.get(nextPlayerIndex);
            System.out.println("--Player " + players.get(nextPlayerIndex).toString() + " was skipped--");
            System.out.println("");
            turnEngine = turnEngine + turnDirection;
        } else if (topCard.getCard().getFace().equals(Faces.Reverse)) {
            turnDirection = turnDirection * (-1);
            System.out.println("--TURN ORDER WAS REVERSED--");
            System.out.println("");
        }

    }

    private void executeCardActionForFirstPerson(Card card, Game game) {
        if (topCard.getCard().getFace().equals(Faces.Draw_2)) {
            this.player = players.get(0);
            player.draw(game);
            player.draw(game);
            //this skips the next player
            turnEngine = turnEngine + turnDirection;
            System.out.println("--Player " + player.toString() + " had to DRAW 2 and skip their turn - OUCH--");
            System.out.println("");
        } else if (topCard.getCard().getFace().equals(Faces.Draw_4)) {
            this.player = players.get(0);
            player.draw(game);
            player.draw(game);
            player.draw(game);
            player.draw(game);
            //this skips the next player
            turnEngine = turnEngine + turnDirection;
            System.out.println("--Player " + player.toString() + " had to DRAW 4 and skip their turn - OUCH--");
            System.out.println("");
        } else if (topCard.getCard().getFace().equals(Faces.Skip)) {
            this.player = players.get(0);
            System.out.println("--Player " + player.toString() + " was skipped--");
            System.out.println("");
            turnEngine = turnEngine + turnDirection;
        } else if (topCard.getCard().getFace().equals(Faces.Reverse)) {
            turnDirection = turnDirection * (-1);
            if (players.size() == 2) {
                turnEngine = turnEngine + turnDirection;
            }
            System.out.println("--TURN ORDER WAS REVERSED--");
            System.out.println("");
        }
    }

    @Override
    public void playCard(Card card, Optional<Colors> declaredColor) {
        deck.getDiscardPile().add(card);
        if (declaredColor.isPresent() == false) {
            if (card.getColor().ordinal() != 5) {
                topCard.setDeclaredColor(card.getColor());
            } else {
                topCard.setDeclaredColor(forcePickValidDeclaredColor());
                topCard.setCard(card);
            }
        } else if (declaredColor.isPresent()) {
            if (isValidDeclaredColor(declaredColor) == false) {
                declaredColor = Optional.ofNullable(forcePickValidDeclaredColor());
            }
            topCard.setCard(card);
            topCard.setDeclaredColor(declaredColor.orElseThrow());
        }
        if (hasAction(card)) {
            executeCardAction(card, this);
        }
    }

    private boolean isValidDeclaredColor(Optional<Colors> declaredColor) {
        boolean isValid = false;
        Colors[] validColor = {Colors.Red, Colors.Green, Colors.Yellow, Colors.Blue};
        for (Colors color : validColor) {
            if (declaredColor.get().ordinal() == color.ordinal()) {
                isValid = true;
            }
        }
        return isValid;
    }

    private Colors forcePickValidDeclaredColor() {
        List<Colors> randomColors = new ArrayList<>();
        randomColors.add(Colors.Blue);
        randomColors.add(Colors.Red);
        randomColors.add(Colors.Green);
        randomColors.add(Colors.Yellow);
        Collections.shuffle(randomColors);
        System.out.println("Invalid color declaration - random color chosen instead.");
        return randomColors.get(0);
    }

    @Override
    public List<IPlayerInfo> getPlayerInfo() {
        List<IPlayerInfo> playerInfo = new ArrayList<>();

        for (IPlayer player : players) {
            playerInfo.add(player);
        }
        return playerInfo;
    }

    @Override
    public IPlayer getNextPlayer() {
        if (turnEngine <= 0) {
            turnEngine = turnEngine + numPlayers;
        }
        var nextPlayer = (turnEngine + turnDirection) % numPlayers;
        return players.get(nextPlayer);
    }

    @Override
    public IPlayer getPreviousPlayer() {
        if (turnEngine <= 0) {
            turnEngine = turnEngine + numPlayers;
        }
        var previousPlayer = (turnEngine + turnDirection - 1) % numPlayers;
        return players.get(previousPlayer);
    }

    @Override
    public IPlayer getNextNextPlayer() {
        if (turnEngine <= 0) {
            turnEngine = turnEngine + numPlayers;
        }
        var nextNextPlayer = (turnEngine + turnDirection + 1) % numPlayers;
        return players.get(nextNextPlayer);
    }
}

