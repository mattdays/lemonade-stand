
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * A Lemonade Stand game player that picks moves uniformly at random and stores
 * previous moves of their opponent.
 *
 * @author MD
 */
public class CollabBot implements Bot {
  private Random generator = new Random();
  private ArrayList<Integer> historyP0 = new ArrayList<>();
  private ArrayList<Integer> historyP1 = new ArrayList<>();
  private ArrayList<Integer> historyP2 = new ArrayList<>();
  // private Integer roundNum = 1;
  private List<Integer>[] history = new ArrayList[3];

  private float smallGamma = 0.05f;
  private float smallDelta = 0.3f;
  private float smallPhi = 0.5f;

  // private float bigK = 15;
  // private int bigGamma = 0;
  private int roundNum = 1;
  // private int roundsPlayed = 0;

  private int rMax = 12;
  private int rMin = 6;
  private int rOther = 8;
  private float errorTolerance = 4;

  // private double responseR = 0.75;
  // private double tol = 0.1;
  // private double pScale = 0.5;
  // private boolean stickCounter = false;

  public CollabBot () {
    this.history[0] = this.historyP0;
    this.history[1] = this.historyP1;
    this.history[2] = this.historyP2;
    // List<Integer> historyP0 = new ArrayList<>();
    // List<Integer> historyP1 = new ArrayList<>();
    // List<Integer> historyP2 = new ArrayList<>();


  }

  private float bigGamma() {
    float sum = 0;
    for (int i = 2; i < this.roundNum - 1; i++) {
      sum += Math.pow(smallGamma, this.roundNum - i);
    }
    return sum;
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

  private float leadIndex (int playerNumber, int playerLastMove) {
    float sum = 0;
    float denom = bigGamma();
    for (int k = 2; k < roundNum - 1; k++) {
      float first = (float) ((Math.pow(smallGamma, this.roundNum - 1 - k))/(denom));
      // float second = minDist((history[playerNumber]).get((history[playerNumber]) - 1, player2));

      // float second = minDist(history, player2);

      // sum += Math
    }
    return 0.0f;
  }

  public int getNextMove(int player1LastMove, int player2LastMove) {
    // updateBigGamma();
    return 0;
  }


  public static void main(String[] args) {
    // int di
    // System.out.println("test");
  }
}
