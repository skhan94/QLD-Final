package com.fsd.logodesign.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fsd.logodesign.R;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class ShapeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    TypedArray shapeArray;
    Context context;

    public ShapeAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shapeArray = context.getResources().obtainTypedArray(R.array.shape_array);

//            Log.d("ImagesDrawable", "I : " + shapeArray);
//        Log.d("ImagesDrawable", "I : " + shapeArray.getDrawable(0));

    }

    @Override
    public int getCount() {
        return shapeArray.length();
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
            convertView = mInflater.inflate(R.layout.shape_layout, parent, false);
            holder = new Holder();
            holder._View = (ImageView) convertView.findViewById(R.id.shape_view);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

//        Glide.with(context).load(shapeArray.getResourceId(position, -1)).into(holder._View);
//        holder._View.setBackgroundResource(shapeArray.getResourceId(position, -1));
//        holder._View.setBackgroundResource(shapeArray.getResourceId(position, -1));
        holder._View.setImageResource(shapeArray.getResourceId(position, -1));
        return convertView;
    }

    /**
     * View holder for the views we need access to
     */
    private static class Holder {
        public ImageView _View;
    }
}
