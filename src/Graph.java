import java.util.ArrayList;
import java.util.HashMap;


/*Class to create a graph to connect empty fields that are next to each other;
 *empty field - a field which has no bombs around and it is not a bomb;

- It uses some kind of hash - since all vertexes are represented by two integers (i,j),
- we create a hash value for every field - hash = i * size + j, so basically every field is numbered from 0 to size^2, starting
from the top leftmost field;
-If we want to get the (i,j) values from the hash value, we can get it from using HashMap<> and HashSolver class;
-Every HashSolver object contains two values, which makes it seem like a C++ pair<> structure;
*/
public class Graph {
    private final ArrayList<ArrayList<Integer>> adj;
    private final boolean[][] visited;
    private final HashMap<Integer, HashSolver> getFixedVal;
    private final GamePanel panel;

    Graph(int size, GamePanel gp){
        adj = new ArrayList<>();
        for(int i=0; i<size*size; i++){
            adj.add(new ArrayList<>());
        }

        visited = new boolean[size][size];
        getFixedVal = new HashMap<>();
        panel = gp;
        initHashMap(size);

    }

    private void initHashMap(int size){ //initializes HashMap for every field;
        for(int i=0; i<size; i++){
            for(int j = 0; j<size; j++){
                getFixedVal.put(i * size + j, new HashSolver(i,j));
            }
        }
    }
    public void addEdge(int u, int v){ //creates graph edge;
        adj.get(u).add(v);
    }
    public boolean wasVisited(int x, int y){ //checks if a field was visited, used in dfs();
        return visited[x][y];
    }
    public void setVisited(int x, int y){ //sets a field as visited, used in dfs();
        visited[x][y] = true;
    }
    public ArrayList<Integer> getCurrent(int x){ //returns adjacency list of a given vertex;
        return adj.get(x);
    }
    public int getFixedX(int x){ //returns x value from hashed field value;
        return getFixedVal.get(x).getHashX();
    }
    public int getFixedY(int y){ //returns y value from hashed field value;
        return getFixedVal.get(y).getHashY();
    }

    public void dfs(int x, int y){ //dfs function;
        setVisited(x,y);
        panel.disableButtonsAround(x,y);
        int hash = x * panel.sizeOf() + y;
        ArrayList<Integer> currAdj = getCurrent(hash);
        for (Integer integer : currAdj) {
            if (!wasVisited(getFixedX(integer), getFixedY(integer))) dfs(getFixedX(integer), getFixedY(integer));

        }
    }
    public void processGraph(int size){ //processes a graph, creates edges between empty fields;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(panel.getMap(i,j) == 0){
                    if(i-1 >= 0 && panel.getMap(i-1,j) == 0){
                        addEdge(i * size + j, (i-1) * size + j);
                    }

                    if(i-1 >= 0 && j - 1 >= 0 && panel.getMap(i-1,j-1) == 0){
                        addEdge(i * size + j, (i-1) * size + (j-1));
                    }


                    if(i-1 >= 0 && j + 1 < size && panel.getMap(i-1,j+1) == 0){
                        addEdge(i * size + j, (i-1) * size + (j+1));
                    }

                    if(i + 1 < size && panel.getMap(i+1,j) == 0){
                        addEdge(i * size + j, (i+1) * size + j);
                    }

                    if(i + 1 < size && j - 1 >= 0 && panel.getMap(i+1,j-1) == 0){
                        addEdge(i * size + j, (i+1) * size + (j-1));
                    }

                    if(i + 1 < size && j + 1 < size && panel.getMap(i+1,j+1) == 0){
                        addEdge(i * size + j, (i+1) * size + (j+1));
                    }

                    if(j - 1 >= 0 && panel.getMap(i, j-1) == 0){
                        addEdge(i * size + j, i * size + (j-1));
                    }

                    if(j + 1 < size && panel.getMap(i, j+1) == 0){
                        addEdge(i * size + j, i * size + (j+1));
                    }
                }


            }
        }
    }
}
