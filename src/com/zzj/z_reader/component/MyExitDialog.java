package com.zzj.z_reader.component;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * 
 * title:退出提示框
 * 
 * @author zzj
 * @date 2014-7-30
 */
public class MyExitDialog extends BaseDialog {

	private Activity activity;

	public MyExitDialog(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	public void show() {
		builder.setTitle("确定退出？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
				dialog.dismiss();
				System.exit(0);
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
