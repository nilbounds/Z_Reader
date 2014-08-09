package com.zzj.z_reader.bean;

/**
 * title:文件列表项的数据
 * 
 * @author zzj
 * @date 2014-7-26
 */
public class FileListItem implements Comparable<FileListItem> {

	public String fileName = "";
	public int fileIcon = 0;

	public FileListItem(String fileName, int fileIcon) {
		this.fileName = fileName;
		this.fileIcon = fileIcon;
	}

	@Override
	public int compareTo(FileListItem another) {
		// TODO Auto-generated method stub
		if (this.fileName != null && !this.fileName.equals("")) {
			return this.fileName.compareTo(another.fileName);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
