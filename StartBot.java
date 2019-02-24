
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Lemonade Stand game player that picks moves uniformly at random and stores
 * previous moves of their opponent.
 * 
 * @author MD, BR
 */
public class StartBot implements Bot {
  private Random generator = new Random();
  private List<Byte> historyP1 = new ArrayList<>();
  private List<Byte> historyP2 = new ArrayList<>();

  /**
   * Returns an action according to the mixed strategy that picks among actions
   * uniformly at random.
   * 
   * @param player1LastMove the action that was selected by Player 1 on the last
   *                        round.
   * @param player2LastMove the action that was selected by Player 2 on the last
   *                        round.
   * 
   * @return the next action to play.
   */
  public int getNextMove(int player1LastMove, int player2LastMove) {
    return generator.nextInt(12) + 1;
  }

  /**
   * Returns an action according to the mixed strategy that picks among actions
   * uniformly at random.
   * 
   * @param player1LastMove the action that was selected by Player 1 on the last
   *                        round.
   * @param player2LastMove the action that was selected by Player 2 on the last
   *                        round.
   * 
   * @return the next action to play.
   */
  public void recordHistory(int player1LastMove, int player2LastMove) {
    historyP1.add((byte)(player1LastMove));
    historyP2.add((byte)(player2LastMove));
  }

}
