package com.fsd.logodesign.drawing_types;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by hamza on 5/20/2016.
 */
public class DrawView extends View implements View.OnTouchListener {

    private Canvas mCanvas;
    private Path    mPath;
    private Paint       mPaint;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    public int fontColor;
    public int markerSize;


    public DrawView(Context context , int color , int markerSize)   {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.markerSize = markerSize;
        this.fontColor = color;
        mPaint.setStrokeWidth(markerSize);
        mCanvas = new Canvas();
        mPath = new Path();
        paths.add(mPath);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(fontColor);
        mPaint.setStrokeWidth(markerSize);
        for (Path p : paths){
            canvas.drawPath(p, mPaint);
        }
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        mPath = new Path();
        paths.add(mPath);
    }

    public void onClickUndo () {
        if (paths.size()>0)  {
            undonePaths.add(paths.remove(paths.size()-1));
            invalidate();
        } else  {
         //   Log.i("undo", "Undo elsecondition");
        }
    }

    public void onClickRedo (){
        if (undonePaths.size()>0)  {
            paths.add(undonePaths.remove(undonePaths.size()-1));
            invalidate();
        }
        else  {
          //  Log.i("undo", "Redo elsecondition");
        }
    }

    public boolean onTouch(View arg0, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
}
