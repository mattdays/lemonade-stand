
import java.util.Random;

/** A Lemonade Stand game player that picks a random opponent and mimics there
  * last move played.
  * 
  * @author MD, BR
  */
public class FollowBot implements Bot {
    private Random generator = new Random();

    private int oppSide(int player){
      if(player <= 6){
        return player + 6;
      }
      else{
        return player - 6;
      }
      // return (player + 6) % 12
    }

    /** Returns an action according to the mixed strategy that picks among 
      * actions uniformly at random.
      * 
      * @param player1LastMove the action that was selected by Player 1 on the
      *                        last round.
      * @param player2LastMove the action that was selected by Player 2 on the
      *                        last round.
      * 
      * @return the next action to play.
      */
    public int getNextMove(int player1LastMove, int player2LastMove) {
      if (generator.nextBoolean()) {
        return oppSide(2);
      }
      else{
        return oppSide(1);
      }
    }
    
}
