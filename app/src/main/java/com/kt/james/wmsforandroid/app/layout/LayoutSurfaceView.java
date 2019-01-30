package com.kt.james.wmsforandroid.app.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.view.MotionEvent.ACTION_DOWN;

public class LayoutSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    private static int GRIDW_SIZE=10; //列数
    private static int GRIDH_SIZE=15; //行数
    private static int startW=10,startH=10; //间距
    private float titleW; //宽度
    private float titleH; //高度
    private int screenW,screenH; // 屏幕宽高
    private Canvas canvas; //画布
    private SurfaceHolder sfh;

    public final static int STATE_EMPTY = 0x00;
    public final static int STATE_FILL = 0x01;
    public final static int STATE_EDIT = 0x02;

    private int[][] mPositions = new int[10][15];

    private OnItemClickListener mListener;

    public LayoutSurfaceView(Context context) {
        super(context);
        init();
    }

    public LayoutSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LayoutSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void init() {
        sfh=this.getHolder();
        sfh.addCallback(this);
    }

    private int getPositionX(float x) {
        float realX = x - startW;
        for (int i = 0; i < GRIDW_SIZE; i++) {
            if (realX < (i + 1) * titleW) {
                return i ;
            }
        }
        return -1;
    }

    private int getPositionY(float y) {
        float realY = y - startH;
        for (int i = 0; i < GRIDH_SIZE; i++) {
            if (realY < (i + 1) * titleH) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int posX = getPositionX(event.getX());
        int posY = getPositionY(event.getY());
        switch (event.getAction()) {
            case ACTION_DOWN:
                if (mListener != null) {
                    mListener.onClick(posX, posY, mPositions[posX][posY]);
                }
                break;
        }
        return true;
    }

    @Override
    public void run() {
      while (true) {
          try {
              screenW=this.getWidth();
              screenH=this.getHeight();
              titleW=(screenW-2*startW)/GRIDW_SIZE;
              titleH=(screenH-2*startH)/GRIDH_SIZE;
              canvas=sfh.lockCanvas();
              canvas.drawColor(Color.WHITE);
              Paint paint=new Paint();
              paint.setColor(Color.BLACK);
              paint.setStrokeWidth(3);
              paint.setStyle(Paint.Style.STROKE);
              float startX=0,startY=0;
              for (int i=0;i<=GRIDW_SIZE;i++){
                  startX=startW+i*titleW;
                  startY=startH;
                  canvas.drawLine(startX,startY,startX,screenH-startH,paint);
              }
              for (int j=0;j<=GRIDH_SIZE;j++){
                  startX=startW;
                  startY=startH+j*titleH;
                  canvas.drawLine(startX,startY,screenW-startW-5,startY,paint);
              }
              for (int i=0; i<GRIDH_SIZE; i++) {
                  for (int j = 0; j < GRIDH_SIZE; j++) {
                      if (mPositions[i][j] == STATE_FILL) {
                          paint.setColor(Color.RED);
                          paint.setStyle(Paint.Style.FILL);
                          paint.setStrokeWidth(0);
                          int left = (int) (startW + i * titleW);
                          int right = (int) (left + titleW);
                          int top = (int) (startH + j * titleH);
                          int bottom = (int) (top + titleH);
                          canvas.drawRect(left, top, right, bottom, paint);
                      } else if (mPositions[i][j] == STATE_EDIT) {
                          paint.setColor(Color.GREEN);
                          paint.setStyle(Paint.Style.FILL);
                          paint.setStrokeWidth(0);
                          int left = (int) (startW + i * titleW);
                          int right = (int) (left + titleW);
                          int top = (int) (startH + j * titleH);
                          int bottom = (int) (top + titleH);
                          canvas.drawRect(left, top, right, bottom, paint);
                      }
                  }
              }
              Thread.sleep(50);
          } catch (Exception e) {

          } finally {
              if (canvas!=null){
                  sfh.unlockCanvasAndPost(canvas);
              }
          }
      }
    }

    public void setState(int x, int y, int state) {
        mPositions[x][y] = state;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int x, int y, int state);
    }

}
