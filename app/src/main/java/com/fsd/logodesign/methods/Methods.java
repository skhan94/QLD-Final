package com.fsd.logodesign.methods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.fsd.logodesign.objectclasses.ImageObject;

public class Methods {

	public static final String strSeparator =", ";


	public AllArraysList Init(Context context,String CompanyName,String ImageName,int ImageColor,int FontStyle){

		ArrayList<ImageObject>	icon_arrayList = new ArrayList<ImageObject>();


		icon_arrayList = IconCurrentVerson(context,icon_arrayList,CompanyName,ImageColor,FontStyle);
		//--------------------------------------------------------------------
		icon_arrayList = IconPreviousVersion1(context,icon_arrayList);
		//--------------------------------------------------------------------
		icon_arrayList = IconPreviousVersion(context,icon_arrayList);
		//--------------------------------------------------------------------


		ArrayList<ImageObject> font_arrayList = new ArrayList<ImageObject>();

		//---------------------------------------------------------------
		font_arrayList = FontCurrentVerson(context, font_arrayList, CompanyName, ImageName, ImageColor);
		//-----------------------------------------------------------------
		font_arrayList = FontPreviousVersion(context, font_arrayList);


		ArrayList<ImageObject> color_arrayList = new ArrayList<ImageObject>();


		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 1, FontStyle,0));
		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 2, FontStyle,0));
		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 3, FontStyle,0));
		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 4, FontStyle,0));
		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 5, FontStyle,0));
		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 6, FontStyle,0));
		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 7, FontStyle,0));
		color_arrayList.add(new ImageObject(context,CompanyName, ImageName, 8, FontStyle,0));

		ArrayList<String>	formats_list= new ArrayList<String>();
		formats_list.add("pdf");
		formats_list.add("jpg");
		formats_list.add("tiff");
		formats_list.add("ai ");
		formats_list.add("psd");
		formats_list.add(".png");

		List<String> methods_options = new ArrayList<String>();
		methods_options.add("PayPal");
		methods_options.add("Credit Card");
//		methods_options.add("Cheque");
		methods_options.add("Wire Transfer");
//		methods_options.add("Local Chase branch");
//		methods_options.add("Others");
		
		return new AllArraysList(icon_arrayList, font_arrayList, color_arrayList, formats_list,methods_options);
	}


	/**
	 * 
	 * @param context
	 * @param icon_arrayList
	 * @return
	 */
	private ArrayList<ImageObject> IconPreviousVersion(Context context,ArrayList<ImageObject> icon_arrayList){
		String CompanyName = icon_arrayList.get(0).getCompanyName();
		int ImageColor = icon_arrayList.get(0).getImageColor();
		int FontStyle = icon_arrayList.get(0).getFontStyle();

		icon_arrayList.add(new ImageObject(context,CompanyName, "image_1_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_2_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_3_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_4_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_5_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_6_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_7_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_8_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_9_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_10_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_11_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_12_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_13_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_14_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_15_", ImageColor, FontStyle,0));


		return icon_arrayList;
	}

	/**
	 * @param context
	 * @param icon_arrayList
	 * @return
	 */
	private ArrayList<ImageObject> IconPreviousVersion1(Context context,ArrayList<ImageObject> icon_arrayList){
		String CompanyName = icon_arrayList.get(0).getCompanyName();
		int ImageColor = icon_arrayList.get(0).getImageColor();
		int FontStyle = icon_arrayList.get(0).getFontStyle();

		icon_arrayList.add(new ImageObject(context,CompanyName, "image_16_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_17_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_18_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_19_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_20_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_21_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_22_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_23_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_24_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_25_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_26_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_27_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_28_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_29_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_30_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_31_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_32_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_33_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_34_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_35_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_36_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_37_", ImageColor, FontStyle,0));

		return icon_arrayList;
	}

	/**
	 * @param context
	 * @param icon_arrayList
	 * @param CompanyName
	 * @param ImageColor
	 * @param FontStyle
	 * @return
	 */
	private ArrayList<ImageObject> IconCurrentVerson(Context context,ArrayList<ImageObject> icon_arrayList,String CompanyName,int ImageColor,int FontStyle){

		icon_arrayList.add(new ImageObject(context,CompanyName, "image_38_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_39_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_40_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_41_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_42_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_43_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_44_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_45_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_46_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_47_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_48_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_49_", ImageColor, FontStyle,0));
		icon_arrayList.add(new ImageObject(context,CompanyName, "image_50_", ImageColor, FontStyle,0));

		return icon_arrayList;
	}

	//------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------

	/**
	 * @param textView / VerticalTextView
	 * @param FontStyle 
	 *  0/ default /1/ fog /2/ lato /3/ mawns /4/ namsoi /5/ niconne /6/ opensans /7/ playball /8/ satisfy /9/ lavanderiaS
	 *  10/akzidenz_grotesk /11/aehnir /12/ times /13/ arial /14/ comic /15/ baskerville /16/ bickhamscript /17/ optima // else default
	 * @return 
	 */
	public  static TextView SetFontStyle(TextView textView,int FontStyle,Context context){

		//------------------------------------------------------------------

		Typeface fog = Typeface.createFromAsset(context.getAssets(),"fonts/fog.ttf");
		Typeface lato = Typeface.createFromAsset(context.getAssets(),"fonts/lato.ttf");
		Typeface mawns = Typeface.createFromAsset(context.getAssets(),"fonts/mawns.ttf");
		Typeface namsoi = Typeface.createFromAsset(context.getAssets(),"fonts/namsoi.ttf");
		Typeface niconne = Typeface.createFromAsset(context.getAssets(),"fonts/niconne.ttf");
		Typeface opensans = Typeface.createFromAsset(context.getAssets(),"fonts/opensans.ttf");
		Typeface playball = Typeface.createFromAsset(context.getAssets(),"fonts/playball.ttf");
		Typeface satisfy = Typeface.createFromAsset(context.getAssets(),"fonts/satisfy.ttf");
		Typeface lavanderiaS = Typeface.createFromAsset(context.getAssets(),"fonts/lavanderiaS.ttf");

		//------------------------------------------------------------------

		Typeface akzidenz_grotesk = Typeface.createFromAsset(context.getAssets(),"fonts/akzidenz-grotesk.ttf");
		Typeface aehnir = Typeface.createFromAsset(context.getAssets(),"fonts/aehnir.ttf");
		Typeface times = Typeface.createFromAsset(context.getAssets(),"fonts/times.ttf");
		Typeface arial = Typeface.createFromAsset(context.getAssets(),"fonts/arial.ttf");
		Typeface comic = Typeface.createFromAsset(context.getAssets(),"fonts/comic.ttf");
		Typeface baskerville = Typeface.createFromAsset(context.getAssets(),"fonts/baskerville.ttf");
		Typeface bickhamscript = Typeface.createFromAsset(context.getAssets(),"fonts/bickhamscript.ttf");
		Typeface optima = Typeface.createFromAsset(context.getAssets(),"fonts/optima.ttf");

		//------------------------------------------------------------------

		if(FontStyle == 0){
			return  textView;
		}else if(FontStyle == 1){
			textView.setTypeface(fog);
			return  textView;
		}else if(FontStyle == 2){
			textView.setTypeface(lato);
			return  textView;
		}else if(FontStyle == 3){
			textView.setTypeface(mawns);
			return  textView;
		}else if(FontStyle == 4){
			textView.setTypeface(namsoi);
			return  textView;
		}else if(FontStyle == 5){
			textView.setTypeface(niconne);
			return  textView;
		}else if(FontStyle == 6){
			textView.setTypeface(opensans);
			return  textView;
		}else if(FontStyle == 7){
			textView.setTypeface(playball);
			return  textView;
		}else if(FontStyle == 8){
			textView.setTypeface(satisfy);
			return  textView;
		}else if(FontStyle == 9){
			textView.setTypeface(lavanderiaS);
			return  textView;
		}else if(FontStyle == 10){
			textView.setTypeface(akzidenz_grotesk);
			return  textView;
		}else if(FontStyle == 11){
			textView.setTypeface(aehnir);
			return  textView;
		}else if(FontStyle == 12){
			textView.setTypeface(times);
			return  textView;
		}else if(FontStyle == 13){
			textView.setTypeface(arial);
			return  textView;
		}else if(FontStyle == 14){
			textView.setTypeface(comic);
			return  textView;
		}else if(FontStyle == 15){
			textView.setTypeface(baskerville);
			return  textView;
		}else if(FontStyle == 16){
			textView.setTypeface(bickhamscript);
			return  textView;
		}else if(FontStyle == 17){
			textView.setTypeface(optima);
			return  textView;
		}else{
			return  textView;
		}

	}

	//------------------------------------------------------------------------------------------------------
	/**
	 * @param context
	 * @param font_arrayList
	 * @return
	 */
	private ArrayList<ImageObject> FontPreviousVersion(Context context,ArrayList<ImageObject> font_arrayList){
		String CompanyName = font_arrayList.get(0).getCompanyName();
		String ImageName = font_arrayList.get(0).getImageName();
		int ImageColor = font_arrayList.get(0).getImageColor();

		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 0,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 1,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 2,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 3,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 4,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 5,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 6,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 7,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 8,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 9,0));

		return font_arrayList;
	}

	/**
	 * @param context
	 * @param font_arrayList
	 * @param CompanyName
	 * @param ImageName
	 * @param ImageColor
	 * @return
	 */
	private ArrayList<ImageObject> FontCurrentVerson(Context context,ArrayList<ImageObject> font_arrayList,String CompanyName,String ImageName,int ImageColor){

		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 10,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 11,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 12,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 13,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 14,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 15,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 16,0));
		font_arrayList.add(new ImageObject(context,CompanyName, ImageName, ImageColor, 17,0));

		return font_arrayList;
	}

	//------------------------------------------------------------------------------------------------------

	/**
	 * @param context
	 * @param final_Logo
	 * @param fileName
	 * @param showToast
	 * @return file name
	 **/
	public String ImageStore(Context context,LinearLayout final_Logo,String fileName,boolean showToast){
		try{

			Bitmap bm = null;

			bm = getBitmapFromView(final_Logo);

			int imageNum = 0;
			File LogoDesign_dir = new File(Environment.getExternalStorageDirectory(),"LogoDesign");
			if(!LogoDesign_dir.exists())
			{
				LogoDesign_dir.mkdir();
			}
			fileName = "image_" + String.valueOf(imageNum) + ".jpg";
			File output = new File(LogoDesign_dir, fileName);
			while (output.exists()){
				imageNum++;
				fileName = "image_" + String.valueOf(imageNum) + ".jpg";
				output = new File(LogoDesign_dir, fileName);
			}
			FileOutputStream outStream = new FileOutputStream(output);
			//bm=eraseColor(bm, Color.TRANSPARENT);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
			// flushing 
			outStream.flush();
			// closing 
			outStream.close();
			ContentValues image = new ContentValues();
			image.put(Images.Media.TITLE, "LogoDesign");
			image.put(Images.Media.DISPLAY_NAME, fileName);
			image.put(Images.Media.DESCRIPTION, "App Image");
			image.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
			image.put(Images.Media.MIME_TYPE, "image/jpg");
			image.put(Images.Media.ORIENTATION, 0);
			File parent = output.getParentFile();
			image.put(Images.ImageColumns.BUCKET_ID, parent.toString().toLowerCase(Locale.getDefault()).hashCode());
			image.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName().toLowerCase(Locale.getDefault()));
			image.put(Images.Media.SIZE, output.length());
			image.put(Images.Media.DATA, output.getAbsolutePath());
			//	Uri result = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
			if(showToast){
				Toast.makeText(context,"File is Saved in  " + output, Toast.LENGTH_SHORT).show();
			}
			Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(output));
			context.sendBroadcast(mediaScanIntent);
			return fileName;


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	//------------------------------------------------------------------------------------------------------

	public static Bitmap getBitmapFromView(View view) {
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);

		Drawable bgDrawable =view.getBackground();
		if (bgDrawable!=null){ 
			bgDrawable.draw(canvas);
		}else{ 
			canvas.drawColor(Color.parseColor("#FFFFFF"));
			view.draw(canvas);
		}
		return returnedBitmap;
	}

	//------------------------------------------------------------------------------------------------------

	/**
	 * @param context
	 * @return internet is available or not
	 */
	public static boolean isNetworkConnected(Context context) {
		boolean connected = false;

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		connected = (cm.getActiveNetworkInfo() != null 
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected());

		return connected;
	}

	//------------------------------------------------------------------------------------------------------

	public String[] convertStringToArray(String str){
		String[] arr = str.split(strSeparator);
		return arr;
	}

	//------------------------------------------------------------------------------------------------------

	public boolean validEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}

	//------------------------------------------------------------------------------------------------------

	public boolean validPhone(String phone) {
		Pattern pattern = Patterns.PHONE;
		return pattern.matcher(phone).matches();
	}

	//------------------------------------------------------------------------------------------------------


	public boolean validName(String name){

		boolean valid = true;
		int numberOfApostrophe = 0;
		char apostrophe = '\'';
		char[] nameChar = name.toCharArray();
		for (char letter: nameChar){
			if (letter == apostrophe |(letter == ' ') |Character.toString(letter).matches("[A-Za-z]+") ){
				if (letter == apostrophe){
					numberOfApostrophe++;
				}
			}
			else{
				valid = false;
			}
		}
		if (valid && numberOfApostrophe <= 2){
			return true;
		}
		else{
			return false;
		}
	}


	//========================================================================================================	

	public class AllArraysList{
		final private ArrayList<ImageObject> 
		_icon_arrayList,
		_font_arrayList,
		_color_arrayList;

		final private ArrayList<String>
		_formats_list;
		
		final private List<String>
		_methods_options;

		AllArraysList(ArrayList<ImageObject> icon_arrayList ,ArrayList<ImageObject> font_arrayList,ArrayList<ImageObject> color_arrayList,ArrayList<String>	formats_list,List<String> methods_options){
			this._icon_arrayList = icon_arrayList;
			this._font_arrayList = font_arrayList;
			this._color_arrayList = color_arrayList;
			this._formats_list = formats_list;
			this._methods_options = methods_options;
		}

		public ArrayList<ImageObject> get_icon_arrayList() {
			return _icon_arrayList;
		}
		public ArrayList<ImageObject> get_font_arrayList() {
			return _font_arrayList;
		}
		public ArrayList<ImageObject> get_color_arrayList() {
			return _color_arrayList;
		}
		public ArrayList<String> get_formats_list() {
			return _formats_list;
		}


		public List<String> get_methods_options(){
			return _methods_options;
		}
	}


	public  static Typeface getFontStyle( int FontStyle, Context context){

		//------------------------------------------------------------------

		Typeface fog = Typeface.createFromAsset(context.getAssets(),"fonts/fog.ttf");
		Typeface lato = Typeface.createFromAsset(context.getAssets(),"fonts/lato.ttf");
		Typeface mawns = Typeface.createFromAsset(context.getAssets(),"fonts/mawns.ttf");
		Typeface namsoi = Typeface.createFromAsset(context.getAssets(),"fonts/namsoi.ttf");
		Typeface niconne = Typeface.createFromAsset(context.getAssets(),"fonts/niconne.ttf");
		Typeface opensans = Typeface.createFromAsset(context.getAssets(),"fonts/opensans.ttf");
		Typeface playball = Typeface.createFromAsset(context.getAssets(),"fonts/playball.ttf");
		Typeface satisfy = Typeface.createFromAsset(context.getAssets(),"fonts/satisfy.ttf");
		Typeface lavanderiaS = Typeface.createFromAsset(context.getAssets(),"fonts/lavanderiaS.ttf");

		//------------------------------------------------------------------

		Typeface akzidenz_grotesk = Typeface.createFromAsset(context.getAssets(),"fonts/akzidenz-grotesk.ttf");
		Typeface aehnir = Typeface.createFromAsset(context.getAssets(),"fonts/aehnir.ttf");
		Typeface times = Typeface.createFromAsset(context.getAssets(),"fonts/times.ttf");
		Typeface arial = Typeface.createFromAsset(context.getAssets(),"fonts/arial.ttf");
		Typeface comic = Typeface.createFromAsset(context.getAssets(),"fonts/comic.ttf");
		Typeface baskerville = Typeface.createFromAsset(context.getAssets(),"fonts/baskerville.ttf");
		Typeface bickhamscript = Typeface.createFromAsset(context.getAssets(),"fonts/bickhamscript.ttf");
		Typeface optima = Typeface.createFromAsset(context.getAssets(),"fonts/optima.ttf");

		//------------------------------------------------------------------

		if(FontStyle == 0){
			return  Typeface.DEFAULT_BOLD;
		}else if(FontStyle == 1){
			return fog;

		}else if(FontStyle == 2){
			return lato;
		}else if(FontStyle == 3){
			return mawns;
		}else if(FontStyle == 4){
			return namsoi;
		}else if(FontStyle == 5){
			return niconne;
		}else if(FontStyle == 6){
			return opensans;
		}else if(FontStyle == 7){
			return playball;
		}else if(FontStyle == 8){
			return satisfy;
		}else if(FontStyle == 9){
			return lavanderiaS;
		}else if(FontStyle == 10){
			return akzidenz_grotesk;
		}else if(FontStyle == 11){
			return aehnir;
		}else if(FontStyle == 12){
			return times;
		}else if(FontStyle == 13){
			return arial;
		}else if(FontStyle == 14){
			return comic;
		}else if(FontStyle == 15){
			return baskerville;
		}else if(FontStyle == 16){
			return bickhamscript;
		}else if(FontStyle == 17){
			return optima;
		}else{
			return  Typeface.DEFAULT_BOLD;
		}

	}
	public static Bitmap viewToBitmap(View view) {
		if(view.getWidth() > 0 && view.getHeight() > 0) {
			Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			view.draw(canvas);
			return bitmap;
		}
		return null;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if (bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static Bitmap replaceColor(Bitmap src, int fromColor, int targetColor) {
		if (src == null) {
			return null;
		}
		// Source image size
		int width = src.getWidth();
		int height = src.getHeight();
		int[] pixels = new int[width * height];
		//get pixels
		src.getPixels(pixels, 0, width, 0, 0, width, height);

		for (int x = 0; x < pixels.length; ++x) {
			pixels[x] = (pixels[x] == fromColor) ? targetColor : pixels[x];
//			pixels[x] =  targetColor ;
		}
		// create result bitmap output
		Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
		//set pixels
		result.setPixels(pixels, 0, width, 0, 0, width, height);

		return result;
	}

	public static String ImageStore(Context context, FrameLayout final_Logo, String fileName, boolean showToast){
		try{

			Bitmap bm = null;

			bm = getBitmapFromView(final_Logo);
			String folder_main = "LogoDesign";

		/*	File f = new File(Environment.getExternalStorageDirectory(), folder_main);
			if (!f.exists()) {
				f.mkdirs();
			}
*/
			int imageNum = 0;
			File LogoDesign_dir = new File(Environment.getExternalStorageDirectory(),"LogoDesign");
			if(!LogoDesign_dir.exists())
			{
				LogoDesign_dir.mkdir();
			}
			fileName = "image_" + String.valueOf(imageNum) + ".jpg";
			File output = new File(LogoDesign_dir, fileName);
			while (output.exists()){
				imageNum++;
				fileName = "image_" + String.valueOf(imageNum) + ".jpg";
				output = new File(LogoDesign_dir, fileName);
			}
			FileOutputStream outStream = new FileOutputStream(output);
			//bm=eraseColor(bm, Color.TRANSPARENT);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
			// flushing
			outStream.flush();
			// closing
			outStream.close();
			ContentValues image = new ContentValues();
			image.put(Images.Media.TITLE, "LogoDesign");
			image.put(Images.Media.DISPLAY_NAME, fileName);
			image.put(Images.Media.DESCRIPTION, "App Image");
			image.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
			image.put(Images.Media.MIME_TYPE, "image/jpg");
			image.put(Images.Media.ORIENTATION, 0);
			File parent = output.getParentFile();
			image.put(Images.ImageColumns.BUCKET_ID, parent.toString().toLowerCase(Locale.getDefault()).hashCode());
			image.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName().toLowerCase(Locale.getDefault()));
			image.put(Images.Media.SIZE, output.length());
			image.put(Images.Media.DATA, output.getAbsolutePath());
			//	Uri result = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
			if(showToast){
				Toast.makeText(context,"File is Saved in  " + output, Toast.LENGTH_SHORT).show();
			}
			Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(output));
			context.sendBroadcast(mediaScanIntent);

			return fileName;


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}


}
