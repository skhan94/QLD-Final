package com.fsd.logodesign;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by hamza on 6/14/2016.
 */
public class MyApplication extends Application {

    public static InterstitialAd mInterstitialAd;


    public static void intilizeAdds(Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3514887027893252/1712554121");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

    }

    /****
     * Working Code Commented Now As Per Client Req:
     ****/
    public static void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                //       .addTestDevice("08DAF80433AEBCBF598991EEBF17CCC2")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public static void displayAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}
