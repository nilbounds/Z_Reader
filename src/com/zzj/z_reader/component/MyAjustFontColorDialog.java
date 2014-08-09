package com.zzj.z_reader.component;

import com.zzj.z_reader.shared.SetupShared;
import com.zzj.z_reader.view.ColorPickerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.WindowManager;

@SuppressLint("DrawAllocation")
public class MyAjustFontColorDialog extends BaseDialog {

	private AlertDialog dialog;

	private Activity activity;

	private int height;
	private int width;

	/**
	 * 
	 * @param context
	 * @param initialColor
	 *            初始颜色
	 * @param title
	 *            标题
	 * @param listener
	 *            回调
	 */
	@SuppressWarnings("deprecation")
	public MyAjustFontColorDialog(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
		this.activity = activity;
		WindowManager manager = activity.getWindow().getWindowManager();
		height = (int) (manager.getDefaultDisplay().getHeight() * 0.5f);
		width = (int) (manager.getDefaultDisplay().getWidth() * 0.7f);
	}

	public void show() {
		builder.setTitle("选择字体颜色");
		int initColor = SetupShared.getFontColor();
		ColorPickerView myView = new ColorPickerView(activity, height, width,
				initColor);
		myView.setmListener(colorChangedListener);
		builder.setView(myView);
		dialog = builder.create();
		dialog.show();
	}

	ColorPickerView.OnColorChangedListener colorChangedListener = new ColorPickerView.OnColorChangedListener() {

		@Override
		public void colorChanged(int color) {
			// TODO Auto-generated method stub
			SetupShared.setFontColor(color);
			dialog.dismiss();
		}
	};

}
