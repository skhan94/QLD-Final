package com.fsd.logodesign.drawing_types;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.fsd.logodesign.Custom_Designing;



/**
 * Created by hamza on 5/4/2016.
 */
public class FontView extends View {

    private static final int INVALID_POINTER_ID = -1;

    private final String TAG = "TESTESTEST";
    private  String mText;

    public Drawable mImage;

    public int getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(int pos)
    {
        this.currentPosition=pos;
    }

    private int currentPosition;
    public static int getCurrentShapePos = 0;  // Used because i'll access them from MainActivity

    public void setmImage(Drawable image) {
        mImage = image;
    }

    public BitmapDrawable getmImage() {
        return (BitmapDrawable) mImage;
    }

    public String getmText() {
        return mText;
    }

    // width and height of original image
    public float mImageWidth;
    public float mImageHeight;

    // when image is scaled, we use this to calculate the bounds of the image
    public int mImageWidthScaled;
    public int mImageHeightScaled;

    public float mPosX = 150;
    public float mPosY = 150;

    public float getRotateAngle() {
        return rotateAngle;
    }

    float rotateAngle;
    public float getmPosX() {
        return mPosX;
    }

    public void setmPosX(float mPosX) {
        this.mPosX = mPosX;
    }

    public float getmPosY() {
        return mPosY;
    }

    public void setmPosY(float mPosY) {
        this.mPosY = mPosY;
    }

    private float mLastTouchX;
    private float mLastTouchY;

    private Paint mBorderLeftLine;
    private Paint mBorderTopLine;
    private Paint mBorderRightLine;
    private Paint mBorderBottomLine;

    int layerID;

    private int mActivePointerId = INVALID_POINTER_ID;
    private ScaleGestureDetector mScaleDetector;
    GestureDetector mGestureDetector;
    public float getmScaleFactor() {
        return mScaleFactor;
    }

    public void setmScaleFactor(float mScaleFactor) {
        this.mScaleFactor = mScaleFactor;
    }

    public float mScaleFactor = 1f;

    // this is to tell Style what view number I am in the array.
    private int mNumberView;

    // this is what draws the red line around the TouchView to tell the user
    // this one is currently selected
    private boolean mSelected = false;


    public void setmSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }


    // this is to keep a reference to the original image, so when we ship, we can tar
    // the images that are needed.
    private String mUri;

    public void setImageLocation(String path) {
        this.mUri = path;
    }

    int foreground, insideCircle;
    public Typeface typeface;

    public int getLayerID() {
        return layerID;
    }

    public void setLayerID(int layerID) {
        this.layerID = layerID;
    }

    public int getForegroundValue() {
        return foreground;
    }

    public void setForeground(int foreground) {
        this.foreground = foreground;
    }

    public int getInsideCircle() {
        return insideCircle;
    }

    public void setInsideCircle(int insideCircle) {
        this.insideCircle = insideCircle;
    }

    int uniqueid;

    public int getUniqueID() {
        return fpos;
    }

    public void setUniqueID(int uniqueID) {
        this.fpos = uniqueID;
    }
    int fpos;

    int FontPosition=0;
      public int getFontPosition()
      {
          return FontPosition;
      }
    public void setFontText(String text)
    {
        this.mText=text;
    }
    public String getFontText()
    {
        return mText;
    }
    public static int font_dialog_state=0;


    public FontView(Context context, String text, BitmapDrawable image, int count, float scaleFactor, int pos, float posX, float posY, int foreground, int insideCircle , float rotateAngle , Typeface typeface,int FontPosition) {
        super(context);
        this.mImage = image;
        mImageWidth = image.getBitmap().getWidth();
        mImageHeight = image.getBitmap().getHeight();
        mImageWidthScaled = (int) (mImageWidth * scaleFactor);
        mImageHeightScaled = (int) (mImageHeight * scaleFactor);
        this.mText = text;
        this.mNumberView = count;
//        this.mStyle = style;
        this.mScaleFactor = scaleFactor;
        this.currentPosition = pos;
        if (posX != 0 & posY != 0) {
            mPosX = posX;
            mPosY = posY;
        }
        this.foreground = foreground;
        this.insideCircle = insideCircle;
        this.rotateAngle = rotateAngle;
        this.typeface = typeface;
        this.FontPosition=FontPosition;
        init(context);
    }

    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
       mGestureDetector = new GestureDetector(context, new GestureTap());
        mGestureDetector.setOnDoubleTapListener(new DoubleTap());
        mBorderLeftLine = new Paint();
        mBorderRightLine = new Paint();
        mBorderBottomLine = new Paint();
        mBorderTopLine = new Paint();
        setBorderParams(Color.RED, 2);

    }

    private void setBorderParams(int color, float width) {
        mBorderLeftLine.setColor(color);
        mBorderLeftLine.setStrokeWidth(width);
        mBorderRightLine.setColor(color);
        mBorderRightLine.setStrokeWidth(width);
        mBorderBottomLine.setColor(color);
        mBorderBottomLine.setStrokeWidth(width);
        mBorderTopLine.setColor(color);
        mBorderTopLine.setStrokeWidth(width);
        mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight());

    }

    Canvas canvas;
    public boolean rotateAllow = false;
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    //    Custom_Designing.selectedposition=layerID;
        getCurrentShapePos = currentPosition;
        mImageWidth=mImage.getIntrinsicWidth();
        mImageWidthScaled = (int) (mImageWidth * mScaleFactor);
        mImageHeightScaled = (int) (mImageHeight * mScaleFactor);

        //  Log.e("UpdateSeek" , "currentPosition TouchView.getCurrentPos : " + currentPosition);
      //  Custom_Designing.selectedposition=getCurrentShapePos;
        this.canvas = canvas;
        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        if(rotateAllow){
            canvas.rotate(rotateAngle ,  mImage.getIntrinsicWidth() /2.0f ,mImage.getIntrinsicHeight()/2.0f );

        }
        mImage.draw(canvas);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        textPaint.setARGB(255, 0, 0, 0);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(typeface);
        if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
            textPaint.setTextSize(45);
        }
        else
        {
            textPaint.setTextSize(35);

        }
        textPaint.setColor(insideCircle);
//
//        canvas.drawText(mText,mImage.getIntrinsicWidth()/2.0f ,mImage.getIntrinsicHeight()/ 1.6f, textPaint);


        canvas.drawText(mText, mImage.getIntrinsicWidth() / 2.0f, mImage.getIntrinsicHeight() / 1.6f, textPaint);
//        canvas.drawText("bar.getName()",mPosX,mPosY, textPaint);
//        canvas.restore();



        if (mSelected) {
            canvas.drawLine(0,
                    0,
                    mImage.getIntrinsicWidth(),
                    0,
                    mBorderTopLine);
            canvas.drawLine(0, mImage.getIntrinsicHeight(),
                    mImage.getIntrinsicWidth(),
                    mImage.getIntrinsicHeight(),
                    mBorderBottomLine);
            canvas.drawLine(0,
                    0,
                    0,
                    mImage.getIntrinsicHeight(),
                    mBorderLeftLine);
            canvas.drawLine(mImage.getIntrinsicWidth(),
                    0,
                    mImage.getIntrinsicWidth(),
                    mImage.getIntrinsicHeight(),
                    mBorderRightLine);
        }
        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //ImageView view = (ImageView) v;

        getCurrentShapePos=currentPosition;
        Custom_Designing.selectedposition=fpos;
        Custom_Designing.isEraserAllow=false;
        Custom_Designing.click=false;
        Custom_Designing.setupVisibility();


        boolean intercept = false;
        boolean result = mScaleDetector.onTouchEvent(event);

        // result is always true here, so I need another way to check for a detected scaling gesture
        boolean isScaling = result = mScaleDetector.isInProgress();
        if(!isScaling)
        {
            mGestureDetector.onTouchEvent(event);
            intercept = gesture(event);
          //  Log.e("Testing","ScaleDetector isinProgress " + mScaleDetector.isInProgress());
        }
        else
        {
            mScaleDetector.onTouchEvent(event);

            intercept = gesture(event);
          // intercept= ScaleGesture(event);
        }

        //boolean defaultResult = onTouchEvent(event);
     /*   Log.d("onTouchEventComponent" , "FontView Clicked");
        Log.d("onTouchEvent", "currentPosition : " + currentPosition);
        Log.d("onTouchEvent", "mNumberView : " + mNumberView);*/

      /*  switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
//                Log.d("onTouchEvent" , " ACTION_DOWN : " + currentPosition);
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                mActivePointerId = event.getPointerId(0);
                if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX + mImageWidthScaled)
                        && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled))) {
                    Log.i(TAG, "My view is here: " + mNumberView);
                    intercept = true;
                    mSelected = true;
//                    mStyle.setmCurrentView(mNumberView);
                    Log.d("onTouchEvent", " ACTION_DOWN IF : " + mNumberView);
                    Log.d("onTouchEventComponent" , "FontView Clicked inside onDown If ");
                    for (View view : Custom_Designing.mFontsArray) {
                        FontView shapeView = (FontView) view;

                        Log.d("onTouchEvent", "**** Shapes Array Current Pos : " + shapeView.currentPosition);
                        if (shapeView.currentPosition != currentPosition) {
                            shapeView.mSelected = false;
                            shapeView.invalidate();
                        }else
                            Custom_Designing.setUpCurrentRadioItem(layerID);
                    }

                    for (View view : Custom_Designing.mShapesArray) {
                        ShapeView shapV = (ShapeView) view;
                        shapV.setmSelected(false);
                        shapV.invalidate();
                    }
                    for (View view : Custom_Designing.mGalleryArray) {
                        GalleryImageView imgView = (GalleryImageView) view;
                        imgView.setmSelected(false);
                        imgView.invalidate();
                    }

                    Custom_Designing.selectedComponent = 1;
                } else {
                    Log.d("onTouchEvent", " ACTION_DOWN ELSE : " + mNumberView);
                    mSelected = false;
//                    onDraw(canvas);
                    Log.d("onTouchEventComponent" , "FontView Clicked inside onDown Else");
                    Custom_Designing.selectedComponent = -1;
                    invalidate();
                }

                Log.i(TAG, "Action down");
                Log.i(TAG, "x is: " + mLastTouchX);
                Log.i(TAG, "y is: " + mLastTouchY);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("onTouchEvent", "ACTION_UP  : " + currentPosition);

                setFocusable(false);
                mImageWidthScaled = (int) (mImageWidth * mScaleFactor);
                mImageHeightScaled = (int) (mImageHeight * mScaleFactor);
            *//*          mPosX = (int) event.getX();
            mPosY = (int) event.getY();*//*
                mActivePointerId = INVALID_POINTER_ID;

                // stop the red rectangle from being drawn around the View
//                mSelected = false;
                invalidate();

                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("onTouchEvent", "ACTION_CANCEL  : " + currentPosition);
//                Log.d("onTouchEvent" , "  : " + currentPosition);
                mActivePointerId = INVALID_POINTER_ID;
                // stop the red rectangle from being drawn around the View
                mSelected = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("Testing", "ACTION_MOVE  : " + currentPosition);
                Log.e("Testing","SCALE DETECTOR " + mScaleDetector.isInProgress());
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);
                if (!mScaleDetector.isInProgress()) {
                    Log.e("Testing","SCALE DETECTOR FALSE");
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;

                    // Invalidate to request a redraw
                    invalidate();
                    Log.e("Testing","GESTURE DETECTOR " + mGestureDetector);
                  //  if(mGestureDetector == null){
                     *//*   mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
                            @Override
                            public void onLongPress(MotionEvent e) {
                                super.onLongPress(e);
                                Log.e("Testing", "Long Pressed " + e.toString());
                                //handle double tap
                                int x = (int) e.getX();
                                int y = (int) e.getY();
                            }

                            @Override
                            public boolean onDoubleTap(MotionEvent event) {
                                Log.e("Testing", "DOUBLE TAP " +event.toString());
                                //handle double tap
                                int x = (int) event.getX();
                                int y = (int) event.getY();
                                return true;
                            }
                        });


                        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                            @Override
                            public boolean onSingleTapConfirmed(MotionEvent e) {
                                Log.e("Testing","onSIngleTapConfirmed");
                                return false;
                            }

                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                Log.e("Testing","onDoubleTap");
                                return true;
                            }

                            @Override
                            public boolean onDoubleTapEvent(MotionEvent e) {
                                Log.e("Testing","onDOubleTapEvent");
                                return false;
                            }
                        });
                  //  }
                    mGestureDetector.onTouchEvent(event);*//*
                 //   mGestureDetector.onTouchEvent(event);

                   // }
                } else {
                    Log.i(TAG, "Now scaling is happening");
                }
                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d("onTouchEvent", " ACTION_POINTER_UP : " + currentPosition);
                final int pointerIndex2 = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK)
                        >> MotionEvent.ACTION_POINTER_ID_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex2);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex2 == 0 ? 1 : 0;
                    mLastTouchX = event.getX(newPointerIndex);
                    mLastTouchY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            default:
                //return defaultResult;
        }*/
        return intercept;
    }

  /*  public boolean ScaleGesture(MotionEvent event)
    {
        boolean intercept=false;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
//                Log.d("onTouchEvent" , " ACTION_DOWN : " + currentPosition);
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                mActivePointerId = event.getPointerId(0);
                if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX + mImageWidthScaled)
                        && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled))) {
                    Log.i(TAG, "My view is here: " + mNumberView);
                    //intercept = true;
                    mSelected = true;
//                    mStyle.setmCurrentView(mNumberView);
                //    Log.d("onTouchEvent", " ACTION_DOWN IF : " + mNumberView);
                //    Log.d("onTouchEventComponent" , "FontView Clicked inside onDown If ");
                    for (View view : Custom_Designing.mFontsArray) {
                        FontView shapeView = (FontView) view;

                  //      Log.d("onTouchEvent", "**** Shapes Array Current Pos : " + shapeView.currentPosition);
                        if (shapeView.currentPosition != currentPosition) {
                            shapeView.mSelected = false;
                            shapeView.invalidate();
                        }else
                            Custom_Designing.setUpCurrentRadioItem(layerID);
                    }

                    for (View view : Custom_Designing.mShapesArray) {
                        ShapeView shapV = (ShapeView) view;
                        shapV.setmSelected(false);
                        shapV.invalidate();
                    }
                    for (View view : Custom_Designing.mGalleryArray) {
                        GalleryImageView imgView = (GalleryImageView) view;
                        imgView.setmSelected(false);
                        imgView.invalidate();
                    }

                    Custom_Designing.selectedComponent = 1;
                } else {
                //    Log.d("onTouchEvent", " ACTION_DOWN ELSE : " + mNumberView);
                    mSelected = false;
//                    onDraw(canvas);
                //    Log.d("onTouchEventComponent" , "FontView Clicked inside onDown Else");
                    Custom_Designing.selectedComponent = -1;
                    invalidate();
                }

              //  Log.i(TAG, "Action down");
              //  Log.i(TAG, "x is: " + mLastTouchX);
              //  Log.i(TAG, "y is: " + mLastTouchY);
                break;
            case MotionEvent.ACTION_UP:
              //  Log.d("onTouchEvent", "ACTION_UP  : " + currentPosition);

                setFocusable(false);
                mImageWidthScaled = (int) (mImageWidth * mScaleFactor);
                mImageHeightScaled = (int) (mImageHeight * mScaleFactor);
            *//*          mPosX = (int) event.getX();
            mPosY = (int) event.getY();*//*
                mActivePointerId = INVALID_POINTER_ID;

                // stop the red rectangle from being drawn around the View
//                mSelected = false;
                invalidate();

                break;
            case MotionEvent.ACTION_CANCEL:
              //  Log.d("onTouchEvent", "ACTION_CANCEL  : " + currentPosition);
//                Log.d("onTouchEvent" , "  : " + currentPosition);
                mActivePointerId = INVALID_POINTER_ID;
                // stop the red rectangle from being drawn around the View
                mSelected = false;
                break;
            case MotionEvent.ACTION_MOVE:
             //   Log.e("Testing", "ACTION_MOVE  : " + currentPosition);
             //   Log.e("Testing","SCALE DETECTOR " + mScaleDetector.isInProgress());
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);
                if (!mScaleDetector.isInProgress()) {
               //     Log.e("Testing","SCALE DETECTOR FALSE");
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;

                    // Invalidate to request a redraw
                    invalidate();
               //     Log.e("Testing","GESTURE DETECTOR " + mGestureDetector);

                } else {
                 //   Log.i(TAG, "Now scaling is happening");
                }
                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            case MotionEvent.ACTION_POINTER_UP:
           //     Log.d("onTouchEvent", " ACTION_POINTER_UP : " + currentPosition);
                final int pointerIndex2 = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK)
                        >> MotionEvent.ACTION_POINTER_ID_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex2);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex2 == 0 ? 1 : 0;
                    mLastTouchX = event.getX(newPointerIndex);
                    mLastTouchY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            default:
                //return defaultResult;
        }
        return true;
    }*/

    public boolean gesture(MotionEvent event)
    {
      /*  mImageWidthScaled = (int) (mImageWidth * mScaleFactor);
        mImageHeightScaled = (int) (mImageHeight * mScaleFactor);
*/

        boolean intercept=false;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mImageWidthScaled = (int) (mImageWidth * mScaleFactor);
                mImageHeightScaled = (int) (mImageHeight * mScaleFactor);
//                Log.d("onTouchEvent" , " ACTION_DOWN : " + currentPosition);
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                mActivePointerId = event.getPointerId(0);
               /* if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX + mImageWidthScaled)
                        && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled)))*/


                if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX + mImageWidthScaled)
                        && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled))){
             //       Log.i(TAG, "My view is here: " + mNumberView);
                    intercept = true;
                    mSelected = true;
//                    mStyle.setmCurrentView(mNumberView);
                //    Log.d("onTouchEvent", " ACTION_DOWN IF : " + mNumberView);
                //    Log.d("onTouchEventComponent" , "FontView Clicked inside onDown If ");
                    for (View view : Custom_Designing.mFontsArray) {
                        FontView shapeView = (FontView) view;

                  //      Log.d("onTouchEvent", "**** Shapes Array Current Pos : " + shapeView.currentPosition);
                        if (shapeView.currentPosition != currentPosition) {
                            shapeView.mSelected = false;
                            shapeView.invalidate();
                        }else
                            Custom_Designing.setUpCurrentRadioItem(layerID);
                    }

                    for (View view : Custom_Designing.mShapesArray) {
                        ShapeView shapV = (ShapeView) view;
                        shapV.setmSelected(false);
                        shapV.invalidate();
                    }
                    for (View view : Custom_Designing.mGalleryArray) {
                        GalleryImageView imgView = (GalleryImageView) view;
                        imgView.setmSelected(false);
                        imgView.invalidate();
                    }

                    Custom_Designing.selectedComponent = 1;
                } else {
                //    Log.d("onTouchEvent", " ACTION_DOWN ELSE : " + mNumberView);
                    mSelected = false;
//                    onDraw(canvas);
                //    Log.d("onTouchEventComponent" , "FontView Clicked inside onDown Else");
                    Custom_Designing.selectedComponent = -1;
                    invalidate();
                }

            /*    Log.i(TAG, "Action down");
                Log.i(TAG, "x is: " + mLastTouchX);
                Log.i(TAG, "y is: " + mLastTouchY);*/
                break;
            case MotionEvent.ACTION_UP:
//                Log.d("onTouchEvent", "ACTION_UP  : " + currentPosition);

                setFocusable(false);
                mImageWidthScaled = (int) (mImageWidth * mScaleFactor);
                mImageHeightScaled = (int) (mImageHeight * mScaleFactor);
            /*          mPosX = (int) event.getX();
            mPosY = (int) event.getY();*/
                mActivePointerId = INVALID_POINTER_ID;

                // stop the red rectangle from being drawn around the View
//                mSelected = false;
                invalidate();

                break;
            case MotionEvent.ACTION_CANCEL:
//                Log.d("onTouchEvent", "ACTION_CANCEL  : " + currentPosition);
//                Log.d("onTouchEvent" , "  : " + currentPosition);
                mActivePointerId = INVALID_POINTER_ID;
                // stop the red rectangle from being drawn around the View
                mSelected = false;
                break;
            case MotionEvent.ACTION_MOVE:
              //  Log.e("Testing", "ACTION_MOVE  : " + currentPosition);
             //   Log.e("Testing","SCALE DETECTOR " + mScaleDetector.isInProgress());
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);
                if (!mScaleDetector.isInProgress()) {
              //      Log.e("Testing","SCALE DETECTOR FALSE");
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;

                    // Invalidate to request a redraw
                    invalidate();
                //    Log.e("Testing", "GESTURE DETECTOR " + mGestureDetector);



                } else {
//                    Log.i(TAG, "Now scaling is happening");
                }
                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            case MotionEvent.ACTION_POINTER_UP:
//                Log.d("onTouchEvent", " ACTION_POINTER_UP : " + currentPosition);
                final int pointerIndex2 = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK)
                        >> MotionEvent.ACTION_POINTER_ID_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex2);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex2 == 0 ? 1 : 0;
                    mLastTouchX = event.getX(newPointerIndex);
                    mLastTouchY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            default:
                //return defaultResult;
        }
        return intercept;
    }

     class GestureTap extends GestureDetector.SimpleOnGestureListener
     {

         @Override
         public void onLongPress(MotionEvent e) {
             super.onLongPress(e);
          //   Log.e("Testing","GESTURE DETECTOR LISTENER LongPressed");
         }

         @Override
         public boolean onDoubleTap(MotionEvent e) {
           //  return super.onDoubleTap(e);
           //  Log.e("Testing","GESTURE DETECTOR LISTENER onDoubleTap1");
             return  true;
         }
     }
     class DoubleTap implements GestureDetector.OnDoubleTapListener
     {


         @Override
         public boolean onSingleTapConfirmed(MotionEvent e) {
           //  Log.e("Testing","GESTURE DETECTOR LISTENER onSingleTapConfirmed");
             return false;
         }

         @Override
         public boolean onDoubleTap(MotionEvent e) {
           //  return super.onDoubleTap(e);

           //  Log.e("Testing", "GESTURE DETECTOR LISTENER onDoubleTap");
             return  true;
         }

         @Override
         public boolean onDoubleTapEvent(MotionEvent e) {
            // Log.e("Testing", "GESTURE DETECTOR LISTENER onDOubleTapEvent");
        /*     Custom_Designing cd= new Custom_Designing();
             cd.font_dialog();*/
             if(font_dialog_state==0) {
                 font_dialog_state=1;
                 Custom_Designing.font_dialog();
             }
             return false;
         }
     }


  public boolean scaleshape(float progress)
    {

        mScaleFactor =1f* progress;

        // Don't let the object get too small or too large.
        mScaleFactor = Math.max(0.05f, Math.min(mScaleFactor, 2.0f));
        invalidate();
//        Log.e(TAG, "New Image size: widht: " + mImage.getIntrinsicWidth() * mScaleFactor + " height: " + mImage.getIntrinsicHeight() * mScaleFactor);
        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.05f, Math.min(mScaleFactor, 2.0f));
            invalidate();
//            Log.i("getIntrinsicWidth", "New Image size: widht: " + mImage.getIntrinsicWidth()*mScaleFactor + " height: " + mImage.getIntrinsicHeight() * mScaleFactor);
            return true;
        }
    }

    public void getRotateDrawable(final float angle) {
        rotateAngle = angle;
        rotateAllow = true;
        invalidate();
    }

    public void greyScaler() {
        ColorMatrix cm = new ColorMatrix();
        cm.set(new float[]{
                .21f, .71f, .07f, 0, 0,
                .21f, .71f, .07f, 0, 0,
                .21f, .71f, .07f, 0, 0,
                0, 0, 0, 1, 0});

        mImage.setColorFilter(new ColorMatrixColorFilter(cm));

        invalidate();
    }

    public void ChangeColor(int color) {
        // Working For HSV
//        ColorFilter colorFilter = ColorFilterGenerator.from(mImage).to(Color.RED);
////        ColorFilter colorFilter = ColorFilterGenerator.from(Color.WHITE).to(Color.RED);
//        mImage.setColorFilter(colorFilter);

        // Working But Complete image Changes
//        int COLOR2 = Color.parseColor("#000000");
//        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;   uncomment OK
//        Drawable d = mImage;//mCtx.getResources().getDrawable(R.drawable.image);
//        mImage.setColorFilter(COLOR2,mMode);
//        mImage.setColorFilter(color,mMode);   uncomment only


        int iColor = color;// Color.parseColor(color);

        int red = (iColor & 0xFF0000) / 0xFFFF;
        int green = (iColor & 0xFF00) / 0xFF;
        int blue = iColor & 0xFF;

        float[] matrix = {0, 0, 0, 0, red,
                0, 0, 0, 0, green,
                0, 0, 0, 0, blue,
                0, 0, 0, 1, 0};

        ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
        mImage.setColorFilter(colorFilter);

        invalidate();
    }


    public void adjust() {
        int to = Color.RED;

        //Need to copy to ensure that the bitmap is mutable.
        Bitmap src = ((BitmapDrawable) mImage).getBitmap();
        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        for (int x = 0; x < bitmap.getWidth(); x++)
            for (int y = 0; y < bitmap.getHeight(); y++)
                if (match(bitmap.getPixel(x, y)))
                    bitmap.setPixel(x, y, to);

        mImage = new BitmapDrawable(bitmap);
        invalidate();
//        return ;
    }

    private static final int[] FROM_COLOR = new int[]{0, 0, 0};
    private static final int THRESHOLD = 3;

    private boolean match(int pixel) {
        //There may be a better way to match, but I wanted to do a comparison ignoring
        //transparency, so I couldn't just do a direct integer compare.
        return Math.abs(Color.red(pixel) - FROM_COLOR[0]) < THRESHOLD &&
                Math.abs(Color.green(pixel) - FROM_COLOR[1]) < THRESHOLD &&
                Math.abs(Color.blue(pixel) - FROM_COLOR[2]) < THRESHOLD;
    }

    public void rotateCanvas(int angle) {
//        canvas.save();
//        canvas.rotate(value);
//        canvas.restore();

        Matrix matrix = new Matrix();
        matrix.setRotate(angle, mImageWidth/2, mImageHeight/2);
//        yourCanvas.drawBitmap(mImage, matrix, null);
    }


}