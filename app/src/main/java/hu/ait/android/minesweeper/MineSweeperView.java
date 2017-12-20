package hu.ait.android.minesweeper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sarahjin on 9/25/17.
 */

public class MineSweeperView extends View {
    Paint paintBg;
    Paint paintLine;
    Paint paintNum;

    private Bitmap bitmapMine;
    private Bitmap bitmapFlag;
    private Bitmap bitmapCat;

    public final String WINNER_TEXT = getContext().getString(R.string.text_winner);
    public final String LOSER_FLAG = getContext().getString(R.string.text_flag_loss);
    public final String LOSER_MINE = getContext().getString(R.string.text_mine_loss);


    public MineSweeperView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.GRAY);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStrokeWidth(5);
        paintLine.setStyle(Paint.Style.STROKE);

        paintNum = new Paint();
        paintNum.setColor(Color.YELLOW);
        paintNum.setTextSize(75);
        paintNum.setStyle(Paint.Style.FILL);

        bitmapMine = BitmapFactory.decodeResource(getResources(), R.drawable.mine);
        bitmapCat = BitmapFactory.decodeResource(getResources(), R.drawable.sadcat);
        bitmapFlag = BitmapFactory.decodeResource(getResources(), R.drawable.daisy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGameArea(canvas);

        drawItems(canvas);
    }

    private void drawGameArea(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        canvas.drawLine(getWidth()/5, 0, getWidth()/5, getHeight(), paintLine);
        canvas.drawLine(2*getWidth()/5, 0, 2*getWidth()/5, getHeight(), paintLine);
        canvas.drawLine(3*getWidth()/5, 0, 3*getWidth()/5, getHeight(), paintLine);
        canvas.drawLine(4*getWidth()/5, 0, 4*getWidth()/5, getHeight(), paintLine);

        canvas.drawLine(0, getHeight()/5, getWidth(), getHeight()/5, paintLine);
        canvas.drawLine(0, 2*getHeight()/5, getWidth(), 2*getHeight()/5, paintLine);
        canvas.drawLine(0, 3*getHeight()/5, getWidth(), 3*getHeight()/5, paintLine);
        canvas.drawLine(0, 4*getHeight()/5, getWidth(), 4*getHeight()/5, paintLine);
    }

    private void drawItems(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(MineSweeperModel.getInstance().getChecked(i, j)){
                    if (MineSweeperModel.getInstance().getFieldContent(i, j) ==
                            MineSweeperModel.MINE){

                        canvas.drawBitmap(bitmapMine, i*getWidth()/5 + getWidth()/50, j*getHeight()/5 + getHeight()/50, null);
                    }
                    else if (MineSweeperModel.getInstance().getFieldContent(i, j) ==
                            MineSweeperModel.FLAG) {

                        canvas.drawBitmap(bitmapFlag, i*getWidth()/5 + getWidth()/50, j*getHeight()/5 + getHeight()/50, null);
                    }
                    else if(MineSweeperModel.getInstance().getFieldContent(i, j) ==
                            MineSweeperModel.WRONG){
                        canvas.drawBitmap(bitmapCat, i*getWidth()/5, j*getHeight()/5, null);
                    }
                    else{
                        canvas.drawText(String.valueOf(MineSweeperModel.getInstance().getFieldContent(i, j)),
                                i*getWidth()/5 + getWidth() / 12,
                                j*getHeight()/5 + getHeight() / 7, paintNum);
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN && !MineSweeperModel.getInstance().isGameOver()) {

            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);

            if(!MineSweeperModel.getInstance().getChecked(tX, tY)){
                MineSweeperModel.getInstance().setChecked(tX, tY);

            int val = MineSweeperModel.getInstance().getFieldContent(tX, tY);

                if(MineSweeperModel.getInstance().isFlagMode()){
                    if(val == MineSweeperModel.MINE){
                        MineSweeperModel.getInstance().setFieldContent(tX, tY, MineSweeperModel.FLAG);
                        MineSweeperModel.getInstance().foundABomb();
                        if(MineSweeperModel.getInstance().getNumBombs() == 3){
                           gameEnd(WINNER_TEXT);
                        }
                    }
                    else{
                        MineSweeperModel.getInstance().setFieldContent(tX, tY, MineSweeperModel.WRONG);
                        gameEnd(LOSER_FLAG);
                    }
                }
                else{
                    if(val == MineSweeperModel.MINE){
                        gameEnd(LOSER_MINE);
                    }
                }

                invalidate();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void gameEnd(String gameStatus){
        MineSweeperModel.getInstance().setGameOver();
        ((MainActivity)getContext()).showGameStatus(gameStatus);
    }

    public void clear(){
        MineSweeperModel.getInstance().resetGame();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int x, int y, int width, int height){
        super.onSizeChanged(x, y, width, height);
        bitmapCat = Bitmap.createScaledBitmap(bitmapCat, getWidth()/5, getHeight()/5, false);
        bitmapMine = Bitmap.createScaledBitmap(bitmapMine, getWidth()/6, getHeight()/6, false);
        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag, getWidth()/6, getHeight()/6, false);
    }
}