package com.fsd.logodesign;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

public class PaymentWebview extends Activity {

	ImageButton Back;
	CircleProgressBar cpb;
	WebView webview;
	String returnURL = "";

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment);
		Intent i = getIntent();
		String Urls =  i.getStringExtra("Payment_link");
		String price = i.getStringExtra("price");
		String orderId = i.getStringExtra("orderid");
		Log.d("DETAILSS" , "Price : " + price);
		Log.d("DETAILSS" , "OrderId : " + orderId);

		if(price!=null & orderId!= null){
			AlertDialogWebView(PaymentWebview.this , "Invoice Ref : " + orderId  + "\nPayment Amount : " + price);
		}

		Back = (ImageButton) findViewById(R.id.back_button);
		Back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		cpb = (CircleProgressBar) findViewById(R.id.circlularprogressBar);
		cpb.setShowArrow(true);
		cpb.setColorSchemeColors(Color.parseColor("#7B9D22"));
//		proBar = (ProgressBar) findViewById(R.id.proBar);
		cpb.setVisibility(View.VISIBLE);


		webview = (WebView) findViewById(R.id.webView1);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(Urls);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setUseWideViewPort(true);

		webview.setWebViewClient(new WebViewClient() {

			public void onPageFinished(WebView view, String url) {
				cpb.setVisibility(View.GONE);
				Log.d("WEBURL", "URL : " + webview.getUrl());
				returnURL = webview.getUrl();
				if(returnURL.equalsIgnoreCase("https://ipn.intuit.com/payNow/submitPayment"))
					AlertDialogMessageShow(PaymentWebview.this , "Your transaction has been made. Our representative will contact you soon.");
			}
		});


	}

	@Override
	public void onBackPressed() {
//		super.onBackPressed();
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result",returnURL);
		setResult(Activity.RESULT_OK,returnIntent);
		finish();
	}

	private void AlertDialogMessageShow(Context context, String Message) {
		final AlertDialog alertDialog = new AlertDialog.Builder(
				context).create();

		alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result",returnURL);
				setResult(Activity.RESULT_OK,returnIntent);
				finish();
				alertDialog.dismiss();

			}
		});
		alertDialog.setMessage(Message);
		alertDialog.show();
	}


	private void AlertDialogWebView(Context context, String Message) {
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

}
