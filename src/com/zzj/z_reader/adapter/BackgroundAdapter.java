package com.zzj.z_reader.adapter;

import com.zzj.z_reader.R;
import com.zzj.z_reader.bean.BackgroundSource;
import com.zzj.z_reader.shared.SetupShared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BackgroundAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private int isChoose;

	private final int[] background = BackgroundSource.backgroundDemo;

	public BackgroundAdapter(Context context) {
		// TODO Auto-generated constructor stub
		layoutInflater = LayoutInflater.from(context);
		isChoose = SetupShared.getBackgroundNum();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return background.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return background[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = layoutInflater
				.inflate(R.layout.ajust_background_item, null);
		ImageView ivSetBackground = (ImageView) view
				.findViewById(R.id.ivSetBackground);
		ImageView ivSetBackgroundChoose = (ImageView) view
				.findViewById(R.id.ivSetBackgroundChoose);

		ivSetBackground.setBackgroundResource(background[position]);

		view.setTag(position);

		if (isChoose == position) {
			ivSetBackgroundChoose.setVisibility(View.VISIBLE);
		} else {
			ivSetBackgroundChoose.setVisibility(View.GONE);
		}

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int thisViewNum = Integer.valueOf(v.getTag().toString());
				if (thisViewNum != isChoose) {
					isChoose = thisViewNum;
					SetupShared.setBackgroundNum(isChoose);
					notifyDataSetChanged();
				}
			}
		});

		return view;
	}

}
