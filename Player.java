import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Player {
  private boolean maximizer;

  public Player(boolean maximizer) {
    this.maximizer = maximizer;
  }

  public boolean isMax() {
    return maximizer;
  }

  /*Blindly fill current GameState's children with possible moves */
  public GameState explore(GameState g, int depth) {
    Board b = g.getBoard();

    if (g.isTerminal() || depth == 0){
      return g;
    }

    int[][] old = b.getState();
    int[][] boardState = new int[b.getXDim()][b.getYDim()];

    for (int i=0; i<b.getXDim(); i++) {
      System.arraycopy(old[i], 0, boardState[i], 0, b.getYDim());
    }
  
    for(int i=0; i<b.getXDim(); i++) {
      for (int j=0; j<b.getYDim(); j++) {
        boardState = new int[b.getXDim()][b.getYDim()];
        for (int k=0; k<b.getXDim(); k++) {
          System.arraycopy(old[k], 0, boardState[k], 0, b.getYDim());
        }
        Board nextBoard = new Board();
        nextBoard.setState(boardState);

        if (maximizer)
          nextBoard.setState(i, j, 1);
        else
          nextBoard.setState(i, j, -1);
        
        if (g.isValidMove(nextBoard)) {
          GameState next = new GameState(nextBoard);
          g.addNext(next);
         // g.setH(g.utility());
        }
      }
    }
    return g;
  }

  public GameState pickMove(GameState g, boolean optimize) {
    LinkedList<GameState> nextStates = g.getAllNext();
    LinkedList<GameState> pickStates = new LinkedList<GameState>();

    //System.out.println("next moves");
    //for (GameState gr : nextStates) 
    //  System.out.println(gr);
    if (optimize && maximizer ) {
      int max = -100;
      int pos = -1;
      for(int i=0; i<nextStates.size(); i++) {
        GameState next = nextStates.get(i);
        if (next.getH() == max) {
          max = next.getH();
          pickStates.add(next);
        } else if (next.getH() > max) {
          max = next.getH();
          pickStates.clear();
          pickStates.add(next);
        }
      }
      return pick(g, pickStates);
    } else if (optimize && !maximizer) {
      int min = 100;
      int pos = -1;
      for (int i=0;i<nextStates.size(); i++) {
        GameState next = nextStates.get(i);
        if (next.getH() == min) {
          min = next.getH();
          pickStates.add(next);
        } else if (next.getH() < min) {
          min = next.getH();
          pickStates.clear();
          pickStates.add(next);
        }
      }
      return pick(g, pickStates);
    }
    else 
      return pick(g, nextStates);
  }

  /*Picks a random child from the children of GameState g */
  public GameState pickRandomChild(GameState g) {
   // return this.pickMove(g, true);
    LinkedList<GameState> nextStates = g.getNext();
    return pick(g, nextStates); 
  }

  public GameState pick(GameState g, LinkedList<GameState> nextStates) {
    if (nextStates == null) {
//      System.out.println("No child can be selected");
      return null;
    }
    Random random = new Random();
    if (nextStates.size() < 1) {
      this.explore(g, 2);
      return pickMove(g, true); 
    }
    
    int index = random.nextInt(nextStates.size());
    GameState next = nextStates.get(index);
//    System.out.println("Picking random child...:");
//    System.out.println(next);
    return next;
  }
}

