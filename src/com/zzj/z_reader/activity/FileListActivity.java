package com.zzj.z_reader.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zzj.z_reader.R;
import com.zzj.z_reader.adapter.FileListAdapter;
import com.zzj.z_reader.bean.FileListItem;
import com.zzj.z_reader.service.BookInfoService;
import com.zzj.z_reader.service.BookInfoServiceImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * title:文件列表界面
 * 
 * @author zzj
 * @date 2014-7-26
 */
public class FileListActivity extends Activity implements OnItemClickListener {

	private ListView lvFileList;
	private FileListAdapter adapter;

	private List<FileListItem> directoryEntries = new ArrayList<FileListItem>();
	private File currentDirectory = new File("/");

	private BookInfoService bookInfoService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_list);

		lvFileList = (ListView) findViewById(R.id.lvFileList);
		bookInfoService = new BookInfoServiceImpl(getBaseContext());
		adapter = new FileListAdapter(getBaseContext(), directoryEntries);
		lvFileList.setAdapter(adapter);
		lvFileList.setOnItemClickListener(this);
		browseToRoot();
	}

	/**
	 * 浏览文件系统的根目录
	 */
	private void browseToRoot() {
		browseTo(new File("/"));
	}

	/**
	 * 浏览指定的目录,如果是文件则进行打开操作
	 * 
	 * @param file
	 */
	private void browseTo(final File file) {
		this.setTitle(file.getAbsolutePath());
		if (file.isDirectory()) {
			this.currentDirectory = file;
			File[] files = file.listFiles();
			if (files == null) {
				files = new File[] {};
			}
			fill(files);
		} else {
			if (checkEndsWithInStringArray(file.getName(), getResources()
					.getStringArray(R.array.fileEndingText))) {
				// 打开文件的操作
				String filePath = this.currentDirectory.getAbsolutePath() + "/"
						+ file.getName();
				//去除后缀
				String fileName = file.getName();
				int pos = fileName.indexOf(".");
				fileName = fileName.substring(0,pos);
				
				Intent intent = new Intent(getBaseContext(),
						MyReadActivity.class);
				intent.putExtra("filePath", filePath);
				if (bookInfoService.isHaving(filePath)) {
					bookInfoService.deleteBookInfo(filePath);
				}
				bookInfoService.insertBookInfo(fileName, filePath);
				startActivity(intent);
			}

		}
	}

	/**
	 * 返回上一级目录
	 */
	private void upOneLevel() {
		if (this.currentDirectory.getParent() != null)
			this.browseTo(this.currentDirectory.getParentFile());
	}

	/**
	 * 设置ListActivity的源
	 * 
	 * @param files
	 */
	private void fill(File[] files) {
		// 清空列表
		this.directoryEntries.clear();

		// 添加一个当前目录的选项
		this.directoryEntries.add(new FileListItem(
				getString(R.string.current_dir), R.drawable.folder));
		// 如果不是根目录则添加上一级目录项
		if (this.currentDirectory.getParent() != null)
			this.directoryEntries.add(new FileListItem(
					getString(R.string.up_one_level), R.drawable.uponelevel));

		int currentIcon = 0;

		for (File currentFile : files) {
			// 判断是一个文件夹还是一个文件

			if (currentFile.isDirectory()) {
				currentIcon = R.drawable.folder;
			} else {
				// 取得文件名
				String fileName = currentFile.getName();
				// 根据文件名来判断文件类型，设置不同的图标
				if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingImage))) {
					currentIcon = R.drawable.image;
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingWebText))) {
					currentIcon = R.drawable.webtext;
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingPackage))) {
					currentIcon = R.drawable.packed;
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingAudio))) {
					currentIcon = R.drawable.audio;
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingVideo))) {
					currentIcon = R.drawable.video;
				} else {
					currentIcon = R.drawable.text;
				}
			}
			// 确保只显示文件名、不显示路径如：/sdcard/111.txt就只是显示111.txt
			int currentPathStringLenght = this.currentDirectory
					.getAbsolutePath().length();
			this.directoryEntries.add(new FileListItem(currentFile
					.getAbsolutePath().substring(currentPathStringLenght),
					currentIcon));
		}
		Collections.sort(this.directoryEntries);
		// 将表设置到ListAdapter中
		// adapter.setList(this.directoryEntries);
		// 为ListActivity添加一个ListAdapter
		adapter.notifyDataSetChanged();
	}

	/**
	 * 通过文件名判断是什么类型的文件
	 * 
	 * @param checkItsEnd
	 * @param fileEndings
	 * @return
	 */
	private boolean checkEndsWithInStringArray(String checkItsEnd,
			String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// 取得选中的一项的文件名
		String selectedFileString = this.directoryEntries.get(position).fileName;

		if (selectedFileString.equals(getString(R.string.current_dir))) {
			// 如果选中的是刷新
			this.browseTo(this.currentDirectory);
		} else if (selectedFileString.equals(getString(R.string.up_one_level))) {
			// 返回上一级目录
			this.upOneLevel();
		} else {

			File clickedFile = null;

			clickedFile = new File(this.currentDirectory.getAbsolutePath()
					+ this.directoryEntries.get(position).fileName);

			if (clickedFile != null)
				this.browseTo(clickedFile);
		}
	}

}
