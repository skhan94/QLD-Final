package com.fsd.logodesign.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fsd.logodesign.R;
import com.fsd.logodesign.objectclasses.ColorObject;

import java.util.ArrayList;

/** An array adapter that knows how to render views when given CustomData classes */
public class ColorArrayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    int[] colorArray;
    Context context;
    ArrayList<ColorObject> colorObjects;

    int selectedIndex = -1;

    int previouslySelected = -1;
    public ColorArrayAdapter(Context context , ArrayList<ColorObject> colorObjects) {
        this.context = context;
        this.colorObjects = colorObjects;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        colorArray = context.getResources().getIntArray(R.array.color_array);
    }

    @Override
    public int getCount() {
        return colorObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.color_layout, parent, false);
            holder = new Holder();
            holder._View = (View) convertView.findViewById(R.id.color_view);
            holder._TickView = (CheckBox) convertView.findViewById(R.id.view_alpha);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

//        if(selectedIndex == position){
//            holder._TickView.setChecked(true);
//        }else{
//            holder._TickView.setChecked(false);
//        }

//        if (colorObjects.get(position).isSelected ) {
////            holder._TickView.setAlpha(0.5f);
////            ((FrameLayout) convertView).setForeground(context.getResources().getDrawable(R.drawable.check));
////
//            holder._TickView.setChecked(true);
//        } else {
////            holder._TickView.setAlpha(0.0f);
//////            ((FrameLayout) convertView).setForeground(null);
//            holder._TickView.setChecked(false);
//        }

        Log.d("asdhkjas" , "Pos : " + position);
        holder._View.setBackgroundColor(colorArray[position]);

        return convertView;
    }

    public void changeSelectedItem(int pos) {

        for (int i = 0 ; i < colorObjects.size() ; i++){
            if(i == pos)
                colorObjects.get(i).setIsSelected(true);
            else
                colorObjects.get(i).setIsSelected(false);
        }
        notifyDataSetChanged();
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public View _View; // Colors
        public CheckBox _TickView;
    }

    public void setSelectedIndex(int index){
        if(previouslySelected == -1)
            previouslySelected = index;
        else
            previouslySelected = selectedIndex;

        selectedIndex = index;
        colorObjects.get(index).setIsSelected(true);
        colorObjects.get(previouslySelected).setIsSelected(false);
        notifyDataSetChanged();
    }

}
