import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.util.Random;


public class GamePanel extends JPanel {
    private final JButton[][] buttons;
    private final int[][] map;
    private final int size;
    private int bombs, flags;
    private final GameWindow window;
    private final MouseFunction mouseActions;
    private final BombsArray[] bombsPos;
    private int tic;
    private Timer timer;
    private int unUsedFields;


    GamePanel(int size, int bombs, GameWindow gw){ //simple constructor, setting the panel size and initializing all components; then processing game map;
        this.setSize(920,600);
        this.setLayout(new GridLayout(size,size));
        buttons = new JButton[size][size];
        map = new int[size][size];
        Graph graph = new Graph(size, this);
        bombsPos = new BombsArray[bombs];
        unUsedFields = size * size;

        this.size = size;
        this.bombs = bombs;
        flags = bombs;

        window = gw;
        mouseActions = new MouseFunction(buttons,this, window, graph, size);

        buttonInit(size);
        generateBombs(bombs, size);
        processMap(size);
        graph.processGraph(size);
    }


    private void buttonInit(int grid) { //function that initializes buttons and their actions
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(new Color(248, 248, 248));

                //all button actions are written in MouseFunction class;
                buttons[i][j].addActionListener(mouseActions);
                buttons[i][j].addMouseListener(mouseActions);
                this.add(buttons[i][j]);

            }
        }
    }
    private void generateBombs(int val, int bound){ //function that generates bombs in random spots;
        Random rand = new Random();
        int x, y;

        for(int i = 0; i < val; i++){

            do{
                x = rand.nextInt(bound);
                y = rand.nextInt(bound);
            } while(map[x][y] == -1);

            bombsPos[i] = new BombsArray(x,y);
            map[x][y] = -1;

        }
    }
    private void processMap(int grid){ //function sets the grid's value; if no bombs are around - map[i][j] = 0; if there are are bombs - map[i][j] = numOfBombs; if it is a bomb - map[i][j] = -1;
        for(int i = 0; i<grid; i++){
            for(int j=0; j < grid; j++){
                if(map[i][j] != -1) map[i][j] = getBombsAround(i,j,grid); //if a grid isn't a bomb, the value will be the number of bombs around this grid;

            }
        }
    }




    private int getBombsAround(int i, int j, int size){ //function checks if there are any bombs around grid;
        int returnValue = 0;
        if(i-1 >= 0 && map[i-1][j] == -1) returnValue ++;

        if(i-1 >= 0 && j - 1 >= 0 && map[i-1][j-1] == -1) returnValue ++;

        if(i-1 >= 0 && j + 1 < size && map[i-1][j+1] == -1) returnValue ++;

        if(i + 1 < size && map[i+1][j] == -1) returnValue ++;

        if(i + 1 < size && j - 1 >= 0 && map[i+1][j-1] == -1) returnValue++;

        if(i + 1 < size && j + 1 < size && map[i+1][j+1] == -1) returnValue++;

        if(j - 1 >= 0 && map[i][j-1] == -1) returnValue++;

        if(j + 1 < size && map[i][j+1] == -1) returnValue ++;

        return returnValue;

    }

    public void disableButtonsAround(int i, int j){ //function that disables buttons around grid[i][j], uses disablesSingleButton(i,j) function;


        if(i - 1 >= 0 && map[i-1][j] != -1){
            disableSingleButton(i-1, j);
        }

        if(i - 1 >= 0 && j - 1 >= 0 && map[i-1][j-1] != -1){
            disableSingleButton(i-1, j-1);
        }


        if(i-1 >= 0 && j + 1 < size && map[i-1][j+1] != -1){
            disableSingleButton(i-1, j+1);
        }

        if(i + 1 < size && map[i+1][j] != -1){
            disableSingleButton(i+1,j);
        }

        if(i + 1 < size && j - 1 >= 0 && map[i+1][j-1] != -1){
            disableSingleButton(i+1,j-1);
        }

        if(i+1 < size && j + 1 < size && map[i+1][j+1] != -1){
            disableSingleButton(i+1,j+1);
        }

        if(j - 1 >= 0 && map[i][j-1] != -1){
            disableSingleButton(i, j-1);
        }

        if(j + 1 < size && map[i][j+1] != -1){
            disableSingleButton(i, j+1);
        }
    }


    public void disableSingleButton(int i, int j){ //function that disables single button, given by coordinates (i,j)
        if(buttons[i][j].isEnabled()) unUsedFields --;
        if(map[i][j] == 0) buttons[i][j].setText("");
        else if(map[i][j] == -1){
            buttons[i][j].setText("");
            buttons[i][j].setBackground(new Color(206, 28, 28));
            buttons[i][j].setEnabled(false);
            return;
        }
        else buttons[i][j].setText(Integer.toString(map[i][j]));
        buttons[i][j].setBackground(new Color(182, 181, 181));
        buttons[i][j].setUI(new MetalButtonUI(){ // setting new color for disabled buttons(by default it is grey);
            @Override
            protected Color getDisabledTextColor() {

                switch(map[i][j]){
                    case 1:
                        return Color.BLUE;
                    case 2:
                        return Color.GREEN;
                    case 3:
                        return Color.RED;
                    case 4:
                        return Color.MAGENTA;
                    case 5:
                        return Color.CYAN;
                    case 6:
                        return Color.YELLOW;
                    case 7:
                        return Color.BLACK;
                    default:
                        return Color.WHITE;
                }
            }
        });

        buttons[i][j].setEnabled(false);
    }
    public void disableAllButtons(){ // function that disables all buttons except bomb fields;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(map[i][j] != -1){
                    disableSingleButton(i,j);
                }
            }
        }
    }
    public void gameEndingInit(String message){ //function that is called when gameEndingTimer ends; it calls endGame screen after 2 seconds;
        timer = new Timer(3000, e->{
            timer.stop();
           window.endGame(message);
        });
        timer.start();
    }
    public void gameEndingTimer(){ //function that is called when you click on a bomb; it reveals other bombs every 100ms;
        window.updateScoreSign("You clicked on a bomb!");
        timer = new Timer(100, e -> {
            if(tic == bombsPos.length){
                timer.stop();
                disableAllButtons();
                gameEndingInit("You lost!");
            }
            else if(tic < bombsPos.length){
                disableSingleButton(bombsPos[tic].getFirst(), bombsPos[tic].getSecond());
                tic++;
            }
        });
        timer.start();
    }

    // Below all important getters and setters;

    public int sizeOf(){
        return size;
    }
    public void incrementFlags(){
        flags++;
    }
    public void decrementFlags(){
        flags--;
    }
    public int flagsOf(){
        return flags;
    }
    public void incrementBombs(){
        bombs ++;
    }
    public void decrementBombs(){
        bombs --;
    }
    public int bombsOf(){
        return bombs;
    }
    public int getMap(int i, int j){
        return map[i][j];
    }
    public void decrementUnUsedFields(){
        unUsedFields--;
    }
    public void incrementUnUsedFields(){
        unUsedFields++;
    }
    public int getUnUsedFields(){
        return unUsedFields;
    }

}

