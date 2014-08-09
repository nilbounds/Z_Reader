package com.zzj.z_reader.service;

import java.util.List;

import com.zzj.z_reader.bean.BookInfo;

public interface BookInfoService {

	/**
	 * 判断是否已经存储该文件信息
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean isHaving(String filePath);

	/**
	 * 获取对应id的书本信息
	 * 
	 * @param id
	 * @return
	 */
	public BookInfo getBookInfo(int id);

	/**
	 * 插入该文件信息
	 * 
	 * @param filename
	 * @param filePath
	 * @return
	 */
	public long insertBookInfo(String filename, String filePath);

	/**
	 * 获取所有书本的信息列表
	 * 
	 * @return
	 */
	public List<BookInfo> getAllBookInfo();

	/**
	 * 删除路径为filePath的书本信息
	 * 
	 * @param filePath
	 */
	public void deleteBookInfo(String filePath);

	/**
	 * 删除对应id的书本信息
	 * 
	 * @param id
	 */
	public void deleteBookInfo(int id);

	/**
	 * 更新对应id的书本信息
	 * 
	 * @param id
	 * @param bookName
	 * @param bookPath
	 */
	public void updateBookInfo(int id, String bookName, String bookPath);

}
