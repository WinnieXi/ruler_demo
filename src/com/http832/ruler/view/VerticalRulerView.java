package com.http832.ruler.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.http832.ruler.util.Arithmetic;

public class VerticalRulerView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    
    private SurfaceHolder mSurfaceHolder = null;
    
    private Canvas canvas;
    
    private Paint paint;
    
    //刻度
    private Bitmap staff;
    
    //刻度游标
    private Bitmap pointer;
    
    //初始值
    private float initValue;
    
    //最小值
    private float minValue;
    
    //最大值
    private float maxValue;
    
    //刻度单位最小值
    private float interval;
    
    //标尺移动top距离
    private float staffMoveTop;
    
    //是否初始
    public boolean isInit = true;
    
    public VerticalRulerView(Context context, float initValue, float interval, int mid_kd) {
        super(context);
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        paint = new Paint();
        this.setFocusable(true);
        this.initValue = initValue;
        this.interval = interval;
        this.MID_KD = mid_kd;
    }
    
    /**
     * 初始化 变量
     * @param staffId 
     * @param pointerId
     * @param maxValue 最大值 的刻度
     * @param maxDiv 标尺的所有刻度总数
     * @param minMove 标尺的最小值的偏移
     */
    public void initBitMap(int staffId, int pointerId, int maxValue, int maxDiv, int minMove) {
        staff = BitmapFactory.decodeResource(getResources(), staffId);
        pointer = BitmapFactory.decodeResource(getResources(), pointerId);
        this.MID_KD -= pointer.getHeight();
        
        move = Arithmetic.div(staff.getHeight(), maxDiv, 5);
        this.minValue = -Arithmetic.round(Arithmetic.div(MID_KD, move, 5), 0) + minMove;
        //        float mv = Arithmetic.sub(move, (int)move);
        //        this.initValue -= mv
        this.maxValue = maxValue + minValue - 5;
        staffMoveTop = Arithmetic.div(pointer.getWidth(), 2, 5);
    }
    
    public int getViewWith() {
        return staff.getWidth() + pointer.getWidth() / 2 + 10;
    }
    
    public void initMoveSize(float move) {
        this.move = move;
    }
    
    public void initMinValue(float minValue) {
        this.minValue = minValue;
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        
    }
    
    @Override
    public void run() {
        draw(0);
    }
    
    private float move = 0f; //每次移动的距离
    
    private int RESULT_X = 36; //结果的X轴位置
    
    private int RESULT_Y = 25; //结果的Y轴位置
    
    private int RESULT_SIZE = 24;//结果的字体大小
    
    private int MID_KD = 0;
    
    private float result = 0;
    
    /**  
    * @param direction 方向，-1向下，1向上，0不动  
    */
    public void draw(int direction) {
        //获取画布
        canvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder == null || canvas == null) {
            return;
        }
        canvas.drawColor(Color.WHITE);
        
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        
        if (isInit) {
            result = initValue;
        } else {
            switch (direction) {
                case 1:
                    result = Arithmetic.add(result, interval);
                    if (result > maxValue) {
                        result = maxValue;
                    }
                    break;
                case -1:
                    result = Arithmetic.sub(result, interval);
                    if (result < minValue) {
                        result = minValue;
                    }
                    break;
            }
        }
        initStaff();
        
        canvas.drawBitmap(pointer, 0, MID_KD, paint);
        
        //        Paint reslutPaint = new Paint();
        //        reslutPaint.setColor(Color.BLACK);
        //        reslutPaint.setTextSize(RESULT_SIZE);
        //        canvas.drawText(Float.toString(result) + " ", RESULT_X, RESULT_Y, reslutPaint);
        //解锁画布，提交画好的图像  
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        if (callback != null)
            callback.changeNum(result - minValue);
    }
    
    boolean isFirstDraw = false;
    
    private void initStaff() {
        canvas.drawBitmap(staff, staffMoveTop, -(result * move), paint);
    }
    
    private float inity = 0;
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inity = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float lasty = event.getY();
                if (lasty > inity + 5) {
                    isInit = false;
                    draw(-1);
                    inity = lasty;
                } else if (lasty < inity - 5) {
                    isInit = false;
                    draw(1);
                    inity = lasty;
                }
                break;
        }
        return true;
    }
    
    public float getResult() {
        return result;
    }
    
    public void registerCallback(ICallback cb) {
        callback = cb;
    }
    
    private ICallback callback;
    
    public interface ICallback {
        public void changeNum(float result);
    }
}