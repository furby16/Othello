public class Board {
  private final int xdim = 8;
  private final int ydim = 8;

  private int[][] board;

  public Board() {
    board = new int[xdim][ydim];    
  }

/*
  public Board(FileInputStream fis) [
  
  }
*/

  public int[][] getState() {
    return board;
  }

  public void setBoard(int[][] board) {
    this.board = board;
  }

  public int getXDim() {
    return xdim;
  }
  
  public int getYDim() {
    return ydim;
  }

  public void setCell(int xcoord, int ycoord, int c) {
    if (board != null)
      board[xcoord][ycoord] = c;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<this.getXDim(); i++) {
      for (int j=0; j<this.getYDim(); j++) {
        if (board[i][j] == 1)
          sb.append("x");
        if (board[i][j] == -1)
          sb.append("o");
        if (board[i][j] == 0) 
          sb.append("_");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
