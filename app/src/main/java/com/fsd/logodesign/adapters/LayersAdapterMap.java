package com.fsd.logodesign.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsd.logodesign.Custom_Designing;
import com.fsd.logodesign.R;
//import com.fsd.logodesign.objectclasses.LayerObject;
import com.fsd.logodesign.objectclasses.LayersObject;
import com.fsd.logodesign.objectclasses.ViewDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamza on 5/18/2016.
 */
public class LayersAdapterMap extends BaseAdapter {

    private ArrayList<ViewDetails> layersObjects;
   int position1=0;
    Context context;
    ClickListener clickListener;
    ClickListener clickListenerDelete;
    ClickListener clickListernerSorting;
    VisibilityClickListener clickListenerVisibility;
  //  public LayerObject layerObject;
    public LayersAdapterMap(Context context,
                            ArrayList<ViewDetails> layersObjects) {
        this.context = context;
        this.layersObjects = layersObjects;
       // this.layerObject=new LayerObject();
      //  this.layerObject=layerObject;
    }

    public void clearAllItems(){
        layersObjects.clear();
    }

    @Override
    public int getCount() {
        return layersObjects.size();
    }

    @Override
    public ViewDetails getItem(int i) {
        return layersObjects.get(i);
    }

    public void setSelectedItem(int pos) {
        position1=pos;
        for (int i = 0; i < layersObjects.size(); i++) {
            if (pos == i) {
                layersObjects.get(i).setSelected(true);
                position1 = pos;
            }
            else
                layersObjects.get(i).setSelected(false);     // Set previously selected to false
        }

        notifyDataSetChanged();
     //   Custom_Designing.selectedposition=pos;
    }
    public int getSelectedItem()
    {
        return position1;
    }
    public void addItem(ViewDetails viewDetails) {
        for (int i = 0; i < layersObjects.size(); i++) {
            layersObjects.get(i).setSelected(false);     // Set previously selected to false
        }
        layersObjects.add(viewDetails);     // And newly added item will be highlighted
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void remove(ViewDetails item) {
        layersObjects.remove(item);
        notifyDataSetChanged();
    }

    public void insert(ViewDetails item, int position) {

        layersObjects.add(position, item);
        notifyDataSetChanged();
    }

    int imageId = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layers_row_view, null);

            holder = new ViewHolder();
            holder.layersview_checkBox = (CheckBox) convertView.findViewById(R.id.layersview_checkBox);
            holder.layersview_image = (ImageView) convertView.findViewById(R.id.layersview_image);
            holder.layer_delete = (ImageView) convertView.findViewById(R.id.layer_delete);
            holder.layer_visibility = (ImageView) convertView.findViewById(R.id.layer_visibility);
            holder.layer_sorting = (ImageView) convertView.findViewById(R.id.layer_sorting);

            convertView.setTag(holder);
            holder.layersview_checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Custom_Designing.selectedposition = position;
                    CheckBox cb = (CheckBox) v;
                    ViewDetails checkBox = (ViewDetails) cb.getTag();
                    checkBox.setSelected(cb.isChecked());
                    try {
                        clickListener.onItemClick(Integer.parseInt(cb.getTag(R.string.pos).toString()));
                    } catch (Exception e) {

                    }

                    for (int i = 0; i < layersObjects.size(); i++) {
                        layersObjects.get(i).setSelected(false);
                    }
                    checkBox.setSelected(cb.isChecked());
                    notifyDataSetChanged();
//                    Log.d("DataChanges", "cb.isChecked() : " + cb.isChecked());
                }
            });
          /*  holder.layer_sorting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView iv = (ImageView) view;
                    CheckBox cb = (CheckBox) view;
//                    clickListenerVisibility.onItemClick(Integer.parseInt(iv.getTag(R.string.pos).toString()));
                    if( clickListernerSorting != null)
                        clickListernerSorting.onItemClick(Integer.parseInt(cb.getTag(R.string.pos).toString()));
                }
            });*/
            holder.layer_visibility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView iv = (ImageView) view;
//                    clickListenerVisibility.onItemClick(Integer.parseInt(iv.getTag(R.string.pos).toString()));
                    if( clickListenerVisibility != null)
                    clickListenerVisibility.onVisibleItemClick(Integer.parseInt(iv.getTag(R.string.pos).toString()), iv);
                }
            });

            holder.layer_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView iv = (ImageView) view;
                    removeItemAt(Integer.parseInt(iv.getTag(R.string.pos).toString()));
                    try {
                        clickListenerDelete.onItemClick(Integer.parseInt(iv.getTag(R.string.pos).toString()));
                    } catch (Exception e) {

                    }

                }
            });

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ViewDetails object = layersObjects.get(position);
//        holder.layersview_name.setText(object.getName());
        holder.layersview_checkBox.setChecked(object.isSelected());

//        Shape , Font

        if (object.getType().equalsIgnoreCase("Shape"))
            imageId = R.drawable.layer_shape;
        else if (object.getType().equalsIgnoreCase("Font"))
            imageId = R.drawable.layer_text;
        else if (object.getType().equalsIgnoreCase("Freehand"))
            imageId = R.drawable.layer_brush;
        else if (object.getType().equalsIgnoreCase("Logo"))
            imageId = R.drawable.layer_logo;
        else  //if (object.getType().equalsIgnoreCase("Gallery"))
            imageId = R.drawable.layer_img;

        holder.layersview_image.setImageResource(imageId);
        holder.layersview_checkBox.setTag(object);
        holder.layersview_checkBox.setTag(R.string.pos, "" + position);
        holder.layer_delete.setTag(R.string.pos, "" + position);
        holder.layer_visibility.setTag(R.string.pos, "" + position);
        return convertView;
    }

    private void removeItemAt(int i) {
        layersObjects.remove(i);
        notifyDataSetChanged();

    }


    public void setOnCheckBoxClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnDeleteClickListener(ClickListener clickListener) {
        this.clickListenerDelete = clickListener;
    }

    public void setOnVisibilityClickListener(VisibilityClickListener clickListener) {
        this.clickListenerVisibility = clickListener;
    }
    public void setOnSortingClickListener(ClickListener clickListener) {
        this.clickListernerSorting = clickListener;
    }

    private class ViewHolder {
        CheckBox layersview_checkBox;
        ImageView layersview_image;
        ImageView layer_delete;
        ImageView layer_visibility;
        ImageView layer_sorting;
    }

    public interface ClickListener {
        void onItemClick(int position);

    }

    public interface VisibilityClickListener {
        void onVisibleItemClick(int position, ImageView view);
    }

}