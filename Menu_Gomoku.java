import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Menu_Gomoku extends JFrame implements ActionListener{ //Runnable
//Declaration
  int selection;
  JPanel northpanel;
    JLabel title, version;
  JPanel centerpanel;
    JPanel center_leftmargin_panel, center_rightmargin_panel;
    JPanel optionpanel;
      JButton opt1_1v1, opt2_blackvAI, opt3_whitevAI, opt4_AIvAI;
  JPanel southpanel;
    JLabel credits;

//Initalization (Constructor)
    Menu_Gomoku(){
      setTitle("Gomoku Menu");
      setSize(600,400);
      setBackground(Color.WHITE);

      northpanel = new JPanel(new GridLayout(3,1));
        title = new JLabel("Gomoku", SwingConstants.CENTER);
          title.setFont(new Font("Serif", Font.PLAIN, 56));
        version = new JLabel("v1.2", SwingConstants.CENTER);
          version.setFont(new Font("Serif", Font.PLAIN, 28));
      centerpanel = new JPanel(new GridLayout(1,3));
        center_leftmargin_panel = new JPanel();
        optionpanel = new JPanel(new GridLayout(4,1));
          opt1_1v1 = new JButton("Player v Player\t\t\t(1v1)");
            opt1_1v1.addActionListener(this);
          opt2_blackvAI = new JButton("Player (Black) v AI\t\t\t(1vAI)");
            opt2_blackvAI.addActionListener(this);
          opt3_whitevAI = new JButton("Player (White) v AI\t\t\t(1vAI)");
            opt3_whitevAI.addActionListener(this);
          opt4_AIvAI = new JButton("AI v AI\t\t\t\t(AIvAI)");
            opt4_AIvAI.addActionListener(this);
          center_rightmargin_panel = new JPanel();
      southpanel = new JPanel(new GridLayout(3,1));
        credits = new JLabel("Developed by: Tony Ryou     ", SwingConstants.RIGHT);


      this.add("North", northpanel);
        northpanel.add(new JPanel()); northpanel.add(title); northpanel.add(version);
      this.add("Center", centerpanel);
        centerpanel.add(center_leftmargin_panel);
        centerpanel.add(optionpanel);
          optionpanel.add(opt1_1v1); optionpanel.add(opt2_blackvAI); optionpanel.add(opt3_whitevAI); optionpanel.add(opt4_AIvAI);
        centerpanel.add(center_rightmargin_panel);
      this.add("South", southpanel);
        southpanel.add(new JPanel()); southpanel.add(credits); southpanel.add(new JPanel());
      setLocationRelativeTo(this);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
    }

//ActionListener
  public void actionPerformed(ActionEvent e){
    if (e.getSource() == opt1_1v1)      {selection = 1; setVisible(false);}
    if (e.getSource() == opt2_blackvAI) {selection = 2; setVisible(false);}
    if (e.getSource() == opt3_whitevAI) {selection = 3; setVisible(false);}
    if (e.getSource() == opt4_AIvAI)    {selection = 4; setVisible(false);}
  }

  public int select(){
    return selection;
  }
}
