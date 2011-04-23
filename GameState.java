// Oh hai, I changed ur file

import java.util.LinkedList;
import java.util.Vector;

public class GameState {
  private int h;
  private Board b;
  private Board weightedBoard;
  private boolean visited;

  private LinkedList<GameState> next;  
  private GameState parent;

  public GameState(Board b) {
    this.b = b;
    next = new LinkedList<GameState>();
 
    //set weigthed board
    weightedBoard = new Board();
    int[][] weights = {{3,2,3},{2,4,2},{3,2,3}};
    weightedBoard.setState(weights);

    visited = false;
  }

  public boolean addNext(GameState g) {
    if (next == null) 
      next = new LinkedList<GameState>();
    if(isValidMove(g.getBoard())) {
      next.add(g);
      g.setParent(this);
      return true;
    }
    return false;
  }

  public LinkedList<GameState> getAllNext() {
    return next;
  }

  public LinkedList<GameState> getNext() {
    LinkedList<GameState> nextUnvisited = new LinkedList<GameState>();
    for (GameState g : next) {
      if (!g.visited())
        nextUnvisited.add(g);
    }
    return nextUnvisited;
  }

  public void setParent(GameState p) {
    parent = p;
  }
  
  public void setH(int h) {
    this.h = h;
  }

  public void setBoard(Board b) {
    this.b = b;
  }

  public void setVisited(boolean v) {
    visited = v;
  }
  
  public GameState getParent() {
    return parent;
  }

  public int getH(){
    return h;
  }

  public Board getBoard() {
    return b;
  }

  public boolean visited() {
    return visited;
  }

  public int utility() {
    int [][] board = b.getState();
    int [][] weights = weightedBoard.getState();
//    System.out.println("in utility(), \n" + this); 
    int h = 0;
    /* Check columns */
    for(int i=0; i< b.getXDim(); i++) {
      int sum = 0;
      for (int j=0; j<b.getYDim(); j++) {
        sum += board[i][j] * weights[i][j];
      }
      h += sum;
      if (sum >= 8)
        return sum;
      if (sum <= -8)
        return sum; 
    }
//    System.out.println("in utility(), h : " + h);
    /* Check rows */
    int rowMax = 0;
    for(int i=0; i< b.getYDim(); i++) {
      int sum = 0;
      for (int j=0; j<b.getXDim(); j++) {
        sum += board[j][i]*weights[i][j];
      }
      if (sum >= 8)
        return sum;
      if (sum <= -8)
        return sum;
    }

 // System.out.println("checking cross pos 1");
    /*Check cross pos 1 */
    int sum = 0;
    for (int i=0; i<b.getXDim(); i++) {
      sum += board[i][i]*weights[i][i];
    }
    if (sum >= 10 )
      return sum;
    if (sum <=-10 )
      return sum; 

//   System.out.println("checking cross pos 2");
    /*Check the other cross pos */
    sum = 0;
    for (int i=b.getXDim()-1; i>=0; i--) {
      int j=2-i;
      sum += board[i][j]*weights[i][j];
      //System.out.println("sum: " + sum + "\tboard[i][j]: " + board[i][j] + "\tweights[i][j]: " + weights[i][j]); 
      
    }
    if (sum >= 10)
      return sum;
    if (sum <=-10)
      return sum;
    h = Math.min(7,h);
    h = Math.max(-7,h);
    return h;
  }


  public int eval() {
    int[][] board = b.getState();
    /* Check columns */
    for(int i=0; i< b.getXDim(); i++) {
      int sum = 0;
      for (int j=0; j<b.getYDim(); j++) {
        sum += board[i][j];
      }
      if (sum == 3) 
        return 1;
      if (sum == -3)
        return -1;
    }

    /* Check rows */
    for(int i=0; i< b.getYDim(); i++) {
      int sum = 0;
      for (int j=0; j<b.getXDim(); j++) {
        sum += board[j][i];
      }
      if (sum == 3) 
        return 1;
      if (sum == -3)
        return -1;
    }

 // System.out.println("checking cross pos 1");

    /*Check cross pos 1 */
    int sum = 0;
    for (int i=0; i<b.getXDim(); i++) {
      sum += board[i][i];
    }
      if (sum == 3) 
        return 1;
      if (sum == -3)
        return -1;

    sum = 0;
//   System.out.println("checking cross pos 2");
    /*Check the other cross pos */
    for (int i=b.getXDim()-1; i>=0; i--) {
      for (int j=0; j<b.getYDim(); j++) {
        sum += board[i][j];
      }
    }
      if (sum == 3) 
        return 1;
      if (sum == -3)
        return -1;
    return 0;
  }

  public boolean isTerminal() {
    int numZeros = 0;
    int[][] board = b.getState();
    
    for (int i=0; i<b.getXDim(); i++) {
      for (int j=0; j<b.getYDim(); j++) {
       if (board[i][j] == 0)
         numZeros++;
      }
    }
    //System.out.println("eval: " + eval());
    return (numZeros == 0 || eval() == -1 || eval() == 1);
  }

  public boolean isValidMove(Board nextBoard) {
    int[][] current = b.getState();
    int[][] next = nextBoard.getState();

    int boardChanged = 0;
    for(int i=0; i<b.getXDim(); i++) {
      for(int j=0; j<b.getYDim(); j++) {
        if((current[i][j] == 1 && next[i][j] != 1) ||
          (current[i][j] == -1 && next[i][j] != -1))
          return false;
        if(current[i][j]==0 && next[i][j]!=0)
          boardChanged++;
      }
    }
    return (boardChanged==1);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Game State:\n");
    if (b != null)
      sb.append(b.toString());
    else
      sb.append("Game State does not have a board!");
    return sb.toString();
  }
}
