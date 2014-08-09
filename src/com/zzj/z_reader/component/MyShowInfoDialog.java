package com.zzj.z_reader.component;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import com.zzj.z_reader.bean.BookInfo;

public class MyShowInfoDialog {
	private Builder builder;

	public BookInfo chooseBook;

	public MyShowInfoDialog(Activity activity) {
		this.builder = new Builder(activity);
	}

	public void show(BookInfo bookInfo) {
		builder.setTitle(bookInfo.name);
		builder.setMessage(bookInfo.path);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
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
