package com.fsd.logodesign.drawing_types;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.fsd.logodesign.Custom_Designing;


/**
 * Created by hamza on 5/4/2016.
 */
public class GalleryImageView extends View {

    private static final int INVALID_POINTER_ID = -1;

    private final String TAG = "TESTESTEST";

    private Drawable mImage;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int pos)
    {
        this.currentPosition=pos;
    }
    private int currentPosition;

//    public static int getCurrentPos ;  // Used because i'll access them from MainActivity
    int layerID;
    public static int getCurrentShapePos = 0;

    public void setmImage(Drawable image) {
        mImage = image;
    }

    // width and height of original image
    private float mImageWidth;
    private float mImageHeight;

    // when image is scaled, we use this to calculate the bounds of the image
    private int mImageWidthScaled;
    private int mImageHeightScaled;

    public float mPosX = 150;
    public float mPosY = 300;

    float rotateAngle;
    boolean rotateAllow = false;

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

    public int getLayerID() {
        return layerID;
    }

    public void setLayerID(int layerID) {
        this.layerID = layerID;
    }


    private float mLastTouchX;
    private float mLastTouchY;

    private Paint mBorderLeftLine;
    private Paint mBorderTopLine;
    private Paint mBorderRightLine;
    private Paint mBorderBottomLine;

    private int mActivePointerId = INVALID_POINTER_ID;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1f;

    // this is to tell Style what view number I am in the array.
    private int mNumberView;

    // this is what draws the red line around the TouchView to tell the user
    // this one is currently selected
    private boolean mSelected = false;


    public void setmSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }

    private Paint.Style mStyle;

    // this is to keep a reference to the original image, so when we ship, we can tar
    // the images that are needed.
    private String mUri;

    public void setImageLocation(String path) {
        this.mUri = path;
    }

    int uniqueid;
    public int getUniqueID() {
        return fpos;
    }

    public void setUniqueID(int uniqueID) {
        this.fpos = uniqueID;
    }
    int fpos;

    public GalleryImageView(Context context, Paint.Style style, Drawable image, int count, float scaleFactor , int pos) {
        super(context);
        this.mImage = image;
        mImageWidth = image.getIntrinsicWidth();
//        mImageHeight = image.getBitmap().getHeight();
        mImageHeight = image.getIntrinsicHeight();
        mImageWidthScaled = (int) (mImageWidth * scaleFactor);
        mImageHeightScaled = (int) (mImageHeight * scaleFactor);
        this.mNumberView = count;
        this.mStyle = style;
        this.mScaleFactor = scaleFactor;
        this.currentPosition = pos;
        init(context);
    }

    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mBorderLeftLine = new Paint();
        mBorderRightLine = new Paint();
        mBorderBottomLine = new Paint();
        mBorderTopLine = new Paint();
        setBorderParams(Color.RED,2);

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
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getCurrentShapePos = currentPosition;
   //     Custom_Designing.selectedposition=getCurrentShapePos;
//        Log.e("UpdateSeek" , "currentPosition TouchView.getCurrentPos : " + currentPosition);
        this.canvas = canvas;
        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        if(rotateAllow){
//            Log.d("rotateAllow" , "Rotate is Allow : Rotate Angle : " + rotateAngle);
            canvas.rotate(rotateAngle ,  mImage.getIntrinsicWidth() /2.0f ,mImage.getIntrinsicHeight()/2.0f );
        }
//        canvas.rotate();
        mImage.draw(canvas);
        if (mSelected){
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

   /*  public void onDoubleClickEvent(MotionEvent event)
     {
         mScaleDetector.onD
     }*/

    public boolean onTouchEvent(MotionEvent event) {
        //ImageView view = (ImageView) v;
        mScaleDetector.onTouchEvent(event);
        Custom_Designing.selectedposition=fpos;
        Custom_Designing.isEraserAllow=false;
        Custom_Designing.click=false;
        Custom_Designing.setupVisibility();
        getCurrentShapePos = currentPosition;


        boolean intercept = false;
        //boolean defaultResult = onTouchEvent(event);
//        Log.d("onTouchEventGallery", "currentPosition : " + currentPosition);
//        Log.d("onTouchEventGallery", "mNumberView : " + mNumberView);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                mActivePointerId = event.getPointerId(0);
                if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX+ mImageWidthScaled)
                        && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled))){
//                    Log.i("onTouchEventGallery","My view is here: "+mNumberView);
                    intercept = true;
                    mSelected = true;
//                    mStyle.setmCurrentView(mNumberView);

//                    for (View view : Custom_Designing.mGalleryArray) {
//                        GalleryImageView shapeView = (GalleryImageView) view;
//
//                        Log.d("onTouchEvent", "**** Shapes Array Current Pos : " + shapeView.currentPosition);
//                        if (shapeView.currentPosition != currentPosition) {
//                            shapeView.mSelected = false;
//                            shapeView.invalidate();
//                        }else
//                            Custom_Designing.setUpCurrentRadioItem(layerID);
//                    }

//                    Log.d("onTouchEvent", " ACTION_DOWN IF : " + mNumberView);
//                    Log.d("onTouchEventComponent", "ShapeView Clicked inside onDown If");

                    for (int i = 0 ; i < Custom_Designing.mGalleryArray.size() ; i++) {
                        GalleryImageView galleryView = (GalleryImageView) Custom_Designing.mGalleryArray.get(i);

//                        Log.d("onTouchEvent", "**** Shapes Array Current Pos : " + galleryView.currentPosition);
                        if (galleryView.currentPosition != currentPosition) {
                            galleryView.mSelected = false;
                            galleryView.invalidate();
                        }else
                            Custom_Designing.setUpCurrentRadioItem(layerID);
                    }


                    for (View view : Custom_Designing.mFontsArray) {
                        FontView fontView = (FontView) view;
                        fontView.setmSelected(false);
                        fontView.invalidate();
                    }

                    for (View view : Custom_Designing.mShapesArray) {
                        ShapeView shapV = (ShapeView) view;
                        shapV.setmSelected(false);
                        shapV.invalidate();
                    }

                    Custom_Designing.selectedComponent = 3;

                }else {
                    mSelected = false;
                    Custom_Designing.selectedComponent = -1;
                    invalidate();
                }

//                Log.i(TAG,"Action down");
//                Log.i(TAG,"x is: "+mLastTouchX);
//                Log.i(TAG,"y is: "+mLastTouchY);
                break;
            case MotionEvent.ACTION_UP:
                setFocusable(false);
                mImageWidthScaled = (int) (mImageWidth*mScaleFactor);
                mImageHeightScaled = (int) (mImageHeight*mScaleFactor);
            /*          mPosX = (int) event.getX();
            mPosY = (int) event.getY();*/
                mActivePointerId = INVALID_POINTER_ID;
                // stop the red rectangle from being drawn around the View
//                mSelected = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER_ID;
                // stop the red rectangle from being drawn around the View
                mSelected = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);
                if (!mScaleDetector.isInProgress()) {
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;

                    // Invalidate to request a redraw
                    invalidate();
                }
                else{
//                    Log.i(TAG,"Now scaling is happening");
                }
                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            case MotionEvent.ACTION_POINTER_UP:
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
//            Log.i(TAG,"New Image size: widht: "+mImage.getIntrinsicWidth()+" height: "+mImage.getIntrinsicHeight());
            return true;
        }
    }

    public void greyScaler() {
        ColorMatrix cm = new ColorMatrix();
        cm.set(new float[] {
                .21f, .71f, .07f, 0, 0,
                .21f, .71f, .07f, 0, 0,
                .21f, .71f, .07f, 0, 0,
                0, 0, 0, 1, 0 });

        mImage.setColorFilter(new ColorMatrixColorFilter(cm));
        invalidate();
    }

    public void getRotateDrawable(final float angle) {
        rotateAngle = angle;
        rotateAllow = true;
        invalidate();
    }

}