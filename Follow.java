import java.util.Random;

/** A Lemonade Stand game player that picks moves uniformly at random.
  * 
  * @author RR
  */
public class RandomBot implements Bot {
    private Random generator = new Random();
    private ArrayList<Integer> historyP1 = new ArrayList<>();
    private Integer numGame = 0;

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
        if(numGame == 0){
            historyP1.add(player1LastMove);
            return generator.nextInt(12) + 1;
        }
        historyP1.add(player1LastMove);
        return oppSide(historyP1.get(historyP1.size() - 1));
    }
    
}
