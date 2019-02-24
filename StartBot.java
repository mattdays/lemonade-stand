
import java.util.List;
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
  private List<Integer> historyP1 = new ArrayList<>();
  private List<Integer> historyP2 = new ArrayList<>();
  private Integer numRounds = 0;

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
    if (numRounds > 1) {
      recordHistory(player1LastMove, player2LastMove);
    }
    numRounds++;
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
    historyP1.add((player1LastMove));
    historyP2.add((player2LastMove));
    System.out.println("h1" + historyP1);
    System.out.println("h2" + historyP2);
  }

}
