import org.junit.Test;

import cs5004.marblesolitaire.model.MarbleSolitaireModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * A test class that puts the model under several types of tests.
 */
public class MarbleSolitaireModelImplTest {

  @Test
  public void testConstructors() {
    MarbleSolitaireModelImpl test = new MarbleSolitaireModelImpl();
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", test.getGameState());
    MarbleSolitaireModelImpl testArmThickness = new MarbleSolitaireModelImpl(5);
    assertEquals("        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O", testArmThickness.getGameState());
    MarbleSolitaireModelImpl testAllElements =
            new MarbleSolitaireModelImpl(5, 5,5);
    assertEquals("        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O _ O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O", testAllElements.getGameState());
    MarbleSolitaireModelImpl testEmptyRowAndColumn =
            new MarbleSolitaireModelImpl(2,2);
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O _ O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O", testEmptyRowAndColumn.getGameState());
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testConstructorEmptySpace() {
    MarbleSolitaireModelImpl fail = new MarbleSolitaireModelImpl(0,1); // Invalid pos.
    MarbleSolitaireModelImpl failTwo = new MarbleSolitaireModelImpl(0,-1); // Negative.
    MarbleSolitaireModelImpl failThree = new MarbleSolitaireModelImpl(-1, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorEnterArmThickness() {
    MarbleSolitaireModelImpl fail = new MarbleSolitaireModelImpl(2); // < 3
    MarbleSolitaireModelImpl failEven = new MarbleSolitaireModelImpl(8); // Even
    MarbleSolitaireModelImpl failNegative = new MarbleSolitaireModelImpl(-5); // -
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorEmptyAndThickness() {
    MarbleSolitaireModelImpl fail = new MarbleSolitaireModelImpl(3, 1,0);
    MarbleSolitaireModelImpl failTwo =
            new MarbleSolitaireModelImpl(3, -1,0); // Negative row
    MarbleSolitaireModelImpl failThree =
            new MarbleSolitaireModelImpl(3, 1,-1); // Negative Column
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    MarbleSolitaireModelImpl game = new MarbleSolitaireModelImpl();
    game.move(5,3,4,2);
    game.move(4, 3, 6, 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void moveSamePositionTwice() {
    MarbleSolitaireModelImpl game = new MarbleSolitaireModelImpl();
    game.move(5,3,3,3);
    game.move(2, 3, 4, 3);
    game.move(2, 3, 4, 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void moveFromValidToInvalid() {
    MarbleSolitaireModelImpl game = new MarbleSolitaireModelImpl(5); // ARMTHICKNESS
    game.move(0, 6, 0, 8);
  }

  @Test
  public void testGame() {
    MarbleSolitaireModelImpl game = new MarbleSolitaireModelImpl();
    assertFalse(game.isGameOver()); // check game over.
    game.move(5,3,3,3); // Move up.
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    assertEquals(31, game.getScore());
    game.move(2, 3, 4, 3); // Move down.
    assertEquals(30, game.getScore());
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(2, 5, 2, 3); // Move left.
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O O O _ _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(3, 1, 3, 3); // Move right.
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O O O _ _ O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(0,4, 2, 4); // Move down, top right.
    assertEquals("    O O _\n"
            + "    O O _\n"
            + "O O O O O _ O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(1, 2, 3, 2); // Move second row first marble.
    assertEquals("    O O _\n"
            + "    _ O _\n"
            + "O O _ O O _ O\n"
            + "O _ O O O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(2, 3, 2, 5); // Move second row 3 - 5 column.
    assertEquals("    O O _\n"
            + "    _ O _\n"
            + "O O _ _ _ O O\n"
            + "O _ O O O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(3, 3, 3, 1);
    assertEquals("    O O _\n"
            + "    _ O _\n"
            + "O O _ _ _ O O\n"
            + "O O _ _ O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(2, 0, 2, 2);
    game.move(3, 0, 3, 2);
    assertEquals("    O O _\n"
            + "    _ O _\n"
            + "_ _ O _ _ O O\n"
            + "_ _ O _ O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    game.move(0, 3, 2, 3);
    game.move(2, 2, 2, 4);
    assertEquals("    O _ _\n"
            + "    _ _ _\n"
            + "_ _ _ _ O O O\n"
            + "_ _ O _ O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O O O", game.getGameState());
    // Making all moves to go through the game and check if its over.
    game.move(2, 5, 2, 3);
    game.move(3, 5, 3, 3);
    game.move(3, 3, 1, 3);
    game.move(4, 2, 2, 2);
    game.move(4, 0, 4, 2);
    game.move(4, 3, 4, 1);
    game.move(4, 5, 4, 3);
    game.move(6, 2, 4, 2);
    game.move(4, 2, 4, 0);
    game.move(6, 4, 4, 4);
    game.move(4, 4 ,4, 2);
    assertEquals("    O _ _\n"
            + "    _ O _\n"
            + "_ _ O _ _ _ O\n"
            + "_ _ _ _ _ _ O\n"
            + "O _ O _ _ _ O\n"
            + "    _ _ _\n"
            + "    _ O _", game.getGameState());
    assertTrue(game.isGameOver());
  }


}