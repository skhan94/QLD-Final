package com.fsd.logodesign;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fsd.logodesign.adapters.PageIconAdapter;
import com.fsd.logodesign.adapters.SmoothViewPager;
import com.fsd.logodesign.methods.CustomFontPagerTitleStrip;
import com.fsd.logodesign.methods.CustomMultiPartEntity;
import com.fsd.logodesign.methods.DelayedTextWatcher;
import com.fsd.logodesign.methods.Methods;
import com.fsd.logodesign.methods.MultiSpinner;
import com.fsd.logodesign.methods.ProgressHUD;
import com.fsd.logodesign.methods.SlideButton;
import com.fsd.logodesign.methods.StickerSpan;
import com.fsd.logodesign.methods.VerticalTextView;
import com.fsd.logodesign.objectclasses.ImageObject;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.pixplicity.sharp.Sharp;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;


public class SVG_View_Pager extends Activity {
    //    final String appPackageName = getPackageName();
    String msg = "Thank you for contacting us! We will contact you shortly. Your Order ID is ";
    //----------------------------------------------------
//    123123
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
  //  private static final String CONFIG_CLIENT_ID = "AWJ-AxAptJj_TRuqbHOJGfgEBZ_bAbX0ygIYeNFlPVvqsQUcAdvpbno8hILR"; // OLD PRODUCTION ID
      private static final String CONFIG_CLIENT_ID = "AX9Mc-e4iDEjxpbF2q6Ew3kYcX8r7zmd2wcuqMWdhbYsDOBiaBx-GxAdjhKuzeb1oXl_lOAZkKzVr4vD";  //NEW PRODUCTION ID
   // private static final String CONFIG_CLIENT_ID = "AayiXwgyFJM2E5YL4OnOAjU_i9K2_RMg_OTRd47GrY3JP9Oh0RokcMMvSvOO5RoKMdcVQxlg22mcjLPn";  //NEW SANDBOX ID
//    private static final String CONFIG_CLIENT_ID = "AZD6UXoPtLnnBhtJy93EXKSTupfxRuYOeez4LbB0dClWzGXIhgB7LxMwcLIxmlTC6c4epiBMsO89KsvU"; // Humza Sandbox Account humza1@fsdolutions.com
//    private static final String CONFIG_CLIENT_ID = "AbhcHqKFummjgGXD6f0zkFQBbvjNRKi09jzG-IEvVIVwByYFHC6EWMdeH8oFScqCXsi-9hf46XZk0-uU";  // Humza Sandbox Account humza3@fsdolutions.com
//    private static final String CONFIG_CLIENT_ID = "AXJSVtCDRubBDLmB-Of_mnafvL29TzS-KlScHRS2zRC84BQbu691dCz69FDB51uqSE5Bldg7IfZ7pzQ9"; //for sandbox
//    123213
    //private static String CONTACT_US_URL = "https://www.webdevelopersrus.com/php/shariq/qldplus/public/api/contactus" ;

    private static String CONTACT_US_URL = "http://webdevelopersrus.com/php/shariq/qldplus/public/api/contactus";
    private static String ORDER_US_URL = "http://webdevelopersrus.com/php/shariq/qldplus/public/api/order";
    private static String PAYMENT_US_URL = "http://webdevelopersrus.com/php/shariq/qldplus/public/api/payment";
//    private static final String CONFIG_RECEIVER_EMAIL = "paypal@fsdsolutions.com";
    //----------------------------------------------------

    private static int pack_add_file_cal;
    private static String Order_id;
    private final int item_price = 10;

    SmoothViewPager viewpager;
    ViewPagerAdapter pagerAdapter;
    CustomFontPagerTitleStrip pagerTitleStrip;
    LinearLayout final_Logo;
    FrameLayout final_card;

    private String CompanyName = "";
    private int ImageColor = 1;
    private int FontStyle = 0;
    private String ImageName = "image";
    String fileName = "";

    //------------------------------------

    static ArrayList<ImageObject> icon_arrayList;
    static ArrayList<ImageObject> font_arrayList;
    static ArrayList<ImageObject> color_arrayList;

    //------------------------------------

    static PageIconAdapter iconAdapter;
    static PageIconAdapter fontAdapter;
    static PageIconAdapter colorAdapter;

    //------------------------------------

    MultiSpinner additional_formats_spinner;
    MultiSpinner.MultiSpinnerListener multiSpinnerListener;
    ArrayList<String> formats_list;
    List<String> methods_options;
    //------------------------------------

    GridView icon_gridview;
    GridView font_gridview;
    GridView color_gridview;


    //------------------------------------
    Typeface typeface;
    EditText Company_Name;
    EditText Contact_name, Contact_name_Contact;
    EditText Contact_number, Contact_number_Contact;
    EditText Contact_email, Contact_email_Contact;
    EditText Contact_message, Contact_message_Contact;
    Button Send_Contact;
//    Spinner Meth_pay_spinner;

    Sharp ins_sharp;

    ProgressHUD ringProgressDialog;
    public static Activity activity;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .acceptCreditCards(true);


    InterstitialAd interAd;


    // Web work
    int timeoutConnection = 100000;
    int timeoutSocket = 150000;
    View contact_us_view;
    ImageView contact_us_img;

    File LogoDesign_dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_view_pager);
        viewpager = (SmoothViewPager) findViewById(R.id.viewpager);
        activity=this;
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        setUpInterstitialAds();

        pagerTitleStrip = (CustomFontPagerTitleStrip) findViewById(R.id.pager_title_strip);

        contact_us_img = (ImageView) findViewById(R.id.contact_us_img);
        contact_us_view = (View) findViewById(R.id.contact_us_view1);
        contact_us_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Custom_Designing.viewpager_state==0) {
                    contact_us_img.setVisibility(View.GONE);
                    viewpager.setCurrentItem(0);
                }
                else
                {
                    removeErorContact();
                    viewpager.setCurrentItem(1);
                    Custom_Designing.viewpager_state=0;
                }
            }
        });

        //pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        pagerAdapter = new ViewPagerAdapter();

        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(6);

        viewpager.setPageTransformer(false, new ZoomOutPageTransformer());
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                /*if(position!=7)
                {
                    Custom_Designing.viewpager_state=0;
                }*/
                if (position == 6)
                    displayAd();
                if (position == 1)
                {
                    viewpager.setSwipeAble(false);
                    removeErorContact();
                    contact_us_img.setBackgroundResource(R.drawable.contact_us);
                 //   contact_us_img.setBackground(R.drawable.contact_us);
             //       contact_us_img.setImageBackground(R.drawable.contact_us);
                    contact_us_img.setVisibility(View.VISIBLE);

                } else {
                    Log.e("Testing","Position " + position);
                    Log.e("Testing","View pager state " + Custom_Designing.viewpager_state);
                    if (position == 7) {
                        removeError();
                        if (Custom_Designing.viewpager_state == 1) {

//                            contact_us_img.setVisibility(View.GONE);
//                            viewpager.setSwipeAble(true);
                            contact_us_img.setBackgroundResource(R.drawable.order_header);
                            contact_us_img.setVisibility(View.VISIBLE);
                            viewpager.setSwipeAble(false);
                        } else {
                            contact_us_img.setVisibility(View.GONE);
                            viewpager.setSwipeAble(true);
                        }
                    } else {
                  /*  if (position != 0)*/
                        contact_us_img.setVisibility(View.GONE);
                        viewpager.setSwipeAble(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //------------------------------------------------------------------------------
        Methods methods = new Methods();
        Methods.AllArraysList allArraysList = methods.Init(SVG_View_Pager.this, CompanyName, ImageName, ImageColor, FontStyle);
        icon_arrayList = allArraysList.get_icon_arrayList();
        font_arrayList = allArraysList.get_font_arrayList();
        color_arrayList = allArraysList.get_color_arrayList();
        formats_list = allArraysList.get_formats_list();
        methods_options = allArraysList.get_methods_options();
        //------------------------------------------------------------------------------

        fontAdapter = new PageIconAdapter(SVG_View_Pager.this, font_arrayList);
        iconAdapter = new PageIconAdapter(SVG_View_Pager.this, icon_arrayList);
        colorAdapter = new PageIconAdapter(SVG_View_Pager.this, color_arrayList);

        viewpager.setSwipeAble(true);
        viewpager.setCurrentItem(1);


        int type = getIntent().getIntExtra("type" , -1);
        Log.d("FireBaseTest", "Version : " + type);
        final String pkg = getIntent().getStringExtra("pkg");
        final String title = getIntent().getStringExtra("title");
        final String content = getIntent().getStringExtra("content");
        Log.d("FireBaseTest", "Pkg : " + pkg);
        Log.d("FireBaseTest", "Version : " + type);
        if (type == 1) {
            showAppsDialog(activity , pkg , title , content);
        }



//        Runnable second_Task = new Runnable() {
//            public void run() {
//                try {
//                    sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//
////                            Shr_Preferences shr_preferences = new Shr_Preferences();
////                            int openTimes = shr_preferences.setUpPrefrences(SVG_View_Pager.this);
////                            if (openTimes == 3) /*Show App Dialog for Quick Gif and Other Apps*/
//                                showAppsDialog(SVG_View_Pager.this , "" , "" , "");
//
//                        }
//                    });
//                }
//            }
//        };
//        Thread thread = new Thread(second_Task);
//        thread.start();


    }

    // -----------------------------------
    public void showAppsDialog(Context context, final String pkg, String title, String text) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setContentView(R.layout.dialog_apps);

        TextView notification_title = (TextView) dialog.findViewById(R.id.notification_title);
        notification_title.setText(title);

        TextView notification_Text = (TextView) dialog.findViewById(R.id.notification_Text);
        notification_Text.setText(text);


        TextView notification_ok = (TextView) dialog.findViewById(R.id.notification_ok);
        notification_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkg)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pkg)));
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

    }
    // -------------------------------------------

    private void setUpInterstitialAds() {
        interAd = new InterstitialAd(this);
//        interAd.setAdUnitId("ca-app-pub-5616184812651997/4133331467"); // This is my account ID
        interAd.setAdUnitId("ca-app-pub-3514887027893252/1712554121");

        AdRequest adRequest = new AdRequest.Builder().build();
        interAd.loadAd(adRequest);
        interAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d("AdsLog", "Ad is loaded");
            }

            @Override
            public void onAdClosed() {
                Log.d("AdsLog", "Ad is Closed");
                AdRequest adRequest = new AdRequest.Builder().build();
                interAd.loadAd(adRequest);

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Log.d("AdsLog", "Ad is FailedToLoad");
                AdRequest adRequest = new AdRequest.Builder().build();
                interAd.loadAd(adRequest);

            }
        });
    }

    // -------------------------------------------

    private void displayAd() {
        if (interAd.isLoaded()) {
            interAd.show();
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {

        SparseArray<View> views = new SparseArray<View>();

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub

            int[] List = {
                    R.drawable.global_contact_icon_1,
                    R.drawable.main_menu,
                    R.drawable.global_add_icon_1,
                    R.drawable.global_choose_icon_1,
                    R.drawable.global_font_icon_1,
                    R.drawable.global_color_icon_1,
                    R.drawable.global_complete_icon_1,
                    R.drawable.order
            };
            SpannableStringBuilder sb = new SpannableStringBuilder(" ");//myTitle[position]); // space added before text for convenience

            Drawable drawable = SVG_View_Pager.this.getResources().getDrawable(List[position]);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            StickerSpan span = new StickerSpan(getApplicationContext(), ((BitmapDrawable) drawable).getBitmap());
            //ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//            sb.append("Position " + position);
//            Log.d("CharPosition" ,"Position " + position );
            return sb;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 8;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View container, int position, Object obj) {
            View view = (View) obj;
            ((SmoothViewPager) container).removeView(view);
            views.remove(position);
            view = null;
        }


        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int resId = 0;
            switch (position) {
                case 1:
                    resId = R.layout.pager_mainmenu;
                    View main_menu = inflater.inflate(resId, null);
                    ((SmoothViewPager) container).addView(main_menu, 0);


                    Button logo_button = (Button) main_menu.findViewById(R.id.mainmenu_logo);
//                    Button contact_button = (Button) main_menu.findViewById(R.id.mainmenu_contact);
                    logo_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

//                            showLoadingDialog(SVG_View_Pager.this);
                            ringProgressDialog = ProgressHUD.show(SVG_View_Pager.this, "Loading \n Please Wait", true, false, null);
                            Intent Main_Menu = new Intent(SVG_View_Pager.this, Custom_Designing.class);
                            startActivity(Main_Menu);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

//
//
//                            Intent Main_Menu = new Intent(SVG_View_Pager.this, Custom_Designing.class);
//                            startActivity(Main_Menu);
//                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        }
                    });

                    Log.d("Changes" , "btn Click");
                    Button createLogo = (Button) main_menu.findViewById(R.id.mainmenu_createLogo);
                    createLogo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewpager.setCurrentItem(2);
                        }
                    });

//                    contact_button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            viewpager.setCurrentItem(0);
//                        }
//                    });

                    views.put(position, main_menu);
                    return main_menu;
                case 2:
                    resId = R.layout.pager_name_screen;
                    View name_screen = inflater.inflate(resId, null);

                    //----------------------------------------------------
                    FrameLayout imagesetter = (FrameLayout) name_screen.findViewById(R.id.imagesetter);

                    AdView adView = new AdView(SVG_View_Pager.this);
                    adView.setAdSize(AdSize.SMART_BANNER);
                    adView.setAdUnitId("ca-app-pub-3514887027893252/7059364126");

                    imagesetter.addView(adView);
                    AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                    adView.loadAd(adRequestBuilder.build());


                    Company_Name = (EditText) name_screen.findViewById(R.id.Add_Text_edit_text);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Company_Name.getWindowToken(), 0);
                    Company_Name.setText(CompanyName);
                    Company_Name.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                // position the text type in the left top corner
                                Company_Name.setGravity(Gravity.LEFT | Gravity.CENTER);
                            } else {
                                // no text entered. Center the hint text.
                                Company_Name.setGravity(Gravity.CENTER);
                            }
                        }
                    });
                    if (Company_Name.getText().toString().length() > 0) {
                        Company_Name.setGravity(Gravity.LEFT | Gravity.CENTER);
                    } else {
                        Company_Name.setGravity(Gravity.CENTER);
                    }
                    Company_Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            // TODO Auto-generated method stub
                            if (hasFocus) {
                                if (Company_Name.getText().toString().length() > 0) {
                                    Company_Name.setText(Company_Name.getText().toString());

                                } else {
                                    Company_Name.setText(" ");

                                }

                            }
                        }
                    });

                    Company_Name.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            // TODO Auto-generated method stub

                            boolean handled = false;
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                Company_Name.clearFocus();
                                handled = true;
                            }
                            return handled;
                        }
                    });

                    SlideButton slide_button = (SlideButton) name_screen.findViewById(R.id.pager_name_slide_button);
                    slide_button.setSlideButtonListener(new SlideButton.SlideButtonListener() {

                        @SuppressWarnings("deprecation")
                        @Override
                        public void handleSlide() {
                            // TODO Auto-generated method stub


                            CompanyName = Company_Name.getText().toString();
                            if (CompanyName.length() == 0 | CompanyName.equals(" ")) {

                                AlertDialogMessageShow(SVG_View_Pager.this, "Please enter the company name to proceed .");
                            } else if (CompanyName.length() > 25) {
                                AlertDialogMessageShow(SVG_View_Pager.this, "Maximum character limit is 25");
                            } else {

                                new AsyncTask<Void, Void, Void>() {
                                    ProgressHUD mProgressHUD;

                                    protected void onPreExecute() {

                                        mProgressHUD = ProgressHUD.show(SVG_View_Pager.this, "Loading \n Please Wait", true, false, null);
                                    }

                                    @Override
                                    protected Void doInBackground(Void... arg0) {
                                        // TODO Auto-generated method stub
                                        try {
                                            sleep(200);
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    refresherName();
                                                    int position = viewpager.getCurrentItem();
                                                    pagerAdapter.notifyDataSetChanged();
                                                    viewpager.setCurrentItem(position);
                                                }

                                            });
                                            sleep(300);
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    protected void onPostExecute(Void result) {

                                        if (mProgressHUD != null) {
                                            mProgressHUD.dismiss();
                                        }

                                        viewpager.setSwipeAble(true);
                                        int position = viewpager.getCurrentItem();
                                        viewpager.setCurrentItem(position + 1, false);


                                    }

                                }.execute();
                            }
                        }
                    });
                    slide_button.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            // TODO Auto-generated method stub
                            if (progress >= 93) {
                                seekBar.setProgress(93);
                            } else if (progress <= 7) {
                                seekBar.setProgress(7);
                            }

                        }
                    });

                    //----------------------------------------------------

                    ((SmoothViewPager) container).addView(name_screen, 0);
                    views.put(position, name_screen);
                    return name_screen;

                case 3:
                    resId = R.layout.pager_icon_screen;
                    View icon_screen = inflater.inflate(resId, null);

                    //----------------------------------------------------

                    //----------------------------------------------------


                    icon_gridview = (GridView) icon_screen.findViewById(R.id.icon_gridview);
                    icon_gridview.setAdapter(iconAdapter);

                    icon_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, final int position, long arg3) {
                            // TODO Auto-generated method stub

                            ImageName = icon_arrayList.get(position).getImageName();
                            if (icon_arrayList.get(position).getStatus() != 1) {

                                new AsyncTask<Void, Void, Void>() {
                                    ProgressHUD mProgressHUD;

                                    protected void onPreExecute() {
                                        mProgressHUD = ProgressHUD.show(SVG_View_Pager.this, "Loading \n Please Wait", true, false, null);
                                    }

                                    @Override
                                    protected Void doInBackground(Void... arg0) {
                                        // TODO Auto-generated method stub
                                        try {
                                            sleep(200);
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    refresherIcon(position);
                                                    int posit = viewpager.getCurrentItem();
                                                    pagerAdapter.notifyDataSetChanged();
                                                    viewpager.setCurrentItem(posit);
                                                }

                                            });
                                            sleep(300);
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    protected void onPostExecute(Void result) {


                                        if (mProgressHUD != null) {
                                            mProgressHUD.dismiss();
                                        }

                                        int posi = viewpager.getCurrentItem();
                                        viewpager.setCurrentItem(posi + 1, false);

                                    }
                                }.execute();
                            } else {
                                Toast.makeText(getApplicationContext(), "This is Already Selected", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    //----------------------------------------------------

                    //----------------------------------------------------


                    ((SmoothViewPager) container).addView(icon_screen, 0);
                    views.put(position, icon_screen);
                    return icon_screen;
                case 4:
                    resId = R.layout.pager_font_screen;
                    View font_screen = inflater.inflate(resId, null);

                    //----------------------------------------------------

                    //----------------------------------------------------


                    font_gridview = (GridView) font_screen.findViewById(R.id.font_gridview);
                    font_gridview.setAdapter(fontAdapter);

                    font_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, final int position, long arg3) {
                            // TODO Auto-generated method stub

                            FontStyle = font_arrayList.get(position).getFontStyle();
                            if (font_arrayList.get(position).getStatus() != 1) {
                                new AsyncTask<Void, Void, Void>() {
                                    ProgressHUD mProgressHUD;

                                    protected void onPreExecute() {
                                        mProgressHUD = ProgressHUD.show(SVG_View_Pager.this, "Loading \n Please Wait", true, false, null);
                                    }

                                    @Override
                                    protected Void doInBackground(Void... arg0) {
                                        // TODO Auto-generated method stub
                                        try {
                                            sleep(200);
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    refresherFont(position);
                                                    int position = viewpager.getCurrentItem();
                                                    pagerAdapter.notifyDataSetChanged();
                                                    viewpager.setCurrentItem(position);
                                                }

                                            });
                                            sleep(300);
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    protected void onPostExecute(Void result) {

                                        if (mProgressHUD != null) {
                                            mProgressHUD.dismiss();
                                        }

                                        int position = viewpager.getCurrentItem();
                                        viewpager.setCurrentItem(position + 1, false);

                                    }
                                }.execute();
                            } else {
                                Toast.makeText(getApplicationContext(), "This is Already Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    //----------------------------------------------------

                    //----------------------------------------------------


                    ((SmoothViewPager) container).addView(font_screen, 0);
                    views.put(position, font_screen);
                    return font_screen;

                case 5:
                    resId = R.layout.pager_color_screen;
                    View color_screen = inflater.inflate(resId, null);

                    //----------------------------------------------------

                    //----------------------------------------------------


                    color_gridview = (GridView) color_screen.findViewById(R.id.color_gridview);
                    color_gridview.setAdapter(colorAdapter);

                    color_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, final int position, long arg3) {
                            // TODO Auto-generated method stub
                            ImageColor = color_arrayList.get(position).getImageColor();
                            if (color_arrayList.get(position).getStatus() != 1) {
                                new AsyncTask<Void, Void, Void>() {
                                    ProgressHUD mProgressHUD;

                                    protected void onPreExecute() {
                                        mProgressHUD = ProgressHUD.show(SVG_View_Pager.this, "Loading \n Please Wait", true, false, null);
                                    }

                                    @Override
                                    protected Void doInBackground(Void... arg0) {
                                        // TODO Auto-generated method stub
                                        try {
                                            sleep(500);
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    protected void onPostExecute(Void result) {
                                        refresherColor(position);
                                        int position = viewpager.getCurrentItem();
                                        pagerAdapter.notifyDataSetChanged();
                                        viewpager.setCurrentItem(position);

                                        if (mProgressHUD != null) {
                                            mProgressHUD.dismiss();
                                        }
                                        viewpager.post(new Runnable() {

                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                int position = viewpager.getCurrentItem();

                                                viewpager.setCurrentItem(position + 1, false);

                                                if (!viewpager.getSwipwAble()) {
                                                    viewpager.setSwipeAble(true);
                                                }
                                            }
                                        });

                                    }
                                }.execute();

                            } else {
                                Toast.makeText(getApplicationContext(), "This is Already Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //----------------------------------------------------

                    //----------------------------------------------------


                    ((SmoothViewPager) container).addView(color_screen, 0);
                    views.put(position, color_screen);
                    return color_screen;

                case 6:
                    resId = R.layout.pager_complete_screen;
                    // displayAd();
                    View complete_screen = inflater.inflate(resId, null);
                    //----------------------------------------------------
                    //----------------------------------------------------
                    WindowManager manager = (WindowManager) SVG_View_Pager.this.getSystemService(Context.WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    int width = display.getWidth();

//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (width / 1.5), (int) (width / 1.5), Gravity.CENTER | Gravity.TOP);

                    final_Logo = (LinearLayout) complete_screen.findViewById(R.id.final_Logo);
                    ImageView final_imageview = (ImageView) complete_screen.findViewById(R.id.final_imageview);
                    TextView final_textview_down = (TextView) complete_screen.findViewById(R.id.final_textview);
                    final_textview_down = Methods.SetFontStyle(final_textview_down, FontStyle, SVG_View_Pager.this); // this is setting font style
                    final_textview_down.setText("" + CompanyName);
                    final_imageview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                    ins_sharp.loadAsset(getAssets(), "svg/" + ImageName + ImageColor + ".svg")
//                            .into(final_imageview);


//                    SVGBuilder svgBuilder_1 = null;
                    Drawable drawable_1 = Sharp.loadAsset(getAssets(), "svg/" + ImageName + ImageColor + ".svg").getDrawable(final_imageview);
                    //                        svgBuilder_1 = new SVGBuilder().readFromAsset(getAssets(), "svg/" + ImageName + ImageColor + ".svg");
//                        svgBuilder_1.build().getDrawable();
                    final_imageview.setImageDrawable(drawable_1);
//                    final_Logo.setLayoutParams(params);


//                    final_imageview.setLayoutParams(params);


                    //---------------------------------------------------
                    final_card = (FrameLayout) complete_screen.findViewById(R.id.final_card);
                    ImageView preview_imageview = (ImageView) complete_screen.findViewById(R.id.preview_imageview);

                    TextView preview_textview = (VerticalTextView) complete_screen.findViewById(R.id.preview_textview);
                    preview_textview = Methods.SetFontStyle(preview_textview, FontStyle, SVG_View_Pager.this);
                    preview_textview.setText("" + CompanyName);

                    preview_imageview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    preview_imageview.setImageDrawable(drawable_1);

                    //---------------------------------------------------

                    CheckBox preview_selection = (CheckBox) complete_screen.findViewById(R.id.preview_selection);
                    preview_selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                            // TODO Auto-generated method stub
                            if (checked) {
                                final_Logo.setVisibility(View.VISIBLE);
                                final_card.setVisibility(View.GONE);
                            } else {
                                final_Logo.setVisibility(View.GONE);
                                final_card.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                    //

                    Button button = (Button) complete_screen.findViewById(R.id.download_jpg);
                    button.setOnClickListener(new View.OnClickListener() {
                        int value = 0;

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SVG_View_Pager.this);
                            alertDialogBuilder.setTitle("Save");
                            // set dialog message
                            alertDialogBuilder//
                                    .setMessage("Do you really want to save jpg?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (!final_Logo.isShown()) {
                                                value = 1;
                                                final_Logo.setVisibility(View.VISIBLE);
                                                final_card.setVisibility(View.GONE);
                                            }
                                            Methods methods = new Methods();
                                            methods.ImageStore(SVG_View_Pager.this, final_Logo, fileName, true);

                                            if (value == 1) {
                                                final_Logo.setVisibility(View.GONE);
                                                final_card.setVisibility(View.VISIBLE);
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


                        }
                    });

                    Button contact_us = (Button) complete_screen.findViewById(R.id.contact_us);
                    contact_us.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Custom_Designing.viewpager_state=0;
                            // TODO Auto-generated method stub
                            new AsyncTask<Void, Void, Void>() {
                                ProgressHUD mProgressHUD;

                                protected void onPreExecute() {
                                    mProgressHUD = ProgressHUD.show(SVG_View_Pager.this, "Loading \n Please Wait", true, false, null);
                                    Contact_name.requestFocus();
                                }

                                @Override
                                protected Void doInBackground(Void... arg0) {
                                    // TODO Auto-generated method stub
                                    try {
                                        sleep(500);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    return null;
                                }

                                protected void onPostExecute(Void result) {

                                    if (mProgressHUD != null) {
                                        mProgressHUD.dismiss();
                                    }
                                    viewpager.post(new Runnable() {

                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            int position = viewpager.getCurrentItem();
                                            Log.e("Testing","View Pager Position " + position );
                                            viewpager.setCurrentItem(position + 1, false);
                                            Custom_Designing.viewpager_state=0;
                                        }
                                    });

                                }
                            }.execute();

                        }
                    });


                    //----------------------------------------------------
                    //----------------------------------------------------

                    ((SmoothViewPager) container).addView(complete_screen, 0);
                    views.put(position, complete_screen);
                    return complete_screen;

                case 7:
                    SVG_View_Pager.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    resId = R.layout.pager_contact_us;
                    View contact_screen = inflater.inflate(resId, null);

                    Contact_name = (EditText) contact_screen.findViewById(R.id.contact_name);
                    Contact_number = (EditText) contact_screen.findViewById(R.id.contact_number);
                    Contact_email = (EditText) contact_screen.findViewById(R.id.contact_email);
                    Contact_message = (EditText) contact_screen.findViewById(R.id.contact_message);
                 //   removeErorContact();
                    removeError();
                  //  ClearFields();
                    //----------------------------------------------------
                    //----------------------- Contact Name
                    Contact_name.addTextChangedListener(new DelayedTextWatcher(2000) {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            super.beforeTextChanged(s, start, count, after);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                // position the text type in the left top corner

                                Contact_name.setGravity(Gravity.LEFT | Gravity.CENTER);
                            } else {
                                // no text entered. Center the hint text.
                                Contact_name.setGravity(Gravity.CENTER);
                            }

                        }

                        @Override
                        public void afterTextChangedDelayed(Editable s) {
                            Methods methods = new Methods();
                            if (Contact_name.getText().toString().length() == 0) {
                                Contact_name.setError("Empty Field");
                            } else if (!methods.validName(Contact_name.getText().toString())) {
                                Contact_name.setError("Invalid Name");
                            } else {
                                Contact_name.setError(null);
                            }
                        }
                    });

                    //----------------------- Contact Name
                    if (Contact_name.getText().toString().length() > 0) {
                        Contact_name.setGravity(Gravity.LEFT | Gravity.CENTER);
                    } else {
                        Contact_name.setGravity(Gravity.CENTER);
                    }
                    //----------------------- Contact Name
                    Contact_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (Contact_name.getText().toString().length() > 0) {
                                } else {
                                    Contact_name.setHint("");
                                    Contact_name.setGravity(Gravity.LEFT | Gravity.CENTER);
                                }
                            } else {
                                if (Contact_name.getText().toString().length() == 0) {
                                    Contact_name.setHint(R.string.hint_enter_full_name);
                                    Contact_name.setGravity(Gravity.CENTER);
                                }
                                if (Contact_number.getText().toString().length() == 0) {
                                    Contact_number.setText("+");
                                    Contact_number.setSelection(Contact_number.length());
                                }
                            }


                        }
                    });

                    //----------------------- Contact Name
                    //----------------------------------------------------

                    //----------------------------------------------------
                    //----------------------- Contact Number

                    Contact_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                Toast.makeText(SVG_View_Pager.this, "Focused", Toast.LENGTH_SHORT).show();
                                if (Contact_number.getText().toString().length() > 0) {
                                    Contact_number.setGravity(Gravity.LEFT);
                                    Contact_number.setText("+");
                                    Contact_number.setSelection(Contact_number.length());
                                }
                            }
                        }
                    });

                    Contact_number.addTextChangedListener(new DelayedTextWatcher(2000) {


                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() == 0) {
                                Contact_number.setGravity(Gravity.LEFT);
                                Contact_number.setText("+");
                                Contact_number.setSelection(Contact_number.length());

                            } else if (s.length() > 0) {
                                // position the text type in the left top corner
//                                Contact_number.setText("+");
                                Contact_number.setGravity(Gravity.LEFT | Gravity.CENTER);
                            } else {
                                // no text entered. Center the hint text.
                                Contact_number.setHint(R.string.hint_enter_contact_number);
                                Contact_number.setGravity(Gravity.CENTER);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            super.afterTextChanged(s);

                        }

                        @Override
                        public void afterTextChangedDelayed(Editable s) {

                            Methods methods = new Methods();
                            if (Contact_number.getText().toString().length() == 0) {
                                Contact_number.setError("Empty Field");
                            } else if (!methods.validPhone(Contact_number.getText().toString())) {
                                Contact_number.setError("Invalid Number");
                            } else {
                                Contact_number.setError(null);
                            }
                        }
                    });

                    //----------------------- Contact Number
                    if (Contact_number.getText().toString().length() > 0) {
                        Contact_number.setGravity(Gravity.LEFT | Gravity.CENTER);
                    } else {
                        Contact_number.setGravity(Gravity.CENTER);
                    }
                    //----------------------- Contact Number
                    Contact_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (Contact_number.getText().toString().length() > 0) {
                                } else {
                                    Contact_number.setHint("");
                                    Contact_number.setGravity(Gravity.LEFT | Gravity.CENTER);
                                }
                            } else {
                                if (Contact_number.getText().toString().length() == 0) {
                                    Contact_number.setHint(R.string.hint_enter_contact_number);
                                    Contact_number.setGravity(Gravity.CENTER);
                                }
                            }
                        }
                    });

                    //----------------------- Contact Number
                    //----------------------------------------------------

                    //----------------------------------------------------
                    //----------------------- Contact Email
                    Contact_email.addTextChangedListener(new DelayedTextWatcher(2000) {

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                // position the text type in the left top corner
                                Contact_email.setGravity(Gravity.LEFT | Gravity.CENTER);
                            } else {
                                // no text entered. Center the hint text.
                                Contact_email.setGravity(Gravity.CENTER);
                            }

                        }

                        @Override
                        public void afterTextChangedDelayed(Editable s) {
                            Methods methods = new Methods();
                            if (Contact_email.getText().toString().length() == 0) {
                                Contact_email.setError("Empty Field");
                            } else if ((!methods.validEmail(Contact_email.getText().toString()))) {
                                Contact_email.setError("Invalid Email");
                            } else {
                                Contact_email.setError(null);
                            }
                        }
                    });

                    //----------------------- Contact Email
                    if (Contact_email.getText().toString().length() > 0) {
                        Contact_email.setGravity(Gravity.LEFT | Gravity.CENTER);
                    } else {
                        Contact_email.setGravity(Gravity.CENTER);
                    }
                    //----------------------- Contact Email
                    Contact_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (Contact_email.getText().toString().length() > 0) {
                                } else {
                                    Contact_email.setHint("");
                                    Contact_email.setGravity(Gravity.LEFT | Gravity.CENTER);
                                }
                            } else {
                                if (Contact_email.getText().toString().length() == 0) {
                                    Contact_email.setHint(R.string.hint_enter_email);
                                    Contact_email.setGravity(Gravity.CENTER);
                                }
                            }
                        }
                    });

                    //----------------------- Contact Email
                    //----------------------------------------------------

                    //----------------------------------------------------
                    //----------------------- Contact Message

                    //----------------------- Contact Message
                    //----------------------------------------------------

//                    Meth_pay_spinner = (Spinner) contact_screen.findViewById(R.id.method_payment_spinner);


                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SVG_View_Pager.this, R.layout.custom_spinner, methods_options);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//                    Meth_pay_spinner.setPrompt("             Payment ");
//                    Meth_pay_spinner.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter, R.layout.custom_spinner, SVG_View_Pager.this));

                    //----------------------------------------------------
                    additional_formats_spinner = (MultiSpinner) contact_screen.findViewById(R.id.multi_Spinner);
                    additional_formats_spinner.setPrompt("       File Formats");
                    multiSpinnerListener = new MultiSpinner.MultiSpinnerListener() {

                        @Override
                        public void onItemsSelected(boolean[] selected) {

                            int[] selectedItems = new int[formats_list.size()];
                            for (int i = 0; i < selected.length; i++) {
                                if (selected[i]) {
                                    selectedItems[i] = 1;
                                } else {
                                    selectedItems[i] = 0;
                                }
                            }
                            int b = 1;
                            int a = 0;
                            for (int i = 0; i < selectedItems.length; i++) {

                                if (selectedItems[i] == 1) {
                                    b = b + 1;
                                    // calculating the value
                                    a = (b - 1) * (Integer.valueOf(item_price));
                                }
                            }
                            pack_add_file_cal = a;

                        }
                    };

                    StringBuffer spinnerBuffer = new StringBuffer();

                    for (int i = 0; i < formats_list.size(); i++) {
                        Log.e("Testing","Formats_list.size " +formats_list.size());
                        spinnerBuffer.append(formats_list.get(i));
                        if (i != (formats_list.size() - 1)) {
                            spinnerBuffer.append(Methods.strSeparator);
                        }

                    }
//                    int counter=0;
//                    for(int i=0;i<formats_list.size();i++)
//                    {
//
//                    }
                    Log.e("Testing","spinner buffer " + spinnerBuffer.toString());
                    if(spinnerBuffer.toString().equals("") || spinnerBuffer.toString().equals(null))
                    {
                        Log.e("Testing","spinner buffer 1" + spinnerBuffer.toString());
                        spinnerBuffer.append("Choose formats");
                    }

                    //------------------------

                    additional_formats_spinner.setItems(formats_list, spinnerBuffer.toString(), multiSpinnerListener);


                    Button Submit_button = (Button) contact_screen.findViewById(R.id.sumbit_details);
                    Submit_button.setOnClickListener(new View.OnClickListener() {
                        private String name, number, email, message, method, formats;

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Methods methods = new Methods();
                            if (Contact_name.getText().toString().length() == 0) {
                                Contact_name.setError("Empty Field");
                            } else if (!methods.validName(Contact_name.getText().toString())) {
                                Contact_name.setError("Invalid Name");
                            } else if (Contact_number.getText().toString().length() == 0) {
                                Contact_number.setError("Empty Field");
                            } else if (!methods.validPhone(Contact_number.getText().toString())) {
                                Contact_number.setError("Invalid Number");
                            } else if (Contact_email.getText().toString().length() == 0) {
                                Contact_email.setError("Empty Field");
                            } else if ((!methods.validEmail(Contact_email.getText().toString()))) {
                                Contact_email.setError("Invalid Email");
                            } else if (Contact_number.getText().toString().length() < 10) {
                                Contact_number.setError("Phone must be at least 10 digits");
                            } else if (Contact_message.getText().toString().length() < 10) {
                                Contact_message.setError("Message must be at least 10 characters");
                            } else if (CompanyName.length() == 0 && Custom_Designing.viewpager_state==0) {
                               // if(Custom_Designing.viewpager_state==0) {
                                    Toast.makeText(SVG_View_Pager.this, "Empty Company Name", Toast.LENGTH_SHORT).show();
                               // }
                            } else if (additional_formats_spinner.getSelectedItem() == null) {
                                Toast.makeText(SVG_View_Pager.this, "Empty File Formats", Toast.LENGTH_SHORT).show();
                            } else if (additional_formats_spinner.getSelectedItem().toString().length() <= 1) {
                                Toast.makeText(SVG_View_Pager.this, "Empty File Formats", Toast.LENGTH_SHORT).show();
                            } /*else if (check_paypal.isChecked() && check_creditcard.isChecked()) {
                                Toast.makeText(SVG_View_Pager.this, "Select only one payment method", Toast.LENGTH_SHORT).show();
                            } else if (!check_paypal.isChecked() && !check_creditcard.isChecked()) {
                                Toast.makeText(SVG_View_Pager.this, "Select payment method", Toast.LENGTH_SHORT).show();
                            }*/ else if (!Methods.isNetworkConnected(SVG_View_Pager.this)) {
                                Toast.makeText(SVG_View_Pager.this, "Internet not reachable", Toast.LENGTH_SHORT).show();
                            }
                             /*else if (Meth_pay_spinner.getSelectedItem() == null) {
                                Toast.makeText(SVG_View_Pager.this, "Empty Payment Method", Toast.LENGTH_SHORT).show();
                            } else if (Meth_pay_spinner.getSelectedItem().toString().length() == 0) {
                                Toast.makeText(SVG_View_Pager.this, "Empty Payment Method", Toast.LENGTH_SHORT).show();
                            }*/
                            else {

                                name = Contact_name.getText().toString();
                                number = Contact_number.getText().toString();
                                email = Contact_email.getText().toString();
                                message = Contact_message.getText().toString();
//                                method = Meth_pay_spinner.getSelectedItem().toString();
//                                if (check_creditcard.isChecked())
//                                    method = "Credit Card";
//                                else if (check_paypal.isChecked())
                                method = "PayPal";
                                formats = additional_formats_spinner.getSelectedItem().toString();
                                //Toast.makeText(SVG_View_Pager.this, "formats : " +formats, Toast.LENGTH_SHORT).show();
                                Log.d("serverResponse", "formats : " + formats);
//                                Contact_name.setText("");
//                                Contact_number.setText("");
//                                Contact_email.setText("");
//                                Contact_message.setText("");


                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SVG_View_Pager.this, R.layout.custom_spinner, methods_options);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                Meth_pay_spinner.setPrompt("             Payment ");


                                multiSpinnerListener = new MultiSpinner.MultiSpinnerListener() {

                                    @Override
                                    public void onItemsSelected(boolean[] selected) {

                                        int[] selectedItems = new int[formats_list.size()];
                                        for (int i = 0; i < selected.length; i++) {
                                            if (selected[i]) {
                                                selectedItems[i] = 1;
                                            } else {
                                                selectedItems[i] = 0;
                                            }
                                        }
                                        int b = 1;
                                        int a = 0;
                                        for (int i = 0; i < selectedItems.length; i++) {

                                            if (selectedItems[i] == 1) {
                                                b = b + 1;
                                                // calculating the value
                                                a = (b - 1) * (Integer.valueOf(item_price));
                                            }
                                        }
                                        pack_add_file_cal = a;

                                    }
                                };
                                StringBuffer spinnerBuffer = new StringBuffer();
                                for (int i = 0; i < formats_list.size(); i++) {
                                    spinnerBuffer.append(formats_list.get(i));
                                    if (i != (formats_list.size() - 1)) {
                                        spinnerBuffer.append(Methods.strSeparator);
                                    }
                                }
                                additional_formats_spinner.setItems(formats_list, spinnerBuffer.toString(), multiSpinnerListener);

                                new AsyncTask<HttpResponse, Integer, String>() {

                                    long totalSize;
                                    private ProgressDialog progressDialog;

                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();

                                        progressDialog = ProgressDialog
                                                .show(SVG_View_Pager.this, "", "Loading...");
                                    }

                                    @Override
                                    protected String doInBackground(HttpResponse... arg0) {

                                        try {
                                            final String LogoDesign_Name;
                                            LogoDesign_dir = new File(Environment.getExternalStorageDirectory(), "LogoDesign");

                                            if(Custom_Designing.viewpager_state==1)
                                            {

                                              /*  File LogoDesign = new File(Environment.getExternalStorageDirectory(), "LogoDesign" + File.separator + Custom_Designing.custom_fileName);
                                                Log.e("Testing","Logo Design " + LogoDesign);
                                                LogoDesign_Name=LogoDesign.toString();*/

                                                LogoDesign_Name= Custom_Designing.custom_fileName;
                                            }
                                            else {
                                                Methods methods = new Methods();
                                                LogoDesign_Name = methods.ImageStore(SVG_View_Pager.this, final_Logo, fileName, false);
                                            }

                                            HttpParams httpParameters = new BasicHttpParams();
                                            httpParameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                                             // Set the timeout in milliseconds until a connection is established.
                                            // The default value is zero, that means the timeout is not used.
                                           int timeoutConnection = 0;

                                            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

                                            // Set the default socket timeout (SO_TIMEOUT)
                                            // in milliseconds which is the timeout for waiting for data.
                                            int timeoutSocket = 0;

                                            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                                            HttpClient httpClient = new DefaultHttpClient();
                                            HttpContext httpContext = new BasicHttpContext();
                                            HttpPost httppost = new HttpPost(ORDER_US_URL);//http://webdevelopersrus.com/php/sadiq/logo-design/api/contactus");
                                            httppost.setParams(httpParameters);
                                            CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(new CustomMultiPartEntity.ProgressListener() {
                                                @Override
                                                public void transferred(long num) {
                                                    Log.e("stats", "long num / " + num + " // " + totalSize + "  // " + ((num / (float) totalSize) * 100));
                                                    publishProgress((int) ((num / (float) totalSize) * 100));
                                                }
                                            });
                                            //Log.e("file_name",fileName);
                                            File output = new File(LogoDesign_dir, LogoDesign_Name);
                                            Log.e("Testing","Output " + output);
                                            ContentBody cbFile = new FileBody(output, "image/jpg");
                                            multipartContent.addPart("file", cbFile);
                                            JSONObject json_detail = new JSONObject();
                                            json_detail.put("name", name);
                                            json_detail.put("phone", number);
                                            json_detail.put("email", email);
//                                            json_detail.put("companyName", CompanyName);
                                            json_detail.put("message", message);
                                            json_detail.put("price", pack_add_file_cal);
                                            json_detail.put("payment_method", method);
                                            json_detail.put("formate", formats);
                                            Log.e("json_detail", json_detail.toString());
                                            multipartContent.addPart("json", new StringBody(json_detail.toString()));
                                            totalSize = multipartContent.getContentLength();

                                            // Send it
                                            httppost.setEntity(multipartContent);
                                            HttpResponse response = httpClient.execute(httppost, httpContext);
                                            String serverResponse = EntityUtils.toString(response.getEntity());
                                            Log.e("serverResponse", serverResponse);

                                            JSONObject mainObject = new JSONObject(serverResponse);
                                          //  String status = mainObject.getString("status");
                                            String message1 = mainObject.getString("message");
//                                            if (status.equals("000")) {
                                            Log.e("Testing","Message " + message1);
                                            if (message1.equalsIgnoreCase("successful"))
                                            {

//                                                msg = mainObject.getString("msg");
//                                                Order_id = mainObject.getString("orderId");
                                                Order_id = mainObject.getJSONObject("data").getString("order_id");
                                                Log.e("serverResponse", "Order_id : " + Order_id);
                                                if (method.equals("PayPal")) {
                                                    if (progressDialog != null) {
                                                        progressDialog.dismiss();
                                                    }

                                                    PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(pack_add_file_cal), "USD", Order_id,
                                                            PayPalPayment.PAYMENT_INTENT_SALE);
//                                                    PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(pack_add_file_cal), "USD", Order_id);

                                                    Intent intent = new Intent(SVG_View_Pager.this, PaymentActivity.class);

                                                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, CONFIG_ENVIRONMENT);


                                                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                                                    startActivityForResult(intent, 0);

                                                } else if (method.equals("Credit Card")) {
//                                                    Intent i = new Intent(this, PaymentWebview.class);
//                                                    AlertDialogWebView(SVG_View_Pager.this, "Your due amount is  " + pack_add_file_cal);
                                                    Intent intent = new Intent(SVG_View_Pager.this, PaymentWebview.class);
                                                    intent.putExtra("Payment_link", "https://ipn.intuit.com/payNow/start?eId=5e35b895ac40dad0&uuId=ca843a5a-1a4f-4466-b455-7471fa992da5");
                                                    intent.putExtra("price", "" + pack_add_file_cal);
                                                    intent.putExtra("orderid", Order_id);
                                                    Log.d("DETAILSS", "before Price : " + pack_add_file_cal);
                                                    Log.d("DETAILSS", "before OrderId : " + Order_id);
                                                    startActivityForResult(intent, 1);
//                                                    startActivity(intent);
                                                } else {
                                                    return msg + " " + Order_id;
                                                }
                                            }


                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        return "";
                                    }

                                    protected void onProgressUpdate(Integer... progress) {
                                        super.onProgressUpdate(progress);
                                    }

                                    @SuppressWarnings("deprecation")
                                    @Override
                                    protected void onPostExecute(String msg) {

                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                        }
                                        Contact_name.setError(null);
                                        Contact_number.setError(null);
                                        Contact_email.setError(null);
                                        Contact_message.setError(null);

                                        if (!msg.equals("")) {
                                            AlertDialogMessageShow(SVG_View_Pager.this, msg);
                                            viewpager.setCurrentItem(1);
                                        }
                                    }

                                }.execute();

                            }

                        }
                    });


                    //---------------------------------------------------------------------

                    ((SmoothViewPager) container).addView(contact_screen, 0);
                    views.put(position, contact_screen);
                    return contact_screen;

                case 0:

                    resId = R.layout.pager_contact_us_new;
                    View contact_screen_new = inflater.inflate(resId, null);


                    Contact_name_Contact = (EditText) contact_screen_new.findViewById(R.id.contact_name_new);
                    Contact_number_Contact = (EditText) contact_screen_new.findViewById(R.id.contact_number_new);
                    Contact_email_Contact = (EditText) contact_screen_new.findViewById(R.id.contact_email_new);
                    Contact_message_Contact = (EditText) contact_screen_new.findViewById(R.id.contact_message_new);
                    Send_Contact = (Button) contact_screen_new.findViewById(R.id.send_details);

                    //----------------------------------------------------
                    //----------------------- Contact Name
                    Contact_name_Contact.addTextChangedListener(new DelayedTextWatcher(2000) {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            super.beforeTextChanged(s, start, count, after);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                // position the text type in the left top corner

                                Contact_name_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                            } else {
                                // no text entered. Center the hint text.
                                Contact_name_Contact.setGravity(Gravity.CENTER);
                            }

                        }

                        @Override
                        public void afterTextChangedDelayed(Editable s) {
                            Methods methods = new Methods();
                            if (Contact_name_Contact.getText().toString().length() == 0) {
                                Contact_name_Contact.setError("Empty Field");
                            } else if (!methods.validName(Contact_name.getText().toString())) {
                                Contact_name_Contact.setError("Invalid Name");
                            } else {
                                Contact_name_Contact.setError(null);
                            }
                        }
                    });

                    //----------------------- Contact Name
                    if (Contact_name_Contact.getText().toString().length() > 0) {
                        Contact_name_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                    } else {
                        Contact_name_Contact.setGravity(Gravity.CENTER);
                    }
                    //----------------------- Contact Name
                    Contact_name_Contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (Contact_name_Contact.getText().toString().length() > 0) {
                                } else {
                                    Contact_name_Contact.setHint("");
                                    Contact_name_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                                }
                            } else {
                                if (Contact_name_Contact.getText().toString().length() == 0) {
                                    Contact_name_Contact.setHint(R.string.hint_enter_full_name);
                                    Contact_name_Contact.setGravity(Gravity.CENTER);
                                }
                                if (Contact_number_Contact.getText().toString().length() == 0) {
                                    Contact_number_Contact.setText("+");
                                    Contact_number_Contact.setSelection(Contact_number_Contact.length());
                                }
                            }


                        }
                    });

                    //----------------------- Contact Name
                    //----------------------------------------------------

                    //----------------------------------------------------
                    //----------------------- Contact Number

                    Contact_number_Contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
//                                Toast.makeText(SVG_View_Pager.this,"Focused",Toast.LENGTH_SHORT).show();
                                if (Contact_number_Contact.getText().toString().length() > 0) {
                                    Contact_number_Contact.setGravity(Gravity.LEFT);
                                    Contact_number_Contact.setText("+");
                                    Contact_number_Contact.setSelection(Contact_number_Contact.length());
                                }
                            }
                        }
                    });


                    Contact_number_Contact.addTextChangedListener(new DelayedTextWatcher(2000) {


                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() == 0) {
                                Contact_number_Contact.setGravity(Gravity.LEFT);
                                Contact_number_Contact.setText("+");
                                Contact_number_Contact.setSelection(Contact_number_Contact.length());

                            } else if (s.length() > 0) {
                                // position the text type in the left top corner
//                                Contact_number.setText("+");
                                Contact_number_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                            } else {
                                // no text entered. Center the hint text.
                                Contact_number_Contact.setHint(R.string.hint_enter_contact_number);
                                Contact_number_Contact.setGravity(Gravity.CENTER);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            super.afterTextChanged(s);

                        }

                        @Override
                        public void afterTextChangedDelayed(Editable s) {

                            Methods methods = new Methods();
                            if (Contact_number_Contact != null)
                                if (Contact_number_Contact.getText().toString().length() == 0) {
                                    Contact_number_Contact.setError("Empty Field");
                                } else if (!methods.validPhone(Contact_number_Contact.getText().toString())) {
                                    Contact_number_Contact.setError("Invalid Number");
                                } else {
                                    Contact_number_Contact.setError(null);
                                }
                        }
                    });

                    //----------------------- Contact Number
                    if (Contact_number_Contact.getText().toString().length() > 0) {
                        Contact_number_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                    } else {
                        Contact_number_Contact.setGravity(Gravity.CENTER);
                    }
                    //----------------------- Contact Number
                    Contact_number_Contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (Contact_number_Contact.getText().toString().length() > 0) {
                                } else {
                                    Contact_number_Contact.setHint("");
                                    Contact_number_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                                }
                            } else {
                                if (Contact_number_Contact.getText().toString().length() == 0) {
                                    Contact_number_Contact.setHint(R.string.hint_enter_contact_number);
                                    Contact_number_Contact.setGravity(Gravity.CENTER);
                                }
                            }
                        }
                    });

                    //----------------------- Contact Number
                    //----------------------------------------------------

                    //----------------------------------------------------
                    //----------------------- Contact Email
                    Contact_email_Contact.addTextChangedListener(new DelayedTextWatcher(2000) {

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                // position the text type in the left top corner
                                Contact_email_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                            } else {
                                // no text entered. Center the hint text.
                                Contact_email_Contact.setGravity(Gravity.CENTER);
                            }

                        }

                        @Override
                        public void afterTextChangedDelayed(Editable s) {
                            Methods methods = new Methods();
                            if (Contact_email_Contact.getText().toString().length() == 0) {
                                Contact_email_Contact.setError("Empty Field");
                            } else if ((!methods.validEmail(Contact_email_Contact.getText().toString()))) {
                                Contact_email_Contact.setError("Invalid Email");
                            } else {
                                Contact_email_Contact.setError(null);
                            }
                        }
                    });

                    //----------------------- Contact Email
                    if (Contact_email_Contact.getText().toString().length() > 0) {
                        Contact_email_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                    } else {
                        Contact_email_Contact.setGravity(Gravity.CENTER);
                    }
                    //----------------------- Contact Email
                    Contact_email_Contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                if (Contact_email_Contact.getText().toString().length() > 0) {
                                } else {
                                    Contact_email_Contact.setHint("");
                                    Contact_email_Contact.setGravity(Gravity.LEFT | Gravity.CENTER);
                                }
                            } else {
                                if (Contact_email_Contact.getText().toString().length() == 0) {
                                    Contact_email_Contact.setHint(R.string.hint_enter_email);
                                    Contact_email_Contact.setGravity(Gravity.CENTER);
                                }
                            }
                        }
                    });

                    Send_Contact.setOnClickListener(new View.OnClickListener() {
                        private String name, number, email, message;

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Methods methods = new Methods();
                            if (Contact_name_Contact.getText().toString().length() == 0) {
                                Contact_name_Contact.setError("Empty Field");
                            } else if (!methods.validName(Contact_name_Contact.getText().toString())) {
                                Contact_name_Contact.setError("Invalid Name");
                            } else if (Contact_number_Contact.getText().toString().length() == 0) {
                                Contact_number_Contact.setError("Empty Field");
                            } else if (!methods.validPhone(Contact_number_Contact.getText().toString())) {
                                Contact_number_Contact.setError("Invalid Number");
                            } else if (Contact_number_Contact.getText().toString().length() < 10) {
                                Contact_number_Contact.setError("Phone must be at least 10 digits");
                            } else if (Contact_email_Contact.getText().toString().length() == 0) {
                                Contact_email_Contact.setError("Empty Field");
                            } else if ((!methods.validEmail(Contact_email_Contact.getText().toString()))) {
                                Contact_email_Contact.setError("Invalid Email");
                            } else if (Contact_message_Contact.getText().toString().length() < 10) {
                                Contact_message_Contact.setError("Message must be at least 10 characters");
                            } else if (!Methods.isNetworkConnected(SVG_View_Pager.this)) {
                                Toast.makeText(SVG_View_Pager.this, "Internet not reachable", Toast.LENGTH_SHORT).show();
                            } else {
                                name = Contact_name_Contact.getText().toString();
                                number = Contact_number_Contact.getText().toString();
                                email = Contact_email_Contact.getText().toString();
                                message = Contact_message_Contact.getText().toString();

//                                Contact_number.setText("");
//                                Contact_email.setText("");
//                                Contact_message.setText("");

                                new AsyncTask<HttpResponse, Integer, String>() {

                                    long totalSize;
                                    private ProgressDialog progressDialog;

                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();

                                        progressDialog = ProgressDialog
                                                .show(SVG_View_Pager.this, "", "Loading...");
                                    }

                                    @Override
                                    protected String doInBackground(HttpResponse... arg0) {


                                        try {
//
                                            JSONObject json = new JSONObject();

                                            json.put("name", name);
                                            json.put("email", email);
                                            json.put("phone", number);
                                            json.put("message", message);


                                            Map<String, Object> values = new LinkedHashMap<>();

                                            values.put("json", json.toString());
                                            Log.e("IM", json.toString());
                                            byte[] detail = getPostData(values);

                                            URL url = new URL(CONTACT_US_URL);
                                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                            conn.setReadTimeout(timeoutSocket /*milliseconds*/);
                                            conn.setConnectTimeout(timeoutConnection /* milliseconds */);
                                            conn.setRequestMethod("POST");
                                            conn.setDoInput(true);
                                            conn.setDoOutput(true);
                                            conn.setFixedLengthStreamingMode(detail.length);

                                            //make some HTTP header nicety
                                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                            //          conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                                            //          conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                                            //open
                                            conn.connect();
                                            //setup send
                                            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                                            os.write(detail);
                                            //clean up
                                            os.flush();

                                            InputStream inputStream = conn.getInputStream();
                                            String temp = readFully(inputStream);

                                            //clean up
                                            os.close();
                                            inputStream.close();
                                            conn.disconnect();
                                            Log.e("METEST", temp);
                                            Log.e("METEST", temp);
                                            JSONObject mainObject = new JSONObject(temp);


                                            String status = mainObject.getString("message");
                                            return status;

                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }


                                        return "";
                                    }

                                    protected void onProgressUpdate(Integer... progress) {
                                        super.onProgressUpdate(progress);
                                    }

                                    @SuppressWarnings("deprecation")
                                    @Override
                                    protected void onPostExecute(String msg) {


                                        Log.d("METEST", "Response : " + msg);
                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                        }
                                        Contact_name_Contact.setError(null);
                                        Contact_number_Contact.setError(null);
                                        Contact_email_Contact.setError(null);
                                        Contact_message_Contact.setError(null);
                                        String message = "";


                                        if (msg.equalsIgnoreCase("successful")) {
//                                            message = "Your Response has been submitted";
                                            message = "Thanks for sending your message! We'll get back to you shortly.";
                                            AlertDialogMessageShow(SVG_View_Pager.this, message);
                                            ClearFieldsContact();
                                            viewpager.setCurrentItem(1);
                                        } else if ((msg.equalsIgnoreCase("") || msg.equalsIgnoreCase(" "))) {
                                            Toast.makeText(SVG_View_Pager.this, "Something went wrong ,Please try again later", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SVG_View_Pager.this, message, Toast.LENGTH_SHORT).show();
                                        }
//                                        if (!msg.equals("")) {
//                                            AlertDialogMessageShow(SVG_View_Pager.this, message);
//                                        }
                                    }

                                }.execute();

                            }

                        }
                    });


                    ((SmoothViewPager) container).addView(contact_screen_new, 0);
                    return contact_screen_new;


            }

            return resId;

        }


    }

    private void showLoadingDialog(final Context context) {


        Runnable second_Task = new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ringProgressDialog = ProgressHUD.show(SVG_View_Pager.this, "Loading \n Please Wait", true, false, null);
                    }
                });

            }
        };
        Thread thread = new Thread(second_Task);
        thread.start();
    }

    private byte[] getPostData(Map<String, Object> params) throws IOException {
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString().getBytes("UTF-8");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return false;
    }


    private void refresherName() {
        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < icon_arrayList.size(); i++) {
            icon_arrayList.get(i).setCompanyName(CompanyName);
        }
        iconAdapter.setArray(icon_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < font_arrayList.size(); i++) {

            font_arrayList.get(i).setCompanyName(CompanyName);
        }
        fontAdapter.setArray(font_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < color_arrayList.size(); i++) {
            color_arrayList.get(i).setCompanyName(CompanyName);
        }
        colorAdapter.setArray(color_arrayList);

        //-------------------------------------------------

    }

    public void refresherIcon(int position) {

        //-------------------------------------------------
        // this will refresh all boxes to false then set 1

        for (int i = 0; i < icon_arrayList.size(); i++) {
            icon_arrayList.get(i).setStatus(0);
        }
        icon_arrayList.get(position).setStatus(1);

        iconAdapter.setArray(icon_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < font_arrayList.size(); i++) {
            font_arrayList.get(i).setImageName(ImageName);
        }
        fontAdapter.setArray(font_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < color_arrayList.size(); i++) {
            color_arrayList.get(i).setImageName(ImageName);
        }
        colorAdapter.setArray(color_arrayList);

    }

    public void refresherFont(int position) {
        //-------------------------------------------------
        // this will refresh all boxes to false then set 1

        for (int i = 0; i < font_arrayList.size(); i++) {

            font_arrayList.get(i).setStatus(0);
        }
        font_arrayList.get(position).setStatus(1);
        fontAdapter.setArray(font_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < icon_arrayList.size(); i++) {
            icon_arrayList.get(i).setFontStyle(FontStyle);
        }
        iconAdapter.setArray(icon_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < color_arrayList.size(); i++) {
            color_arrayList.get(i).setFontStyle(FontStyle);
        }
        colorAdapter.setArray(color_arrayList);

        //-------------------------------------------------
    }


    private void refresherColor(int position) {
        //-------------------------------------------------
        // this will refresh all boxes to false then set 1

        for (int i = 0; i < color_arrayList.size(); i++) {
            color_arrayList.get(i).setStatus(0);
        }
        color_arrayList.get(position).setStatus(1);
        colorAdapter.setArray(color_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < icon_arrayList.size(); i++) {
            icon_arrayList.get(i).setImageColor(ImageColor);
        }
        iconAdapter.setArray(icon_arrayList);

        //-------------------------------------------------
        // this will refresh

        for (int i = 0; i < font_arrayList.size(); i++) {

            font_arrayList.get(i).setImageColor(ImageColor);
        }
        fontAdapter.setArray(font_arrayList);

        //-------------------------------------------------

    }


    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                //view.setAlpha(MIN_ALPHA +	(scaleFactor - MIN_SCALE) /	(1 - MIN_SCALE) * (1 - MIN_ALPHA));
                view.setAlpha(1);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }


    //------------------------------------------------------------------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                final PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {

                    new AsyncTask<String, String, String>() {

                        @Override
                        protected String doInBackground(String... params) {
                            // TODO Auto-generated method stub
                            String msg = verifiypayment3(confirm.toJSONObject());
                            return msg;

                        }

                        @SuppressWarnings("deprecation")
                        protected void onPostExecute(String result) {
                            Contact_name.setError(null);
                            Contact_number.setError(null);
                            Contact_email.setError(null);
                            Contact_message.setError(null);
//                        if (!result.equals("")) {
                            if (result.equalsIgnoreCase("successful")) {
                                AlertDialogAfterPayment(SVG_View_Pager.this);
//                                AlertDialogMessageShow(SVG_View_Pager.this, "Your transaction was successful. Thank you! We will contact you shortly. \nFeel free to contact us for any query\nEmail : contactus@quicklogodesign.com\nPhone : 214-550-3360 ");
//                                AlertDialogMessageShow(SVG_View_Pager.this, "Your transaction was successful. Thank you! We will contact you shortly.");
                                ClearFields();
                                removeError();
                                viewpager.setCurrentItem(1);
                            } else {
                                Toast.makeText(SVG_View_Pager.this, "Your payment has been made successfully!", Toast.LENGTH_SHORT).show();
                                ClearFields();
                                removeError();
                                viewpager.setCurrentItem(1);
                            }
                        }

                        ;
                    }.execute();


                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.e("paymentExample", "An invalid payment was submitted. Please see the docs.");
            }
        } else if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                ClearFields();
                viewpager.setCurrentItem(1);
                Log.d("ResultURL", "Result : " + result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    //------------------------------------------------------------------------------------------------------


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    //------------------------------------------------------------------------------------------------------


    public String verifiypayment3(JSONObject Json) { // W.r.t new API and Request

        JSONObject mainObject = Json;
        JSONObject dataObject = null;
        try {

            JSONObject responseObject = mainObject.getJSONObject("response");
            String Status = responseObject.getString("state");
            String ref = responseObject.getString("id");
            JSONObject payObject = new JSONObject();
            payObject.put("order_id", Order_id);
            payObject.put("reference", ref);

            if (Status.equals("approved") || Status.equals("COMPLETED") || Status.equals("Completed")) {
                payObject.put("status", true);
            } else {
                payObject.put("status", false);
            }
            Log.d("serverResponse", "Payment JSON  : " + payObject.toString());
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("json", payObject.toString()));

            Log.e("serverRequest", payObject.toString());

            Map<String, Object> values = new LinkedHashMap<>();

            values.put("json", payObject.toString());
            Log.e("IM", payObject.toString());
            byte[] detail = getPostData(values);

            URL url = new URL(PAYMENT_US_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(timeoutSocket /*milliseconds*/);
            conn.setConnectTimeout(timeoutConnection /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(detail.length);

            //make some HTTP header nicety
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //open
            conn.connect();
            //setup send
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(detail);
            //clean up
            os.flush();

            InputStream inputStream = conn.getInputStream();
            String temp = readFully(inputStream);

            //clean up
            os.close();
            inputStream.close();
            conn.disconnect();
            Log.e("METEST", temp);
            Log.e("METEST", temp);

            JSONObject mainServerObject = new JSONObject(temp);


            String status = mainServerObject.getString("message");
            return status;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }


    public String verifiypayment(JSONObject Json) { // W.r.t Old Api
        try {

            JSONObject mainObject = Json;
            JSONObject dataObject = mainObject.getJSONObject("proof_of_payment");
            JSONObject userObject = dataObject.getJSONObject("adaptive_payment");
            String Status = userObject.getString("payment_exec_status");
            String ref = userObject.getString("pay_key");
            String ap_id = userObject.getString("app_id");
            JSONObject dataObject2 = mainObject.getJSONObject("payment");
            String Desc = dataObject2.getString("short_description");

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://webdevelopersrus.com/php/sadiq/logo-design/api/paypal");
            JSONObject payObject = new JSONObject();

            payObject.put("orderId", Order_id);
            payObject.put("paymentReference", ref);
            if (Status.equals("COMPLETED") || Status.equals("Completed")) {
                payObject.put("paymentStatus", "TRUE");
            } else {
                payObject.put("paymentStatus", Status);
            }
            payObject.put("message", ap_id + "  -  " + Desc);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("json", payObject.toString()));

            Log.e("serverRequest", payObject.toString());

            httppost.setEntity(new UrlEncodedFormEntity(param));

            HttpResponse response = httpclient.execute(httppost);
            String serverResponse = EntityUtils.toString(response.getEntity());
            Log.e("serverResponse", serverResponse);

            JSONObject jsonObject = new JSONObject(serverResponse);
            String reply_status = jsonObject.getString("status");
            String msg = jsonObject.getString("msg");
            if (reply_status.equals("000")) {
                return msg;
            } else if (reply_status.equals("001")) {
                return msg;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //------------------------------------------------------------------------------------------------------

    @SuppressWarnings("deprecation")
    private void AlertDialogMessageShow(Context context, String Message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();

        alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
                alertDialog.dismiss();
            }
        });
        alertDialog.setMessage(Message);
        alertDialog.show();
    }

    private void AlertDialogAfterPayment(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_dialog);
        dialog.show();
        Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
    }


//
//    private void AlertDialogWebView(Context context, String Message) {
//        final AlertDialog alertDialog = new AlertDialog.Builder(
//                context).create();
//
//             alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // here you can add functions
//                Intent intent = new Intent(SVG_View_Pager.this, PaymentWebview.class);
//                intent.putExtra("price" , pack_add_file_cal);
//                intent.putExtra("orderid" , Order_id);
//                intent.putExtra("Payment_link", "https://ipn.intuit.com/payNow/start?eId=5e35b895ac40dad0&uuId=ca843a5a-1a4f-4466-b455-7471fa992da5");
//                startActivityForResult(intent, 1);
//            }
//        });
//        alertDialog.setMessage(Message);
//        alertDialog.show();
//    }


    //------------------------------------------------------------------------------------------------------

    private void ClearFields() {
        Company_Name.getText().clear();
        Contact_name.getText().clear();
        Contact_number.getText().clear();
        Contact_email.getText().clear();
        Contact_message.getText().clear();
    }

    private void removeError() {
//        Company_Name.getText().clear();
        Contact_name.setError(null);
        Contact_number.setError(null);
        Contact_email.setError(null);
        Contact_message.setError(null);
    }

    private void ClearFieldsContact() {
        Contact_name_Contact.getText().clear();
        Contact_number_Contact.getText().clear();
        Contact_email_Contact.getText().clear();
        Contact_message_Contact.getText().clear();
    }

    private void removeErorContact() {
        try {
            Contact_name_Contact.setError(null);
            Contact_number_Contact.setError(null);
            Contact_email_Contact.setError(null);
            Contact_message_Contact.setError(null);
        } catch (Exception e) {

        }
    }


    private String readFully(InputStream entityResponse) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = entityResponse.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(ringProgressDialog != null)
//            ringProgressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity=this;
        if(ringProgressDialog != null)
            ringProgressDialog.dismiss();

        if(Custom_Designing.viewpager_state==1)
        { //Custom_Designing.viewpager_state=0;
            viewpager.setSwipeAble(true);
            viewpager.setCurrentItem(7);
           // viewpager.setSwipeAble(false);
        }
      /*  else {
            viewpager.setCurrentItem(1);
        }*/
        Log.e("Testing","View pager psoition" + viewpager.getCurrentItem());
    }

}
