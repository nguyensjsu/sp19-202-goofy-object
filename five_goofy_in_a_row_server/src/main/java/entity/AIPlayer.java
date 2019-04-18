package entity;

// SCRATCH!!!
import java.util.ArrayList;


class Node{
  public Node bestChild = null;
  public ArrayList<Node> child = new  ArrayList<Node>() ;
  public Position p = new Position();
  public int score;



  // ??? Tree Node
  Node(){
    this.child.clear();
    bestChild = null;
    score = 0;
  }

  public void setPosition(Position p){
    this.p.setX(p.getX());
    this.p.setY(p.getY());
  }

  public void addChild(Node r){
    this.child.add(r);
  }

  public Node getLastChild(){
    return child.get(child.size()-1);
  }
}




public class AIPlayer {

  private final int gridNum = 15;
  private final int searchDepth = 5; // search depth
  private final int alpha = 10; // alpha
  private final int beta = 10; // beta
  private final int limitNum = 8; //


  public Position getAIPosition() {

    int maxAIScore = 0;

    // return the position with maximum AI score
    for (i = 0; i<= gridNum; i++){
      for (j = 0; j<= gridNum; j++){
        if (chessArray[i][j] == 0 && maxAIScore < AIScore[i][j]){
          maxAIScore = AIScore[i][j];
          result = new Position(i, j);
          }
        }
    }
    return result;
  }

  public int[][] getAIScore(Board board) {

    int AIScore[][] = new int[gridNum][gridNum];
    for (i = 0; i <= gridNum; i++) {
      for (j = 0; j <= gridNum; j++) {
        if (board[i][j] != 0) {
          Position p = new Position(i, j);
          AIScore[i][j] = minimax(p, board, searchDepth, alpha, beta, true);
        }
      }
    }
    return AIScore;
  }


  public int minimax(Position p, Board board, int searchDepth, int alpha, int beta, boolean AIPlayer){

    int result = 0;

    if (searchDepth == 0 || "GAME END @ position" ) {
      result = staticScore( p, board);
    }

    if (AIPlayer) {
      int maxScore = Integer.MIN_VALUE;
      for (i = 0; i <= gridNum; i++) {
        for (j = 0; j <= gridNum; j++) {
          if (board[i][j] == 0) {
            Position pp = new Position(i, j);
            int score = minimax(pp, board, searchDepth - 1, alpha, beta, false);
            maxScore = max(maxScore, score);
            alpha = max(alpha, score);
            if (beta <= alpha) {
              break;
            }
            result = maxScore;
          }
        }
      }
    }

    else {
      int minScore = Integer.MAX_VALUE ;
      for (i = 0; i <= gridNum; i++) {
        for (j = 0; j <= gridNum; j++) {
          if (board[i][j] == 0) {
            Position pp = new Position(i, j);
            int score = minimax(pp, board,searchDepth - 1, alpha, beta, true);
            minScore = min(minScore, score);
            beta = min(beta, score);
            if (beta <= alpha) {
              break;
            }
            result = minScore;
          }
        }
      }
    }
    return result;
  }


  public int staticScore(Position p, Board board){
    String status = getStatus( p,  board) ;
    switch(status){
      case "live_five":
        return 1000000;
        break;
      case "live_four":
        return 100000;
        break;
      case "sleep_four":
        return 10000;
        break;
      case "live_three":
        return 1000;
        break;
      case "live_two":
        return 100;
        break;
      case "sleep_three":
        return 100;
        break;
      case "live_one":
        return 10;
        break;
      case "sleep_two":
        return 10;
        break;
      case "sleep_one":
        return 1;
        break;
      case "un_known":
        return 0;
        break;

    }

  }

  public String getStatus(Position p, Board board){
    return "live_five";
  }


}



