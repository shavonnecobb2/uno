package com.improving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShavonnePlayer implements IPlayer {
    private String name;
    private List<Card> handCards = new ArrayList<>();

    public ShavonnePlayer(List<Card> handCards) {
        this.handCards = handCards;
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    @Override
    public void takeTurn(IGame iGame) {
        if (handSize() == 1) {
            System.out.println("UNNNOOOO BIIIITTTCHHHHH");
        }

        for (var playerCard : getHandCards()) {
            if (iGame.isPlayable(playerCard)) {
                System.out.println("");
                System.out.println(playerCard + " was just played.");
                System.out.println("--END TURN--");
                System.out.println("~*~*~*~*~*~*~*~*~*~*~");
                if (attackAttack(playerCard, iGame)) {
                    playCard(playerCard, iGame);
                } else playCard(playerCard, iGame);
                return;
            }
        }
        System.out.println("Can't play! Gotta draw!");
        System.out.println("");
        var newCard = draw(iGame);
        System.out.println("[" + newCard.toString() + "] was drawn!");
        if (iGame.isPlayable(newCard)) {
            playCard(newCard, iGame);
            System.out.println("The card was played - HOT DAMN!");
            System.out.println("--END TURN--");
            System.out.println("~*~*~*~*~*~*~*~*~*~*~");
        } else {
            System.out.println("--END TURN--");
            System.out.println("~*~*~*~*~*~*~*~*~*~*~");
        }
    }

    @Override
    public int handSize() {
        return handCards.size();
    }

    @Override
    public Card draw(IGame iGame) {
        var drawnCard = iGame.draw();
        handCards.add(drawnCard);
        return drawnCard;
    }

    private void playCard(Card card, IGame iGame) {
        Colors declaredColor = declareColor(card, iGame);
        handCards.remove(card);
        iGame.playCard(card, Optional.ofNullable(declaredColor));
    }


    private Colors declareColor(Card card, IGame iGame) {
        var declaredColor = card.getColor();
        if (card.getColor().toString().equals("Wild")) {
            List<Colors> randomColors = new ArrayList<>();
            randomColors.add(Colors.Red);
            randomColors.add(Colors.Blue);
            randomColors.add(Colors.Green);
            randomColors.add(Colors.Yellow);
            declaredColor = randomColors.get(0);
            boolean declaredColorinHand = false;
            int numWildColorCardsInHand = 0;

            if (card.getColor().equals(Colors.Wild)) {
                while (declaredColorinHand) {
                    Collections.shuffle(randomColors);
                    for (Card c : handCards) {
                        if (card.getColor().equals(randomColors.get(0))) {
                            declaredColorinHand = true;
                            declaredColor = card.getColor();
                            break;
                        }
                        if (card.getColor().equals(Colors.Wild)) {
                            numWildColorCardsInHand++;
                        }
                        if (numWildColorCardsInHand == handSize()) {
                            Collections.shuffle(randomColors);
                            declaredColorinHand = true;
                            declaredColor = randomColors.get(0);
                        }
                    }
                }
            }
        }
        return declaredColor;
    }

    private boolean attackAttack(Card playerCard, IGame iGame) {
        if (iGame.getNextPlayer().handSize() <= 5) {
            if (playerCard.getFace().getValue() == 20) {
                return true;
            }
        } return false;
    }
}

