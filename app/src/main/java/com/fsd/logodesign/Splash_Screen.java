package com.fsd.logodesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.google.firebase.iid.FirebaseInstanceId;

public class Splash_Screen extends Activity {
    int type = -1;
    String pkg = "", appname = "", content = "", title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);


        Thread timer = new Thread() {
            public void run() {
                try {
                    String token = FirebaseInstanceId.getInstance().getToken();
                    Log.e("Testing","TOKEN " + token);
  //TOKEN exvCgg3kM_0:APA91bGPySFDYCTyJj_o2eb7crT3izqKGsmebCOQqCX1lfPdBFTuHdASvnTCQu9KBtyIpwugBFCeJRdvtDGIVkCAhLpG_Cj6Z2Zt_R9EgdRAEtQ4107P1Hk_1r0o4bcnFp2Oz30KBMwE
                   /* // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, msg);*/
                    if (getIntent().getExtras() != null) {
                        for (String key : getIntent().getExtras().keySet()) {
                            String value = getIntent().getExtras().getString(key);
                            if (key.equalsIgnoreCase("type") ) {
                                type = Integer.valueOf(value);
                            } else if (key.equalsIgnoreCase("pkg")) {
                                pkg = value;
                            }
                             else if (key.equalsIgnoreCase("title")) {
                                title = value;
                            } else if (key.equalsIgnoreCase("content")) {
                                content = value;
                            }
                        }
                        Log.e("Testing","TYPE " + type + " PKG " + pkg + " TITLE " + title + " CONTENT " + content);
                    }

                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent Main_Menu = new Intent(Splash_Screen.this, SVG_View_Pager.class);
                    Main_Menu.putExtra("type", type);
                    Main_Menu.putExtra("pkg", pkg);
                   // Main_Menu.putExtra("appname", appname);
                    Main_Menu.putExtra("title",title);
                    Main_Menu.putExtra("content" , content);
                    startActivity(Main_Menu);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    Splash_Screen.this.finish();
                }
            }
        };
        timer.start();
    }

    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        finish();
    }

}
