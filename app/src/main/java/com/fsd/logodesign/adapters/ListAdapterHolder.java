/*
 * Copyright (C) 2014 VenomVendor <info@VenomVendor.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.fsd.logodesign.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.fsd.logodesign.R;
import com.fsd.logodesign.objectclasses.ColorObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListAdapterHolder extends RecyclerView.Adapter<ListAdapterHolder.ViewHolder> {

    Context context;
    private List<ColorObject> colorObjects = new ArrayList<ColorObject>();
//    int[] colorArray = {R.color.color_1, R.color.color_2 , R.color.color_3 ,R.color.color_4
//    , R.color.color_5 , R.color.color_6 , R.color.color_7 , R.color.color_8
//            , R.color.color_9 , R.color.color_10 , R.color.color_11 , R.color.color_12
//            , R.color.color_13 , R.color.color_14 , R.color.color_15 , R.color.color_16};

    ClickListener clickListener;


    public ListAdapterHolder(Context ctx, ArrayList<ColorObject> objects) {
        this.context = ctx;
        this.colorObjects = colorObjects;
//        colorArray = context.getResources().getIntArray(R.array.color_array);
        colorObjects = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.color_layout, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , final int position) {
        holder._View.setBackgroundColor(colorObjects.get(position).getResID());
    }

    @Override
    public int getItemCount() {
        return colorObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View _View; // Colors
        public CheckBox _TickView;


        public ViewHolder(View view) {
            super(view);
            _View = (View) view.findViewById(R.id.color_view);
            _TickView = (CheckBox) view.findViewById(R.id.view_alpha);
            _View.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }



    }
    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}
