package com.zzj.z_reader.shared;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * title:存储设置的属性
 * 
 * @author zzj
 * @date 2014-7-30
 */
public class SetupShared {

	public static final String FONT_SIZE = "font_zize";
	public static final String FONT_COLOR = "font_color";
	public static final String SCREEN_LIGHT = "screen_light";
	public static final String BACKGROUND = "background";
	public static final String PAGE_EFFECT = "page_effect";
	
	private static SharedPreferences sp = null;
	private static Editor editor = null;

	private SetupShared(){
		
	}
	
	public static final void initSetupShared(Application application) {
		sp = application.getSharedPreferences("SetupShared",
				Context.MODE_PRIVATE);
	}

	/**
	 * 保存文本数据的操作
	 * 
	 * @param where
	 * @param strData
	 */
	public static final void saveDate(String where, String strData) {
		editor = sp.edit();
		editor.putString(where, strData);
		editor.commit();
	}

	/**
	 * 保存数据的操作
	 * 
	 * @param where
	 * @param strData
	 */
	public static final void saveDate(String where, int intData) {
		editor = sp.edit();
		editor.putInt(where, intData);
		editor.commit();
	}

	/**
	 * 清空存储的数据
	 */
	public static void clear() {
		editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 存储字体大小信息
	 * 
	 * @param fontSize
	 */
	public static void setFontSize(int fontSize) {
		// TODO Auto-generated method stub
		saveDate(FONT_SIZE, fontSize);
	}

	/**
	 * 获取字体大小信息
	 * 默认是 18
	 * @return
	 */
	public static int getFontSize() {
		// TODO Auto-generated method stub
		return sp.getInt(FONT_SIZE, 18);
	}

	/**
	 * 设置字体颜色信息
	 * 
	 * @param FontColor
	 */
	public static void setFontColor(int FontColor) {
		// TODO Auto-generated method stub
		saveDate(FONT_COLOR, FontColor);
	}

	/**
	 * 获取字体颜色信息
	 * 默认是黑色 0xFF000000
	 * @return
	 */
	public static int getFontColor() {
		// TODO Auto-generated method stub
		return sp.getInt(FONT_COLOR, 0xFF000000);
	}

	
	/**
	 * 设置屏幕亮度信息
	 * 
	 * @param FontColor
	 */
	public static void setSeenLight(int screenLight) {
		// TODO Auto-generated method stub
		saveDate(SCREEN_LIGHT, screenLight);
	}

	/**
	 * 获取屏幕亮度信息
	 * 默认是50%
	 * @return
	 */
	public static int getScreenLight() {
		// TODO Auto-generated method stub
		return sp.getInt(SCREEN_LIGHT, 120);
	}

	
	
	/**
	 * 设置对应数值背景
	 * 
	 * @param bgNum
	 */
	public static void setBackgroundNum(int bgNum) {
		// TODO Auto-generated method stub
		saveDate(BACKGROUND, bgNum);
	}

	/**
	 * 获取对应背景的数值
	 * 默认是0
	 * @return
	 */
	public static int getBackgroundNum() {
		// TODO Auto-generated method stub
		return sp.getInt(BACKGROUND, 0);
	}

	/**
	 * 设置对应数值翻页效果
	 * 
	 * @param effectNum
	 */
	public static void setEffectNum(int effectNum) {
		// TODO Auto-generated method stub
		saveDate(PAGE_EFFECT, effectNum);
	}

	/**
	 * 获取对应翻页效果的数值
	 * 默认是0
	 * 0:滑动模式
	 * 1:纸张模式
	 * @return
	 */
	public static int getEffectNum() {
		// TODO Auto-generated method stub
		return sp.getInt(PAGE_EFFECT, 0);
	}

}
