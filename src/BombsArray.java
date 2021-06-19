public class BombsArray { //class to store coordinates of a bomb, works the same as HashSolver, but it makes the code look better
    private final int first;
    private final int second;
    BombsArray(int f, int s){
        first = f;
        second = s;
    }
    public int getFirst(){return first;}
    public int getSecond(){return second;}
}
