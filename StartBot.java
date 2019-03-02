
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
  private ArrayList<Integer> historyP0 = new ArrayList<>();
  private ArrayList<Integer> historyP1 = new ArrayList<>();
  private ArrayList<Integer> historyP2 = new ArrayList<>();
  private ArrayList<ArrayList<Integer>> history = new ArrayList<ArrayList<Integer>>();
  private Integer roundNum = 0;
  private float responseR = (float) 0.75;
  private float tol = (float) 0.1;
  private float pScale = (float) 0.5;
  private boolean stickCounter = false;
  private boolean worstFlag = false;
  private Integer worstTarget = null;
  private Integer worstTarVal = null;
  private Integer worstCount = 0;
  private float sumConstant = 0;
  private float s1 = 0;
  private float s2 = 0;
  private float f1 = 0;
  private float f2 = 0;
  private float f10 = 0;
  private float f12 = 0;
  private float f20 = 0;
  private float f21 = 0;

  public StartBot(){
    // this.historyP0.add(12);
    // this.historyP1.add(12);
    // this.historyP2.add(12);
    this.history.add(this.historyP0);
    this.history.add(this.historyP1);
    this.history.add(this.historyP2);
  }

  private int scoreRound(int action1, int action2, int action3) {
      if ((action1 == action2) && (action1 == action3))
          return 8; // three-way tie
      else if ((action1 == action2) || (action1 == action3)) {
          return 6; // two-way tie
      }
      else {
          int score = 0;
          int i = action1;
          while ((i != action2) && (i != action3)) { // score clockwise
              i = (i % 12) + 1;
              score += 1;
          }
          i = action1;
          while ((i != action2) && (i != action3)) { // score anti-clockwise
              i = (i-1 > 0) ? i-1 : 12;
              score += 1;
          }
          return score;
      }
  }

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

  private void followPair(int player1, int player2){
    int opp = oppSide(history.get(player2).get(this.roundNum - 2));
    if((player1 == 1) && (player2 == 0)){
      this.f10 *= this.responseR;
      this.f10 -= Math.pow((minDist(history.get(player1).get(this.roundNum - 2), opp)), this.pScale)
                    / this.sumConstant;
    }
    else if((player1 == 1) && (player2 == 2)){
      this.f12 *= this.responseR;
      this.f12 -= Math.pow((minDist(history.get(player1).get(this.roundNum - 2), opp)), this.pScale)
                    / this.sumConstant;
    }
    else if((player1 == 2) && (player2 == 0)){
      this.f20 *= this.responseR;
      this.f20 -= Math.pow((minDist(history.get(player1).get(this.roundNum - 2), opp)), this.pScale)
                    / this.sumConstant;
    }
    else if((player1 == 2) && (player2 == 1)){
      this.f21 *= this.responseR;
      this.f21 -= Math.pow((minDist(history.get(player1).get(this.roundNum - 2), opp)), this.pScale)
                    / this.sumConstant;
    }
  }


  private boolean followIndex(int player){
    int opp2, opp3, current, minD, minD1, minD2;
    current = history.get(player).get(this.roundNum - 2);
    int player2 = (player + 1) % 3;
    int player3 = (player + 2) % 3;
    opp2 = oppSide(history.get(player2).get(this.roundNum - 2));
    opp3 = oppSide(history.get(player3).get(this.roundNum - 2));
    minD1 = minDist(current, opp2);
    minD2 = minDist(current, opp3);
    if(minD1 < minD2){
      minD = minD1;
    }
    else{
      minD = minD2;
    }

    if(player == 1){
      this.f1 *= this.responseR;
      this.f1 -= Math.pow(minD, this.pScale) / this.sumConstant;
    }
    else if(player == 2){
      this.f2 *= this.responseR;
      this.f2 -= Math.pow(minD, this.pScale) / this.sumConstant;    
    }
    return true;
  }

  private int worstCase(int playerVal){
    while(playerVal == this.worstTarVal){
      return playerVal;
    }
    if(this.worstCount < 6){
      this.worstCount++;
      return oppSide(playerVal);
    }
    else{
      this.worstFlag = false;
      this.worstCount = 0;
      return oppSide(playerVal);
    }
  }

  private int ea(int player1Last, int player2Last){
    int size = this.history.get(0).size();
    int current = this.history.get(0).get(size - 1);

    if(this.worstFlag){
      if(this.worstTarget == 1){
        return worstCase(player1Last);
      }
      else if(this.worstTarget == 2){
        return worstCase(player2Last);
      }
    }

    if(this.stickCounter){
      int utility = this.scoreRound(this.history.get(0).get(size - 1), this.history.get(1).get(size - 1), this.history.get(2).get(size - 1));
      if(utility > 8){
        return current;
      }
      this.stickCounter = false;
    }

  
    // System.out.println("history p1:" + history.get(1).size());
    // System.out.println("RoundNum: " + this.roundNum);

    //if roundNum <= 2:
    if (this.roundNum >= 4) {
      this.sumConstant += Math.pow(responseR, this.roundNum - 2 - 2);
      this.s1 *= this.responseR;
      this.s1 -= Math.pow(minDist(history.get(1).get(roundNum - 2), history.get(1).get(roundNum - 3)), this.pScale) / this.sumConstant;
      this.s2 *= this.responseR;
      this.s2 -= Math.pow(minDist(history.get(2).get(roundNum - 2), history.get(2).get(roundNum - 3)), this.pScale) / this.sumConstant;
      followIndex(1);
      followIndex(2);
    }
   
    //stick conditional

    System.out.println("s1: " + s1 + " s2: " + s2 + " f1: " + f1 + " f2: " + f2);
    if((s1 > (s2 + this.tol)) && (s1 > (f1 + this.tol)) && (s1 > (f2 + this.tol))){
    // if((s1 > (s2 + this.tol))){
      //follow s1
      // System.out.println("got here");
      return oppSide(player1Last);
    }
    else if((s2 > (s1 + this.tol)) && (s2 > (f1 + this.tol)) && (s2 > (f2 + this.tol))){
      //follow s2
      return oppSide(player2Last);
    }

    //both i and j have high stick indices
    if(s1 > (f1 + this.tol)){
      if(s2 > (f2 + this.tol)){
        int utility = this.scoreRound(this.history.get(0).get(size - 1), this.history.get(1).get(size - 1), this.history.get(2).get(size - 1));
        if (utility > 8) {
          this.stickCounter = true;
          return current;
        }
        else{
          if(s1 > s2){
            return oppSide(player1Last);
          }
          else{
            return oppSide(player2Last);
          }
        }
      }
    }

    //follow conditional
    if(this.roundNum >= 4){
      followPair(1, 0);
      followPair(1, 2);
      followPair(2, 0);
      followPair(2, 1);
    }
    // float f10 = followPair(constant, 1, 0);
    // float f02 = followPair(constant, 0, 2);
    // float f20 = followPair(constant, 2, 0);
    // float f12 = followPair(constant, 1, 2);
    // float f21 = followPair(constant, 2, 1);
    if((f1 > (s2 + this.tol)) && (f1 > (s1 + this.tol)) && (f1 > (f2 + this.tol))){
      if(f10 > f12){
        return current;
      }
      else{
        return this.history.get(2).get(size - 1);
      }
    }
    else if((f2 > (s2 + this.tol)) && (f2 > (s1 + this.tol)) && (f2 > (f1 + this.tol))){
      if(f20 > f21){
        return current;
      }
      else{
        return this.history.get(1).get(size - 1);
      }
    }

    //Both i and j have high follow indices
    if( (f1 > (s1 + this.tol)) && (f2 > (s2 + this.tol)) && (f12 > f10) && (f21 > f20) ){
      if(f1 > f2){
        return this.history.get(1).get(size - 1);
      }
      else{
        return this.history.get(2).get(size - 1);
      }
    }

    //Carrot and Stick
    int opp1 = oppSide(player1Last);
    if(opp1 == player2Last){
      this.worstFlag = true;
      if(f1 > f2){
        this.worstTarget = 1;
        this.worstTarVal = player1Last;
        return player1Last;
      }
      else{
        this.worstTarget = 2;
        this.worstTarVal = player2Last;
        return player2Last;
      }
    }
    return current;
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
    this.roundNum++;
    if (this.roundNum > 1) {
      recordHistory(player1LastMove, player2LastMove);
      int nextMove = ea(player1LastMove, player2LastMove);
      history.get(0).add(nextMove);

      // int utility0 = scoreRound(this.historyP0.get(this.historyP0.size() - 1),
      // this.historyP1.get(this.historyP1.size() - 1), this.historyP2.get(this.historyP2.size() - 1));

      // int utility1 = scoreRound(this.historyP1.get(this.historyP1.size() - 1),
      // this.historyP0.get(this.historyP0.size() - 1), this.historyP2.get(this.historyP2.size() - 1));

      // int utility2 = scoreRound(this.historyP2.get(this.historyP2.size() - 1),
      // this.historyP1.get(this.historyP1.size() - 1), this.historyP0.get(this.historyP0.size() - 1));

      // System.out.println("/////////////////////////////////////////////////////////");
      // System.out.println("ROUND: " + this.roundNum);
      // System.out.println("Player 0: " + nextMove + " Previous-Player 1: " + player1LastMove + " Previous-Player 2: " + player2LastMove);
      // System.out.println("Utility 0: " + utility0 + " Utility 1: " + utility1 + " Utility 2: " + utility2);
      // System.out.println("/////////////////////////////////////////////////////////");
      return nextMove;
    } else if (this.roundNum == 2) {
      recordHistory(player1LastMove, player2LastMove);
      int utility = scoreRound(this.historyP0.get(this.historyP0.size() - 1),
                    this.historyP1.get(this.historyP1.size() - 1), this.historyP2.get(this.historyP2.size() - 1));
      int nextMove = this.generator.nextInt(12) + 1;
      if (utility < 8) {
        nextMove = (this.historyP0.get(this.historyP0.size() - 1) + 4) % 12;
        if (nextMove == 0) {
            nextMove = 12;
        }
      }
      history.get(0).add(nextMove);
      return nextMove;
      
    } else {
      int nextMove = this.generator.nextInt(12) + 1;
      // recordHistory(player1LastMove, player2LastMove);
      history.get(0).add(nextMove);
      return nextMove;
    }
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
  }

  public static void main(String[] args) {
    int test1 = 0, test2 = 1, test3 = 3;
    StartBot a = new StartBot();


    // int a = bot.getNextMove(test1, test2);
    // System.out.println(bot.history.get(1).get(0));
  }
}
