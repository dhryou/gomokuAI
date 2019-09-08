import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

class Board_Gomoku extends JFrame implements ActionListener, MouseListener{
//Declaration
  JMenuBar menuBar;
  JMenu fileMenu, displayMenu, hintMenu, aboutMenu;
    JMenuItem file_newGame, file_open, file_save;
    JMenuItem display_board, display_skeleton;
    JMenuItem hint_show;
    JMenuItem about_me;
  Canvas canvas;
  JPanel westPanel, southPanel;
    JLabel[] westLabel = new JLabel[15];
    JLabel[] southLabel = new JLabel[16];
  static boolean playerNum = true;		    //Black = true
	static boolean gameState = true;
	int mouseX, mouseY, boardX, boardY;
  JDialog winDialog;
		JLabel winLabel;
		JPanel winPanel;
  JDialog ruleDialog;
		JTextArea ruleTextArea;
  JDialog skeleton;
    String skeletonText;
    JTextArea skeletonTextArea;
  int[][] board;
  Point[] history;
    int histindex;
	String order = "", fileName = "";
  int playoption;
  Renju_Gomoku judge;
  AI_Gomoku AI;
  Point AIselectpoint;

  Board_Gomoku(int playoption){
    this.playoption = playoption;
    menuBar = new JMenuBar();
			fileMenu = new JMenu("File");
				file_newGame = new JMenuItem("New Game");
				file_open = new JMenuItem("Open");
				file_save = new JMenuItem("Save");
			displayMenu = new JMenu("Display");
				display_board = new JMenuItem("Board");
				display_skeleton = new JMenuItem("Skeleton");
			hintMenu = new JMenu("Hint");
				hint_show = new JMenuItem("Show Hint");
			aboutMenu = new JMenu("About");
				about_me = new JMenuItem("About the developer");
    menuBar.add(fileMenu);
  		fileMenu.add(file_newGame);         file_newGame.addActionListener(this);
  		fileMenu.add(file_open);            file_open.addActionListener(this);
  		fileMenu.add(file_save);            file_save.addActionListener(this);
  	menuBar.add(displayMenu);
  		displayMenu.add(display_board);     display_board.addActionListener(this);
  		displayMenu.add(display_skeleton);  display_skeleton.addActionListener(this);
  	menuBar.add(hintMenu);
  		hintMenu.add(hint_show);            hint_show.addActionListener(this);
  	menuBar.add(aboutMenu);
  		aboutMenu.add(about_me);            about_me.addActionListener(this);

    canvas = new GameCanvas();
		canvas.addMouseListener(this);
		canvas.setBackground(new Color(253,231,86));
		add("Center", canvas);

		westPanel = new JPanel(new GridLayout(15,1));
			for(int i=0; i<15; i++){
				westLabel[i] = new JLabel(""+i);
				westPanel.add(westLabel[i]);
			}
		    westPanel.setBackground(new Color(253,231,86));
		add("West", westPanel);
		southPanel = new JPanel(new GridLayout(1,16));
			southLabel[0] = new JLabel("");
			southPanel.add(southLabel[0]);
			for(int i=1; i<16; i++){
				southLabel[i] = new JLabel(""+(i-1));
				southPanel.add(southLabel[i]);
			}
		    southPanel.setBackground(new Color(253,231,86));
		add("South", southPanel);

    board = new int[15][15];
			for (int i=0; i<15; i++){
				for (int j=0; j<15; j++)
					board[i][j] = 0;
			}
		history = new Point[225];

    winDialog = new JDialog(this, "Winner", true);
			winLabel = new JLabel();
        winLabel.setForeground(Color.RED);
        winLabel.setFont(new java.awt.Font("MS Song", Font.BOLD, 44));
			winPanel = new JPanel();
        winPanel.add("Center", winLabel);
      winDialog.add("Center", winPanel);
			winDialog.setSize(300,100);
			winDialog.setLocationRelativeTo(this);

    ruleDialog = new JDialog(this, "", true);
			String ruleText = "\r\n  For player 1 (black), the following stone\r\n  placements are illegal:\r\n\r\n    - 3x3\r\n    - 4x4\r\n    - 6 or more";
			ruleTextArea = new JTextArea(ruleText);
				ruleTextArea.setEditable(false);
				ruleTextArea.setBackground(new Color(28, 142, 224));
				ruleTextArea.setForeground(Color.WHITE);
			ruleDialog.setSize(300,150);
			ruleDialog.setLocationRelativeTo(this);
			ruleDialog.add(ruleTextArea);

		skeleton = new JDialog(this, "X-Ray Board", true);
			skeletonText = "\r\n";
			skeletonTextArea = new JTextArea();
				skeletonTextArea.setEditable(false);
				skeletonTextArea.setForeground(Color.BLACK);
			skeleton.setSize(300,300);
			skeleton.setLocationRelativeTo(this);
			skeleton.add(skeletonTextArea);

    setTitle("Gomoku");
    setSize(630,660);
    setJMenuBar(menuBar);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);

    if (playoption == 3) placestone(true,7,7);
    if (playoption == 4) AIvAI();
  }

  private void AIvAI(){
    AI = new AI_Gomoku(board, playerNum);
    placestone(true,7,7);
    while(this.gameState){
      try {
        (Thread.currentThread()).sleep(1500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      AIselectpoint = AI.whiteAI();
      placestone(false,AIselectpoint.y,AIselectpoint.x);
      try {
        (Thread.currentThread()).sleep(1500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      AIselectpoint = AI.blackAI();
      placestone(true,AIselectpoint.y,AIselectpoint.x);
    }
    if(playerNum) placestone(true,AIselectpoint.y,AIselectpoint.x);
    else placestone(false,AIselectpoint.y,AIselectpoint.x);
  }

  public void placestone(boolean playerNum, int boardY, int boardX){            //Place stone (edit board and canvas repaint)
    if (playerNum == true) board[boardY][boardX] = 1;
		else board[boardY][boardX] = 2;
		history[histindex++] = new Point(boardY, boardX);
    mouseY = boardY*40+20; mouseX = boardX*40+20;
    System.out.println(playerNum +": "+ boardY+","+boardX +"; "+ mouseY+","+mouseX);
    canvas.repaint();
    this.playerNum = !this.playerNum;
  }

  public void mouseClicked(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
  public void mousePressed(MouseEvent e){
    if (gameState == false){
      System.out.println("Game over.");
      return;
    }
    if (playoption == 4) return;

    mouseX = e.getX();  if ((mouseX-20)%40 != 0) mouseX = 40*(mouseX/40)+20;
    mouseY = e.getY();  if ((mouseY-20)%40 != 0) mouseY = 40*(mouseY/40)+20;
    boardX = (mouseX-20)/40;
    boardY = (mouseY-20)/40;
    if( board[boardY][boardX] != 0 ){                                           //Check "occupied"
  		System.out.println("Occupied.");
  		return;
  	}

    judge = new Renju_Gomoku(board, boardY, boardX, playerNum);                 //CALL Renju_Gomoku(judge)
    if(playerNum == true){
      if(judge.ruleAbide()){                                                    //CHECK 3x3, 4x4, connect6 + ruleDialog
        ruleDialog.setVisible(true);
        //********************ruleDialog "In this case, the violation is: " and pull up the type of error from Renju Gomoku;
        return;
      }
    }

    placestone(playerNum, boardY, boardX);                                      //Placing stone (Method also available to AI)

    if(judge.checkmate()){                                                      //5row(Checkmate) + winDialog
      //playerNum boolean values are switched b/c placestone automatically flips values
      if(playerNum == false){winLabel.setText("Black wins."); winPanel.setBackground(Color.BLACK); winDialog.setTitle("Black wins");}
      else {winLabel.setText("White wins."); winPanel.setBackground(Color.WHITE); winDialog.setTitle("White wins");}
			winDialog.setVisible(true);
      gameState = false;
      return;
    }
  }
  public void actionPerformed(ActionEvent e){
    if (e.getSource() == file_newGame){
			order = "newGame";
			System.out.println("New Game");
      canvas.repaint();
		}
		if (e.getSource() == file_open) {
			File openFile = new File("./");
			JFileChooser jfilechooser = new JFileChooser(openFile);
			jfilechooser.showOpenDialog(null);
			openFile = jfilechooser.getSelectedFile();
			FileInputStream fis;
			InputStreamReader isr;
			BufferedReader breader;
			String readString = null;
			int temp = 0, x, y;
			String strX = null, strY = null;

			try {
				fis = new FileInputStream(openFile);
				isr = new InputStreamReader(fis);
				breader = new BufferedReader(isr);
				histindex = 0;
				readString = breader.readLine();
				fileName = readString.substring(0, readString.indexOf("e")+1);

				while ((readString = breader.readLine()) != null) {
					temp = readString.indexOf(",");
					if (temp <= 0) continue;
					strY = readString.substring(temp-2, temp);
						y = Integer.parseInt(strY.trim(), 10);
					strX = readString.substring(temp+1, readString.length());
						x = Integer.parseInt(strX.trim(), 10);

					history[histindex++] = new Point(y, x);
					System.out.print(history[histindex-1].x + ", " + history[histindex-1].y + "\r\n");
				}

				breader.close();
				isr.close();
				fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			this.order = "openGame";
			canvas.repaint();
		}
		if (e.getSource() == file_save) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_HHmm");
			DateTimeFormatter chFormatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
			LocalDateTime time = LocalDateTime.now(ZoneId.of("GMT+9")).withNano(0);
			String timeString = time.format( formatter ); //String timeSimple = time.format( DateTimeFormatter.ISO_LOCAL_TIME );
			String fileName = timeString + "_Gomoku.txt";
			String fileIntro = time.format(chFormatter) + " Game" + "\r\n";

			try {
				File newFile = new File(fileName);
				FileOutputStream fos = new FileOutputStream(newFile);
				PrintWriter pw = new PrintWriter(fos);
				pw.write(fileIntro);	pw.flush();
				for (int i=0; i<histindex; i++) {
					if(i%2 == 0)
						pw.write("Black:\t" + history[i].x + "," + history[i].y + "\r\n");
					else pw.write("White:\t" + history[i].x + "," + history[i].y + "\r\n");
					pw.flush();
				}
				if (histindex %2 == 1) pw.write("----- Black wins. -----");
				else pw.write("----- White wins. -----");
				pw.flush();

				pw.close();
				fos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
    if (e.getSource() == display_skeleton) {
			skeletonText = "\r\n        ";
			for (int i=0; i<15; i++){
				for (int j=0; j<15; j++)
					skeletonText += board[i][j] + "  ";
				skeletonText += "\r\n        ";
			}
			skeletonTextArea.setText(skeletonText);
			skeleton.setVisible(true);
		}
  }

  class GameCanvas extends Canvas{
    @Override
		public void paint(Graphics g){
			for (int i=20; i<=580; i+=40)
				g.drawLine(20,i,580,i);
			for (int i=20; i<=580; i+=40)
				g.drawLine(i,20,i,580);

			if (!(mouseX==0 && mouseY == 0) && playerNum == true)
				g.drawOval(mouseX-20,mouseY-20,37,37);
			else if (!(mouseX==0 && mouseY == 0) && playerNum == false)
				g.fillOval(mouseX-20,mouseY-20,37,37);

      if(playoption == 2 && histindex != 0){
        AI = new AI_Gomoku(board, playerNum);
        AIselectpoint = AI.whiteAI();
        board[AIselectpoint.y][AIselectpoint.x] = 2;
    		history[histindex++] = new Point(AIselectpoint.y, AIselectpoint.x);
        mouseY = AIselectpoint.y*40+20; mouseX = AIselectpoint.x*40+20;
        System.out.println(playerNum +": "+ AIselectpoint.y+","+AIselectpoint.x +"; "+ mouseY+","+mouseX);
        playerNum = !playerNum;
        try {
          (Thread.currentThread()).sleep(1000);
        } catch (InterruptedException e) {}
        g.drawOval(mouseX-20,mouseY-20,37,37);
      }
      if(playoption == 3 && histindex != 0 && histindex != 1){
        AI = new AI_Gomoku(board, playerNum);
        AIselectpoint = AI.blackAI();
        board[AIselectpoint.y][AIselectpoint.x] = 1;
    		history[histindex++] = new Point(AIselectpoint.y, AIselectpoint.x);
        mouseY = AIselectpoint.y*40+20; mouseX = AIselectpoint.x*40+20;
        System.out.println(playerNum +": "+ AIselectpoint.y+","+AIselectpoint.x +"; "+ mouseY+","+mouseX);
        playerNum = !playerNum;
        try {
          (Thread.currentThread()).sleep(1000);
        } catch (InterruptedException e) {}
        g.fillOval(mouseX-20,mouseY-20,37,37);
      }

      if (order.equals("newGame")){
        if (playoption == 4){
          System.out.println("Cannot start new game while AI v AI game is in session.");
          order = "";
          return;
        }
        g.clearRect(0,0,630,660);
        for (int i=20; i<=580; i+=40)
  				g.drawLine(20,i,580,i);
  			for (int i=20; i<=580; i+=40)
  				g.drawLine(i,20,i,580);

        gameState = playerNum = true;
        boardX = boardY = mouseX = mouseY = 0;
        for(int i=0; i<15; i++)
          for(int j=0; j<15; j++)
            board[i][j] = 0;
        for(int i=0; i<histindex; i++)
          history[i] = null;
        histindex = 0;
        if (playoption == 3) placestone(true,7,7);
        order = "";
      }
			if (order.equals("openGame")) {
				g.clearRect(0, 0, 650, 650);
				for (int i=20; i<=580; i+=40)
					g.drawLine(20,i,580,i);
				for (int i=20; i<=580; i+=40)
					g.drawLine(i,20,i,580);
				int i = 0, tempX, tempY;
				for (int j=0; j<15; j++) {
					for (int k=0; k<15; k++) {
						board[j][k] = 0;
					}
				}
				while(history[i] != null) {
					if (i%2 == 0) {
						board[history[i].x][history[i].y] = 1;
						tempY = history[i].y * 40;
						tempX = history[i].x * 40;
						g.fillOval(tempY, tempX, 37, 37);
					}
					else {
						board[history[i].x][history[i].y] = 2;
						tempY = history[i].y * 40;
						tempX = history[i].x * 40;
						g.drawOval(tempY, tempX, 37, 37);
					}
					i++;
					try {
						(Thread.currentThread()).sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	//Add replay options -- play, pause, time delay, etc.

				}
				g.drawString(fileName, 410, 595);
				order = "";
				gameState = false;
			}
      if (order.equals("AI_firstmove")) g.fillOval(280,280,37,37);
    }
		@Override
		public void update(Graphics g){
			paint(g);
		}
  }
}
