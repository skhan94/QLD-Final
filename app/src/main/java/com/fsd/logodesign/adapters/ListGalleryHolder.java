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
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fsd.logodesign.R;
import com.fsd.logodesign.objectclasses.ImageObject;

import java.util.ArrayList;

public class ListGalleryHolder extends RecyclerView.Adapter<ListGalleryHolder.ViewHolder> {

    Context context;
    private ArrayList<ImageObject> _ArrayList;

    ClickListener clickListener;

    public ListGalleryHolder(Context ctx,ArrayList<ImageObject> arrayList) {
        this.context = ctx;
        _ArrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.icon_gallery_item, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , final int position) {
        Drawable drawable_1  = _ArrayList.get(position).getDrawable();
        holder.imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        holder.imageView.setImageDrawable(drawable_1);
    }

    @Override
    public int getItemCount() {
        try {
            if (_ArrayList.size() > 5)    // Added because there is a Scaling problem to the last 14 SVGS
                return _ArrayList.size() - 5;
        }
        catch (Exception e)
        {
            Log.e("Testing","Exception " + e);
        }

            return _ArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        ImageView icon_gridview_check_button;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.icon_gridview_imageview);
            icon_gridview_check_button = (ImageView)view.findViewById(R.id.icon_gridview_check_button);
            imageView.setOnClickListener(this);
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
