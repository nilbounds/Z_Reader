package com.zzj.z_reader.component;

import android.app.Activity;
import android.widget.GridView;

import com.zzj.z_reader.R;
import com.zzj.z_reader.adapter.BackgroundAdapter;

/**
 * 
 * title:背景选择框
 * 
 * @author zzj
 * @date 2014-7-31
 */
public class MyAjustBackgroundDialog extends BaseDialog {

	private GridView gvBackground;
	private BackgroundAdapter adapter;

	public MyAjustBackgroundDialog(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
		adapter = new BackgroundAdapter(activity);
	}

	public void show() {
		builder.setTitle("选择背景主题");
		gvBackground = (GridView) layoutInflater.inflate(
				R.layout.ajust_background, null);
		gvBackground.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		builder.setView(gvBackground);
		builder.create().show();
	}

}
