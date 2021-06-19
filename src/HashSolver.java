public class HashSolver { //HashSolver class, stores 2 values;
    private final int first;
    private final int second;
    HashSolver(int x, int y){
        first = x;
        second = y;
    }
    public int getHashX(){
        return first;
    }
    public int getHashY(){
        return second;
    }
}
