package com.fsd.logodesign.objectclasses;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.pixplicity.sharp.Sharp;
import com.pixplicity.sharp.SharpDrawable;

import java.io.IOException;


public class ImageObject{




    private String _company_name;
    private String _image_name;
    private int _font_style;
    private int _image_color;
    private int _status;
    private Context mContext;
    private Drawable _drawable;
    private Sharp mSvg;
    View v;

    public ImageObject() {
        // TODO Auto-generated constructor stub
    }

    public ImageObject(Context context,String CompanyName,String ImageName,int ImageColor,int FontStyle, int status){
        this.mContext = context;
        this.setCompanyName(CompanyName);
        this.setImageNameNOSVG(ImageName);
        this.setImageColorNOSVG(ImageColor);
        this.setFontStyle(FontStyle);
        this.setStatus(status);
        try {
            this.setDrawable(getSVGDrawable(v));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //---------------------------------------------

    public String getImageName() {
        return _image_name;
    }

    private void setImageNameNOSVG(String image_name) { // this is will obselete the repletation
        this._image_name = image_name;
    }

    public void setImageName(String image_name) {
        this._image_name = image_name;
        try {
            this.setDrawable(getSVGDrawable(v));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //---------------------------------------------
    public int getFontStyle() {
        return _font_style;
    }

    public void setFontStyle(int font_style) {
        this._font_style = font_style;
    }

    //---------------------------------------------


    public String getCompanyName() {
        return _company_name;
    }

    public void setCompanyName(String company_name) {
        this._company_name = company_name;
    }
    //---------------------------------------------

    public int getImageColor() {
        return _image_color;
    }

    private void setImageColorNOSVG(int image_color) { // this is prevent to extract drawable multiple times
        this._image_color = image_color;
    }

    public void setImageColor(int image_color) {
        this._image_color = image_color;
        try {
            this.setDrawable(getSVGDrawable(v));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //---------------------------------------------

    public int getStatus() {
        return _status;
    }

    public void setStatus(int status) {
        this._status = status;
    }

    //---------------------------------------------

    public String getImageAssetName(){
        return "svg/"+getImageName()+getImageColor()+".svg";
    }

    //---------------------------------------------

    public Sharp getSVG(String ImageAssetName) throws IOException{
        Sharp ins = mSvg.loadAsset(mContext.getAssets(), ImageAssetName);
        return  ins;
//        SVGBuilder svgBuilder_1 = new SVGBuilder().readFromAsset(mContext.getAssets(),ImageAssetName);
//        return svgBuilder_1.build();
    }

    //---------------------------------------------

    public Sharp getSVG() throws IOException{
        Sharp ins = mSvg.loadAsset(mContext.getAssets(), getImageAssetName());
        return  ins;
//        SVGBuilder svgBuilder_1 = new SVGBuilder().readFromAsset(mContext.getAssets(),getImageAssetName());
//        return svgBuilder_1.build();
    }

    //---------------------------------------------

    public SharpDrawable getSVGDrawable(View v) throws IOException{
        return getSVG().getDrawable(v);
    }

    //---------------------------------------------

    public void setDrawable(Drawable drawable) {
        this._drawable = drawable;
    }
    //---------------------------------------------
    public Drawable getDrawable(){
        return this._drawable;
    }


}