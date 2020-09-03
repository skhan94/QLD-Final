package com.fsd.logodesign.objectclasses;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hamza on 5/20/2016.
 */
public class ViewDetails {

    String type;



    View view;
    ViewGroup viewGroup;
    String name = null;
    Bitmap image = null;
    boolean selected = false;

    int uniqueid;
    int layerId;



    public ViewDetails(String name, Bitmap image, boolean selected, View view) {
        this.name = name;
        this.image = image;
        this.selected = selected;
        this.view = view;

    }
    public ViewDetails(String name, Bitmap image, boolean selected, View view, int position, int layerId) {
        this.name = name;
        this.image = image;
        this.selected = selected;
        this.view = view;
        this.uniqueid=position;
        this.layerId=layerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
//    View view = null;

      public ViewDetails(String type, View view, ViewGroup viewGroup) {
        this.type = type;
        this.view = view;
        this.viewGroup = viewGroup;
    }
    public ViewDetails(String type, View view, ViewGroup viewGroup, int position) {
        this.type = type;
        this.view = view;
        this.viewGroup = viewGroup;
        this.uniqueid=position;
    }

    public int getLayerId() {
        return layerId;
    }

    public void setLayerId(int LayerID) {
        this.layerId = LayerID;
    }


    public int getUniqueID() {
        return uniqueid;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueid = uniqueID;
    }
    public View getView() {
        return view;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public String getType() {
        return type;
    }
}
