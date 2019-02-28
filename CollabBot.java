
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Lemonade Stand game player that picks moves uniformly at random and stores
 * previous moves of their opponent.
 *
 * @author MD, BR
 */
public class CollabBot implements Bot {
  private Random generator = new Random();
  private ArrayList<Integer> historyP0 = new ArrayList<>();
  private ArrayList<Integer> historyP1 = new ArrayList<>();
  private ArrayList<Integer> historyP2 = new ArrayList<>();
  // private Integer roundNum = 1;

  private float smallGamma = 0.05;
  private float smallDelta = 0.3;
  private float smallPhi = 0.5;

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

  private float bigGamma() {
    for (int)
  }

  private float leadIndex (int playerNumber, int playerLastMove) {
    float sum = 0;
    for (int k = 2; k < roundNum; k++) {
      
    }
  }

  public int getNextMove(int player1LastMove, int player2LastMove) {
    updateBigGamma();
  }


  public static void main(String[] args) {
    // int di
    // System.out.println("test");
  }
}
