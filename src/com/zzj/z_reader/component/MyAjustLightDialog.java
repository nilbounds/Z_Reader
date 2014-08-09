package com.zzj.z_reader.component;

import com.zzj.z_reader.R;
import com.zzj.z_reader.shared.SetupShared;
import com.zzj.z_reader.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * title:进度条拖动选择框
 * 
 * @author zzj
 * @date 2014-7-31
 */
public class MyAjustLightDialog extends BaseDialog {

	private SeekBar sbScreenLight;
	private Activity activity;

	public MyAjustLightDialog(Activity activity) {
		super(activity);
		builder = new Builder(activity);
		this.activity = activity;
		layoutInflater = LayoutInflater.from(activity);
	}

	public void show() {
		builder.setTitle("调整亮度");
		int normal = Settings.System.getInt(activity.getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS, 255);
		View view = layoutInflater.inflate(R.layout.ajust_screen_light, null);
		sbScreenLight = (SeekBar) view.findViewById(R.id.sbSetScreenLight);
		sbScreenLight.setOnSeekBarChangeListener(mChangeListener);
		sbScreenLight.setMax(255);
		sbScreenLight.setProgress(normal);
		builder.setView(view);
		builder.create().show();
	}

	OnSeekBarChangeListener mChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			int num = sbScreenLight.getProgress();
			Settings.System.putInt(activity.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS, num);
			num = Settings.System.getInt(activity.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS, -1);
			WindowManager.LayoutParams wl = activity.getWindow()
					.getAttributes();
			float tmpLightToSet = (float) num / 255;
			if (tmpLightToSet > 0 && tmpLightToSet <= 1) {
				wl.screenBrightness = tmpLightToSet;
			}
			SetupShared.setSeenLight(num);
			activity.getWindow().setAttributes(wl);
			ToastUtil.show(activity,
					String.valueOf(progress * 100 / seekBar.getMax()) + "%");
		}
	};

}
