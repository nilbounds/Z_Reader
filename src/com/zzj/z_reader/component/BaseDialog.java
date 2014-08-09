package com.zzj.z_reader.component;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.view.LayoutInflater;

/**
 * 
 * title:基类dialog
 * 
 * @author zzj
 * @date 2014-8-5
 */
public abstract class BaseDialog {

	protected Builder builder;
	protected LayoutInflater layoutInflater;

	public BaseDialog(Activity activity) {
		builder = new Builder(activity);
		layoutInflater = LayoutInflater.from(activity);
	}

	public abstract void show();

}
