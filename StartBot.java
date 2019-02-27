
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
  private ArrayList<ArrayList<Integer>> history = new ArrayList<ArrayList<Intger>>();
  private List<Integer> historyP0 = new ArrayList<>();
  private List<Integer> historyP1 = new ArrayList<>();
  private List<Integer> historyP2 = new ArrayList<>();
  history.add(historyP0);
  history.add(historyP1);
  history.add(historyP2);
  private Integer roundNum = 1;
  private double responseR = 0.75;
  private double tol = 0.1;
  private double pScale = 0.5;

  private int oppSide(int player){
    if(player <= 6){
      return player + 6;
    }
    else{
      return player - 6;
    }
    // return (player + 6) % 12
  }

  private int minDist(int player1, int player2){
    int smaller = 0;
    int dist2 = 0;
    if(player1 > player2){
      smaller = player2;
      dist2 = Math.abs(player1 - (12 + smaller));
    }
    else{
      smaller = player1;
      dist2 = Math.abs(player2 - (12 + smaller));
    }
    int dist1 = Math.abs(player1 - player2);
    return Math.min(dist2, dist1);
  }

  private double sumConst(){
    double sum = 0;
    for(int k = 2; k < roundNum; k++) {
      sum += Math.pow(responseR, roundNum - 1 - k);
    }
    return sum;
  }

  private double stickIndex(float sumConst, int player){
    double sum = 0;
    // Must do if check for which player to compare against
    for(int k = 2; k < roundNum; k++){
      sum += (Math.pow(responseR, roundNum - 1 - k) / sumConst) *
                Math.pow((minDist(history.get(player).get(k),
                  history.get(player).get(k - 1)), pScale);
    }
    return sum;
  }

  private double followPair(float sumConst, int player1, int player2){
    double sum = 0;
    for(int k = 2; k < roundNum; k++){
      int opp = oppSide(history.get(player2).get(k - 1));
      sum += (Math.pow(responseR, roundNum - 1 - k) / sumConst) *
              Math.pow((minDist(history.get(player1).get(k), opp), pScale);
    }
    return sum;

  }

  private double followIndex(int player){
    int opp2, opp3, curr, minD;
    double sum = 0;
    int player2 = (player + 1) % 3;
    int player3 = (player + 2) % 3;
    for(int k = 2; k < roundNum; k++){
      opp2 = oppSide(history.get(player2).get(k - 1));
      opp3 = oppSide(history.get(player3).get(k - 1));
      curr = history.get(player).get(k);
      minD1 = minDist(curr, opp2);
      minD2 = minDist(curr, opp3);
      if(minD1 < minD2){
        minD = minD1;
      }
      else{
        minD = minD2;
      }
      sum += (Math.pow(responseR, roundNum - 1 - k) / sumConst) *
                Math.pow(minD, pScale);
    }
    return sum;
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

    int nextMove = generator.nextInt(12) + 1;
    history.get(0).add(nextMove);
    // historyP0.add(nextMove);
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
    history.get(1).add(player1LastMove);
    history.get(2).add(player2LastMove);
    // historyP1.add((player1LastMove));
    // historyP2.add((player2LastMove));
    System.out.println("h1" + history.get(1));
    System.out.println("h2" + history.get(2));
  }

  public static void main(String[] args) {
    // int di
    System.out.println("test");
  }
}
