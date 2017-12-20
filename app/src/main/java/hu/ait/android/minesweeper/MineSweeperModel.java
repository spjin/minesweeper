package hu.ait.android.minesweeper;
import java.util.Random;

/**
 * Created by sarahjin on 9/25/17.
 */

public class MineSweeperModel {
    private static MineSweeperModel mineSweeperModel = null;

    private MineSweeperModel(){
        this.setBombs();
        this.setNumbers();
    }

    public static MineSweeperModel getInstance(){
        if(mineSweeperModel == null){
            mineSweeperModel = new MineSweeperModel();
        }
        return mineSweeperModel;
    }

    public static final int EMPTY = 0;
    public static final int MINE = -1;
    public static final int FLAG = -2;
    public static final int WRONG = -3;

    private int[][] model = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };

    private boolean[][] checked = {
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
    };

    private boolean flagMode = false;
    private boolean gameOver = false;
    private int numBombs = 0;

    public int getNumBombs() {
        return numBombs;
    }

    public void foundABomb() {
        this.numBombs++;
    }


    public void setBombs(){
        int bombs = 0;
        while(bombs < 3){
            int i = new Random(System.currentTimeMillis()).nextInt(5);
            int j = new Random(System.currentTimeMillis()).nextInt(5);

            if(model[i][j] == EMPTY){
                setFieldContent(i, j, MINE);
                bombs++;
            }
        }
    }

    public void setNumbers(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(model[i][j] != MINE){
                    int upperLeft = checkNeighbors(i-1,j-1);
                    int upper = checkNeighbors(i-1, j);
                    int upperRight = checkNeighbors(i-1, j+1);
                    int right = checkNeighbors(i, j+1);
                    int lowerRight = checkNeighbors(i+1, j+1);
                    int lower = checkNeighbors(i+1, j);
                    int lowerLeft = checkNeighbors(i+1, j-1);
                    int left = checkNeighbors(i, j-1);

                    int numNeighbors = upperLeft + upper + upperRight + right+lowerRight + lower + lowerLeft + left;
                    setFieldContent(i, j, numNeighbors);
                    }
                }
            }
        }

    public void setGameOver(){
        gameOver = true;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public int checkNeighbors(int x, int y){
        if(x < 0 || x >= 5 || y < 0 || y >= 5){
            return 0;
        }
        else{
            if(model[x][y] == MINE){
                return 1;
            }
            return 0;
        }
    }

    public int getFieldContent(int x, int y){
        return model[x][y];
    }

    public void setFieldContent(int x, int y, int content){
        model[x][y] = content;
    }

    public boolean getChecked(int x, int y){
        return checked[x][y];
    }

    public void setChecked(int x, int y){
        checked[x][y] = true;
    }

    public boolean isFlagMode() {
        return flagMode;
    }

    public void setFlagMode(boolean flagMode) {
        this.flagMode = flagMode;
    }

    public void resetGame(){
        this.mineSweeperModel = new MineSweeperModel();
        gameOver = false;

    }
}
