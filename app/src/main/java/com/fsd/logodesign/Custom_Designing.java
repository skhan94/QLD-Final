package com.fsd.logodesign;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fsd.logodesign.adapters.LayersAdapterMap;
import com.fsd.logodesign.adapters.ListAdapterHolder;
import com.fsd.logodesign.adapters.ListGalleryHolder;
import com.fsd.logodesign.adapters.ShapeAdapter;
import com.fsd.logodesign.drawing_types.DrawView;
import com.fsd.logodesign.drawing_types.FontView;
import com.fsd.logodesign.drawing_types.GalleryImageView;
import com.fsd.logodesign.drawing_types.ShapeView;
import com.fsd.logodesign.methods.HorizontalListView;
import com.fsd.logodesign.methods.Methods;
import com.fsd.logodesign.objectclasses.ColorObject;
import com.fsd.logodesign.objectclasses.ImageObject;

import com.fsd.logodesign.objectclasses.ViewDetails;
import com.mobeta.android.dslv.DragSortListView;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by hamza on 5/12/2016.
 */
public class Custom_Designing extends Activity implements View.OnClickListener {


    CheckBox create_logo_grid_icon;
    FrameLayout grid_frame;

    /*Bottom Components*/
    CheckBox checkbox_brush, checkbox_gallery, checkbox_shape, checkbox_font;
    LinearLayout all_markers_layout, all_shapes_layout;
    RelativeLayout all_gallery_layout;
    HorizontalScrollView all_fonts_layout;

    /*Layers Side Menu */
    ImageView create_logo_layer_icon;
    FrameLayout layers_side_frame;
    Animation animationslideright, animationslideleft;
    HorizontalListView mHlvSimpleList;
    String content;
    public static Dialog dialog;
    EditText fontTextViewText;
    View fontsubmitview;

    public static int selectedComponent = -1;  // 0 --> Shapes , 1 --> Font, 3 -->Gallery, 2 -->for freehand
    public static SeekBar angleSeekBar;
    public static SeekBar angleSeekBar1;
    public static ArrayList<View> mShapesArray = new ArrayList<View>();
    private int mShapesCount = 0;

    // Fonts

    public static ArrayList<View> mFontsArray = new ArrayList<View>();
    private int mFontsCount = 0;
    public static int final_unique_id=0;   //setting unique id to every layer so that they can be easily deleted
 //   long adapterposition;
    public static ArrayList<View> mLayersView = new ArrayList<>();


    /*Main Frame*/
    FrameLayout layout_layers;
    static FrameLayout layout_single_parent;

    /*Storage Data Stuctures / Types */
    Map<Integer, ViewDetails> mRecentViewMaps = new HashMap<Integer, ViewDetails>(); // KEY , Value
    ViewDetails viewDetails;


    /*Side Menu Adapter*/
    public static LayersAdapterMap adapter;
    DragSortListView dragSortListView;
    ArrayList<ViewDetails> layersObjects = new ArrayList<>();


    /*Color Variables */
    boolean isColorChanges = false;
    int selectedColorRGB = -6042959;//Color.BLACK;

    /*Gallery Work*/
    ImageView add_img_icon;
    public static ArrayList<View> mGalleryArray = new ArrayList<View>();
    private static final int GALLERY_PIC_REQUEST = 1112;
    private int mGalleryCount = 0;


    /*Save Work*/
    ImageView create_logo_save;

    /*Free Hand*/
    DrawView drawView;
    int MARKER_SIZE = -1;
    int MARKER_SIZE_ERASER = 10;
    ImageView marker_1, marker_2, marker_3, marker_4, marker_5;
    FrameLayout layout_layers_freehand;

    FrameLayout layout_complete;
    Animation animationslidedown, animationslideup;

    /*Eraser*/
    ImageView eraser; // FreeHand Eraser
    ImageView eraser_all;
  //  boolean freehand_eraser=false;
    //int position;
    ImageView selectedMarker = null;   // 1 is for First , and 5 is for Last

    public static int unique_id=0;
    /*Layers Freehand Logic*/
   // boolean isFreehandLayerAdded = false;
    boolean comingFromBottomBar = false;

    /*Eraser Logic */
    public static boolean isEraserAllow = true;
    boolean isCurrentlyUsingEraser = false;


    public static boolean click=false;
    public static int selectedposition=0;
    int clicked_position=-1;
    /*FreeHand Fixes*/

    public static ImageView rotate_icon;
    public static ImageView resize_icon;
  //  LayerObject layerObject;
    int final_position;

    //Shapes Border color
    ImageView stroke;
    ImageView bucket;
    public static int stroke_clicked=0; //0 means not clicked 1 means clicked
    ShapeView newView;
    public static Activity a;
    public static Dialog fontdialog;

    public static String custom_fileName;
    public static int viewpager_state=0; //1 for order now to SVG_ViewPager


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_designing);
        a=this;
        /*mShapesArray = new ArrayList<View>();
        mFontsArray = new ArrayList<View>();
        mGalleryArray = new ArrayList<View>();
        mShapesCount = 0;
        mFontsCount = 0;
        mGalleryCount = 0;
        mLayersView = new ArrayList<View>();

        mRecentViewMaps = new HashMap<Integer, ViewDetails>();
        viewDetails = null;

        layersObjects = new ArrayList<ViewDetails>();
        final_unique_id=0;
*/
        //Setting up border and background for shapes
      //  setUpShapeFeatures();

        /* Setting Up UI*/
        setUpFonts();
        setUpColor();
        setUpGridFeature();
        settUpBottomComponents();

        /*Setting Up Animation*/
        setUpSideAnimation();
        setUpLayersSideView();

        /*Setting Up Eraser Animation*/
        setUpEraserAnimation();
        setUpEraserTool();


        /*Setting Up functionalities */
        layout_layers = (FrameLayout) findViewById(R.id.layout_layers);
        setUpRotateBar();
        setUpListViewDragListener();
        setUpShapeFeature();
        setUpGallery();
     //   layerObject = new LayerObject();
        resize_icon.setVisibility(View.INVISIBLE);
        rotate_icon.setVisibility(View.INVISIBLE);
        angleSeekBar.setVisibility(View.INVISIBLE);
        angleSeekBar1.setVisibility(View.INVISIBLE);
        /*Setting Up Ads*/
        MyApplication.intilizeAdds(Custom_Designing.this);

        create_logo_save = (ImageView) findViewById(R.id.create_logo_save);
        create_logo_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication.displayAd();
             /*   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Custom_Designing.this);
                alertDialogBuilder.setTitle("Save");
                // set dialog message
                alertDialogBuilder//
                        .setMessage("Do you really want to save jpg?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter.setSelectedItem(adapter.getCount());
                                for(int i=0;i<mFontsArray.size();i++)
                                {
                                    FontView font= ((FontView) mFontsArray.get(i));
                                    font.setmSelected(false);
                                    font.invalidate();
                                }
                                for(int i=0;i<mGalleryArray.size();i++)
                                {
                                    GalleryImageView gallery= ((GalleryImageView) mGalleryArray.get(i));
                                    gallery.setmSelected(false);
                                    gallery.invalidate();
                                }
                                for(int i=0;i<mShapesArray.size();i++)
                                {
                                    ShapeView shape= ((ShapeView) mShapesArray.get(i));
                                    shape.setmSelected(false);
                                    shape.invalidate();
                                }
                                custom_fileName= Methods.ImageStore(Custom_Designing.this, layout_complete, "fileName", true);
                                if(custom_fileName!= null) {
                                    dialog.dismiss();
                                    order_now();
                                }
                            }
                        }) //
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }) //
                        .show();
*/

                final Dialog dialog = new Dialog(Custom_Designing.this, R.style.Dialog);
                dialog.setContentView(R.layout.dialog_apps);

                TextView notification_title = (TextView) dialog.findViewById(R.id.notification_title);
                notification_title.setText("Save");

                TextView notification_Text = (TextView) dialog.findViewById(R.id.notification_Text);
                notification_Text.setText("Do you really want to save the logo?");


                TextView notification_ok = (TextView) dialog.findViewById(R.id.notification_ok);
                notification_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.setSelectedItem(adapter.getCount());
                        for(int i=0;i<mFontsArray.size();i++)
                        {
                            FontView font= ((FontView) mFontsArray.get(i));
                            font.setmSelected(false);
                            font.invalidate();
                        }
                        for(int i=0;i<mGalleryArray.size();i++)
                        {
                            GalleryImageView gallery= ((GalleryImageView) mGalleryArray.get(i));
                            gallery.setmSelected(false);
                            gallery.invalidate();
                        }
                        for(int i=0;i<mShapesArray.size();i++)
                        {
                            ShapeView shape= ((ShapeView) mShapesArray.get(i));
                            shape.setmSelected(false);
                            shape.invalidate();
                        }
                        custom_fileName= Methods.ImageStore(Custom_Designing.this, layout_complete, "fileName", true);
                        if(custom_fileName!= null) {
                            dialog.dismiss();
                            order_now();
                        }
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }
                });

                TextView notification_cancel = (TextView) dialog.findViewById(R.id.notification_cancel);
                notification_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();



//
            }
        });

        layout_layers_freehand = (FrameLayout) findViewById(R.id.layout_layers_freehand);
        /*This is because freehand is available for the first time */
//        drawView = new DrawView(this, selectedColorRGB, MARKER_SIZE);
//        layout_layers_freehand.addView(drawView);


        marker_1 = (ImageView) findViewById(R.id.marker_1);
        marker_2 = (ImageView) findViewById(R.id.marker_2);
        marker_3 = (ImageView) findViewById(R.id.marker_3);
        marker_4 = (ImageView) findViewById(R.id.marker_4);
        marker_5 = (ImageView) findViewById(R.id.marker_5);
        marker_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedComponent=2;

                remove_eraser();
                Log.d("layers_freehand", "Adding Marker Size : " + layout_layers_freehand.getChildCount());
                isEraserAllow=true;
                Log.e("Testing","CHILD COUNT " + layout_layers_freehand.getChildCount());
                if (layout_layers_freehand.getChildCount() <= 0) {

                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                } /*else if (layout_layers_freehand.getChildCount() == 1 && comingFromBottomBar) {
                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                    comingFromBottomBar = false;
                }*/
                selectedMarker = (ImageView) view;
                if (MARKER_SIZE != 10) {
                    MARKER_SIZE = 10;
                    drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                    layout_layers_freehand.addView(drawView);
//                    eraser.setChecked(false);
                    eraser.setTag(R.id.eraser, 0);
                    eraser.setBackgroundColor(Color.WHITE);
                }


                marker_1.setBackgroundColor(getResources().getColor(R.color.layer_bg));
                marker_2.setBackgroundColor(Color.WHITE);
                marker_3.setBackgroundColor(Color.WHITE);
                marker_4.setBackgroundColor(Color.WHITE);
                marker_5.setBackgroundColor(Color.WHITE);

            }
        });
        marker_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedComponent=2;
                remove_eraser();
                Log.d("layers_freehand", "Adding Marker Size : " + layout_layers_freehand.getChildCount());
                isEraserAllow=true;
                Log.e("Testing","CHILD COUNT " + layout_layers_freehand.getChildCount());
                if (layout_layers_freehand.getChildCount() <= 0) {

                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                } /*else if (layout_layers_freehand.getChildCount() == 1 && comingFromBottomBar) {
                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                    comingFromBottomBar = false;
                }*/

                selectedMarker = (ImageView) view;
                if (MARKER_SIZE != 15) {
                    MARKER_SIZE = 15;
                    drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                    layout_layers_freehand.addView(drawView);
//                    eraser.setChecked(false);
                    eraser.setTag(R.id.eraser, 0);
                    eraser.setBackgroundColor(Color.WHITE);

                }

                isEraserAllow=true;

                marker_1.setBackgroundColor(Color.WHITE);
                marker_2.setBackgroundColor(getResources().getColor(R.color.layer_bg));
                marker_3.setBackgroundColor(Color.WHITE);
                marker_4.setBackgroundColor(Color.WHITE);
                marker_5.setBackgroundColor(Color.WHITE);

            }
        });
        marker_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedComponent=2;
                remove_eraser();
                Log.d("layers_freehand", "Adding Marker Size : " + layout_layers_freehand.getChildCount());
                  isEraserAllow=true;
                Log.e("Testing","CHILD COUNT " + layout_layers_freehand.getChildCount());
                if (layout_layers_freehand.getChildCount() <= 0) {

                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                } /*else if (layout_layers_freehand.getChildCount() == 1 && comingFromBottomBar) {
                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                    comingFromBottomBar = false;
                }*/

                selectedMarker = (ImageView) view;
                if (MARKER_SIZE != 7) {
                    MARKER_SIZE = 7;
                    drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                    layout_layers_freehand.addView(drawView);
//                    eraser.setChecked(false);
                    eraser.setTag(R.id.eraser, 0);
                    eraser.setBackgroundColor(Color.WHITE);
                }
                isEraserAllow=true;

                marker_1.setBackgroundColor(Color.WHITE);
                marker_2.setBackgroundColor(Color.WHITE);
                marker_3.setBackgroundColor(getResources().getColor(R.color.layer_bg));
                marker_4.setBackgroundColor(Color.WHITE);
                marker_5.setBackgroundColor(Color.WHITE);

            }
        });
        marker_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedComponent=2;
                remove_eraser();
                Log.d("layers_freehand", "Adding Marker Size : " + layout_layers_freehand.getChildCount());
                //isEraserAllow=true;
                Log.e("Testing","CHILD COUNT " + layout_layers_freehand.getChildCount());
                if (layout_layers_freehand.getChildCount() <= 0) {
                  //  Log.e("Testing","CHILD COUNT " + layout_layers_freehand.getChildCount());
                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                } /*else if (layout_layers_freehand.getChildCount() == 1 && comingFromBottomBar) {
                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                    comingFromBottomBar = false;
                }*/

                selectedMarker = (ImageView) view;
                if (MARKER_SIZE != 5) {
                    MARKER_SIZE = 5;
                    drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                    layout_layers_freehand.addView(drawView);
//                    eraser.setChecked(false);
                    eraser.setTag(R.id.eraser, 0);
                    eraser.setBackgroundColor(Color.WHITE);
                }

                isEraserAllow=true;

                marker_1.setBackgroundColor(Color.WHITE);
                marker_2.setBackgroundColor(Color.WHITE);
                marker_3.setBackgroundColor(Color.WHITE);
                marker_4.setBackgroundColor(getResources().getColor(R.color.layer_bg));
                marker_5.setBackgroundColor(Color.WHITE);

            }
        });
        marker_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedComponent=2;
                remove_eraser();
                Log.d("layers_freehand", "Adding Marker Size : " + layout_layers_freehand.getChildCount());
                // isEraserAllow=true;
                Log.e("Testing","CHILD COUNT " + layout_layers_freehand.getChildCount());
                if (layout_layers_freehand.getChildCount() <= 0) {

                    try {        /*If user select logo and then shapes , later if they replace layers up or down , the freehand view will remove*/
                        layout_layers.addView(layout_layers_freehand);
                    } catch (Exception e) {                         /*If user use freehand first then exception will be thrown*/
                        Log.d("Exception", "Exception at layers : " + e.toString());
                    }

                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                } /*else if (layout_layers_freehand.getChildCount() == 1 && comingFromBottomBar) {
                    mLayersView.add(layout_layers_freehand);
                    addItemtoAdapter("Freehand");
                    comingFromBottomBar = false;
                }*/
                selectedMarker = (ImageView) view;
                if (MARKER_SIZE != 30) {
                    MARKER_SIZE = 30;
                    drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                    Log.d("layer_freehand_isnull", " layout_layers_freehand is : " + (layout_layers_freehand));
                    layout_layers_freehand.addView(drawView);
//                    eraser.setChecked(false);
                    eraser.setTag(R.id.eraser, 0);
                    eraser.setBackgroundColor(Color.WHITE);
                }

                isEraserAllow=true;

                marker_1.setBackgroundColor(Color.WHITE);
                marker_2.setBackgroundColor(Color.WHITE);
                marker_3.setBackgroundColor(Color.WHITE);
                marker_4.setBackgroundColor(Color.WHITE);
                marker_5.setBackgroundColor(getResources().getColor(R.color.layer_bg));

            }
        });

        layout_complete = (FrameLayout) findViewById(R.id.layout_complete);

        ImageView create_logo_home = (ImageView) findViewById(R.id.create_logo_home);
        create_logo_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Custom_Designing.this);
                alertDialogBuilder.setTitle("Exit");
                // set dialog message
                alertDialogBuilder//
                        .setMessage("Do you want to exit? All your design will be removed.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                onBackPressed();
                            }
                        }) //
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }) //
                        .show();

            }
        });




//        if (SVG_View_Pager.ringProgressDialog != null)
//            SVG_View_Pager.ringProgressDialog.dismiss();



        adapter.clearAllItems();
        adapter.notifyDataSetChanged();
        MARKER_SIZE = -1;

        layout_layers.removeAllViews();
        layout_layers_freehand.removeAllViews();
        layout_layers.addView(layout_layers_freehand);
        if (selectedMarker != null)
            selectedMarker.setBackgroundColor(Color.WHITE);


    }

    public void order_now()
    {
      /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Custom_Designing.this);
        alertDialogBuilder.setTitle("Order");
        // set dialog message
        alertDialogBuilder//
                .setMessage("Would you like to get additional formats for your logo?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        viewpager_state=1;
                        ClearStaticVariables();
                        Custom_Designing.this.finish();
                        dialog.dismiss();

                    }
                }) //
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }) //
                .show();*/
//

        final Dialog dialog = new Dialog(Custom_Designing.this, R.style.Dialog);
        dialog.setContentView(R.layout.dialog_apps);

        TextView notification_title = (TextView) dialog.findViewById(R.id.notification_title);
        notification_title.setText("Order");

        TextView notification_Text = (TextView) dialog.findViewById(R.id.notification_Text);
        notification_Text.setText("Would you like to get additional formats for your logo?");


        TextView notification_ok = (TextView) dialog.findViewById(R.id.notification_ok);
        notification_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewpager_state=1;
                ClearStaticVariables();
                Custom_Designing.this.finish();
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });

        TextView notification_cancel = (TextView) dialog.findViewById(R.id.notification_cancel);
        notification_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });

        if (!dialog.isShowing())
            dialog.show();





    }

   /* public void setUpShapeFeatures()
    {
       *//* stroke=(ImageView)findViewById(R.id.stroke);
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stroke_clicked=1;
                Log.e("Testing","Stroke Clicked " + stroke_clicked);
            }
        });
        bucket=(ImageView)findViewById(R.id.bucket);
        bucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stroke_clicked=0;
                Log.e("Testing","Stroke Clicked " + stroke_clicked);
            }
        });*//*
    }*/


    @Override
    protected void onResume() {
        super.onResume();
        SVG_View_Pager.activity=this;
    }

    private void remove_eraser()
 {
     if (Integer.parseInt(eraser.getTag(R.id.eraser).toString()) == 1)  // Means It was Checked now we are unchecking it
     {
         if (MARKER_SIZE != -1) {
             drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
             layout_layers_freehand.addView(drawView);
             if (selectedMarker != null)
                 selectedMarker.setBackgroundColor(getResources().getColor(R.color.layer_bg));
         }

         eraser.setBackgroundColor(Color.WHITE);
         eraser.setTag(R.id.eraser, 0);

         isCurrentlyUsingEraser = false;
     }
 }
    private void setUpEraserTool() {

        eraser = (ImageView) findViewById(R.id.eraser);
        eraser.setTag(R.id.eraser, 0);

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.e("Testing","Selected COmponent in eraser " + selectedComponent);
                  if(selectedComponent == -1)
                      for(int i=0;i<adapter.getCount();i++)
                      {
                          if(adapter.getItem(i).getType().equals("Freehand"))
                          {
                              isEraserAllow=true;
                          }
                      }
                /*if (Integer.parseInt(eraser.getTag(R.id.eraser).toString()) == 1)  // Means It was Checked now we are unchecking it
                {
                    if (MARKER_SIZE != -1) {
                        drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                        layout_layers_freehand.addView(drawView);
                        if (selectedMarker != null)
                            selectedMarker.setBackgroundColor(getResources().getColor(R.color.layer_bg));
                    }

                    eraser.setBackgroundColor(Color.WHITE);
                    eraser.setTag(R.id.eraser, 0);

                    isCurrentlyUsingEraser = false;
                }
*/

                if (isEraserAllow)
                {

                    if (Integer.parseInt(eraser.getTag(R.id.eraser).toString()) == 0)
                    {// Means It was Unchecked now we are Checking it
                        eraser.setBackgroundColor(getResources().getColor(R.color.layer_bg1));
                        eraser.setTag(R.id.eraser, 1);

                        if (MARKER_SIZE != -1)
                        {
                            drawView = new DrawView(Custom_Designing.this, Color.WHITE, MARKER_SIZE_ERASER);
                            layout_layers_freehand.addView(drawView);
                            if (selectedMarker != null)
                                selectedMarker.setBackgroundColor(Color.WHITE);
                        }
                        isCurrentlyUsingEraser = true;
                    }
                    else if (Integer.parseInt(eraser.getTag(R.id.eraser).toString()) == 1)  // Means It was Checked now we are unchecking it
                    {
                        if (MARKER_SIZE != -1) {
                            drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                            layout_layers_freehand.addView(drawView);
                            if (selectedMarker != null)
                                selectedMarker.setBackgroundColor(getResources().getColor(R.color.layer_bg));
                        }

                        eraser.setBackgroundColor(Color.WHITE);
                        eraser.setTag(R.id.eraser, 0);

                        isCurrentlyUsingEraser = false;
                    }
                }
                else
                {
                    if(selectedComponent == 0 || selectedComponent == 1 || selectedComponent == 3)
                    {

                        for(int count=0;count<adapter.getCount();count++)
                        {

                            int a=adapter.getItem(count).getUniqueID();
                            if(a==selectedposition)
                            {

                                final_position=count;
                            }
                        }

                        for (int layerId = 0; layerId < mLayersView.size(); layerId++) {
                            if (layerId == final_position)
                            {
                                View mainView = mLayersView.get(layerId);
                                layout_single_parent = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);

                                if (layout_single_parent != null) {  /* Mean its a component */
                                    int childcount = layout_single_parent.getChildCount();

                                    Log.e("ShapeType ", "Child Count : " + childcount);
                                    for (int j = 0; j < childcount; j++)
                                    {
                                        View v = layout_single_parent.getChildAt(j);
                                        if (v instanceof ShapeView) {
                                            ShapeView shapeView = (ShapeView) v;
                                             int position=shapeView.getLayerID();


                                            // final_unique_id=shapeView.getUniqueID();
                                            shapeView.invalidate();
                                            mShapesArray.remove(shapeView);
                                            mShapesCount--;

                                            for(int k=position;k<mShapesArray.size();k++)
                                            {
                                                ShapeView shape= ((ShapeView) mShapesArray.get(k));
                                                int pid= shape.getLayerID();
                                                shape.setLayerID(pid - 1);

                                                int pid1= shape.getCurrentPosition();
                                                shape.setCurrentPosition(pid1 - 1);
                                            }


                                            if (mShapesCount < 0)
                                                mShapesCount = 0;
                                            Log.d("ShapeType ", "Its Shape Instance at : " + layerId);
//                                Toast.makeText(Custom_Designing.this, "instanceof ShapeView : ", Toast.LENGTH_SHORT).show();
                                        } else if (v instanceof FontView)
                                        {
                                            FontView fontView = (FontView) v;
                                            int position=fontView.getLayerID();
                                            // final_unique_id=fontView.getUniqueID();
                                            fontView.invalidate();
                                            mFontsArray.remove(fontView);
                                            mFontsCount--;


                                            for(int k=position;k<mFontsArray.size();k++)
                                            {
                                                FontView font= ((FontView) mFontsArray.get(k));
                                                int pid= font.getLayerID();
                                                font.setLayerID(pid - 1);
                                                int pid1= font.getCurrentPosition();
                                                font.setCurrentPosition(pid1 - 1);
                                            }

                                            if (mFontsCount < 0)
                                                mFontsCount = 0;

                                        } else if (v instanceof GalleryImageView) {
                                            GalleryImageView touchView = (GalleryImageView) v;
                                            //final_unique_id=touchView.getUniqueID();
                                            int position=touchView.getLayerID();
                                            touchView.invalidate();
                                            mGalleryArray.remove(touchView);
                                            mGalleryCount--;

                                            for(int k=position;k<mGalleryArray.size();k++)
                                            {
                                                GalleryImageView gallery= ((GalleryImageView) mGalleryArray.get(k));
                                                int pid= gallery.getLayerID();
                                                gallery.setLayerID(pid - 1);
                                                int pid1= gallery.getCurrentPosition();
                                                gallery.setCurrentPosition(pid1 - 1);
                                            }

                                            if (mGalleryCount < 0)
                                                mGalleryCount = 0;

                                        }

                                        layout_single_parent.removeView(v);
                                    }


                                    mLayersView.remove(layerId);
                                    adapter.remove(adapter.getItem(layerId));

                                    adapter.notifyDataSetChanged();
                                    isEraserAllow=true;
/*
                             for(int i=final_position;i<adapter.getCount();i++)
                             {
                                 int pos=adapter.getItem(i).getLayerId();
                                 adapter.getItem(i).setLayerId(pos - 1);
                             }*/

                                }
                            }
                        }
                    }
                }
            }
        });
//        eraser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b)  // Means Its Checked
//                {
//                    if(MARKER_SIZE != -1) {
//                        drawView = new DrawView(Custom_Designing.this, Color.WHITE, MARKER_SIZE_ERASER);
//                        layout_layers_freehand.addView(drawView);
//                        if (selectedMarker != null)
//                            selectedMarker.setBackgroundColor(Color.WHITE);
//                    }
//                } else {
//                    if(MARKER_SIZE != -1) {
//                        drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
//                        layout_layers_freehand.addView(drawView);
//                        if (selectedMarker != null)
//                            selectedMarker.setBackgroundColor(getResources().getColor(R.color.layer_bg));
//                    }
//                }
//            }
//        });

        eraser_all = (ImageView) findViewById(R.id.eraserall);
        eraser_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Custom_Designing.this);
                alertDialogBuilder.setTitle("Delete All");
                // set dialog message
                alertDialogBuilder//
                        .setMessage("Do you really want to clear canvas?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ClearStaticVariables();
//                                adapter = new LayersAdapterMap(Custom_Designing.this, layersObjects);
//                                dragSortListView.setAdapter(adapter);
                                adapter.clearAllItems();
                                adapter.notifyDataSetChanged();
                                MARKER_SIZE = -1;

                                layout_layers.removeAllViews();
                                layout_layers_freehand.removeAllViews();
                                layout_layers.addView(layout_layers_freehand);
                                if (selectedMarker != null)
                                    selectedMarker.setBackgroundColor(Color.WHITE);
                            }
                        }) //
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }) //
                        .show();

            }
        });


    }

    private void setUpEraserAnimation() {
        final LinearLayout layers_eraser_more = (LinearLayout) findViewById(R.id.layers_eraser_more);
        final ImageView eraser_all = (ImageView) findViewById(R.id.eraserall);
        animationslidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animationslideup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationslideup.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                eraser_all.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        final ImageView dropdown_up = (ImageView) findViewById(R.id.dropdown_up);
        dropdown_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eraser_all.getVisibility() == View.VISIBLE) {
                    eraser_all.startAnimation(animationslideup);
                    dropdown_up.startAnimation(animationslideup);
                    dropdown_up.setImageResource(R.drawable.dropdown_down);
                } else {
                    eraser_all.setVisibility(View.VISIBLE);
                    eraser_all.startAnimation(animationslidedown);
                    dropdown_up.startAnimation(animationslidedown);
                    dropdown_up.setImageResource(R.drawable.dropdown_up);
                }

            }
        });

    }

    private void setUpGallery() {
        add_img_icon = (ImageView) findViewById(R.id.add_img_icon);
        add_img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_PIC_REQUEST);
            }
        });
    }

    private void setUpListViewDragListener() {
        dragSortListView = (DragSortListView) findViewById(R.id.dragSortListView);

        dragSortListView.setDropListener(new DragSortListView.DropListener() {
            @Override
            public void drop(int from, int to) {
                if (from != to) {
                    ViewDetails item = adapter.getItem(from);
                    adapter.remove(item);
                    adapter.insert(item, to);
                    adapter.notifyDataSetChanged();


                    View viewItem = mLayersView.get(from);
                    mLayersView.remove(viewItem);
                    mLayersView.add(to, viewItem);


//                    Log.d("ChangingFrame", "Child : " + layout_layers.getChildCount());
                    layout_layers.removeAllViews();
                    for (int i = 0; i < mLayersView.size(); i++) {
                        View mainView = mLayersView.get(i);

                        FrameLayout layout_single_temp = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);
                        if (layout_single_temp != null) {   /*Which mean it can be any Drawing Component but not FreeHand*/
                            int childcount = layout_single_temp.getChildCount();

                            Log.d("ShapeType ", "Child Count : " + childcount);
                            View v = layout_single_temp.getChildAt(0);

                            if (v instanceof ShapeView) {
                                if (ShapeView.getCurrentShapePos < mShapesArray.size()) {
                                    final ShapeView shapeView1 = ((ShapeView) mShapesArray.get(ShapeView.getCurrentShapePos));
                             //       int shape_layerID = shapeView1.getLayerID();
                                    int shape_position = shapeView1.getUniqueID();
                                    ShapeView shapeView = (ShapeView) v;
//                            shapeView.setmSelected(false);
                                    shapeView.setLayerID(i);
                                    shapeView.setUniqueID(shape_position);
                                    shapeView.invalidate();
                                    layout_single_temp.removeAllViews();
                                    layout_single_temp.addView(shapeView);
                                    layout_layers.addView(layout_single_temp);
                                }
                            } else if (v instanceof FontView) {
                                if (FontView.getCurrentShapePos < mFontsArray.size()) {
                                    final FontView fontView1 = ((FontView) mFontsArray.get(FontView.getCurrentShapePos));
                          //          int shape_layerID = fontView1.getLayerID();
                                    int shape_position = fontView1.getUniqueID();
                                    FontView fonView = (FontView) v;
//                            shapeView.setmSelected(false);
                                    Log.d("ChangingLayers", "From : " + from);
                                    Log.d("ChangingLayers", "To : " + to);
                                    fonView.setLayerID(i);
                                    fonView.setUniqueID(shape_position);
                                    fonView.invalidate();

                                    layout_single_temp.removeAllViews();
                                    layout_single_temp.addView(fonView);
                                    layout_layers.addView(layout_single_temp);
                                }
                            } else if (v instanceof GalleryImageView) {
                                if (GalleryImageView.getCurrentShapePos < mGalleryArray.size()) {
                                    final GalleryImageView ImageView1 = ((GalleryImageView) mGalleryArray.get(GalleryImageView.getCurrentShapePos));
                                 //   int shape_layerID = ImageView1.getLayerID();
                                    int shape_position = ImageView1.getUniqueID();
                                    GalleryImageView galleryView = (GalleryImageView) v;
//                            shapeView.setmSelected(false);
                                //    Log.d("ChangingLayers", "From : " + from);
                                //    Log.d("ChangingLayers", "To : " + to);
                                    galleryView.setLayerID(i);
                                    galleryView.setUniqueID(shape_position);
                                    galleryView.invalidate();

                                    layout_single_temp.removeAllViews();
                                    layout_single_temp.addView(galleryView);
                                    layout_layers.addView(layout_single_temp);
                                }
                            }



                        } else
                        {  /*Mean its FreeHand*/
                            layout_layers_freehand = (FrameLayout) mainView.findViewById(R.id.layout_layers_freehand);
//                            View v = layout_layers_freehand.getChildAt(0);
                            View[] viewArray = new View[layout_layers_freehand.getChildCount()];
                            for (int iteratr = 0; iteratr < layout_layers_freehand.getChildCount(); iteratr++) {
                                viewArray[iteratr] = layout_layers_freehand.getChildAt(iteratr);
                            }

                            layout_layers_freehand.removeAllViews();

                            for (int iteratr = 0; iteratr < viewArray.length; iteratr++) {
                                layout_layers_freehand.addView(viewArray[iteratr]);
                            }
//                            layout_layers_freehand.addView(v);
                            layout_layers.addView(layout_layers_freehand);

                            if (i > 0)
                                Toast.makeText(Custom_Designing.this, "Freehand Layer should be placed on Top", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });
        DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
            @Override
            public void remove(int which) {
                adapter.remove(adapter.getItem(which));
            }
        };

        dragSortListView.setRemoveListener(onRemove);
//      layersObjects.add(new ViewDetails("Layer # ", null, true, layout_layers));
        adapter = new LayersAdapterMap(this, layersObjects);
        dragSortListView.setAdapter(adapter);

        adapter.setOnDeleteClickListener(new LayersAdapterMap.ClickListener() {
            @Override
            public void onItemClick(int i) {


//                Toast.makeText(Custom_Designing.this, "I : " + i, Toast.LENGTH_SHORT).show();
                for (int layerId = 0; layerId < mLayersView.size(); layerId++) {
                    if (layerId == i)
                    {
                        View mainView = mLayersView.get(layerId);
                        layout_single_parent = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);

                        if (layout_single_parent != null) {  /* Mean its a component */
                            int childcount = layout_single_parent.getChildCount();

           //                 Log.d("ShapeType ", "Child Count : " + childcount);
                            for (int j = 0; j < childcount; j++) {
                                View v = layout_single_parent.getChildAt(j);
             //                   Log.d("ShapeType", i + " : " + v);

                                if (v instanceof ShapeView) {
                                    ShapeView shapeView = (ShapeView) v;
                                    int position=shapeView.getLayerID();
                                    shapeView.invalidate();
                                    mShapesArray.remove(shapeView);
                                    mShapesCount--;

                                    for(int k=position;k<mShapesArray.size();k++)
                                    {
                                        ShapeView shape= ((ShapeView) mShapesArray.get(k));
                                        int pid= shape.getLayerID();
                                        shape.setLayerID(pid - 1);

                                        int pid1= shape.getCurrentPosition();
                                        shape.setCurrentPosition(pid1 - 1);
                                    }

                                    if (mShapesCount < 0)
                                        mShapesCount = 0;
                   //                 Log.d("ShapeType ", "Its Shape Instance at : " + layerId);
//                                Toast.makeText(Custom_Designing.this, "instanceof ShapeView : ", Toast.LENGTH_SHORT).show();
                                } else if (v instanceof FontView) {
                                    FontView fontView = (FontView) v;
                                    int position=fontView.getLayerID();
                                    mFontsArray.remove(fontView);
                                    mFontsCount--;
                                    for(int k=position;k<mFontsArray.size();k++)
                                    {
                                        FontView font= ((FontView) mFontsArray.get(k));
                                        int pid= font.getLayerID();
                                        font.setLayerID(pid - 1);
                                        int pid1= font.getCurrentPosition();
                                        font.setCurrentPosition(pid1 - 1);
                                    }

                                    if (mFontsCount < 0)
                                        mFontsCount = 0;
                                    fontView.invalidate();
                                } else if (v instanceof GalleryImageView) {
                                    GalleryImageView touchView = (GalleryImageView) v;
                                    int position=touchView.getLayerID();
                                    mGalleryArray.remove(touchView);
                                    mGalleryCount--;
                                    for(int k=position;k<mGalleryArray.size();k++)
                                    {
                                        GalleryImageView gallery= ((GalleryImageView) mGalleryArray.get(k));
                                        int pid= gallery.getLayerID();
                                        gallery.setLayerID(pid - 1);
                                        int pid1= gallery.getCurrentPosition();
                                        gallery.setCurrentPosition(pid1 - 1);
                                    }

                                    if (mGalleryCount < 0)
                                        mGalleryCount = 0;
                                    touchView.invalidate();
                                }

                                layout_single_parent.removeView(v);
                            }
                            mLayersView.remove(layerId);
                           /* for(int j=final_position;j<adapter.getCount();j++)
                            {
                                int pos=adapter.getItem(j).getLayerId();
                                adapter.getItem(j).setLayerId(pos-1);
                            }*/
                        } else { /*Mean Its a Freehand*/

                            MARKER_SIZE = -1;
                            Log.d("layers_freehand", "Before layers_freehand Size : " + layout_layers_freehand.getChildCount());
                            layout_layers_freehand.removeAllViews();
                            Log.d("layers_freehand", "After layers_freehand Size : " + layout_layers_freehand.getChildCount());
                            selectedMarker.setBackgroundColor(Color.WHITE);
                            mLayersView.remove(layerId);

                        }
                    }


                }


            }
        });

        adapter.setOnVisibilityClickListener(new LayersAdapterMap.VisibilityClickListener() {

            @Override
            public void onVisibleItemClick(int position, ImageView view) {
                for (int layerId = 0; layerId < mLayersView.size(); layerId++) {
                    if (layerId == position) {
                        View mainView = mLayersView.get(layerId);
                        layout_single_parent = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);

                        if (layout_single_parent != null) {  /* Mean its a component */
                            int childcount = layout_single_parent.getChildCount();
                            for (int j = 0; j < childcount; j++) {
                                View v = layout_single_parent.getChildAt(j);

                                if (v.getVisibility() == View.VISIBLE) {
                                    v.setVisibility(View.INVISIBLE);
                                    view.setImageResource(R.drawable.eye_1);
                                } else {
                                    v.setVisibility(View.VISIBLE);
                                    view.setImageResource(R.drawable.eye);
                                }
                            }

                        } else { /*Mean Its a Freehand*/

                            MARKER_SIZE = -1;
                            if (layout_layers_freehand.getVisibility() == View.VISIBLE) {
                                layout_layers_freehand.setVisibility(View.INVISIBLE);
                                view.setImageResource(R.drawable.eye_1);
                            } else {
                                layout_layers_freehand.setVisibility(View.VISIBLE);
                                view.setImageResource(R.drawable.eye);
                            }
                            selectedMarker.setBackgroundColor(Color.WHITE);

                        }
                    }


                }
            }
        });


        adapter.setOnCheckBoxClickListener(new LayersAdapterMap.ClickListener() {
            @Override
            public void onItemClick(int i) {
//                Toast.makeText(Custom_Designing.this, "I : " + i, Toast.LENGTH_SHORT).show();
                clicked_position=-1;
                if (adapter.getItem(i).getType().equals("Freehand") )
                {
                    isEraserAllow=true;
                    eraser.setTag(R.id.eraser, 0);
                    eraser.setBackgroundColor(Color.WHITE);

                }
                else {
                    isEraserAllow=false;
                }

                if (adapter.getItem(i).getType().equals("Shape"))

                {
                    clicked_position=i;
                    click = true;

                }

                else  if (adapter.getItem(i).getType().equals("Font"))
                {
                    for(int count=0;count<=i;count++)
                    {
                        if (adapter.getItem(count).getType().equals("Font"))
                        {
                            clicked_position += 1;
                            click = true;
                        }
                    }
                    FontView.getCurrentShapePos=clicked_position;

                }


                for (int layerId = 0; layerId < mLayersView.size(); layerId++) {
                    if (layerId == i) {
                        View mainView = mLayersView.get(layerId);
//                        mainView.setVisibility(View.VISIBLE);
                        layout_single_parent = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);

                        if (layout_single_parent != null)
                        {

                            for (View viewshape : mShapesArray) {
                                ShapeView shapeView = (ShapeView) viewshape;
                                shapeView.setmSelected(false);
                                shapeView.invalidate();
                                Log.d("ShapeType ", "Its Shape at : " + layerId);


                            }
                            for (View viewfont : mFontsArray) {
                                FontView fontView = (FontView) viewfont;
                                fontView.setmSelected(false);
                                fontView.invalidate();


                            }
                            for (View viewgallery : mGalleryArray) {
                                GalleryImageView touchView = (GalleryImageView) viewgallery;
                                touchView.setmSelected(false);
                                touchView.invalidate();
                            }

                            int childcount = layout_single_parent.getChildCount();

                            Log.d("ShapeType ", "Child Count : " + childcount);
                            for (int j = 0; j < childcount; j++) {
                                View v = layout_single_parent.getChildAt(j);
                                Log.d("ShapeType", i + " : " + v);

                                if (v instanceof ShapeView) {
                                    ShapeView shapeView = (ShapeView) v;
                                    shapeView.setmSelected(true);
                                    shapeView.invalidate();

                                    Log.d("ShapeType ", "Its Shape Instance at : " + layerId);
//                                Toast.makeText(Custom_Designing.this, "instanceof ShapeView : ", Toast.LENGTH_SHORT).show();

                                    selectedComponent = 0;

                                } else if (v instanceof FontView) {
                                    FontView fontView = (FontView) v;
                                    fontView.setmSelected(true);
                                    fontView.invalidate();
//                                Toast.makeText(Custom_Designing.this, "instanceof FontView : ", Toast.LENGTH_SHORT).show();

                                    selectedComponent = 1;

                                } else if (v instanceof GalleryImageView) {
                                    GalleryImageView touchView = (GalleryImageView) v;
                                    touchView.setmSelected(true);
                                    touchView.invalidate();
                                    selectedComponent = 3;
                                }
                            }


                        }
                    }

                }


            }
        });


//        dragSortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Custom_Designing.this, "I : " + i, Toast.LENGTH_SHORT).show();
//
//                for (int layerId = 0; layerId < mLayersView.size(); layerId++) {
//                    if (layerId == i) {
//                        View mainView = mLayersView.get(layerId);
////                        mainView.setVisibility(View.VISIBLE);
//                        layout_single_parent = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);
//
//                        for (View viewshape : mShapesArray) {
//                            ShapeView shapeView = (ShapeView) viewshape;
//                            shapeView.setmSelected(false);
//                            shapeView.invalidate();
//                        }
//                        for (View viewfont : mFontsArray) {
//                            FontView fontView = (FontView) viewfont;
//                            fontView.setmSelected(false);
//                            fontView.invalidate();
//                        }
//                        for (View viewgallery : mGalleryArray) {
//                            GalleryImageView touchView = (GalleryImageView) viewgallery;
//                            touchView.setmSelected(false);
//                            touchView.invalidate();
//                        }
//
//                        int childcount = layout_single_parent.getChildCount();
//                        for (int j = 0; i < childcount; i++) {
//                            View v = layout_single_parent.getChildAt(j);
//                            Log.d("ChildCount", i + " : " + v);
//
//                            if (v instanceof ShapeView) {
//                                ShapeView shapeView = (ShapeView) v;
//                                shapeView.setmSelected(true);
//                                shapeView.invalidate();
//                                Toast.makeText(Custom_Designing.this, "instanceof ShapeView : ", Toast.LENGTH_SHORT).show();
//                            } else if (v instanceof FontView) {
//                                FontView fontView = (FontView) v;
//                                fontView.setmSelected(true);
//                                fontView.invalidate();
//                                Toast.makeText(Custom_Designing.this, "instanceof FontView : ", Toast.LENGTH_SHORT).show();
//                            } else if (v instanceof GalleryImageView) {
//                                GalleryImageView touchView = (GalleryImageView) v;
//                                touchView.setmSelected(true);
//                                touchView.invalidate();
//                            }
//                        }
//
//
//                    }
//
//
//                }
//            }
//        });

    }

    private void setUpShapeFeature() {
        mHlvSimpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setUpNewLayerAndRadioButton();
                drawShapes(i);
                addItemtoAdapter("Shape");
                //   stroke_clicked=0;
            }
        });

        stroke=(ImageView)findViewById(R.id.stroke);
        bucket=(ImageView)findViewById(R.id.bucket);
        stroke.setTag(R.id.stroke, 0);

        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stroke_clicked = 1;
                Log.e("Testing", "Stroke Clicked " + stroke_clicked);

                if (Integer.parseInt(stroke.getTag(R.id.stroke).toString()) == 0) {// Means It was Unchecked now we are Checking it
                    stroke.setBackgroundColor(getResources().getColor(R.color.layer_bg1));
                    stroke.setTag(R.id.stroke, 1);
                    bucket.setBackgroundColor(Color.WHITE);
                    bucket.setTag(R.id.bucket, 0);

                } else if (Integer.parseInt(stroke.getTag(R.id.stroke).toString()) == 1)  // Means It was Checked now we are unchecking it
                {

                    stroke.setBackgroundColor(Color.WHITE);
                    stroke.setTag(R.id.stroke, 0);
                    bucket.setBackgroundColor(getResources().getColor(R.color.layer_bg1));
                    bucket.setTag(R.id.bucket, 1);


                }
            }
        });

        bucket.setTag(R.id.bucket, 1);
        bucket.setBackgroundColor(getResources().getColor(R.color.layer_bg1));
        bucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stroke_clicked = 0;
                Log.e("Testing", "Stroke Clicked " + stroke_clicked);

                if (Integer.parseInt(bucket.getTag(R.id.bucket).toString()) == 0) {// Means It was Unchecked now we are Checking it
                    bucket.setBackgroundColor(getResources().getColor(R.color.layer_bg1));
                    bucket.setTag(R.id.bucket, 1);
                    stroke.setBackgroundColor(Color.WHITE);
                    stroke.setTag(R.id.stroke, 0);

                }
            }
        });
    }

    public void setUpNewLayerAndRadioButton() {
        RadioButton button = new RadioButton(this);
        final int id = mLayersView.size();

        button.setId(id);
        button.setTextSize(35);
        button.setText(Integer.toString(id));
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RadioGroup) view.getParent()).check(view.getId());
                int selectedID = view.getId();

                for (int layerId = 0; layerId < mLayersView.size(); layerId++) {
                    if (layerId == selectedID) {
                        View mainView = mLayersView.get(layerId);
                        layout_single_parent = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);

                        for (View viewshape : mShapesArray) {
                            ShapeView shapeView = (ShapeView) viewshape;
                            shapeView.setmSelected(false);
                            shapeView.invalidate();
                        }
                        for (View viewfont : mFontsArray) {
                            FontView fontView = (FontView) viewfont;
                            fontView.setmSelected(false);
                            fontView.invalidate();
                        }
                        for (View viewgallery : mGalleryArray) {
                            GalleryImageView galleryImageView = (GalleryImageView) viewgallery;
                            galleryImageView.setmSelected(false);
                            galleryImageView.invalidate();
                        }

                        int childcount = layout_single_parent.getChildCount();
                        for (int i = 0; i < childcount; i++) {
                            View v = layout_single_parent.getChildAt(i);
                            Log.d("ChildCount", i + " : " + v);

                            if (v instanceof ShapeView) {
                                ShapeView shapeView = (ShapeView) v;
                                shapeView.setmSelected(true);
                                shapeView.invalidate();
                            } else if (v instanceof FontView) {
                                FontView fontView = (FontView) v;
                                fontView.setmSelected(true);
                                fontView.invalidate();
                            } else if (v instanceof GalleryImageView) {
                                GalleryImageView galleryImageView = (GalleryImageView) v;
                                galleryImageView.setmSelected(true);
                                galleryImageView.invalidate();
                            }
                        }
                    }

                }

            }
        });

//        radioGroup.addView(button);
//        ((RadioButton) radioGroup.getChildAt(id)).setChecked(true);
        // Setting Frames Content
        View child = getLayoutInflater().inflate(R.layout.layers_single, null);
        mLayersView.add(child);
//        FrameLayout frameLayout = (FrameLayout) child;

        layout_single_parent = (FrameLayout) child.findViewById(R.id.layout_single_parent);
        layout_layers.addView(child);  // Adding to Actual Drawable Content
    }

   /* public void drawLogos(Drawable drawableId) {

//        BitmapDrawable drawable = (BitmapDrawable) drawableId;
        GalleryImageView newView = new GalleryImageView(this, null, drawableId, mGalleryCount, 1f, mGalleryArray.size());

        newView.setClickable(true);
        newView.setmSelected(true);
        newView.setLayerID(mLayersView.size() - 1);
        mGalleryArray.add(newView);
        layout_single_parent.addView(mGalleryArray.get(mGalleryCount));

        viewDetails = new ViewDetails("logos", newView, layout_single_parent);
        mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);
        newView.invalidate();
        mGalleryCount += 1;

    }
*/

    public void drawShapes(int shapeId) {
     /*   InputStream is = null;
        InputStream is2 = null;
//        if (shapeId == 0) {
//            is = getResources().openRawResource(R.raw.triangle);
//            is2 = getResources().openRawResource(R.raw.triangle);
//        } else if (shapeId == 1) {
//            is = getResources().openRawResource(R.raw.circle);
//            is2 = getResources().openRawResource(R.raw.circle);
//        } else if (shapeId == 2) {
//            is = getResources().openRawResource(R.raw.hex);
//            is2 = getResources().openRawResource(R.raw.hex);
//        } else if (shapeId == 3) {
//            is = getResources().openRawResource(R.raw.square);
//            is2 = getResources().openRawResource(R.raw.square);
//        } else {
//            is = getResources().openRawResource(R.raw.star);
//            is2 = getResources().openRawResource(R.raw.star);
//        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        int REQUIRED_SIZE = 200;
        int scale = 1;
        Bitmap returnedImage = null;
        switch (getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:

            case DisplayMetrics.DENSITY_MEDIUM:

            case DisplayMetrics.DENSITY_HIGH:

            case DisplayMetrics.DENSITY_XHIGH:
                // ...
//                Toast.makeText(Custom_Designing.this, "XHDPI", Toast.LENGTH_SHORT).show();
//                REQUIRED_SIZE = 300;

                if (shapeId == 0) {
                    is = getResources().openRawResource(R.raw.triangle);
                    is2 = getResources().openRawResource(R.raw.triangle);
                } else if (shapeId == 1) {
                    is = getResources().openRawResource(R.raw.circle);
                    is2 = getResources().openRawResource(R.raw.circle);
                } else if (shapeId == 2) {
                    is = getResources().openRawResource(R.raw.hex);
                    is2 = getResources().openRawResource(R.raw.hex);
                } else if (shapeId == 3) {
                    is = getResources().openRawResource(R.raw.squarenew);
                    is2 = getResources().openRawResource(R.raw.squarenew);
                } else {
                    is = getResources().openRawResource(R.raw.star);
                    is2 = getResources().openRawResource(R.raw.star);
                }

                BitmapFactory.decodeStream(is, null, opts);


//        Toast.makeText(Custom_Designing.this, "REQUIRED_SIZE : " + REQUIRED_SIZE, Toast.LENGTH_SHORT).show();

                while (opts.outWidth / scale / 2 >= REQUIRED_SIZE || opts.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;


                opts.inSampleSize = scale;
                opts.inJustDecodeBounds = false;
                is = null;
                System.gc();
                returnedImage = BitmapFactory.decodeStream(is2, null, opts);

                break;
            case DisplayMetrics.DENSITY_XXHIGH:

                if (shapeId == 0) {
                    is = getResources().openRawResource(R.raw.triangle_xxx);
                    is2 = getResources().openRawResource(R.raw.triangle_xxx);
                } else if (shapeId == 1) {
                    is = getResources().openRawResource(R.raw.circle_xxx);
                    is2 = getResources().openRawResource(R.raw.circle_xxx);
                } else if (shapeId == 2) {
                    is = getResources().openRawResource(R.raw.hex_xxx);
                    is2 = getResources().openRawResource(R.raw.hex_xxx);
                } else if (shapeId == 3) {
                    is = getResources().openRawResource(R.raw.square_xxx);
                    is2 = getResources().openRawResource(R.raw.square_xxx);
                } else {
                    is = getResources().openRawResource(R.raw.star_xxx);
                    is2 = getResources().openRawResource(R.raw.star_xxx);
                }
                BitmapFactory.decodeStream(is, null, opts);

                opts.inJustDecodeBounds = false;
                is = null;

                System.gc();
                returnedImage = BitmapFactory.decodeStream(is2, null, opts);
                is2=null;
                returnedImage = Bitmap.createScaledBitmap(returnedImage, 1024, 1024, true);

                break;
            case DisplayMetrics.DENSITY_XXXHIGH:

                if (shapeId == 0) {
                    is = getResources().openRawResource(R.raw.triangle_xxx);
                    is2 = getResources().openRawResource(R.raw.triangle_xxx);
                } else if (shapeId == 1) {
                    is = getResources().openRawResource(R.raw.circle_xxx);
                    is2 = getResources().openRawResource(R.raw.circle_xxx);
                } else if (shapeId == 2) {
                    is = getResources().openRawResource(R.raw.hex_xxx);
                    is2 = getResources().openRawResource(R.raw.hex_xxx);
                } else if (shapeId == 3) {
                    is = getResources().openRawResource(R.raw.square_xxx);
                    is2 = getResources().openRawResource(R.raw.square_xxx);
                } else {
                    is = getResources().openRawResource(R.raw.star_xxx);
                    is2 = getResources().openRawResource(R.raw.star_xxx);
                }


                BitmapFactory.decodeStream(is, null, opts);

                opts.inJustDecodeBounds = false;
                is = null;
                System.gc();
                returnedImage = BitmapFactory.decodeStream(is2, null, opts);
                is2=null;
                returnedImage = Bitmap.createScaledBitmap(returnedImage, 2048, 2048, true);
                break;

        }
*/
        ShapeView newView = new ShapeView(Custom_Designing.this, null,mShapesCount, 1f,mShapesArray.size(), 0, 0, Color.BLACK, Color.WHITE, 0, true);
        newView.setClickable(true);
        newView.setmSelected(true);
        newView.setLayerID(mLayersView.size() - 1);
        newView.setShapeId(shapeId);
        newView.setUniqueID(final_unique_id);
        //  layerObject.setUniqueID(unique_id);

        mShapesArray.add(newView);
        layout_single_parent.addView(mShapesArray.get(mShapesCount));

        viewDetails = new ViewDetails("shape", newView, layout_single_parent);
        mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);
        //   unique_id++;
        newView.invalidate();
        mShapesCount += 1;

    }
  //  public static int fselected_pos;
    public static void setUpCurrentRadioItem(int pos) {


        if (pos < mLayersView.size()) {
            adapter.setSelectedItem(pos);
            adapter.notifyDataSetChanged();

            layout_single_parent = (FrameLayout) mLayersView.get(pos).findViewById(R.id.layout_single_parent);

        }

    }


    public  static void setupVisibility()
    {

        resize_icon.setVisibility(View.VISIBLE);
        rotate_icon.setVisibility(View.VISIBLE);
        angleSeekBar.setVisibility(View.VISIBLE);
        angleSeekBar1.setVisibility(View.VISIBLE);
    }
    public void addItemtoAdapter(String type) {
        ViewDetails details = new ViewDetails("Layer # " + mLayersView.size(), Methods.viewToBitmap(mLayersView.get(mLayersView.size() - 1)), true, layout_single_parent, final_unique_id, mLayersView.size() - 1);
        details.setType(type);
        adapter.addItem(details);
        adapter.notifyDataSetChanged();
        selectedposition = mLayersView.size() - 1;
        int position1 = adapter.getCount() - 1;
        Log.e("Testing","Position13 " + position1);
        Log.e("Testing","adapter get type " + adapter.getItem(position1).getType());

        if (adapter.getItem(position1).getType().equals("Font"))
        {
            int count = 0;
           Log.e("Testing","1");
        for (int i = 0; i < adapter.getCount(); i++) {
            Log.e("Testing","1");
            if (adapter.getItem(i).getType().equals("Font")) {
                Log.e("Testing","1");
                count +=1;
            }
        }
        if (count == 1) {
            Log.e("Testing", "count " + count);
            Toast.makeText(this, "You can edit text by double tapping", Toast.LENGTH_SHORT).show();
        }
    }
        final_unique_id++;
        // angleSeekBar.setProgress(0);

    }


    private void setUpSideAnimation() {
        animationslideright = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
        animationslideleft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
//        animationslideleft.setAnimationListener(this);
    }

    private void setUpLayersSideView() {
        layers_side_frame = (FrameLayout) findViewById(R.id.layers_side_frame);

        create_logo_layer_icon = (ImageView) findViewById(R.id.create_logo_layer_icon);
        create_logo_layer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layers_side_frame.getVisibility() == View.VISIBLE) {
                    layers_side_frame.setVisibility(View.INVISIBLE);
                    layers_side_frame.startAnimation(animationslideleft);
                    create_logo_layer_icon.setImageResource(R.drawable.create_logo_layer_icon_1);

                } else {
                    layers_side_frame.setVisibility(View.VISIBLE);
                    layers_side_frame.startAnimation(animationslideright);
                    create_logo_layer_icon.setImageResource(R.drawable.create_logo_layer_icon);

                }
            }
        });

    }

    //int check_Item = -1;

    private void settUpBottomComponents() {

        selectedComponent = 2;

        checkbox_brush = (CheckBox) findViewById(R.id.checkbox_brush);
        checkbox_gallery = (CheckBox) findViewById(R.id.checkbox_gallery);
        checkbox_shape = (CheckBox) findViewById(R.id.checkbox_shape);
        checkbox_font = (CheckBox) findViewById(R.id.checkbox_font);

        all_markers_layout = (LinearLayout) findViewById(R.id.all_markers_layout);
        all_gallery_layout = (RelativeLayout) findViewById(R.id.all_gallery_layout);
        all_shapes_layout = (LinearLayout) findViewById(R.id.all_shapes_layout);
        all_fonts_layout = (HorizontalScrollView) findViewById(R.id.all_fonts_layout);

        resize_icon = (ImageView)findViewById(R.id.resize_icon);
        rotate_icon = (ImageView)findViewById(R.id.create_logo_rotate_omg);

        checkbox_brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedComponent = 2;
                resize_icon.setVisibility(View.INVISIBLE);
                rotate_icon.setVisibility(View.INVISIBLE);
                angleSeekBar.setVisibility(View.INVISIBLE);
                angleSeekBar1.setVisibility(View.INVISIBLE);
                checkbox_brush.setChecked(true);
                checkbox_shape.setChecked(false);
                checkbox_font.setChecked(false);
                checkbox_gallery.setChecked(false);

                if (isCurrentlyUsingEraser) {
                    isEraserAllow = true;
                    eraser.setBackgroundColor(getResources().getColor(R.color.layer_bg1));
                    drawView = new DrawView(Custom_Designing.this, Color.WHITE, MARKER_SIZE_ERASER);
                    layout_layers_freehand.addView(drawView);

                } else {

                    if (isEraserAllow == false) {
                        eraser.setBackgroundColor(Color.WHITE);
                    }

                    isEraserAllow = true;
                    if (MARKER_SIZE != -1) {
                        drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                        layout_layers_freehand.addView(drawView);
                        comingFromBottomBar = true;
                    }
                }
            }
        });
        checkbox_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedComponent = 3;
                resize_icon.setVisibility(View.VISIBLE);
                rotate_icon.setVisibility(View.VISIBLE);
                angleSeekBar.setVisibility(View.VISIBLE);
                angleSeekBar1.setVisibility(View.VISIBLE);
                checkbox_brush.setChecked(false);
                checkbox_shape.setChecked(false);
                checkbox_font.setChecked(false);
                checkbox_gallery.setChecked(true);

                isEraserAllow = false;
                eraser.setBackgroundColor(Color.WHITE);
                eraser.setTag(R.id.eraser, 0);

                if (MARKER_SIZE != -1) {
                    drawView = new DrawView(Custom_Designing.this, 0, MARKER_SIZE);
                    layout_layers_freehand.addView(drawView);
                }
            }
        });
        checkbox_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resize_icon.setVisibility(View.VISIBLE);
                rotate_icon.setVisibility(View.VISIBLE);
                angleSeekBar.setVisibility(View.VISIBLE);
                angleSeekBar1.setVisibility(View.VISIBLE);
                checkbox_brush.setChecked(false);
                checkbox_shape.setChecked(true);
                checkbox_font.setChecked(false);
                checkbox_gallery.setChecked(false);

                isEraserAllow = false;
                eraser.setBackgroundColor(Color.WHITE);
                eraser.setTag(R.id.eraser, 0);

                if (MARKER_SIZE != -1) {
                    drawView = new DrawView(Custom_Designing.this, 0, MARKER_SIZE);
                    layout_layers_freehand.addView(drawView);
                }
            }
        });

        checkbox_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resize_icon.setVisibility(View.VISIBLE);
                rotate_icon.setVisibility(View.VISIBLE);
                angleSeekBar.setVisibility(View.VISIBLE);
                angleSeekBar1.setVisibility(View.VISIBLE);
                checkbox_brush.setChecked(false);
                checkbox_shape.setChecked(false);
                checkbox_font.setChecked(true);
                checkbox_gallery.setChecked(false);

                isEraserAllow = false;
                eraser.setBackgroundColor(Color.WHITE);
                eraser.setTag(R.id.eraser, 0);

                if (MARKER_SIZE != -1) {
                    drawView = new DrawView(Custom_Designing.this, 0, MARKER_SIZE);
                    layout_layers_freehand.addView(drawView);
                }
            }
        });

        checkbox_brush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    all_markers_layout.setVisibility(View.VISIBLE);
                    all_gallery_layout.setVisibility(View.GONE);
                    all_shapes_layout.setVisibility(View.GONE);
                    all_fonts_layout.setVisibility(View.GONE);
                }
            }
        });
        checkbox_gallery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    all_markers_layout.setVisibility(View.GONE);
                    all_gallery_layout.setVisibility(View.VISIBLE);
                    all_shapes_layout.setVisibility(View.GONE);
                    all_fonts_layout.setVisibility(View.GONE);
                }

            }
        });

        checkbox_shape.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    all_markers_layout.setVisibility(View.GONE);
                    all_gallery_layout.setVisibility(View.GONE);
                    all_shapes_layout.setVisibility(View.VISIBLE);
                    all_fonts_layout.setVisibility(View.GONE);
                }

            }
        });
        checkbox_font.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    all_markers_layout.setVisibility(View.GONE);
                    all_gallery_layout.setVisibility(View.GONE);
                    all_shapes_layout.setVisibility(View.GONE);
                    all_fonts_layout.setVisibility(View.VISIBLE);
                }

            }
        });


        setUpCustomShapes();
        setUpGalleryImages();

    }

    private void setUpGalleryImages() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.gallery_recycler_view);
//        Methods methods = new Methods();
//        Methods.AllArraysList allArraysList = methods.Init(Custom_Designing.this, "", "image", 1, 0);
        final ArrayList<ImageObject> icon_arrayList;
//        icon_arrayList = allArraysList.get_icon_arrayList();
        icon_arrayList = SVG_View_Pager.icon_arrayList;
        ListGalleryHolder adapter = new ListGalleryHolder(this, icon_arrayList);
        adapter.setOnItemClickListener(new ListGalleryHolder.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                selectedComponent = 3;
                ImageView view = (ImageView) v;
                setUpNewLayerAndRadioButton();

                PictureDrawable pictureDrawable = (PictureDrawable) view.getDrawable();
                Bitmap bitmap = Bitmap.createBitmap(pictureDrawable.getIntrinsicWidth(), pictureDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawPicture(pictureDrawable.getPicture());
                Bitmap currentBitmap = bitmap;

                if (getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXXHIGH)
                    currentBitmap = Bitmap.createScaledBitmap(currentBitmap, 2048, 2048, true);
                else if (getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXHIGH)
                    currentBitmap = Bitmap.createScaledBitmap(currentBitmap, 1024, 1024, true);
//                else
//                    bm = Bitmap.createScaledBitmap(bm, 275, 80, true);

                GalleryImageView newView = new GalleryImageView(Custom_Designing.this, null, new BitmapDrawable(currentBitmap), mGalleryCount, 1f, mGalleryArray.size());
                newView.setClickable(true);
                newView.setmSelected(true);
                newView.setLayerID(mLayersView.size() - 1);
                newView.setUniqueID(final_unique_id);
                mGalleryArray.add(newView);
                layout_single_parent.addView(mGalleryArray.get(mGalleryCount));
                viewDetails = new ViewDetails("gallery", newView, layout_single_parent);
                mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);
                newView.invalidate();
                //  unique_id++;
                mGalleryCount += 1;

                addItemtoAdapter("Logo");
//                        drawLogos(view.getDrawable());
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("OnLongItemClick", "Position : " + position);
            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpCustomShapes() {

        mHlvSimpleList = (HorizontalListView) findViewById(R.id.hlv_shapes);
        ShapeAdapter adapter = new ShapeAdapter(this);
        mHlvSimpleList.setAdapter(adapter);

    }

    private void setUpGridFeature() {
        grid_frame = (FrameLayout) findViewById(R.id.grid_frame);
        create_logo_grid_icon = (CheckBox) findViewById(R.id.create_logo_grid_icon);
        create_logo_grid_icon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    grid_frame.setVisibility(View.VISIBLE);
                else
                    grid_frame.setVisibility(View.GONE);
            }
        });
    }

    private void setUpColor() {
        final ArrayList<ColorObject> objects = new ArrayList<>();
        int[] colorArray = getResources().getIntArray(R.array.color_array);
        for (int i = 0; i < colorArray.length; i++) {
            objects.add(new ColorObject(colorArray[i], false));
        }
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ListAdapterHolder adapter1 = new ListAdapterHolder(this, objects);
        mRecyclerView.setAdapter(adapter1);
        mRecyclerView.setHasFixedSize(true);
        adapter1.setOnItemClickListener(new ListAdapterHolder.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

//      Log.e("Testing","SELECTED COMPONENT SAMAN " + selectedComponent);
                selectedColorRGB = objects.get(position).getResID();
                isColorChanges = true;
             //   if(selectedComponent!=0 && selectedComponent!=1 && selectedComponent!=3) {
                if(checkbox_brush.isChecked())
                {
                    if (Integer.parseInt(eraser.getTag(R.id.eraser).toString()) == 0){
                        Log.e("Testing","ERASER.GET TAG " + Integer.parseInt(eraser.getTag(R.id.eraser).toString()));
                        drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                        layout_layers_freehand.addView(drawView);
                    }
                }
//                drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
//                layout_layers_freehand.addView(drawView);

                Log.e("colorchanging", "ShapeView.getCurrentShapePos : " + ShapeView.getCurrentShapePos);


                if (selectedComponent == 0)
               {
                   if(click)
                   {
                       int cpos = -1;

                       if (adapter.getItem(clicked_position).getType().equals("Shape"))

                       {

                         for(int count=0;count<=clicked_position;count++)
                        {
                        if (adapter.getItem(count).getType().equals("Shape"))
                        {
                            cpos += 1;
                            click = true;
                        }
                      }
                      ShapeView.getCurrentShapePos=cpos;

                       }

                       if (ShapeView.getCurrentShapePos < mShapesArray.size())
                       {
                        //   Log.e("Testing","Hello");
                           final ShapeView shapeView = ((ShapeView) mShapesArray.get(ShapeView.getCurrentShapePos));
                           int shape_layerID = shapeView.getLayerID();
                           int shape_position = shapeView.getUniqueID();
                           int shapeid=shapeView.getShapeId();

                           viewDetails = new ViewDetails("shapeColor", shapeView, layout_single_parent);
                           mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);

                      /*     BitmapDrawable drawable = shapeView.getmImage();
                           Bitmap bitmap = Methods.drawableToBitmap(drawable);
                           drawable=null;*/
                           if(stroke_clicked==0) {

                         //      bitmap = Methods.replaceColor(bitmap, shapeView.getInsideCircle(), selectedColorRGB);
                             //  Log.e("Testing","COLOR CODE " + shapeView.getInsideCircle() + " " + selectedColorRGB);
                              // bitmap = Methods.replaceColor(bitmap,shapeView.getInsideCircle(), )
                               newView = new ShapeView(Custom_Designing.this, null,mShapesCount, shapeView.getmScaleFactor(), shapeView.getCurrentPosition(), shapeView.mPosX, shapeView.mPosY, shapeView.getForegroundValue(), selectedColorRGB, shapeView.getRotateAngle(), shapeView.isMoveAllow());

                           }
                           else  //border clicked
                           {

                           //    bitmap = Methods.replaceColor(bitmap, shapeView.getForegroundValue(), selectedColorRGB);
                               newView = new ShapeView(Custom_Designing.this, null,mShapesCount, shapeView.getmScaleFactor(), shapeView.getCurrentPosition(), shapeView.mPosX, shapeView.mPosY,selectedColorRGB, shapeView.getInsideCircle(),shapeView.getRotateAngle(), shapeView.isMoveAllow());

                           }
                           newView.rotateAllow = true;
                           newView.setClickable(true);
                           newView.setmSelected(true);
                           newView.setLayerID(shape_layerID);
                           newView.setUniqueID(shape_position);
                           newView.setShapeId(shapeid);

                           int index = -1;
                           if (layout_single_parent != null)
                               index = layout_single_parent.indexOfChild(shapeView);

                           if (index != -1) {
                               Log.e("IndexValue", "index != -1 : " + index);
                               mShapesArray.set(shapeView.getCurrentPosition(), newView);
                               layout_single_parent.removeView(shapeView);
                               layout_single_parent.addView(newView, index);
                           } else {




                              /* This is the case due to which color is not changing */
                                    /*Adding some rewfresh logic here */

                               Log.d("IndexValue", "index : " + index);
                               Log.d("IndexValue", "Exception Arises : " + index);

                               layout_layers.removeAllViews();
                               for (int i = 0; i < mLayersView.size(); i++) {
                                   View mainView = mLayersView.get(i);

                                   FrameLayout layout_single_temp = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);
                                   if (layout_single_temp != null) {   /*Which mean it can be any Drawing Component but not FreeHand*/
                                       int childcount = layout_single_temp.getChildCount();

                                       Log.d("ShapeType ", "Child Count : " + childcount);
                                       View v1 = layout_single_temp.getChildAt(0);

                                       if (v1 instanceof ShapeView) {
                                           ShapeView shapeView1 = (ShapeView) v1;
//                            shapeView.setmSelected(false);
                                           //    Log.e("shapeview1","shapeview1 " + shapeView1.getUniqueID());
                                           shapeView1.setLayerID(i);
                                           shapeView1.setUniqueID(shape_position);
                                           shapeView1.invalidate();
                                           layout_single_temp.removeAllViews();
                                           layout_single_temp.addView(shapeView1);
                                       } else if (v1 instanceof FontView) {
                                           FontView fonView = (FontView) v1;
                                           fonView.setLayerID(i);
                                           fonView.setUniqueID(shape_position);
                                           fonView.invalidate();
                                           layout_single_temp.removeAllViews();
                                           layout_single_temp.addView(fonView);
                                       } else if (v1 instanceof GalleryImageView) {
                                           GalleryImageView galleryView = (GalleryImageView) v1;
                                           galleryView.setLayerID(i);
                                           galleryView.setUniqueID(shape_position);
                                           galleryView.invalidate();
                                           layout_single_temp.removeAllViews();
                                           layout_single_temp.addView(galleryView);
                                       }


                                       layout_layers.addView(layout_single_temp);
                                   } else {  /*Mean its FreeHand*/
                                       layout_layers_freehand = (FrameLayout) mainView.findViewById(R.id.layout_layers_freehand);
                                       View[] viewArray = new View[layout_layers_freehand.getChildCount()];
                                       for (int iteratr = 0; iteratr < layout_layers_freehand.getChildCount(); iteratr++) {
                                           viewArray[iteratr] = layout_layers_freehand.getChildAt(iteratr);
                                       }

                                       layout_layers_freehand.removeAllViews();

                                       for (int iteratr = 0; iteratr < viewArray.length; iteratr++) {
                                           layout_layers_freehand.addView(viewArray[iteratr]);
                                       }
                                       layout_layers.addView(layout_layers_freehand);


                                   }
                               }


                           }
                           viewDetails = new ViewDetails("shapeColor", newView, layout_single_parent);
                           mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);

                           newView.invalidate();
                      //     bitmap.recycle();
                           click=false;
                       }
                   }
                   else
                   {

                       //int cpos = -1;


                       if (ShapeView.getCurrentShapePos < mShapesArray.size()) {

                           final ShapeView shapeView = ((ShapeView) mShapesArray.get(ShapeView.getCurrentShapePos));
                           int shape_layerID = shapeView.getLayerID();
                           int shape_position = shapeView.getUniqueID();
                           int shapeid= shapeView.getShapeId();

                           viewDetails = new ViewDetails("shapeColor", shapeView, layout_single_parent);
                           mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);

                         //  BitmapDrawable drawable = shapeView.getmImage();
                          // Bitmap bitmap = Methods.drawableToBitmap(drawable);
                           if(stroke_clicked==0) {

                           //    bitmap = Methods.replaceColor(bitmap, shapeView.getInsideCircle(), selectedColorRGB);
                               newView = new ShapeView(Custom_Designing.this, null, mShapesCount, shapeView.getmScaleFactor(), shapeView.getCurrentPosition(), shapeView.mPosX, shapeView.mPosY, shapeView.getForegroundValue(), selectedColorRGB, shapeView.getRotateAngle(), shapeView.isMoveAllow());

                           }
                           else  //border clicked
                           {

                             //  bitmap = Methods.replaceColor(bitmap, shapeView.getForegroundValue(), selectedColorRGB);
                               newView = new ShapeView(Custom_Designing.this, null, mShapesCount, shapeView.getmScaleFactor(), shapeView.getCurrentPosition(), shapeView.mPosX, shapeView.mPosY,selectedColorRGB, shapeView.getInsideCircle(),shapeView.getRotateAngle(), shapeView.isMoveAllow());

                           }

                            newView.rotateAllow = true;
                           newView.setClickable(true);
                           newView.setmSelected(true);
                           newView.setShapeId(shapeid);
                           newView.setLayerID(shape_layerID);
                           newView.setUniqueID(shape_position);
                           int index = -1;
                           if (layout_single_parent != null)
                               index = layout_single_parent.indexOfChild(shapeView);

                           if (index != -1) {
                               Log.e("IndexValue", "index != -1 : 1" + index);
                               mShapesArray.set(shapeView.getCurrentPosition(), newView);
                               layout_single_parent.removeView(shapeView);
                               layout_single_parent.addView(newView, index);
                           } else {




                              /* This is the case due to which color is not changing */
                                    /*Adding some rewfresh logic here */

                               Log.d("IndexValue", "index : " + index);
                               Log.d("IndexValue", "Exception Arises : " + index);

                               layout_layers.removeAllViews();
                               for (int i = 0; i < mLayersView.size(); i++) {
                                   View mainView = mLayersView.get(i);

                                   FrameLayout layout_single_temp = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);
                                   if (layout_single_temp != null) {   /*Which mean it can be any Drawing Component but not FreeHand*/
                                       int childcount = layout_single_temp.getChildCount();

                                       Log.d("ShapeType ", "Child Count : " + childcount);
                                       View v1 = layout_single_temp.getChildAt(0);

                                       if (v1 instanceof ShapeView) {
                                           ShapeView shapeView1 = (ShapeView) v1;
//                            shapeView.setmSelected(false);
                                           //    Log.e("shapeview1","shapeview1 " + shapeView1.getUniqueID());
                                           shapeView1.setLayerID(i);
                                           shapeView1.setUniqueID(shape_position);
                                           shapeView1.invalidate();
                                           layout_single_temp.removeAllViews();
                                           layout_single_temp.addView(shapeView1);
                                       } else if (v1 instanceof FontView) {
                                           FontView fonView = (FontView) v1;
                                           fonView.setLayerID(i);
                                           fonView.setUniqueID(shape_position);
                                           fonView.invalidate();
                                           layout_single_temp.removeAllViews();
                                           layout_single_temp.addView(fonView);
                                       } else if (v1 instanceof GalleryImageView) {
                                           GalleryImageView galleryView = (GalleryImageView) v1;
                                           galleryView.setLayerID(i);
                                           galleryView.setUniqueID(shape_position);
                                           galleryView.invalidate();
                                           layout_single_temp.removeAllViews();
                                           layout_single_temp.addView(galleryView);
                                       }


                                       layout_layers.addView(layout_single_temp);
                                   } else {  /*Mean its FreeHand*/
                                       layout_layers_freehand = (FrameLayout) mainView.findViewById(R.id.layout_layers_freehand);
                                       View[] viewArray = new View[layout_layers_freehand.getChildCount()];
                                       for (int iteratr = 0; iteratr < layout_layers_freehand.getChildCount(); iteratr++) {
                                           viewArray[iteratr] = layout_layers_freehand.getChildAt(iteratr);
                                       }

                                       layout_layers_freehand.removeAllViews();

                                       for (int iteratr = 0; iteratr < viewArray.length; iteratr++) {
                                           layout_layers_freehand.addView(viewArray[iteratr]);
                                       }
                                       layout_layers.addView(layout_layers_freehand);


                                   }
                               }


                           }
                           viewDetails = new ViewDetails("shapeColor", newView, layout_single_parent);
                           mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);

                           newView.invalidate();
                       //    bitmap.recycle();
                       }

                   }
                } else if (selectedComponent == 1) {

                    final FontView fontView = ((FontView) mFontsArray.get(FontView.getCurrentShapePos));
                    viewDetails = new ViewDetails("fontColor", fontView, layout_single_parent);
                    mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);
                    fontView.setForeground(fontView.getInsideCircle());
                    fontView.setInsideCircle(selectedColorRGB);

                    fontView.invalidate();
                } else if (selectedComponent == 2) {
                  //  Log.e("Testing","SELECTED COMPONNET " + selectedComponent);
                    if (Integer.parseInt(eraser.getTag(R.id.eraser).toString())==0 ) {
                    //    Log.e("Testing", "ERASER.GET TAG1 " + Integer.parseInt(eraser.getTag(R.id.eraser).toString()));
                        drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                        layout_layers_freehand.addView(drawView);
                    }
                }
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        View changeColor = (View) findViewById(R.id.color_gradient);
        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker cp = new ColorPicker(Custom_Designing.this, 40, 80, 70);
                cp.show();
                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("SelectedRGB", "Color : " + cp.getColor());
                        selectedColorRGB = cp.getColor();

                        isColorChanges = true;
                        if (checkbox_brush.isChecked()) {
                            drawView = new DrawView(Custom_Designing.this, selectedColorRGB, MARKER_SIZE);
                            layout_layers_freehand.addView(drawView);
                        }
                        if (selectedComponent == 0) {

                            if (ShapeView.getCurrentShapePos < mShapesArray.size()) {
                                final ShapeView shapeView = ((ShapeView) mShapesArray.get(ShapeView.getCurrentShapePos));
                                viewDetails = new ViewDetails("shapeColor", shapeView, layout_single_parent);
                                mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);

                          /*      BitmapDrawable drawable = shapeView.getmImage();
                                Bitmap bitmap = Methods.drawableToBitmap(drawable);
*/
                                int shape_layerID = shapeView.getLayerID();
                                int shape_position = shapeView.getUniqueID();
                                int shapeid= shapeView.getShapeId();

                                if (stroke_clicked == 0) {
                                 /*   Log.e("Testing", "STROKE NOT CLICKED");
                                    Log.e("Testing", "Stroke Clicked " + stroke_clicked);
                                    Log.e("Testing", "shapeView.get foreground value  " + shapeView.getForegroundValue());
                                    Log.e("Testing", "ShapeView.get Inside Circle " + stroke_clicked);*/
                                  //  bitmap = Methods.replaceColor(bitmap, shapeView.getInsideCircle(), selectedColorRGB);
                                    newView = new ShapeView(Custom_Designing.this, null, mShapesCount, shapeView.getmScaleFactor(), shapeView.getCurrentPosition(), shapeView.mPosX, shapeView.mPosY, shapeView.getForegroundValue(), selectedColorRGB, shapeView.getRotateAngle(), shapeView.isMoveAllow());

                                } else  //border clicked
                                {
                                  /*  Log.e("Testing", "STROKE CLICKED");
                                    Log.e("Testing", "Stroke Clicked " + stroke_clicked);
                                    Log.e("Testing", "shapeView.get foreground value  " + shapeView.getForegroundValue());
                                    Log.e("Testing", "ShapeView.get Inside Circle " + stroke_clicked);*/
                                  //  bitmap = Methods.replaceColor(bitmap, shapeView.getForegroundValue(), selectedColorRGB);
                                    newView = new ShapeView(Custom_Designing.this, null, mShapesCount, shapeView.getmScaleFactor(), shapeView.getCurrentPosition(), shapeView.mPosX, shapeView.mPosY, selectedColorRGB, shapeView.getInsideCircle(), shapeView.getRotateAngle(), shapeView.isMoveAllow());

                                }

                                newView.rotateAllow = true;
                                newView.setClickable(true);
                                newView.setmSelected(true);
                                newView.setLayerID(shape_layerID);
                                newView.setUniqueID(shape_position);
                                newView.setShapeId(shapeid);
                                int index = 0;
                                if (layout_single_parent != null)
                                    index = layout_single_parent.indexOfChild(shapeView);
//                                Log.d("IndexValue", "index : " + index);

                                if (index != -1) {
                                    Log.d("IndexValue", "index != -1: " + index);
                                    mShapesArray.set(shapeView.getCurrentPosition(), newView);
                                    layout_single_parent.removeView(shapeView);
                                    layout_single_parent.addView(newView, index);
                                } else {

                                    /* This is the case due to which color is not changing */
                                    /*Adding some rewfresh logic here */

                                    Log.d("IndexValue", "index : " + index);
                                    Log.d("IndexValue", "Exception Arises : " + index);

                                    layout_layers.removeAllViews();
                                    for (int i = 0; i < mLayersView.size(); i++) {
                                        View mainView = mLayersView.get(i);

                                        FrameLayout layout_single_temp = (FrameLayout) mainView.findViewById(R.id.layout_single_parent);
                                        if (layout_single_temp != null) {   /*Which mean it can be any Drawing Component but not FreeHand*/
                                            int childcount = layout_single_temp.getChildCount();

                                            Log.d("ShapeType ", "Child Count : " + childcount);
                                            View v1 = layout_single_temp.getChildAt(0);

                                            if (v1 instanceof ShapeView) {
                                                ShapeView shapeView1 = (ShapeView) v1;
//                            shapeView.setmSelected(false);
                                                shapeView1.setLayerID(i);
                                                shapeView1.setUniqueID(shape_position);
                                                shapeView1.invalidate();
                                                layout_single_temp.removeAllViews();
                                                layout_single_temp.addView(shapeView1);
                                            } else if (v1 instanceof FontView) {
                                                FontView fonView = (FontView) v1;
                                                fonView.setLayerID(i);
                                                fonView.setUniqueID(shape_position);
                                                fonView.invalidate();

                                                layout_single_temp.removeAllViews();
                                                layout_single_temp.addView(fonView);
                                            } else if (v1 instanceof GalleryImageView) {
                                                GalleryImageView galleryView = (GalleryImageView) v1;
                                                galleryView.setLayerID(i);
                                                galleryView.setUniqueID(shape_position);
                                                galleryView.invalidate();
                                                layout_single_temp.removeAllViews();
                                                layout_single_temp.addView(galleryView);
                                            }


                                            layout_layers.addView(layout_single_temp);
                                        } else {  /*Mean its FreeHand*/
                                            layout_layers_freehand = (FrameLayout) mainView.findViewById(R.id.layout_layers_freehand);
                                            View[] viewArray = new View[layout_layers_freehand.getChildCount()];
                                            for (int iteratr = 0; iteratr < layout_layers_freehand.getChildCount(); iteratr++) {
                                                viewArray[iteratr] = layout_layers_freehand.getChildAt(iteratr);
                                            }

                                            layout_layers_freehand.removeAllViews();

                                            for (int iteratr = 0; iteratr < viewArray.length; iteratr++) {
                                                layout_layers_freehand.addView(viewArray[iteratr]);
                                            }
                                            layout_layers.addView(layout_layers_freehand);

                                        }
                                    }


                                }
                                viewDetails = new ViewDetails("shapeColor", newView, layout_single_parent);
                                mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);

                                newView.invalidate();
                           //     bitmap.recycle();
                            }
                        } else if (selectedComponent == 1) {

                            final FontView fontView = ((FontView) mFontsArray.get(FontView.getCurrentShapePos));
                            viewDetails = new ViewDetails("fontColor", fontView, layout_single_parent);
                            mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);
                            fontView.setForeground(fontView.getInsideCircle());
                            fontView.setInsideCircle(selectedColorRGB);
                            fontView.invalidate();
                        }

                        cp.dismiss();
                    }
                });
            }
        });

    }

    private void setUpFonts() {
        getAllFonts();
//        Button write_text = (Button) findViewById(R.id.write_text);
//        write_text.setOnClickListener(this);
    }

    private void getAllFonts() {
        TextView tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setTag("1");
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setTag("2");
        TextView tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setTag("3");
        TextView tv4 = (TextView) findViewById(R.id.textView4);
        tv4.setTag("4");
        TextView tv5 = (TextView) findViewById(R.id.textView5);
        tv5.setTag("5");
        TextView tv6 = (TextView) findViewById(R.id.textView6);
        tv6.setTag("6");
        TextView tv7 = (TextView) findViewById(R.id.textView7);
        tv7.setTag("7");
        TextView tv8 = (TextView) findViewById(R.id.textView8);
        tv8.setTag("8");
        TextView tv9 = (TextView) findViewById(R.id.textView9);
        tv9.setTag("9");
        TextView tv10 = (TextView) findViewById(R.id.textView10);
        tv10.setTag("10");
        TextView tv11 = (TextView) findViewById(R.id.textView11);
        tv11.setTag("11");
        TextView tv12 = (TextView) findViewById(R.id.textView12);
        tv12.setTag("12");
        TextView tv13 = (TextView) findViewById(R.id.textView13);
        tv13.setTag("13");
        TextView tv14 = (TextView) findViewById(R.id.textView14);
        tv14.setTag("14");
        TextView tv15 = (TextView) findViewById(R.id.textView15);
        tv15.setTag("15");

        tv1 = Methods.SetFontStyle(tv1, 1, this);
        tv2 = Methods.SetFontStyle(tv2, 2, this);
        tv3 = Methods.SetFontStyle(tv3, 3, this);
        tv4 = Methods.SetFontStyle(tv4, 4, this);
        tv5 = Methods.SetFontStyle(tv5, 5, this);
        tv6 = Methods.SetFontStyle(tv6, 6, this);
        tv7 = Methods.SetFontStyle(tv7, 7, this);
        tv8 = Methods.SetFontStyle(tv8, 8, this);
        tv9 = Methods.SetFontStyle(tv9, 9, this);
        tv10 = Methods.SetFontStyle(tv10, 10, this);
        tv11 = Methods.SetFontStyle(tv11, 11, this);
        tv12 = Methods.SetFontStyle(tv12, 12, this);
        tv13 = Methods.SetFontStyle(tv13, 13, this);
        tv14 = Methods.SetFontStyle(tv14, 14, this);
        tv15 = Methods.SetFontStyle(tv15, 15, this);

        tv1.setOnClickListener(Font_Click_Listener);
        tv2.setOnClickListener(Font_Click_Listener);
        tv3.setOnClickListener(Font_Click_Listener);
        tv4.setOnClickListener(Font_Click_Listener);
        tv5.setOnClickListener(Font_Click_Listener);
        tv6.setOnClickListener(Font_Click_Listener);
        tv7.setOnClickListener(Font_Click_Listener);
        tv8.setOnClickListener(Font_Click_Listener);
        tv9.setOnClickListener(Font_Click_Listener);
        tv10.setOnClickListener(Font_Click_Listener);
        tv11.setOnClickListener(Font_Click_Listener);
        tv12.setOnClickListener(Font_Click_Listener);
        tv13.setOnClickListener(Font_Click_Listener);
        tv14.setOnClickListener(Font_Click_Listener);
        tv15.setOnClickListener(Font_Click_Listener);

    }

    public static void font_dialog()
    {
        selectedComponent=1;
     //   fontsubmitview =fontview;
        final FontView fontview = ((FontView) mFontsArray.get(FontView.getCurrentShapePos));

      fontdialog = new Dialog(a, R.style.Dialog);
        fontdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fontdialog.setContentView(R.layout.dialog_font);

        fontdialog.findViewById(R.id.cancel_Red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FontView.font_dialog_state=0;
                fontdialog.dismiss();
            }
        });
     final EditText fontTextViewText = (EditText) fontdialog.findViewById(R.id.dialog_fontText);
        fontTextViewText.setText(fontview.getFontText());
        Button submit = (Button) fontdialog.findViewById(R.id.dialog_submit);
        fontTextViewText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    int fontNumber = 0;
                    try {
                        FontView fontview = ((FontView) mFontsArray.get(FontView.getCurrentShapePos));
                        fontNumber = fontview.getFontPosition();
                        //   fontNumber = Integer.parseInt(fontsubmitview.getTag().toString());
                        Log.e("Testing", "Font Number " + fontNumber);
                    } catch (Exception e) {
                    }
                    String content = fontTextViewText.getText().toString();
                    if (content.length() < 1)
                        fontTextViewText.setError("Minimum 1 letters required");
                    else if (content.length() > 25)
                        fontTextViewText.setError("Maximum 25 letters allowed");
                    else {
                        selectedComponent = 1;
                        fontTextViewText.setError(null);
                        Typeface typeface = Methods.getFontStyle(fontNumber, a);
                        BitmapDrawable testBitmapDrawable = writeOnDrawable1(R.raw.fontimage, fontTextViewText.getText().toString(), typeface, fontNumber);
                        fontview.setmImage(testBitmapDrawable);
                        fontview.setFontText(fontTextViewText.getText().toString());
                        fontview.invalidate();

                        FontView.font_dialog_state=0;
                        fontdialog.dismiss();
                        testBitmapDrawable = null;
                    }

                }
                return false;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fontNumber = 0;
                try {
                    FontView fontview = ((FontView) mFontsArray.get(FontView.getCurrentShapePos));
                    fontNumber = fontview.getFontPosition();
                    //   fontNumber = Integer.parseInt(fontsubmitview.getTag().toString());
                    Log.e("Testing", "Font Number " + fontNumber);
                } catch (Exception e) {
                }
                String content = fontTextViewText.getText().toString();
                if (content.length() < 1)
                    fontTextViewText.setError("Minimum 1 letters required");
                else if (content.length() > 25)
                    fontTextViewText.setError("Maximum 25 letters allowed");
                else {
                    selectedComponent = 1;
                    fontTextViewText.setError(null);
                    Typeface typeface = Methods.getFontStyle(fontNumber, a);
                    BitmapDrawable testBitmapDrawable = writeOnDrawable1(R.raw.fontimage, fontTextViewText.getText().toString(), typeface, fontNumber);
                    fontview.setmImage(testBitmapDrawable);
                    fontview.setFontText(fontTextViewText.getText().toString());
                    fontview.invalidate();

                    FontView.font_dialog_state=0;
                    fontdialog.dismiss();
                    testBitmapDrawable = null;
                }

            }
        });
        if(!fontdialog.isShowing())
            fontdialog.show();


        //return true;
    }
    private View.OnClickListener Font_Click_Listener = new View.OnClickListener() {
        @Override
        public void onClick(final View fontview) {
            selectedComponent=1;
            fontsubmitview = fontview;
            dialog = new Dialog(Custom_Designing.this, R.style.Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_font);

            dialog.findViewById(R.id.cancel_Red).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            fontTextViewText = (EditText) dialog.findViewById(R.id.dialog_fontText);
            Button submit = (Button) dialog.findViewById(R.id.dialog_submit);
            fontTextViewText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        font_submit();
                    }
                    return false;
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    font_submit();
                }
            });
            dialog.show();

        }
    };

    public void font_submit() {
        // angleSeekBar.
        // angleSeekBar.setProgress(0);
        // angleSeekBar.invalidate();
        int fontNumber = 0;
        try {
            fontNumber = Integer.parseInt(fontsubmitview.getTag().toString());
            Log.e("Testing","Font Number " +fontNumber);
        } catch (Exception e) {
        }
        content = fontTextViewText.getText().toString();
        if (content.length() < 1)
            fontTextViewText.setError("Minimum 1 letters required");
        else if (content.length() > 25)
            fontTextViewText.setError("Maximum 25 letters allowed");
        else {
            selectedComponent = 1;
            fontTextViewText.setError(null);
            setUpNewLayerAndRadioButton();
            drawFont(fontNumber, fontTextViewText.getText().toString());
            addItemtoAdapter("Font");

            //  angleSeekBar.setProgress(0);
            dialog.dismiss();
        }

    }


    public void drawFont(int FontPosition, String Message) {
        Typeface typeface = Methods.getFontStyle(FontPosition, Custom_Designing.this);
        BitmapDrawable testBitmapDrawable = writeOnDrawable(R.raw.fontimage, Message, typeface, FontPosition);


        FontView newView = new FontView(Custom_Designing.this, Message, testBitmapDrawable, mFontsCount, 1f, mFontsArray.size(), 0, 0, Color.WHITE, Color.BLACK, 0, typeface,FontPosition);
        newView.setClickable(true);
        newView.setmSelected(true);
        newView.setLayerID(mLayersView.size() - 1);
        newView.setUniqueID(final_unique_id);
        //  layerObject.setUniqueID(unique_id);

        mFontsArray.add(newView);
//        layout_font.addView(mFontsArray.get(mFontsCount));
        layout_single_parent.addView(mFontsArray.get(mFontsCount));
        newView.invalidate();
//        viewDetails = new ViewDetails("font", newView, layout_font);
        viewDetails = new ViewDetails("font", newView, layout_single_parent);
        mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);
        // unique_id++;
        mFontsCount += 1;
       testBitmapDrawable = null;
    }

    public static BitmapDrawable writeOnDrawable1(int drawableId, String text, Typeface typeface, int fontPosition) {
        Bitmap bm = BitmapFactory.decodeResource(a.getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

//        (275 % 500 = 0.55)  for Normal DPI   80 % 500 = 0.16
//        (0.55 * 1024 = 563)  for Normal XXXHDPI   && 0.16 * 1024 = 164
//        (0.55 * 1024 = 563)  for Normal XXXHDPI   && 0.16 * 1024 = 164  // Changes
       /* Log.d("Testing", " getResources().getDisplayMetrics().densityDpi : " + a.getResources().getDisplayMetrics().densityDpi);
       Log.e("Testing", "CONTENT LENGTH " + text.length());*/
        if (a.getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXXHIGH) {
            //    Log.e("Testing","HELLO ");

            Log.e("Testing","XXXHIGH");
            if(text.length()>0 && text.length()<=5)
            {
          //  {Log.e("Testing", "Content length1 " + text.length());

                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 170, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 200, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 150, 100, true);
            }
            else if(text.length()>5 && text.length()<=10)
            {
            //    Log.e("Testing", "Content length2 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 340, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 400, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 300, 100, true);
            }
            else if(text.length()>10 && text.length()<=15)
            {
              //  Log.e("Testing", "Content length3 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 510, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 600, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 450, 100, true);
            }
            else if(text.length()>15 && text.length()<=20)
            {
             //   Log.e("Testing", "Content length4 " + text.length());
                //   Log.e("Testing", "Content length1 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 680, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 800, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 600, 100, true);
            }

            else
            {
             //   Log.e("Testing", "Content length5 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 850, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 1000, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 750, 100, true);
            }

        } else if (a.getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXHIGH)
        {
            Log.e("Testing","XXHIGH");
            if(text.length()>0 && text.length()<=5)
            {
          //  {Log.e("Testing", "Content length1 " + text.length());

                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 200, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 230, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 170, 100, true);
            }
            else if(text.length()>5 && text.length()<=10)
            {
              //  Log.e("Testing", "Content length2 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 400, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 460, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 340, 100, true);
            }
            else if(text.length()>10 && text.length()<=15)
            {
             //   Log.e("Testing", "Content length3 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 600, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 690, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 510, 100, true);
            }
            else if(text.length()>15 && text.length()<=20)
            {
              //  Log.e("Testing", "Content length4 " + text.length());
                //   Log.e("Testing", "Content length1 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 800, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 920, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 680, 100, true);
            }

            else
            {
             //   Log.e("Testing", "Content length5 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 1000, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 1150, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 850, 100, true);
            }

        } else {
            if(text.length()>0 && text.length()<=5)
            {
               // Log.e("Testing", "Content length1 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 130, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 160, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 110, 80, true);
            }
            else if(text.length()>5 && text.length()<=10)
            {
             //   Log.e("Testing", "Content length2 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 260, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 320, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 220, 80, true);
            }

            else if(text.length()>10 && text.length()<=15)
            {
             //   Log.e("Testing", "Content length3 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 390, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 480, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 330, 80, true);
            }

            else if(text.length()>15 && text.length()<=20)
            {
             //   Log.e("Testing", "Content length4 " + text.length());

                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 520, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 640, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 440, 80, true);
            }

            else {
              //  Log.e("Testing", "Content length5 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 650, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 800, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 550, 80, true);
            }
        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        Canvas canvas = new Canvas(bm);
        text = "";
        canvas.drawText(text, bm.getWidth() / 2, bm.getHeight() / 1.6f, paint);
        return new BitmapDrawable(a.getResources(), bm);
    }

    public BitmapDrawable writeOnDrawable(int drawableId, String text, Typeface typeface, int fontPosition) {
        Bitmap bm = BitmapFactory.decodeResource(a.getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

//        (275 % 500 = 0.55)  for Normal DPI   80 % 500 = 0.16
//        (0.55 * 1024 = 563)  for Normal XXXHDPI   && 0.16 * 1024 = 164
//        (0.55 * 1024 = 563)  for Normal XXXHDPI   && 0.16 * 1024 = 164  // Changes
    //    Log.e("Testing", " getResources().getDisplayMetrics().densityDpi : " + getResources().getDisplayMetrics().densityDpi);
    //    Log.e("Testing", "Content length " + content.length());
        if (a.getResources().getDisplayMetrics().densityDpi >= DisplayMetrics.DENSITY_XXXHIGH) {
        //    Log.e("Testing","HELLO ");

            Log.e("Testing","XXXHIGH");
            if(content.length()>0 && content.length()<=5)
            {
         //   {Log.e("Testing", "Content length1 " + content.length());

                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 170, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 200, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 150, 100, true);
            }
            else if(content.length()>5 && content.length()<=10)
            {
            //    Log.e("Testing", "Content length2 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 340, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 400, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 300, 100, true);
            }
            else if(content.length()>10 && content.length()<=15)
            {
             //   Log.e("Testing", "Content length3 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 510, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 600, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 450, 100, true);
            }
            else if(content.length()>15 && content.length()<=20)
            {
             //   Log.e("Testing", "Content length4 " + content.length());
                //   Log.e("Testing", "Content length1 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 680, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 800, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 600, 100, true);
            }

            else
            {
             //   Log.e("Testing", "Content length5 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 850, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 1000, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 750, 100, true);
            }


        }  else if (a.getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXHIGH)
        {
            Log.e("Testing","XXHIGH");
            if(text.length()>0 && text.length()<=5)
            {//Log.e("Testing", "Content length1 " + text.length());

                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 200, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 230, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 170, 100, true);
            }
            else if(text.length()>5 && text.length()<=10)
            {
               // Log.e("Testing", "Content length2 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 400, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 460, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 340, 100, true);
            }
            else if(text.length()>10 && text.length()<=15)
            {
               // Log.e("Testing", "Content length3 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 600, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 690, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 510, 100, true);
            }
            else if(text.length()>15 && text.length()<=20)
            {
             //   Log.e("Testing", "Content length4 " + text.length());
                //   Log.e("Testing", "Content length1 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 800, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 920, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 680, 100, true);
            }

            else
            {
             //   Log.e("Testing", "Content length5 " + text.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 1000, 100, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 1150, 100, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 850, 100, true);
            }

        } else {

            if(content.length()>0 && content.length()<=5)
            {
               // Log.e("Testing", "Content length1 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 130, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 160, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 110, 80, true);
            }
            else if(content.length()>5 && content.length()<=10)
            {
              //  Log.e("Testing", "Content length2 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 260, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 320, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 220, 80, true);
            }

            else if(content.length()>10 && content.length()<=15)
            {
             //   Log.e("Testing", "Content length3 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 390, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 480, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 330, 80, true);
            }

            else if(content.length()>15 && content.length()<=20)
            {
             //   Log.e("Testing", "Content length4 " + content.length());

                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 520, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 640, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 440, 80, true);
            }

            else {
              //  Log.e("Testing", "Content length5 " + content.length());
                if (fontPosition == 1)
                    bm = Bitmap.createScaledBitmap(bm, 650, 80, true);
                else if (fontPosition == 4)
                    bm = Bitmap.createScaledBitmap(bm, 800, 80, true);
                else
                    bm = Bitmap.createScaledBitmap(bm, 550, 80, true);
            }
        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        Canvas canvas = new Canvas(bm);
        text = "";
        canvas.drawText(text, bm.getWidth() / 2, bm.getHeight() / 1.6f, paint);
        return new BitmapDrawable(a.getResources(), bm);
    }

    private void setUpRotateBar() {
        angleSeekBar = (SeekBar) findViewById(R.id.angle_seek_bar);
        angleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (selectedComponent == 0) {
                    try
                    {
                    if (mShapesArray.size() > 0) {
                        ShapeView shapeView = (ShapeView) mShapesArray.get(ShapeView.getCurrentShapePos);
                        shapeView.getRotateDrawable(progress);

                    }
                    }
                     catch(Exception e)
                    {
                    Log.e("Testing","Exception " + e);
                    }

                } else if (selectedComponent == 1) {
                    try {
                    if (mFontsArray.size() > 0) {
                        FontView fontView = (FontView) mFontsArray.get(FontView.getCurrentShapePos);
                        fontView.getRotateDrawable(progress);
                    }
                    }
                    catch(Exception e)
                    {
                        Log.e("Testing","Exception " + e);
                    }
                } else if (selectedComponent == 3) {
                    try {
                        if (mGalleryArray.size() > 0) {
                            GalleryImageView galleryImageView = (GalleryImageView) mGalleryArray.get(GalleryImageView.getCurrentShapePos);
                            galleryImageView.getRotateDrawable(progress);
                        }
                    }
                    catch(Exception e)
                    {
                        Log.e("Testing","Exception " + e);
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        angleSeekBar1 = (SeekBar) findViewById(R.id.angle_seek_bar_1);
        //angleSeekBar1.setProgress(90);
        angleSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (selectedComponent == 0) {
                    try {
                        if (mShapesArray.size() > 0) {

                            ShapeView shapeView = (ShapeView) mShapesArray.get(ShapeView.getCurrentShapePos);
                            float progress1 = (progress + 50) / 100f;
                            shapeView.scaleshape(progress1);
                            //   shapeView.getRotateDrawable(progress);
                        }
                    }
                    catch(Exception e)
                    {
                        Log.e("Testing","Exception " + e);
                    }
                } else if (selectedComponent == 1) {
                    try {
                        if (mFontsArray.size() > 0) {
                            FontView fontView = (FontView) mFontsArray.get(FontView.getCurrentShapePos);
                            float progress1 = (progress + 50) / 100f;
                            fontView.scaleshape(progress1);
                        }
                    }
                    catch(Exception e)
                    {
                        Log.e("Testing", "Exception " + e);
                    }
                } else if (selectedComponent == 3) {
                    if (mGalleryArray.size() > 0) {
                        try {
                            GalleryImageView galleryImageView = (GalleryImageView) mGalleryArray.get(GalleryImageView.getCurrentShapePos);
                            float progress1=(progress + 50)/100f;
                            galleryImageView.scaleshape(progress1);

                        }
                        catch(Exception e)
                        {
                            Log.e("Testing","Exception " + e);
                        }

                    }
                }
               /* else
                {
                    Toast.makeText(Custom_Designing.this, "No layer is selected", Toast.LENGTH_SHORT).show();
                }
*/
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void onClick(View view) {

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ImagePath", "requestCode : " + requestCode);

        if (requestCode == GALLERY_PIC_REQUEST) {
            try {
                setUpNewLayerAndRadioButton();
                Uri selectedImage = data.getData();
                getPath(selectedImage);
                InputStream is;
                is = getContentResolver().openInputStream(selectedImage);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, opts);
                //The new size we want to scale to
                final int REQUIRED_SIZE = 200;

                //Find the correct scale value. It should be the power of 2.
                int scale = 1;
                while (opts.outWidth / scale / 2 >= REQUIRED_SIZE || opts.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;

                if ((getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXXHIGH)
                        || (getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXHIGH))
                    ;
                else
                    opts.inSampleSize = scale;

                opts.inSampleSize = scale;
                opts.inJustDecodeBounds = false;
                is = null;
                System.gc();
                InputStream is2 = getContentResolver().openInputStream(selectedImage);

                Bitmap returnedImage = BitmapFactory.decodeStream(is2, null, opts);
                is2=null;
                if (getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXXHIGH)
                    returnedImage = Bitmap.createScaledBitmap(returnedImage, 2048, 2048, true);
                else if (getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XXHIGH)
                    returnedImage = Bitmap.createScaledBitmap(returnedImage, 1024, 1024, true);

                GalleryImageView newView = new GalleryImageView(this, null, new BitmapDrawable(returnedImage), mGalleryCount, 1f, mGalleryArray.size());
                newView.setImageLocation(getPath(selectedImage));
                newView.setClickable(true);
                newView.setmSelected(true);
                newView.setLayerID(mLayersView.size() - 1);
                newView.setUniqueID(final_unique_id);
                // layerObject.setUniqueID(unique_id);

                mGalleryArray.add(newView);
                layout_single_parent.addView(mGalleryArray.get(mGalleryCount));
                viewDetails = new ViewDetails("gallery", newView, layout_single_parent);
                mRecentViewMaps.put(mRecentViewMaps.size(), viewDetails);

                newView.invalidate();
                //   unique_id++;
                mGalleryCount += 1;
           //    returnedImage.recycle();
                addItemtoAdapter("Gallery");

            } catch (FileNotFoundException e) {

            } catch (NullPointerException e) {
            }
        }
    }

    public String getPath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        Log.d("ImagePath", cursor.getString(columnIndex));
        return cursor.getString(columnIndex);
    }


    private void ClearStaticVariables() {
        mShapesArray = new ArrayList<View>();
        mFontsArray = new ArrayList<View>();
        mGalleryArray = new ArrayList<View>();
        mShapesCount = 0;
        mFontsCount = 0;
        mGalleryCount = 0;
        mLayersView = new ArrayList<View>();

        mRecentViewMaps = new HashMap<Integer, ViewDetails>();
        viewDetails = null;

        layersObjects = new ArrayList<ViewDetails>();
        final_unique_id=0;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        ClearStaticVariables();
//        startActivity(new Intent(Custom_Designing.this, MainScreen.class));
    }
}