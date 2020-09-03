package com.fsd.logodesign.objectclasses;

/**
 * Created by hamza on 6/1/2016.
 */
public class ColorObject {
    int resID;
    public boolean isSelected;

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public ColorObject(int resID, boolean isSelected) {
        this.resID = resID;
        this.isSelected = isSelected;
    }
}
