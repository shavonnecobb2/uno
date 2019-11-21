package com.improving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DumbPlayer2 implements IPlayer {
    private List<Card> hand = new ArrayList<>();
    private final Logger logger;

    public DumbPlayer2(Logger logger, List<Card> handCards) {
        this.hand = handCards;
        this.logger = logger;
    }

    public List<Card> getHandCards() {
        return hand;
    }

    @Override
    public void takeTurn(IGame iGame) {
        if (handSize() == 1) {
            logger.println("UNNNOOOO BIIIITTTCHHHHH");
        }

        for (var playerCard : getHandCards()) {
            if (iGame.isPlayable(playerCard)) {
                logger.println("");
                logger.println(playerCard + " was just played.");
                logger.println("--END TURN--");
                logger.println("~*~*~*~*~*~*~*~*~*~*~");
                playCard(playerCard, iGame);
                return;
            }
        }
        logger.println("Can't play! Gotta draw!");
        logger.println("");
        var newCard = draw(iGame);
        logger.println("[" + newCard.toString() + "] was drawn!");
        if (iGame.isPlayable(newCard)) {
            playCard(newCard, iGame);
            logger.println("The card was played - HOT DAMN!");
            logger.println("--END TURN--");
            logger.println("~*~*~*~*~*~*~*~*~*~*~");
        } else {
            logger.println("--END TURN--");
            logger.println("~*~*~*~*~*~*~*~*~*~*~");
        }
    }

    @Override
    public String getName() {
        return "Dummy 2";
    }

    @Override
    public int handSize() {
        return hand.size();
    }

    @Override
    public void newHand(List<Card> cards) {
        this.hand.clear();
        this.hand.addAll(cards);
    }

    @Override
    public Card draw(IGame iGame) {
        var drawnCard = iGame.draw();
        hand.add(drawnCard);
        return drawnCard;
    }

    private void playCard(Card card, IGame iGame) {
        Colors declaredColor = declareColor(card, iGame);
        hand.remove(card);
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
                    for (Card c : hand) {
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
}
