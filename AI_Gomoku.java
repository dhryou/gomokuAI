import java.awt.*;
import java.util.*;

class AI_Gomoku{
  Renju_Gomoku judge;
  boolean playerNum;
  int[][] board;
  int checkY, checkX;
  HashSet <PointGrade> defendantList, plaintiffList;
    Iterator it_defendant, it_plaintiff;
    PointGrade tempPoint;
    int temp = 1;

  AI_Gomoku(int[][] board, boolean playerNum){
    this.board = new int[15][15];
    for(int i=0; i<15; i++)
      for(int j=0; j<15; j++)
        this.board[i][j] = board[i][j];
    this.playerNum = playerNum;
    judge = new Renju_Gomoku(board, 0, 0, playerNum);
    defendantList = new HashSet<PointGrade>();
    plaintiffList = new HashSet<PointGrade>();

    for(int i=0; i<14; i++){
      for(int j=0; j<14; j++){
        if (board[i][j] == 0) continue;
        for (int m=-1; m<=1; m++) {
        			checkY = i;
        			if (checkY+m < 0 || checkY+m > 14) continue;
        	for (int n=-1; n<=1; n++) {
        			checkX = j;
        			if (checkX+n < 0 || checkX+n > 14 || (m == 0 && n == 0)) continue;
        		if (board[checkY+m][checkX+n] == 0){
              defendantList.add(new PointGrade(checkX+n, checkY+m));
              plaintiffList.add(new PointGrade(checkX+n, checkY+m));
            }
        	}
    		}
      }
    }
    // it = defendantList.iterator();
    // while(it.hasNext()){
    //   tempPoint = (PointGrade) it.next();
    //   System.out.println(temp++ + ": " + tempPoint.y + ", " + tempPoint.x);
    // }
  }

  public Point whiteAI(){
    it_defendant = defendantList.iterator();
    while(it_defendant.hasNext()){
      tempPoint = (PointGrade) it_defendant.next();
      judge.AIscan(tempPoint, true);
    }
    it_defendant = defendantList.iterator();
    while(it_defendant.hasNext()){
      tempPoint = (PointGrade) it_defendant.next();
      System.out.println(temp++ +" ("+tempPoint.y+","+tempPoint.x+") Priority 1: " + tempPoint.priority1 + ", Priority 2: " + tempPoint.priority2 + ", Priority 3: "
       + tempPoint.priority3 + ", Priority 4: " + tempPoint.priority4 + ", Priority 5: " + tempPoint.priority5 + ", Priority 6: "
       + tempPoint.priority6 + ",\nConnect6: " + tempPoint.connect6 + ", Single line 4x4: " + tempPoint.line4x4);
    }
    //plaintiff array도 judgescan 시키고
    //defendantarray priorityX 먼저, plaintiffarray priorityX 다음, defendantarray priorityX+1 등등
      //여기서 it.next()의 모든 priorityX값을 확인하는 방법찾기 == iterator가 고생 좀 해줌

    int randY = 0,randX = 0;
    randY = (int)(Math.random()*15);
    randX = (int)(Math.random()*15);
    //System.out.println(randY+","+randX);

    //ruleAbide();
    return new Point(randY, randX);
  }
  public Point blackAI(){
    int randY = 0,randX = 0;
    randY = (int)(Math.random()*15);
    randX = (int)(Math.random()*15);
    //System.out.println(randY+","+randX);

    //ruleAbide();
    return new Point(randY, randX);
  }
  //AI class의 white,blackAI에다가 argument 뭘 받을지
  //Renju이용해서 checkmate() true되면 static gameState값 바꿔줘서 AIvAI의 while 문장 stop시킬것.
}
