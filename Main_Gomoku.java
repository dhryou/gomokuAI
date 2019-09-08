class Main_Gomoku{
//Initialization
  static Menu_Gomoku menu;
  static Board_Gomoku board;
  static int menuselection;

//Main
  public static void main(String [] args){
    menu = new Menu_Gomoku();
    System.out.println(menuselection);
    for(int i=0; i<1000; i++){
      if ((menuselection = menu.select()) != 0 ) break;
      try{Thread.currentThread().sleep(50);}
      catch(Exception exc){exc.printStackTrace();}
    }
    switch(menuselection){
      case 1: System.out.println("1"); board = new Board_Gomoku(1); break;
      case 2: System.out.println("2"); board = new Board_Gomoku(2); break;
      case 3: System.out.println("3"); board = new Board_Gomoku(3); break;
      case 4: System.out.println("4"); board = new Board_Gomoku(4); break;
    }
  }
}


/*Thread t = new Thread(menu);
t.start();
try{t.join();}
catch(Exception exc2){};
t.stop();*/
