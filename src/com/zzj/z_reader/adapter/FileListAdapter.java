package com.zzj.z_reader.adapter;

import java.util.ArrayList;
import java.util.List;
import com.zzj.z_reader.R;
import com.zzj.z_reader.bean.FileListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * title: 文件列表适配器
 * @author zzj 
 * @date 2014-7-26
 */
public class FileListAdapter extends BaseAdapter {
	
	private LayoutInflater layoutInflater;
	
	private List<FileListItem> fList = new ArrayList<FileListItem>();

	public FileListAdapter(Context context, List<FileListItem> fileListItems) {
		this.layoutInflater = LayoutInflater.from(context);
		this.fList = fileListItems;
	}

	public void addItem(FileListItem fItem) {
		this.fList.add(fItem);
	}

	public void setList(List<FileListItem> fileListItems) {
		if (fileListItems != null) {
			this.fList.clear();
			this.fList.addAll(fileListItems);
		}
	}

	public int getCount() {
		return this.fList.size();
	}

	public Object getItem(int position) {
		return fList.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.file_list_item, null);
			viewHolder.ivFileIcon = (ImageView) convertView.findViewById(R.id.ivFileItemIcon);
			viewHolder.tvFileName = (TextView) convertView.findViewById(R.id.tvFileItemName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.ivFileIcon.setImageResource(fList.get(position).fileIcon);
		viewHolder.tvFileName.setText(fList.get(position).fileName);
		
		return convertView;
	}
	
	
	class ViewHolder{
		public TextView tvFileName;
		public ImageView ivFileIcon;
	}
	
}
