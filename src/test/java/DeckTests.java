import com.improving.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTests {

    @Test
    public void A_Deck_Should_Have_112_Cards() {
        //Arrange
        Deck deck = new Deck();

        //Act


        //Assert
        assertEquals(112, deck.getDeckSize());
    }

//    @Test
//    public void A_Deck_Should_Draw_First_Card_From_DrawPile() {
//        //Arrange
//        Deck deck = new Deck();
//
//
//        //Act
//        var firstCard = deck.draw().toString();
//
//        //Assert
//        assertEquals(firstCard, deck.getDrawPile().get(0).toString());
//    }

}
