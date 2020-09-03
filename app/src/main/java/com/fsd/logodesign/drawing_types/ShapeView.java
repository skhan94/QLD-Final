package com.fsd.logodesign.drawing_types;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.fsd.logodesign.Custom_Designing;
import com.fsd.logodesign.adapters.LayersAdapterMap;


/**
 * Created by hamza on 5/4/2016.
 */
public class ShapeView extends View {

    private static final int INVALID_POINTER_ID = -1;

    private final String TAG = "TESTESTEST";

    public Drawable mImage;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int pos)
    {
        this.currentPosition=pos;
    }
    private int currentPosition;
    public static int getCurrentShapePos;  // Used because i'll access them from Custom_Designing

    public void setmImage(Drawable image) {
        mImage = image;
    }

    public BitmapDrawable getmImage() {
        return (BitmapDrawable) mImage;
    }

    // width and height of original image
    public float mImageWidth;
    public float mImageHeight;

    // when image is scaled, we use this to calculate the bounds of the image
    private int mImageWidthScaled;
    private int mImageHeightScaled;

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

    float value=0.0f;
    int layerID;

    private int mActivePointerId = INVALID_POINTER_ID;
    private ScaleGestureDetector mScaleDetector;

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
    boolean moveAllow = true;

    public boolean isMoveAllow() {
        return moveAllow;
    }

    public void setMoveAllow(boolean moveAllow) {
        this.moveAllow = moveAllow;
    }

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

    int foreground, insideCircle;

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

    public int getLayerID() {
        return layerID;
    }

    public void setLayerID(int layerID) {
        this.layerID = layerID;
    }
    int uniqueid;
    public int getUniqueID() {
        return fpos;
    }

    public void setUniqueID(int uniqueID) {
        this.fpos = uniqueID;
    }
    int fpos;
    private Rect rectangle;
    private Paint paint;
    Point a,b,c,d,e,f;
    int shapeId;
    Path path;
    public void setShapeId(int shapeid)
    {
        this.shapeId=shapeid;
    }
    public int getShapeId()
    {
        return shapeId;
    }

      int ImageWidth,ImageHeight;
    public ShapeView(Context context, Paint.Style style, int count, float scaleFactor, int pos, float posX, float posY, int foreground, int insideCircle, float rotateAngle, boolean moveAllow) {
        super(context);
     //   this.mImage = image;
       // mImageWidth = image.getBitmap().getWidth();
       // mImageHeight = image.getBitmap().getHeight();

        this.mNumberView = count;
        this.mStyle = style;
        this.mScaleFactor = scaleFactor;
        this.currentPosition = pos;
        if (posX != 0 & posY != 0) {
            mPosX = posX;
            mPosY = posY;
        }
        this.foreground = foreground;
        this.insideCircle = insideCircle;
        this.rotateAngle = rotateAngle;
        this.moveAllow = moveAllow;

        init(context);
    }


    public ShapeView(Context context, Paint.Style style, int count, float scaleFactor, int pos, float posX, float posY, int foreground, int insideCircle, float rotateAngle) {
        super(context);
      //  this.mImage = image;
        //mImageWidth = image.getBitmap().getWidth();
        //mImageHeight = image.getBitmap().getHeight();
        mImageWidthScaled = (int) (125 * scaleFactor);
        mImageHeightScaled = (int) (125 * scaleFactor);
        this.mNumberView = count;
        this.mStyle = style;
        this.mScaleFactor = scaleFactor;
        this.currentPosition = pos;
        if (posX != 0 & posY != 0) {
            mPosX = posX;
            mPosY = posY;
        }
        this.foreground = foreground;
        this.insideCircle = insideCircle;
        this.rotateAngle = rotateAngle;
        init(context);
    }
    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mBorderLeftLine = new Paint();
        mBorderRightLine = new Paint();
        mBorderBottomLine = new Paint();
        mBorderTopLine = new Paint();
        setBorderParams(Color.RED, 2);
        if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH)
        {
            Log.e("Testing","XXHIGH");
            ImageWidth=375;
            ImageHeight=375;
        }
        else
        {
            ImageWidth=125;
            ImageHeight=125;
        }
        mImageWidthScaled = (int) (ImageWidth * mScaleFactor);
        mImageHeightScaled = (int) (ImageHeight * mScaleFactor);

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
      //  mImage.setBounds(0, 0,125, tIntrinsicHeight());

    }

    Canvas canvas;
    public boolean rotateAllow = false;

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getCurrentShapePos = currentPosition;
           Log.e("Testing","ShapeView Draw");
    //    Custom_Designing.selectedposition=layerID;
      //  Log.e("UpdateSeek", "currentPosition TouchView.getCurrentShapePos : " + currentPosition);
      /*  if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
        //    Log.e("Testing","DISPLAY METRICS " + getResources().getDisplayMetrics().densityDpi);
            value=3.3f;
        }

        else
        {
            value=1.8f;
        }
*/

        this.canvas = canvas;
            canvas.save();
            canvas.translate(mPosX, mPosY);

            canvas.scale(mScaleFactor, mScaleFactor);
            if (rotateAllow) {
//                Log.d("rotateAllow", "Rotate is Allow : Rotate Angle : " + rotateAngle);
                //            canvas.rotate(rotateAngle ,  mImage.getIntrinsicWidth() * getmScaleFactor()/2.0f ,mImage.getIntrinsicHeight()* getmScaleFactor()/2.0f );
                canvas.rotate(rotateAngle, ImageWidth / 2.0f, ImageWidth / 2.0f);
//            rotateAllow = false;
            }
        //    mImage.draw(canvas);

        switch(shapeId) {

            case 0:
//TRIANGLE
                paint = new Paint();
                paint.setColor(insideCircle);
                paint.setStyle(Paint.Style.FILL);
                paint.setAntiAlias(true);
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
                    a = new Point(15, 360);
                    b = new Point(360, 360);
                    c = new Point(180, 15);
                }
                else
                {
                    a = new Point(5, 120);
                    b = new Point(120, 120);
                    c = new Point(60, 5);

                }
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.moveTo(a.x, a.y);
                path.lineTo(b.x, b.y);
                path.lineTo(c.x, c.y);
                path.lineTo(a.x, a.y);
                path.close();

                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(foreground);
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
                    paint.setStrokeWidth(9);
                }
                else
                {
                    paint.setStrokeWidth(5);
                }

                paint.setAntiAlias(true);
                canvas.drawPath(path,paint);
                break;

            case 1:
//CIRCLE
                float mid1;
                float min1,fat1,half1,rad1;
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
                     mid1 = ImageWidth / 2;
                    min1 = Math.min(ImageWidth, ImageHeight);
                     fat1 = min1 / 17;
                     half1 = min1 / 2;
                     rad1 = half1 - fat1;
                    mid1 = mid1 - half1;
                }

                else
                {
                     mid1 = ImageWidth / 2;
                     min1 = Math.min(ImageWidth, ImageHeight);
                     fat1 = min1 / 17;
                     half1 = min1 / 2;
                     rad1 = half1 - fat1;
                    mid1 = mid1 - half1;
                }

                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
                    paint.setStrokeWidth(13);
                }
                else
                {
                    paint.setStrokeWidth(9);
                }

                paint.setColor(foreground);
                canvas.drawCircle(mid1 + half1, half1, rad1, paint);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(insideCircle);
                canvas.drawCircle(mid1 + half1, half1, rad1, paint);


   /*     int radius;
        radius = 60;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(insideCircle);
        canvas.drawCircle(60,60,radius,paint);
      //  canvas.drawPaint(paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(foreground);

        canvas.drawCircle(60, 60, radius, paint);*/

                break;

            case 2:

//HEX
                paint = new Paint();
                paint.setColor(insideCircle);
                paint.setStyle(Paint.Style.FILL);
                paint.setAntiAlias(true);
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
                    a = new Point(105, 30);
                    b = new Point(270, 30);
                    c = new Point(360, 186);
                    d = new Point(270, 345);
                    e = new Point(105, 345);
                    f = new Point(15, 186);
                }
                else
                {
                    a = new Point(35, 10);
                    b = new Point(90, 10);
                    c = new Point(120, 62);
                    d = new Point(90, 115);
                    e = new Point(35, 115);
                    f = new Point(5, 62);
                }
                path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.moveTo(a.x, a.y);
                path.lineTo(b.x, b.y);
                path.lineTo(c.x, c.y);
                path.lineTo(d.x, d.y);
                path.lineTo(e.x, e.y);
                path.lineTo(f.x, f.y);
                path.lineTo(a.x, a.y);
                path.close();

                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(foreground);
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
                    paint.setStrokeWidth(9);
                }
                else
                {
                    paint.setStrokeWidth(5);
                }

                paint.setAntiAlias(true);
                canvas.drawPath(path,paint);
                break;


            case 3:

//RECTANGLE
                int sideLength;
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {

                        sideLength = 360;
                    rectangle = new Rect(15,15, sideLength, sideLength);
                    }
                 else
                    {
                        sideLength = 120;
                        rectangle = new Rect(5, 5, sideLength, sideLength);

                    }

        // create a rectangle that we'll draw later


        // create the Paint and set its color
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(insideCircle);
        canvas.drawRect(rectangle, paint);

        // border
        paint.setStyle(Paint.Style.STROKE);
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {

                    paint.setStrokeWidth(9);
                }
                else
                {
                    paint.setStrokeWidth(5);
                }
        paint.setColor(foreground);
        canvas.drawRect(rectangle, paint);
           break;








            //STAR

            case 4:
                float mid,min,fat,half,rad;
            //  float mid = getWidth() / 2;
                if(getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
                     mid = ImageWidth / 2;
                     min = Math.min(ImageWidth, ImageHeight);
                     fat = min / 17;
                     half = min / 2;
                     rad = half - fat;
                    mid = mid - half;
                    paint = new Paint();
    /*    paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(mid + half, half, rad, paint);*/
                    paint.setAntiAlias(true);

                    int minDim = Math.min(ImageWidth - 30 - 30,
                            ImageHeight - 30 - 30);

//STAR
// b = |
// a = _
// hyp = \

// bigHypot = height / cos(18)
                    double bigHypot = (minDim / Math.cos(Math.toRadians(18)));
                    double bigB = minDim;
                    double bigA = Math.tan(Math.toRadians(18)) * bigB;

// lengths of the little triangles.
// littleC = littleC + littleC + littleA + littleA
// cos(72)*C = A
                    double littleHypot = bigHypot / (2 + Math.cos(Math.toRadians(72)) + Math.cos(Math.toRadians(72)));
                    double littleA = Math.cos(Math.toRadians(72)) * littleHypot;
                    double littleB = Math.sin(Math.toRadians(72)) * littleHypot;

                    int topXPoint = (ImageWidth-30-30)/2;
                    int topYPoint = 30;

// start at the top point
                    path = new Path();
                    path.moveTo(topXPoint + 30, topYPoint);

// top to bottom right point
                    path.lineTo((int) (topXPoint + bigA + 30), (int) (topYPoint + bigB));

// bottom right to middle left point
                    path.lineTo((int) (topXPoint - littleA - littleB + 30), (int) (topYPoint + littleB));

// middle left to middle right point
                    path.lineTo((int) (topXPoint + littleA + littleB + 30), (int) (topYPoint + littleB));

//        // middle right to bottom left point
                    path.lineTo((int)(topXPoint - bigA +30), (int)(topYPoint + bigB));

//        // bottom left to top point
                    path.lineTo(topXPoint +30, topYPoint);
                    path.close();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(foreground);
                    paint.setStrokeWidth(13);
                    canvas.drawPath(path, paint);
                    paint.setColor(insideCircle);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawPath(path, paint);
                }
                else
                {
                    mid = ImageWidth / 2;
                    min = Math.min(ImageWidth, ImageHeight);
                    fat = min / 17;
                    half = min / 2;
                    rad = half - fat;
                    mid = mid - half;

                    paint = new Paint();
    /*    paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(mid + half, half, rad, paint);*/
                    paint.setAntiAlias(true);

                    int minDim = Math.min(125 - 10 - 10,
                            125 - 10 - 10);

//STAR
// b = |
// a = _
// hyp = \

// bigHypot = height / cos(18)
                    double bigHypot = (minDim / Math.cos(Math.toRadians(18)));
                    double bigB = minDim;
                    double bigA = Math.tan(Math.toRadians(18)) * bigB;

// lengths of the little triangles.
// littleC = littleC + littleC + littleA + littleA
// cos(72)*C = A
                    double littleHypot = bigHypot / (2 + Math.cos(Math.toRadians(72)) + Math.cos(Math.toRadians(72)));
                    double littleA = Math.cos(Math.toRadians(72)) * littleHypot;
                    double littleB = Math.sin(Math.toRadians(72)) * littleHypot;

                    int topXPoint = (125-10-10)/2;
                    int topYPoint = 10;

// start at the top point
                    path = new Path();
                    path.moveTo(topXPoint + 10, topYPoint);

// top to bottom right point
                    path.lineTo((int) (topXPoint + bigA + 10), (int) (topYPoint + bigB));

// bottom right to middle left point
                    path.lineTo((int) (topXPoint - littleA - littleB + 10), (int) (topYPoint + littleB));

// middle left to middle right point
                    path.lineTo((int) (topXPoint + littleA + littleB + 10), (int) (topYPoint + littleB));

//        // middle right to bottom left point
                    path.lineTo((int)(topXPoint - bigA +10), (int)(topYPoint + bigB));

//        // bottom left to top point
                    path.lineTo(topXPoint +10, topYPoint);
                    path.close();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(foreground);
                    paint.setStrokeWidth(9);
                    canvas.drawPath(path, paint);
                    paint.setColor(insideCircle);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawPath(path, paint);
                }


       /*  paint.setStyle(Paint.Style.STROKE);
         paint.setStrokeWidth(5);
         paint.setColor(foreground);
         canvas.drawPath(path,paint);*/
         break;
        }

   /*     Log.e("Testing", "IMAGE WIDTH " + mImage.getIntrinsicWidth());
               Log.e("Testing","IMAGE HEIGHT " + mImage.getIntrinsicHeight());
*/


            if (mSelected) {
                Log.e("Testing","SELECTED " + ImageHeight + " " + ImageWidth);
                canvas.drawLine(0,
                        0,
                        ImageHeight,
                        0,
                        mBorderTopLine);
                canvas.drawLine(0, ImageWidth,
                        ImageHeight,
                        ImageHeight,
                        mBorderBottomLine);
                canvas.drawLine(0,
                        0,
                        0,
                        ImageHeight,
                        mBorderLeftLine);
                canvas.drawLine(ImageHeight,
                        0,
                        ImageHeight,
                        ImageHeight,
                        mBorderRightLine);
             /*   canvas.drawLine(0,
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
                        mBorderRightLine);*/
            }
            canvas.restore();
//        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        //ImageView view = (ImageView) v;
        getCurrentShapePos = currentPosition;
        Custom_Designing.selectedposition=fpos;
        Custom_Designing.isEraserAllow=false;
       Custom_Designing.click=false;
        Custom_Designing.setupVisibility();

        if (moveAllow) {
            mScaleDetector.onTouchEvent(event);
            boolean intercept = false;
            //boolean defaultResult = onTouchEvent(event);
//            Log.d("onTouchEventComponent", "ShapeView Clicked");
//            Log.d("onTouchEvent", "currentPosition : " + currentPosition);
//            Log.d("onTouchEvent", "mNumberView : " + mNumberView);

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
//                Log.d("onTouchEvent" , " ACTION_DOWN : " + currentPosition);
                    mLastTouchX = event.getX();
                    mLastTouchY = event.getY();
                    mActivePointerId = event.getPointerId(0);
//                    if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX + mImageWidthScaled/1.2)
//                            && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled/1.2))) {

             //    Log.e("Testing","Value " + value);
                 /*   if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX + mImageWidthScaled/value)
                            && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled/value))) */

                    if (((mLastTouchX >= mPosX) && (mLastTouchX <= mPosX + mImageWidthScaled)
                            && (mLastTouchY >= mPosY) && (mLastTouchY <= mPosY + mImageHeightScaled)))
                    {
                        Log.e("Testing","ALL CONDITIONS SATISFIED ");
                        Log.i(TAG, "My view is here: " + mNumberView);
                        intercept = true;
                        mSelected = true;
//                    mStyle.setmCurrentView(mNumberView);
//                        Log.d("onTouchEvent", " ACTION_DOWN IF : " + mNumberView);
//                        Log.d("onTouchEventComponent", "ShapeView Clicked inside onDown If");
//                        for (View view : Custom_Designing.mShapesArray) {
//                            ShapeView shapeView = (ShapeView) view;
//
//                            Log.d("onTouchEvent", "**** Shapes Array Current Pos : " + shapeView.currentPosition);
//                            if (shapeView.currentPosition != currentPosition) {
//                                shapeView.mSelected = false;
//                                shapeView.invalidate();
//                            }else
//                                Custom_Designing.setUpCurrentRadioItem(layerID);
//                        }
                        for (int i = 0 ; i < Custom_Designing.mShapesArray.size() ; i++) {
                            ShapeView shapeView = (ShapeView) Custom_Designing.mShapesArray.get(i);

//                            Log.d("onTouchEvent", "**** Shapes Array Current Pos : " + shapeView.currentPosition);
                            if (shapeView.currentPosition != currentPosition) {
                                shapeView.mSelected = false;
                                shapeView.invalidate();
                            }else
                                Custom_Designing.setUpCurrentRadioItem(layerID);
                        }
                        for (View view : Custom_Designing.mFontsArray) {
                            FontView fontView = (FontView) view;
                            fontView.setmSelected(false);
                            fontView.invalidate();
                        }
                        for (View view : Custom_Designing.mGalleryArray) {
                            GalleryImageView imgView = (GalleryImageView) view;
                            imgView.setmSelected(false);
                            imgView.invalidate();
                        }

                        Custom_Designing.selectedComponent = 0;
                    } else {
//                        Log.d("onTouchEvent", " ACTION_DOWN ELSE : " + mNumberView);
                        mSelected = false;
                        Log.e("Testing","CONDITIONS NOT SATISFIED");
//                    onDraw(canvas);
//                        Log.d("onTouchEventComponent", "ShapeView Clicked inside onDown Else");
                        Custom_Designing.selectedComponent = -1;
                        invalidate();
                    }

//                    Log.i(TAG, "Action down");
//                    Log.i(TAG, "x is: " + mLastTouchX);
//                    Log.i(TAG, "y is: " + mLastTouchY);
                    break;
                case MotionEvent.ACTION_UP:
//                    Log.d("onTouchEvent", "ACTION_UP  : " + currentPosition);

                    setFocusable(false);
                    mImageWidthScaled = (int) (ImageWidth * mScaleFactor);
                    mImageHeightScaled = (int) (ImageHeight * mScaleFactor);
            /*          mPosX = (int) event.getX();
            mPosY = (int) event.getY();*/
                    mActivePointerId = INVALID_POINTER_ID;

                    // stop the red rectangle from being drawn around the View
//                mSelected = false;
                    invalidate();

                    break;
                case MotionEvent.ACTION_CANCEL:
//                    Log.d("onTouchEvent", "ACTION_CANCEL  : " + currentPosition);
//                Log.d("onTouchEvent" , "  : " + currentPosition);
                    mActivePointerId = INVALID_POINTER_ID;
                    // stop the red rectangle from being drawn around the View
                    mSelected = false;
                    break;
                case MotionEvent.ACTION_MOVE:
//                    Log.d("onTouchEvent", "ACTION_MOVE  : " + currentPosition);
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
                    } else {
//                        Log.i(TAG, "Now scaling is happening");
                    }
                    // Remember this touch position for the next move event
                    mLastTouchX = x;
                    mLastTouchY = y;

                    break;
                case MotionEvent.ACTION_POINTER_UP:
//                    Log.d("onTouchEvent", " ACTION_POINTER_UP : " + currentPosition);
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
        return true;

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

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.05f, Math.min(mScaleFactor, 2.0f));
            invalidate();
//            Log.e(TAG, "New Image size: widht: " + mImage.getIntrinsicWidth() * mScaleFactor + " height: " + mImage.getIntrinsicHeight() * mScaleFactor);
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
        matrix.setRotate(angle, 125 / 2, 125 / 2);
//        yourCanvas.drawBitmap(mImage, matrix, null);
    }
}