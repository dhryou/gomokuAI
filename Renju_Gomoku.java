class Renju_Gomoku{
  boolean playerNum;
    int player, opponent;
  int[][] board;
  int checkY, checkX;
  PointGrade defendant;
  boolean l4r1, l3r2, l2r3, l1r4;                                               //(Black)Connect6, (White)Priority 1
  boolean l4r, l3r1, l2r2, l1r3, lr4;                                           //Priority 1
  boolean l_3r_, l_2r1_, l_1r2_, l_r3_;                                         //Priority 2
  boolean l03r_, l02rl_, l01r2_, l0r3_, l_r30, l_1r20, l_2r10, l_3r0;           //Priority 3
  boolean l1_2r, l2_1r, l3_r, l1_1r1, l1r1_1, l1_r2, l2r_1, l1r_2, l2_r1, lr_3, lr1_2, lr2_1; //Priority 3 (Blank in media)
  boolean l1_r2_1, l2_r1_2, l1_1r1_1, l1_2r1_, l2_1r_2;                         //Single line 4x4 (Black illegal)
  boolean l_2r_, l_1r1_, l_r2_, l_1_1r_, l_1_r1_, l_1r_1_, l_r1_1_, l_2_r_, l_r_2_; //Priority4
  boolean l02r__, l01r1__, l0r2__, l__r20, l__1r10, l__2r0;                     //Priority 5
  boolean l_1r_, l_r1_;                                                         //Priority 6
  //AI class uses an PointGrade[]arr, while Renju_Gomoku only uses one OPoint and checks its member field values.

  Renju_Gomoku(int[][] board, int boardY, int boardX, boolean playerNum){
    this.board = new int[15][15];
    for(int i=0; i<15; i++)
      for(int j=0; j<15; j++)
        this.board[i][j] = board[i][j];

    this.playerNum = playerNum;
      if (playerNum) {player = 1; opponent = 2;}
      else {player = 2; opponent = 1;}
    defendant = new PointGrade(boardX, boardY);
  }

  void step1a_horizontalScan(){
    defendant.cL = 1; defendant.cR = 1;
    for (int h=-4; h<=4; h++) {
			checkY = defendant.y; checkX = defendant.x;
			if (h == 0) continue;
			else checkX = checkX + h;
			step2X_cLcR(checkY, checkX);
		}
    step3_priority();
    //System.out.println("Horizontal: cL = " + defendant.cL + ", cR = " + defendant.cR);
  }
  void step1b_diagBLTRScan(){
    defendant.cL = 1; defendant.cR = 1;
    for (int lr=-4; lr<=4; lr++) {
			checkY = defendant.y; checkX = defendant.x;
			if (lr == 0) continue;
			else {checkY = checkY + lr; checkX = checkX + lr;}
			step2X_cLcR(checkY, checkX);
		}
    step3_priority();
    //System.out.println("BLTR: cL = " + defendant.cL + ", cR = " + defendant.cR);
  }
  void step1c_diagBRTLScan(){
    defendant.cL = 1; defendant.cR = 1;
    for (int rl=-4; rl<=4; rl++) {
			checkY = defendant.y; checkX = defendant.x;
			if (rl == 0) continue;
			else {checkY = checkY + rl; checkX = checkX - rl;}
			step2Y_cLcR(checkY, checkX);
		}
    step3_priority();
    //System.out.println("BRTL: cL = " + defendant.cL + ", cR = " + defendant.cR);
  }
  void step1d_verticalScan(){
    defendant.cL = 1; defendant.cR = 1;
    for (int v=-4; v<=4; v++) {
			checkY = defendant.y; checkX = defendant.x;
			if (v == 0) continue;
			else {checkY = checkY + v;}
			step2Y_cLcR(checkY, checkX);
		}
    step3_priority();
    //System.out.println("Vertical: cL = " + defendant.cL + ", cR = " + defendant.cR);
  }

  void step2X_cLcR(int checkY, int checkX){
    //If outside board indices, still needs to be considered BLOCKED, and given the following values:
    if ( (checkX < 0 || checkX > 14 || checkY < 0 || checkY > 14) || board[checkY][checkX] == opponent){
      switch (checkX - defendant.x) {
					case -4: defendant.cL *= 29; break;
					case -3: defendant.cL *= 31; break;
					case -2: defendant.cL *= 37; break;
					case -1: defendant.cL *= 41; break;
					case 1: defendant.cR *= 41; break;
					case 2: defendant.cR *= 37; break;
					case 3: defendant.cR *= 31; break;
					case 4: defendant.cR *= 29; break;
				}
    }
    //If blank, given the following values:
    else if (board[checkY][checkX] == 0) {
      switch (checkX - defendant.x) {
					case -4: defendant.cL *= 3; break;
					case -3: defendant.cL *= 5; break;
					case -2: defendant.cL *= 7; break;
					case -1: defendant.cL *= 11; break;
					case 1: defendant.cR *= 11; break;
					case 2: defendant.cR *= 7; break;
					case 3: defendant.cR *= 5; break;
					case 4: defendant.cR *= 3; break;
				}
    }
    //If player stone, given the following values:
    else {
      switch (checkX - defendant.x) {
					case -4: defendant.cL *= 13; break;
					case -3: defendant.cL *= 17; break;
					case -2: defendant.cL *= 19; break;
					case -1: defendant.cL *= 23; break;
					case 1: defendant.cR *= 23; break;
					case 2: defendant.cR *= 19; break;
					case 3: defendant.cR *= 17; break;
					case 4: defendant.cR *= 13; break;
				}
    }
  }
  void step2Y_cLcR(int checkY, int checkX){
    //If outside board indices, still needs to be considered BLOCKED, and given the following values:
    if ( (checkX < 0 || checkX > 14 || checkY < 0 || checkY > 14) || board[checkY][checkX] == opponent){
      switch (checkY - defendant.y) {
					case -4: defendant.cL *= 29; break;
					case -3: defendant.cL *= 31; break;
					case -2: defendant.cL *= 37; break;
					case -1: defendant.cL *= 41; break;
					case 1: defendant.cR *= 41; break;
					case 2: defendant.cR *= 37; break;
					case 3: defendant.cR *= 31; break;
					case 4: defendant.cR *= 29; break;
				}
    }
    //If blank, given the following values:
    else if (board[checkY][checkX] == 0) {
      switch (checkY - defendant.y) {
					case -4: defendant.cL *= 3; break;
					case -3: defendant.cL *= 5; break;
					case -2: defendant.cL *= 7; break;
					case -1: defendant.cL *= 11; break;
					case 1: defendant.cR *= 11; break;
					case 2: defendant.cR *= 7; break;
					case 3: defendant.cR *= 5; break;
					case 4: defendant.cR *= 3; break;
				}
    }
    //If player stone, given the following values:
    else {
      switch (checkY - defendant.y) {
					case -4: defendant.cL *= 13; break;
					case -3: defendant.cL *= 17; break;
					case -2: defendant.cL *= 19; break;
					case -1: defendant.cL *= 23; break;
					case 1: defendant.cR *= 23; break;
					case 2: defendant.cR *= 19; break;
					case 3: defendant.cR *= 17; break;
					case 4: defendant.cR *= 13; break;
				}
    }
  }

  void step3_priority(){
    l4r1 = l3r2 = l2r3 = l1r4 = l4r = l3r1 = l2r2 = l1r3 = lr4 = l_3r_ = l_2r1_ = l_1r2_ = l_r3_ = false;
    l03r_ = l02rl_ = l01r2_ = l0r3_ = l0r3_ = l_r30 = l_1r20 = l_2r10 = l_3r0 = false;
    l1_2r = l2_1r = l3_r = l1_1r1 = l1r1_1 = l1_r2 = l2r_1 = l1r_2 = l2_r1 = lr_3 = lr1_2 = lr2_1 = false;
    l1_r2_1 = l2_r1_2 = l1_1r1_1 = l1_2r1_ = l2_1r_2 = l02r__ = l01r1__ = l0r2__ = l__r20 = l__1r10 = l__2r0 = l_1r_ = l_r1_ = false;
    //(Black)Connect6, (White)Priority 1
    l4r1 = defendant.cL%96577 == 0 && defendant.cR%23 == 0;
    l3r2 = defendant.cL%7429 == 0 && defendant.cR%437 == 0;
    l2r3 = defendant.cL%437 == 0 && defendant.cR%7429 == 0;
    l1r4 = defendant.cL%23 == 0 && defendant.cR%96577 == 0;
    //Priority 1
    l4r = defendant.cL%96577 == 0;
    l3r1 = defendant.cL%7429 == 0 && defendant.cR%23 == 0;;
    l1r3 = defendant.cL%23 == 0 && defendant.cR%7429 == 0;
    lr4 = defendant.cR%96577 == 0;
    //Priority 2
    l_3r_ = defendant.cL%22287 == 0 && defendant.cR%11 == 0;
    l_2r1_ = defendant.cL%2185 == 0 && defendant.cR%161 == 0;
    l_1r2_ = defendant.cL%161 == 0 && defendant.cR%2185 == 0;
    l_r3_ = defendant.cL%11 == 0 && defendant.cR%22287 == 0;
    //Priority 3
    l03r_ = defendant.cL%215441 == 0 && defendant.cR%11 == 0;
    l02rl_= defendant.cL%13547== 0 && defendant.cR%161 == 0;
    l01r2_= defendant.cL%851 == 0 && defendant.cR%2185== 0;
    l0r3_ = defendant.cL%41== 0 && defendant.cR%22287== 0;
    l_r30 = defendant.cL%11 == 0 && defendant.cR%215441== 0;
    l_1r20= defendant.cL%161== 0 && defendant.cR%13547== 0;
    l_2r10= defendant.cL%2185== 0 && defendant.cR%851== 0;
    l_3r0 = defendant.cL%23598== 0 && defendant.cR%41 == 0;
    l1_2r = defendant.cL%28405 == 0;
    l2_1r = defendant.cL%35581 == 0;
    l3_r = defendant.cL%46189 == 0;
    l1_1r1 = defendant.cL%2737 == 0 && defendant.cR%11 == 0;
    l1r1_1 = defendant.cL%11 == 0 && defendant.cR%2737 == 0;
    l1_r2 = defendant.cL%209 == 0 && defendant.cR%437 == 0;
    l2r_1 = defendant.cL%437 == 0 && defendant.cR%209 == 0;
    l1r_2 = defendant.cL%23 == 0 && defendant.cR%3553 == 0;
    l2_r1 = defendant.cL%3553 == 0 && defendant.cR%23 == 0;
    lr_3 = defendant.cR%46189 == 0;
    lr1_2 = defendant.cR%35581 == 0;
    lr2_1 = defendant.cR%28405 == 0;
    //Single line 4x4
    l1_r2_1 = defendant.cL%209 == 0 && defendant.cR%28405 == 0;
    l2_r1_2 = defendant.cL%3553 == 0 && defendant.cR%35581 == 0;
    l1_1r1_1 = defendant.cL%2737 == 0 && defendant.cR%2737 == 0;
    l1_2r1_ = defendant.cR%28405 == 0 && defendant.cR%209 == 0;
    l2_1r_2 = defendant.cL%35581 == 0 && defendant.cR%3553 == 0;
    //Priority 4
    l_2r_ = defendant.cL%2185 == 0 && defendant.cR%11 == 0;
    l_1r1_ = defendant.cL%161 == 0 && defendant.cR%161 == 0;
    l_r2_ = defendant.cL%11 == 0 && defendant.cR%2185 == 0;
    l_1_1r_ = defendant.cL%8211 == 0 && defendant.cR%11 == 0;
    l_1_r1_ = defendant.cL%1045 == 0 && defendant.cR%161 == 0;
    l_1r_1_ = defendant.cL%161 == 0 && defendant.cR%1045 == 0;
    l_r1_1_ = defendant.cL%11 == 0 && defendant.cR%8211 == 0;
    l_2_r_ = defendant.cL%10659 == 0 && defendant.cR%11 == 0;
    l_r_2_ = defendant.cL%11 == 0 && defendant.cR%10659 == 0;
    //Priority 5
    l02r__ = defendant.cL%13547 == 0 && defendant.cR%77 == 0;
    l01r1__ = defendant.cL%851 == 0 && defendant.cR%805 == 0;
    l0r2__ = defendant.cL%41 == 0 && defendant.cR%6210 == 0;
    l__r20 = defendant.cL%77 == 0 && defendant.cR%13547 == 0;
    l__1r10 = defendant.cL%805 == 0 && defendant.cR%851 == 0;
    l__2r0 = defendant.cL%6210 == 0 && defendant.cR%41 == 0;
    //Priority 6
    l_1r_ = defendant.cL%161 == 0 && defendant.cR%11 == 0;
    l_r1_ = defendant.cL%11 == 0 && defendant.cR%161 == 0;


    if (l4r1 || l3r2 || l2r3 || l1r4) defendant.connect6 = true;
    if (l4r || l3r1 || l2r2 || l1r3 || lr4) defendant.priority1++;
    if (l_3r_ || l_2r1_ || l_1r2_ || l_r3_) defendant.priority2++;
    if (l03r_|| l02rl_ || l01r2_  || l0r3_ || l_r30 || l_1r20 || l_2r10 || l_3r0 ||
    l1_2r || l2_1r || l3_r || l1_1r1 || l1r1_1 || l1_r2 || l2r_1 || l1r_2 || l2_r1 || lr_3 || lr1_2 || lr2_1) defendant.priority3++;
    if (l1_r2_1 || l2_r1_2 || l1_1r1_1 || l1_2r1_ || l2_1r_2) defendant.line4x4 = true;
    if (l_2r_ || l_1r1_ || l_r2_ || l_1_1r_ || l_1_r1_ || l_1r_1_ || l_r1_1_ || l_2_r_ || l_r_2_) defendant.priority4++;
    if (l02r__ || l01r1__ || l0r2__ || l__r20 || l__1r10 || l__2r0) defendant.priority5++;
    if (l_1r_ || l_r1_) defendant.priority6++;
  }

  public boolean ruleAbide(){
    step1a_horizontalScan();
    step1b_diagBLTRScan();
    step1c_diagBRTLScan();
    step1d_verticalScan();

    System.out.println("Priority 1: " + defendant.priority1 + ", Priority 2: " + defendant.priority2 + ", Priority 3: "
     + defendant.priority3 + ", Priority 4: " + defendant.priority4 + ", Priority 5: " + defendant.priority5 + ", Priority 6: "
     + defendant.priority6 + ",\nConnect6: " + defendant.connect6 + ", Single line 4x4: " + defendant.line4x4);
    if (defendant.priority4 >= 2) {
      System.out.println("Detected 3x3");
      return true;
    }
    if ((defendant.priority2 + defendant.priority3) >= 2 || defendant.line4x4) {
      System.out.println("Detected 4x4");
      return true;
    }
    if (defendant.connect6) {
      System.out.println("Detected connect6");
      return true;
    }
    return false;
  }
  public boolean checkmate(){
    step1a_horizontalScan();
    step1b_diagBLTRScan();
    step1c_diagBRTLScan();
    step1d_verticalScan();

    if (defendant.priority1 > 0) return true;
    return false;
  }
  public void AIscan(PointGrade point, boolean playerNum){
    defendant = point;
    this.playerNum = playerNum;
      if (playerNum) {player = 1; opponent = 2;}
      else {player = 2; opponent = 1;}
    step1a_horizontalScan();
    step1b_diagBLTRScan();
    step1c_diagBRTLScan();
    step1d_verticalScan();
  }
}

/*How scanning the board using prime numbers works:
  Big picture:  ---- For each *point* ----
                  1. Horizontal, Diagonal(Top left to bottom right), Diagonal(Top right to bottom left), Vertical
                     2. For EACH of the four directions around given *point*, obtain a countLeft(cL) and countRight(cR) value, avoiding non-board indices
                     3. Compares the cL/cR values to any of the combinations to check its priority
                  4. All directions are checked for their priorities and added to an "ArrayList<Point> priorityX"
                ---- Steps 1-4 are repeated for EVERY point applicable ----
                ---- A comprehensive "ArrayList<Point>" for all priorities for the WHOLE BOARD is completed ----
                5. (Excluding priority1, which ends the game) Starting w/ priority 2 to 6, if there are any repeated Points within the ArrayList,
                   returns that Point, as it is the best placement.
                   - If "ArrayList<Point> priorityX" has no repeated Point, then returns a random point within that ArrayList
                   - If "ArrayList<Point> priorityX" is empty, moves onto "ArrayList<Point> priority(X+1)"
                6. There will always be a point to return, as priority6 is adjacent to any already-placed point
*/
