package com.zzj.z_reader.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zzj.z_reader.R;
import com.zzj.z_reader.bean.BookInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * 
 * title: 首页书架的列表适配器
 * 
 * @author zzj
 * @date 2014-7-30
 */
public class ShelfAdapter extends BaseAdapter {

	public int[] size;
	public int realTotalRow;
	public int showTotalRow;
	public int bookCount; // 图书的数量

	private List<BookInfo> bookInfos = new ArrayList<BookInfo>();
	private LayoutInflater layoutInflater;

	private OnClickListener mShelfItemOnClick;
	private OnCreateContextMenuListener mCreateMenuListener;

	public ShelfAdapter(Context context, List<BookInfo> bookInfos,
			OnClickListener mShelfItemOnClick,OnCreateContextMenuListener mCreateMenuListener) {
		this.layoutInflater = LayoutInflater.from(context);
		this.mShelfItemOnClick = mShelfItemOnClick;
		this.mCreateMenuListener = mCreateMenuListener;
		if (bookInfos != null) {
			this.bookInfos = bookInfos;
		}
		resize();
	}

	public void resize(){
		bookCount = bookInfos.size();
		if (bookCount % 3 > 0) {
			realTotalRow = bookCount / 3 + 1;
		} else {
			realTotalRow = bookCount / 3;
		}
		if (realTotalRow < 4) {
			showTotalRow = 4;
		} else {
			showTotalRow = realTotalRow;
		}
		size = new int[showTotalRow];
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (size.length > 3) {
			return size.length;
		} else {
			return 3;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return size[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View layout = layoutInflater.inflate(R.layout.shelf_list_item, null);
		if (position < realTotalRow) {
			int buttonNum = 3;
			if (position + 1 == realTotalRow) {
				buttonNum = bookCount % 3;
			}

			for (int i = 0; i < buttonNum; i++) {
				BookInfo bookInfo = null;
				Button button = null;
				switch (i) {
				case 0:
					bookInfo = bookInfos.get(position * 3);
					button = (Button) layout.findViewById(R.id.button_1);
					setBookInfo(button, bookInfo, position * 3);
					break;
				case 1:
					bookInfo = bookInfos.get(position * 3 + 1);
					button = (Button) layout.findViewById(R.id.button_2);
					setBookInfo(button, bookInfo, position * 3 + 1);
					break;
				case 2:
					bookInfo = bookInfos.get(position * 3 + 2);
					button = (Button) layout.findViewById(R.id.button_3);
					setBookInfo(button, bookInfo, position * 3 + 2);
					break;

				default:
					break;
				}
			}
		}
		return layout;
	}

	private void setBookInfo(Button button, BookInfo bookInfo, int id) {
		button.setVisibility(View.VISIBLE);
		button.setId(id);
		button.setText(bookInfo.name);
		if (mShelfItemOnClick != null) {
			button.setOnClickListener(mShelfItemOnClick);
		}
		if (mCreateMenuListener != null) {
			button.setOnCreateContextMenuListener(mCreateMenuListener);
		}
	}
}
