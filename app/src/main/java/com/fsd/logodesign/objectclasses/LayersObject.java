package com.fsd.logodesign.objectclasses;

import android.graphics.Bitmap;
import android.view.View;

import java.io.Serializable;

/**
 * Created by hamza on 5/18/2016.
 */
public class LayersObject implements Serializable{
    String name = null;
    Bitmap image = null;
    boolean selected = false;
    View view = null;

    public LayersObject(String name, Bitmap image, boolean selected, View view) {
        this.name = name;
        this.image = image;
        this.selected = selected;
        this.view = view;
    }

    public LayersObject(String name, Bitmap image, boolean selected) {
        this.name = name;
        this.image = image;
        this.selected = selected;
    }


    public View getView() {
        return view;
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

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
