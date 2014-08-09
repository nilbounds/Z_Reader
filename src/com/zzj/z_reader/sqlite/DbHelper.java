package com.zzj.z_reader.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.zzj.z_reader.bean.BookInfo;
import com.zzj.z_reader.service.BookInfoService;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * title:存储打开过的文件
 * 
 * @author zzj
 * @date 2014-7-30
 */
public class DbHelper extends SQLiteOpenHelper implements BookInfoService {

	private final static String DATABASE_NAME = "zzj_db";
	private final static int DATABASE_VERSION = 1;

	private final static String TABLE_BOOK_INFO = "book_info";

	private final static String COLUMN_NAME = "name";
	private final static String COLUMN_PATH = "path";

	public DbHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		// 创建书本信息表
		StringBuffer sqlCreateBookInfoTable = new StringBuffer();
		sqlCreateBookInfoTable.append("create table ").append(TABLE_BOOK_INFO)
				.append("(_id integer primary key autoincrement,")
				.append(" " + COLUMN_NAME + " text,")
				.append(" " + COLUMN_PATH + " text);");
		db.execSQL(sqlCreateBookInfoTable.toString());

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// 删除旧的数据库表
		String sqlDropBookInfoTable = " DROP TABLE IF EXISTS" + TABLE_BOOK_INFO;
		db.execSQL(sqlDropBookInfoTable);
		onCreate(db);
	}

	@Override
	public boolean isHaving(String filePath) {
		SQLiteDatabase db = this.getReadableDatabase();
		// 将查询结果根据id的内容降序排列
		Cursor cursor = db.query(TABLE_BOOK_INFO, new String[] { COLUMN_PATH },
				COLUMN_PATH + " like ?", new String[] { filePath }, null, null,
				null);
		if (cursor.getCount() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public BookInfo getBookInfo(int id) {
		BookInfo bookInfo = new BookInfo();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_BOOK_INFO, null, "_id=" + id, null,
				null, null, null);
		cursor.moveToPosition(0);
		bookInfo.id = id;
		bookInfo.name = cursor.getString(1);
		bookInfo.path = cursor.getString(2);
		db.close();
		return bookInfo;
	}

	@Override
	public long insertBookInfo(String filename, String filePath) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME, filename);
		cv.put(COLUMN_PATH, filePath);
		long row = db.insert(TABLE_BOOK_INFO, null, cv);
		return row;
	}

	@Override
	public List<BookInfo> getAllBookInfo() {
		List<BookInfo> books = new ArrayList<BookInfo>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_BOOK_INFO, null, null, null, null, null,
				" _id desc");
		int count = cursor.getCount();
		for (int i = 0; i < count; i++) {
			cursor.moveToPosition(i);
			BookInfo book = new BookInfo();
			book.id = cursor.getInt(0);
			book.name = cursor.getString(1);
			book.path = cursor.getString(2);
			books.add(book);
		}
		return books;
	}

	@Override
	public void deleteBookInfo(String filePath) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = COLUMN_PATH + "=?";
		String[] whereValue = { filePath };
		db.delete(TABLE_BOOK_INFO, where, whereValue);
	}

	@Override
	public void deleteBookInfo(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = "_id" + "=?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_BOOK_INFO, where, whereValue);
	}

	@Override
	public void updateBookInfo(int id, String bookName, String bookPath) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = "_id" + "=?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME, bookName);
		cv.put(COLUMN_PATH, bookPath);
		db.update(TABLE_BOOK_INFO, cv, where, whereValue);
	}

}
