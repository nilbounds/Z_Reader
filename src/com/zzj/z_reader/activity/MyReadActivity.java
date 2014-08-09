package com.zzj.z_reader.activity;

import com.zzj.z_reader.R;
import com.zzj.z_reader.component.MyPaperTextShow;
import com.zzj.z_reader.component.MySlideTextShow;
import com.zzj.z_reader.shared.SetupShared;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * title: 文本显示的界面
 * 
 * @author zzj
 * @date 2014-7-28
 */
public class MyReadActivity extends Activity {

	private boolean fromShortcut = false;

	private MySlideTextShow mySlideTextShow;
	private MyPaperTextShow myPaperTextShow;
	private GestureDetector detector;

	private int pageMode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		SetupShared.initSetupShared(getApplication());

		pageMode = SetupShared.getEffectNum();

		Intent intent = getIntent();
		String filePath = intent.getStringExtra("filePath");
		fromShortcut = intent.getBooleanExtra("fromShortcut", false);

		Log.i("MyReadActivity", filePath);
		init(filePath);

	}

	private void init(final String filePath) {
		switch (pageMode) {
		case 0:
			setContentView(R.layout.text_show);
			mySlideTextShow = new MySlideTextShow(this);
			mySlideTextShow.init();
			detector = new GestureDetector(getBaseContext(), mySlideTextShow);
			mySlideTextShow.getViewFlipper().postDelayed(new Runnable() {
				@Override
				public void run() {
					mySlideTextShow.loadBook(filePath);
				}
			}, 300);
			break;
		case 1:
			myPaperTextShow = new MyPaperTextShow(this);
			myPaperTextShow.loadBook(filePath);
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (pageMode == 0) {
			return this.detector.onTouchEvent(event);
		} else {
			return super.onTouchEvent(event);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (fromShortcut) {
				Intent intent = new Intent(getBaseContext(), MainActivity.class);
				startActivity(intent);
			}
			this.finish();
		}
		return true;
	}

}
