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


public class MainScreen extends Activity {

    String msg = "Thank you for contacting us! We will contact you shortly. Your Order ID is ";
    //----------------------------------------------------
//    123123
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    private static final String CONFIG_CLIENT_ID = "AWJ-AxAptJj_TRuqbHOJGfgEBZ_bAbX0ygIYeNFlPVvqsQUcAdvpbno8hILR"; // PRODUCTION ID


    private static String CONTACT_US_URL = "http://webdevelopersrus.com/php/shariq/qldplus/public/api/contactus";
    private static String PAYMENT_US_URL = "http://webdevelopersrus.com/php/shariq/qldplus/public/api/payment";
    //----------------------------------------------------


    SmoothViewPager viewpager;
    ViewPagerAdapter pagerAdapter;
    CustomFontPagerTitleStrip pagerTitleStrip;


    EditText Company_Name;
    EditText Contact_name, Contact_name_Contact;
    EditText Contact_number, Contact_number_Contact;
    EditText Contact_email, Contact_email_Contact;
    EditText Contact_message, Contact_message_Contact;
    Button Send_Contact;
//    Spinner Meth_pay_spinner;


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .acceptCreditCards(true);



    // Web work
    int timeoutConnection = 100000;
    int timeoutSocket = 150000;

    ImageView title_image , title_image_1;
    ImageView contact_us_form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        viewpager = (SmoothViewPager) findViewById(R.id.viewpager);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        title_image = (ImageView) findViewById(R.id.title_image);
        title_image_1 = (ImageView) findViewById(R.id.title_image_1);

        pagerTitleStrip = (CustomFontPagerTitleStrip) findViewById(R.id.pager_title_strip);

        //pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        pagerAdapter = new ViewPagerAdapter();

        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(6);

        contact_us_form = (ImageView) findViewById(R.id.contact_us_form);
        contact_us_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(0);
            }
        });

        viewpager.setPageTransformer(false, new ZoomOutPageTransformer());
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("PageScrolled", "pos : " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("PageScrolled", "case 2 : " + position);

                if (position == 1) {
                    viewpager.setSwipeAble(false);
                    title_image.setImageResource(R.drawable.main_menu);
                    removeErorContact();
                    contact_us_form.setVisibility(View.VISIBLE);
                    title_image_1.setVisibility(View.GONE);
                    title_image.setVisibility(View.VISIBLE);
                } else {
                    viewpager.setSwipeAble(true);
                    title_image.setImageResource(R.drawable.global_contact_icon_1);
                    contact_us_form.setVisibility(View.GONE);
                    title_image.setVisibility(View.GONE);
                    title_image_1.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });







        viewpager.setSwipeAble(false);
        viewpager.setCurrentItem(1);

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

            Drawable drawable = MainScreen.this.getResources().getDrawable(List[position]);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            StickerSpan span = new StickerSpan(getApplicationContext(), ((BitmapDrawable) drawable).getBitmap());
            //ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return sb;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 2;
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
                    Button createLogo = (Button) main_menu.findViewById(R.id.mainmenu_createLogo);
                    logo_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent Main_Menu = new Intent(MainScreen.this, Custom_Designing.class);
                            startActivity(Main_Menu);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

//                             finish();
                        }
                    });
                    createLogo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent Main_Menu = new Intent(MainScreen.this, SVG_View_Pager.class);
                            startActivity(Main_Menu);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        }
                    });

                    views.put(position, main_menu);
                    return main_menu;
//                    break;
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
                            } else if (!Methods.isNetworkConnected(MainScreen.this)) {
                                Toast.makeText(MainScreen.this, "Internet not reachable", Toast.LENGTH_SHORT).show();
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
                                                .show(MainScreen.this, "", "Loading...");
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
                                            AlertDialogMessageShow(MainScreen.this, message);
                                            ClearFieldsContact();
                                            viewpager.setCurrentItem(1);
                                        } else if ((msg.equalsIgnoreCase("") || msg.equalsIgnoreCase(" "))) {
                                            Toast.makeText(MainScreen.this, "Something went wrong ,Please try again later", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainScreen.this, message, Toast.LENGTH_SHORT).show();
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


    //------------------------------------------------------------------------------------------------------


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    //------------------------------------------------------------------------------------------------------




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
    private void removeError(){
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
}
