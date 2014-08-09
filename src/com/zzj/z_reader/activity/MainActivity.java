package com.zzj.z_reader.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zzj.z_reader.R;
import com.zzj.z_reader.adapter.ShelfAdapter;
import com.zzj.z_reader.bean.BookInfo;
import com.zzj.z_reader.component.MyAjustBackgroundDialog;
import com.zzj.z_reader.component.MyAjustFontColorDialog;
import com.zzj.z_reader.component.MyAjustFontSizeDialog;
import com.zzj.z_reader.component.MyAjustLightDialog;
import com.zzj.z_reader.component.MyAjustPaperMode;
import com.zzj.z_reader.component.MyCallBack;
import com.zzj.z_reader.component.MyDeleteDialog;
import com.zzj.z_reader.component.MyExitDialog;
import com.zzj.z_reader.component.MyShowInfoDialog;
import com.zzj.z_reader.service.BookInfoService;
import com.zzj.z_reader.service.BookInfoServiceImpl;
import com.zzj.z_reader.shared.SetupShared;
import com.zzj.z_reader.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ListView;

/**
 * title:主界面
 * 
 * @author zzj
 * @date 2014-7-29
 */
public class MainActivity extends Activity {

	private ListView lvShelf;
	private ShelfAdapter adapter;

	private MyExitDialog myAlertDialog;
	private MyDeleteDialog myDeleteDialog;
	private MyShowInfoDialog myShowInfoDialog;
	private MyAjustPaperMode myAjustPaperMode;
	private MyAjustLightDialog myAjustLightDialog;
	private MyAjustFontSizeDialog myAjustFontSizeDialog;
	private MyAjustFontColorDialog myAjustFontColorDialog;
	private MyAjustBackgroundDialog myAjustBackgroundDialog;

	private List<BookInfo> bookInfos = new ArrayList<BookInfo>();
	private BookInfoService bookInfoService;

	private BookInfo chooseBook;
	private long exitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SetupShared.initSetupShared(getApplication());

		lvShelf = (ListView) findViewById(R.id.lvShelf);

		myAlertDialog = new MyExitDialog(this);
		myDeleteDialog = new MyDeleteDialog(this);
		myShowInfoDialog = new MyShowInfoDialog(this);
		myAjustPaperMode = new MyAjustPaperMode(this);
		myAjustLightDialog = new MyAjustLightDialog(this);
		myAjustFontSizeDialog = new MyAjustFontSizeDialog(this);
		myAjustFontColorDialog = new MyAjustFontColorDialog(this);
		myAjustBackgroundDialog = new MyAjustBackgroundDialog(this);

		bookInfoService = new BookInfoServiceImpl(getBaseContext());

		update();

	}

	private void update() {
		bookInfos.clear();
		bookInfos.addAll(bookInfoService.getAllBookInfo());
		adapter = new ShelfAdapter(getBaseContext(), bookInfos,
				mShelfItemOnClick, mCreateMenuListener);
		lvShelf.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	MyCallBack deleteBack = new MyCallBack() {

		@Override
		public void callback(String choose) {
			// TODO Auto-generated method stub
			bookInfoService.deleteBookInfo(chooseBook.id);
			bookInfos.remove(chooseBook);
			adapter.resize();
			adapter.notifyDataSetChanged();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_open:
			Intent intent = new Intent(getBaseContext(), FileListActivity.class);
			startActivityForResult(intent, 1);
			return true;
		case R.id.action_exit:
			myAlertDialog.show();
			return true;
		case R.id.set_font_color:
			myAjustFontColorDialog.show();
			break;
		case R.id.set_font_size:
			myAjustFontSizeDialog.show();
			break;
		case R.id.set_screen_light:
			myAjustLightDialog.show();
			break;
		case R.id.set_background:
			myAjustBackgroundDialog.show();
			break;
		case R.id.set_page_effect:
			myAjustPaperMode.show();
			break;
		default:
		}
		return super.onOptionsItemSelected(item);
	}

	OnClickListener mShelfItemOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, MyReadActivity.class);
			intent.putExtra("filePath", bookInfos.get(v.getId()).path);
			startActivityForResult(intent, 1);
		}

	};

	// 添加长按点击
	OnCreateContextMenuListener mCreateMenuListener = new OnCreateContextMenuListener() {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// menu.setHeaderTitle(String.valueOf(v.getId()));
			menu.add(0, 0, v.getId(), "详细信息");
			menu.add(0, 1, v.getId(), "删除本书");
			menu.add(0, 2, v.getId(), "分享本书");
			menu.add(0, 3, v.getId(), "添至桌面");
		}
	};

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		chooseBook = bookInfos.get(item.getOrder());
		switch (item.getItemId()) {
		case 0:
			myShowInfoDialog.show(chooseBook);
			break;
		case 1:
			myDeleteDialog.show(chooseBook.name, deleteBack);
			break;
		case 2:
			Intent share = new Intent(Intent.ACTION_SEND);
			File file = new File(chooseBook.path);
			share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
			share.setType("*/*");
			startActivity(Intent.createChooser(share, "分享文本"));
			break;
		case 3:
			addShortcut(chooseBook);
			break;
		default:
			break;
		}

		return true;
	};

	private void addShortcut(BookInfo bookInfo) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 显示的名字
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, bookInfo.name);
		// 显示的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
				R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

		// 不允许重复创建
		shortcut.putExtra("duplicate", false);
		
		Intent intent = new Intent(getApplicationContext(), MyReadActivity.class);
		intent.putExtra("filePath", bookInfo.path);
		intent.putExtra("fromShortcut", true);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

		// 发送广播用以创建shortcut
		this.sendBroadcast(shortcut);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		update();
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 800) // System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				ToastUtil.show(this, "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				this.finish();
				System.exit(0);
			}
		}
		return true;
	}

}
