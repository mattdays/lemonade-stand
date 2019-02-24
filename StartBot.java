
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
  private List<Integer> historyP0 = new ArrayList<>();
  private List<Integer> historyP1 = new ArrayList<>();
  private List<Integer> historyP2 = new ArrayList<>();
  private Integer roundNum = 1;
  private float responseR = 0.75;
  private float tol = 0.1;
  private float pScale = 0.5;

  private int minDist(int player1, int player2){
    int smaller = 0;
    int dist2 = 0;
    if(player1 > player2){
      smaller = player2;
      dist2 = Math.abs(player1 - (12 + smaller))
    }
    else{
      smaller = player1;
      dist2 = Math.abs(player2 - (12 + smaller))
    }
    int dist1 = Math.abs(player1 - player2);
    return min(dist2, dist1);
  }

  private float sumConst(){
    float sum = 0;
    for(int k = 2; k < rounNum; k++) {
      sum += Math.pow(responseR, roundNum - 1 - k);
    }
    return sum;
  }

  private float stickIndex(float sumConst){
    float sum = 0;
    for(int k = 2; k < roundNum; k++){
      sum += (Math.pow(responseR, roundNum - 1 - k) / sumConst) *
    }
  }

  private float followIndex(float sumConst){


  }

  private float followPair(float sumConst){

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
  public int getNextMove(int player1LastMove, int player2LastMove) {
    roundNum++;
    if (roundNum > 1) {
      recordHistory(player1LastMove, player2LastMove);
    }

    nextMove = generator.nextInt(12) + 1;
    historyP0.add(nextMove);
    return nextMove;
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

public static void main(String[] args) {
}
