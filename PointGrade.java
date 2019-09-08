import java.awt.Point;

class PointGrade extends Point{
  boolean connect6;
  boolean line4x4;
  int priority1;  //5row FINISH
  int priority2;  //4row (checkmate)
  int priority3;  //4row-blocked (check)
  int priority4;  //3row
  int priority5;  //3row-blocked
  int priority6;  //adjacent (2row)
  int cL = 1;     //countLeft
  int cR = 1;     //countRight
  PointGrade(){
    super();
  }
  PointGrade(int x, int y){
    super(x,y);
  }
  PointGrade(Point p){
    super(p);
  }
  public boolean equals(Object obj){
    PointGrade comparison = (PointGrade) obj;
    if(this.y == comparison.y && this.x == comparison.x) return true;
    return false;
  }
}
