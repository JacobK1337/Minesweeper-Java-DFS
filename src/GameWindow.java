import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final JButton[] choice;
    private JLabel endSign;
    public JLabel scoreSign;
    GameWindow(){
        this.setSize(1000,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Minesweeper");
        BorderLayout layoutToUse = new BorderLayout();
        layoutToUse.setVgap(0);
        this.setLayout(layoutToUse);
        this.setVisible(true);
        choice = new JButton[3];

        helloSignInit();
        buttonInit();


    }
    private void helloSignInit(){ //initialize welcome-sing;
        JLabel helloSign = new JLabel("Choose the size of the grid and play!");
        helloSign.setBounds(70,0, 500,100);
        helloSign.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        helloSign.setBackground(new Color(0,0,0));
        helloSign.setForeground(new Color(45, 201, 60));
        helloSign.setOpaque(true);
        helloSign.setHorizontalAlignment(JLabel.CENTER);

        this.add(helloSign, BorderLayout.PAGE_START);
    }
    private void endSignInit(String message){ //initialize end-game sign;
        endSign = new JLabel(message);
        endSign.setBounds(70,0, 500, 100);
        endSign.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        endSign.setBackground(new Color(0,0,0));
        endSign.setForeground(new Color(45, 201, 60));
        endSign.setOpaque(true);
        endSign.setHorizontalAlignment(JLabel.CENTER);
    }
    private void buttonInit(){ // function that initializes starting screen buttons;
        for(int i=0; i < 3; i++){
            choice[i] = new JButton();

        }
        choice[0].setText("10x10 / 8 bombs");
        choice[0].setBackground(new Color(86, 160, 189));


        choice[0].addActionListener(l-> makeGamePanel(10, 8));

        choice[1].setText("15x15 / 30 bombs");
        choice[1].setBackground(new Color(238, 160, 160));


        choice[1].addActionListener(l-> makeGamePanel(15,30));

        choice[2].setText("24x24 / 70 bombs");
        choice[2].setBackground(new Color(253, 230, 122));
        choice[2].setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        choice[2].setForeground(new Color(45, 68, 73));

        choice[2].addActionListener(l-> makeGamePanel(24,70));

        this.add(choice[0], BorderLayout.LINE_START); this.add(choice[1], BorderLayout.LINE_END); this.add(choice[2], BorderLayout.CENTER);
    }
    public void updateScoreSign(String updatedString){ // function that updates score after flagging grid;
        scoreSign.setText(updatedString);
    }
    private void makeGamePanel(int s, int b){ //function that creates game map;
        GamePanel gp = new GamePanel(s, b, this);
        scoreSign = new JLabel("Flags: " + b);
        scoreSign.setBounds(70,0, 500, 100);
        scoreSign.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        scoreSign.setForeground(new Color(64, 210, 75));
        scoreSign.setBackground(new Color(0,0,0));
        scoreSign.setOpaque(true);
        scoreSign.setHorizontalAlignment(JLabel.CENTER);

        this.getContentPane().removeAll();
        this.add(scoreSign, BorderLayout.PAGE_START);
        this.add(gp, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    public void endGame(String message){ //function that is used when a player clicks on a bomb;

        endSignInit(message);
        this.getContentPane().removeAll();
        this.add(endSign, BorderLayout.PAGE_START);
        JButton restartButton = new JButton("RESTART");
        restartButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        restartButton.setBackground(new Color(255,255,255));

        restartButton.addActionListener(l-> restartGame());

        this.add(restartButton, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }


    private void restartGame(){ //restarts the game;
        this.getContentPane().removeAll();
        helloSignInit();
        buttonInit();
        this.revalidate();
        this.repaint();
    }
}
