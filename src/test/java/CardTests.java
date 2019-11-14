import com.improving.Card;
import com.improving.Colors;
import com.improving.Faces;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTests {

    @Test
    public void A_Card_Should_Have_A_Face() {
        //Arrange
        Faces face = Faces.Draw_2;

        //Act
        var card = new Card(face, Colors.Blue);

        //Assert
        assertEquals(face, card.getFace());
    }

    @Test
    public void A_Card_Should_Have_A_Color() {
        //Arrange
        Colors color = Colors.Wild;

        //Act
        var card = new Card(Faces.Nine, color);

        //Assert
        assertEquals(color, card.getColor());
    }

    @Test
    public void A_Card_Should_Have_A_Color_And_A_Face() {
        //Arrange
        Colors color = Colors.Wild;
        Faces face = Faces.Five;

        //Act
        var card = new Card(face, color);

        //Assert
        assertEquals(new Card(face, color).toString(), new Card(card.getFace(), card.getColor()).toString());
    }
}
