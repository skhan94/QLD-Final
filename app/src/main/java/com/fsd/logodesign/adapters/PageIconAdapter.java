package com.fsd.logodesign.adapters;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsd.logodesign.R;
import com.fsd.logodesign.methods.Methods;
import com.fsd.logodesign.objectclasses.ImageObject;

import java.util.ArrayList;

public class PageIconAdapter extends BaseAdapter{

	private ArrayList<ImageObject> _ArrayList;
	private LayoutInflater mInflater;
	private Context mContext;


	public PageIconAdapter(Context context,ArrayList<ImageObject> arrayList) {
		_ArrayList = arrayList;
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _ArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return _ArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/**
	 * @param arrayList
	 */
	public void setArray(ArrayList<ImageObject> arrayList){
		//_ArrayList.clear();
		_ArrayList = arrayList;
		notifyDataSetChanged();

	}

	private static class ViewHolder{
		ImageView imageView;
		ImageView icon_gridview_check_button;
		TextView textView;
		
	}
	
	ViewHolder holder;
	
	@Override
	public View getView(int position, View vi, ViewGroup parent) {
		// TODO Auto-generated method stub
		try{
		

			if (vi == null){  
				vi = mInflater.inflate(R.layout.icon_grid_item, null);
				holder = new ViewHolder();
				
				holder.textView = (TextView) vi.findViewById(R.id.icon_gridview_textview);  
				holder.imageView = (ImageView) vi.findViewById(R.id.icon_gridview_imageview);  
				holder.icon_gridview_check_button = (ImageView)vi.findViewById(R.id.icon_gridview_check_button);
		
				vi.setTag(holder);
			} else {
                holder = (ViewHolder) vi.getTag();
            }


			Bitmap b = ((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.logo_bg)).getBitmap();
			int width = b.getWidth();
		
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(width/1.1),(int)(width/1.1),Gravity.CENTER);
			LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER);
			params_1.setMargins(0, 0, 0, 5);
			

			
			if(_ArrayList.get(position).getStatus() == 1){
				holder.icon_gridview_check_button.setVisibility(View.VISIBLE);
			}else{
				holder.icon_gridview_check_button.setVisibility(View.GONE);
			}
			
			holder.textView = Methods.SetFontStyle(holder.textView, _ArrayList.get(position).getFontStyle(),mContext);
			holder.textView.setText(""+_ArrayList.get(position).getCompanyName());
			holder.imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			Drawable drawable_1  = _ArrayList.get(position).getDrawable();
		
			holder.imageView.setImageDrawable(drawable_1);

			holder.textView.setLayoutParams(params_1); 
			holder.textView.setGravity(Gravity.CENTER);

			holder.imageView.setLayoutParams(params);

			return vi;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}




	


}