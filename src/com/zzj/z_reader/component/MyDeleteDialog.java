package com.zzj.z_reader.component;

import com.zzj.z_reader.bean.BookInfo;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

/**
 * 
 * title:删除提示框
 * @author zzj 
 * @date 2014-8-3
 */
public class MyDeleteDialog {

	private Builder builder;
	
	public BookInfo chooseBook;
	
	public MyDeleteDialog(Activity activity){
		this.builder = new Builder(activity);
	}
	
	
	public void show(String fileName,final MyCallBack myCallBack){
		builder.setTitle("确定删除:" + fileName +"?");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				myCallBack.callback("");
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
