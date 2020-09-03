package com.fsd.logodesign.methods;

import java.util.List;

import com.fsd.logodesign.R;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MultiSpinner extends Spinner implements OnMultiChoiceClickListener, OnCancelListener {

	private List<String> items;
	private boolean[] selected;
	private String defaultText="Choose formats";
	private MultiSpinnerListener listener;

	public MultiSpinner(Context context) {
		super(context);
	}

	public MultiSpinner(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if (isChecked)
			selected[which] = true;
		else
			selected[which] = false;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// refresh text on spinner
		StringBuffer spinnerBuffer = new StringBuffer();
		boolean someUnselected = false;
		for (int i = 0; i < items.size(); i++) {
			if (selected[i] == true) {
				spinnerBuffer.append(items.get(i));
				spinnerBuffer.append(", ");
			} else {
				someUnselected = true;
			}
		}
		String spinnerText;
		if (someUnselected) {
			spinnerText = spinnerBuffer.toString();
			if (spinnerText.length() > 2)
				spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
		} else {
			spinnerText = defaultText;
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.custom_spinner,new String[] { spinnerText });
		setAdapter(adapter);
		listener.onItemsSelected(selected);
	}

	@Override
	public boolean performClick() {
		TextView title = new TextView(getContext());
	    // You Can Customise your Title here
	    title.setText("File Formats");
	    title.setBackgroundColor(Color.BLACK);
	    title.setPadding(10, 15, 15, 10);
	    title.setGravity(Gravity.CENTER);
	    title.setTextColor(Color.WHITE);
	    title.setTextSize(22);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMultiChoiceItems(items.toArray(new CharSequence[items.size()]), selected, this);
		
		builder.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setOnCancelListener(this);
		 AlertDialog alert = builder.create();
		 alert.setCustomTitle(title);
		 
		 alert.show();
		
		return true;
	}

	public void setItems(List<String> items, String allText,
			MultiSpinnerListener listener) {
		this.items = items;
		Log.e("Testing","default text1 " + defaultText);
		this.defaultText = allText;
		Log.e("Testing","default text " + defaultText);
		this.listener = listener;

		// all selected by default
		selected = new boolean[items.size()];
		for (int i = 0; i < selected.length; i++)
			selected[i] = false;

		// all text on the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
				R.layout.custom_spinner, new String[] { "Choose Formats " });
		setAdapter(adapter);
	}

	public interface MultiSpinnerListener {
		public void onItemsSelected(boolean[] selected);
	}
}