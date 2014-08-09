package com.zzj.z_reader.component;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.RadioButton;

import com.zzj.z_reader.R;
import com.zzj.z_reader.shared.SetupShared;

/**
 * 
 * title:翻页效果选择框
 * 
 * @author zzj
 * @date 2014-7-31
 */
public class MyAjustPaperMode extends BaseDialog {

	public MyAjustPaperMode(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
	}

	public void show() {
		builder.setTitle("选择翻页效果");
		View view = layoutInflater.inflate(R.layout.ajust_page_effect, null);
		final RadioButton rbPaperMode = (RadioButton) view
				.findViewById(R.id.rbPaperMode);
		final RadioButton rbSlideMode = (RadioButton) view
				.findViewById(R.id.rbSlideMode);
		if (0 == SetupShared.getEffectNum()) {
			rbSlideMode.setChecked(true);
		} else {
			rbPaperMode.setChecked(true);
		}
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (rbPaperMode.isChecked()) {
					SetupShared.setEffectNum(1);
				} else if (rbSlideMode.isChecked()) {
					SetupShared.setEffectNum(0);
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setView(view);
		builder.create().show();
	}

}
