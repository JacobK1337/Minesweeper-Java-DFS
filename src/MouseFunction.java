import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseFunction implements MouseListener, ActionListener { //this class stores all information about buttons behaviour;
    private final JButton[][] buttons;
    private final int size;
    private final boolean[][] isFlagged;
    private final GamePanel panel;
    private final GameWindow window;
    private final Graph graph;
    MouseFunction(JButton[][] jb, GamePanel gp, GameWindow win, Graph g, int size){
        buttons = jb;
        this.size = size;
        isFlagged = new boolean[size][size];
        panel = gp;
        window = win;
        graph = g;
    }
    @Override
    public void mouseClicked(MouseEvent e) { //we flag a field if we clicked a button with right mouse key;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(buttons[i][j] == e.getSource()){
                    if(SwingUtilities.isRightMouseButton(e)){
                        if(buttons[i][j].isEnabled() && !isFlagged[i][j]){

                            buttons[i][j].setBackground(new Color(244, 252, 8));

                            panel.decrementFlags();
                            panel.decrementUnUsedFields();


                            if(panel.getMap(i,j) == -1) panel.decrementBombs();
                            window.updateScoreSign("Flags: " + panel.flagsOf());
                            if(panel.bombsOf() == 0 && panel.getUnUsedFields() == 0){
                                window.updateScoreSign("You flagged all bombs!");
                                panel.gameEndingInit("You won!");
                            }
                            isFlagged[i][j] = true;

                        }
                        else if(buttons[i][j].isEnabled() && isFlagged[i][j]){
                            buttons[i][j].setBackground(new Color(248, 248, 248));

                            panel.incrementFlags();
                            panel.incrementUnUsedFields();


                            if(panel.getMap(i,j) == -1) panel.incrementBombs();
                            window.updateScoreSign("Flags: " + panel.flagsOf());
                            isFlagged[i][j] = false;

                        }
                    }
                }
            }
        }
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(buttons[i][j] == e.getSource()){
                    if(!isFlagged[i][j]){
                        panel.disableSingleButton(i, j);

                        if(panel.bombsOf() == 0 && panel.getUnUsedFields() == 0){
                            window.updateScoreSign("You flagged all bombs!");
                            panel.gameEndingInit("You won!");
                        }

                        if (panel.getMap(i,j) == 0) graph.dfs(i, j);
                        if (panel.getMap(i,j) == -1) panel.gameEndingTimer();

                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) { }

    }

