import java.util.Random;


public class StickySmartBot implements Bot {
    private Random generator = new Random();
    private int current = generator.nextInt(12)+1;
    private int[] history = new int[2];

    public int getNextMove(int player1LastMove, int player2LastMove) {
        
        int fromP1a = Math.abs(current - player1LastMove);
        int fromP1b = Math.abs(player1LastMove - 12) + current;
        int fromP1c = Math.min(fromP1a, fromP1b);

        int fromP2a = Math.abs(current - player2LastMove);
        int fromP2b = Math.abs(player2LastMove - 12) + current;
        int fromP2c = Math.min(fromP2a, fromP2b);

        int fromPrevP1 = history[0];
        int fromPrevP2 = history[1];
        int fromP1;
        int fromP2;
        
        if (fromP1c < fromP2c) {
            fromP1 = fromP1c;
            fromP2 = fromP2b;
        } else if (fromP1c > fromP2c) {
            fromP2 = fromP2c;
            fromP1 = fromP1b;
        } else {
            fromP1 = fromP1c;
            fromP2 = fromP2b;
        }

        if (fromP1 < fromP2) {
            if (fromPrevP1 == fromP1 || fromP1 < fromPrevP1){
                current = player1LastMove + 6;
                if (current > 12) {
                    current = current - 12;
                }
                return current;
            }
        }
        else if (fromP2 < fromP1) {
            if (fromPrevP2 == fromP2 || fromP2 < fromPrevP2) {
                current = player2LastMove + 6;
                if (current > 12) {
                    current = current - 12;
                }
            }
        }
        else {
            current = (fromP2+3)%12;
        }
        history[0] = fromP1;
        history[1] = fromP2;

        return current;
    }
}